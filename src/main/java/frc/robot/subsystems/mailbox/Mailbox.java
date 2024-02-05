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

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** Subsystem for the mailbox that outputs game pieces from our robot. */
public class Mailbox extends SubsystemBase {

  /** Limit switch that detects when the mailbox is raised. */
  private DigitalInput limitSwitch;

  /** Creates a new Mailbox. */
  public Mailbox() {
    this.limitSwitch = new DigitalInput(Constants.Mailbox.MAILBOX_LIMIT_SWITCH_DIO_PORT);
  }

  /**
   * Gets the Mailbox Limit Switch's Value. Assumes the limitSwitch reports false when open.
   * 
   * @return True if the mailbox is fully raised. False otherwise.
   */
  public boolean getLimitSwitch() {
    /* Assumes the limit switch is wired to be normally open. */
    return limitSwitch.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
