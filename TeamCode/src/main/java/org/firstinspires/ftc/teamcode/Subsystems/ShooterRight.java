package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Config.Config;
import org.firstinspires.ftc.teamcode.Config.Robot;

public class ShooterRight {  //MAX RPM is 4150 18.5F 30P 1D, try 2900 RPM pollen and 3000 RPM nectar
    private final Robot robot = new Robot();
    private DcMotorEx shooter;

    public void setTargetRPM(double targetRPM) {
        Config.TARGET_RPM_RIGHT = targetRPM;
    }

    public double getTargetRPM() {
        return Config.TARGET_RPM_RIGHT;
    }


    public void applyPIDF() {
        shooter.setVelocityPIDFCoefficients(Config.kP_RIGHT, Config.kI_RIGHT, Config.kD_RIGHT, Config.kF_RIGHT);}

    public void update() {
        double targetMotorRPM = Config.TARGET_RPM_RIGHT / Config.GEAR_RATIO;
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
