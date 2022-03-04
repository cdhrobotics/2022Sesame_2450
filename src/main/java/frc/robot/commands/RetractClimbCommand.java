package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.ClimberSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RetractClimbCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final ClimberSubsystem m_subsystem;

  public RetractClimbCommand(ClimberSubsystem subsystem) {
    m_subsystem = subsystem;

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    m_subsystem.ExtendClimbArm.set(-Constants.climbSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    m_subsystem.ExtendClimbArm.set(0);
  }

  @Override
  public boolean isFinished() {
    boolean limitSwitchValue = m_subsystem.limitSwitch1.get();
    if (limitSwitchValue == true){
      System.out.println("Limit switch activated!");
      m_subsystem.ExtendClimbArm.stopMotor();
      m_subsystem.encoder1.reset();
      return true;
    } else {
      return false;
    }
  }
}