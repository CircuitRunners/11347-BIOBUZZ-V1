package org.firstinspires.ftc.teamcode.Subsystems;

import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Mat;

public class SingleFramePipeline extends OpenCvPipeline {
    private Mat savedFrame = new Mat();
    private volatile boolean frameCaptured = false;

    @Override
    public Mat processFrame(Mat input) {

        if (!frameCaptured) {
            input.copyTo(savedFrame);
            frameCaptured = true;
        }
        return input;
    }

    public boolean isFrameCaptured() {
        return frameCaptured;
    }

    public Mat getSavedFrame() {
        return savedFrame;
    }
}