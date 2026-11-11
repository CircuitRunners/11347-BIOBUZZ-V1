package org.firstinspires.ftc.teamcode.Subsystems;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuad;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Main.hardwareMappings;

public class Drive {
    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;
    public Drive(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotorEx.class, hardwareMappings.frontLeft);
        frontRight = hardwareMap.get(DcMotorEx.class, hardwareMappings.frontRight);
        backLeft = hardwareMap.get(DcMotorEx.class, hardwareMappings.backLeft);
        backRight = hardwareMap.get(DcMotorEx.class, hardwareMappings.backRight);
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
    }
    private void setPowers(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
        double maxSpeed = 1.0;
        maxSpeed = Math.max(maxSpeed, Math.abs(frontLeftPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(frontRightPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(backLeftPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(backRightPower));
        frontLeftPower /= maxSpeed;
        frontRightPower /= maxSpeed;
        backLeftPower /= maxSpeed;
        backRightPower /= maxSpeed;
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }
    public void drive(double forward, double strafe, double turn) {

        double frontLeftPower = (forward + strafe + turn);
        double backLeftPower = (forward - strafe + turn);
        double frontRightPower = (forward - strafe - turn);
        double backRightPower = (forward + strafe - turn);

        setPowers(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }

    public void drive(double forward, double strafe, double turn, Follower follower){

        double yaw = follower.localizer.pose().heading();
        double TargetX = strafe * Math.cos(-yaw) - (-forward) * Math.sin(-yaw);
        double TargetY = strafe * Math.sin(-yaw) + (-forward) * Math.cos(-yaw);

        double frontLeftPower = (TargetY + TargetX + turn);
        double backLeftPower = (TargetY - TargetX + turn);
        double frontRightPower = (TargetY - TargetX - turn);
        double backRightPower = (TargetY + TargetX - turn);

        setPowers(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }
}