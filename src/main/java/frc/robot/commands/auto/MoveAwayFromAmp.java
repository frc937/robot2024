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

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;

public class MoveAwayFromAmp extends SequentialCommandGroup {
  /** Creates a new MoveAwayFromAmp. */
  public MoveAwayFromAmp() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new ParallelDeadlineGroup(
            new WaitCommand(Constants.Drive.BACK_UP_FROM_AMP_TIME),
            new DriveAutoFieldOriented(
                new Translation2d(Constants.Drive.TAXI_AUTO_METERS_PER_SECOND, 0), 0)),
        new ParallelDeadlineGroup(
            new WaitCommand(Constants.Drive.DRIVE_AWAY_FROM_AMP_TIME),
            new DriveAutoFieldOriented(
                new Translation2d(0, Constants.Drive.TAXI_AUTO_METERS_PER_SECOND), 0)));
  }
}
