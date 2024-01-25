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

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** Represents two double solenoids "leftPiston" "rightPiston" */
public class MailboxPneumatics extends SubsystemBase {
  private DoubleSolenoid leftPiston;
  private DoubleSolenoid rightPiston;

  /** Constructor for the mailbox pneumatics subsystem. */
  public MailboxPneumatics() {
    leftPiston =
        new DoubleSolenoid(
            PneumaticsModuleType.CTREPCM,
            Constants.MailboxPneumatics.LEFT_SOLENOID_FORWARD_CHANNEL,
            Constants.MailboxPneumatics.LEFT_SOLENOID_REVERSE_CHANNEL);
    rightPiston =
        new DoubleSolenoid(
            PneumaticsModuleType.CTREPCM,
            Constants.MailboxPneumatics.RIGHT_SOLENOID_FORWARD_CHANNEL,
            Constants.MailboxPneumatics.RIGHT_SOLENOID_REVERSE_CHANNEL);
  }

  /** Lifts the mailbox. */
  public void extend() {
    leftPiston.set(DoubleSolenoid.Value.kForward);
    rightPiston.set(DoubleSolenoid.Value.kForward);
  }

  /** Lowers the mailbox. */
  public void retract() {
    leftPiston.set(DoubleSolenoid.Value.kReverse);
    rightPiston.set(DoubleSolenoid.Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
