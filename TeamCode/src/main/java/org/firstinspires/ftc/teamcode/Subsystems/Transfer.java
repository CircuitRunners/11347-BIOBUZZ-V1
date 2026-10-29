package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.graphics.Color;

import org.firstinspires.ftc.teamcode.Config.Config;
import org.firstinspires.ftc.teamcode.Config.ConfigTemp;
import org.firstinspires.ftc.teamcode.Config.Robot;
import org.firstinspires.ftc.teamcode.Config.cDrive;
import org.firstinspires.ftc.teamcode.Main.hardwareMappings;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShootCommand;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import java.util.ArrayList;
import java.util.HashMap;

public class Transfer {
    private NormalizedColorSensor leftColorSensorOB; //One Bottom
    private NormalizedColorSensor leftColorSensorOS; //One Side
    private NormalizedColorSensor leftColorSensorT; // Two

    cDrive drive = new cDrive();

    NormalizedColorSensor[] colorSensors = new NormalizedColorSensor[3]; // temp: 3 - final will be 6

    public Transfer(){
        leftColorSensorOB = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorLeftOneBottom);
        leftColorSensorOS = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorLeftOneSide);
        leftColorSensorT = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorLeftTwo);


        colorSensors = new NormalizedColorSensor[]{leftColorSensorOB, leftColorSensorOS, leftColorSensorT};
    }

    public void setTransferPower(double power) {
    }

    public void transferOn() {

    }

    public void transferSpit() {

    }

    public void transferStop() {


    }

    public ShootCommand determineShootOrder(){
        String[] returnList = {"NONE", "NONE", "NONE"};

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
