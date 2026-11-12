package org.firstinspires.ftc.teamcode.Subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Main.hardwareMappings;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShooterVelocityController;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShootCommand;
import org.firstinspires.ftc.teamcode.Util.enums.GAMEPIECE;
import org.firstinspires.ftc.teamcode.Util.enums.SIDE;

@Configurable
public class Shooter {
    private DcMotorEx leftShooterMotor;
    private DcMotorEx rightShooterMotor;
    private VoltageSensor voltageSensor;

    private ShooterVelocityController leftShooter;
    private ShooterVelocityController rightShooter;

    private Servo leftCompression;
    private Servo rightCompression;

    Transfer transfer;

    public static double lkp = 0.5;
    public static double lki = 0.0;
    public static double lkd = 0.001;

    public static double rkp = 0.001;
    public static double rki = 0.0;
    public static double rkd = 0.5;

    public static double pollenCompression = 0.0;
    public static double nectarCompression = 0.0;

    public static double pollenRPM = 2400.0;
    public static double nectarRPM = 2800.0;
    public static double primedRPM = 1500.0;
    public static double defaultRPM = 0.0;
    private static final double GEAR_RATIO = 1.5;
    public Shooter(HardwareMap hardwareMap){
        leftShooterMotor = hardwareMap.get(DcMotorEx.class, hardwareMappings.leftShooterMotor);
        rightShooterMotor = hardwareMap.get(DcMotorEx.class, hardwareMappings.rightShooterMotor);
        rightShooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        voltageSensor = hardwareMap.voltageSensor.iterator().hasNext()
                ? hardwareMap.voltageSensor.iterator().next() : null;

        leftShooter = new ShooterVelocityController(leftShooterMotor, voltageSensor, 28, 12.6, 1.85e-04, 0.1580, lkp, lki, lkd);
        rightShooter = new ShooterVelocityController(rightShooterMotor, voltageSensor, 28, 12.6, 1.85e-04, 0.1580, rkp, rki, rkd);

        leftShooter.setGains(lkp, lki, lkd);
        rightShooter.setGains(rkp, rki, rkd);

        leftShooter.start();
        rightShooter.start();

        leftCompression = hardwareMap.get(Servo.class, hardwareMappings.leftCompression);
        rightCompression = hardwareMap.get(Servo.class, hardwareMappings.rightCompression);

        transfer = new Transfer(hardwareMap);
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
        double targetRPM = gamepiece.equals(GAMEPIECE.NECTAR) ? nectarRPM : gamepiece.equals(GAMEPIECE.POLLEN) ? pollenRPM : primedRPM;
        target.setTargetRpm(targetRPM * GEAR_RATIO);
    }
    public void setTargetRPMPrimed(SIDE side) {
        ShooterVelocityController target = side.equals(SIDE.LEFT) ? leftShooter : rightShooter;
        target.setTargetRpm(primedRPM * GEAR_RATIO);
    }
    public void setTargetRPMDefault(SIDE side) {
        ShooterVelocityController target = side.equals(SIDE.LEFT) ? leftShooter : rightShooter;
        target.setTargetRpm(defaultRPM * GEAR_RATIO);
    }
    public void setTargetRPM(SIDE side, double targetRPM) {
        ShooterVelocityController target = side.equals(SIDE.LEFT) ? leftShooter : rightShooter;
        target.setTargetRpm(targetRPM * GEAR_RATIO);
    }
    public double getCurrentRPM(SIDE side) {
        ShooterVelocityController target = side.equals(SIDE.LEFT) ? leftShooter : rightShooter;
        return target.getActualRpm() / GEAR_RATIO;
    }

    public void setLeftGains() {
        leftShooter.setGains(lkp, lki, lkd);
    }

    public void setRightGains() {rightShooter.setGains(rkp, rki, rkd);
    }

}
