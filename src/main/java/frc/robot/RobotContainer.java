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
import frc.robot.commands.DriveFieldOriented;
import frc.robot.commands.DriveRobotOriented;
import frc.robot.commands.EnterXMode;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

public class RobotContainer {

  /*
   * **************
   * * SUBSYSTEMS *
   * **************
   */

  /* We declare all subsystems as public static because we don't dependency inject because
   * injecting a dependency through six or seven commands in a chain of command groups would be
   * awful.
   */
  public static Drive drive = new Drive();
  public static Intake intake = new Intake();

  /*
   * ************
   * * COMMANDS *
   * ************
   */

  /* For now, we don't make commands public static, as there isn't really a reason to. */
  private final DriveRobotOriented driveRobotOriented = new DriveRobotOriented();
  private final DriveFieldOriented driveFieldOriented = new DriveFieldOriented();
  private final EnterXMode enterXMode = new EnterXMode();

  /*
   * ***********************
   * * OTHER INSTANCE VARS *
   * ***********************
   */

  /* The CommandXboxController instance must be static to allow the getter methods for its axes
   * to work.
   */
  public static CommandXboxController driverController =
      new CommandXboxController(Constants.Controllers.DRIVER_CONTROLLER_PORT);

  public RobotContainer() {
    configureBindings();

    drive.setDefaultCommand(driveRobotOriented);
  }

  private void configureBindings() {
    driverController.leftStick().toggleOnTrue(driveFieldOriented);

    driverController.x().onTrue(enterXMode);
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  public static double scaleAxis(double axis) {
    double deadbanded = MathUtil.applyDeadband(axis, 0.1);
    return Math.pow(deadbanded, 3);
  }

  public static double getControllerLeftXAxis() {
    return driverController.getLeftX();
  }

  public static double getScaledControllerLeftXAxis() {
    return scaleAxis(getControllerLeftXAxis());
  }

  public static double getControllerLeftYAxis() {
    return driverController.getLeftY();
  }

  public static double getScaledControllerLeftYAxis() {
    return scaleAxis(getControllerLeftYAxis());
  }

  public static double getControllerRightXAxis() {
    return driverController.getRightX();
  }

  public static double getScaledControllerRightXAxis() {
    return scaleAxis(getControllerRightXAxis());
  }

  public static double getControllerRightYAxis() {
    return driverController.getRightY();
  }

  public static double getScaledControllerRightYAxis() {
    return scaleAxis(getControllerRightYAxis());
  }
}
