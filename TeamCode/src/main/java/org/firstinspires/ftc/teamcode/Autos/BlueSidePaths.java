package org.firstinspires.ftc.teamcode.Autos;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class BlueSidePaths {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(83.5, 133, 90);
    private final Pose path1 = poseFactory.of(133, 118, 90);
    private final Pose path1Control1 = poseFactory.of(118.2764, 131.7714, 0);
    private final Pose point2 = poseFactory.of(133, 133, 90);
    private final Pose point3 = poseFactory.of(83.5, 133, 90);
    private final Pose point4 = poseFactory.of(126.5, 94.5, 0);
    private final Pose point4Control1 = poseFactory.of(80.8702, 113.3975, 0);
    private final Pose point4Control2 = poseFactory.of(118.0994, 131.6503, 0);
    private final Pose point4Control3 = poseFactory.of(126.5174, 125.7447, 0);
    private final Pose point5 = poseFactory.of(128.5587, 94.3242, 0);
    private final Pose point6 = poseFactory.of(106.1714, 46.1062, 0);
    private final Pose point6Control1 = poseFactory.of(98.9099, 96.1286, 0);
    private final Pose point6Control2 = poseFactory.of(107.764, 61.3398, 0);
    private final Pose point7 = poseFactory.of(82.75, 8.5, 270);
    private final Pose point7Control1 = poseFactory.of(108.4124, 14.1342, 0);
    private final Pose point7Control2 = poseFactory.of(81.3559, 37.5199, 0);
    private final Pose point7Control3 = poseFactory.of(81.8745, 34.1373, 0);
    private final Pose point8 = poseFactory.of(133.3919, 20.9112, 270);
    private final Pose point8Control1 = poseFactory.of(81.0472, 38.1298, 0);
    private final Pose point8Control2 = poseFactory.of(109.6323, 28.1373, 0);

    public Path path1() {
        return Paths.curve(start, path1Control1, path1).constant(path1);
    }

    public Path path2() {
        return Paths.line(path1, point2).constant(point2);
    }

    public Path path3() {
        return Paths.line(point2, point3).constant(point3);
    }

    public Path path4() {
        return Paths.curve(point3, point4Control1, point4Control2, point4Control3, point4).linear(point3, point4);
    }

    public Path path5() {
        return Paths.line(point4, point5).constant(point5);
    }

    public Path path6() {
        return Paths.curve(point5, point6Control1, point6Control2, point6).constant(point6);
    }

    public Path path7() {
        return Paths.curve(point6, point7Control1, point7Control2, point7Control3, point7).linear(point6, point7);
    }

    public Path path8() {
        return Paths.curve(point7, point8Control1, point8Control2, point8).constant(point8);
    }
    public Pose getStart(){
        return start;
    }
}