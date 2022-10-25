package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.*;

import edu.wpi.first.math.Pair;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SUB_PhotoVisionNT extends SubsystemBase {
  NetworkTable table;

  public SUB_PhotoVisionNT() {
    table = NetworkTableInstance.getDefault().getTable("photonvision/Full_HD_webcam");
    System.out.println("got network table photovision");
  }

  public void periodic() {
    

  }

  // checks if target is availible
  public boolean targetFound(){
    return table.getEntry("hasTarget").getBoolean(false);
  }
 
  // X coordinate of the target on the camera, in cartesian
  public double targetCameraXcord(){
    // make sure to check if target avalible before using.
    // And set camera Res in constants
      return table.getEntry("targetPixelsX").getDouble(0) - Constants.cameraXRes/2;
  }
  // Q: How do we detect multiple targets

  

 
}