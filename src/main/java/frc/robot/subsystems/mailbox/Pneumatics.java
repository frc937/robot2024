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

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pneumatics extends SubsystemBase {
  private Solenoid leftSolenoid;
  private Solenoid rightSolenoid;

  public Pneumatics() {
    leftSolenoid =
        new Solenoid(PneumaticsModuleType.CTREPCM, Constants.Pneumatics.LEFT_SOLENOID_CHANNEL);
    rightSolenoid =
        new Solenoid(PneumaticsModuleType.CTREPCM, Constants.Pneumatics.RIGHT_SOLENOID_CHANNEL);
  }

  /** Lifts the mailbox. */
  public void extend() {
    leftSolenoid.set(true);
    rightSolenoid.set(true);
  }

  /** retracts the mailbox. */
  public void retract() {
    leftSolenoid.set(false);
    rightSolenoid.set(false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
