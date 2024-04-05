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
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import swervelib.SwerveController;

/** Aims with the limelight towards an object. */
public class AimWithLimelight extends Command {
  private Drive drive;
  private Limelight limelight;

  private boolean finished, hasSeenTarget, isBlueAlliance;
  private int counter, snapAngleXComponent;
  private double steerStrength,
      desiredDistanceFromTarget,
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
   * @param desiredDistanceFromTarget How far from in inches we want to be from the target.
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
      double desiredDistanceFromTarget,
      double mountHeight,
      double mountAngle,
      double driveStrength,
      double speedLimit,
      double turnDoneThreshold,
      double distanceDoneThreshold,
      double targetHeight,
      double pipelineNumber) {
    this.steerStrength = steerStrength;
    this.desiredDistanceFromTarget = desiredDistanceFromTarget;
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

  /**
   * Get the current distance to the target.
   *
   * @return The distance to the target.
   */
  private double getCurrentDistance() {
    return desiredDistanceFromTarget
        - ((targetHeight - mountHeight) / Math.tan(Math.toRadians(mountAngle + limelight.getTY())));
  }

  /**
   * Gets the velocity that we want to command the drivetrain to rotate.
   *
   * @return The velocity (in radians/s) that the drivetrain should rotate at to aim towards the
   *     target.
   */
  private double getRotation() {
    double rot = limelight.getTX() * steerStrength;
    if (rot > speedLimit) {
      rot = speedLimit;
    }
    return rot * drive.getMaximumAngularSpeed();
  }

  /**
   * Gets the x-axis velocity that we want to command the drivetrain to drive.
   *
   * @return The x-axis (forward/backward) velocity that the drivetrain should drive to aim towards
   *     the target.
   */
  private double getX() {
    if (getCurrentDistance() > speedLimit) {
      return speedLimit * driveStrength * drive.getMaximumSpeed();
    }
    return getCurrentDistance() * driveStrength * drive.getMaximumSpeed();
  }

  /**
   * Checks if the robot is currently angled to the target within the tolerance for this command.
   *
   * @return True if we are angled.
   */
  private boolean isAngled() {
    return Math.abs(getRotation()) <= turnDoneThreshold;
  }

  /**
   * Checks if the robot is currently distanced to the target within the tolerance for this command.
   *
   * @return True if we are at the correct distance.
   */
  private boolean isDistanced() {
    return Math.abs(getCurrentDistance()) <= distanceDoneThreshold;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.oldPipelineNumber = limelight.getLimelightPipeline();
    limelight.setLimelightPipeline(pipelineNumber);
    drive.setHeadingCorrection(true);
    this.finished = false;
    this.hasSeenTarget = false;
    this.counter = 0;

    this.snapAngleXComponent = -1;

    var alliance = DriverStation.getAlliance();
    if (alliance.isPresent() ? alliance.get() == DriverStation.Alliance.Red : false) {
      this.snapAngleXComponent *= -1;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    /* Only try to aim if Limelight has a target */
    if (limelight.hasValidTarget()) {
      /* Remember if we've seen a target so that we can assume we're aimed if we lose sight of the target
       * Only reason we assume this is because of where the Limelight is, which results in us not actually seeing the target if we're properly aimed
       */
      // hasSeenTarget = true;

      ChassisSpeeds desiredSpeeds =
          drive.getTargetSpeeds(getX(), getRotation(), snapAngleXComponent, 0);

      Translation2d translation = SwerveController.getTranslation2d(desiredSpeeds);
      drive.driveRobot(translation, desiredSpeeds.omegaRadiansPerSecond, false);

      /* End the command if we're at our "aimed" threshold */
      if (isAngled() && isDistanced()) {
        counter++;

        if (counter > 5) {
          this.finished = true;
        }
      }

      /* If we've seen a target before but lost sight of it, assume we're aimed
       * See above for details on why this is
       */
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
