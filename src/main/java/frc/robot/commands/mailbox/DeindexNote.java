// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.mailbox;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.mailbox.Mailbox;

public class DeindexNote extends Command {

  private Intake intake;
  private Mailbox mailbox;

  /** Creates a new DeindexNote. */
  public DeindexNote() {
    this.intake = RobotContainer.intake;
    this.mailbox = RobotContainer.mailbox;
    addRequirements(intake, mailbox);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (mailbox.getLimitSwitch()) {
      intake.runIntake();
    }
    /* This else isn't neccessary, just advised for safety. If it interferes with anything, feel free to remove it. */
    else {
      intake.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
