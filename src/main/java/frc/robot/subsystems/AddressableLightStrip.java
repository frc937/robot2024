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

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AddressableLightStrip extends SubsystemBase {

  private final AddressableLED ledStrip;
  private final AddressableLEDBuffer buffer;

  /**
   * Creates a new AddressableLightStrip
   *
   * @param pwmPort The PWM port for the light strip.
   * @param lightCount The count of lights on the switch
   */
  public AddressableLightStrip(int pwmPort, int lightCount) {
    this.ledStrip = new AddressableLED(pwmPort);
    this.buffer = new AddressableLEDBuffer(lightCount);
    this.ledStrip.setLength(lightCount);
  }

  /**
   * Sets a light on the light strip to a color in RGB.
   *
   * @param lightNumber The light number
   * @param r The red component
   * @param g The green component
   * @param b The blue component
   */
  public void setRgbLight(int lightNumber, int r, int g, int b) {
    this.buffer.setRGB(lightNumber, r, g, b);
  }

  /**
   * Sets a light on the light strip to a color in HSV
   *
   * @param lightNumber The light number
   * @param h The hue component
   * @param s The saturation component
   * @param v The value component
   */
  public void setHSVLight(int lightNumber, int h, int s, int v) {
    this.buffer.setHSV(lightNumber, h, s, v);
  }

  /**
   * Sets a light on the light strip to a {@link edu.wpi.first.wpilibj.util.Color}
   *
   * @param lightNumber
   * @param color
   */
  public void setColorLight(int lightNumber, Color color) {
    this.buffer.setLED(lightNumber, color);
  }

  /**
   * Sets the strip with a color.
   *
   * @param color The color to set the strip to.
   */
  public void setStripColor(Color color) {
    for (int i = 0; i < this.buffer.getLength(); i++) {
      this.buffer.setLED(i, color);
    }
  }

  private static double lerp(double v0, double v1, double amount) {
    return (1 - amount) * v0 + amount * v1;
  }

  /**
   * Lineraly interpolates 2 colors. Utility method for fading colors into eachother.
   *
   * @param color1 The start color
   * @param color2 The color to fade into
   * @param amount The amount to interporlate. [0, 1]
   * @return The interpolated color.
   */
  public static Color lerpColors(Color color1, Color color2, double amount) {
    return new Color(
        lerp(color1.red, color2.red, amount),
        lerp(color1.blue, color2.blue, amount),
        lerp(color1.green, color2.green, amount));
  }

  /**
   * Gets the internal buffer used to send to the lights.
   *
   * @return the internal buffer used to send to the lights.
   */
  public AddressableLEDBuffer getLEDBuffer() {
    return this.buffer;
  }

  /** Fushes the buffer to the light strip. */
  public void flush() {
    this.ledStrip.setData(this.buffer);
  }

  /** Starts the light strip. */
  public void startLights() {
    this.ledStrip.start();
  }

  /** Stops the light strip. */
  public void stopLights() {
    this.ledStrip.stop();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}