// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.mailbox.FireNoteRoutine;

/**
 * Uses the Limelight to aim the bot to the Amp and then deposits the note into the Amp. Has a
 * timeout on the Note deposit.
 */
public class AimAndFireRoutine extends SequentialCommandGroup {
  /** Creates a new AimAndFireRoutine. */
  public AimAndFireRoutine() {
    addCommands(
        new AimWithLimelight(
            RobotContainer.limelight,
            Constants.Limelight.AimingLimelight.STEER_STRENGTH,
            Constants.Limelight.AimingLimelight.DISTANCE_FROM_TARGET,
            Constants.Limelight.AimingLimelight.MOUNT_HEIGHT,
            Constants.Limelight.AimingLimelight.MOUNT_ANGLE,
            Constants.Limelight.AimingLimelight.DRIVE_STRENGTH,
            Constants.Limelight.AimingLimelight.SPEED_LIMIT,
            Constants.Limelight.AimingLimelight.TURN_DONE_THRESHOLD,
            Constants.Limelight.AimingLimelight.DISTANCE_DONE_THRESHOLD,
            Constants.Limelight.AimingLimelight.AMP_APRILTAG_HEIGHT),
        new EnterXMode(),
        new ParallelDeadlineGroup(
            new WaitCommand(Constants.Auto.FIRE_NOTE_FOR_TIME), new FireNoteRoutine()));
  }
}
