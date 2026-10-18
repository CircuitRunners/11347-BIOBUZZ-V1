package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Subsystems.SingleFramePipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(name = "Camera Init Example", group = "Concept")
public class CameraInitExample extends LinearOpMode {

    private OpenCvWebcam webcam;
    private SingleFramePipeline pipeline;

    @Override
    public void runOpMode() {
        // 1. Get the camera monitor view ID (allows preview on Driver Station if connected)
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        // 2. Instantiate the webcam using your configuration name ("Webcam 1")
        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "transferCamera"), cameraMonitorViewId);

        // 3. Instantiate and attach your pipeline
        pipeline = new SingleFramePipeline();
        webcam.setPipeline(pipeline);

        // 4. Open the camera device asynchronously (Crucial for preventing crashes)
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                // Start streaming at 640x480 (native, stable resolution for Logitech C270)
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error", "Failed to open camera. Error code: " + errorCode);
                telemetry.update();
            }
        });

        telemetry.addData("Status", "Waiting for INIT...");
        telemetry.update();

        // Wait for the user to press INIT on the Driver Station
        waitForStart();

        // Match starts here - you can check if your single frame was captured
        if (pipeline.isFrameCaptured()) {
            telemetry.addData("Vision", "Frame captured successfully!");
        } else {
            telemetry.addData("Vision", "Frame not yet captured.");
        }
        telemetry.update();

        while (opModeIsActive()) {
            idle();
        }

        // Clean up resources when done webcam.stopStreaming();

    }
}