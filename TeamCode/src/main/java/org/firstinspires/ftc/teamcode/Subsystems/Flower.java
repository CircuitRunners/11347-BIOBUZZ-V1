package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config.ConfigTemp;
import org.firstinspires.ftc.teamcode.Config.Robot;
import org.firstinspires.ftc.teamcode.Main.hardwareMappings;

public class Flower {
    private Servo servoFlower;
    private boolean flowerDown;
    public Flower() {
        servoFlower = hardwareMap.get(Servo.class, hardwareMappings.flowerServo);
        flowerDown = false;
    }
    public void setFlowerDown() {
        servoFlower.setPosition(0.0);
    }
    public void setFlowerUp() {
        servoFlower.setPosition(0.0);
    }
    public void flowerToggle(){
        if(flowerDown){
            setFlowerUp();
        }else{
            setFlowerDown();
        }
        flowerDown = !flowerDown;
    }
}
