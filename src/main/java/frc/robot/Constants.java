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
}
