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

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** The subsystem for the climber. */
public class Climber extends SubsystemBase {
  private SparkMax climberMotor;

  /** Creates a new climber. */
  public Climber() {
    this.climberMotor = new SparkMax(Constants.Climber.CLIMBER_MOTOR_ID, MotorType.kBrushed);

    SparkMaxConfig climberConfig = new SparkMaxConfig();
    climberConfig.idleMode(Constants.Climber.CLIMBER_MOTOR_IDLE_MODE);
    climberConfig.inverted(Constants.Climber.CLIMBER_INVERTED);

    climberMotor.configure(
        climberConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  /** Tells the robot to climb up. */
  public void climbUp() {
    this.climberMotor.set(Constants.Climber.CLIMBER_MOTOR_SPEED);
  }

  /** Tells the robot to climb down. */
  public void climbDown() {
    this.climberMotor.set(-Constants.Climber.CLIMBER_MOTOR_SPEED);
  }

  /** Stops the climber. */
  public void stop() {
    this.climberMotor.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
