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
import com.revrobotics.CANSparkBase.IdleMode;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.util.Color;

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
  public static final class Mailbox {
    // It was me, DIO!
    /** DIO Port ID for the Mailbox limit switch. */
    public static final int MAILBOX_LIMIT_SWITCH_DIO_PORT = 0;

    /** The delay between raising the mailbox and firing the note. In seconds. */
    public static final double FIRE_NOTE_DELAY_TIME = 1.25;
  }

  /** Constants for the Pneumatics system. */
  public static final class MailboxPneumatics {
    /** The channel on the PCM for the forward direction on the left solenoid. */
    public static final int LEFT_SOLENOID_FORWARD_CHANNEL = 5;

    /** The channel on the PCM for the reverse direction on the left solenoid. */
    public static final int LEFT_SOLENOID_REVERSE_CHANNEL = 4;

    /** The channel on the PCM for the forward direction on the right solenoid. */
    public static final int RIGHT_SOLENOID_FORWARD_CHANNEL = 7;

    /** The channel on the PCM for the reverse direction on the right solenoid. */
    public static final int RIGHT_SOLENOID_REVERSE_CHANNEL = 6;
  }

  /** Constants for the Belts system. */
  public static final class MailboxBelts {
    /** The CAN ID for the upper belt motor */
    public static final int UPPER_BELT_MOTOR_ID = 11;

    /** The CAN ID for the lower belt motor */
    public static final int LOWER_BELT_MOTOR_ID = 12;

    /** The speed for the belt motors. */
    public static final double BELT_MOTOR_SPEED = 1;

    /** Inversion state of the upper belt motor. */
    public static final boolean UPPER_BELT_MOTOR_INVERTED = true;

    /** Inversion state of the belts follower motor. */
    public static final boolean BELTS_FOLLOWER_INVERSE_STATE = false;

    /** Current limit (in amps) for the belt motor(s) */
    public static final int BELT_MOTOR_CURRENT_LIMIT = 40;

    /** Idle mode for the belt motors. (Either brake or coast) */
    public static final IdleMode BELTS_IDLE_MODE = IdleMode.kBrake;
  }

  /** Constants that are relating to the controllers. */
  public static final class Controllers {
    /** Driver station port number for the pilot controller */
    public static final int PILOT_CONTROLLER_PORT = 0;

    /** Driver station port number for the operator controller */
    public static final int OPERATOR_CONTROLLER_PORT = 2;

    /** Axis deadband for driver controller. */
    public static final double DRIVER_CONTROLLER_DEADBAND = 0.1;
  }

  /** Constants for the Drivetrain */
  public static final class Drive {

    /** Empty translation to prevent creating 2 Translation2ds every time the drive train stops. */
    public static final Translation2d EMPTY_TRANSLATION = new Translation2d();

    /** The max speed the robot can go in m/s */
    public static final double MAX_SPEED = 100;

    /** The max speed the robot can rotate */
    public static final double MAX_ANGULAR_SPEED = 100;

    /** The distance from the center of the robot to any of the swerve modules. */
    public static final double DISTANCE_ROBOT_CENTER_TO_SWERVE_MODULE = 0.3;

    /** The max speed for the robot when not sprinting (in m/s) */
    public static final double MAX_NORMAL_SPEED = 2;

    /** The max angular speed (in radians) for the robot when not sprinting */
    public static final double MAX_NORMAL_ANGULAR_SPEED = 2 * Math.PI;

    /** The Translation Drive PID for the robot. */
    public static final PIDConstants TRANSLATION_DRIVE_PID = new PIDConstants(1.0, 0.0, 0.0);

    /** The Rotation Drive PID for the robot. */
    public static final PIDConstants ROTATION_DRIVE_PID = new PIDConstants(1.0, 0.0, 0.0);
  }

  /** Holds constants specifically related to autonomous. */
  public static class Auto {

    /** The number of meters per second that we want to move forward during the taxi auto */
    public static final double TAXI_AUTO_METERS_PER_SECOND = 1.0;

    /** The number of seconds that we want to taxi for */
    public static final double TAXI_AUTO_DURATION_SECONDS = 2.0;

    /** The amount of time we want/need to drive away from the amp in auto. */
    public static final double DRIVE_AWAY_FROM_AMP_TIME = 2.0;

    /** The time we use to back away from the amp in auto. */
    public static final double BACK_UP_FROM_AMP_TIME = 0.5;

    /** The amount of time that we want to run the fire note command in auto. */
    public static final double FIRE_NOTE_FOR_TIME = 4.0;
  }

  /** Constants for the Intake System */
  public static final class Intake {
    /** Motor id of the Lower Intake motor. */
    public static final int LOWER_INTAKE_MOTOR_ID = 1;

    /** Motor id of the Upper Intake motor. */
    public static final int UPPER_INTAKE_MOTOR_ID = 2;

    /** Inversion state of the upper intake motor. */
    public static final boolean UPPER_INTAKE_MOTOR_INVERTED = false;

    // It was me, DIO!
    /** DIO Port ID for the Intake limit switch. */
    public static final int INTAKE_LIMIT_SWITCH_DIO_PORT = 1;

    /** Speed we want to run the Intake at. */
    public static final double INTAKE_MOTOR_SPEED = .6;

    /** Inversion state for the intake follower motor. */
    public static final boolean INTAKE_FOLLOWER_INVERSE_STATE = false;

    /** Current limit (in amps) for the intake motor(s) */
    public static final int INTAKE_MOTOR_CURRENT_LIMIT = 40;

    /** Idle mode for the intake motors. (Either brake or coast) */
    public static final IdleMode INTAKE_MOTOR_IDLE_MODE = IdleMode.kBrake;
  }

  /** Holds constants for the Limelights. */
  public static final class Limelight {
    /** Constants for aiming Limelight. */
    public static final class AimingLimelight {
      /** Our name in the network tables for the limelight */
      public static final String LIMELIGHT_NAME = "limelight";

      /** The number of degrees the Limelight is mounted back from perfectly vertical */
      public static final double MOUNT_ANGLE = 50;

      /** The number of inches from the center of the Limelight lens to the floor */
      public static final double MOUNT_HEIGHT = 10.0625;

      /** The height to the Amp Apriltag off the ground. */
      public static final double AMP_APRILTAG_HEIGHT = 53.13;

      /** How far in inches we want to be from the target when we shoot */
      public static final double DISTANCE_FROM_TARGET = 14;

      /**
       * How hard to turn toward the target. Double between 0 and 1, standard way to drive a motor
       */
      public static final double STEER_STRENGTH = 0.01;

      /** How hard to drive toward the target. Same notation as above. */
      public static final double DRIVE_STRENGTH = 0.01;

      /** VERY BASIC speed limit to make sure we don't drive too fast towards the target. */
      public static final double SPEED_LIMIT = 0.2;

      /**
       * When we're at or below this number of degrees from where we want to be, we'll consider the
       * Limelight's aim routine "done"
       */
      public static final double TURN_DONE_THRESHOLD = 1;

      /**
       * When we're at or below this number of inches from the target distance, we'll consider the
       * Limelight's drive routine "done"
       */
      public static final double DISTANCE_DONE_THRESHOLD = 4;

      /** Holds pipeline numbers for this Limelight. */
      public static final class PipelineNumbers {
        /** The Limelight pipeline number for amp AprilTags. */
        public static final double AMP_PIPELINE_NUMBER = 1;
      }
    }

    /** Number of radians per second that we want to turn while seeking an unseen target. */
    public static final double LIMELIGHT_SEEKING_RADIANS_PER_SECOND = Math.PI / 2;
  }

  /** The constants for the climber. */
  public static final class Climber {
    /** The motor id for the climber */
    public static final int CLIMBER_MOTOR_ID = 13;

    /** The speed the climber will climb. */
    public static final double CLIMBER_MOTOR_SPEED = 0.5;

    /** Whether or not the climber is inverted. */
    public static final boolean CLIMBER_INVERTED = true;

    /** Idle mode for the climber motors. (Either brake or coast) */
    public static final IdleMode CLIMBER_MOTOR_IDLE_MODE = IdleMode.kBrake;
  }

  /** The constants for the USB cameras */
  public static final class Camera {
    /** The camera id for the intake camera. */
    public static final int INTAKE_CAMERA_ID = 0;
  }

  /** The constants for the light strips */
  public static class LightStrips {
    /** The PWM ID for the underglow light strip */
    public static final int UNDERGLOW_PWM_ID = 0;

    /** The count of LEDs for the underglow light strip. */
    public static final int UNDERGLOW_LED_COUNT = 150;

    /** The speed of the fade animation. [0, 1] */
    public static final double STRIP_FADE_AMOUNT = 0.1;

    public static final class Colors {
      /** The color for the lights when the robot is disabled. */
      public static final Color DISABLED_COLOR = Color.fromHSV(15, 255, 100);

      public static final Color ENABLE_COLOR_NO_ALLIANCE = Color.fromHSV(137, 255, 200);

      public static final Color ENABLE_COLOR_BLUE_ALLIANCE = Color.fromHSV(120, 255, 200);
      public static final Color ENABLE_COLOR_RED_ALLIANCE = Color.fromHSV(0, 255, 200);
    }
  }
}
