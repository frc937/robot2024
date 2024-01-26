// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.mailbox.MailboxBelts;
/** Command that activates belts when started, and deactivates belts when ended. */
public class DeployBelts extends Command {
  private MailboxBelts mailboxBelts;
  /** Creates a new DeployBelts. */
  public DeployBelts() {
    this.mailboxBelts = RobotContainer.mailboxBelts;
    addRequirements(mailboxBelts);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mailboxBelts.runBelts();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mailboxBelts.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
