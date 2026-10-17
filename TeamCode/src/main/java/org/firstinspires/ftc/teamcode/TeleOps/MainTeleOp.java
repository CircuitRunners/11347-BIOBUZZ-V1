package org.firstinspires.ftc.teamcode.TeleOps;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
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

import com.qualcomm.robotcore.util.ElapsedTime;

@Configurable
@TeleOp(name = "BIOBUZZ MainTeleOp", group = "Competition")
public class MainTeleOp extends LinearOpMode {
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
    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        drive = new Drive(robot);
        flowerIntake = new FlowerIntake(robot);
        transfer = new Transfer(robot);
        shooterLeft = new Shooter(robot, robot.shooterLeft, Config.kP_LEFT, Config.kI_LEFT, Config.kD_LEFT, Config.kF_LEFT);
        shooterRight = new Shooter(robot, robot.shooterRight, Config.kP_RIGHT, Config.kI_RIGHT, Config.kD_RIGHT, Config.kF_RIGHT);
        outtakeLeft = new Outtake(robot, robot.servoBlockerL, robot.servoRampL, robot.servoHoodL);
        outtakeRight = new Outtake(robot, robot.servoBLockerR, robot.servoRampR, robot.servoHoodR);

        outtakeLeft.setHoodPosition(0.5);
        outtakeRight.setHoodPosition(0.5);


        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.addLine("INITIALIZED");
        telemetryM.update(telemetry);

        waitForStart();

        while (opModeIsActive()) {
            shooterLeft.update();
            shooterRight.update();

            GamePadControls();

            //Telemetry
            telemetryM.addLine("TEST");
            telemetryM.addLine("==============================");
            telemetryM.addLine("Outtake Telemetry");
            telemetryM.addData("Servo Ramp Position LEFT", outtakeLeft.getRampPosition());
            telemetryM.addData("Servo Ramp Position RIGHT", outtakeRight.getRampPosition());
            telemetryM.addData("Servo Hood Position LEFT", outtakeLeft.getHoodPosition());
            telemetryM.addData("Servo Hood Position RIGHT", outtakeRight.getHoodPosition());
            telemetryM.addData("Blocker Position LEFT", outtakeLeft.getBlockerPosition());
            telemetryM.addData("Blocker Position RIGHT", outtakeRight.getBlockerPosition());
            telemetryM.addLine("==============================");
            telemetryM.addLine("Shooter Telemetry");
            telemetryM.addData("Shooter Target RPM LEFT", shooterLeft.getTargetRPM());
            telemetryM.addData("Shooter Target RPM RIGHT", shooterRight.getTargetRPM());
            telemetryM.addData("Shooter Velocity LEFT", shooterLeft.getShooterVelocity());
            telemetryM.addData("Shooter Velocity RIGHT", shooterRight.getShooterVelocity());
            telemetryM.update(telemetry);
        }
    }
    private void GamePadControls() {
        if (gamepad1.left_bumper || gamepad1.right_bumper || gamepad1.left_trigger > 0.1) {
            transfer.transferOn();
        }
        else if (gamepad1.right_trigger > 0.1) {
            transfer.transferSpit();
        }
        else {
            transfer.transferStop();
        }

        if (gamepad1.left_bumper) {
            outtakeLeft.setBlockerPosition(Config.blockerOpenL);
            //need to determine Shooting Algorithm - Jonathan. Code below is temporary.
            if (isPollenL) {
                outtakeLeft.setRampPosition(Config.rampPollenL);
                outtakeLeft.setHoodPosition(Config.hoodPollenL);
            }
            else {
                outtakeLeft.setRampPosition(Config.rampNectarL);
                outtakeLeft. setHoodPosition(Config.hoodNectarL);
            }
        }
        else {
            outtakeLeft.setBlockerPosition(Config.blockerClosedL);
            outtakeLeft.setRampPosition(0.5); //temp
            outtakeLeft.setHoodPosition(0.5); //temp
        }

        if (gamepad1.right_bumper) {
            outtakeRight.setBlockerPosition(Config.blockerOpenR);
            //need to determine Shooting Algorithm - Jonathan. Code below is temporary
            if (isPollenR) {
                outtakeRight.setRampPosition(Config.rampPollenR);
                outtakeRight.setHoodPosition(Config.hoodPollenR);
            }
            else {
                outtakeRight.setRampPosition(Config.rampNectarR);
                outtakeRight.setHoodPosition(Config.hoodNectarR);
            }
        }
        else {
            outtakeRight.setBlockerPosition(Config.blockerClosedR);
//            outtakeRight.setRampPosition(0.5); //temp
//            outtakeRight.setHoodPosition(0.5); //temp
        }

        if (gamepad2.left_stick_y > 0.1) {
            double servo_pos = outtakeLeft.getHoodPosition();
            if (servo_pos < Config.hoodMaxAngleL) {
                outtakeLeft.setHoodPosition(servo_pos + Config.ANGLE_INCREASE_RATE);
            }
        }
        else if (gamepad2.left_stick_y < -0.1) {
            double servo_pos = outtakeLeft.getHoodPosition();
            if (servo_pos > Config.hoodMinAngleL) {
                outtakeLeft.setHoodPosition(servo_pos - Config.ANGLE_INCREASE_RATE);
            }
        }

        if (gamepad2.right_stick_y > 0.1) {
            double servo_pos = outtakeRight.getHoodPosition();
            if (servo_pos < Config.hoodMaxAngleR) {
                outtakeRight.setHoodPosition(servo_pos + Config.ANGLE_INCREASE_RATE);
            }
        }
        else if (gamepad2.right_stick_y < 0.1) {
            double servo_pos = outtakeRight.getHoodPosition();
            if (servo_pos > Config.hoodMinAngleR) {
                outtakeRight.setHoodPosition(servo_pos - Config.ANGLE_INCREASE_RATE);
            }
        }

//        if (gamepad1.b) { //CIRCLE
//
//        }

        if (gamepad1.x) { //SQUARE
            if (flowerDown) {
                flowerIntake.setFlowerUp();
            }
            else {
                flowerIntake.setFlowerDown();
            }
        }
        if (gamepad1.dpadLeftWasPressed()) {
            if (!shooterOnL) {
                shooterOnL = true;
                shooterLeft.setTargetRPM(Config.POLLEN_RPM);
            }
            else {
                shooterLeft.setTargetRPM(0);
                shooterOnL = false;
            }
        }

        if (gamepad1.dpadRightWasPressed()) {
            if (!shooterOnR) {
                shooterOnR = true;
                shooterRight.setTargetRPM(Config.POLLEN_RPM);
            }
            else {
                shooterRight.setTargetRPM(0);
                shooterOnR = false;
            }
        }

        double driveY = -gamepad1.left_stick_y;
        double driveX = gamepad1.left_stick_x;
        double driveRotation = gamepad1.right_stick_x;

        drive.drive(driveY, driveX, driveRotation);
    }
}




//left trigger: intake/transfer
//right trigger: spit ball
//
//left bumper: turn on and off shooter, move left blocker and turn on transfer/intake motors (hold to do stuff)
//right bumper: turn on and off shooter, move right blocker and turn on transfer/intake motors (hold to do stuff)
//dpad up and down: adjust hood
//
//square: move up and down the flower intake thingy
//circle: reset localization
//left and right joystick: robot movement