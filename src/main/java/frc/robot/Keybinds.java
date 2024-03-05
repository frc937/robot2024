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

/** Add your docs here. */
public final class Keybinds {
  /**
   * Configures the robot with default keybinds for competition.
   *
   * @param pilotController
   * @param operatorController
   */
  public void configureDefaultKeybinds(
      CommandXboxController pilotController, CommandXboxController operatorController) {
    operatorController.y().whileTrue(RobotContainer.climbUp);
    operatorController.a().whileTrue(RobotContainer.climbDown);
    operatorController.povUp().whileTrue(RobotContainer.runIntake);
    /* TODO: bind intake reverse to povDown when issue 92 is closed. */
    operatorController.leftTrigger().whileTrue(RobotContainer.aimToAmp);
    operatorController.rightTrigger().whileTrue(RobotContainer.fireNote);

    pilotController.leftTrigger().toggleOnTrue(RobotContainer.driveFieldOriented);
    pilotController.rightBumper().toggleOnTrue(RobotContainer.enterXMode);
    /* TODO: angle / velocity steering toggle w/ right trigger and boost on left bumper */
  }
}
