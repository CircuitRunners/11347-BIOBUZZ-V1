package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Config.ConfigTemp;
import org.firstinspires.ftc.teamcode.Config.Robot;

public class FlowerIntake {
    private final Robot robot;

    public FlowerIntake(Robot robot) {
        this.robot = robot;
    }

    public void setFlowerDown() {
        robot.servoFlower.setPosition(ConfigTemp.flowerDown);
    }
    public void setFlowerUp() {
        robot.servoFlower.setPosition(ConfigTemp.flowerUp);
    }
}
