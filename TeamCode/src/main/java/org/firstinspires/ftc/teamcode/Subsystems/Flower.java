package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Main.hardwareMappings;

@Configurable
public class Flower {
    private Servo servoFlower;
    private boolean flowerDown;
    public static double flowerDownPos = 0.0;
    public static double flowerUpPos = 0.0;
    public Flower() {
        servoFlower = hardwareMap.get(Servo.class, hardwareMappings.flowerServo);
        flowerDown = false;
    }
    public void setFlowerDown() {
        servoFlower.setPosition(flowerDownPos);
    }
    public void setFlowerUp() {
        servoFlower.setPosition(flowerUpPos);
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
