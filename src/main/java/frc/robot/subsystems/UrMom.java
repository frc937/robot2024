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

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Subsystem for printing "your mom" */
public class UrMom extends SubsystemBase {
  /** Creates a new UrMom. */
  public UrMom() {}

  public void printUrMom() {
    System.out.println("your mom");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
