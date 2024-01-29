// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot.commands.drive;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;

/** Drives the robot in robot-oriented mode. Default command for {@link Drive} subsystem. */
public class DriveRobotOriented extends Command {
  private final Drive drive;

  /** Creates a new DriveRobotOriented. */
  public DriveRobotOriented() {
    this.drive = RobotContainer.drive;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     double x = RobotContainer.getScaledControllerLeftYAxis() * Constants.Drive.MAX_SPEED;
    double y = RobotContainer.getScaledControllerLeftXAxis() * Constants.Drive.MAX_SPEED;
    double z = RobotContainer.getScaledControllerRightXAxis() * Constants.Drive.MAX_ANGULAR_SPEED;
    Translation2d translation = new Translation2d(x, y);

    drive.driveRobotOriented(translation, RobotContainer.getControllerRightXAxis());
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
