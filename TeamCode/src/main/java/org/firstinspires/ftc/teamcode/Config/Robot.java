package org.firstinspires.ftc.teamcode.Config;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.List;

public class Robot {
    public DcMotorEx frontLeft, frontRight, backLeft, backRight;
    public DcMotorEx shooterLeft, shooterRight;
    public DcMotorEx transferLeft, transferRight;
    public Servo servoBlockerL, servoBLockerR;
    public Servo servoRampL, servoRampR;
    public Servo servoHoodL, servoHoodR;
    public Servo servoFlower;
    public IMU imu;
    public ColorSensor color;
    public List<LynxModule> hubs;

    public final Servo[] servos = new Servo[7];

    public void init(HardwareMap hardwareMap) {
        hubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        frontLeft = required(hardwareMap, DcMotorEx.class, Config.FL);
        frontRight = required(hardwareMap, DcMotorEx.class, Config.FR);
        backLeft = required(hardwareMap, DcMotorEx.class, Config.BL);
        backRight = required(hardwareMap, DcMotorEx.class, Config.BR);

        transferLeft = required(hardwareMap, DcMotorEx.class, Config.transferL);
        transferRight = required(hardwareMap, DcMotorEx.class, Config.transferR);

        shooterLeft = required(hardwareMap, DcMotorEx.class, Config.shooterL);
        shooterRight = required(hardwareMap, DcMotorEx.class, Config.shooterR);

        servoBlockerL = required(hardwareMap, Servo.class, Config.servoBlockerL);
        servoBLockerR = required(hardwareMap, Servo.class, Config.servoBlockerR);

        servoRampL = required(hardwareMap, Servo.class, Config.servoRampL);
        servoRampR = required(hardwareMap, Servo.class, Config.servoRampR);

        servoHoodL = required(hardwareMap, Servo.class, Config.servoHoodL);
        servoHoodR = required(hardwareMap, Servo.class, Config.servoHoodR);

        servoFlower = required(hardwareMap, Servo.class, Config.servoFlower);

        //imu = required(hardwareMap, IMU.class, Config.IMU);
        //color = required(hardwareMap, ColorSensor.class, Config.COLOR);

        frontLeft.setDirection(Config.REVERSE_FL ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD);
        frontRight.setDirection(Config.REVERSE_FR ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD);
        backLeft.setDirection(Config.REVERSE_BL ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD);
        backRight.setDirection(Config.REVERSE_BR ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD);

        shooterLeft.setDirection(Config.REVERSE_OUTTAKE_LEFT ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD);
        shooterLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooterLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        shooterRight.setDirection(Config.REVERSE_OUTTAKE_RIGHT ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD);
        shooterRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooterRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        transferLeft.setDirection(Config.REVERSE_TRANSFER_LEFT ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD);
        transferRight.setDirection(Config.REVERSE_TRANSFER_RIGHT ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD);

        configureMotor(frontLeft, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        configureMotor(frontRight, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        configureMotor(backLeft, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        configureMotor(backRight, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        configureMotor(transferLeft, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        configureMotor(transferRight, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

//        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
//                RevHubOrientationOnRobot.LogoFacingDirection.UP,
//                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
//        )));
//        imu.resetYaw();
//        setServoPositions(Config.HOME);
//        setBlockerPositions(Config.BLOCKER_BLOCK_POS);
        clearBulkCache();
    }

    public void clearBulkCache() {
        if (hubs != null) {
            for (LynxModule hub : hubs) {
                hub.clearBulkCache();
            }
        }
    }

    private void configureMotor(DcMotorEx motor, DcMotor.RunMode mode) {
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(mode);
        motor.setPower(0);
    }

    public double getHeadingRadians() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    public void resetHeading() {
        imu.resetYaw();
    }

    public void stopAll() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        shooterLeft.setPower(0);
        shooterRight.setPower(0);
        transferLeft.setPower(0);
        transferRight.setPower(0);
    }

    private <T> T required(HardwareMap hardwareMap, Class<? extends T> type, String name) {
        try {
            return hardwareMap.get(type, name);
        } catch (Exception e) {
            throw new IllegalStateException("Missing hardware: " + name, e);
        }
    }
}
