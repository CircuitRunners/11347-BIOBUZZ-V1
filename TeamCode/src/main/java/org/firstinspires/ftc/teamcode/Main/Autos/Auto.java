package org.firstinspires.ftc.teamcode.Main.Autos;


import static com.pedropathing.ivy.Scheduler.schedule;

import androidx.core.math.MathUtils;

import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.utils.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Main.Autos.Subclasses.AutoSelector;
import org.firstinspires.ftc.teamcode.Pedropathing.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShootCommand;

@Autonomous(name = "Autonomous", group = "Competition")
public class Auto extends LinearOpMode {

    AutoSelector selector;
    private Timer pathTimer;
    private Follower follower;
    private Pose startPos;
    private Command autoRoutine;

    @Override
    public void runOpMode(){
        pathTimer = new Timer();
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.update();

        selector = new AutoSelector(follower);
        AutoSelector.Paths[] paths = selector.getPaths();

        int index = 0;
        while(opModeInInit()){
            if(gamepad1.dpadLeftWasPressed()){
                index -= 1;
            }
            if(gamepad1.dpadRightWasPressed()){
                index += 1;
            }
            MathUtils.clamp(index, 0, paths.length - 1);

            if(gamepad1.dpadUpWasPressed()){
                startPos = (Pose) selector.getPath(paths[index])[0];
                autoRoutine = (Command) selector.getPath(paths[index])[1];
                break;
            }

            telemetry.addLine(paths[index].toString());
            telemetry.addLine("> " + selector.getDescription(paths[index]));
            telemetry.update();
        }

        follower.setPose(startPos);
        follower.update();

        waitForStart();

        pathTimer.reset();
        schedule(autoRoutine);

        while(opModeIsActive()){
            follower.update();
            Scheduler.execute();

            ShootCommand.tick();
            selector.getShooter().tick();
        }
    }
}
