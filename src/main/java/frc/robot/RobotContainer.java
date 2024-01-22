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

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.DriveRobotOriented;
import frc.robot.subsystems.Drive;

public class RobotContainer {

  /*
   * **************
   * * SUBSYSTEMS *
   * **************
   */
  private final Drive drive = new Drive();

  /*
   * ************
   * * COMMANDS *
   * ************
   */

  private final DriveRobotOriented driveRobotOriented = new DriveRobotOriented(drive);

  /*
   * *****************
   * * OTHER OBJECTS *
   * *****************
   */

  public static CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public RobotContainer() {
    configureBindings();

    drive.setDefaultCommand(driveRobotOriented);
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  public static double scaleAxis(double axis) {
    double deadbanded = MathUtil.applyDeadband(axis, 0.1);
    return Math.pow(deadbanded, 3);
  }

  public static double getControllerLeftXAxis() {
    return m_driverController.getLeftX();
  }

  public static double getScaledControllerLeftXAxis() {
    return scaleAxis(getControllerLeftXAxis());
  }

  public static double getControllerLeftYAxis() {
    return m_driverController.getLeftY();
  }

  public static double getScaledControllerLeftYAxis() {
    return scaleAxis(getControllerLeftYAxis());
  }

  public static double getControllerRightXAxis() {
    return m_driverController.getRightX();
  }

  public static double getScaledControllerRightXAxis() {
    return scaleAxis(getControllerRightXAxis());
  }

  public static double getControllerRightYAxis() {
    return m_driverController.getRightY();
  }

  public static double getScaledControllerRightYAxis() {
    return scaleAxis(getControllerRightYAxis());
  }
}
