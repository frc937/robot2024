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
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Controllers.ControllerAxis;
import frc.robot.Controllers.Keymap;
import frc.robot.commands.AimAndFireRoutine;
import frc.robot.commands.AimWithLimelight;
import frc.robot.commands.ClearPDPStickyFaults;
import frc.robot.commands.ClimbDown;
import frc.robot.commands.ClimbUp;
import frc.robot.commands.ControlCompressor;
import frc.robot.commands.DeployUrMom;
import frc.robot.commands.EnterXMode;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunIntakeReverse;
import frc.robot.commands.StartCamera;
import frc.robot.commands.auto.DelayedTaxiAuto;
import frc.robot.commands.auto.DriveToCenterAuto;
import frc.robot.commands.auto.MoveAwayFromAmp;
import frc.robot.commands.auto.OnePieceAuto;
import frc.robot.commands.auto.OnePieceAutoButItWorksISwear;
import frc.robot.commands.auto.PickUpFromCenterAuto;
import frc.robot.commands.auto.TaxiAuto;
import frc.robot.commands.auto.TaxiLongAuto;
import frc.robot.commands.drive.DriveFieldOrientedHeadingSnapping;
import frc.robot.commands.drive.DriveRobot;
import frc.robot.commands.drive.SetDrivePerspectiveFieldOriented;
import frc.robot.commands.drive.SetDrivePerspectiveFieldOrientedHeadingSnapping;
import frc.robot.commands.drive.SetDrivePerspectiveRobotOriented;
import frc.robot.commands.drive.ZeroGyro;
import frc.robot.commands.lightstrip.DisabledLight;
import frc.robot.commands.lightstrip.EnabledLight;
import frc.robot.commands.lightstrip.NoteLight;
import frc.robot.commands.mailbox.DeindexNote;
import frc.robot.commands.mailbox.DeployMailbox;
import frc.robot.commands.mailbox.DeployPneumatics;
import frc.robot.commands.mailbox.FireNoteRoutine;
import frc.robot.commands.mailbox.FireNoteRoutineNoLimitSwitch;
import frc.robot.commands.mailbox.RunBelts;
import frc.robot.subsystems.AddressableLightStrip;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Compressor;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.PDP;
import frc.robot.subsystems.UrMom;
import frc.robot.subsystems.mailbox.Mailbox;
import frc.robot.subsystems.mailbox.MailboxBelts;
import frc.robot.subsystems.mailbox.MailboxPneumatics;
import java.util.Optional;

