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
import org.firstinspires.ftc.teamcode.Subsystems.Flower;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShootCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShooterVelocityController;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
@Configurable
@Autonomous(name = "BIOBUZZ Blue Side AUto", group = "Competition")
public class BlueSideAuto extends LinearOpMode {
    private Follower follower;
    private BlueSidePaths paths = new BlueSidePaths();
    private Timer pathTimer;
    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    @Override
    public void runOpMode() {
        pathTimer = new Timer();

        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(paths.getStart());
        follower.update();

        waitForStart();

        pathTimer.reset();
        schedule(autoRoutine());

        while (opModeIsActive()) {

            follower.update();
            Scheduler.execute();
            ShootCommand.tick();


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