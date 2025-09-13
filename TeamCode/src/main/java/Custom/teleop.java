package Custom;


import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;


@TeleOp(name="testTeleop")
//@Disabled

public class teleop extends LinearOpMode
{
    private Follower follower;

    private final int READ_PERIOD = 1;

    private int Vision = 1;

    public DcMotor intake = null;

    public DcMotor shooter = null;

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

    public double speed = .75;
    private final Pose startPose = new Pose(0,0,0);



    @Override
    public void runOpMode() throws InterruptedException
    {
        BatbotHardwareMap robot = new BatbotHardwareMap(hardwareMap);

        intake = hardwareMap.dcMotor.get("intake");
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        shooter = hardwareMap.dcMotor.get("shooter");
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);


        while(!isStarted() && !isStopRequested()) {

            shooter.setPower(.5);
            shooter.setTargetPosition(0);
            shooter.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            intake.setPower(.5);
            intake.setTargetPosition(0);
            intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        follower.startTeleopDrive();

        while (opModeIsActive())
        {

            follower.setTeleOpMovementVectors(-gamepad1.right_stick_y, -gamepad1.right_stick_x, -gamepad1.left_stick_x, true);
            follower.update();

            if(gamepad1.a && buttonA){

                shooter.setPower(.5);
                shooter.setTargetPosition(1);
                shooter.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                buttonA = false;

            }

            if(!gamepad1.a && !buttonA){

                buttonA = true;

            }

            if(gamepad1.b && buttonB){

                shooter.setPower(.5);
                shooter.setTargetPosition(0);
                shooter.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                buttonB = false;

            }

            if(!gamepad1.b && !buttonB){

                buttonB = true;

            }




            if(gamepad1.y && buttonY){

                intake.setPower(.5);
                intake.setTargetPosition(1);
                intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                buttonY = false;

            }


            if(!gamepad1.y && !buttonY){

                buttonY = true;

            }

            if(gamepad1.x && buttonX){

                intake.setPower(.5);
                intake.setTargetPosition(0);
                intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                buttonB = false;

            }


            if(!gamepad1.x && !buttonX){

                buttonX = true;

            }


        }
    }
}
