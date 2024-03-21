// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot.commands.mailbox;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.mailbox.Mailbox;

/** Outputs the note from the index belts into the mailbox belts. */
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
    /* If the mailbox is fully raised, run the intake. */
    if (mailbox.getLimitSwitch()) {
      intake.runIntake();
    }
    /* This else isn't neccessary, just advised for safety. If it interferes with anything, feel
    free to remove it. */
    else {
      intake.stop();
    }
    intake.runIntake();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.stop();
    intake.reportNoteIsNotInIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
