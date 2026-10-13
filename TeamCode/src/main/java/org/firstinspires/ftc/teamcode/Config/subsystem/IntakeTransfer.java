package org.firstinspires.ftc.teamcode.Config.subsystem;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeTransfer {
    public static double transferSpeed = 0.9;

    public DcMotorEx transferMotor1;
    public DcMotorEx transferMotor2;

    public void init(HardwareMap hardwareMap) {
        transferMotor1 = hardwareMap.get(DcMotorEx.class, "TM1");
        transferMotor2 = hardwareMap.get(DcMotorEx.class, "TM2");

    }
    //two motors -> make functions to make it spin

    //to make it spin, stop function another function to change transfer speed
    public void runTransfer () {
        transferMotor1.setPower(transferSpeed);
        transferMotor2.setPower(transferSpeed);
    }

    public void stopTransfer () {
        transferMotor1.setPower(0);
        transferMotor2.setPower(0);
    }

    public void setSpeed (double speed) {
        transferSpeed = speed;
    }
}




