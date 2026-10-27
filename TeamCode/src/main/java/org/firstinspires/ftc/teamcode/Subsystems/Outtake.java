package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config.Robot;

public class Outtake {
    private Robot robot;
    private Servo servoBlocker;
    private Servo servoRamp;
    private Servo servoHood;
    public Outtake(Robot robot, Servo servoBlocker, Servo servoRamp, Servo servoHood) {
        this.robot = robot;
        this.servoBlocker = servoBlocker;
        this.servoRamp = servoRamp;
        this.servoHood = servoHood;
    }

    public void setBlockerPosition(double position) {
        servoBlocker.setPosition(position);
    }

    public void setRampPosition(double position) {
        servoRamp.setPosition(position);
    }

    public void setHoodPosition(double position) {
        servoHood.setPosition(position);
    }

    public double getBlockerPosition() {
        return servoBlocker.getPosition();
    }

    public double getRampPosition() {
        return servoRamp.getPosition();
    }

    public double getHoodPosition() {
        return servoHood.getPosition();
    }
}
