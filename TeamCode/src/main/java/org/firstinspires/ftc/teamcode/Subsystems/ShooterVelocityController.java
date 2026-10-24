package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ShooterVelocityController {

    // ==== FF constants (replace with your fitted values) ====
    private double K_V;//= 1.85e-04;   // example placeholder
    private double K_S;//= 0.1580;         // example placeholder
    // ==== hardware / params ====
    private final DcMotorEx leader;   // has encoder
    private final VoltageSensor vSensor;
    private final double ticksPerRev;
    private final double nominalVolt;

    // ==== PID gains (tune as needed) ====
    private double Kp;
    private double Ki;
    private double Kd;

    // ==== I-term guards ====
    private double iZoneRpm   = 1000.0;
    private double iMaxAbs    = 0.30;
    private double leakPerSec = 0.05;
    private double satMargin  = 0.02;

    // ==== D low-pass filter ====
    private double dCutHz = 10.0;

    // ==== state ====
    private final ElapsedTime timer = new ElapsedTime();
    private double lastTimeS = 0.0;
    private double lastActualRpm = 0.0;
    private double dActualFilt = 0.0;
    private double iTerm = 0.0;
    private double targetRpm = 0.0;

    public ShooterVelocityController(
            DcMotorEx leader, VoltageSensor vSensor,
            double ticksPerRev, double nominalVolt,
            double kV, double kS, double Kp, double Ki, double Kd
    ) {
        this.leader = leader;
        this.vSensor = vSensor;
        this.ticksPerRev = ticksPerRev;
        this.nominalVolt = nominalVolt;
        this.K_V = kV;
        this.K_S = kS;
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
    }

    public ShooterVelocityController setGains(double kp, double ki, double kd) {
        this.Kp = kp; this.Ki = ki; this.Kd = kd; return this;
    }
    public ShooterVelocityController setIZone(double rpm) { this.iZoneRpm = rpm; return this; }
    public ShooterVelocityController setIMax(double iMax) { this.iMaxAbs = iMax; return this; }
    public ShooterVelocityController setLeak(double leak) { this.leakPerSec = leak; return this; }
    public ShooterVelocityController setSatMargin(double m) { this.satMargin = m; return this; }
    public ShooterVelocityController setDCutHz(double hz) { this.dCutHz = hz; return this; }

    public void start() {
        leader.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leader.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        iTerm = 0; dActualFilt = 0; lastActualRpm = 0;
        timer.reset(); lastTimeS = timer.seconds();
    }

    public void setTargetRpm(double rpm) { targetRpm = Math.max(0.0, rpm); } // forward only

    /** Call ~every 10ms; returns applied power [0..1] */
    public double update() {
        double now = timer.seconds();
        double dt = now - lastTimeS;
        lastTimeS = now;
        if (dt <= 0) dt = 0.01;

        double ticksPerSec = leader.getVelocity();
        double actualRpm = ticksPerSec * 60.0 / ticksPerRev;

        double vBatt = (vSensor != null) ? vSensor.getVoltage() : nominalVolt;
        double ff = (targetRpm > 0)
                ? (K_V * targetRpm + K_S) * (nominalVolt / Math.max(1e-6, vBatt))
                : 0.0;

        double err = targetRpm - actualRpm;
        double pTerm = Kp * err;

        double dActual = (actualRpm - lastActualRpm) / dt;
        lastActualRpm = actualRpm;

        double RC = 1.0 / (2.0 * Math.PI * Math.max(0.1, dCutHz));
        double alpha = dt / (RC + dt);
        dActualFilt += alpha * (dActual - dActualFilt);
        double dTerm = -Kd * dActualFilt; // D on measurement

        double unconstrained = ff + pTerm + iTerm + dTerm;
        boolean satHi = unconstrained > (1.0 - satMargin);
        boolean satLo = unconstrained < (0.0 + satMargin);
        boolean allowI = (targetRpm > 0) && Math.abs(err) < iZoneRpm && !satHi && !satLo;

        if (allowI) {
            iTerm += Ki * err * dt;
            iTerm = clamp(iTerm, -iMaxAbs, iMaxAbs);
        } else {
            iTerm *= Math.max(0.0, 1.0 - leakPerSec * dt);
        }

        double power = clamp(ff + pTerm + iTerm + dTerm, 0.0, 1.0);
        leader.setPower(power);

        return power;
    }

    public void stop() { leader.setPower(0); }
    public double getActualRpm() { return lastActualRpm; }
    public double getTargetRpm() { return targetRpm; }

    private static double clamp(double x, double lo, double hi) { return (x < lo) ? lo : (x > hi) ? hi : x; }
}