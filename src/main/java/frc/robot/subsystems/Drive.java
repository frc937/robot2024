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

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import java.io.File;
import java.io.IOException;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;

/** The subsystem that represents the drivetrain. */
public class Drive extends SubsystemBase {
  private SwerveDrive drive;
  private ShuffleboardTab debugTab = Shuffleboard.getTab("Debug");
  private GenericEntry[] encoderEntries = {
    debugTab.add("FL Encoder", 0).getEntry(),
    debugTab.add("FR Encoder", 0).getEntry(),
    debugTab.add("BL Encoder", 0).getEntry(),
    debugTab.add("BR Encoder", 0).getEntry(),
  };
  private GenericEntry driveLabelEntry =
      Shuffleboard.getTab("Driver").add("Drive Mode", "").getEntry();

  /** Creates a new Drive. */
  public Drive() {
    /* Try-catch because otherwise the compiler tries to anticipate runtime errors and throws a
     * comp-time error for a missing file even though it shouldn't
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

    /* Configure AutoBuilder last */
    AutoBuilder.configureHolonomic(
        this.drive::getPose, /* Robot pose supplier */
        this.drive
            ::resetOdometry, /* Method to reset odometry (will be called if your auto has a starting pose) */
        this.drive::getRobotVelocity, /* ChassisSpeeds supplier. MUST BE ROBOT RELATIVE */
        this.drive
            ::setChassisSpeeds, /* Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds */
        new HolonomicPathFollowerConfig(
            /* HolonomicPathFollowerConfig, this should likely live in your Constants class */
            Constants.Drive.TRANSLATION_DRIVE_PID, /* Translation PID constants */
            Constants.Drive.ROTATION_DRIVE_PID, /* Rotation PID constants */
            getMaximumSpeed(), /* Max module speed, in m/s */
            Constants.Drive
                .DISTANCE_ROBOT_CENTER_TO_SWERVE_MODULE, /* Drive base radius in meters. Distance from robot center to furthest module. */
            new ReplanningConfig() /* Default path replanning config. See the API for the options here */),
        () -> {
          /* Boolean supplier that controls when the path will be mirrored for the red alliance */
          /* This will flip the path being followed to the red side of the field. */
          /* THE ORIGIN WILL REMAIN ON THE BLUE SIDE */

          var alliance = DriverStation.getAlliance();
          if (alliance.isPresent()) {
            return alliance.get() == DriverStation.Alliance.Red;
          }
          return false;
        },
        this /* Reference to this subsystem to set requirements */);
  }

  /**
   * Drives the robot in either field relative or robot relative.
   *
   * @param translation {@link Translation2d} that represents the commanded robot velocities on the
   *     x and y axes. Front-left positive. Relative to the field.
   * @param z Robot angular velocity around the z-axis in radians per second. Counter-clockwise is
   *     positive.
   * @param isFieldOriented If the robot should either drive field oriented or robot oriented.
   */
  public void driveRobot(Translation2d translation, double z, boolean isFieldOriented) {
    drive.drive(translation, z, isFieldOriented, false);
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
   * Zeroes the NavX gyro. Mostly used for resetting the angle for field-oriented drive (for now).
   *
   * <p>Somewhat notably, this will also reset odometry to the same position it's currently at, but
   * facing towards zero.
   */
  public void zeroGyro() {
    drive.zeroGyro();
  }

  /**
   * Gets the maximum speed the robot chassis can achieve in m/s.
   *
   * @return Maximum speed the robot chassis can achieve in m/s.
   */
  public double getMaximumSpeed() {
    return Math.min(drive.getMaximumVelocity(), Constants.Drive.MAX_SPEED);
  }

  /**
   * Gets the maximum angular speed the robot chassis can achieve in rad/s.
   *
   * @return Maximum angular speed the robot chassis can achieve in rad/s.
   */
  public double getMaximumAngularSpeed() {
    return Math.min(drive.getMaximumAngularVelocity(), Constants.Drive.MAX_ANGULAR_SPEED);
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
        getMaximumSpeed());
  }

  /**
   * Sets the drive mode in SmartDashboard.
   *
   * <p><strong> THIS DOES NOT SET THE ROBOT'S DRIVE MODE</strong>
   *
   * @param driveMode The mode to display in SmartDashboard.
   */
  public void setDriveMode(String driveMode) {
    driveLabelEntry.setString(driveMode);
  }

  /**
   * Returns the heading of the robot.
   *
   * @return The heading of the robot.
   */
  public Rotation2d getHeading() {
    return drive.getPose().getRotation();
  }

  /** Runs every scheduler run. */
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    encoderEntries[0].setDouble(drive.getModules()[0].getAbsolutePosition()); // FL
    encoderEntries[1].setDouble(drive.getModules()[1].getAbsolutePosition()); // FR
    encoderEntries[2].setDouble(drive.getModules()[2].getAbsolutePosition()); // BL
    encoderEntries[3].setDouble(drive.getModules()[3].getAbsolutePosition()); // BR
  }
}
