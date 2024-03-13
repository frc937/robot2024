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
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import java.util.function.Supplier;

/**
 * The list of keybinds for our controllers for the robot. Call ONE of these methods in {@link
 * RobotContainer}'s configureBindings() method.'
 */
public final class Controllers {
  /** Xbox controller used by the pilot, who is the student that controls the drivetrain */
  public static CommandXboxController pilotController =
      new CommandXboxController(Constants.Controllers.PILOT_CONTROLLER_PORT);

  /**
   * Xbox controller used by the operator, who is the student who controls all mechanisms other than
   * the drivetrain
   */
  public static CommandXboxController operatorController =
      new CommandXboxController(Constants.Controllers.OPERATOR_CONTROLLER_PORT);

  /**
   * Raw HID Xbox controller object for the {@link pilotController}. Only used for POV/button
   * suppliers.
   */
  private static XboxController rawPilotController = pilotController.getHID();

  /** Raw HID Xbox controller object for the {@link operatorController}. */
  private static XboxController rawOpXboxController = operatorController.getHID();

  /** Variable determining whether to rumble if battery voltage is less than 10.5v */
  public static boolean rumbleBrowningOutWorking = true;

  /** When this supplier returns true, the robot should snap towards the opposing alliance wall. */
  public static Supplier<Boolean> headingSnappingUpSupplier =
      () -> rawPilotController.getPOV() == 0;

  /** When this supplier returns true, the robot should snap towards the right side of the field. */
  public static Supplier<Boolean> headingSnappingRightSupplier =
      () -> rawPilotController.getPOV() == 90;

  /** When this supplier returns true, the robot should snap towards our alliance wall. */
  public static Supplier<Boolean> headingSnappingDownSupplier =
      () -> rawPilotController.getPOV() == 180;

  /** When this supplier returns true, the robot should snap towards the left side of the field. */
  public static Supplier<Boolean> headingSnappingLeftSupplier =
      () -> rawPilotController.getPOV() == 270;

  public static GenericEntry keymapEntry =
      Shuffleboard.getTab("Driver").add("Keymap", "Default").getEntry();

  /**
   * Represents an axis on a controller. Used to pass into {@link Controllers#getControllerAxis}.
   */
  public enum ControllerAxis {
    /** Left stick X axis (left to right) */
    LeftX,
    /** Left stick Y axis (up and down) */
    LeftY,
    /** Right stick X axis (left to right) */
    RightX,
    /** Right stick Y axis (up and down) */
    RightY
  }

  /** Enumeration of possible keymaps for the robot. */
  public enum Keymap {
    /** Default keymap defined by drive team. Uses both controllers. */
    Default,
    /** Keymap that doesn't use the {@link operatorController}. */
    Operatorless,
    /**
     * Original testing keymap. Doesn't use the {@link operatorController}.
     *
     * @deprecated Because we don't use these keymaps anymore.
     */
    @Deprecated
    Original
  }

  /** Configures the robot with default keybinds for competition. */
  private static void configureDefaultKeybinds() {
    operatorController.y().whileTrue(RobotContainer.climbUp);
    operatorController.a().whileTrue(RobotContainer.climbDown);
    operatorController.povUp().whileTrue(RobotContainer.runIntake);
    operatorController.povDown().whileTrue(RobotContainer.runIntakeReverse);
    operatorController.leftTrigger().whileTrue(RobotContainer.aimToAmp);
    operatorController.rightTrigger().whileTrue(RobotContainer.fireNote);

    // pilotController.leftTrigger().toggleOnTrue(RobotContainer.driveFieldOriented);
    pilotController.rightBumper().toggleOnTrue(RobotContainer.enterXMode);
    pilotController.leftBumper().whileTrue(RobotContainer.driveRobotOrientedSprint);
    /* TODO: angle / velocity steering toggle w/ right trigger (no issue) and boost on left bumper (issue 86) */

    keymapEntry.setString("Default");
  }

  /**
   * Configures the robot with keybinds for if we can't use the operator controller. (All buttons
   * bound to pilotController)
   */
  private static void configureOperatorlessKeybinds() {
    pilotController.leftStick().toggleOnTrue(RobotContainer.driveFieldOriented);
    /* TODO: angle / velocity steering toggle w/ right stick (no issue) and boost on right bumper (issue 86) */
    pilotController.povDown().whileTrue(RobotContainer.runIntakeReverse);
    pilotController.povUp().toggleOnTrue(RobotContainer.enterXMode);
    pilotController.rightTrigger().whileTrue(RobotContainer.runIntake);
    pilotController.a().whileTrue(RobotContainer.aimToAmp);
    pilotController.b().whileTrue(RobotContainer.fireNote);
    pilotController.x().whileTrue(RobotContainer.climbUp);
    pilotController.y().whileTrue(RobotContainer.climbDown);

    keymapEntry.setString("Operatorless");
  }

