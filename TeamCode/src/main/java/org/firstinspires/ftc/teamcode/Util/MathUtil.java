package org.firstinspires.ftc.teamcode.Util;

public class MathUtil{
    public static double clamp(double num, double min, double max){
        return Math.max(min, Math.min(num, max));
    }
}
