package org.firstinspires.ftc.teamcode.TeleOps;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Config.Config;
import org.firstinspires.ftc.teamcode.Config.Robot;

import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.FlowerIntake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.opencv.core.Scalar;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.Map;

@Configurable
@TeleOp(name = "fsfsfsfsfp", group = "Competition")
public class InitTest extends LinearOpMode {
    private final Robot robot = new Robot();
    private Drive drive;
    private FlowerIntake flowerIntake;
    private Transfer transfer;
    private Shooter shooterLeft;
    private Shooter shooterRight;
    private Outtake outtakeLeft;
    private Outtake outtakeRight;
    private boolean flowerDown = false;
    public static boolean isPollenL = true; //temp
    public static boolean isPollenR = true; //temp
    private boolean shooterOnL = false;
    private boolean shooterOnR = false;

    private ArrayList<Map<String, Scalar>> testSnap = new ArrayList<>();
    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {
        }
    }
}