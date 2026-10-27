
package org.firstinspires.ftc.teamcode.Autos;

import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class PathsFullAuto {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(86, 133, 90);
    private final Pose point1 = poseFactory.of(83, 133, 0);
    private final Pose path2 = poseFactory.of(128.254, 117.6484, 90);
    private final Pose path2Control1 = poseFactory.of(118.2764, 131.7714, 0);
    private final Pose point3 = poseFactory.of(130.3634, 126.8478, 60);
    private final Pose point3Control1 = poseFactory.of(132.7714, 120.4019, 0);
    private final Pose point4 = poseFactory.of(131.8391, 131.4584, 90);
    private final Pose point5 = poseFactory.of(83.5, 133, 90);
    private final Pose point6 = poseFactory.of(120.172, 93.0938, 0);
    private final Pose point6Control1 = poseFactory.of(80.8702, 113.3975, 0);
    private final Pose point6Control2 = poseFactory.of(118.0994, 131.6503, 0);
    private final Pose point6Control3 = poseFactory.of(120.7168, 125.2174, 0);
    private final Pose point7 = poseFactory.of(125.5, 93, 0);
    private final Pose penStroke = poseFactory.of(125.4671, 93.1169, 0);
    private final Pose penStrokeThrough1 = poseFactory.of(125.4671, 95.4099, 0);
    private final Pose penStrokeThrough2 = poseFactory.of(125.6429, 92.0337, 0);
    private final Pose penStrokeThrough3 = poseFactory.of(125.4671, 90.5454, 0);
    private final Pose penStrokeThrough4 = poseFactory.of(125.1974, 93.914, 0);
    private final Pose penStrokeThrough5 = poseFactory.of(125.4671, 93.6205, 0);
    private final Pose penStrokeThrough6 = poseFactory.of(125.4671, 90.4375, 0);
    private final Pose penStrokeThrough7 = poseFactory.of(125.4671, 93.8865, 0);
    private final Pose point9 = poseFactory.of(110.7207, 80.4353, 0);
    private final Pose point9Control1 = poseFactory.of(108.0448, 93.4776, 0);
    private final Pose point10 = poseFactory.of(80.2891, 8.5, 270);
    private final Pose point10Control1 = poseFactory.of(113.5418, 1.0454, 0);
    private final Pose point10Control2 = poseFactory.of(81.3559, 37.5199, 0);
    private final Pose point10Control3 = poseFactory.of(82.0503, 30.7975, 0);
    private final Pose point11 = poseFactory.of(128.2944, 20.3839, 180);
    private final Pose point11Control1 = poseFactory.of(81.0472, 38.1298, 0);
    private final Pose point11Control2 = poseFactory.of(109.6323, 28.1373, 0);

    public Path path1() {
        return Paths.line(start, point1).reverseTangent();
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
        return Paths.line(point6, point7).constant(point7);
    }

    public Path penStroke() {
        return Paths.through(point7, penStrokeThrough1, penStrokeThrough2, penStrokeThrough3, penStrokeThrough4, penStrokeThrough5, penStrokeThrough6, penStrokeThrough7, penStroke).constant(penStroke);
    }

    public Path path9() {
        return Paths.curve(penStroke, point9Control1, point9).constant(point9);
    }

    public Path path10() {
        return Paths.curve(point9, point10Control1, point10Control2, point10Control3, point10).linear(point9, point10);
    }

    public Path path11() {
        return Paths.curve(point10, point11Control1, point11Control2, point11).linear(point10, point11);
    }

    public Pose getStart(){
        return start;
    }
}