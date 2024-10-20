// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

// One piece - the anime thing
/**
 * Auto that manually drives to the amp and offloads the note. Made due to faulty limelight at comp.
 */
public class OnePieceAutoButItWorksISwear extends SequentialCommandGroup {
  /** Creates a new OnePieceAutoButItWorksISwear. */
  public OnePieceAutoButItWorksISwear() {

    if (RobotContainer.isRedAlliance()) {
      addCommands(new DriveToAmpRed(), new DumpNote());
    } else {
      addCommands(new DriveToAmpBlue(), new DumpNote());
    }
  }
}
