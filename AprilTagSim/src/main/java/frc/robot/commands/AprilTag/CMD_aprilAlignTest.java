// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AprilTag;

//import frc.robot.subsystems.SUB_Drivetrain;
import frc.robot.subsystems.SUB_PhotoVision;
import edu.wpi.first.wpilibj2.command.CommandBase;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.*;

public class CMD_aprilAlignTest extends CommandBase {
    SUB_PhotoVision photoVision;
  //SUB_Drivetrain drivetrain;
  
  /**
   * turn to goal using aprilTag
   * @param photoVision
   * @param driveIn
   */
  public CMD_aprilAlignTest(SUB_PhotoVision photoVision) {
    this.photoVision = photoVision;
    addRequirements(photoVision);
  }

  @Override
  public void initialize() {
    
  }

  @Override
  public void execute() {
        PhotonTrackedTarget centerYTarget = photoVision.getCenterYTarget();
        if (centerYTarget != null){
          double xCoord = photoVision.getOnscreenXcoord(centerYTarget);

          if ((Math.abs(xCoord) > 35)) {
          System.out.println("Motor out: Left: "+Math.signum(xCoord) * 0.3 + " Right: "+ Math.signum(xCoord) * -0.3+" XCoord: "+xCoord);

          } else {
              System.out.println("Motor out: Left: "+(0.006) * (xCoord)+" Right: "+(-0.006) * (xCoord)+" XCoord: "+xCoord);

          }
        } 
        else{
          System.out.println("centerTarget is null");
        }
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("Motor out: Left: 0 Right: 0");

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    PhotonTrackedTarget centerYTarget = photoVision.getCenterYTarget();
      if (centerYTarget != null && 10 > Math.abs(photoVision.getOnscreenXcoord(centerYTarget))){
        return true;
      }
    return false;
  }







}
