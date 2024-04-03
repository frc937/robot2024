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

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import org.jspecify.annotations.Nullable;

/** The subsystem for the lights on the robot. */
public class AddressableLightStrip extends SubsystemBase {

  private final AddressableLED ledStrip;
  private final AddressableLEDBuffer buffer;
  private int rainbowHue = 0;
  private double fadeSpeed = Constants.LightStrips.STRIP_FADE_SPEED;
  @Nullable private Color targetStripColor = null;
  private GenericEntry targetLightEntry;

  /**
   * Creates a new AddressableLightStrip
   *
   * @param pwmPort The PWM port for the light strip.
   * @param lightCount The count of lights on the switch
   */
  public AddressableLightStrip(int pwmPort, int lightCount) {
    this.ledStrip = new AddressableLED(pwmPort);
    this.buffer = new AddressableLEDBuffer(lightCount);
    this.targetLightEntry = Shuffleboard.getTab("Debug").add("Lights at target?", false).getEntry();
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
    this.targetStripColor = null;
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
    this.targetStripColor = null;
    this.buffer.setHSV(lightNumber, h, s, v);
  }

  /**
   * Sets a light on the light strip to a {@link edu.wpi.first.wpilibj.util.Color}
   *
   * @param lightNumber
   * @param color
   */
  public void setColorLight(int lightNumber, Color color) {
    this.targetStripColor = null;
    this.buffer.setLED(lightNumber, color);
  }

  /**
   * Sets the strip with a color.
   *
   * @param color The color to set the strip to.
   */
  public void setStripColor(Color color) {
    targetStripColor = color;
  }

  /**
   * Directly sets the LED colors with no fade effect.
   *
   * @param color The color to set the LEDs to.
   */
  public void setStripColorRaw(Color color) {
    for (int led = 0; led < this.buffer.getLength(); led++) {
      this.buffer.setLED(led, color);
    }
  }

  private static double lerp(double v0, double v1, double amount) {
    if (v0 == v1) {
      return v0;
    }
    if (v0 < v1) {
      return (1 - amount) * v1 + amount * v0;
    }
    return (1 - amount) * v0 + amount * v1;
  }

  /**
   * Linearly interpolates 2 colors. Utility method for fading colors into each other.
   *
   * @param color1 The start color
   * @param color2 The color to fade into
   * @param amount The amount to interpolate. [0, 1]
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

  /**
   * Gets the length of the LED buffer.
   *
   * @return the length of the LED buffer.
   */
  public int getLength() {
    return this.buffer.getLength();
  }

  /** Flushes the buffer to the light strip. */
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

  /** Updates the LED rainbow pattern. Must be ran for it to animate. */
  public void updateRainbow() {
    this.targetStripColor = null;
    rainbowHue++;
    for (int led = 0; led < buffer.getLength(); led++) {
      this.buffer.setHSV(led, (rainbowHue + led) % 180, 255, 255);
    }
    this.flush();
  }

  private boolean stripAtTargetColor() {
    if (this.targetStripColor == null) {
      // targetStripColor being null means that the light strip has been manually changed, and we
      // should not touch the values.
      return true;
    }
    for (int led = 0; led < this.buffer.getLength(); led++) {
      // This is a kinda gross way to do this, but it removes the like 10 Math.floors or (int)s
      if (this.targetStripColor.toHexString() != this.buffer.getLED(led).toHexString()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Sets the speed {@link #setStripColor(Color)} fades with.
   *
   * @param speed The speed. [0, 1]
   */
  public void setFadeSpeed(double speed) {
    this.fadeSpeed = speed;
  }

  /** Resets the speed {@link #setStripColor(Color)} fades with to default; */
  public void resetFadeSpeed() {
    this.fadeSpeed = Constants.LightStrips.STRIP_FADE_SPEED;
  }

  @Override
  public void periodic() {
    boolean atTarget = stripAtTargetColor();
    this.targetLightEntry.setBoolean(atTarget);
    if (!atTarget) {
      for (int led = 0; led < this.buffer.getLength(); led++) {
        Color c = lerpColors(this.buffer.getLED(led), targetStripColor, fadeSpeed);
        this.buffer.setLED(led, c);
      }
      this.flush();
    }
  }
}
