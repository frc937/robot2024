// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.Constants;

/**
 * The intake of the robot.
 */
public class Intake extends SubsystemBase {

  private Spark intake;

  /** Creates a new Intake. */
  public Intake() {
    this.intake = new Spark(Constants.Intake.INTAKE_MOTOR_ID);
  }

  /** Runs the intake motors. */
  public void runIntake() {
    intake.set(Constants.Intake.INTAKE_MOTOR_SPEED);
  }

  /** Stops the intake motors. */
  public void stop() {
    intake.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
