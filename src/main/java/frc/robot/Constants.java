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
    /** The channel on the PCM for the belt motor */
    public static final int BELT_MOTOR_ID = 0;

    /** The speed for the belt motor. */
    public static final double BELT_MOTOR_SPEED = .7;
  }

  /** Constants that are relating to the controllers. */
  public static class Controllers {
    /** Driver station port number for the drive controller. */
    public static final int DRIVER_CONTROLLER_PORT = 0;
  }

  public static class Drive {
    /** Empty translation to prevent creating 2 Translation2ds every time the drive train stops. */
    public static Translation2d EMPTY_TRANSLATION = new Translation2d();

    /** The max speed the robot can go */
    public static double MAX_SPEED = 1;

    /** The max speed the robot can rotate */
    public static double MAX_ANGULAR_SPEED = Math.PI / 2;
  }

  public static class Intake {
    /** Motor id of the Intake motor. */
    public static final int INTAKE_MOTOR_ID = 0;

    // It was me, DIO!
    /** DIO Port ID for the Intake limit switch. */
    public static final int INTAKE_LIMIT_SWITCH_DIO_PORT = 0;

    /** Speed we want to run the Intake at. */
    public static final double INTAKE_MOTOR_SPEED = 0.5;
  }

  public static class Limelight {
    public static class AimingLimelight {
      /* The number of degrees the limelight is mounted back from perfectly vertical */
      public static final double MOUNT_ANGLE = 0;

      /* The number of inches from the center of the Limelight lens to the floor */
      public static final double MOUNT_HEIGHT = 0;

      /* The number of inches from the retroreflective tape on the upper hub to the floor */
      /* This is 8'8", which is what the manual says the height from the floor to the top of the upper hub is. */
      public static final double UPPER_HUB_TAPE_HEIGHT = 0;

      /* How far in inches we want to be from the target when we shoot */
      public static final double DISTANCE_FROM_TARGET = 0;

      /* How hard to turn toward the target. Double between 0 and 1, standard way to drive a motor */
      public static final double STEER_STRENGTH = 0;

      /* How hard to drive toward the target. Same notation as above. */
      public static final double DRIVE_STRENGTH = 0;

      /* VERY BASIC speed limit to make sure we don't drive too fast towards the target. Will need to be changed when implementing PID. */
      public static final double SPEED_LIMIT = 0;

      /* When we're at or below this number of degrees from where we want to be, we'll consider the limelight's aim routine "done" */
      public static final double TURN_DONE_THRESHOLD = 0;

      /* When we're at or below this number of inches from the target distance, we'll consider the limelight's drive routine "done" */
      public static final double DISTANCE_DONE_THRESHOLD = 0;
    }
  }
}
