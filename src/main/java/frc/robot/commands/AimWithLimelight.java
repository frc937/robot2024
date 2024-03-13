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

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;

/** Aims with the limelight towards an object. */
public class AimWithLimelight extends Command {
  private Drive drive;
  private Limelight limelight;

  private boolean finished, hasSeenTarget;
  private int counter;

  private double steerStrength,
      distanceFromTarget,
      mountHeight,
      mountAngle,
      driveStrength,
      speedLimit,
      turnDoneThreshold,
      distanceDoneThreshold,
      targetHeight,
      pipelineNumber,
      oldPipelineNumber;

  /**
   * Creates a new command to aim with the Limelight.
   *
   * @param limelight The Limelight subsystem to aim with.
   * @param steerStrength How hard to turn towards the target; between 0 and 1.
   * @param distanceFromTarget How far from in inches we want to be from the target.
   * @param mountHeight The height of the center of the Limelight lens off the floor.
   * @param mountAngle The number of degrees the Limelight is mounted back from perfectly vertical.
   *     Positive means rotated such that the lens is facing up, and not down.
   * @param driveStrength How hard to drive towards the target; between 0 and 1.
   * @param speedLimit Basic speed limit to make sure we don't drive too fast. Percentage of max
   *     speed the robot can go. 0 to 1.
   * @param turnDoneThreshold The threshold in degrees when we consider the aiming done.
   * @param distanceDoneThreshold The threshold in inches when we consider the aiming done.
   * @param targetHeight The height of the target off the floor.
   * @param pipelineNumber The pipeline to use while aiming
   */
  public AimWithLimelight(
      Limelight limelight,
      double steerStrength,
      double distanceFromTarget,
      double mountHeight,
      double mountAngle,
      double driveStrength,
      double speedLimit,
      double turnDoneThreshold,
      double distanceDoneThreshold,
      double targetHeight,
      double pipelineNumber) {
    this.steerStrength = steerStrength;
    this.distanceFromTarget = distanceFromTarget;
    this.mountHeight = mountHeight;
    this.mountAngle = mountAngle;
    this.driveStrength = driveStrength;
    this.speedLimit = speedLimit;
    this.turnDoneThreshold = turnDoneThreshold;
    this.distanceDoneThreshold = distanceDoneThreshold;
    this.targetHeight = targetHeight;
    this.pipelineNumber = pipelineNumber;
    this.drive = RobotContainer.drive;
    this.limelight = limelight;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive, limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.oldPipelineNumber = limelight.getLimelightPipeline();
    limelight.setLimelightPipeline(pipelineNumber);
    this.finished = false;
    this.hasSeenTarget = false;
    this.counter = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (limelight.hasValidTarget()) {
      hasSeenTarget = true;
      double z = limelight.getTX() * steerStrength;
      double yComponent =
          distanceFromTarget
              - ((targetHeight - mountHeight) / Math.tan((mountAngle + limelight.getTY())));
      double y = yComponent * (Math.PI / 180.0) * driveStrength;
      if (z > speedLimit) {
        z = speedLimit;
      }
      drive.driveRobot(new Translation2d(y * -1.0, 0.0), z, false);
      boolean isAngled = Math.abs(limelight.getTX()) < turnDoneThreshold;
      boolean isDistanced =
          Math.abs(
                  (distanceFromTarget)
                      - ((targetHeight - mountHeight)
                          / Math.tan((mountAngle + limelight.getTY()) * (Math.PI / 180.0))))
              <= distanceDoneThreshold;
      if (isAngled && isDistanced) {
        counter++;
        if (counter > 5) {
          this.finished = true;
        }
      }
    } else if (hasSeenTarget) {
      this.finished = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    limelight.setLimelightPipeline(oldPipelineNumber);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
