package org.firstinspires.ftc.teamcode.Test;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Config.Robot;

/**
 * Start with kF auto, then raise/lower kF until the motor reaches near target at steady state (low error).
 * Add small kP to tighten response (too high = oscillation).
 * Only add kI if you see steady-state error that kF+kP can’t fix, prob won't be needed
 * Add kD to damp oscillations during spin-up, might not be needed
 */

@Configurable
@TeleOp(name="ShooterPIDTuner")
public class ShooterPIDTuner extends OpMode {
    private DcMotorEx shooter;

    // Dashboard-tunable constants
    public static double TARGET_RPM = 0.0;
    public static double GEAR_RATIO = 1.0;
    public static double TICKS_PER_REV = 28.0;


    // PIDF (velocity)
    public static double kP = 0.0;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static double kF = 0.0;

    public double kfValue;

    // Toggles shooter on and off in dashboard
    public static boolean runShooter = false;
    public static boolean isLeft = true;

    private final Robot robot = new Robot();;

    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    @Override
    public void init() {
        robot.init(hardwareMap);

        if (isLeft) {
            shooter = robot.shooterLeft;
        }
        else {
            shooter = robot.shooterRight;
        }

        applyPIDF();

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.addLine("init done");
        telemetryM.update(telemetry);
    }

    //TODO: test if this works, if not remove
    private void applyPIDF() {
        double kFLocal = kF;
        if (kFLocal == 0.0) {
            // Based on achievable max ticks/s reported by the SDK
            // Good starting point for later tuning in Dashboard.
            double maxTps = shooter.getMotorType().getAchieveableMaxTicksPerSecond();
            if (maxTps <= 0) maxTps = (1620.0 * TICKS_PER_REV) / 60.0;
            // REV internal scaling expects kF around 32767/maxVelocity as a reasonable baseline
            kFLocal = 32767.0 / maxTps;
        }
        kfValue = kFLocal;
        shooter.setVelocityPIDFCoefficients(kP, kI, kD, kFLocal);
    }

    @Override
    public void loop() {
        robot.clearBulkCache();
        /** Live PIDF updates via dashboard */
        applyPIDF();

        double targetMotorRPM = TARGET_RPM / GEAR_RATIO;
        double targetTicksPerSec = (targetMotorRPM * TICKS_PER_REV) / 60.0;

        if (runShooter) {
            shooter.setVelocity(targetTicksPerSec); // ticks/s
        } else {
            shooter.setVelocity(0);
        }

        // Gets current velo of motor
        double currTicksPerSec = shooter.getVelocity(); // ticks/s of motor
        double currMotorRPM = (currTicksPerSec * 60.0) / TICKS_PER_REV;
        double currShooterRPM = currMotorRPM * GEAR_RATIO;

        // Tuning Stuff
        double errorMotorRPM = targetMotorRPM - currMotorRPM;
        double errorShooterRPM = TARGET_RPM - currShooterRPM;

        // ===== Graphs on FTC Dashboard =====
        telemetryM.addData("target_shooter_rpm", TARGET_RPM);
        telemetryM.addData("current_shooter_rpm", currShooterRPM);
        telemetryM.addData("target_motor_rpm", targetMotorRPM);
        telemetryM.addData("current_motor_rpm", currMotorRPM);
        telemetryM.addData("error_shooter_rpm", errorShooterRPM);
        telemetryM.addData("error_motor_rpm", errorMotorRPM);
        telemetryM.addData("motor_ticks_per_sec", currTicksPerSec);
        telemetryM.addData("kF Local Value: ", kfValue);
        telemetryM.update(telemetry);
    }
}
