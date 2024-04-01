// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot.commands.lightstrip;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.AddressableLightStrip;

/** Activates when the robot is disabled. */
public class NodeLight extends Command {
  private AddressableLightStrip robotLights;

  /** Creates a new RobotDisabledLights. */
  public NodeLight() {
    this.robotLights = RobotContainer.robotLights;
    addRequirements(this.robotLights);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    robotLights.startLights();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    robotLights.updateRainbow();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
