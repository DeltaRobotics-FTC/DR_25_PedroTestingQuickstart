package Custom;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;


@TeleOp(name="testTeleop")
//@Disabled

public class testTeleop extends LinearOpMode
{
    private Follower follower;

    public Servo LHE = null;
    public Servo RHE = null;
    public Servo wrist = null;
    public Servo claw = null;

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

        LHE = hardwareMap.servo.get("LHE");
        RHE = hardwareMap.servo.get("RHE");

        wrist = hardwareMap.servo.get("wrist");
        claw = hardwareMap.servo.get("claw");

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);

        //small numbers are out
        LHE.setPosition(.905);
        RHE.setPosition(.905);

        //lower numbers go up
        wrist.setPosition(.65);
        //claw.setPosition(.5);

        waitForStart();

        follower.startTeleopDrive();



        while (opModeIsActive())
        {

            follower.setTeleOpMovementVectors(-gamepad1.right_stick_y, -gamepad1.right_stick_x, -gamepad1.left_stick_x, true);
            follower.update();


            //pushing the slides out
            if(gamepad1.right_bumper && buttonRB){

                LHE.setPosition(.75);
                RHE.setPosition(.75);

                buttonRB = false;
            }

            if(!gamepad1.right_bumper && !buttonRB){

                buttonRB = true;
            }


            // bringing the slides inside
             if(gamepad1.left_bumper && buttonLB){
                LHE.setPosition(.905);
                RHE.setPosition(.905);

                buttonLB = false;
             }
             if(!gamepad1.left_bumper && !buttonLB){

                buttonLB = true;
            }

             if(gamepad1.y && buttonY){

                 wrist.setPosition(.5);

                 buttonY = false;

             }

            if(!gamepad1.y && !buttonY){

                buttonY = true;

            }


            if(gamepad1.a && buttonA){

                wrist.setPosition(.65);

                buttonA = false;

            }

            if(!gamepad1.a && !buttonA){

                buttonA = true;

            }

        }
    }
}
