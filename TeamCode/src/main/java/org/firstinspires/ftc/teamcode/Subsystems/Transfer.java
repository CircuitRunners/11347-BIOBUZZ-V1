package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Config.Config;
import org.firstinspires.ftc.teamcode.Config.Robot;

public class Transfer {
    private final Robot robot;

    public Transfer(Robot robot) {
        this.robot = robot;
    }

    public void setTransferPower(double power) {
        robot.transferLeft.setPower(power);
        robot.transferRight.setPower(power);
    }

    public void transferOn() {
        setTransferPower(Config.intakePower);
    }

    public void transferSpit() {
        setTransferPower(Config.spitPower);
    }

    public void transferStop() {
        setTransferPower(0);
    }
}
