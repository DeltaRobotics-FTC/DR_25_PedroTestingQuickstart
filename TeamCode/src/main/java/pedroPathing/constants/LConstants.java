package pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class LConstants {
    static {
        ThreeWheelConstants.forwardTicksToInches = .003;
        ThreeWheelConstants.strafeTicksToInches = .003;
        ThreeWheelConstants.turnTicksToInches = .0036;
        ThreeWheelConstants.leftY = 8;
        ThreeWheelConstants.rightY = -8;
        ThreeWheelConstants.strafeX = -3.5;
        ThreeWheelConstants.leftEncoder_HardwareMapName = "motorLB";
        ThreeWheelConstants.rightEncoder_HardwareMapName = "motorRB";
        ThreeWheelConstants.strafeEncoder_HardwareMapName = "motorLF";
        ThreeWheelConstants.leftEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.rightEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.strafeEncoderDirection = Encoder.FORWARD;
    }
}




