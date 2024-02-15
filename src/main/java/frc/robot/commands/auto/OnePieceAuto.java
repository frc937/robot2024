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

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.AimAndFireRountine;
import frc.robot.commands.SeekTargetWithLimelight;

public class OnePieceAuto extends SequentialCommandGroup {
  /** Creates a new OnePieceAuto. */
  public OnePieceAuto() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new SeekTargetWithLimelight(
            RobotContainer.limelight,
            Constants.Limelight.AimingLimelight.AMP_PIPELINE_NUMBER,
            Constants.Drive.LIMELIGHT_SEEKING_RADIANS_PER_SECOND),
        new AimAndFireRountine(),
        new MoveAwayFromAmp());
  }
}
