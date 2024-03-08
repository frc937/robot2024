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
import edu.wpi.first.wpilibj2.command.Command;
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
  private final Supplier<Double> xSupplier, ySupplier, zSupplier;
  private final Supplier<Boolean> upSupplier, downSupplier, leftSupplier, rightSupplier;

  /**
   * Drives the robot field oriented with heading snapping. All values are suppliers to make the
   * command more versatile.
   *
   * @param xSupplier The joystick value for the X axis. [-1, 1] left positive.
   * @param ySupplier The joystick value for the Y axis. [-1, 1] back positive.
   * @param zSupplier The joystick value for the Z axis. [-1, 1] counterclockwise positive.
   * @param upSupplier If true, the robot heading will be set to move towards the opposing alliance
   *     wall
   * @param downSupplier If true, the robot heading will be set to move towards our alliance wall
   * @param leftSupplier If true, the robot heading will be set to move towards the left
   * @param rightSupplier If true, the robot heading will be set to move towards the right
   */
  public DriveFieldOrientedHeadingSnapping(
      Supplier<Double> xSupplier,
      Supplier<Double> ySupplier,
      Supplier<Double> zSupplier,
      Supplier<Boolean> upSupplier,
      Supplier<Boolean> downSupplier,
      Supplier<Boolean> leftSupplier,
      Supplier<Boolean> rightSupplier) {
    this.drive = RobotContainer.drive;
    this.xSupplier = xSupplier;
    this.ySupplier = ySupplier;
    this.zSupplier = zSupplier;
    this.upSupplier = upSupplier;
    this.downSupplier = downSupplier;
    this.leftSupplier = leftSupplier;
    this.rightSupplier = rightSupplier;
    addRequirements(RobotContainer.drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.setDriveMode("Field Oriented (Heading Snapping)");
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
        drive.getTargetSpeeds(xSupplier.get(), ySupplier.get(), headingX, headingY);

    Translation2d translation = SwerveController.getTranslation2d(desiredSpeeds);
    if (headingX == 0 && headingY == 0 && Math.abs(zSupplier.get()) > 0) {
      drive.driveRobot(translation, zSupplier.get() * drive.getMaximumAngularSpeed(), true);
    } else {
      drive.driveRobot(translation, desiredSpeeds.omegaRadiansPerSecond, true);
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
