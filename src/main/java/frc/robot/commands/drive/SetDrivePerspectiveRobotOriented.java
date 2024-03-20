// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Controllers;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;

/** Sets the current drive perspective to robot oriented. */
public class SetDrivePerspectiveRobotOriented extends Command {
  /** Creates a new SetDrivePerspectiveFieldOriented. */

  private Drive drive;
  public SetDrivePerspectiveRobotOriented() {
    this.drive = RobotContainer.drive;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.setDefaultCommand(RobotContainer.driveRobotOriented);
    Controllers.pilotController.leftTrigger().toggleOnTrue(RobotContainer.driveRobotOriented);
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
