package Custom;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;


@TeleOp(name="imuTurnToAngle")
//@Disabled

public class imuTurnToAngle extends LinearOpMode
{

    private IMU imu = null;

    private YawPitchRollAngles orentationData = null;

    @Override
    public void runOpMode() throws InterruptedException
    {

        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.LEFT;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD;

        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);



        // Now initialize the IMU with this mounting orientation
        // Note: if you choose two conflicting directions, this initialization will cause a code exception.
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        waitForStart();

        imu.resetYaw();

        while (opModeIsActive())
        {

            orentationData = imu.getRobotYawPitchRollAngles();

            telemetry.addData("Pitch", orentationData.getPitch());
            telemetry.addData("Yaw", orentationData.getYaw());
            telemetry.addData("Roll", orentationData.getRoll());
            telemetry.update();

        }
    }
}
