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

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** PDP of the robot */
public class PDP extends SubsystemBase {

  private PowerDistribution powerDistributionPanel;

  private TalonFX dummyTalon;

  /** Creates a new PDP. */
  public PDP() {
    this.powerDistributionPanel = new PowerDistribution();

    /* Creating a dummy Talon FX that doesn't actually exist so that Phoenix framework runs, allowing us to use Phoenix tuner for the PDP and PCM. */
    this.dummyTalon = new TalonFX(0);
  }

  /** Clears the sticky faults of the PDP */
  public void clearStickyFaults() {
    powerDistributionPanel.clearStickyFaults();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
