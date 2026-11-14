package org.firstinspires.ftc.teamcode.Main.Autos.Subclasses;

import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.commands.Commands.waitUntil;
import static com.pedropathing.ivy.groups.Groups.sequential;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.Flower;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;

public class AutoCommands {
    Shooter shooter;
    Flower flower;
    Transfer transfer;

    public AutoCommands(HardwareMap hardwareMap){
        flower = new Flower(hardwareMap);
        transfer = new Transfer(hardwareMap);
        shooter = new Shooter(hardwareMap, transfer);
    }
    public Command shoot(){
        return sequential(
                instant(() -> {
                    shooter.shoot();
                }),
                waitUntil(shooter.queueClear()),
                waitMs(500)
        );
    }
    public Command flowerDown(){
        return sequential(
                instant(() -> {
                    flower.setFlowerDown();
                }),
                waitMs(200)
        );
    }
    public Command flowerUp(){
        return sequential(
                instant(() -> {
                    flower.setFlowerUp();
                }),
                waitMs(200)
        );
    }
    public Command transferOn(){
        return sequential(
                instant(() -> {
                    transfer.transferIntakeOn();
                }),
                waitMs(200)
        );
    }
    public Command transferSpit(){
        return sequential(
                instant(() -> {
                    transfer.transferIntakeSpit();
                }),
                waitMs(200)
        );
    }
    public Command transferStop(){
        return sequential(
                instant(() -> {
                    transfer.transferStop();
                }),
                waitMs(200)
        );
    }
    public Shooter getShooter(){
        return shooter;
    };
    public Transfer getTransfer(){return transfer;}
}
