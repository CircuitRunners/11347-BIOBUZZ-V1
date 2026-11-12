package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Color;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Main.hardwareMappings;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShootCommand;
import org.firstinspires.ftc.teamcode.Util.enums.GAMEPIECE;
import org.firstinspires.ftc.teamcode.Util.enums.SIDE;
import org.jetbrains.annotations.UnknownNullability;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

@Configurable
public class Transfer {
    private NormalizedColorSensor leftColorSensorO; //One
    private NormalizedColorSensor leftColorSensorT; // Two
    private NormalizedColorSensor rightColorSensorO; // One
    private NormalizedColorSensor rightColorSensorT; // Two

    private DcMotorEx transferLeft;
    private DcMotorEx transferRight;

    private Servo blockerLeft;
    private Servo blockerRight;

    public static double blockerClosed = 0.5;
    public static double blockerOpen = 0.5;

    public static double intakeSpeed = 1.0;
    public static double transferSpeed = 0.5;
    public static double spitSpeed = -0.5;
    private HardwareMap hardwareMap;
    private GAMEPIECE[] returnList = new GAMEPIECE[]{GAMEPIECE.NONE, GAMEPIECE.NONE, GAMEPIECE.NONE, GAMEPIECE.NONE};;

    NormalizedColorSensor[] colorSensors;

    public Transfer(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        leftColorSensorO = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorLeftOne);
        leftColorSensorT = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorLeftTwo);
        rightColorSensorO = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorRightOne);
        rightColorSensorT = hardwareMap.get(NormalizedColorSensor.class, hardwareMappings.colorSensorRightTwo);

        colorSensors = new NormalizedColorSensor[]{leftColorSensorO, leftColorSensorT, rightColorSensorO, rightColorSensorT};

        transferLeft = hardwareMap.get(DcMotorEx.class, hardwareMappings.transferLeft);
        transferRight = hardwareMap.get(DcMotorEx.class, hardwareMappings.transferRight);

        //transferLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        blockerLeft = hardwareMap.get(Servo.class, hardwareMappings.blockerLeft);
        blockerRight = hardwareMap.get(Servo.class, hardwareMappings.blockerRight);
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
        Servo target = side.equals(SIDE.LEFT) ? blockerLeft : blockerRight;
        target.setPosition(on ? blockerOpen : blockerClosed);
    }
    public void transferStop() {
        setTransferPower(0.0);
    }
    public ShootCommand determineShootOrder(Shooter shooter, Transfer transfer){
        int index = 0;
        for(NormalizedColorSensor i : colorSensors){
            i.setGain(15);
            NormalizedRGBA color = i.getNormalizedColors();
            float[] hsv = new float[3];
            Color.RGBToHSV((int)(color.red * 255), (int)(color.green * 255), (int)(color.blue * 255), hsv);

            if(hsv[1] >= 0.2){
                if(hsv[0] >= 85 && hsv[0] <= 98){
                    returnList[index] = GAMEPIECE.POLLEN;
                }else
                if(hsv[0] >= 20 && hsv[0] <= 30){
                    returnList[index] = GAMEPIECE.NECTAR;
                }else
                if(hsv[0] >= 210 && hsv[0] <= 230) {
                    returnList[index] = GAMEPIECE.NECTAR;
                }else{
                    returnList[index] = GAMEPIECE.NONE;
                }
            }else{
                returnList[index] = GAMEPIECE.NONE;
            }

            index ++;
        }

        return new ShootCommand(returnList, shooter, this);
    }

    // yea yea ik this is AI -> womp womp
//    public void updateColorTelemetry(@UnknownNullability Telemetry telemetry) {
//
//        telemetry.addLine("========== COLOR SENSORS ==========");
//
//        for (int index = 0; index < colorSensors.length; index++) {
//
//            NormalizedRGBA color = colorSensors[index].getNormalizedColors();
//            colorSensors[index].setGain(15);
//
//            float[] hsv = new float[3];
//
//            Color.RGBToHSV(
//                    (int)(color.red * 255),
//                    (int)(color.green * 255),
//                    (int)(color.blue * 255),
//                    hsv
//            );
//
//            telemetry.addData("Sensor " + index + " Red", color.red);
//            telemetry.addData("Sensor " + index + " Green", color.green);
//            telemetry.addData("Sensor " + index + " Blue", color.blue);
//
//            telemetry.addData("Sensor " + index + " Hue", hsv[0]);
//            telemetry.addData("Sensor " + index + " Saturation", hsv[1]);
//            telemetry.addData("Sensor " + index + " Value", hsv[2]);
//
//            telemetry.addData("Sensor " + index + " Detection",
//                    returnList == null ? "Not scanned" : returnList[index]);
//        }
//    }
    public GAMEPIECE[] getShootOrder() {
        return returnList;
    }
}



