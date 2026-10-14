package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Config.Robot;
import org.firstinspires.ftc.teamcode.Config.Config;

public class ShooterLeft { //MAX RPM is 4150 18F 30P 1D, try 2900 RPM pollen and 3000 RPM nectar
    private final Robot robot = new Robot();
    private DcMotorEx shooter;

    public void setTargetRPM(double targetRPM) {
        Config.TARGET_RPM_LEFT = targetRPM;
    }

    public double getTargetRPM() {
        return Config.TARGET_RPM_LEFT;
    }


    public void applyPIDF() {
        shooter.setVelocityPIDFCoefficients(Config.kP_LEFT, Config.kI_LEFT, Config.kD_LEFT, Config.kF_LEFT);}

    public void update() {
        double targetMotorRPM = Config.TARGET_RPM_LEFT / Config.GEAR_RATIO;
        double targetTicksPerSec = (targetMotorRPM * Config.TICKS_PER_REV) / 60.0;
        shooter.setVelocity(targetTicksPerSec);
    }

    public void stop() {
        shooter.setPower(0);
        shooter.setVelocity(0);
    }

    public double getShooterVelocity() {

        double currTicksPerSec = shooter.getVelocity(); // ticks/s of motor
        double currMotorRPM = (currTicksPerSec * 60) / Config.TICKS_PER_REV;

        return currMotorRPM;
    }

    public boolean isAtTargetThreshold() {
        return ((getShooterVelocity() > (getTargetRPM() - Config.THRESHOLD)) && getShooterVelocity() != 0);
    }
}
