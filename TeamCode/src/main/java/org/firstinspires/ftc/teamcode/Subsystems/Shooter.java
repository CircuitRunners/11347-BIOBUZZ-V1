package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Config.Robot;
import org.firstinspires.ftc.teamcode.Config.ConfigTemp;

public class Shooter { //MAX RPM is 4150 18F 30P 1D, try 2900 RPM pollen and 3000 RPM nectar
    private final Robot robot;
    private DcMotorEx shooter;
    private double TARGET_RPM = 0.0;
    private double kP;
    private double kI;
    private double kD;
    private double kF;
    public Shooter(Robot robot, DcMotorEx shooter, double kP, double kI, double kD, double kF) {
        this.robot = robot;
        this.shooter = shooter;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;

        applyPIDF();

    }
    public void pollenRPM() {
        setTargetRPM(ConfigTemp.POLLEN_RPML);
    }

//    public void nectarRPM() {
//        setTargetRPM(Config.NECTAR_RPM);
//    }

    public void setTargetRPM(double targetRPM) {
        TARGET_RPM = targetRPM;
    }

    public double getTargetRPM() {
        return TARGET_RPM;
    }

    public void applyPIDF() {
        shooter.setVelocityPIDFCoefficients(kP, kI, kD, kF);}

    public void update() {
        double targetMotorRPM = TARGET_RPM / ConfigTemp.GEAR_RATIO;
        double targetTicksPerSec = (targetMotorRPM * ConfigTemp.TICKS_PER_REV) / 60.0;
        shooter.setVelocity(targetTicksPerSec);
    }

    public void stop() {
        shooter.setPower(0);
        shooter.setVelocity(0);
    }

    public double getShooterVelocity() {

        double currTicksPerSec = shooter.getVelocity(); // ticks/s of motor
        double currMotorRPM = (currTicksPerSec * 60) / ConfigTemp.TICKS_PER_REV;

        return currMotorRPM;
    }

    public boolean isAtTargetThreshold() {
        return ((getShooterVelocity() > (getTargetRPM() - ConfigTemp.THRESHOLD)) && getShooterVelocity() != 0);
    }
}
