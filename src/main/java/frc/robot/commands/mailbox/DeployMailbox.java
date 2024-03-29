// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot.commands.mailbox;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

/**
 * Deploys the mailbox pneumatics and runs the belts; effectively deploys the mailbox on the bot.
 */
public class DeployMailbox extends ParallelCommandGroup {
  /** Creates a new DeployMailbox. */
  public DeployMailbox() {
    addCommands(new DeployPneumatics(), new RunBelts());
  }
}
