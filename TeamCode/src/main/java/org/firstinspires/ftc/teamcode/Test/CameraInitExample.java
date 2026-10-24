package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Subsystems.SingleFramePipeline;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;
import java.util.List;

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
        telemetry.update();

        while (opModeIsActive()) {
            if(gamepad1.circleWasPressed()){

                ArrayList<String> print = takeSnapshot();
                telemetry.addData("print", print);
                telemetry.update();
            }
        }

        // Clean up resources when done webcam.stopStreaming();

    }

    public ArrayList<String> takeSnapshot() {

        ArrayList<String> returnArray = new ArrayList<>();
        webcam.startStreaming(1280, 720);
        Mat snapshot = pipeline.getSavedFrame();
        webcam.stopStreaming();

        Mat startingSnap = snapshot;

        int w = startingSnap.cols() / 2;
        int h = startingSnap.rows() / 2;

        Mat topLeft = startingSnap.submat(0, h, 0, w).clone();
        Mat topRight = startingSnap.submat(0, h, w, 2*w).clone();
        Mat bottomLeft = startingSnap.submat(h, 2*h, 0, w).clone();
        Mat bottomRight = startingSnap.submat(h, 2*h, w, 2*w).clone();

        List<Mat> viewBoxes = List.of(topLeft, topRight, bottomLeft, bottomRight);
        List<String> returnNames = List.of("topLeft", "topRight", "bottomLeft", "bottomRight");

        int i = 0;
        for (Mat viewBox : viewBoxes) {

            Mat gray = new Mat();
            Imgproc.cvtColor(viewBox, gray, Imgproc.COLOR_BGR2GRAY);

            Mat mask = new Mat();
            Imgproc.threshold(gray, mask, 75, 255, Imgproc.THRESH_BINARY);

            int totalPixels = viewBox.rows() * viewBox.cols();
            int nonBlackPixels = Core.countNonZero(mask);
            int blackPixels = totalPixels - nonBlackPixels;

            double maskedPercent = (blackPixels / (double) totalPixels) * 100.0;

            Scalar avgBgr;
            if (nonBlackPixels == 0) {
                avgBgr = new Scalar(0, 0, 0);
            } else {
                avgBgr = Core.mean(viewBox, mask);
            }

            Mat bgrMat = new Mat(1, 1, CvType.CV_8UC3);
            bgrMat.put(0, 0, new double[]{
                    avgBgr.val[0],
                    avgBgr.val[1],
                    avgBgr.val[2]
            });

            Mat hsvMat = new Mat();
            Imgproc.cvtColor(bgrMat, hsvMat, Imgproc.COLOR_BGR2HSV);
            double[] hsv = hsvMat.get(0, 0);

            bgrMat.release();
            hsvMat.release();
            gray.release();
            mask.release();
            telemetry.addData("thing", maskedPercent + "|" + hsv[0] + "," + hsv[1] + "," + hsv[2]);
            if(maskedPercent > 75.0){
                returnArray.add("NO GAME PIECE" + hsv[0]+ " | " + maskedPercent);
            }else{
                if(hsv[0] < 2.5 || hsv[0] > 170.0){
                    returnArray.add("NECTAR" + hsv[0]);
                }else
                if(hsv[0] > 100 && hsv[0] < 127.5){
                    returnArray.add("NECTAR" + hsv[0]);
                }else
                if(hsv[0] > 17.5 && hsv[0] < 27.5){
                    returnArray.add("POLLEN" + hsv[0]);
                }else{returnArray.add("NO GAME PIECE" + hsv[0]);}
            }
            i++;
        }

        return returnArray;
    }
}