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

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
  public final NetworkTable limelightTable;
  private double tv, tx, ty, ta;

  /** Creates a new limelight. */
  public Limelight(String name) {
    this.limelightTable = NetworkTableInstance.getDefault().getTable(name);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    tv = limelightTable.getEntry("tv").getDouble(0);
    tx = limelightTable.getEntry("tx").getDouble(0);
    ty = limelightTable.getEntry("ty").getDouble(0);
    ta = limelightTable.getEntry("ta").getDouble(0);
  }

  /**
   * Gets the horizontal offset from the crosshair to the currently active target.
   *
   * @return tx; horizontal offset from the crosshair to the currently active target.
   */
  public double getTX() {
    return tx;
  }

  /**
   * Gets the vertical offset from the crosshair to the currently active target.
   *
   * @return ty; vertical offset from the crosshair to the currently active target.
   */
  public double getTY() {
    return ty;
  }

  /**
   * Gets the area of the target 0% to 100% of image.
   *
   * @return ta; area of the target 0% to 100% of image.
   */
  public double getTA() {
    return ta;
  }

  /**
   * Returns true if the Limelight has a valid target.
   *
   * @return a boolean; true if the Limelight has a vlid target.
   */
  public boolean hasValidTarget() {
    return tv = 1.0;
  }
}
