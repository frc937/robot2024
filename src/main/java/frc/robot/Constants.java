// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot;

import com.pathplanner.lib.util.PIDConstants;
import edu.wpi.first.math.geometry.Translation2d;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  /** Constants for the Mailbox system */
  public static class Mailbox {
    // It was me, DIO!
    /** DIO Port ID for the Mailbox limit switch. */
    public static final int MAILBOX_LIMIT_SWITCH_DIO_PORT = 0;
  }

  /** Constants for the Pneumatics system. */
  public static class MailboxPneumatics {
    /** The channel on the PCM for the forward direction on the left solenoid. */
    public static final int LEFT_SOLENOID_FORWARD_CHANNEL = 0;

    /** The channel on the PCM for the reverse direction on the left solenoid. */
    public static final int LEFT_SOLENOID_REVERSE_CHANNEL = 1;

    /** The channel on the PCM for the forward direction on the right solenoid. */
    public static final int RIGHT_SOLENOID_FORWARD_CHANNEL = 2;

    /** The channel on the PCM for the reverse direction on the right solenoid. */
    public static final int RIGHT_SOLENOID_REVERSE_CHANNEL = 3;
  }

  /** Constants for the Belts system. */
  public static class MailboxBelts {
    /** The CAN ID for the upper belt motor */
    public static final int UPPER_BELT_MOTOR_ID = 0;

    /** The CAN ID for the lower belt motor */
    public static final int LOWER_BELT_MOTOR_ID = 0;

    /** The speed for the belt motors. */
    public static final double BELT_MOTOR_SPEED = 1;

    /** Inversion state of the upper belt motor. */
    public static final boolean UPPER_BELT_MOTOR_INVERTED = false;

    /** Inversion state of the belts follower motor. */
    public static final boolean BELTS_FOLLOWER_INVERSE_STATE = false;
  }

  /** Constants that are relating to the controllers. */
  public static class Controllers {
    /** Driver station port number for the drive controller. */
    public static final int DRIVER_CONTROLLER_PORT = 0;

    /** Axis deadband for driver controller. */
    public static double DRIVER_CONTROLLER_DEADBAND = 0.1;
  }

  /** Constants for the Drivetrain */
  public static class Drive {
    /** Empty translation to prevent creating 2 Translation2ds every time the drive train stops. */
    public static Translation2d EMPTY_TRANSLATION = new Translation2d();

    /** The max speed the robot can go in m/s */
    public static double MAX_SPEED = 1;

    /** The max speed the robot can rotate */
    public static double MAX_ANGULAR_SPEED = Math.PI / 2;

    /** The distance from the center of the robot to any of the swerve modules. */
    public static double DISTANCE_ROBOT_CENTER_TO_SWERVE_MODULE = 0.3;

    /** The Translation Drive PID for the robot. */
    public static PIDConstants TRANSLATION_DRIVE_PID = new PIDConstants(1.0, 0.0, 0.0);

    /** The Rotation Drive PID for the robot. */
    public static PIDConstants ROTATION_DRIVE_PID = new PIDConstants(1.0, 0.0, 0.0);
  }

  /** Holds constants specfically related to autonomous. */
  public static class Auto {
    /** The number of meters per second that we want to move forward during the taxi auto */
    public static double TAXI_AUTO_METERS_PER_SECOND = 1.0;

    /** The number of seconds that we want to taxi for */
    public static double TAXI_AUTO_DURATION_SECONDS = 4.0;

    /** The amount of time we want/need to drive away from the amp in auto. */
    public static double DRIVE_AWAY_FROM_AMP_TIME = 2.0;

    /** The time we use to back away from the amp in auto. */
    public static double BACK_UP_FROM_AMP_TIME = 0.5;

    /** The amount of time that we want to run the fire note command in auto. */
    public static final double FIRE_NOTE_FOR_TIME = 4.0;
  }

  /** Constants for the Intake System */
  public static class Intake {
    /** Motor id of the Lower Intake motor. */
    public static final int LOWER_INTAKE_MOTOR_ID = 0;

    /** Motor id of the Upper Intake motor. */
    public static final int UPPER_INTAKE_MOTOR_ID = 0;

    /** Inversion state of the upper intake motor. */
    public static final boolean UPPER_INTAKE_MOTOR_INVERTED = false;

    // It was me, DIO!
    /** DIO Port ID for the Intake limit switch. */
    public static final int INTAKE_LIMIT_SWITCH_DIO_PORT = 0;

    /** Speed we want to run the Intake at. */
    public static final double INTAKE_MOTOR_SPEED = 1;

    /** Inversion state for the intake follower motor. */
    public static final boolean INTAKE_FOLLOWER_INVERSE_STATE = false;
  }

  /** Holds contstants for the Limelights. */
  public static class Limelight {
    /** Constants for aiming Limelight. */
    public static class AimingLimelight {
      /** Our name in the networktables for the limelight */
      public static final String LIMELIGHT_NAME = "limelight";

      /** The number of degrees the Limelight is mounted back from perfectly vertical */
      public static final double MOUNT_ANGLE = 0;

      /** The number of inches from the center of the Limelight lens to the floor */
      public static final double MOUNT_HEIGHT = 0;

      /** The height to the Amp Apriltag off the ground. */
      public static final double AMP_APRILTAG_HEIGHT = 0;

      /** How far in inches we want to be from the target when we shoot */
      public static final double DISTANCE_FROM_TARGET = 0;

      /**
       * How hard to turn toward the target. Double between 0 and 1, standard way to drive a motor
       */
      public static final double STEER_STRENGTH = 0;

      /** How hard to drive toward the target. Same notation as above. */
      public static final double DRIVE_STRENGTH = 0;

      /** VERY BASIC speed limit to make sure we don't drive too fast towards the target. */
      public static final double SPEED_LIMIT = 0;

      /**
       * When we're at or below this number of degrees from where we want to be, we'll consider the
       * Limelight's aim routine "done"
       */
      public static final double TURN_DONE_THRESHOLD = 0;

      /**
       * When we're at or below this number of inches from the target distance, we'll consider the
       * Limelight's drive routine "done"
       */
      public static final double DISTANCE_DONE_THRESHOLD = 0;

      /** Holds pipeline numbers for this Limelight. */
      public static class PipelineNumbers {
        /** The Limelight pipeline number for amp AprilTags. */
        public static final double AMP_PIPELINE_NUMBER = 6;
      }
    }

    /** Number of radians per second that we want to turn while seeking an unseen target. */
    public static double LIMELIGHT_SEEKING_RADIANS_PER_SECOND = Math.PI / 2;
  }
}
