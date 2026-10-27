package org.firstinspires.ftc.teamcode.Test;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Config.Config;
import org.firstinspires.ftc.teamcode.Config.Robot;

import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.FlowerIntake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.opencv.core.Scalar;

import java.util.ArrayList;
import java.util.Map;

@Configurable
@TeleOp(name = "fsfsfsfsfp", group = "Competition")
public class InitTest extends LinearOpMode {
    private final Robot robot = new Robot();
    private Drive drive;
    private FlowerIntake flowerIntake;
    private Transfer transfer;
    private Shooter shooterLeft;
    private Shooter shooterRight;
    private Outtake outtakeLeft;
    private Outtake outtakeRight;
    private boolean flowerDown = false;
    public static boolean isPollenL = true; //temp
    public static boolean isPollenR = true; //temp
    private boolean shooterOnL = false;
    private boolean shooterOnR = false;

    private ArrayList<Map<String, Scalar>> testSnap = new ArrayList<>();
    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        drive = new Drive(robot);
        flowerIntake = new FlowerIntake(robot);
        //transfer = new Transfer(robot);
        shooterLeft = new Shooter(robot, robot.shooterLeft, Config.kP_LEFT, Config.kI_LEFT, Config.kD_LEFT, Config.kF_LEFT);
        shooterRight = new Shooter(robot, robot.shooterRight, Config.kP_RIGHT, Config.kI_RIGHT, Config.kD_RIGHT, Config.kF_RIGHT);
        outtakeLeft = new Outtake(robot, robot.servoBlockerL, robot.servoRampL, robot.servoHoodL);
        outtakeRight = new Outtake(robot, robot.servoBLockerR, robot.servoRampR, robot.servoHoodR);
        waitForStart();

        while (opModeIsActive()) {
        }
    }
}