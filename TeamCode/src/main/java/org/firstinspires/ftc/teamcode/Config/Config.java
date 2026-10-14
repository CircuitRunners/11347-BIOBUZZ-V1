package org.firstinspires.ftc.teamcode.Config;

public class Config {

    //Hardware Map
    public static final String FL = "fl", FR = "fr", BL = "bl", BR = "br";
    public static final String shooterL = "sl", shooterR = "sr"; //shooter left, shooter right
    public static final String servo_rampL = "rl", servo_rampR = "rr"; //ramp Left, ramp Right
    public static final String servo_hoodL = "hl", servo_hoodR = "hr"; //hood Left, hood Right
    public static final String servo_blockerL = "bl", servo_blockerR = "br"; //blocker left, blocker right
    public static final String servo_flower = "flower";
    public static final String IMU = "imu", COLOR = "color";

    //Motor reverses
    public static final boolean REVERSE_FL = true, REVERSE_FR = false;
    public static final boolean REVERSE_BL = true, REVERSE_BR = false;
    public static final boolean REVERSE_TRANSFER_LEFT = true, REVERSE_TRANSFER_RIGHT = false;
    public static final boolean REVERSE_OUTTAKE_LEFT = false, REVERSE_OUTTAKE_RIGHT = true;
    public static final boolean REVERSE_AXON_1 = false, REVERSE_AXON_2 = false;
    public static final boolean REVERSE_AXON_3 = false, REVERSE_AXON_4 = false;
    public static final boolean REVERSE_SERVO_25_1 = false, REVERSE_SERVO_25_2 = false;
    public static final boolean REVERSE_SERVO_25_3 = false;

    //Servo positions
    public static final double flowerDown = 0.0, flowerUp = 0.0;
    public static final double OUTTAKE_MAX_POWER = 1.0;

    public static final double AUTO_PARK_X_IN = 24, AUTO_PARK_Y_IN = 36, AUTO_PARK_HEADING_DEG = 0;

    public static final boolean FENCE_ENABLED_DEFAULT = true;
    public static final double FIELD_MIN_IN = 0.0, FIELD_MAX_IN = 144.0;
    public static final double ROBOT_RADIUS_IN = 9.0;
    public static final double FENCE_SLOW_DIST_IN = 12.0;
    }