package org.firstinspires.ftc.teamcode.Main.TeleOps;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Flower;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Util.enums.GAMEPIECE;
import org.firstinspires.ftc.teamcode.Util.enums.SIDE;

import java.util.Arrays;
import java.util.List;

@Configurable
@TeleOp(name = "BIOBUZZ MainTeleOp", group = "Competition")
public class MainTeleOp extends LinearOpMode {
    @IgnoreConfigurable
    static TelemetryManager telemetryM;
    Drive drive;
    Shooter shooter;
    Flower flower;
    Transfer transfer;
    List<LynxModule> hubs;
    long lastTime = System.currentTimeMillis();
    //private Follower follower;

    @Override
    public void runOpMode() {
        drive = new Drive(hardwareMap);
        flower = new Flower(hardwareMap);
        transfer = new Transfer(hardwareMap);
        shooter = new Shooter(hardwareMap, transfer);

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.addLine("Initialized");
        telemetryM.update(telemetry);

        transfer.blockerState(SIDE.LEFT, false);
        transfer.blockerState(SIDE.RIGHT, false);

        hubs = hardwareMap.getAll(LynxModule.class);

        waitForStart();

        while (opModeIsActive()) {
            for (LynxModule hub : hubs) {hub.clearBulkCache();}

            if(shooter.queueClear().getAsBoolean()){
                if(gamepad1.triangleWasPressed()){flower.flowerToggle();}

                if(gamepad1.left_trigger > 0.2){transfer.transferIntakeOn();}
                else if (gamepad1.right_trigger > 0.2) {transfer.transferIntakeSpit();}
                else {transfer.transferStop();}

                if (gamepad1.crossWasPressed()){flower.flowerToggle();}

                if(gamepad1.leftBumperWasPressed()){shooter.shoot();}
            }

            drive.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

            shooter.tick();
            transfer.tick();

            telemetryM.addLine("---------------------");
            telemetryM.addData("Left Target RPM", shooter.getTargetRPM(SIDE.LEFT));
            telemetryM.addData("Left Current RPM", shooter.getCurrentRPM(SIDE.LEFT));
            telemetryM.addLine("---------------------");
            telemetryM.addData("Right Target RPM", shooter.getTargetRPM(SIDE.RIGHT));
            telemetryM.addData("Right Current RPM", shooter.getCurrentRPM(SIDE.RIGHT));
            telemetryM.addLine("---------------------");
            telemetryM.addData("Loop Time", System.currentTimeMillis() - lastTime + " ms");
            telemetryM.addData("System Time", System.currentTimeMillis());
            telemetryM.addData("Queue", shooter.telemetry());
            lastTime = System.currentTimeMillis();
            telemetryM.update(telemetry);

        }
    }
}