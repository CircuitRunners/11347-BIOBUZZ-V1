package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Config.Robot;

public class Outtake {
    private final Robot robot;
    public Outtake(Robot robot) {
        this.robot = robot;
    }

    public void setBlockerPositions(double positionL, double positionR) {
        robot.servoBlockerL.setPosition(positionL);
        robot.servoBLockerR.setPosition(positionR);
    }
}
