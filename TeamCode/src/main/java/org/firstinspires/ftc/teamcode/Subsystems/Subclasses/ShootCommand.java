package org.firstinspires.ftc.teamcode.Subsystems.Subclasses;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Util.enums.GAMEPIECE;
import org.firstinspires.ftc.teamcode.Util.enums.SIDE;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.BooleanSupplier;
import java.util.Iterator;
import java.util.Map;

public class ShootCommand {
    private static Shooter shooter;
    private static Transfer transfer;

    private GAMEPIECE[] order = new GAMEPIECE[4];
    private ArrayList<String> leftOrder = new ArrayList<>();
    private ArrayList<String> rightOrder = new ArrayList<>();
    static HashMap<String, String> queue = new HashMap<>();
    public ShootCommand(GAMEPIECE[] colorSensorValues, Shooter shooter, Transfer transfer){
        order = colorSensorValues;
        ShootCommand.shooter = shooter;
        ShootCommand.transfer = transfer;
    }
    public void calculateOrder(){
        GAMEPIECE[] leftChamber = {order[0], order[1]};
        GAMEPIECE[] rightChamber = {order[2], order[3]};
        GAMEPIECE[][] chambers = {leftChamber, rightChamber};
        ArrayList[] orders = {leftOrder, rightOrder};

        for(int i = 0; i < chambers.length; i++){
            GAMEPIECE[] index = chambers[i];
            ArrayList<String> order = orders[i];

            if(index[0].equals(GAMEPIECE.NONE) && !index[1].equals(GAMEPIECE.NONE)){
                index = new GAMEPIECE[]{index[1], GAMEPIECE.NONE};
            }

            if (!(index[0].equals(GAMEPIECE.NONE))) {
                order.add("START");
                order.add("d2.8");
                order.add(index[0].name());
                order.add("d1.2");
                order.add("ENGAGE");
                if (!(index[1].equals(index[0]))) {
                    if (!(index[1].equals(GAMEPIECE.NONE))) {
                        order.add("d1.2");
                        order.add("DISENGAGE");
                        order.add("d1.2");
                        order.add(index[1].name());
                        order.add("d1.8");
                        order.add("ENGAGE");
                        order.add("d1.2");
                        order.add("STOP");
                    } else {
                        order.add("d1.2");
                        order.add("STOP");
                    }
                } else {
                    order.add("d0.2");
                    order.add("DISENGAGE");
                    order.add("d0.2");
                    order.add(index[1].name());
                    order.add("d0.2");
                    order.add("ENGAGE");
                    order.add("d0.2");
                    order.add("STOP");
                }
            } else {
                order.add("STOP");
            }
        }
    }
    public void shoot(String mode){
        calculateOrder();

        if(mode.equalsIgnoreCase("burst")){
            int index = 0;
            for(ArrayList orders : new ArrayList[]{leftOrder, rightOrder}){
                String shooter = new String[]{"leftShooter", "rightShooter"}[index];
                double delay = 0.0;
                for(Object orderIndex: orders){
                    String order = orderIndex.toString();
                    if(order.substring(0, 1).equals("d")){
                        delay += Double.parseDouble(order.substring(1));
                    }else{
                        appendOrder((int) (delay*1000), (shooter + "-" + order));
                    }
                }
                index ++;
            }
        }else if(mode.equalsIgnoreCase("single")){
            int index = 0;
            for(ArrayList orders : new ArrayList[]{leftOrder, rightOrder}){
                String shooter = new String[]{"leftShooter", "rightShooter"}[index];
                double delay = 0.0;
                for(Object orderIndex: orders){
                    String order = orderIndex.toString();
                    if(order.substring(0, 1).equals("d")){
                        delay += Double.parseDouble(order.substring(1));
                    }else{
                        appendOrder((int) (delay*1000), (shooter + "-" + order));
                        appendOrder((int) (delay + 0.4)*1000, (shooter + "-STOP"));
                        for(int i = 0; i <= orders.indexOf(orderIndex); i++){
                            orders.remove(i);
                        }
                        orders.add(0, "START");
                        break;
                    }
                }
                index ++;
            }
        }
    }

    public static void appendOrder(int delay, String functionID) {
        long delayInMili = (System.currentTimeMillis() + delay);
        queue.put((Math.round((Math.random()*1000000000)) + "~" + delayInMili), functionID);
    }

    public static void tick(){
        long now = System.currentTimeMillis();
        ArrayList<Map.Entry<String, String>> due = new ArrayList<>();

        Iterator<Map.Entry<String, String>> it = queue.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, String> e = it.next();
            if(timeOf(e.getKey()) <= now){
                due.add(new AbstractMap.SimpleEntry<>(e));
                it.remove();
            }
        }

        due.sort(Comparator.comparingLong(e -> timeOf(e.getKey())));
        for(Map.Entry<String, String> e : due) executeFunction(e.getValue());
    }

    private static long timeOf(String key){
        return Long.parseLong(key.split("~")[1]);
    }

    public static BooleanSupplier queueClear(){
        return () -> queue.isEmpty();
    }

    public static void executeFunction(String functionID) {
        switch (functionID) {
            case "leftShooter-START":
                shooter.setTargetRPMPrimed(SIDE.LEFT);
                break;
            case "leftShooter-POLLEN":
                shooter.setCompression(SIDE.LEFT, GAMEPIECE.POLLEN);
                shooter.setTargetRPM(SIDE.LEFT, GAMEPIECE.POLLEN);
                break;
            case "leftShooter-NECTAR":
                shooter.setCompression(SIDE.LEFT, GAMEPIECE.NECTAR);
                shooter.setTargetRPM(SIDE.LEFT, GAMEPIECE.NECTAR);
                break;
            case "leftShooter-ENGAGE":
                transfer.transferTransferOn();
                transfer.blockerState(SIDE.LEFT, true);
                break;
            case "leftShooter-DISENGAGE":
                transfer.transferStop();
                transfer.blockerState(SIDE.LEFT, false);
                break;
            case "leftShooter-STOP":
                shooter.setTargetRPMDefault(SIDE.LEFT);
                transfer.transferStop();
                transfer.blockerState(SIDE.LEFT, false);
                break;

            case "rightShooter-START":
                shooter.setTargetRPMPrimed(SIDE.RIGHT);
                break;
            case "rightShooter-POLLEN":
                shooter.setTargetRPM(SIDE.RIGHT, GAMEPIECE.POLLEN);
                shooter.setCompression(SIDE.RIGHT, GAMEPIECE.POLLEN);
                break;
            case "rightShooter-NECTAR":
                shooter.setTargetRPM(SIDE.RIGHT, GAMEPIECE.NECTAR);
                shooter.setCompression(SIDE.RIGHT, GAMEPIECE.NECTAR);
                break;
            case "rightShooter-ENGAGE":
                transfer.transferTransferOn();
                transfer.blockerState(SIDE.RIGHT, true);
                break;
            case "rightShooter-DISENGAGE":
                transfer.transferStop();
                transfer.blockerState(SIDE.RIGHT, false);
                break;
            case "rightShooter-STOP":
                shooter.setTargetRPMDefault(SIDE.RIGHT);
                transfer.transferStop();
                transfer.blockerState(SIDE.RIGHT, false);
                break;
        }
    }
}
