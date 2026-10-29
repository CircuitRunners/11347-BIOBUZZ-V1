package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Main.hardwareMappings;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShooterVelocityController;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShootCommand;

public class Shooter {
    private DcMotorEx leftShooterMotor;
    private DcMotorEx rightShooterMotor;
    private VoltageSensor voltageSensor;

    private ShooterVelocityController leftShooter;
    private ShooterVelocityController rightShooter;

    Transfer transfer = new Transfer();

    private double lkp = 0.0;
    private double lki = 0.0;
    private double lkd = 0.0;

    private double rkp = 0.0;
    private double rki = 0.0;
    private double rkd = 0.0;

    public Shooter(){
        leftShooterMotor = hardwareMap.get(DcMotorEx.class, hardwareMappings.leftShooterMotor);
        rightShooterMotor = hardwareMap.get(DcMotorEx.class, hardwareMappings.rightShooterMotor);

        voltageSensor = hardwareMap.voltageSensor.iterator().hasNext()
                ? hardwareMap.voltageSensor.iterator().next() : null;

        leftShooter = new ShooterVelocityController(leftShooterMotor, voltageSensor, 28, 12.6, 1.85e-04, 0.1580, lkp, lki, lkd);
        rightShooter = new ShooterVelocityController(rightShooterMotor, voltageSensor, 28, 12.6, 1.85e-04, 0.1580, rkp, rki, rkd);
    }

    public void shoot(String mode){
        ShootCommand command = transfer.determineShootOrder();
        command.shoot(mode);
    }
}
