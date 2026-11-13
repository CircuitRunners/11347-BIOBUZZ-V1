package org.firstinspires.ftc.teamcode.Subsystems.Subclasses;

import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Util.enums.COMMAND;
import org.firstinspires.ftc.teamcode.Util.enums.GAMEPIECE;
import org.firstinspires.ftc.teamcode.Util.enums.SIDE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BooleanSupplier;

public class Queue {

    private ArrayList<QueueCommand> queue = new ArrayList<>();
    Shooter shooter;
    Transfer transfer;

    public Queue(Shooter shooter, Transfer transfer) {
        this.shooter = shooter;
        this.transfer = transfer;
    }

    public void tick() {
        long now = System.currentTimeMillis();
        ArrayList<QueueCommand> due = new ArrayList<>();

        for (QueueCommand q : queue) {
            if (q.systemTime <= now) {
                due.add(q);
            }
        }

        for (QueueCommand q : due) {
            queue.remove(q);
            executeFunction(q.command, q.side);
        }
    }

    public void generateQueue(GAMEPIECE[] colorValues) {
        parseOrder(calculateOrder(colorValues));
    }

    public void parseOrder(ArrayList<Object>[] orders) {
        for (int i = 0; i < orders.length; i++) {
            double totalDelay = 0.0;
            for (Object o : orders[i]) {
                if (o.getClass() == double.class) {
                    totalDelay += (double) o;
                } else {
                    if (o.getClass() == COMMAND.class) {
                        queue.add(new QueueCommand(totalDelay, (COMMAND) o, i == 0 ? SIDE.LEFT : SIDE.RIGHT));
                    }
                }
            }
        }
    }

    public ArrayList<Object>[] calculateOrder(GAMEPIECE[] colorValues) {
        GAMEPIECE[][] chambers = {{colorValues[0], colorValues[1]}, {colorValues[2], colorValues[3]}};
        ArrayList<Object>[] orders = new ArrayList[2];

        for (int i = 0; i < chambers.length; i++) {
            GAMEPIECE[] index = chambers[i];

            if (index[0].equals(GAMEPIECE.NONE) && !index[1].equals(GAMEPIECE.NONE)) {
                index = new GAMEPIECE[]{index[1], GAMEPIECE.NONE};
            }

            if (!(index[0].equals(GAMEPIECE.NONE))) {
                orders[i].add(COMMAND.START);
                orders[i].add(2.8);
                orders[i].add(index[1].name().equals("POLLEN") ? COMMAND.POLLEN : COMMAND.NECTAR);
                orders[i].add(1.2);
                orders[i].add(COMMAND.ENGAGE);
                if (!(index[1].equals(index[0]))) {
                    if (!(index[1].equals(GAMEPIECE.NONE))) {
                        orders[i].add(1.2);
                        orders[i].add(COMMAND.DISENGAGE);
                        orders[i].add(1.2);
                        orders[i].add(index[1].name().equals("POLLEN") ? COMMAND.POLLEN : COMMAND.NECTAR);
                        orders[i].add(1.8);
                        orders[i].add(COMMAND.ENGAGE);
                        orders[i].add(1.2);
                        orders[i].add(COMMAND.STOP);
                    } else {
                        orders[i].add(1.2);
                        orders[i].add(COMMAND.STOP);
                    }
                } else {
                    orders[i].add(0.2);
                    orders[i].add(COMMAND.DISENGAGE);
                    orders[i].add(0.2);
                    orders[i].add(index[1].name().equals("POLLEN") ? COMMAND.POLLEN : COMMAND.NECTAR);
                    orders[i].add(0.2);
                    orders[i].add(COMMAND.ENGAGE);
                    orders[i].add(0.2);
                    orders[i].add(COMMAND.STOP);
                }
            } else {
                orders[i].add(COMMAND.STOP);
            }
        }
        return orders;
    }

    public void executeFunction(COMMAND command, SIDE side) {
        switch (command) {
            case START:
                shooter.setTargetRPMPrimed(side);
                break;
            case POLLEN:
                shooter.setCompression(side, GAMEPIECE.POLLEN);
                shooter.setTargetRPM(side, GAMEPIECE.POLLEN);
                break;
            case NECTAR:
                shooter.setCompression(side, GAMEPIECE.NECTAR);
                shooter.setTargetRPM(side, GAMEPIECE.NECTAR);
                break;
            case ENGAGE:
                transfer.transferTransferOn();
                transfer.blockerState(side, true);
                break;
            case DISENGAGE:
                transfer.transferStop();
                transfer.blockerState(side, false);
                break;
            case STOP:
                shooter.setTargetRPMDefault(side);
                transfer.transferStop();
                transfer.blockerState(side, false);
                break;

        }
    }

    public BooleanSupplier isEmpty(){
        return () -> queue.isEmpty();
    }

}
