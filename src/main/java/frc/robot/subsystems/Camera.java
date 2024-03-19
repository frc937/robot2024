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

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Subsystem for a USB camera. */
public class Camera extends SubsystemBase {
  private int port;
  private UsbCamera camera;

  /**
   * Creates a new Camera.
   *
   * @param port The USB port number on the RoboRIO that the camera is plugged into. (This will
   *     determine which /dev/videoX device the CameraServer tries to find).
   */
  public Camera(int port) {
    this.port = port;
  }

  /**
   * Starts the camera that this subsystem represents and adds it to the Driver tab on Shuffleboard.
   */
  public void startCamera() {
    camera = CameraServer.startAutomaticCapture(port);
    camera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);

    Shuffleboard.getTab("Driver").add(camera).withSize(4, 4);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
