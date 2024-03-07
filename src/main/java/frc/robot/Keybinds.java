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

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * The list of keybinds for our controllers for the robot. Call ONE of these methods in {@link
 * RobotContainer}'s configureBindings() method.'
 */
public final class Keybinds {
  /**
   * Configures the robot with default keybinds for competition.
   *
   * @param pilotController
   * @param operatorController
   */
  public static void configureDefaultKeybinds(
      CommandXboxController pilotController, CommandXboxController operatorController) {
    operatorController.y().whileTrue(RobotContainer.climbUp);
    operatorController.a().whileTrue(RobotContainer.climbDown);
    operatorController.povUp().whileTrue(RobotContainer.runIntake);
    operatorController.povDown().whileTrue(RobotContainer.runIntakeReverse);
    operatorController.leftTrigger().whileTrue(RobotContainer.aimToAmp);
    operatorController.rightTrigger().whileTrue(RobotContainer.fireNote);

    pilotController.leftTrigger().toggleOnTrue(RobotContainer.driveFieldOriented);
    pilotController.rightBumper().toggleOnTrue(RobotContainer.enterXMode);
    /* TODO: angle / velocity steering toggle w/ right trigger (no issue) and boost on left bumper (issue 86) */
  }

  /**
   * Configures the robot with keybinds for if we can't use the operator controller. (All buttons
   * bound to pilotController)
   *
   * @param pilotController
   * @param operatorController
   */
  public static void configureOperatorlessKeybinds(
      CommandXboxController pilotController, CommandXboxController operatorController) {
    pilotController.leftStick().toggleOnTrue(RobotContainer.driveFieldOriented);
    /* TODO: angle / velocity steering toggle w/ right stick (no issue) and boost on right bumper (issue 86) */
    pilotController.povDown().whileTrue(RobotContainer.runIntakeReverse);
    pilotController.povUp().toggleOnTrue(RobotContainer.enterXMode);
    pilotController.rightTrigger().whileTrue(RobotContainer.runIntake);
    pilotController.a().whileTrue(RobotContainer.aimToAmp);
    pilotController.b().whileTrue(RobotContainer.fireNote);
    pilotController.x().whileTrue(RobotContainer.climbUp);
    pilotController.y().whileTrue(RobotContainer.climbDown);
  }

  /**
   * Configures the robot with the original keybinds. DOES NOT USE OPERATOR CONTROLLER
   *
   * @param pilotController
   * @param operatorController
   */
  public static void configureOriginalKeybinds(
      CommandXboxController pilotController, CommandXboxController operatorController) {
    pilotController.leftStick().toggleOnTrue(RobotContainer.driveFieldOriented);
    pilotController.x().onTrue(RobotContainer.enterXMode);
    pilotController.y().whileTrue(RobotContainer.fireNote);
    pilotController.a().whileTrue(RobotContainer.runIntake);
    pilotController.povDown().whileTrue(RobotContainer.runIntakeReverse);
    pilotController.b().whileTrue(RobotContainer.aimToAmp);
    pilotController.leftTrigger().whileTrue(RobotContainer.climbDown);
    pilotController.rightTrigger().whileTrue(RobotContainer.climbUp);
    /* TODO: angle / velocity steering toggle w/ right stick (no issue) and boost on left bumper (issue 86) */
  }
}
