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
import frc.robot.commands.AimAndFireRoutine;
import frc.robot.commands.SeekTargetWithLimelight;

/* Enter weeb joke here. */
/**
 * Auto that deposits a preloaded Note into the Amp. Does so by spinning until the Limelight sees an
 * AprilTag for one of the Amps, moving the bot towards the Amp with the Limelight, depositing the
 * note in the Amp, and finally taxiing away from the Amp.
 */
public class OnePieceAuto extends SequentialCommandGroup {
  /** Creates a new OnePieceAuto. */
  public OnePieceAuto() {
    addCommands(
        new SeekTargetWithLimelight(
            RobotContainer.limelight,
            Constants.Limelight.AimingLimelight.PipelineNumbers.AMP_PIPELINE_NUMBER,
            Constants.Limelight.LIMELIGHT_SEEKING_RADIANS_PER_SECOND),
        new AimAndFireRoutine(),
        new MoveAwayFromAmp());
  }
}
