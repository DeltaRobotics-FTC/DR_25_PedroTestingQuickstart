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
    public Servo LHE = null;
    public Servo RHE = null;
    public Servo wrist = null;
    public Servo claw = null;

    public boolean buttonA = true;
    public boolean buttonB = true;
    public boolean buttonX = true;
    public boolean buttonY = true;


    @Override
    public void runOpMode() throws InterruptedException
    {
        LHE = hardwareMap.servo.get("LHE");
        RHE = hardwareMap.servo.get("RHE");

        wrist = hardwareMap.servo.get("wrist");
        claw = hardwareMap.servo.get("claw");

        while (!isStarted() && !isStopRequested()) {

            //Bigger number is backwards
            LHE.setPosition(.9);
            RHE.setPosition(.9);

            //wrist.setPosition(.5);
            //claw.setPosition(.5);

        }

        while (opModeIsActive())
        {
            if(gamepad1.a && buttonA){

                LHE.setPosition(.75);
                RHE.setPosition(.75);

                buttonA = false;
            }

            if(!gamepad1.a && !buttonA){

                buttonA = true;
            }



            if(gamepad1.b && buttonB){

                LHE.setPosition(.1);
                RHE.setPosition(.9);

                buttonB = false;
            }

            if(!gamepad1.b && !buttonB){

                buttonB = true;
            }



            if(gamepad1.x && buttonX){

                LHE.setPosition(.25);
                RHE.setPosition(.75);

                buttonX = false;
            }

            if(!gamepad1.x && !buttonX){

                buttonX = true;
            }

        }
    }
}