/** Singleton class that contains all the robot's subsystems, commands, and button bindings. */
@SuppressWarnings("unused")
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

  /** Singleton instance of the intake {@link Camera} for the whole robot. */
  public static Camera intakeCamera = new Camera(Constants.Camera.INTAKE_CAMERA_ID);

  /** Singleton instance of the intake {@link Compressor} for the whole robot. */
  public static Compressor compressor = new Compressor();

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

  /** Singleton instance of the intake {@link StartCamera} for the whole robot. */
  public static StartCamera startIntakeCamera = new StartCamera(intakeCamera);

  /** Singleton instance of {@link SetDrivePerspectiveFieldOriented} for the whole robot. */
  public static SetDrivePerspectiveFieldOriented setDrivePerspectiveFieldOriented =
      new SetDrivePerspectiveFieldOriented();

  /** Singleton instance of {@link AddressableLightStrip} for the whole robot. */
  public static AddressableLightStrip robotLights =
      new AddressableLightStrip(Constants.LightStrips.PWM_ID, Constants.LightStrips.LED_COUNT);

  /**
   * Singleton instance of {@link SetDrivePerspectiveFieldOrientedHeadingSnapping} for the whole
   * robot.
   */
  public static SetDrivePerspectiveFieldOrientedHeadingSnapping
      setDrivePerspectiveFieldOrientedHeadingSnapping =
          new SetDrivePerspectiveFieldOrientedHeadingSnapping();

  /** Singleton instance of {@link SetDrivePerspectiveRobotOriented} for the whole robot. */
  public static SetDrivePerspectiveRobotOriented setDrivePerspectiveRobotOriented =
      new SetDrivePerspectiveRobotOriented();

  /* Autos */
  /** Singleton instance of {@link MoveAwayFromAmp} for the whole robot. */
  public static MoveAwayFromAmp moveAwayFromAmp = new MoveAwayFromAmp();

  /** Singleton instance of {@link OnePieceAuto} for the whole robot. */
  public static OnePieceAuto onePieceAuto = new OnePieceAuto();

  /** Singleton instance of {@link OnePieceAutoButItWorksISwear} for the whole robot. */
  public static OnePieceAutoButItWorksISwear onePieceAutoButItWorksISwear =
      new OnePieceAutoButItWorksISwear();

  /** Singleton instance of {@link TaxiLongAuto} for the whole robot. */
  public static TaxiLongAuto taxiLongAuto = new TaxiLongAuto();

  /** Singleton instance of {@link PickUpFromCenterAuto} for the whole robot. */
  public static PickUpFromCenterAuto pickUpFromCenterAuto = new PickUpFromCenterAuto();

  /** Singleton instance of {@link DriveToCenterAuto} for the whole robot. */
  public static DriveToCenterAuto driveToCenterAuto = new DriveToCenterAuto();

  /** Singleton instance of {@link TaxiAuto} for the whole robot. */
  public static TaxiAuto taxiAuto = new TaxiAuto();

  /** Singleton instance of {@link DelayedTaxiAuto} for the whole robot. */
  public static DelayedTaxiAuto delayedTaxiAuto = new DelayedTaxiAuto();

  /** Singleton instance of {@link ClearPDPStickyFaults} for the whole robot. */
  public static ClearPDPStickyFaults clearPDPStickyFaults = new ClearPDPStickyFaults();

  /** Singleton instance of {@link ZeroGyro} for the whole robot. */
  public static ZeroGyro zeroGyro = new ZeroGyro();

  /** Singleton instance of {@link DisabledLight} for the whole robot. */
  public static DisabledLight disabledLights = new DisabledLight();

  /** Singleton instance of {@link EnabledLight} for the whole robot. */
  public static EnabledLight enabledLights = new EnabledLight();

  /** Singleton instance of {@link NoteLight} for the whole robot. */
  public static NoteLight noteLight = new NoteLight();

  /** Singleton instance of {@link ControlCompressor} for the whole robot. */
  public static ControlCompressor controlCompressor = new ControlCompressor();

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
    Shuffleboard.getTab("Driver")
        .add("Set Drive Perspective to Field Oriented", setDrivePerspectiveFieldOriented);
    Shuffleboard.getTab("Driver")
        .add(
            "Set Drive Perspective to Field Oriented Heading Snapping",
            setDrivePerspectiveFieldOrientedHeadingSnapping);
    Shuffleboard.getTab("Driver")
        .add("Set Drive Perspective Robot Oriented", setDrivePerspectiveRobotOriented);

    startIntakeCamera.schedule();

    drive.setDefaultCommand(driveFieldOriented);
    robotLights.setDefaultCommand(enabledLights);
    compressor.setDefaultCommand(controlCompressor);
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

    // autoChooser.addOption("One Note With Limelight", onePieceAuto);

    autoChooser.addOption("WORKING ONE PIECE AUTO I HOPE", onePieceAutoButItWorksISwear);

    autoChooser.addOption("LONG taxi auto", taxiLongAuto);

    autoChooser.addOption("Pick Up Note From Center", pickUpFromCenterAuto);

    autoChooser.addOption("NO INTAKE drive to center", driveToCenterAuto);

    autoChooser.addOption("Taxi with 10 second delay", delayedTaxiAuto);

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

  /**
   * Returns true if the robot is on the red alliance. False otherwise.
   *
   * @return True if the robot is on the red alliance. False otherwise.
   */
  public static boolean isRedAlliance() {
    Optional<Alliance> alliance = DriverStation.getAlliance();
    if (!alliance.isEmpty()) {
      return alliance.get() == Alliance.Red;
    } else {
      return false;
    }
  }
}
