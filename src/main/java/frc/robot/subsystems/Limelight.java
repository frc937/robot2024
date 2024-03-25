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
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.SendableCameraWrapper;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Subsystem for the Limelight 2+ that we use for vision. */
public class Limelight extends SubsystemBase {

  /* variable chain-gun, I promise we use all of these */
  private DoubleSubscriber tvSubscriber, txSubscriber, tySubscriber, taSubscriber;
  /* See https://docs.limelightvision.io/en/latest/apriltags_in_3d.html#robot-localization-botpose-and-megatag
   * to understand what the heck the different indices in this array mean
   */
  private DoubleArraySubscriber botposSubscriber;
  private DoubleSubscriber pipelineSubscriber;
  private DoublePublisher pipelinePublisher;
  private String name;

  private String fmtPath(String end) {
    return "/" + name + "/" + end;
  }

  private GenericEntry limelightHasTarget;

  /**
   * Creates a new Limelight.
   *
   * @param name The hostname of the Limelight
   */
  public Limelight(String name) {
    this.name = name;
    tvSubscriber = NetworkTableInstance.getDefault().getDoubleTopic(fmtPath("tv")).subscribe(0.0);
    txSubscriber = NetworkTableInstance.getDefault().getDoubleTopic(fmtPath("tx")).subscribe(0.0);
    tySubscriber = NetworkTableInstance.getDefault().getDoubleTopic(fmtPath("ty")).subscribe(0.0);
    taSubscriber = NetworkTableInstance.getDefault().getDoubleTopic(fmtPath("ta")).subscribe(0.0);
    DoubleTopic pipelineTopic =
        NetworkTableInstance.getDefault().getDoubleTopic(fmtPath("pipeline"));
    this.pipelineSubscriber = pipelineTopic.subscribe(0.0);
    this.pipelinePublisher = pipelineTopic.publish();
    double[] defaultBotpos = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    botposSubscriber =
        NetworkTableInstance.getDefault()
            .getDoubleArrayTopic(fmtPath("botpose"))
            .subscribe(defaultBotpos);

    Shuffleboard.getTab("Driver")
        .add(SendableCameraWrapper.wrap(name, "http://" + name + ".local:5800/stream.mjpg"))
        .withSize(4, 4);

    /* TODO: CONSTANTS */
    limelightHasTarget = Shuffleboard.getTab("Driver").add(name + "has target", false).getEntry();
  }

  /* now its time for getter method chain gun, which I have to write manually because VS Code */

  /**
   * Returns whether the Limelight has a valid target.
   *
   * @return Whether or not the Limelight has a valid target. True if it does, false if it doesn't.
   */
  public boolean hasValidTarget() {
    return tvSubscriber.get() == 1.0;
  }

  /**
   * Gets the horizontal offset from the crosshair to the currently active target.
   *
   * @return tx; the horizontal offset from the crosshair to the target.
   */
  public double getTX() {
    return txSubscriber.get();
  }

  /**
   * Gets the vertical offset from the crosshair to the currently active target.
   *
   * @return ty; the vertical offset from the crosshair to the target.
   */
  public double getTY() {
    return tySubscriber.get();
  }

  /**
   * Gets the area of the target, 0% to 100% of the image.
   *
   * @return ta; the area of the target.
   */
  public double getTA() {
    return taSubscriber.get();
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
   * @return A {@link Pose3d}; the robot's current position relative to the field.
   */
  public Pose3d getBotpose() {
    double[] botpos = botposSubscriber.get();
    return new Pose3d(
        botpos[0], botpos[1], botpos[2], new Rotation3d(botpos[3], botpos[4], botpos[5]));
  }

  /**
   * Gets the robot's current pose relative to the field.
   *
   * <p>Units are meters; 0,0 is at the center of the field.
   *
   * <p>Will only work if the Limelight can see an AprilTag and read it correctly.
   *
   * @return A {@link Pose2d}; the robot's current position relative to the field in two dimensions.
   */
  public Pose2d getBotpose2d() {
    double[] botpos = botposSubscriber.get();
    return new Pose2d(botpos[0], botpos[1], Rotation2d.fromDegrees(botpos[5]));
  }

  /**
   * Returns the current Limelight pipeline number.
   *
   * @return the current Limelight pipeline number.
   */
  public double getLimelightPipeline() {
    return pipelineSubscriber.get();
  }

  /**
   * Sets the Limelight pipeline number.
   *
   * @param pipeline the pipeline number.
   */
  public void setLimelightPipeline(double pipeline) {
    pipelinePublisher.set(pipeline);
  }

  /** Subsystem periodic; runs every scheduler run. */
  @Override
  public void periodic() {
    limelightHasTarget.setBoolean(hasValidTarget());
  }
}
