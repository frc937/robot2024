// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot.commands.lightstrip;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.AddressableLightStrip;

/** Activates when the robot is disabled. */
public class DisabledLights extends Command {
  private AddressableLightStrip robotLights;
  private boolean firstStart = true;
  private int rainbowTick = 0;
  private int ledCount = 1;

  /** Creates a new RobotDisabledLights. */
  public DisabledLights() {
    this.robotLights = RobotContainer.robotLights;
    addRequirements(this.robotLights);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (!firstStart) {
      robotLights.setStripColor(Constants.LightStrips.Colors.DISABLED_COLOR);
      robotLights.flush();
    } else {
      robotLights.setFadeSpeed(0.01);
    }
    robotLights.startLights();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (firstStart) {
      rainbowTick++;
      for (int led = 0; led < ledCount; led++) {
        robotLights.setColorLight(
            led, Color.fromHSV((led + rainbowTick) % 180, 255, rainbowTick - led));
      }
      robotLights.flush();
      if ((rainbowTick >= robotLights.getLength() * 2)) {
        firstStart = false;
        robotLights.setStripColorRaw(Constants.LightStrips.Colors.BOOT_SEQUENCE_PULSE_COLOR);
        robotLights.setStripColor(Constants.LightStrips.Colors.DISABLED_COLOR);
      }
      ledCount = Math.min(ledCount + 1, robotLights.getLength());
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    firstStart = false;
    robotLights.resetFadeSpeed();
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
