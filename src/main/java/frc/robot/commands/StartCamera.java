// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Camera;

/** Command that starts up a camera and sends it to shuffleboard. */
public class StartCamera extends Command {
  Camera camera;
  /** Creates a new StartCamera. */
  public StartCamera(Camera camera) {
    this.camera = camera;
    addRequirements(camera);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    camera.startCamera();
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
    return false;
  }
}
