// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.PDP;

/** Clears the PDP's sticky faults */
public class ClearPDPStickyFaults extends Command {

  private PDP pdp;

  /** Creates a new ClearPDPStickyFaults. */
  public ClearPDPStickyFaults() {
    this.pdp = RobotContainer.pdp;
    addRequirements(this.pdp);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pdp.clearStickyFaults();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
