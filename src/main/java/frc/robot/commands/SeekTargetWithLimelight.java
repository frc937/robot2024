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

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;

/**
 * Seeks an unseen target with the Limelight by spinning the robot in a circle until the Limelight
 * sees a valid target.
 */
public class SeekTargetWithLimelight extends Command {
  private Limelight limelight;
  private Drive drive;
  private double pipelineNumber;
  private double rotationRadiansPerSecond;

  /**
   * Creates a new SeekTargetWithLimelight.
   *
   * @param limelight The Limelight to seek the target with.
   * @param pipelineNumber The pipeline to set the Limelight to while seeking.
   * @param rotationRadiansPerSecond The number of radians per second to rotate while seeking.
   */
  public SeekTargetWithLimelight(
      Limelight limelight, double pipelineNumber, double rotationRadiansPerSecond) {
    this.limelight = limelight;
    this.drive = RobotContainer.drive;
    this.pipelineNumber = pipelineNumber;
    this.rotationRadiansPerSecond = rotationRadiansPerSecond;

    addRequirements(limelight, drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    limelight.setLimelightPipeline(pipelineNumber);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!limelight.hasValidTarget()) {
      drive.driveRobotOriented(Constants.Drive.EMPTY_TRANSLATION, rotationRadiansPerSecond);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return limelight.hasValidTarget();
  }
}
