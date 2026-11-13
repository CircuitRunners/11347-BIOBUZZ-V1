package org.firstinspires.ftc.teamcode.Subsystems.Subclasses;

import org.firstinspires.ftc.teamcode.Util.enums.COMMAND;
import org.firstinspires.ftc.teamcode.Util.enums.SIDE;

import java.util.ArrayList;

public class QueueCommand {

    public static ArrayList<Double> ids;

    public long systemTime;
    public COMMAND command;
    public SIDE side;

    public QueueCommand(double delay, COMMAND command, SIDE side){
        this.systemTime = System.currentTimeMillis() + (long) delay*1000;
        this.command = command;
        this.side = side;
    }
}
