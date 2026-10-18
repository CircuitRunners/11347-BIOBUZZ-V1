package org.firstinspires.ftc.teamcode.Subsystems;

import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Mat;

public class SingleFramePipeline extends OpenCvPipeline {
    private Mat savedFrame = new Mat();
    private volatile boolean frameCaptured = false;

    @Override
    public Mat processFrame(Mat input) {
        // Capture only once safely
        if (!frameCaptured) {
            input.copyTo(savedFrame); // Safer than clone() for pre-allocated Mats
            frameCaptured = true;
        }
        return input; // Always return the input so the preview doesn't freeze
    }

    public boolean isFrameCaptured() {
        return frameCaptured;
    }

    public Mat getSavedFrame() {
        return savedFrame;
    }
}