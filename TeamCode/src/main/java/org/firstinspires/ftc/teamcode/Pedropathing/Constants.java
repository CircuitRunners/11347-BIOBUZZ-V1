package org.firstinspires.ftc.teamcode.Pedropathing;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.OctoQuadConfig;
import com.pedropathing.revhub.localizers.OctoQuadLocalizer;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuad;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower(
                new OctoQuadLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("fl");
        c.frontRightName.set("fr");
        c.backLeftName.set("bl");
        c.backRightName.set("br");
        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });


    public static OctoQuadConfig localizerConfig = new OctoQuadConfig(c -> {
        c.name.set("octoquad");
        c.xPodPort.set(7);
        c.yPodPort.set(6);
        c.ticksPerUnit.set(505.316944406);
        c.xPodOffset.set(1.2007874015748032);
        c.yPodOffset.set(-7.165354330708661);
        c.xPodDirection.set(OctoQuad.EncoderDirection.REVERSE);
        c.yPodDirection.set(OctoQuad.EncoderDirection.REVERSE);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
        c.i2cRecoveryMode.set(OctoQuad.I2cRecoveryMode.MODE_1_PERIPH_RST_ON_FRAME_ERR);
        c.headingScalar.set(1.0830637917376482);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.3766115868666198);
                Controller secondaryTranslationalForward = Controller.proportional(0.1391479181002775);
                Controller primaryTranslationalLateral = Controller.proportional(0.48779264123322286);
                Controller secondaryTranslationalLateral = Controller.proportional(0.1802263468762504);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.015774123102703545));
                c.brake.set(Controller.proportionalFeedforward(0.013408004637298012));

                c.headingFeedback.set(Controller.proportional(6.20425367395882));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.05850754550769848, 0.004974305795818604));

                c.linearBrakeCoefficients.set(Matrix.diag(0.07738290266389328, 0.05547817553639249));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0016762902422421504, 0.0017964378814177592));

                c.maxAchievableForwardVelocity.set(67.38180857151366);
                c.maxAchievableStrafeVelocity.set(58.42907287738791);
                c.naturalForwardDeceleration.set(43.88813287687628);
                c.naturalStrafeDeceleration.set(67.7091396605512);
            }
    );


}