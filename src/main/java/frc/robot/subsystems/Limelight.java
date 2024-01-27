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

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Subsystem for the Limelight 2+ that we use for vision. */
public class Limelight extends SubsystemBase {

  /* Note to future devs: we are NOT doing the whole setting values in NetworkTables to turn the Limelight
   * LEDS on and off thing this year (turnOnLEDs() and turnOfflLEDs() in robot2022).
   * For one, if we're not tracking reflective targets (which as of now we are not, we're just doing
   * AprilTags), then we shouldn't need them on at all; for another, the correct way to turn the LEDs on
   * and off is to make the default pipeline one with them off and then switch to a pipeline which has them
   * on if we need to turn them on. This method prevents the whole "Limelight LEDs are stuck on until robot
   * code starts!" thing.
   */

  /* This class may need logic to switch between pipelines based off of AprilTag pipeline restrictions. As
   * of now, I'm not adding them, since in theory the Limelight should just handle getting a botpos with as
   * many AprilTags in its FOV as can fit because MegaTag
   * (https://docs.limelightvision.io/en/latest/apriltags_in_3d.html#robot-localization-botpose-and-megatag).
   */
  /* it would be so cool if we just didn't have to do multiple pipelines at all this year */

  /* variable chaingun, I promise we use all of these */
  private DoubleSubscriber tvSubscriber, txSubscriber, tySubscriber, taSubscriber;
  private DoubleArraySubscriber botposSubscriber;
  private double tv, tx, ty, ta;
  /* See https://docs.limelightvision.io/en/latest/apriltags_in_3d.html#robot-localization-botpose-and-megatag
   * to understand what the heck the different indices in this array mean
   */
  private double[] botpos;
  private String name;

  private String fmtPath(String end) {
    return "/" + name + "/" + end;
  }

  /**
   * Creates a new Limelight. Should be run once from {@link frc.robot.RobotContainer}.
   *
   * @param name The hostname of the limelight
   */
  public Limelight(String name) {
    this.name = name;
    tvSubscriber = NetworkTableInstance.getDefault().getDoubleTopic(fmtPath("tv")).subscribe(0.0);
    txSubscriber = NetworkTableInstance.getDefault().getDoubleTopic(fmtPath("tx")).subscribe(0.0);
    tySubscriber = NetworkTableInstance.getDefault().getDoubleTopic(fmtPath("ty")).subscribe(0.0);
    taSubscriber = NetworkTableInstance.getDefault().getDoubleTopic(fmtPath("ta")).subscribe(0.0);
    /* In theory this won't break. It got mad when I tried to insert the array into the
     * method like .subscribe({0.0, 0.0, 0.0, 0.0, 0.0, 0.0}) so ¯\_(ツ)_/¯
     */
    double[] defaultBotpos = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    botposSubscriber =
        NetworkTableInstance.getDefault()
            .getDoubleArrayTopic(fmtPath("botpose"))
            .subscribe(defaultBotpos);
  }

  /* now its time for getter method chaingun, which I have to write manually because VS Code */
  /* nvm im making a python script do this for me */

  /**
   * Returns whether the Limelight has a valid target.
   *
   * @return Whether or not the Limelight has a valid target. True if it does, false if it doesn't.
   */
  public boolean hasValidTarget() {
    return tv == 1.0;
  }

  /**
   * Gets the horizontal offset from the crosshair to the currently active target.
   *
   * @return tx; the horizontal offset from the crosshair to the target.
   */
  public double getTX() {
    return tx;
  }

  /**
   * Gets the vertical offset from the crosshair to the currently active target.
   *
   * @return ty; the vertical offset from the crosshair to the target.
   */
  public double getTY() {
    return ty;
  }

  /**
   * Gets the area of the target, 0% to 100% of the image.
   *
   * @return ta; the area of the target.
   */
  public double getTA() {
    return ta;
  }

  /**
   * Returns the robot's current pose relative to the field.
   *
   * <p>Units are meters; 0,0 is at the center of the field.
   *
   * <p>Will only work if the Limelight can see an AprilTag and read it correctly.
   *
   * <p>Notably, the Z, roll, and pitch values will always be zero, since we snap the bot to the
   * floor on the Limelight.
   *
   * @return A {@link Pose}; the robot's current position relative to the field.
   */
  public Pose3d getBotpose() {
    return new Pose3d(
        botpos[0], botpos[1], botpos[2], new Rotation3d(botpos[3], botpos[4], botpos[5]));
  }

  /**
   * Gets the robot's current post relative to the field.
   *
   * <p>Units are meters; 0,0 is at the center of the field.
   *
   * <p>Will only work if the Limelight can see an AprilTag and read it correctly.
   *
   * @return A {@link Pose2d}; the robot's current position relative to the field in two dimensions.
   */
  public Pose2d getBotpose2d() {
    return new Pose2d(botpos[0], botpos[1], Rotation2d.fromDegrees(botpos[5]));
  }

  /**
   * Subsystem periodic; runs every scheduler run. Used to update Limelight data from NetworkTables
   * in this subsystem.
   */
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    tv = tvSubscriber.get();
    tx = txSubscriber.get();
    ty = tySubscriber.get();
    ta = taSubscriber.get();
    /* See https://docs.limelightvision.io/en/latest/apriltags_in_3d.html#robot-localization-botpose-and-megatag
     * to understand what the heck the different indices in this array mean
     */
    botpos = botposSubscriber.get();
  }
}
