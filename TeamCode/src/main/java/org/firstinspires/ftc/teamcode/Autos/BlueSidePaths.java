package org.firstinspires.ftc.teamcode.Autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class BlueSidePaths {
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(58, 8, 90);
    private final Pose flowerIntake1 = poseFactory.of(30, 8, 180);
    private final Pose flowerIntakeControl1 = poseFactory.of(35, 20, 0);
    private final Pose flowerIntake2Start = poseFactory.of(30, 8, 180);
    private final Pose flowerIntake2 = poseFactory.of(15, 8, 180);
    private final Pose flowerIntake3Start = poseFactory.of(15, 8, 180);
    private final Pose flowerIntake3 = poseFactory.of(11, 8, 190);
    private final Pose point4Start = poseFactory.of(11, 8, 190);
    private final Pose point4 = poseFactory.of(58, 8, 90);
    private final Pose point5Start = poseFactory.of(58, 8, 90);
    private final Pose point5 = poseFactory.of(58, 132, 90);
    private final Pose point6Start = poseFactory.of(58, 132, 90);
    private final Pose point6 = poseFactory.of(58, 126, 90);
    private final Pose point7Start = poseFactory.of(58, 126, 90);
    private final Pose point7 = poseFactory.of(47, 126, 90);
    private final Pose point8Start = poseFactory.of(47, 126, 90);
    private final Pose point8 = poseFactory.of(47, 126, 90);
    private final Pose point9 = poseFactory.of(47, 111.7092, -90);
    private final Pose point10Start = poseFactory.of(47, 111.7092, 90);
    private final Pose point10 = poseFactory.of(47, 110, 90);
    private final Pose point11Start = poseFactory.of(47, 110, 90);
    private final Pose point11 = poseFactory.of(58, 120, 90);
    private final Pose point12 = poseFactory.of(56.209, 21.2861, -91.0394);
    private final Pose point13 = poseFactory.of(55.9646, 38.1488, 90.8303);
    private final Pose point14 = poseFactory.of(60.6079, 24.2187, -71.5651);
    private final Pose point15 = poseFactory.of(89.69, 43.7697, 33.9117);
    private final Pose point16 = poseFactory.of(54.987, 38.882, -171.9829);

    public Pose getStart() {
        return start;
    }

    public Path flowerIntake1() {
        return Paths.curve(start, flowerIntakeControl1, flowerIntake1).linear(start, flowerIntake1);
    }

    public Path flowerIntake2() {
        return Paths.line(flowerIntake2Start, flowerIntake2).linear(flowerIntake2Start, flowerIntake2);
    }

    public Path path3() {
        return Paths.line(flowerIntake3Start, flowerIntake3).linear(flowerIntake3Start, flowerIntake3);
    }

//    public Path path4() {
//        return Paths.line(point4Start, point4).linear(point4Start, point4);
//    }
//
//    public Path path5() {
//        return Paths.line(point5Start, point5).linear(point5Start, point5);
//    }
//
//    public Path path6() {
//        return Paths.line(point6Start, point6).linear(point6Start, point6);
//    }

//    public Path path7() {
//        return Paths.line(point7Start, point7).linear(point7Start, point7);
//    }
//
//    public Path path8() {
//        return Paths.line(point8Start, point8).linear(point8Start, point8);
//    }
//
//    public Path path9() {
//        return Paths.line(point8, point9).tangent();
//    }
//
//    public Path path10() {
//        return Paths.line(point10Start, point10).linear(point10Start, point10);
//    }
//
//    public Path path11() {
//        return Paths.line(point11Start, point11).linear(point11Start, point11);
//    }
//
//    public Path path12() {
//        return Paths.line(point11, point12).tangent();
//    }
//
//    public Path path13() {
//        return Paths.line(point12, point13).tangent();
//    }
//
//    public Path path14() {
//        return Paths.line(point13, point14).tangent();
//    }
//
//    public Path path15() {
//        return Paths.line(point14, point15).tangent();
//    }
//
//    public Path path16() {
//        return Paths.line(point15, point16).tangent();
//    }
}
