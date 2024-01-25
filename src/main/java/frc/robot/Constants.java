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
  public static class MailboxBelt {
    /** The channel on the PCM for the belt motor */
    public static final int BELT_MOTOR_ID = 0;
    /** The speed for the belt motor. */
    public static final double BELT_MOTOR_SPEED = .7;
  }
  /** Constants for the Controllers system. */
  public static class Controllers {
    /** the channel on the computer for the driver controller. */
    public static final int DRIVER_CONTROLLER_PORT = 0;
  }
}
