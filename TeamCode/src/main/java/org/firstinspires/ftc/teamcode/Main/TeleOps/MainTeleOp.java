package org.firstinspires.ftc.teamcode.Main.TeleOps;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Configurable
@TeleOp(name = "BIOBUZZ MainTeleOp", group = "Competition")
public class MainTeleOp extends LinearOpMode {
    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    @Override
    public void runOpMode() {

        waitForStart();

        while (opModeIsActive()) {

        }
    }
}