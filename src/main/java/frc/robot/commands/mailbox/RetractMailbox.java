// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.mailbox;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class RetractMailbox extends ParallelCommandGroup {
  /** Creates a new DeployMailbox. */
  public RetractMailbox() {
    addCommands(new DeployPneumatics(), new RunBelts());
  }
}
