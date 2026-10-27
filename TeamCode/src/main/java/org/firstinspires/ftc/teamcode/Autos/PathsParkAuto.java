package org.firstinspires.ftc.teamcode.Autos;

import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class PathsParkAuto {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(86, 133, 90);
    private final Pose point1 = poseFactory.of(83, 133, 90);
    private final Pose path1 = poseFactory.of(113.6004, 19.1151, 90);
    private final Pose path1Control1 = poseFactory.of(118.9901, 134.2839, 0);
    private final Pose path1Control2 = poseFactory.of(113.9516, 85.4845, 0);
    private final Pose path1Control3 = poseFactory.of(112.1942, 39.5051, 0);
    private final Pose point3 = poseFactory.of(126.5068, 19.2509, 180);

    public Path path1() {
        return Paths.line(start, point1).constant(point1);
    }

    public Path path2() {
        return Paths.curve(point1, path1Control1, path1Control2, path1Control3, path1).constant(path1);
    }

    public Path path3() {
        return Paths.line(path1, point3).linear(path1, point3);
    }

    public Pose getStart(){
        return start;
    }
}