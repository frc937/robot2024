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
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drive.DriveFieldOrientedHeadingSnapping;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
/** Drives bot to red amp. */
public class DriveToAmpRed extends SequentialCommandGroup {

  /** Creates a new DriveToAmpRed. */
  public DriveToAmpRed() {
    super(
        new ParallelDeadlineGroup(
            new WaitCommand(1),
            new DriveFieldOrientedHeadingSnapping(
                () -> 0.0,
                () -> 0.0,
                () -> 0.0,
                () -> false,
                () -> false,
                () -> false,
                () -> true)),
        new ParallelDeadlineGroup(
            new WaitCommand(0.5),
            new DriveFieldOrientedHeadingSnapping(
                () -> 0.0,
                () -> -1.0,
                () -> 0.0,
                () -> false,
                () -> false,
                () -> false,
                () -> false))); /* TODO: CONSTANTS */
  }
}
