package org.firstinspires.ftc.teamcode.Main.Autos;

import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.pedropathing.ivy.Scheduler.schedule;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;

import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.utils.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Config.ConfigTemp;
import org.firstinspires.ftc.teamcode.Config.Robot;
import org.firstinspires.ftc.teamcode.Pedropathing.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.FlowerIntake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShooterVelocityController;
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
        shooterLeft = new ShooterVelocityController(robot.shooterLeft, robot.voltageSensor, 29, 12,  1.85e-04, 0.1580, ConfigTemp.kP_LEFT, ConfigTemp.kI_LEFT, ConfigTemp.kD_LEFT);
        shooterRight = new ShooterVelocityController(robot.shooterRight, robot.voltageSensor, 29, 12,  1.85e-04, 0.1580, ConfigTemp.kP_RIGHT, ConfigTemp.kI_RIGHT, ConfigTemp.kD_RIGHT);
        outtakeLeft = new Outtake(robot, robot.servoBlockerL, robot.servoRampL, robot.servoHoodL);
        outtakeRight = new Outtake(robot, robot.servoBLockerR, robot.servoRampR, robot.servoHoodR);

        outtakeLeft.setHoodPosition(0.5);
        outtakeRight.setHoodPosition(0.5);

        outtakeLeft.setRampPosition(0.5);
        outtakeRight.setRampPosition(0.5);

        outtakeLeft.setBlockerPosition(ConfigTemp.blockerClosedL);
        outtakeRight.setBlockerPosition(ConfigTemp.blockerClosedR);

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
                    shooterLeft.setTargetRpm(ConfigTemp.POLLEN_RPML);
                    shooterRight.setTargetRpm(ConfigTemp.POLLEN_RPMR);
                    outtakeLeft.setHoodPosition(ConfigTemp.hoodPollenL);
                    outtakeRight.setHoodPosition(ConfigTemp.hoodPollenR);
                    outtakeLeft.setRampPosition(ConfigTemp.rampPollenL);
                    outtakeRight.setRampPosition(ConfigTemp.rampPollenR);
                }),

                waitMs(1000),

                instant(() -> {
                    transfer.setTransferPower(ConfigTemp.transferPower);
                    outtakeLeft.setBlockerPosition(ConfigTemp.blockerOpenL);
                }),

                waitMs(2000),

                instant(() -> {
                    outtakeLeft.setBlockerPosition(ConfigTemp.blockerClosedL);
                    outtakeRight.setBlockerPosition(ConfigTemp.blockerOpenR);
                }),

                waitMs(2000),

                instant(() -> {
                    transfer.setTransferPower(0);
                    outtakeRight.setBlockerPosition(ConfigTemp.blockerClosedR);
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