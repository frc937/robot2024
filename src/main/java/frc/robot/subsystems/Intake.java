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

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** The intake of the robot. */
public class Intake extends SubsystemBase {

  private CANSparkMax intakeLower;
  private CANSparkMax intakeUpper;
  private DigitalInput limitSwitch;
  private GenericEntry noteIsInIntake;

  /** Creates a new Intake. */
  public Intake() {
    this.intakeLower =
        new CANSparkMax(Constants.Intake.LOWER_INTAKE_MOTOR_ID, MotorType.kBrushless);
    this.intakeUpper =
        new CANSparkMax(Constants.Intake.UPPER_INTAKE_MOTOR_ID, MotorType.kBrushless);
    this.limitSwitch = new DigitalInput(Constants.Intake.INTAKE_LIMIT_SWITCH_DIO_PORT);

    intakeLower.setSmartCurrentLimit(Constants.Intake.INTAKE_MOTOR_CURRENT_LIMIT);
    intakeUpper.setSmartCurrentLimit(Constants.Intake.INTAKE_MOTOR_CURRENT_LIMIT);

    intakeLower.setIdleMode(Constants.Intake.INTAKE_MOTOR_IDLE_MODE);
    intakeUpper.setIdleMode(Constants.Intake.INTAKE_MOTOR_IDLE_MODE);

    intakeLower.setIdleMode(IdleMode.kBrake);
    intakeUpper.setIdleMode(IdleMode.kBrake);

    intakeLower.follow(intakeUpper, Constants.Intake.INTAKE_FOLLOWER_INVERSE_STATE);
    intakeUpper.setInverted(Constants.Intake.UPPER_INTAKE_MOTOR_INVERTED);

    intakeLower.burnFlash();
    intakeUpper.burnFlash();

    noteIsInIntake = Shuffleboard.getTab("Driver").add("Note in intake", false).getEntry();
  }

  /** Runs the intake motors. */
  public void runIntake() {
    intakeUpper.set(Constants.Intake.INTAKE_MOTOR_SPEED);
  }

  /** Runs the intake motors in reverse. */
  public void runIntakeReverse() {
    intakeUpper.set(-Constants.Intake.INTAKE_MOTOR_SPEED);
  }

  /**
   * Returns a boolean value on whether or not the Limit Switch (for the intake) has been activated.
   *
   * @return the status of the limit switch
   */
  public boolean getLimitSwitch() {
    /* Assumes the limit switch is wired to be normally closed. */
    return !limitSwitch.get();
  }

  /** Tells drivers the intake is full */
  public void reportNoteIsInIntake() {
    noteIsInIntake.setBoolean(true);
  }

  /** Tells drivers the intake is empty */
  public void reportNoteIsNotInIntake() {
    noteIsInIntake.setBoolean(false);
  }

  /** Stops the intake motors. */
  public void stop() {
    intakeUpper.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
