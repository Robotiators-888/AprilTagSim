// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AprilTag;


import frc.robot.subsystems.SUB_PhotoVision;
import edu.wpi.first.wpilibj2.command.CommandBase;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.*;

public class CMD_aprilTest extends CommandBase {
    SUB_PhotoVision photoVision;
  
  /**
   * turn to goal using aprilTag
   * @param photoVision
   * @param driveIn
   */
  public CMD_aprilTest(SUB_PhotoVision photoVision) {
    this.photoVision = photoVision;
    addRequirements(photoVision);
  }

  @Override
  public void initialize() {
    
  }

  @Override
  public void execute() {
    System.out.print("aprilTest: in execute\n");
    photoVision.test();
  }

  @Override
  public void end(boolean interrupted) {
   

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
  return false;
  }







}
