// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.mailbox.FireNoteRoutine;

/** Auto that dumps the preloaded note in the robot after 4 seconds. */
public class DumpNote extends ParallelDeadlineGroup {
  /** Creates a new DumpNote. */
  public DumpNote() {
    super(new WaitCommand(Constants.Auto.DUMP_NOTE_WAIT_TIME), new FireNoteRoutine());
  }
}
