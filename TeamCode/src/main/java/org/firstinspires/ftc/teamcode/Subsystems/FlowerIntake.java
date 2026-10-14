package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Config.Config;
import org.firstinspires.ftc.teamcode.Config.Robot;

public class FlowerIntake {
    private final Robot robot;

    public FlowerIntake(Robot robot) {
        this.robot = robot;
    }

    public void setFlowerDown() {
        robot.setServoPositions(Config.flowerDown);
    }
    public void setFlowerUp() {
        robot.setServoPositions(Config.flowerUp);
    }
}
