// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import java.io.File;
import java.io.IOException;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;

/** The subsystem that represents the drivetrain. */
public class Drive extends SubsystemBase {
  private SwerveDrive drive;

  /** Creates a new Drive. */
  public Drive() {
    /* Try-catch because otherwise the compiler tries to anticipate runtime errors and throws a
     * compiletime error for a missing file even though it shouldn't
     */
    try {
      drive =
          new SwerveParser(new File(Filesystem.getDeployDirectory(), "swerve"))
              .createSwerveDrive(Units.feetToMeters(14.5));
    } catch (IOException e) {
      e.printStackTrace();
    }
    /* setting the motors to brake mode */
    drive.setMotorIdleMode(true);
  }

  /**
   * Drives the robot in robot-oriented mode.
   *
   * @param x Robot velocity left to right in m/s. Left is positive.
   * @param y Robot velocity forward and backward in m/s. Forward is positive.
   * @param z Robot angular velocity around the z-axis in radians per second. Counter-clockwise is
   *     positive.
   */
  public void driveRobotOriented(double x, double y, double z) {
    x = x * getMaximumSpeed();
    y = y * getMaximumSpeed();
    z = z * getMaximumAngularSpeed();
    Translation2d translation = new Translation2d(x, y);

    drive.drive(translation, z, false, false);
  }

  /**
   * Drives the robot in field-oriented mode.
   *
   * @param x Robot velocity left to right in m/s. Left is positive. Relative to the field.
   * @param y Robot velocity forward and backward in m/s. Toward the opposing alliance wall is
   *     positive.
   * @param z Robot angular velocity around the z-axis in radians per second. Counter-clockwise is
   *     positive.
   */
  public void driveFieldOriented(double x, double y, double z) {
    x = x * getMaximumSpeed();
    y = y * getMaximumSpeed();
    z = z * getMaximumAngularSpeed();
    Translation2d translation = new Translation2d(x, y);

    drive.drive(translation, z, true, false);
  }

  /** Stops all motors in the subsystem. */
  public void stop() {
    drive.drive(
        Constants.Drive.EMPTY_TRANSLATION, 0, false, false, Constants.Drive.EMPTY_TRANSLATION);
  }

  /** Points the wheels toward the inside and stops the wheels from moving in any direction. */
  public void enterXMode() {
    drive.lockPose();
  }

  private double getMaximumSpeed() {
    return Constants.Drive.MAX_SPEED;
  }

  private double getMaximumAngularSpeed() {
    return Constants.Drive.MAX_ANGULAR_SPEED;
  }

  /** Runs every scheduler run. */
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("FL Encoder", drive.getModules()[0].getAbsolutePosition());
    SmartDashboard.putNumber("FR Encoder", drive.getModules()[1].getAbsolutePosition());
    SmartDashboard.putNumber("BL Encoder", drive.getModules()[2].getAbsolutePosition());
    SmartDashboard.putNumber("BR Encoder", drive.getModules()[3].getAbsolutePosition());
  }
}
