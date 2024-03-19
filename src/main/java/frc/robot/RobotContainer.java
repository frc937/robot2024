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
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Controllers.ControllerAxis;
import frc.robot.Controllers.Keymap;
import frc.robot.commands.AimAndFireRoutine;
import frc.robot.commands.AimWithLimelight;
import frc.robot.commands.ClearPDPStickyFaults;
import frc.robot.commands.ClimbDown;
import frc.robot.commands.ClimbUp;
import frc.robot.commands.DeployUrMom;
import frc.robot.commands.EnterXMode;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunIntakeReverse;
import frc.robot.commands.auto.MoveAwayFromAmp;
import frc.robot.commands.auto.OnePieceAuto;
import frc.robot.commands.auto.TaxiAuto;
import frc.robot.commands.drive.DriveFieldOrientedHeadingSnapping;
import frc.robot.commands.drive.DriveRobot;
import frc.robot.commands.drive.ZeroGyro;
import frc.robot.commands.mailbox.DeindexNote;
import frc.robot.commands.mailbox.DeployMailbox;
import frc.robot.commands.mailbox.DeployPneumatics;
import frc.robot.commands.mailbox.FireNoteRoutine;
import frc.robot.commands.mailbox.FireNoteRoutineNoLimitSwitch;
import frc.robot.commands.mailbox.RunBelts;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.PDP;
import frc.robot.subsystems.UrMom;
import frc.robot.subsystems.mailbox.Mailbox;
import frc.robot.subsystems.mailbox.MailboxBelts;
import frc.robot.subsystems.mailbox.MailboxPneumatics;

