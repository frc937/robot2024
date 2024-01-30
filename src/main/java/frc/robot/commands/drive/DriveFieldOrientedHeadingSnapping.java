// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot.commands.drive;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;
import swervelib.SwerveController;

public class DriveFieldOrientedHeadingSnapping extends Command {
  private Drive drive;
  private XboxController controller;

  /** Creates a new DriveFieldOrientedHeadingSnapping. */
  public DriveFieldOrientedHeadingSnapping() {
    this.controller = RobotContainer.driverController.getHID();
    this.drive = RobotContainer.drive;
    addRequirements(RobotContainer.drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double headingX = 0;
    double headingY = 0;
    if (controller.getPOV() == 0) {
      headingY = -1;
    }
    if (controller.getPOV() == 90) {
      headingX = 1;
    }
    if (controller.getPOV() == 180) {
      headingY = 1;
    }
    if (controller.getPOV() == 270) {
      headingX = -1;
    }

    ChassisSpeeds desiredSpeeds =
        drive.getTargetSpeeds(
            RobotContainer.getScaledControllerLeftXAxis(),
            RobotContainer.getScaledControllerLeftYAxis(),
            headingX,
            headingY);

    Translation2d translation = SwerveController.getTranslation2d(desiredSpeeds);
    if (headingX == 0
        && headingY == 0
        && Math.abs(RobotContainer.getScaledControllerRightXAxis()) > 0) {
      drive.driveFieldOriented(
          translation,
          (RobotContainer.getScaledControllerRightXAxis() * Constants.Drive.MAX_ANGULAR_SPEED));
    } else {
      drive.driveFieldOriented(translation, desiredSpeeds.omegaRadiansPerSecond);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
