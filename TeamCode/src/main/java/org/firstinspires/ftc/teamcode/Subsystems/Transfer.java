package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Config.Config;
import org.firstinspires.ftc.teamcode.Config.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.CvType;
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

    public ArrayList<String> takeSnapshot() {

        ArrayList<String> returnArray = new ArrayList<>();
        robot.transferCamera.startStreaming(1280, 720);
        Mat snapshot = pipeline.getSavedFrame();
        robot.transferCamera.stopStreaming();

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
            Imgproc.threshold(gray, mask, 100, 255, Imgproc.THRESH_BINARY);

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

            if(maskedPercent > 75.0){
                returnArray.add("NO GAME PIECE");
            }else{
                if(hsv[0] < 2.5 || hsv[0] > 87.5){
                    returnArray.add("NECTAR");
                }
                if(hsv[0] > 95 && hsv[0] < 125){
                    returnArray.add("NECTAR");
                }
                if(hsv[0] > 17.5 && hsv[0] < 27.5){
                    returnArray.add("POLLEN");
                }
            }
            i++;
        }

        return returnArray;
    }

}
