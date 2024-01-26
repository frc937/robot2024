# Team 937's code for the 2024 FRC season

[![The Compile State of this Code](https://github.com/frc937/robot2024/actions/workflows/compile.yml/badge.svg)](https://github.com/frc937/robot2024/actions/workflows/compile.yml)

### The Spec Sheet for our Robot
![A picture of team 937's spec sheet for the robot.](./pictures/Robot%20Specs.png)

### Summary of how our Robot Functions
1. Run Intake to intake a Note
2. Stop Intake when Index Switch senses a Note
3. Align bot with Amp using Limelight
4. Spin up Output while extending Piston
5. When Position Switch detects Tower is in upright position, run Intake to index Note to Output
7. When button is released, stop Intake and Output and retract Piston.

### Basic Introduction to our Code

- We use Command-Based Programming
[[Command-Based Programming WPILib Documentation](https://docs.wpilib.org/en/stable/docs/software/commandbased/index.html)]
- We use Java
- We use a global Constants file to allow easy access to any constants.
- RobotContainer contains objects of our subsystems and commands, for use throughout all of our code. This lets us avoid dependency injection.
- We have javadoc on most of our code.

### Asimov's Laws:
 * The First Law: A robot may not injure a human being or, through inaction, allow a human being to come to harm.
 * The Second Law: A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.
 * The Third Law: A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.

