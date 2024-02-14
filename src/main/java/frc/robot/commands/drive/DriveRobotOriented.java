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
  private final Supplier<Double> xScaledSupplier, yScaledSupplier, zScaledSupplier;

  /** Creates a new DriveRobotOriented. */
  public DriveRobotOriented(
      Supplier<Double> xScaledSupplier,
      Supplier<Double> yScaledSupplier,
      Supplier<Double> zScaledSupplier) {
    this.drive = RobotContainer.drive;
    this.xScaledSupplier = xScaledSupplier;
    this.yScaledSupplier = yScaledSupplier;
    this.zScaledSupplier = zScaledSupplier;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double x = xScaledSupplier.get() * Constants.Drive.MAX_SPEED;
    double y = yScaledSupplier.get() * Constants.Drive.MAX_SPEED;
    double z = zScaledSupplier.get() * Constants.Drive.MAX_ANGULAR_SPEED;
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
