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

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.AimWithLimelight;
import frc.robot.commands.DeployUrMom;
import frc.robot.commands.EnterXMode;
import frc.robot.commands.RunIntake;
import frc.robot.commands.drive.DriveFieldOriented;
import frc.robot.commands.drive.DriveRobotOriented;
import frc.robot.commands.mailbox.DeindexNote;
import frc.robot.commands.mailbox.DeployMailbox;
import frc.robot.commands.mailbox.DeployPneumatics;
import frc.robot.commands.mailbox.FireNoteRoutine;
import frc.robot.commands.mailbox.RunBelts;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.UrMom;
import frc.robot.subsystems.mailbox.Mailbox;
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
  /** Singleton instance of {@link Drive} for the whole robot. */
  public static Drive drive = new Drive();

  /** Singleton instance of {@link MailboxPneumatics} for the whole robot. */
  public static MailboxPneumatics mailboxPneumatics = new MailboxPneumatics();

  /** Singleton instance of {@link MailboxBelts} for the whole robot. */
  public static MailboxBelts mailboxBelts = new MailboxBelts();

  /** Singleton instance of {@link Mailbox} for the whole robot. */
  public static Mailbox mailbox = new Mailbox();

  /** Singleton instance of {@link Intake} for the whole robot. */
  public static Intake intake = new Intake();

  /** Singleton instance of {@link Limelight} for aiming. */
  public static Limelight limelight =
      new Limelight(Constants.Limelight.AimingLimelight.LIMELIGHT_NAME);

  /** Singleton instance of {@link UrMom} for the whole robot. */
  public static UrMom urMom = new UrMom();

  /*
   * ************
   * * COMMANDS *
   * ************
   */

  private DriveRobotOriented driveRobotOriented = new DriveRobotOriented();
  private DriveFieldOriented driveFieldOriented = new DriveFieldOriented();
  private EnterXMode enterXMode = new EnterXMode();
  private DeployPneumatics deployPneumatics = new DeployPneumatics();
  private RunBelts runBelts = new RunBelts();
  private DeployMailbox deployMailbox = new DeployMailbox();
  private DeindexNote deindexNote = new DeindexNote();
  private FireNoteRoutine fireNote = new FireNoteRoutine();
  private RunIntake runIntake = new RunIntake();
  private AimWithLimelight aimToAmp =
      new AimWithLimelight(
          limelight,
          Constants.Limelight.AimingLimelight.STEER_STRENGTH,
          Constants.Limelight.AimingLimelight.DISTANCE_FROM_TARGET,
          Constants.Limelight.AimingLimelight.MOUNT_HEIGHT,
          Constants.Limelight.AimingLimelight.MOUNT_ANGLE,
          Constants.Limelight.AimingLimelight.DRIVE_STRENGTH,
          Constants.Limelight.AimingLimelight.SPEED_LIMIT,
          Constants.Limelight.AimingLimelight.TURN_DONE_THRESHOLD,
          Constants.Limelight.AimingLimelight.DISTANCE_DONE_THRESHOLD,
          Constants.Limelight.AimingLimelight.AMP_APRILTAG_HEIGHT);
  private DeployUrMom deployUrMom = new DeployUrMom();

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

  /** Sendable Chooser for path planning autos. */
  private final SendableChooser<Command> autoChooser;

  /** Constructor for {@link RobotContainer} */
  public RobotContainer() {
    configureBindings();

    drive.setDefaultCommand(driveRobotOriented);

    /* Build an auto chooser. This will use Commands.none() as the default option. */
    autoChooser = AutoBuilder.buildAutoChooser();
    /* Another option that allows you to specify the default auto by its name */
    /* autoChooser = AutoBuilder.buildAutoChooser("My Default Auto"); */
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  private void configureBindings() {
    driverController.leftStick().toggleOnTrue(driveFieldOriented);

    driverController.x().onTrue(enterXMode);
    driverController.a().onTrue(runIntake);
    driverController.y().onTrue(fireNote);
  }

  /**
   * Gets the current autonomous command.
   *
   * @return The current autonomous command.
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
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