  /** Configures the robot with the original keybinds. DOES NOT USE OPERATOR CONTROLLER */
  private static void configureOriginalKeybinds() {
    pilotController.leftStick().toggleOnTrue(RobotContainer.driveFieldOriented);
    pilotController.x().onTrue(RobotContainer.enterXMode);
    pilotController.y().whileTrue(RobotContainer.fireNote);
    pilotController.a().whileTrue(RobotContainer.runIntake);
    pilotController.povDown().whileTrue(RobotContainer.runIntakeReverse);
    pilotController.b().whileTrue(RobotContainer.aimToAmp);
    pilotController.leftTrigger().whileTrue(RobotContainer.climbDown);
    pilotController.rightTrigger().whileTrue(RobotContainer.climbUp);
    /* TODO: angle / velocity steering toggle w/ right stick (no issue) and boost on left bumper (issue 86) */

    keymapEntry.setString("Original");
  }

  /**
   * Configures robot keybinds.
   *
   * <p>Should be called at least once somewhere in robot initialization.
   *
   * @param keymap Which keymap to be used. Supplied by {@link Controllers.Keymap}.
   */
  public static void configureKeybinds(Keymap keymap) {
    CommandScheduler.getInstance().getActiveButtonLoop().clear();
    switch (keymap) {
      case Default:
        configureDefaultKeybinds();
        break;
      case Operatorless:
        configureOperatorlessKeybinds();
        break;
      case Original:
        configureOriginalKeybinds();
        break;
      default:
        throw new IllegalArgumentException(
            "configureKeybinds() recieved an illegal enum constant argument");
    }
  }

  /**
   * Scales a controller axis (joystick value).
   *
   * @param axis Axis value to scale.
   * @return Scaled axis value.
   */
  private static double scaleAxis(double axis) {
    double deadbanded =
        MathUtil.applyDeadband(axis, Constants.Controllers.DRIVER_CONTROLLER_DEADBAND);
    return -Math.pow(deadbanded, 2) * Math.signum(axis);
  }

  /**
   * Gets a given controller axis (a joystick value).
   *
   * @throws IllegalArgumentException If an enum value for ControllerAxis is passed that doesn't
   *     exist within that type
   * @param controller The controller to get the axis from. Should probably pass a public field of
   *     {@link Controllers}
   * @param controllerAxis Enum representing which axis (left stick vs. right stick and x axis vs. y
   *     axis) to get.
   * @param scaled Whether or not to deadband and quadratically scale the axis.
   * @return Axis value.
   */
  public static double getControllerAxis(
      CommandXboxController controller, ControllerAxis controllerAxis, boolean scaled) {
    if (scaled) {
      return scaleAxis(getControllerAxis(controller, controllerAxis, false));
    } else {
      switch (controllerAxis) {
        case LeftX:
          return controller.getLeftX();
        case LeftY:
          return controller.getLeftY();
        case RightX:
          return controller.getRightX();
        case RightY:
          return controller.getRightY();
        default:
          throw new IllegalArgumentException(
              "getControllerAxis() recieved an illegal enum constant argument");
      }
    }
  }

  private static SimpleWidget browningOutRumble =
      Shuffleboard.getTab("Driver")
          .add("Rumble if battery voltage is below 10.5v", rumbleBrowningOutWorking);

  /** Rumbles the controllers while browning out */
  public static void rumbleIfBrowningOut() {
    if (RobotController.getBatteryVoltage() < 10.5 && rumbleBrowningOutWorking == true) {
      rawOpXboxController.setRumble(GenericHID.RumbleType.kBothRumble, 0.5);
      rawPilotController.setRumble(GenericHID.RumbleType.kBothRumble, 0.5);
    } else if (RobotController.isBrownedOut()) {
      rawOpXboxController.setRumble(GenericHID.RumbleType.kBothRumble, 1);
      rawPilotController.setRumble(GenericHID.RumbleType.kBothRumble, 1);
    } else {
      rawOpXboxController.setRumble(GenericHID.RumbleType.kBothRumble, 0);
      rawPilotController.setRumble(GenericHID.RumbleType.kBothRumble, 0);
    }
  }

  /**
   * Gets supplier for a given controller axis (a joystick value).
   *
   * @param controller The controller to get the axis from. Should probably pass a public field of
   *     {@link Controllers}
   * @param controllerAxis Enum representing which axis (left stick vs. right stick and x axis vs. y
   *     axis) to get.
   * @param scaled Whether or not to deadband and quadratically scale the axis.
   * @return Supplier that, when .get() is run, will return the requested axis value.
   */
  public static Supplier<Double> getControllerAxisSupplier(
      CommandXboxController controller, ControllerAxis controllerAxis, boolean scaled) {
    return () -> getControllerAxis(controller, controllerAxis, scaled);
  }
}
