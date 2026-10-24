package org.firstinspires.ftc.teamcode.Autos;

import static com.pedropathing.api.Paths.*;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.pedropathing.ivy.Scheduler.schedule;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.utils.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Config.Config;
import org.firstinspires.ftc.teamcode.Config.Robot;
import org.firstinspires.ftc.teamcode.Pedropathing.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.FlowerIntake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterVelocityController;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
@Configurable
@Autonomous(name = "BIOBUZZ Blue Side AUto", group = "Competition")
public class BlueSideAuto extends LinearOpMode {
    private Follower follower;
    private final Robot robot = new Robot();
    private BlueSidePaths paths = new BlueSidePaths();
    private FlowerIntake flowerIntake;
    private Transfer transfer;
    private ShooterVelocityController shooterLeft;
    private ShooterVelocityController shooterRight;
    private Outtake outtakeLeft;
    private Outtake outtakeRight;
    private Timer pathTimer;
    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        flowerIntake = new FlowerIntake(robot);
        transfer = new Transfer(robot);
        shooterLeft = new ShooterVelocityController(robot.shooterLeft, robot.voltageSensor, 29, 12,  1.85e-04, 0.1580, Config.kP_LEFT, Config.kI_LEFT, Config.kD_LEFT);
        shooterRight = new ShooterVelocityController(robot.shooterRight, robot.voltageSensor, 29, 12,  1.85e-04, 0.1580, Config.kP_RIGHT, Config.kI_RIGHT, Config.kD_RIGHT);
        outtakeLeft = new Outtake(robot, robot.servoBlockerL, robot.servoRampL, robot.servoHoodL);
        outtakeRight = new Outtake(robot, robot.servoBLockerR, robot.servoRampR, robot.servoHoodR);

        outtakeLeft.setHoodPosition(0.5);
        outtakeRight.setHoodPosition(0.5);

        outtakeLeft.setRampPosition(0.5);
        outtakeRight.setRampPosition(0.5);

        outtakeLeft.setBlockerPosition(Config.blockerClosedL);
        outtakeRight.setBlockerPosition(Config.blockerClosedR);

        pathTimer = new Timer();

        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(paths.getStart());
        follower.update();

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.addLine("INITIALIZED");
        telemetryM.update(telemetry);

        waitForStart();
        pathTimer.reset();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            robot.clearBulkCache();

            follower.update();
            Scheduler.execute();

            shooterLeft.update();
            shooterRight.update();


            telemetryM.update(telemetry);
        }
    }

    private Command autoRoutine() {
        return sequential(
                instant(() -> {
                    shooterLeft.setTargetRpm(Config.POLLEN_RPML);
                    shooterRight.setTargetRpm(Config.POLLEN_RPMR);
                    outtakeLeft.setHoodPosition(Config.hoodPollenL);
                    outtakeRight.setHoodPosition(Config.hoodPollenR);
                    outtakeLeft.setRampPosition(Config.rampPollenL);
                    outtakeRight.setRampPosition(Config.rampPollenR);
                }),

                waitMs(1000),

                instant(() -> {
                    transfer.setTransferPower(Config.transferPower);
                    outtakeLeft.setBlockerPosition(Config.blockerOpenL);
                }),

                waitMs(2000),

                instant(() -> {
                    outtakeLeft.setBlockerPosition(Config.blockerClosedL);
                    outtakeRight.setBlockerPosition(Config.blockerOpenR);
                }),

                waitMs(2000),

                instant(() -> {
                    transfer.setTransferPower(0);
                    outtakeRight.setBlockerPosition(Config.blockerClosedR);
                }),

                follow(follower, paths.path1()),

                instant(() -> {
                    transfer.transferOn();
                }),

                follow(follower, paths.path2()),

                instant(() -> {
                    transfer.transferStop();
                }),

                follow(follower, paths.path3()),
                follow(follower, paths.path4()),
                follow(follower, paths.path5()),
                follow(follower, paths.path6()),
                follow(follower, paths.path7()),
                follow(follower, paths.path8())
                // Add mechanism commands here.
                //follow(follower, )
        );
    }


}