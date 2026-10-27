package org.firstinspires.ftc.teamcode.Autos;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class PathsScuffAuto {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(82.5, 8.5, 270);
    private final Pose path1 = poseFactory.of(129.123, 18.9497, 270);
    private final Pose path1Control1 = poseFactory.of(71.1963, 56.6099, 0);
    private final Pose path1Control2 = poseFactory.of(102.5068, 35.372, 0);
    private final Pose path1Control3 = poseFactory.of(79.7298, 6.4503, 0);

    public Path path1() {
        return Paths.curve(start, path1Control1, path1Control2, path1Control3, path1).constant(path1);
    }

    public Pose getStart(){
        return start;
    }
}