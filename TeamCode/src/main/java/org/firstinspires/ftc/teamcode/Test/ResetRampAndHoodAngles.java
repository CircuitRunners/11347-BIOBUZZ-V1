package org.firstinspires.ftc.teamcode.Test;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config.Robot;

//For ramp servoss: robot left ramp servo: nectar = 0.5, pollen = like 0.2, so decrease servo values :)

@Configurable
@TeleOp(name="Set Ramp and Hood Position to 0.5")
public class ResetRampAndHoodAngles extends OpMode {
    private final Robot robot = new Robot();
    public static double position = 0.0;
    public static double INCREMENT = 0.001;
    public static double MAX_POSITION = 100;  //right = 0.32
    private final double MIN_POSITION = 0.0;
    public static boolean reversed = false;

    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    @Override
    public void init() {
        // Initialize the servo (name it "testServo" in your hardware configuration)
        robot.init(hardwareMap);
        //testServo.setDirection(Servo.Direction.REVERSE);
        position = 0.5;

        robot.servoRampL.setPosition(position);
        robot.servoRampR.setPosition(position);

        robot.servoHoodL.setPosition(position);
        robot.servoHoodR.setPosition(0.3);

        telemetry.addData("ServoTester", "Initialized");
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.addLine("init done");
        telemetryM.update(telemetry);
    }

    @Override
    public void init_loop() {
        super.init_loop();
    }

    @Override
    public void loop() {
        // Increment the servo position when the "A" button is pressed
//        if (gamepad1.right_bumper && position < MAX_POSITION) {
//            position += INCREMENT;
//            if (position > MAX_POSITION) {
//                position = MAX_POSITION;
//            }
//        }
//
//        if (gamepad1.left_bumper && position > MIN_POSITION) {
//            position -= INCREMENT;
//            if (position < MIN_POSITION) {
//                position = MIN_POSITION;
//            }
//        }

        robot.servoRampL.setPosition(position);
        robot.servoRampL.setPosition(position);

        robot.servoHoodL.setPosition(position);
        robot.servoHoodR.setPosition(0.3);

        telemetryM.addData("Servo Position", position);
        telemetryM.update(telemetry);
    }


}
