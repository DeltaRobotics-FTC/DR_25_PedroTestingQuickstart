package RobotUtilities;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;
import java.util.List;

@TeleOp(group = "Robot Utilities", name = "Servo Tuner")
public class ServoTuner extends LinearOpMode
{

    private enum ServoTunerCommands
    {
        INCREMENT_VALUE,
        DECREMENT_VALUE,
        INCREMENT_SERVO_INDEX,
        DECREMENT_SERVO_INDEX,
        TOGGLE_MODE,
        EXECUTE_ACTION,
        NO_COMMAND;
    }

    private enum ServoTunerModes
    {
        CHANGE_GRANULARITY,
        CHANGE_POSITION,
        CHANGE_INIT_POSITION,
        INIT;

        // CHANGE_GRANULARITY -> CHANGE_INIT_POSITION -> CHANGE_POSITION -> INIT -> go back
        public ServoTunerModes getNextMode()
        {
            switch(this)
            {
                case CHANGE_GRANULARITY:
                    return CHANGE_INIT_POSITION;
                case CHANGE_INIT_POSITION:
                    return CHANGE_POSITION;
                case CHANGE_POSITION:
                    return INIT;
                case INIT:
                    return CHANGE_GRANULARITY;
            }

            // Never reached.
            return CHANGE_GRANULARITY;
        }

        @Override
        public String toString()
        {
            switch(this)
            {
                case CHANGE_GRANULARITY:
                    return "Change Granularity";
                case CHANGE_POSITION:
                    return "Change Position";
                case CHANGE_INIT_POSITION:
                    return "Change Init Position";
                case INIT:
                    return "Init";
            }

            // Never reached
            return null;
        }
    }

    private static final double GRANULARITY_DELTA = 0.05;
    private static final double GRANULARITY_MIN = 0 + GRANULARITY_DELTA;
    private static final double GRANULARITY_MAX = 0.25;
    private static final double DEFAULT_GRANULARITY =  0.1;
    private static final double DEFAULT_POSITION = 0.5;
    private static final int MAX_SERVO_COUNT = 10;

    private static final ServoTunerModes DEFAULT_MODE = ServoTunerModes.CHANGE_GRANULARITY;

    private class ServoData
    {
        public double initPosition = DEFAULT_POSITION;
        public double proposedPosition = DEFAULT_POSITION;
    }

    private double currentValue = 0;
    private double proposedValue = 0;
    private int currentServoIndex = 0;
    private ServoTunerModes mode = DEFAULT_MODE;
    private ServoTunerCommands command = ServoTunerCommands.NO_COMMAND;

    private double granularity = DEFAULT_GRANULARITY;

    private List<ServoData> servoData = new ArrayList<ServoData>();

    private List<Servo> servos = new ArrayList<Servo>();

    @Override
    public void runOpMode() throws InterruptedException
    {
        for(int i = 0; i < MAX_SERVO_COUNT; ++i)
        {
            servos.add(i, hardwareMap.servo.get("servo_" + (i + 1)));
        }

        for(int i = 0; i < MAX_SERVO_COUNT; ++i)
        {
            servoData.add(i, new ServoData());
        }

        waitForStart();

        while(!isStopRequested())
        {
            // Map input to commands.
            if(gamepad1.a)
            {
                command = ServoTunerCommands.EXECUTE_ACTION;
            }
            else if(gamepad1.x)
            {
                command = ServoTunerCommands.TOGGLE_MODE;
            }
            else if(gamepad1.dpad_left)
            {
                command = ServoTunerCommands.DECREMENT_SERVO_INDEX;
            }
            else if(gamepad1.dpad_right)
            {
                command = ServoTunerCommands.INCREMENT_SERVO_INDEX;
            }
            else if(gamepad1.dpad_down)
            {
                command = ServoTunerCommands.DECREMENT_VALUE;
            }
            else if(gamepad1.dpad_up)
            {
                command = ServoTunerCommands.INCREMENT_VALUE;
            }
            else
            {
                command = ServoTunerCommands.NO_COMMAND;
            }

            // Now that we know the command, perform it!
            switch(command)
            {
                case INCREMENT_SERVO_INDEX:
                {
                    currentServoIndex = (currentServoIndex + 1) % MAX_SERVO_COUNT;
                    break;
                }
                case DECREMENT_SERVO_INDEX:
                {
                    if(currentServoIndex == 0)
                    {
                        currentServoIndex = MAX_SERVO_COUNT - 1;
                    }
                    else
                    {
                        currentServoIndex--;
                    }
                    break;
                }
                case INCREMENT_VALUE:
                {
                    if(mode != ServoTunerModes.INIT)
                    {
                        if(mode == ServoTunerModes.CHANGE_GRANULARITY)
                        {
                            granularity = granularity + GRANULARITY_DELTA;
                            if(granularity > GRANULARITY_MAX)
                            {
                                granularity = GRANULARITY_MAX;
                            }
                        }
                        else
                        {
                            proposedValue += granularity;
                            if(proposedValue > 1.0)
                            {
                                proposedValue = 1.0;
                            }
                        }
                    }
                    break;
                }
                case DECREMENT_VALUE:
                {
                    if(mode != ServoTunerModes.INIT)
                    {
                        if(mode == ServoTunerModes.CHANGE_GRANULARITY)
                        {
                            granularity = granularity - GRANULARITY_DELTA;
                            if(granularity < GRANULARITY_MIN)
                            {
                                granularity = GRANULARITY_MIN;
                            }
                        }
                        else
                        {
                            proposedValue -= granularity;
                            if(proposedValue < 0.0)
                            {
                                proposedValue = 0.0;
                            }
                        }
                    }
                    break;
                }
                case TOGGLE_MODE:
                {
                    mode = mode.getNextMode();
                    break;
                }
                case EXECUTE_ACTION:
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
        }
    }
}
