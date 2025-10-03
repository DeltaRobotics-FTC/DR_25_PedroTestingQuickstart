package Custom;


import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.concurrent.TimeUnit;


@TeleOp(name="testTeleop")
//@Disabled

public class testTeleop extends LinearOpMode
{
    private Follower follower;
    private final int READ_PERIOD = 1;

    private HuskyLens huskyLens;

    private Servo turret = null;

    private Servo hood = null;

    private IMU imu         = null;

    public boolean buttonLT = true;
    public boolean buttonRT = true;
    public boolean buttonLB = true;
    public boolean buttonRB = true;
    public boolean buttonA = true;
    public boolean buttonB = true;
    public boolean buttonX = true;
    public boolean buttonY = true;
    public boolean buttonDU = true;
    public boolean buttonDD = true;
    public boolean buttonDR = true;
    public boolean buttonDL = true;

    public double speed = .1;
    private final Pose startPose = new Pose(0,0,0);



    @Override
    public void runOpMode() throws InterruptedException {
        BatbotHardwareMap robot = new BatbotHardwareMap(hardwareMap);

        turret = hardwareMap.servo.get("cameraTurret");
        hood = hardwareMap.servo.get("hood");

        huskyLens = hardwareMap.get(HuskyLens.class, "huskyLens");

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.RIGHT;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);

        rateLimit.expire();

        if (!huskyLens.knock()) {
            telemetry.addData(">>", "Problem communicating with " + huskyLens.getDeviceName());
        } else {
            telemetry.addData(">>", "Press start to continue");
        }

        huskyLens.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION);

        telemetry.update();

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);



        while (!isStarted() && !isStopRequested()) {

            turret.setPosition(1);//bigger number goes clockwise when looking down on the robot
            hood.setPosition(0);// 0 is clockwise from looking at the top of the servo

        }

        imu.resetYaw();

        follower.startTeleopDrive();

        while (opModeIsActive()) {

            follower = Constants.createFollower(hardwareMap);
            robot.mecanumDrive(-gamepad1.right_stick_y, -gamepad1.right_stick_x, -gamepad1.left_stick_x, .75);


            if (!rateLimit.hasExpired()) {
                continue;
            }
            rateLimit.reset();

            HuskyLens.Block[] blocks = huskyLens.blocks();

            /////////////////////////////////////////////////////////////////////////////////

            for (int i = 0; i < blocks.length; i++){

                if(blocks[i].id == 1) {
                    telemetry.addData("positionX", String.valueOf(blocks[i].x));
                    telemetry.addData("positionY", String.valueOf(blocks[i].y));


                }

            }
            telemetry.update();


            // TURRET CONTROLS
            if (gamepad1.y && buttonY){

                turret.setPosition(0.5);//facing forward ish

                buttonY = false;
            }
            if (!gamepad1.y && !buttonY){

                buttonY = true;
            }


            if (gamepad1.x && buttonX){

                turret.setPosition(0.2);

                buttonX = false;
            }
            if (!gamepad1.x && !buttonX){

                buttonX = true;
            }


            if (gamepad1.b && buttonB){

                turret.setPosition(0.8);

                buttonB = false;
            }
            if (!gamepad1.b && !buttonB){

                buttonB = true;
            }



            //CAMERA HOOD CONTROLS
            if (gamepad1.dpad_down && buttonDD){

                hood.setPosition(0.5);

                buttonDD = false;
            }
            if (!gamepad1.dpad_down && !buttonDD){

                buttonDD = true;
            }


            if (gamepad1.dpad_up && buttonDU){

                hood.setPosition(8);

                buttonDU = false;
            }
            if (!gamepad1.dpad_up && !buttonDU){

                buttonDU = true;
            }

            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            orientation.getYaw(AngleUnit.DEGREES);


        }
    }
}
