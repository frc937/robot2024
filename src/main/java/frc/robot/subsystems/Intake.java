// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** The intake of the robot. */
public class Intake extends SubsystemBase {

  private CANSparkMax intake;
  private DigitalInput limitSwitch;

  /** Creates a new Intake. */
  public Intake() {
    this.intake = new CANSparkMax(Constants.Intake.INTAKE_MOTOR_ID, MotorType.kBrushless);
    this.limitSwitch = new DigitalInput(Constants.Intake.INTAKE_LIMIT_SWITCH_DIO_PORT);
  }

  /** Runs the intake motors. */
  public void runIntake() {
    intake.set(Constants.Intake.INTAKE_MOTOR_SPEED);
  }

  /**
   * Returns a boolean value on whether or not the Limit Switch (for the intake) has been activated.
   */
  public boolean getLimitSwitch() {
    return limitSwitch.get();
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
