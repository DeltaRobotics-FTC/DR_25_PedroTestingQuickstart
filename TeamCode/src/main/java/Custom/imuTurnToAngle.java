package Custom;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

@TeleOp(name="imuTurnToAngle")
//@Disabled

public class imuTurnToAngle extends LinearOpMode
{
    private IMU imu = null;

    private YawPitchRollAngles orentationData = null;

    private final int READ_PERIOD = 1;

    private HuskyLens huskyLens;

    private double turretDegreeRatio = 0.003703703704;

    private double turretCenterPosition = 147.3;

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

    private Servo turret = null;

    @Override
    public void runOpMode() throws InterruptedException
    {

        turret = hardwareMap.servo.get("turret");

        /*
        huskyLens = hardwareMap.get(HuskyLens.class, "huskylens");

        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);

        rateLimit.expire();

        if (!huskyLens.knock()) {
            telemetry.addData(">>", "Problem communicating with " + huskyLens.getDeviceName());
        } else {
            telemetry.addData(">>", "Press start to continue");
        }

        huskyLens.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);
        telemetry.update();

         */


        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.LEFT;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD;

        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        imu.initialize(new IMU.Parameters(orientationOnRobot));

        while (!isStarted() && !isStopRequested()) {

            turret.setPosition(147.3 * 0.003703703704);

        }

        imu.resetYaw();

        while (opModeIsActive())
        {

            //+ ten degrees - ten degrees
            //+ one degree - one degree


            //Multiplying current position by 10
            if(gamepad1.a && buttonA){

                turret.setPosition(turret.getPosition() + turretDegreeRatio * 10);

                buttonA = false;
            }

            if(!gamepad1.a && !buttonA){

                buttonA = true;
            }


            //subtracting 10 degrees from the current position
            if(gamepad1.b && buttonB){

                turret.setPosition(turret.getPosition() - turretDegreeRatio * 10);

                buttonB = false;
            }

            if(!gamepad1.b && !buttonB){

                buttonB = true;
            }



            // adding 1 degree to the current position
            if(gamepad1.y && buttonY){

                turret.setPosition(turret.getPosition() + turretDegreeRatio * 1);

                buttonY = false;
            }

            if(!gamepad1.y && !buttonY){

                buttonY = true;
            }


            //subtracting 1 degree from the current position
            if(gamepad1.x && buttonX){

                turret.setPosition(turret.getPosition() - turretDegreeRatio * 1);

                buttonX = false;
            }

            if(!gamepad1.x && !buttonX){

                buttonX = true;
            }



            orentationData = imu.getRobotYawPitchRollAngles();

            telemetry.addData("turretCurrentPosition", turret.getPosition());

            telemetry.addData("Pitch", orentationData.getPitch());
            telemetry.addData("Yaw", orentationData.getYaw());
            telemetry.addData("Roll", orentationData.getRoll());
            telemetry.update();


            /**
            if (!rateLimit.hasExpired()) {
                continue;
            }
            rateLimit.reset();

            HuskyLens.Block[] blocks = huskyLens.blocks();
            telemetry.addData("Block count", blocks.length);
            for (int i = 0; i < blocks.length; i++) {
                telemetry.addData("Block", blocks[i].toString());

            }

            telemetry.update();

            /*
            0 is the april tag on the blue GOAl
            * 1 is on the OBELISK and is GPP
            * 2 is on the OBELISK and is PPG
            * 3 is on the OBELISK and is PGP
            * 4 is the april tag on the red GOAL


            for (int i = 0; i < blocks.length; i++) {
                if(orentationData.getYaw() == 30 && blocks[i].id == 0){



                }

            }
            */


        }
    }
}
