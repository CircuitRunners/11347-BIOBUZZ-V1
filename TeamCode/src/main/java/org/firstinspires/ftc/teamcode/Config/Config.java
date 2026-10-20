package org.firstinspires.ftc.teamcode.Config;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class Config {

    //Hardware Map
    public static final String FL = "fl", FR = "fr", BL = "bl", BR = "br";
    public static final String transferL = "tl", transferR = "tr";
    public static final String shooterL = "sl", shooterR = "sr"; //shooter left, shooter right
    public static final String servoRampL = "rl", servoRampR = "rr"; //ramp Left, ramp Right
    public static final String servoHoodL = "hl", servoHoodR = "hr"; //hood Left, hood Right
    public static final String servoBlockerL = "blockl", servoBlockerR = "blockr"; //blocker left, blocker right
    public static final String servoFlower = "flower";
    //public static final String IMU = "imu", COLOR = "color";

    //Motor reverses
    public static boolean REVERSE_FL = false, REVERSE_FR = false;
    public static boolean REVERSE_BL = true, REVERSE_BR = false;
    public static boolean REVERSE_TRANSFER_LEFT = true, REVERSE_TRANSFER_RIGHT = false;
    public static boolean REVERSE_OUTTAKE_LEFT = false, REVERSE_OUTTAKE_RIGHT = true; //Finalized, DO NOT TOUCH

    //Flower Intake
    public static double flowerDown = 0.0, flowerUp = 0.55;

    //Outtake
    public static double blockerOpenL = 0.8, blockerOpenR = 0.8;
    public static double blockerClosedL = 0.6, blockerClosedR = 0.6;
    public static double rampPollenL = 0.225, rampPollenR = 0.22; //here
    public static double rampNectarL = 0.5, rampNectarR = 0.5;
    public static double hoodPollenL = 0.91, hoodPollenR = 0.5;
    public static double hoodNectarL = 0.5, hoodNectarR = 0.5;

    public static double hoodMinAngleL = 0.6, hoodMinAngleR = 0.3;
    public static double hoodMaxAngleL = 0.91, hoodMaxAngleR = 0.95;
    public static double ANGLE_INCREASE_RATE = 0.001;

    //Transfer Constants
    public static double intakePower = 0.9, spitPower = -0.9;

    //Shooter Constants
    public static double POLLEN_RPM = 2900;
    public static double NECTAR_RPM = 3000;
    public static double GEAR_RATIO = 1.0;
    public static double TICKS_PER_REV = 28;
    public static double THRESHOLD = 200;

    public static double kP_LEFT = 30.0;
    public static double kI_LEFT = 0.0;
    public static double kD_LEFT = 1.0;
    public static double kF_LEFT = 18.0;

    public static double kP_RIGHT = 30.0;
    public static double kI_RIGHT = 0.0;
    public static double kD_RIGHT = 1.0;
    public static double kF_RIGHT = 18.5;

    //----------------------

    public static final double AUTO_PARK_X_IN = 24, AUTO_PARK_Y_IN = 36, AUTO_PARK_HEADING_DEG = 0;

    public static final boolean FENCE_ENABLED_DEFAULT = true;
    public static final double FIELD_MIN_IN = 0.0, FIELD_MAX_IN = 144.0;
    public static final double ROBOT_RADIUS_IN = 9.0;
    public static final double FENCE_SLOW_DIST_IN = 12.0;
    }