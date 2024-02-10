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
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import java.io.File;
import java.io.IOException;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

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

    // Configure AutoBuilder last
    AutoBuilder.configureHolonomic(
      this.drive::getPose, // Robot pose supplier
      this.drive::resetOdometry, // Method to reset odometry (will be called if your auto has a starting pose)
      this.drive::getRobotVelocity, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
      this.drive::setChassisSpeeds, // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds
      new HolonomicPathFollowerConfig( // HolonomicPathFollowerConfig, this should likely live in your Constants class
              new PIDConstants(1.0, 0.0, 0.0), // Translation PID constants
              new PIDConstants(1.0, 0.0, 0.0), // Rotation PID constants
              Constants.Drive.MAX_SPEED, // Max module speed, in m/s
              Constants.Drive.MAX_ANGULAR_SPEED, // Drive base radius in meters. Distance from robot center to furthest module.
              new ReplanningConfig() // Default path replanning config. See the API for the options here
      ),
      () -> {
        // Boolean supplier that controls when the path will be mirrored for the red alliance
        // This will flip the path being followed to the red side of the field.
        // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

        var alliance = DriverStation.getAlliance();
        if (alliance.isPresent()) {
          return alliance.get() == DriverStation.Alliance.Red;
        }
        return false;
      },
      this // Reference to this subsystem to set requirements
    );
  }

  /**
   * Drives the robot in robot-oriented mode.
   *
   * @param translation {@link Translation2d} that represents the commanded robot velocities on the
   *     x and y axes. Front-left postitive.
   * @param z Robot angular velocity around the z-axis in radians per second. Counter-clockwise is
   *     positive.
   */
  public void driveRobotOriented(Translation2d translation, double z) {
    drive.drive(translation, z, false, false);
  }

  /**
   * Drives the robot in field-oriented mode.
   *
   * @param translation {@link Translation2d} that represents the commanded robot velocities on the
   *     x and y axes. Front-left postitive. Reletive to the field.
   * @param z Robot angular velocity around the z-axis in radians per second. Counter-clockwise is
   *     positive.
   */
  public void driveFieldOriented(Translation2d translation, double z) {
    drive.drive(translation, z, true, false);
  }

  /** Stops all motors in the subsystem. */
  public void stop() {
    drive.drive(Constants.Drive.EMPTY_TRANSLATION, 0, false, false);
  }

  /** Points the wheels toward the inside and stops the wheels from moving in any direction. */
  public void enterXMode() {
    drive.lockPose();
  }

  /**
   * Set the heading correction capabilities of YAGSL. Should only be enabled when heading
   * correction capabilities are in use
   *
   * @param state SwerveDrive.headingCorrection state
   */
  public void setHeadingCorrection(boolean state) {
    drive.setHeadingCorrection(state);
  }

  /**
   * Takes [-1, 1] joystick-like inputs and converts them to a {@link ChassisSpeeds} object that
   * represents the commanded robot velocities
   *
   * @param translationX joystick input for the left to right axis. [-1, 1], left is positive.
   * @param translationY joystick input for the forward to backward axis. [-1, 1], forward is
   *     positive.
   * @param headingX x component of the cartesian angle of the robot's heading
   * @param headingY y component of the cartesian angle of the robot's heading
   * @return {@link ChassisSpeeds} object that represents the commanded robot velocities
   */
  public ChassisSpeeds getTargetSpeeds(
      double translationX, double translationY, double headingX, double headingY) {
    return drive.swerveController.getTargetSpeeds(
        translationX,
        translationY,
        headingX,
        headingY,
        drive.getPose().getRotation().getRadians(),
        Constants.Drive.MAX_SPEED);
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
