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

import edu.wpi.first.wpilibj.RobotBase;

/* Hey! You probably don't want to put code here! Your might looking for RobotContainer. */
/** The robot's entrypoint. */
public final class Main {
  private Main() {}

  /**
   * The entrypoint for the robot.
   *
   * @param args The args passed from the RoboRIO
   */
  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
