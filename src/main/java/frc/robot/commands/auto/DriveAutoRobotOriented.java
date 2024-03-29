// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;

/** Drives the robot field robot autonomously. */
public class DriveAutoRobotOriented extends Command {
  private Drive drive;
  private Translation2d destination;
  private double rotation;

  /**
   * Creates a new DriveAutoRobotOriented.
   *
   * @param translation The velocities for the robot to drive while the command is running
   * @param rotation The rotation velocity of the robot while the command is running.
   */
  public DriveAutoRobotOriented(Translation2d translation, double rotation) {
    this.drive = RobotContainer.drive;
    addRequirements(this.drive);
    this.destination = translation;
    this.rotation = rotation;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.driveRobot(destination, rotation, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
