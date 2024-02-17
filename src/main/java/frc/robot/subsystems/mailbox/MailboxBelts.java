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

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** Subsystem for the mailbox belt. */
public class MailboxBelts extends SubsystemBase {
  private CANSparkMax UpperBeltMotor;
  private CANSparkMax LowerBeltMotor;

  /** Constructer for MailboxBelts subsystem */
  public MailboxBelts() {
    UpperBeltMotor =
        new CANSparkMax(Constants.MailboxBelts.UPPER_BELT_MOTOR_ID, MotorType.kBrushless);
    LowerBeltMotor =
        new CANSparkMax(Constants.MailboxBelts.LOWER_BELT_MOTOR_ID, MotorType.kBrushless);
  }

  /** Runs the belt. */
  public void runBelts() {
    UpperBeltMotor.set(Constants.MailboxBelts.BELT_MOTOR_SPEED);
    LowerBeltMotor.set(Constants.MailboxBelts.BELT_MOTOR_SPEED);
  }

  /** Stops the belt. */
  public void stop() {
    UpperBeltMotor.set(0);
    LowerBeltMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
