package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Config.Config;
import org.firstinspires.ftc.teamcode.Config.Robot;

public class Shooter {
    private final Robot robot;
    private double targetPower;
    private double commandedPower;
    private final ElapsedTime overCurrentTimer = new ElapsedTime();
    public Shooter(Robot robot) {
        this.robot = robot;
    }

    public void setPower(double power) {
        targetPower = Range.clip(power, -Config.OUTTAKE_MAX_POWER, Config.OUTTAKE_MAX_POWER);
    }

    public void update() {
        commandedPower += Range.clip(targetPower - commandedPower, -Config.OUTTAKE_SLEW_PER_LOOP, Config.OUTTAKE_SLEW_PER_LOOP);
        robot.outtakeLeft.setPower(commandedPower);
        robot.outtakeRight.setPower(commandedPower);
    }

    public double getCurrentAmps() {
        return Math.max(robot.outtakeLeft.getCurrent(CurrentUnit.AMPS), robot.outtakeRight.getCurrent(CurrentUnit.AMPS));
    }

    public double getCommandedPower() {
        return commandedPower;
    }

    public double getTargetPower() {
        return targetPower;
    }

    public void stop() {
        setPower(0);
        robot.outtakeLeft.setVelocity(0);
        robot.outtakeRight.setVelocity(0);
    }
}
