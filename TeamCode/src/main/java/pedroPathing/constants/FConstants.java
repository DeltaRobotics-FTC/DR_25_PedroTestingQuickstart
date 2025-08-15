package pedroPathing.constants;

import com.pedropathing.localization.Localizers;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.util.CustomFilteredPIDFCoefficients;
import com.pedropathing.util.CustomPIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class FConstants {
    static {
        FollowerConstants.localizers = Localizers.THREE_WHEEL;

        FollowerConstants.leftFrontMotorName = "motorLF";
        FollowerConstants.leftRearMotorName = "motorLB";
        FollowerConstants.rightFrontMotorName = "motorRF";
        FollowerConstants.rightRearMotorName = "motorRB";

        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.REVERSE;

        FollowerConstants.mass = 8.040;

        FollowerConstants.xMovement = 338.9431;
        FollowerConstants.yMovement = 253.1578;

        FollowerConstants.forwardZeroPowerAcceleration = -31.52450748547713;
        FollowerConstants.lateralZeroPowerAcceleration = -96.3279;

        //translational
        FollowerConstants.translationalPIDFCoefficients.setCoefficients(0.1,0,0.01,0.05);
        FollowerConstants.useSecondaryTranslationalPID = false;
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(0,0,0,0); // Not being used, @see useSecondaryTranslationalPID


        //heading
        FollowerConstants.headingPIDFCoefficients.setCoefficients(1.5,0,0.1,0.24);
        FollowerConstants.useSecondaryHeadingPID = false;
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(0,0,0,0); // Not being used, @see useSecondaryHeadingPID


        //drive
        FollowerConstants.drivePIDFCoefficients.setCoefficients(0.03,0,0,0.1,0.13);
        FollowerConstants.useSecondaryDrivePID = false;
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(0,0,0,0.06,0); // Not being used, @see useSecondaryDrivePID


        FollowerConstants.zeroPowerAccelerationMultiplier = 5;
        FollowerConstants.centripetalScaling = 0.0005;

        FollowerConstants.pathEndTimeoutConstraint = 500;
        FollowerConstants.pathEndTValueConstraint = 0.995;
        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;

        
    }
}
