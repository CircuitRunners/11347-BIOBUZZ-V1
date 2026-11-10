package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.graphics.Color;

import org.firstinspires.ftc.teamcode.Config.Config;
import org.firstinspires.ftc.teamcode.Config.ConfigTemp;
import org.firstinspires.ftc.teamcode.Config.Robot;
import org.firstinspires.ftc.teamcode.Config.cDrive;
import org.firstinspires.ftc.teamcode.Main.hardwareMappings;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShootCommand;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import java.util.ArrayList;
import java.util.HashMap;

public class Transfer {
    private NormalizedColorSensor leftColorSensorOL; //One Left
    private NormalizedColorSensor leftColorSensorOR; //One Right
    private NormalizedColorSensor leftColorSensorT; // Two
    private NormalizedColorSensor rightColorSensorOL; // One Left
    private NormalizedColorSensor getRightColorSensorOR; // One Right
    private NormalizedColorSensor getRightColorSensorT; // Two

    private DcMotorEx transferLeft;
    private DcMotorEx transferRight;

    NormalizedColorSensor[] colorSensors;

    public Transfer(){
        leftColorSensorOL = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorLeftOneLeft);
        leftColorSensorOR = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorLeftOneRight);
        leftColorSensorT = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorLeftTwo);
        rightColorSensorOL = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorRightOneLeft);
        getRightColorSensorOR = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorRightOneRight);
        getRightColorSensorT = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorRightTwo);

        colorSensors = new NormalizedColorSensor[]{leftColorSensorOL, leftColorSensorOR, leftColorSensorT, rightColorSensorOL, getRightColorSensorOR, getRightColorSensorT};

        transferLeft = hardwareMap.get(DcMotorEx.class, hardwareMappings.transferLeft);
        transferRight = hardwareMap.get(DcMotorEx.class, hardwareMappings.transferRight);
    }

    public void setTransferPower(double power) {
        transferLeft.setPower(power);
        transferRight.setPower(power);
    }

    public void transferIntakeOn() {
        setTransferPower(0.7);
    }

    public void transferTransferOn(){
        setTransferPower(0.4);
    }

    public void transferIntakeSpit() {
        setTransferPower(-0.4);
    }

    public void transferStop() {
        setTransferPower(0.0);
    }

    public ShootCommand determineShootOrder(){
        String[] returnList = {"NONE", "NONE", "NONE", "NONE", "NONE", "NONE"};

        int index = 0;
        for(NormalizedColorSensor i : colorSensors){
            NormalizedRGBA color = i.getNormalizedColors();
            float[] hsv = new float[3];
            Color.RGBToHSV((int)(color.red * 255), (int)(color.green * 255), (int)(color.blue * 255), hsv);

            if(hsv[1] >= 50 && hsv[2] >= 50){
                if(hsv[0] >= 40 && hsv[0] <= 60){
                    returnList[index] = "POLLEN";
                }else
                if(hsv[0] >= 195 && hsv[0] <= 260){
                    returnList[index] = "NECTAR";
                }else
                if(hsv[0] >= 335 || hsv[0] <= 20) {
                    returnList[index] = "NECTAR";
                }else{
                    returnList[index] = "NONE";
                }
            }else{
                returnList[index] = "NONE";
            }

            index ++;
        }

        return new ShootCommand(returnList);
    }
}
