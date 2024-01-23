// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.mailbox;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Belt extends SubsystemBase {
  private CANSparkMax beltMotor;
  
  public Belt() {
    beltMotor = new CANSparkMax(Constants.Belt.BELT_DEVICE_ID, MotorType.kBrushless);
  }

  /**
   * Runs the belt.
   */
  public void runBelt(){
    beltMotor.set(Constants.Belt.BELT_MOTOR_SPEED);
  }

  /**
   * Stops the belt.
   */
  public void stop(){
    beltMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
