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

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Represents the pistons that raise the Mailbox, which is the system that outputs game pieces from
 * our robot
 */
public class MailboxPneumatics extends SubsystemBase {
  private Relay pneumaticsRelay;

  /** Constructor for the mailbox pneumatics subsystem. */
  public MailboxPneumatics() {
    pneumaticsRelay = new Relay(Constants.MailboxPneumatics.MAILBOX_SOLENOID_RELAY_ID);
  }

  /** Lifts the mailbox. */
  public void extend() {
    pneumaticsRelay.set(Relay.Value.kForward);
  }

  /** Lowers the mailbox. */
  public void retract() {
    pneumaticsRelay.set(Relay.Value.kReverse);
  }

  /** Sets pistons to off */
  public void off() {
    pneumaticsRelay.set(Relay.Value.kOff);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
