package org.firstinspires.ftc.teamcode.Autos;

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

import org.firstinspires.ftc.teamcode.Config.Config;
import org.firstinspires.ftc.teamcode.Config.Robot;
import org.firstinspires.ftc.teamcode.Pedropathing.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.FlowerIntake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
@Configurable
@Autonomous(name = "BIOBUZZ Auto - PARK", group = "Competition")
public class ParkAuto extends LinearOpMode {
    private Follower follower;
    private final Robot robot = new Robot();
    private PathsParkAuto paths = new PathsParkAuto();
    private FlowerIntake flowerIntake;
    private Transfer transfer;
    private Shooter shooterLeft;
    private Shooter shooterRight;
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
        shooterLeft = new Shooter(robot, robot.shooterLeft, Config.kP_LEFT, Config.kI_LEFT, Config.kD_LEFT, Config.kF_LEFT);
        shooterRight = new Shooter(robot, robot.shooterRight, Config.kP_RIGHT, Config.kI_RIGHT, Config.kD_RIGHT, Config.kF_RIGHT);
        outtakeLeft = new Outtake(robot, robot.servoBlockerL, robot.servoRampL, robot.servoHoodL);
        outtakeRight = new Outtake(robot, robot.servoBLockerR, robot.servoRampR, robot.servoHoodR);

        outtakeLeft.setHoodPosition(0.5);
        outtakeRight.setHoodPosition(0.5);

        outtakeLeft.setRampPosition(0.5);
        outtakeRight.setRampPosition(0.5);

        outtakeLeft.setBlockerPosition(Config.blockerOpenL);
        outtakeRight.setBlockerPosition(Config.blockerOpenR);

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
                follow(follower, paths.path1()),

                shoot(),

                follow(follower, paths.path2()),
                follow(follower, paths.path3())
        );
    }

    private Command shoot(){
        return sequential(
                instant(() -> {
                    shooterRight.setTargetRPM(Config.POLLEN_RPMR);
                    outtakeRight.setHoodPosition(Config.hoodPollenR);
                    outtakeRight.setRampPosition(Config.rampPollenR);
                    outtakeRight.setBlockerPosition(Config.blockerOpenR);
                }),

                waitMs(1000),

                instant(() -> {
                    transfer.setTransferPower(Config.transferPower);
                }),

                waitMs(3500),

                instant(() -> {
                    shooterRight.setTargetRPM(Config.POLLEN_RPMR/3);
                })
        );
    }



}
