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
import java.util.function.Supplier;

/** Drives the robot in robot-oriented mode. Default command for {@link Drive} subsystem. */
public class DriveRobotOriented extends Command {
  private final Drive drive;
  private final Supplier<Double> xSupplier, ySupplier, zSupplier;

  /**
   * Drives the robot robot-oriented
   *
   * @param xSupplier The joystick value for the Y axis. [-1, 1] left positive.
   * @param ySupplier The joystick value for the Y axis. [-1, 1] back positive.
   * @param zSupplier The joystick value for the Z axis. [-1, 1] counterclockwise positive.
   */
  public DriveRobotOriented(
      Supplier<Double> xSupplier, Supplier<Double> ySupplier, Supplier<Double> zSupplier) {
    this.drive = RobotContainer.drive;
    this.xSupplier = xSupplier;
    this.ySupplier = ySupplier;
    this.zSupplier = zSupplier;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double x = xSupplier.get() * Constants.Drive.MAX_SPEED;
    double y = ySupplier.get() * Constants.Drive.MAX_SPEED;
    double z = zSupplier.get() * Constants.Drive.MAX_ANGULAR_SPEED;
    Translation2d translation = new Translation2d(x, y);

    drive.driveRobotOriented(translation, z);
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
