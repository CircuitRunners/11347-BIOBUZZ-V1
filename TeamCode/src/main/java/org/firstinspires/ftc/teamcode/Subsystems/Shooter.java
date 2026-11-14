package org.firstinspires.ftc.teamcode.Subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Main.hardwareMappings;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.Queue;
import org.firstinspires.ftc.teamcode.Subsystems.Subclasses.ShooterVelocityController;
import org.firstinspires.ftc.teamcode.Util.enums.GAMEPIECE;
import org.firstinspires.ftc.teamcode.Util.enums.SIDE;

import java.util.function.BooleanSupplier;

@Configurable
public class Shooter {
    private DcMotorEx leftShooterMotor;
    private DcMotorEx rightShooterMotor;
    private VoltageSensor voltageSensor;

    private ShooterVelocityController leftShooter;
    private ShooterVelocityController rightShooter;

    private Servo leftCompression;
    private Servo rightCompression;

    private Transfer transfer;
    private Queue queue;
    public static double lkp = 0.5;
    public static double lki = 0.0;
    public static double lkd = 0.001;

    public static double rkp = 0.5;
    public static double rki = 0.0;
    public static double rkd = 0.001;

    public static double pollenCompression = 0.79 ;
    public static double nectarCompression = 0.5;

    public static double pollenRPM = 2350.0;
    public static double nectarRPM = 2900.0;
    public static double primedRPM = 1800.0;
    public static double defaultRPM = 1000.0;
    private static final double GEAR_RATIO = 1.5;
    private double leftRPM;
    private double rightRPM;
    public Shooter(HardwareMap hardwareMap, Transfer transfer){
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

        this.transfer = transfer;
        queue = new Queue(this, transfer);
    }

    public void tick(){
        leftShooter.update();
        rightShooter.update();
        queue.tick();
    }

    public BooleanSupplier queueClear(){
        return queue.isEmpty();
    }

    public void shoot(){
        queue.generateQueue(transfer.determineShootOrder());
    }

    public void setCompression(SIDE side, GAMEPIECE gamepiece){
        Servo target = side.equals(SIDE.LEFT) ? leftCompression : rightCompression;
        target.setPosition(gamepiece.equals(GAMEPIECE.NECTAR) ? nectarCompression : pollenCompression);
    }
    public void setTargetRPM(SIDE side, GAMEPIECE gamepiece) {
        ShooterVelocityController target = side.equals(SIDE.LEFT) ? leftShooter : rightShooter;
        double targetRPM = gamepiece.equals(GAMEPIECE.NECTAR) ? nectarRPM : gamepiece.equals(GAMEPIECE.POLLEN) ? pollenRPM : primedRPM;
        target.setTargetRpm(targetRPM * GEAR_RATIO);
        if (side.equals(SIDE.LEFT)) {
            leftRPM = targetRPM;
        }
        else {
            rightRPM = targetRPM;
        }
    }
    public void setTargetRPMPrimed(SIDE side) {
        ShooterVelocityController target = side.equals(SIDE.LEFT) ? leftShooter : rightShooter;
        target.setTargetRpm(primedRPM * GEAR_RATIO);
        if (side.equals(SIDE.LEFT)) {
            leftRPM = primedRPM;
        }
        else {
            rightRPM = primedRPM;
        }
    }
    public void setTargetRPMDefault(SIDE side) {
        ShooterVelocityController target = side.equals(SIDE.LEFT) ? leftShooter : rightShooter;
        target.setTargetRpm(defaultRPM * GEAR_RATIO);
        if (side.equals(SIDE.LEFT)) {
            leftRPM = defaultRPM;
        }
        else {
            rightRPM = defaultRPM;
        }
    }
    public void setAnyTargetRPM(SIDE side, double targetRPM) {
        ShooterVelocityController target = side.equals(SIDE.LEFT) ? leftShooter : rightShooter;
        target.setTargetRpm(targetRPM * GEAR_RATIO);
        if (side.equals(SIDE.LEFT)) {
            leftRPM = targetRPM;
        }
        else {
            rightRPM = targetRPM;
        }
    }
    public double getCurrentRPM(SIDE side) {
        ShooterVelocityController target = side.equals(SIDE.LEFT) ? leftShooter : rightShooter;
        return target.getActualRpm() / GEAR_RATIO;
    }

    public double getTargetRPM(SIDE side) {
        return side.equals(SIDE.LEFT) ? leftRPM : rightRPM;
    }

    public void setShooterGains(SIDE side) {
        leftShooter.setGains(lkp, lki, lkd);
        if (side.equals(SIDE.LEFT)) {
            leftShooter.setGains(lkp, lki, lkd);
        }
        else {
            rightShooter.setGains(rkp, rki, rkd);
        }
    }

    public String telemetry(){
        return queue.telemetry();
    }

}
