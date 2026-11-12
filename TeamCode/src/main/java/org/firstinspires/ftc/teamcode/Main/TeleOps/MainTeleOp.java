package org.firstinspires.ftc.teamcode.Main.TeleOps;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
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
import org.firstinspires.ftc.teamcode.Util.enums.GAMEPIECE;
import org.firstinspires.ftc.teamcode.Util.enums.SIDE;

@Configurable
@TeleOp(name = "BIOBUZZ MainTeleOp", group = "Competition")
public class MainTeleOp extends LinearOpMode {
    @IgnoreConfigurable
    static TelemetryManager telemetryM;
    Drive drive;
    Shooter shooter;
    Flower flower;
    Transfer transfer;
    //private Follower follower;

    @Override
    public void runOpMode() {
        drive = new Drive(hardwareMap);
        shooter = new Shooter(hardwareMap);
        flower = new Flower(hardwareMap);
        transfer = new Transfer(hardwareMap);

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.addLine("Initialized");
        telemetryM.update(telemetry);
        waitForStart();

        //follower = Constants.create(hardwareMap);
        //follower.setPose(new Pose(0,0,0));

        while (opModeIsActive()) {
            if(ShootCommand.queueClear().getAsBoolean()){
                if(gamepad1.triangleWasPressed()){flower.flowerToggle();}

                if(gamepad1.left_trigger > 0.5){transfer.transferIntakeOn();}
                else if (gamepad1.right_trigger > 0.5) {transfer.transferIntakeSpit();}
                else {transfer.transferStop();}

                if (gamepad1.crossWasPressed()){flower.flowerToggle();}

                if(gamepad1.leftBumperWasPressed()){shooter.shoot("burst");}
                if(gamepad1.rightBumperWasPressed()){shooter.shoot("single");}
            }

            drive.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);//, follower);

            transfer.determineShootOrder();

            shooter.tick();
            ShootCommand.tick(telemetryM);
            //follower.update();

            GAMEPIECE[] returnList = transfer.getShootOrder();

            telemetryM.addData("Left Sensor 1", returnList[0]);
            telemetryM.addData("Left Sensor 2", returnList[1]);
            telemetryM.addLine("---------------------");
            telemetryM.addData("Right Sensor 1", returnList[2]);
            telemetryM.addData("Right Sensor 2", returnList[3]);
            telemetryM.addLine("---------------------");
            telemetryM.addLine("---------------------");
            telemetryM.addData("Left Target RPM", shooter.getTargetRPM(SIDE.LEFT));
            telemetryM.addData("Left Current RPM", shooter.getCurrentRPM(SIDE.LEFT));
            telemetryM.addLine("---------------------");
            telemetryM.addData("Right Target RPM", shooter.getTargetRPM(SIDE.RIGHT));
            telemetryM.addData("Right Current RPM", shooter.getCurrentRPM(SIDE.RIGHT));
            telemetryM.update(telemetry);

        }
    }
}