package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.graphics.Color;


import org.firstinspires.ftc.teamcode.Main.hardwareMappings;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShootCommand;
import org.firstinspires.ftc.teamcode.Util.enums.GAMEPIECE;
import org.firstinspires.ftc.teamcode.Util.enums.SIDE;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

public class Transfer {
    private NormalizedColorSensor leftColorSensorO; //One
    private NormalizedColorSensor leftColorSensorT; // Two
    private NormalizedColorSensor rightColorSensorO; // One
    private NormalizedColorSensor rightColorSensorT; // Two

    private DcMotorEx transferLeft;
    private DcMotorEx transferRight;

    private Servo blockerLeft;
    private Servo blockerRight;

    private double blockerClosed = 0.0;
    private double blockerOpen = 0.0;

    NormalizedColorSensor[] colorSensors;

    public Transfer(){
        leftColorSensorO = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorLeftOne);
        leftColorSensorT = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorLeftTwo);
        rightColorSensorO = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorRightOne);
        rightColorSensorT = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorRightTwo);

        colorSensors = new NormalizedColorSensor[]{leftColorSensorO, leftColorSensorT, rightColorSensorO, rightColorSensorT};

        transferLeft = hardwareMap.get(DcMotorEx.class, hardwareMappings.transferLeft);
        transferRight = hardwareMap.get(DcMotorEx.class, hardwareMappings.transferRight);

        blockerLeft = hardwareMap.get(Servo.class, hardwareMappings.blockerLeft);
        blockerRight = hardwareMap.get(Servo.class, hardwareMappings.blockerRight);
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

    public void blockerState(SIDE side, boolean on){
        Servo target = side.equals(SIDE.LEFT) ? blockerLeft : blockerRight;
        target.setPosition(on ? blockerOpen : blockerClosed);
    }
    public void transferStop() {
        setTransferPower(0.0);
    }
    public ShootCommand determineShootOrder(){
        GAMEPIECE[] returnList = {GAMEPIECE.NONE, GAMEPIECE.NONE, GAMEPIECE.NONE, GAMEPIECE.NONE};

        int index = 0;
        for(NormalizedColorSensor i : colorSensors){
            NormalizedRGBA color = i.getNormalizedColors();
            float[] hsv = new float[3];
            Color.RGBToHSV((int)(color.red * 255), (int)(color.green * 255), (int)(color.blue * 255), hsv);

            if(hsv[1] >= 50 && hsv[2] >= 50){
                if(hsv[0] >= 40 && hsv[0] <= 60){
                    returnList[index] = GAMEPIECE.POLLEN;
                }else
                if(hsv[0] >= 195 && hsv[0] <= 260){
                    returnList[index] = GAMEPIECE.NECTAR;
                }else
                if(hsv[0] >= 335 || hsv[0] <= 20) {
                    returnList[index] = GAMEPIECE.NECTAR;
                }else{
                    returnList[index] = GAMEPIECE.NONE;
                }
            }else{
                returnList[index] = GAMEPIECE.NONE;
            }

            index ++;
        }

        return new ShootCommand(returnList);
    }
}
