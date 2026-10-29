package org.firstinspires.ftc.teamcode.Subsystems.Subclasses;

import java.util.ArrayList;
import java.util.HashMap;

public class ShootCommand {
    private String[] order = new String[6];

    private ArrayList<String> leftOrder = new ArrayList<>();
    private ArrayList<String> rightOrder = new ArrayList<>();
    HashMap<String, String> queue = new HashMap<>();
    public ShootCommand(String[] colorSensorValues){
        order = colorSensorValues;
    }
    public void calculateOrder(){
        String[] leftChamber = {(order[0].equals(order[1])?order[0]:(order[0].equals("NONE")?order[1]:order[0])), order[2]};
        String[] rightChamber = {(order[3].equals(order[4])?order[3]:(order[3].equals("NONE")?order[4]:order[3])), order[5]};
        String[][] chambers = {leftChamber, rightChamber};
        ArrayList[] orders = {leftOrder, rightOrder};

        for(int i = 0; i < chambers.length; i++){
            String[] index = chambers[i];
            ArrayList<String> order = orders[i];
            if (!(index[0].equals("NONE"))) {
                order.add("START");
                order.add("d0.8");
                order.add(index[0]);
                order.add("d0.2");
                order.add("ENGAGE");
                if (!(index[1].equals(index[0]))) {
                    if (!(index[1].equals("NONE"))) {
                        order.add("d0.2");
                        order.add(index[1]);
                        order.add("d0.8");
                        order.add("ENGAGE");
                        order.add("d0.2");
                        order.add("STOP");
                    } else {
                        order.add("d0.2");
                        order.add("STOP");
                    }
                } else {
                    order.add("d0.2");
                    order.add(index[1]);
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
                        appendOrder((int) delay*1000, (shooter + "-" + order));
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
                        appendOrder((int) delay*1000, (shooter + "-" + order));
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

    public void appendOrder(int delay, String functionID) {
        long delayInMili = (System.currentTimeMillis() + delay);
        queue.put((Math.round((Math.random()*1000000000)) + "-" + delayInMili), functionID);
    }

    public void tick(){
        ArrayList<String> toExecute = new ArrayList<>();
        System.currentTimeMillis();
        Long systemTime = System.currentTimeMillis();

        for(String i : queue.keySet()){
            if(Long.parseLong(i.split("-")[1]) <= systemTime){
                toExecute.add(queue.get(i));
            }
        }

        for(String i : queue.keySet()){if(Long.parseLong(i.split("-")[1]) <= systemTime) queue.remove(i);}

        for(String functionID : toExecute){
            executeFunction(functionID);
        }
    }

    public void executeFunction(String functionID) {
        switch (functionID) {
            case "leftShooter-START":
                //Shooter RPM -> Constant Prep speed ~ avg of pollen and nectar rpm
                break;
            case "leftShooter-POLLEN":
                //Shooter RPM -> Pollen RPM
                break;
            case "leftShooter-NECTAR":
                //Shooter RPM -> Nectar RPM
                break;
            case "leftShooter-ENGAGE":
                //Kicker on
                //Transfer on
                break;
            case "leftShooter-STOP":
                //Shooter RPM -> Constant speed ~ 40% of avg pollen nectar rpm
                break;

            // v Same as left shooter
            case "rightShooter-START":

                break;
            case "rightShooter-POLLEN":

                break;
            case "rightShooter-NECTAR":

                break;
            case "rightShooter-ENGAGE":

                break;
            case "rightShooter-STOP":

                break;
        }
    }
}
