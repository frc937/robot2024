// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot.subsystems.mailbox;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** Subsystem for the mailbox belt. */
public class MailboxBelts extends SubsystemBase {
  private SparkMax upperBeltMotor;
  private SparkMax lowerBeltMotor;

  /** Constructor for MailboxBelts subsystem */
  public MailboxBelts() {
    upperBeltMotor = new SparkMax(Constants.MailboxBelts.UPPER_BELT_MOTOR_ID, MotorType.kBrushless);
    lowerBeltMotor = new SparkMax(Constants.MailboxBelts.LOWER_BELT_MOTOR_ID, MotorType.kBrushless);

    SparkMaxConfig genericConfig = new SparkMaxConfig();
    genericConfig.smartCurrentLimit(Constants.MailboxBelts.BELT_MOTOR_CURRENT_LIMIT);
    genericConfig.idleMode(Constants.MailboxBelts.BELTS_IDLE_MODE);

    SparkMaxConfig upper = new SparkMaxConfig().apply(genericConfig);
    SparkMaxConfig lower = new SparkMaxConfig().apply(genericConfig);

    upper.inverted(Constants.MailboxBelts.UPPER_BELT_MOTOR_INVERTED);

    lower.inverted(Constants.MailboxBelts.LOWER_BELT_MOTOR_INVERTED);
    lower.follow(this.upperBeltMotor, Constants.MailboxBelts.BELTS_FOLLOWER_INVERSE_STATE);

    upperBeltMotor.configure(upper, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    lowerBeltMotor.configure(lower, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  /** Runs the belt. */
  public void runBelts() {
    upperBeltMotor.set(Constants.MailboxBelts.BELT_MOTOR_SPEED);
  }

  /** Stops the belt. */
  public void stop() {
    upperBeltMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
