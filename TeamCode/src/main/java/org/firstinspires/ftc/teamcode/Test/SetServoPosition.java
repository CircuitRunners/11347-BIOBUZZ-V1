package org.firstinspires.ftc.teamcode.Test;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

//For ramp servoss: robot left ramp servo: nectar = 0.5, pollen = like 0.2, so decrease servo values :)

@Configurable
@TeleOp(name="Set Servo Position")
public class SetServoPosition extends OpMode {
    private Servo testServo;
    public static double position = 0.5;
    public static double INCREMENT = 0.001; // Amount to increment servo position
    public static double MAX_POSITION = 100;  //right = 0.32
    private final double MIN_POSITION = 0.0;
    public static boolean reversed = false;

    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    @Override
    public void init() {
        // Initialize the servo (name it "testServo" in your hardware configuration)
        testServo = hardwareMap.get(Servo.class, "testServo");
        //testServo.setDirection(Servo.Direction.REVERSE);

        testServo.setPosition(position);

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
        if (gamepad1.right_bumper && position < MAX_POSITION) {
            position += INCREMENT;
            if (position > MAX_POSITION) {
                position = MAX_POSITION;
            }
        }

        if (gamepad1.left_bumper && position > MIN_POSITION) {
            position -= INCREMENT;
            if (position < MIN_POSITION) {
                position = MIN_POSITION;
            }
        }

        if (reversed) {
            testServo.setDirection(Servo.Direction.REVERSE);
        } else {
            testServo.setDirection(Servo.Direction.FORWARD);
        }

        testServo.setPosition(position);

        telemetryM.addData("Servo Position", position);
        telemetryM.update(telemetry);
    }


}
