package org.firstinspires.ftc.teamcode.Autos;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class PathsSemiAuto {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(86, 133, 90);
    private final Pose point1 = poseFactory.of(83.5, 133, 90);
    private final Pose path2 = poseFactory.of(128.254, 117.6484, 90);
    private final Pose path2Control1 = poseFactory.of(118.2764, 131.7714, 0);
    private final Pose point3 = poseFactory.of(130.3634, 126.8478, 60);
    private final Pose point3Control1 = poseFactory.of(132.7714, 120.4019, 0);
    private final Pose point4 = poseFactory.of(131.8391, 131.4584, 90);
    private final Pose point5 = poseFactory.of(83.5, 133, 90);
    private final Pose point6 = poseFactory.of(110, 47, 0);
    private final Pose point6Control1 = poseFactory.of(79, 108, 0);
    private final Pose point6Control2 = poseFactory.of(118.0994, 131.6503, 0);
    private final Pose point6Control3 = poseFactory.of(111, 123, 0);
    private final Pose point7 = poseFactory.of(124.9547, 21.4385, 180);
    private final Pose point7Control1 = poseFactory.of(110, 25, 0);
    private final Pose point7Control2 = poseFactory.of(106.8907, 12.4062, 0);

    public Path path1() {
        return Paths.line(start, point1).constant(point1);
    }

    public Path path2() {
        return Paths.curve(point1, path2Control1, path2).constant(path2);
    }

    public Path path3() {
        return Paths.curve(path2, point3Control1, point3).linear(path2, point3);
    }

    public Path path4() {
        return Paths.line(point3, point4).linear(point3, point4);
    }

    public Path path5() {
        return Paths.line(point4, point5).constant(point5);
    }

    public Path path6() {
        return Paths.curve(point5, point6Control1, point6Control2, point6Control3, point6).linear(point5, point6);
    }

    public Path path7() {
        return Paths.curve(point6, point7Control1, point7Control2, point7).linear(point6, point7);
    }

    public Pose getStart(){
        return start;
    }
}