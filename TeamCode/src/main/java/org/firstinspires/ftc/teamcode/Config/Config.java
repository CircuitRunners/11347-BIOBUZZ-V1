package org.firstinspires.ftc.teamcode.Config;

public class Config {

    public cDrive drive;

    public Config(String... strings){
        for(String i : strings){
            switch(i.toLowerCase()){
                case "drive":
                    cDrive drive = new cDrive();
                    break;
            }
        }
    }

    public void initRobot(){

    }
}
