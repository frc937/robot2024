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
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;

public class AimWithLimelight extends Command {
  private Drive drive;
  private Limelight limelight;

  private boolean finished;
  private int counter;

  private double steerStrength,
      distanceFromTarget,
      mountHeight,
      mountAngle,
      driveStrength,
      speedLimit,
      turnDoneThreshold,
      distanceDoneThreshold,
      upperHubTapeHeight;

  /** Creates a new AimWithLimelight. */
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
      double hubTapeHeight) {
    this.steerStrength = steerStrength;
    this.distanceFromTarget = distanceFromTarget;
    this.mountHeight = mountHeight;
    this.mountAngle = mountAngle;
    this.driveStrength = driveStrength;
    this.speedLimit = speedLimit;
    this.turnDoneThreshold = turnDoneThreshold;
    this.distanceDoneThreshold = distanceDoneThreshold;
    this.upperHubTapeHeight = hubTapeHeight;
    this.drive = RobotContainer.drive;
    this.limelight = limelight;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive, limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.finished = false;
    this.counter = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (limelight.hasValidTarget()) {
      double z = limelight.getTX() * steerStrength;
      double yComponent =
          distanceFromTarget
              - ((upperHubTapeHeight - mountHeight) / Math.tan((mountAngle + limelight.getTY())));
      double y = yComponent * (Math.PI / 180.0) * driveStrength;
      if (z > speedLimit) {
        z = speedLimit;
      }
      drive.driveRobotOriented(y * -1.0, 0.0, z);
      boolean isAngled = Math.abs(limelight.getTX()) < turnDoneThreshold;
      boolean isDistanced =
          Math.abs(
                  (distanceFromTarget)
                      - ((upperHubTapeHeight - mountHeight)
                          / Math.tan((mountAngle + limelight.getTY()) * (Math.PI / 180.0))))
              <= distanceDoneThreshold;
      if (isAngled && isDistanced) {
        counter++;
        if (counter > 5) {
          this.finished = true;
        }
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
