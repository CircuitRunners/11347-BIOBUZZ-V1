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
    CaseTable caseTable;
    double totalDelay = 0.0;
    public Queue(Shooter shooter, Transfer transfer) {
        this.shooter = shooter;
        this.transfer = transfer;
        caseTable = new CaseTable();
    }

    public String telemetry(){
        String returnString = "";
        for(QueueCommand i : queue){
            returnString += i.command.name() + " " + i.side.name() + " " + i.systemTime + "\n";
        }

        return returnString;
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

    public void parseOrder(Object[][] orders) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) continue;
            Double delay = 0.0;
            for (Object o : orders[i]) {
                if (o instanceof COMMAND) {
                    queue.add(new QueueCommand(delay, (COMMAND) o, i == 0 ? SIDE.LEFT : SIDE.RIGHT));
                }else{
                    delay += (Double) o;
                }
            }
        }
    }


    public Object[][] calculateOrder(GAMEPIECE[] colorValues) {
        GAMEPIECE[][] chambers = {{colorValues[0], colorValues[1]}, {colorValues[2], colorValues[3]}};
        Object[][] orders = new Object[2][2];

        for (int i = 0; i < chambers.length; i++) {
            GAMEPIECE[] index = chambers[i];
            orders[i] = caseTable.getOrder(index);
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
            case CAP:
                transfer.transferIntakeOn();
                break;
            case CAP_2:
                transfer.transferStop();
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
