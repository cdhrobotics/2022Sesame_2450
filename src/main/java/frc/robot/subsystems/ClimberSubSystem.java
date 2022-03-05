package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
    public final CANSparkMax DynamicArm1 = new CANSparkMax(5, MotorType.kBrushed);
    public final CANSparkMax DynamicArm2 = new CANSparkMax(6, MotorType.kBrushed);
    public final CANSparkMax AngleAdjustmentArm = new CANSparkMax(7, MotorType.kBrushed);
    public final Encoder encoder1 = new Encoder(0, 1, false, Encoder.EncodingType.k1X);
    public final DigitalInput limitSwitch1 = new DigitalInput(2);
    State state = State.ANGLE_ADJUSTER;
    public boolean doWeNeedToStopRumble = false;

    // Enum that defines the differnt possible states
    enum State {
        ANGLE_ADJUSTER,
        VERTICAL_ADJUSTER,
    }

    public ClimberSubsystem() {

    }

    public void ManualExtendsInputs(XboxController xbox) {
        // If the controller is still rumbleing, then stop the rumbleing
        if (doWeNeedToStopRumble == true) {
            doWeNeedToStopRumble = false;
            xbox.setRumble(RumbleType.kLeftRumble, 0);
        }

        //System.out.println("The value of the POV is " + xbox.getPOV() + " degrees");

        // INFO ABOUT POV AND D-PAD:
        // The up and down buttons on the d-pad are the vertical adjusters
        // The left and right button on the d-pad are the angle adjusters
        // The POV of the xbox is the d-pads buttons which each correlate to degrees 90, 180, 270, 360
        // If the pov of the xbox is a certain degree (button), then set the state to its corrsponding state
        
        // If right or left, then the state is angle adjuster
        if ((xbox.getPOV() == Constants.pov_right) || (xbox.getPOV() == Constants.pov_left)) {
            state = State.ANGLE_ADJUSTER;
            SmartDashboard.putString("Current State", "ANGLE_ADJUSTER");
            // Vibrate the controller when you switch into this state
            xbox.setRumble(RumbleType.kLeftRumble, 1);

            // Turn rumble off
            doWeNeedToStopRumble = true;

        // Otherwise if up or down, then the state is vertical adjuster
        } else if ((xbox.getPOV() == Constants.pov_up) || (xbox.getPOV() == Constants.pov_down)) {
            state = State.VERTICAL_ADJUSTER;
            SmartDashboard.putString("Current State", "VERTICAL_ADJUSTER");
            // Vibrate the controller when you switch into this state
            xbox.setRumble(RumbleType.kLeftRumble, 1);
            doWeNeedToStopRumble = true;

        // If not up, down, left, or right, then use the previously set state
        } else {

        }

        if (state == State.ANGLE_ADJUSTER) {
            AngleAdjustmentArm.set(xbox.getRightY() / 2);
            // Shutting off the other motors
            DynamicArm1.set(0);
            DynamicArm2.set(0);
        }

        else if (state == State.VERTICAL_ADJUSTER) {
            xbox.setRumble(RumbleType.kRightRumble, 1);
            DynamicArm1.set(xbox.getRightY() / 2);
            DynamicArm2.set(xbox.getRightY() / 2);
            // Shutting off the other motor
            AngleAdjustmentArm.set(0);
        }


        //TODO: Finish/incoperate this:
        // if (xbox.getRightY() < .1){
        // AngleAdjustmentArm.set(0);
        // }
        // else {
        // }

    }
}