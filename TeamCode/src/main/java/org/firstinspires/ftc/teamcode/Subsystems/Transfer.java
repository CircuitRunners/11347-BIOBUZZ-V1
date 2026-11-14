package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Color;


import org.firstinspires.ftc.teamcode.Main.hardwareMappings;
import org.firstinspires.ftc.teamcode.Util.enums.GAMEPIECE;
import org.firstinspires.ftc.teamcode.Util.enums.SIDE;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.hardware.Servo;

@Configurable
public class Transfer {
    private RevColorSensorV3 leftColorSensorO;  // One
    private RevColorSensorV3 leftColorSensorT;  // Two
    private RevColorSensorV3 rightColorSensorO; // One
    private RevColorSensorV3 rightColorSensorT; // Two

    private DcMotorEx transferLeft;
    private DcMotorEx transferRight;

    private Servo blockerLeft;
    private Servo blockerRight;

    public static double blockerClosed = 0.5;
    public static double blockerOpen = 0.32 ;

    public static double intakeSpeed = 1.0;
    public static double transferSpeed = 1.0;
    public static double spitSpeed = -0.5;

    private boolean leftState = false;
    private boolean rightState = false;
    private HardwareMap hardwareMap;

    RevColorSensorV3[] colorSensors;

    public Transfer(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        leftColorSensorO = hardwareMap.get(RevColorSensorV3.class, hardwareMappings.colorSensorLeftOne);
        leftColorSensorT = hardwareMap.get(RevColorSensorV3.class, hardwareMappings.colorSensorLeftTwo);
        rightColorSensorO = hardwareMap.get(RevColorSensorV3.class, hardwareMappings.colorSensorRightOne);
        rightColorSensorT = hardwareMap.get(RevColorSensorV3.class, hardwareMappings.colorSensorRightTwo);

        colorSensors = new RevColorSensorV3[]{leftColorSensorO, leftColorSensorT, rightColorSensorO, rightColorSensorT};

        transferLeft = hardwareMap.get(DcMotorEx.class, hardwareMappings.transferLeft);
        transferRight = hardwareMap.get(DcMotorEx.class, hardwareMappings.transferRight);

        //transferLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        blockerLeft = hardwareMap.get(Servo.class, hardwareMappings.blockerLeft);
        blockerRight = hardwareMap.get(Servo.class, hardwareMappings.blockerRight);
    }

    public void tick(){
        blockerLeft.setPosition(leftState ? blockerOpen : blockerClosed);
        blockerRight.setPosition(rightState ? blockerOpen : blockerClosed);
    }

    public void setTransferPower(double power) {
        transferLeft.setPower(power);
        transferRight.setPower(power);
    }

    public void transferIntakeOn() {
        setTransferPower(intakeSpeed);
    }
    public void transferTransferOn(){
        setTransferPower(transferSpeed);
    }
    public void transferIntakeSpit() {
        setTransferPower(spitSpeed);
    }

    public void blockerState(SIDE side, boolean on){
        if(side.equals(SIDE.LEFT)){
            leftState = on;
        }
        if(side.equals(SIDE.RIGHT)){
            rightState = on;
        }
    }
    public void transferStop() {
        setTransferPower(0.0);
    }
    public GAMEPIECE[] determineShootOrder(){
        GAMEPIECE[] returnList = new GAMEPIECE[]{GAMEPIECE.NONE, GAMEPIECE.NONE, GAMEPIECE.NONE, GAMEPIECE.NONE};
        int index = 0;
        for(RevColorSensorV3 i : colorSensors){
            double[] rgb = {i.red(), i.green(), i.blue()};
            i.setGain(-15);
            double max = Math.max(rgb[0], Math.max(rgb[1], rgb[2]));
            if(max > 255){
                double divisor = max / 255;
                for(int i2 = 0; i2 < 3; i2++){
                    rgb[i2] /= divisor;
                }
            }
            float[] hsv = new float[3];
            Color.RGBToHSV((int)rgb[0], (int)rgb[1], (int)rgb[2], hsv);
            if(hsv[1] >= 0.2){
                if(hsv[0] >= 45 && hsv[0] <= 120){
                    returnList[index] = GAMEPIECE.POLLEN;
                }else if(hsv[0] <= 40 || hsv[0] >= 320){
                    returnList[index] = GAMEPIECE.NECTAR;
                }else if(hsv[0] >= 190 && hsv[0] <= 280) {
                    returnList[index] = GAMEPIECE.NECTAR;
                }else{
                    returnList[index] = GAMEPIECE.NONE;
                }
            }else{
                returnList[index] = GAMEPIECE.NONE;
            }

            index ++;
        }

        return returnList;
    }

    public String[] telemetryColors(){
        String[] returnString = new String[4];
        int index = 0;
        for(RevColorSensorV3 i : colorSensors){
            double[] rgb = {i.red(), i.green(), i.blue()};
            i.setGain(-15);
            double max = Math.max(rgb[0], Math.max(rgb[1], rgb[2]));
            if(max > 255){
                double divisor = max / 255;
                for(int i2 = 0; i2 < 3; i2++){
                    rgb[i2] /= divisor;
                }
            }
            float[] hsv = new float[3];
            Color.RGBToHSV((int)rgb[0], (int)rgb[1], (int)rgb[2], hsv);
            returnString[index] = (rgb[0] + ", " + rgb[1] + ", " + rgb[2] + ", " + hsv[0] + ", " + hsv[1] + ", " + hsv[2] + "END");
            index++;
        }

        return returnString;
    }
}



