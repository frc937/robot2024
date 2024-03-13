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

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.RunIntake;

/**
 * Scores the note into the amp. Raises the mailbox, runs the mailbox belts, and then runs the index
 * belts to send the note into the belts without a limit switch.
 *
 * @deprecated This class does not work consistantly when there is an intake limit switch present,
 *     which will be present for the forseeable future.
 */
@Deprecated
public class FireNoteRoutineNoLimitSwitch extends ParallelCommandGroup {
  /** Creates a new FireNoteRoutineNoLimitSwitch. */
  public FireNoteRoutineNoLimitSwitch() {
    addCommands(new DeployMailbox(), new RunIntake());
  }
}
