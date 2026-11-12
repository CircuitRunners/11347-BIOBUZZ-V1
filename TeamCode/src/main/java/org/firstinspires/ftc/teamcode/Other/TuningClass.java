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
        Shooter shooter = new Shooter(hardwareMap);
        Transfer transfer = new Transfer(hardwareMap);
        waitForStart();


        while(opModeIsActive()){
            shooter.setTargetRPM(SIDE.LEFT, leftRPM);
            shooter.setTargetRPM(SIDE.RIGHT, rightRPM);

            if (gamepad1.left_bumper){transfer.transferStop();}
            if(gamepad1.right_bumper){transfer.transferTransferOn();}

            shooter.setLeftGains();
            shooter.setRightGains();
            shooter.tick();

            transfer.determineShootOrder();
            GAMEPIECE[] returnList = transfer.getShootOrder();

            transfer.updateColorTelemetry(telemetry);

            telemetryM.addData("Left Sensor 1", returnList[0]);
            telemetryM.addData("Left Sensor 2", returnList[1]);
            telemetryM.addLine("---------------------");
            telemetryM.addData("Right Sensor 1", returnList[2]);
            telemetryM.addData("Right Sensor 2", returnList[3]);
            telemetryM.addLine("---------------------");
            telemetryM.addLine("---------------------");
            telemetryM.addData("Left Target RPM", leftRPM);
            telemetryM.addData("Left Current RPM", shooter.getCurrentRPM(SIDE.LEFT));
            telemetryM.addLine("---------------------");
            telemetryM.addData("Right Target RPM", rightRPM);
            telemetryM.addData("Right Current RPM", shooter.getCurrentRPM(SIDE.RIGHT));
            telemetryM.update(telemetry);
        }
    }
}
