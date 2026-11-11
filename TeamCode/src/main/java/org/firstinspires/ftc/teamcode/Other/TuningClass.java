package org.firstinspires.ftc.teamcode.Other;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Util.enums.SIDE;


@Configurable
public class TuningClass extends LinearOpMode {
    public static double rpm = 1000;

    @Override
    public void runOpMode(){
        waitForStart();

        Shooter shooter = new Shooter(hardwareMap);
        while(opModeIsActive()){
            shooter.setTargetRPM(SIDE.LEFT, rpm);
        }
    }
}
