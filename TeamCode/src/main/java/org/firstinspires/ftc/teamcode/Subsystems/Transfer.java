package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Config.Config;
import org.firstinspires.ftc.teamcode.Config.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Transfer {

    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    private final Robot robot;
    private final SingleFramePipeline pipeline = new SingleFramePipeline();

    public Transfer(Robot robot) {
        this.robot = robot;
    }

    public void setTransferPower(double power) {
        robot.transferLeft.setPower(power);
        robot.transferRight.setPower(power);
    }

    public void transferOn() {
        setTransferPower(Config.intakePower);
    }

    public void transferSpit() {
        setTransferPower(Config.spitPower);
    }

    public void transferStop() {
        setTransferPower(0);
    }

    public ArrayList<Map<String, Scalar>> takeSnapshot(){

        ArrayList<Map<String, Scalar>> returnArray = new ArrayList<>();
        robot.transferCamera.startStreaming(1280, 720);
        Mat snapshot = pipeline.getSavedFrame();
        robot.transferCamera.stopStreaming();

        Mat startingSnap = snapshot;

        int w = startingSnap.cols()/2;
        int h = startingSnap.width()/2;

        Mat topLeft = startingSnap.submat(0, h, 0, w).clone();
        Mat topRight = startingSnap.submat(0, h, w, 2*w).clone();
        Mat bottomLeft = startingSnap.submat(h, 2*h, 0, w).clone();
        Mat bottomRight = startingSnap.submat(h, 2*h, w, 2*w).clone();

        List<Mat> viewBoxes = List.of(topLeft, topRight, bottomLeft, bottomRight);
        List<String> returnNames = List.of("topLeft", "topRight", "bottomLeft", "bottomRight");

        int i = 0;
        for(Mat viewBox : viewBoxes){
            Mat viewBoxMask = new Mat();
            Imgproc.cvtColor(viewBox, viewBoxMask, Imgproc.COLOR_BGR2GRAY);

            Imgproc.threshold(viewBoxMask, viewBoxMask, 100, 255, Imgproc.THRESH_BINARY);

            Scalar avg = Core.mean(viewBox, viewBoxMask);

            returnArray.add(Map.of(returnNames.get(i), avg));

            i++;
        }

        return returnArray;
    }
}
