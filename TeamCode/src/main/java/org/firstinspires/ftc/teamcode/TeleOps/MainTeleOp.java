//package org.firstinspires.ftc.teamcode.TeleOps;
//
//import com.pedropathing.math.Pose;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//
//import org.firstinspires.ftc.teamcode.Config.Config;
//import org.firstinspires.ftc.teamcode.Config.Robot;
//
//import org.firstinspires.ftc.teamcode.Subsystems.Drive;
//import org.firstinspires.ftc.teamcode.Subsystems.ShooterLeft;
//import org.firstinspires.ftc.teamcode.Subsystems.ShooterRight;
//import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
//import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
//
//@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "BIOBUZZ TeleOp", group = "Competition")
//public class MainTeleOp extends OpMode {
//    public enum Preset { HOME, INTAKE, TRANSFER, SCORE }
//
//    private final Robot robot = new Robot();
//    private Drive drive;
//    private Transfer transfer;
//    private ShooterLeft shooterLeft;
//
//    private Preset currentPreset = Preset.HOME;
//    private boolean lastStart, lastA, lastB, lastX, lastY, lastDpadUp, lastDpadDown;
//    private boolean lastBack1, lastX1, lastY1;
//
//    @Override
//    public void init() {
//        robot.init(hardwareMap);
//        drive = new Drive(robot);
//        transfer = new Transfer(robot);
//        shooter = new Shooter(robot);
//        localizer = new Localizer(hardwareMap);
//        avoidance = new ObstacleAvoidance(robot);
//        reseedPose();
//
//        telemetry.addLine("BIOBUZZ READY");
//        telemetry.addLine("P1: BACK = fence on/off | X = obstacle avoid on/off");
//        telemetry.addLine("P1: Y = re-seed pose at start spot | hold LT = override both");
//        telemetry.update();
//    }
//
//    @Override
//    public void start() {
//        robot.resetHeading();
//    }
//
//    @Override
//    public void loop() {
//        robot.clearBulkCache();
//
//        if (gamepad1.start && !lastStart) robot.resetHeading();
//        lastStart = gamepad1.start;
//
//        double strafe = -gamepad1.left_stick_x;
//        double forward = -gamepad1.left_stick_y;
//        double turn = -gamepad1.right_stick_x;
//
//
//        drive.drive(strafe, forward, turn);
//
//
//        Pose pose = localizer.getPose();
//        if (!override) {
//            cmd = fence.limitRobotFrame(cmd[0], cmd[1], pose.x(), pose.y(), pose.heading());
//            cmd = avoidance.limit(cmd[0], cmd[1], scale);
//        } else {
//            avoidance.reset();
//        }
//
//
//        drive.drive(cmd[0], cmd[1], turn, true, scale);
//
//        if (gamepad2.a && !lastA) {
//            transfer1.setHomePosition();
//            currentPreset = Preset.HOME;
//        }
//        if (gamepad2.b && !lastB) {
//            transfer1.setIntakePosition();
//            currentPreset = Preset.INTAKE;
//        }
//        if (gamepad2.x && !lastX) {
//            transfer.setTransferPosition();
//            currentPreset = Preset.TRANSFER;
//        }
//        if (gamepad2.y && !lastY) {
//            transfer.setScorePosition();
//            currentPreset = Preset.SCORE;
//        }
//        lastA = gamepad2.a;
//        lastB = gamepad2.b;
//        lastX = gamepad2.x;
//        lastY = gamepad2.y;
//
//        if (gamepad2.dpad_up && !lastDpadUp) shooter.setPower(1.0);
//        if (gamepad2.dpad_down && !lastDpadDown) shooter.setPower(0.0);
//        lastDpadUp = gamepad2.dpad_up;
//        lastDpadDown = gamepad2.dpad_down;
//
//        double manualShooter = gamepad2.right_trigger - gamepad2.left_trigger;
//        if (Math.abs(manualShooter) > 0.05) shooter.setPower(manualShooter);
//        shooter.update();
//
//        telemetry.addData("Heading Deg", "%.1f", Math.toDegrees(robot.getHeadingRadians()));
//        telemetry.addData("Drive Mode", robotCentric ? "Robot" : "Field");
//        telemetry.addData("Preset", currentPreset);
//        telemetry.addData("Shooter Power", "%.2f / %.2f", shooter.getCommandedPower(), shooter.getTargetPower());
//        telemetry.addData("Shooter Current", "%.2f A", shooter.getCurrentAmps());
//        telemetry.addData("Pose X/Y/H", "%.1f / %.1f / %.1f", pose.x(), pose.y(), Math.toDegrees(pose.heading()));
//        telemetry.addData("Fence", override ? "OVERRIDE" : (fence.isEnabled() ? (fence.isLimiting() ? "ON - LIMITING" : "ON") : "OFF"));
//        telemetry.addData("Obstacle Guard", override ? "OVERRIDE"
//                : (avoidance.isEnabled() ? (avoidance.isLimiting() ? "ON - BLOCKED" : "ON") : "OFF"));
//        telemetry.addData("Blocked Fwd/Strafe", "%d / %d", avoidance.forwardBlockedDir(), avoidance.strafeBlockedDir());
//        telemetry.addData("Odo Speed Fwd/Strafe (in/s)", "%.1f / %.1f", avoidance.forwardSpeedInS(), avoidance.strafeSpeedInS());
//        telemetry.update();
//    }
//
//    @Override
//    public void stop() {
//        robot.stopAll();
//    }
//}