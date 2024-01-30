// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.
 */

import org.junit.jupiter.api.Test;

public class BaseTests {

  /* HEY! If your test is failing, don't comment this out! Your code won't work on the robot. So fix it! /lh /nm */
  @Test
  public void TestAutoTasks() {
    new RobotContainer();
  }
}