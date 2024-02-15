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
import java.util.function.Supplier;
import swervelib.SwerveController;

/**
 * Drives robot in field oriented mode with shortcuts to snap to field relative angles in increments
 * of 90º using dpad
 */
public class DriveFieldOrientedHeadingSnapping extends Command {
  private Drive drive;
  private XboxController controller;
  private final Supplier<Double> xScaledSupplier, yScaledSupplier, zScaledSupplier;
  private final Supplier<Boolean> upSupplier, downSupplier, leftSupplier, rightSupplier;

  /** Creates a new DriveFieldOrientedHeadingSnapping. */
  public DriveFieldOrientedHeadingSnapping(
      Supplier<Double> xScaledSupplier,
      Supplier<Double> yScaledSupplier,
      Supplier<Double> zScaledSupplier,
      Supplier<Boolean> upSupplier,
      Supplier<Boolean> downSupplier,
      Supplier<Boolean> leftSupplier,
      Supplier<Boolean> rightSupplier) {
    this.controller = RobotContainer.driverController.getHID();
    this.drive = RobotContainer.drive;
    this.xScaledSupplier = xScaledSupplier;
    this.yScaledSupplier = yScaledSupplier;
    this.zScaledSupplier = zScaledSupplier;
    this.upSupplier = upSupplier;
    this.downSupplier = downSupplier;
    this.leftSupplier = leftSupplier;
    this.rightSupplier = rightSupplier;
    addRequirements(RobotContainer.drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.setHeadingCorrection(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double headingX = 0;
    double headingY = 0;
    if (upSupplier.get()) {
      headingY = -1;
    }
    if (rightSupplier.get()) {
      headingX = 1;
    }
    if (downSupplier.get()) {
      headingY = 1;
    }
    if (leftSupplier.get()) {
      headingX = -1;
    }

    ChassisSpeeds desiredSpeeds =
        drive.getTargetSpeeds(xScaledSupplier.get(), yScaledSupplier.get(), headingX, headingY);

    Translation2d translation = SwerveController.getTranslation2d(desiredSpeeds);
    if (headingX == 0 && headingY == 0 && Math.abs(zScaledSupplier.get()) > 0) {
      drive.driveFieldOriented(
          translation, (zScaledSupplier.get()) * Constants.Drive.MAX_ANGULAR_SPEED);
    } else {
      drive.driveFieldOriented(translation, desiredSpeeds.omegaRadiansPerSecond);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.setHeadingCorrection(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
