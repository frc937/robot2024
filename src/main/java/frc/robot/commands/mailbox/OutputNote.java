// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.mailbox;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.mailbox.Mailbox;
import frc.robot.subsystems.mailbox.MailboxBelts;
import frc.robot.subsystems.mailbox.MailboxPneumatics;



public class OutputNote extends Command {
  /** Creates a new OutputNote. */

  private MailboxBelts mailboxBelts;
  private MailboxPneumatics mailboxPneumatics;
  private Mailbox mailbox;

  public OutputNote() {

    this.mailboxBelts = RobotContainer.mailboxBelts;
    this.mailboxPneumatics = RobotContainer.mailboxPneumatics;
    this.mailbox = RobotContainer.mailbox;
    
    addRequirements(mailboxBelts, mailboxPneumatics);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (mailbox.getLimitSwitch()) {
      
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}