package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Main.hardwareMappings;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShooterVelocityController;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShootCommand;
import org.firstinspires.ftc.teamcode.Util.enums.GAMEPIECE;
import org.firstinspires.ftc.teamcode.Util.enums.SIDE;

public class Shooter {
    private DcMotorEx leftShooterMotor;
    private DcMotorEx rightShooterMotor;
    private VoltageSensor voltageSensor;

    private ShooterVelocityController leftShooter;
    private ShooterVelocityController rightShooter;

    private Servo leftCompression;
    private Servo rightCompression;

    Transfer transfer = new Transfer();

    private double lkp = 0.0;
    private double lki = 0.0;
    private double lkd = 0.0;

    private double rkp = 0.0;
    private double rki = 0.0;
    private double rkd = 0.0;

    private double pollenCompression = 0.0;
    private double nectarCompression = 0.0;

    private double pollenRPM = 0.0;
    private double nectarRPM = 0.0;
    private double primedRPM = 0.0;
    private double defaultRPM = 0.0;
    public Shooter(){
        leftShooterMotor = hardwareMap.get(DcMotorEx.class, hardwareMappings.leftShooterMotor);
        rightShooterMotor = hardwareMap.get(DcMotorEx.class, hardwareMappings.rightShooterMotor);

        voltageSensor = hardwareMap.voltageSensor.iterator().hasNext()
                ? hardwareMap.voltageSensor.iterator().next() : null;

        leftShooter = new ShooterVelocityController(leftShooterMotor, voltageSensor, 28, 12.6, 1.85e-04, 0.1580, lkp, lki, lkd);
        rightShooter = new ShooterVelocityController(rightShooterMotor, voltageSensor, 28, 12.6, 1.85e-04, 0.1580, rkp, rki, rkd);

        leftCompression = hardwareMap.get(Servo.class, hardwareMappings.leftCompression);
        rightCompression = hardwareMap.get(Servo.class, hardwareMappings.rightCompression);
    }

    public void tick(){
        leftShooter.update();
        rightShooter.update();
    }

    public void shoot(String mode){
        ShootCommand command = transfer.determineShootOrder();
        command.calculateOrder();
        command.shoot(mode);
    }

    public void setCompression(SIDE side, GAMEPIECE gamepiece){
        Servo target = side.equals(SIDE.LEFT) ? leftCompression : rightCompression;
        target.setPosition(gamepiece.equals(GAMEPIECE.NECTAR) ? nectarCompression : pollenCompression);
    }
    public void setTargetRPM(SIDE side, GAMEPIECE gamepiece) {
        ShooterVelocityController target = side.equals(SIDE.LEFT) ? leftShooter : rightShooter;
        target.setTargetRpm(gamepiece.equals(GAMEPIECE.NECTAR) ? nectarRPM : gamepiece.equals(GAMEPIECE.POLLEN) ? pollenRPM : primedRPM);
    }
    public void setTargetRPMPrimed(SIDE side) {
        ShooterVelocityController target = side.equals(SIDE.LEFT) ? leftShooter : rightShooter;
        target.setTargetRpm(primedRPM);
    }
    public void setTargetRPMDefault(SIDE side) {
        ShooterVelocityController target = side.equals(SIDE.LEFT) ? leftShooter : rightShooter;
        target.setTargetRpm(defaultRPM);
    }
}
