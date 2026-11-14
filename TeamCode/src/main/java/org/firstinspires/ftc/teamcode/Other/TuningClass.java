package org.firstinspires.ftc.teamcode.Other;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Util.enums.GAMEPIECE;
import org.firstinspires.ftc.teamcode.Util.enums.SIDE;


@Configurable
@TeleOp(name = "Tuning Class", group = "Tuning")

public class TuningClass extends LinearOpMode {
    public static double leftRPM = 0;
    public static double rightRPM = 0;
    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    @Override
    public void runOpMode(){
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.addLine("Testing");
        telemetryM.update(telemetry);
        Shooter shooter = new Shooter(hardwareMap, null);
        Transfer transfer = new Transfer(hardwareMap);
        waitForStart();


        while(opModeIsActive()){
            shooter.setAnyTargetRPM(SIDE.LEFT, leftRPM);
            shooter.setAnyTargetRPM(SIDE.RIGHT, rightRPM);

            if (gamepad1.left_bumper){transfer.transferStop();}
            if(gamepad1.right_bumper){transfer.transferTransferOn();}

            shooter.setShooterGains(SIDE.LEFT);
            shooter.setShooterGains(SIDE.RIGHT);

            shooter.setCompression(SIDE.LEFT, GAMEPIECE.POLLEN);
            shooter.setCompression(SIDE.RIGHT, GAMEPIECE.NECTAR);
            shooter.tick();

            transfer.determineShootOrder();

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
