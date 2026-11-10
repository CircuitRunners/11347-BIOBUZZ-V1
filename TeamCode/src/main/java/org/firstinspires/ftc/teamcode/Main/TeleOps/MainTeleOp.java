package org.firstinspires.ftc.teamcode.Main.TeleOps;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Pedropathing.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Flower;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShootCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;

@Configurable
@TeleOp(name = "BIOBUZZ MainTeleOp", group = "Competition")
public class MainTeleOp extends LinearOpMode {
    @IgnoreConfigurable
    static TelemetryManager telemetryM;
    Drive drive = new Drive();
    Shooter shooter = new Shooter();
    Flower flower = new Flower();
    Transfer transfer = new Transfer();
    private Follower follower;

    @Override
    public void runOpMode() {

        waitForStart();

        follower = Constants.create(hardwareMap);
        follower.setPose(new Pose(0,0,0));

        while (opModeIsActive()) {
            if(ShootCommand.queueClear().getAsBoolean()){
                if(gamepad1.triangleWasPressed()){flower.flowerToggle();}

                if(gamepad1.right_trigger > 0.5){transfer.transferIntakeOn();}
                if(gamepad1.rightTriggerWasReleased()){transfer.transferStop();}
                if(gamepad1.right_bumper){transfer.transferIntakeSpit();}
                if(gamepad1.rightBumperWasReleased()){transfer.transferStop();}

                if(gamepad1.crossWasPressed()){shooter.shoot("burst");}
                if(gamepad1.squareWasPressed()){shooter.shoot("single");}
            }

            drive.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, follower);

            shooter.tick();
            ShootCommand.tick();
            follower.update();
        }
    }
}