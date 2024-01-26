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
import frc.robot.commands.DeployPneumatics;
import frc.robot.commands.DriveFieldOriented;
import frc.robot.commands.DriveRobotOriented;
import frc.robot.commands.EnterXMode;
import frc.robot.commands.RunBelts;
import frc.robot.commands.RunIntake;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.mailbox.MailboxBelts;
import frc.robot.subsystems.mailbox.MailboxPneumatics;

/** Singleton class that contains all the robot's subsystems, commands, and button bindings. */
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
  /** Singleton instance of {@link Drive} */
  public static Drive drive = new Drive();

  /** mailboxPneumatics object for the MailboxPneumatics class */
  public static MailboxPneumatics mailboxPneumatics = new MailboxPneumatics();

  /** mailboxBelts object for the MailboxBelts class */
  public static MailboxBelts mailboxBelts = new MailboxBelts();

  public static Intake intake = new Intake();

  /*
   * ************
   * * COMMANDS *
   * ************
   */

  public static DriveRobotOriented driveRobotOriented = new DriveRobotOriented();
  public static DriveFieldOriented driveFieldOriented = new DriveFieldOriented();
  public static EnterXMode enterXMode = new EnterXMode();
  public static DeployPneumatics deployPneumatics = new DeployPneumatics();
  public static RunBelts runBelts = new RunBelts();
  public static RunIntake runIntake = new RunIntake();

  /*
   * ***********************
   * * OTHER INSTANCE VARS *
   * ***********************
   */

  /* The CommandXboxController instance must be static to allow the getter methods for its axes
   * to work.
   */
  /** Xbox controller for the driver. */
  public static CommandXboxController driverController =
      new CommandXboxController(Constants.Controllers.DRIVER_CONTROLLER_PORT);

  /** Constructor for {@link RobotContainer} */
  public RobotContainer() {
    configureBindings();

    drive.setDefaultCommand(driveRobotOriented);
  }

  private void configureBindings() {
    driverController.leftStick().toggleOnTrue(driveFieldOriented);

    driverController.x().onTrue(enterXMode);
    driverController.a().onTrue(runIntake);
  }

  /**
   * Gets the current autonomous command.
   *
   * @return The current autonomous command.
   */
  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  private static double scaleAxis(double axis) {
    double deadbanded = MathUtil.applyDeadband(axis, 0.1);
    return Math.pow(deadbanded, 3);
  }

  /**
   * Gets x-axis of left stick of driver controller.
   *
   * @return x-axis of left stick of driver controller.
   */
  public static double getControllerLeftXAxis() {
    return driverController.getLeftX();
  }

  /**
   * Gets scaled x-axis of left stick of driver controller.
   *
   * @return scaled x-axis of left stick of driver controller.
   */
  public static double getScaledControllerLeftXAxis() {
    return scaleAxis(getControllerLeftXAxis());
  }

  /**
   * Gets y-axis of left stick of driver controller.
   *
   * @return y-axis of left stick of driver controller.
   */
  public static double getControllerLeftYAxis() {
    return driverController.getLeftY();
  }

  /**
   * Gets scaled y-axis of left stick of driver controller.
   *
   * @return scaled y-axis of left stick of driver controller.
   */
  public static double getScaledControllerLeftYAxis() {
    return scaleAxis(getControllerLeftYAxis());
  }

  /**
   * Gets x-axis of right stick of driver controller.
   *
   * @return x-axis of right stick of driver controller.
   */
  public static double getControllerRightXAxis() {
    return driverController.getRightX();
  }

  /**
   * Gets scaled x-axis of right stick of driver controller.
   *
   * @return scaled x-axis of right stick of driver controller.
   */
  public static double getScaledControllerRightXAxis() {
    return scaleAxis(getControllerRightXAxis());
  }

  /**
   * Gets y-axis of right stick of driver controller.
   *
   * @return y-axis of right stick of driver controller.
   */
  public static double getControllerRightYAxis() {
    return driverController.getRightY();
  }

  /**
   * Gets scaled y-axis of right stick of driver controller.
   *
   * @return scaled y-axis of right stick of driver controller.
   */
  public static double getScaledControllerRightYAxis() {
    return scaleAxis(getControllerRightYAxis());
  }
}
