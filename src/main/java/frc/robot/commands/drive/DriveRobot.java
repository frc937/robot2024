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
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;
import java.util.function.Supplier;

/** Drives the robot. */
public class DriveRobot extends Command {
  private final Drive drive;
  private final Supplier<Double> xSupplier, ySupplier, zSupplier;
  private final boolean isFieldOriented;
  private double maxSpeed = RobotContainer.drive.getMaximumSpeed();
  private double maxAngularSpeed = RobotContainer.drive.getMaximumAngularSpeed();

  /**
   * Drives the robot.
   *
   * @param xSupplier The joystick value for the Y axis. [-1, 1] left positive.
   * @param ySupplier The joystick value for the Y axis. [-1, 1] back positive.
   * @param zSupplier The joystick value for the Z axis. [-1, 1] counterclockwise positive.
   * @param isFieldOriented If the robot should drive field oriented or robot oriented.
   */
  public DriveRobot(
      Supplier<Double> xSupplier,
      Supplier<Double> ySupplier,
      Supplier<Double> zSupplier,
      boolean isFieldOriented) {
    this.xSupplier = xSupplier;
    this.ySupplier = ySupplier;
    this.zSupplier = zSupplier;
    this.drive = RobotContainer.drive;
    this.isFieldOriented = isFieldOriented;
    addRequirements(drive);
  }

  /**
   * Creates a DriveRobot with
   *
   * @param xSupplier The joystick value for the Y axis. [-1, 1] left positive.
   * @param ySupplier The joystick value for the Y axis. [-1, 1] back positive.
   * @param zSupplier The joystick value for the Z axis. [-1, 1] counterclockwise positive.
   * @param isFieldOriented If the robot should drive field oriented or robot oriented.
   * @param maxSpeed The max speed this command should go.
   * @param maxAngularSpeed The max angular speed this command should go.
   */
  public DriveRobot(
      Supplier<Double> xSupplier,
      Supplier<Double> ySupplier,
      Supplier<Double> zSupplier,
      boolean isFieldOriented,
      double maxSpeed,
      double maxAngularSpeed) {
    this(xSupplier, ySupplier, zSupplier, isFieldOriented);
    this.maxSpeed = maxSpeed;
    this.maxAngularSpeed = maxAngularSpeed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.setHeadingCorrection(false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double x = this.xSupplier.get() * this.maxSpeed;
    double y = this.ySupplier.get() * this.maxSpeed;
    double z = this.zSupplier.get() * this.maxAngularSpeed;
    Translation2d translation = new Translation2d(x, y);

    drive.driveRobot(translation, z, this.isFieldOriented);
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