@SuppressWarnings("unused")
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

  /** Singleton instance of {@link Climber} for the whole robot. */
  public static Climber climber = new Climber();

  /** Singleton instance of {@link PDP} for the whole robot. */
  public static PDP pdp = new PDP();

  /* TODO: JAVADOC */
  public static Camera intakeCamera = new Camera(0);

  /*
   * ************
   * * COMMANDS *
   * ************
   */

  /** Singleton instance of robot oriented {@link DriveRobot} for the whole robot. */
  public static DriveRobot driveRobotOriented =
      new DriveRobot(
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.LeftY, true),
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.LeftX, true),
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.RightX, true),
          false,
          Constants.Drive.MAX_NORMAL_SPEED,
          Constants.Drive.MAX_NORMAL_ANGULAR_SPEED);

  /** Singleton instance of field oriented {@link DriveRobot} for the whole robot. */
  public static DriveRobot driveFieldOriented =
      new DriveRobot(
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.LeftY, true),
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.LeftX, true),
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.RightX, true),
          true,
          Constants.Drive.MAX_NORMAL_SPEED,
          Constants.Drive.MAX_NORMAL_ANGULAR_SPEED);

  /** Singleton instance of robot oriented sprint {@link DriveRobot} for the whole robot. */
  public static DriveRobot driveRobotOrientedSprint =
      new DriveRobot(
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.LeftY, true),
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.LeftX, true),
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.RightX, true),
          false);

  /** Singleton instance of field oriented sprint {@link DriveRobot} for the whole robot. */
  public static DriveRobot driveFieldOrientedSprint =
      new DriveRobot(
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.LeftY, true),
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.LeftX, true),
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.RightX, true),
          true);

  /** Singleton instance of {@link DriveFieldOrientedHeadingSnapping} for the whole robot. */
  public static DriveFieldOrientedHeadingSnapping driveFieldOrientedHeadingSnapping =
      new DriveFieldOrientedHeadingSnapping(
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.LeftY, true),
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.LeftX, true),
          Controllers.getControllerAxisSupplier(
              Controllers.pilotController, ControllerAxis.RightX, true),
          Controllers.headingSnappingUpSupplier,
          Controllers.headingSnappingDownSupplier,
          Controllers.headingSnappingLeftSupplier,
          Controllers.headingSnappingRightSupplier);

  /** Singleton instance of {@link EnterXMode} for the whole robot. */
  public static EnterXMode enterXMode = new EnterXMode();

  /** Singleton instance of {@link DeployPneumatics} for the whole robot. */
  public static DeployPneumatics deployPneumatics = new DeployPneumatics();

  /** Singleton instance of {@link RunBelts} for the whole robot. */
  public static RunBelts runBelts = new RunBelts();

  /** Singleton instance of {@link DeployMailbox} for the whole robot. */
  public static DeployMailbox deployMailbox = new DeployMailbox();

  /** Singleton instance of {@link DeindexNote} for the whole robot. */
  public static DeindexNote deindexNote = new DeindexNote();

  /** Singleton instance of {@link FireNoteRoutine} for the whole robot. */
  public static FireNoteRoutine fireNote = new FireNoteRoutine();

  /** Singleton instance of {@link FireNoteRoutineNoLimitSwitch} for the whole robot. */
  public static FireNoteRoutineNoLimitSwitch fireNoteRoutineNoLimitSwitch =
      new FireNoteRoutineNoLimitSwitch();

  /** Singleton instance of {@link RunIntakeReverse} for the whole robot. */
  public static RunIntakeReverse runIntakeReverse = new RunIntakeReverse();

  /** Singleton instance of {@link RunIntake} for the whole robot. */
  public static RunIntake runIntake = new RunIntake();

  /** Singleton instance of {@link AimWithLimelight} for the whole robot. */
  public static AimWithLimelight aimToAmp =
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
          Constants.Limelight.AimingLimelight.AMP_APRILTAG_HEIGHT,
          Constants.Limelight.AimingLimelight.PipelineNumbers.AMP_PIPELINE_NUMBER);

  /** Singleton instance of {@link AimAndFireRoutine} for the whole robot. */
  public static AimAndFireRoutine aimAndFire = new AimAndFireRoutine();

  /** Singleton instance of {@link DeployUrMom} for the whole robot. */
  public static DeployUrMom deployUrMom = new DeployUrMom();

  /** Singleton instance of {@link ClimbUp} for the whole robot. */
  public static ClimbUp climbUp = new ClimbUp();

  /** Singleton instance of {@link ClimbDown} for the whole robot. */
  public static ClimbDown climbDown = new ClimbDown();

  /* Autos */
  /** Singleton instance of {@link MoveAwayFromAmp} for the whole robot. */
  public static MoveAwayFromAmp moveAwayFromAmp = new MoveAwayFromAmp();

  /** Singleton instance of {@link OnePieceAuto} for the whole robot. */
  public static OnePieceAuto onePieceAuto = new OnePieceAuto();

  /** Singleton instance of {@link TaxiAuto} for the whole robot. */
  public static TaxiAuto taxiAuto = new TaxiAuto();

  /** Singleton instance of {@link ClearPDPStickyFaults} for the whole robot. */
  public static ClearPDPStickyFaults clearPDPStickyFaults = new ClearPDPStickyFaults();

  /** Singleton instance of {@link ZeroGyro} for the whole robot. */
  public static ZeroGyro zeroGyro = new ZeroGyro();

  /*
   * ***********************
   * * OTHER INSTANCE VARS *
   * ***********************
   */

  /** Sendable Chooser for autos. */
  private SendableChooser<Command> autoChooser;

  private SendableChooser<Keymap> keymapChooser;

  /** Constructor for {@link RobotContainer} */
  public RobotContainer() {
    configureBindings();
    configureAuto();
    Shuffleboard.getTab("Driver").add("Clear PDP sticky faults", clearPDPStickyFaults);
    Shuffleboard.getTab("Driver").add("Zero Gyro", zeroGyro);

    switch (Constants.Drive.currentDrivePerspective) {
      case RobotOriented:
        drive.setDefaultCommand(driveRobotOriented);
        break;
      case FieldOriented:
        drive.setDefaultCommand(driveFieldOriented);
        break;
      default:
        throw new IllegalStateException();
    }
  }

  private void configureAuto() {

    NamedCommands.registerCommand("runIntake", runIntake);
    NamedCommands.registerCommand("aimAndFire", aimAndFire);

    /* Build an auto chooser. This will use Commands.none() as the default option. */
    autoChooser = AutoBuilder.buildAutoChooser();
    /* Another option that allows you to specify the default auto by its name */
    /* autoChooser = AutoBuilder.buildAutoChooser("My Default Auto"); */

    /* This is where you put auto commands. Call autoChooser.addOption() to add autos. */
    autoChooser.addOption("Taxi", taxiAuto);

    Shuffleboard.getTab("Driver").add("Choose Auto Routine", autoChooser);
  }

  private void configureBindings() {
    keymapChooser = new SendableChooser<>();

    keymapChooser.setDefaultOption("Default", Keymap.Default);
    keymapChooser.addOption("Operatorless", Keymap.Operatorless);
    keymapChooser.addOption("Original", Keymap.Original);

    /* TODO: Test and make sure this actually works to change the keymap over whenever we switch keymaps in the chooser */
    keymapChooser.onChange(Controllers::configureKeybinds);

    Shuffleboard.getTab("Driver").add("Select Keymap", keymapChooser);

    Controllers.configureKeybinds(keymapChooser.getSelected());
  }

  /**
   * Gets the current autonomous command.
   *
   * @return The current autonomous command.
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
