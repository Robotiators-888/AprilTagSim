package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;
import org.photonvision.*;

import edu.wpi.first.math.Pair;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class SUB_PhotoVision extends SubsystemBase {
    PhotonCamera camera;

  public SUB_PhotoVision() {
      // Change this to match the name of your camera
   this.camera = new PhotonCamera(Constants.cameraName);

  }

  public void periodic() {
   
  }




  public Pair<Boolean, List<PhotonTrackedTarget>> getTargetInfo(){
    var targetInfo = this.camera.getLatestResult();
    System.out.println("targetInfo: "+targetInfo);
      
    boolean hasTargets = targetInfo.hasTargets();
    System.out.println("hasTargets: "+hasTargets);
    if (hasTargets){
      // has targets
      System.out.println("getTargetInfo: has targets");
      SmartDashboard.putBoolean("hasTargets", true);
      return new Pair<>(true, targetInfo.getTargets());
    }
    else{  
      // no targets
      System.out.println("getTargetInfo: no targets");
      SmartDashboard.putBoolean("hasTargets", false);
      return new Pair<>(false, null);
    }
  }

  public void test(){
   PhotonTrackedTarget centerTarget = getCenterYTarget();
    if (centerTarget != null){
      System.out.println("centerTag: not null, TAGID:"+centerTarget.getFiducialId());
      SmartDashboard.putNumber("tagOnscreenX",getOnscreenYcoord(centerTarget));
    }
    else{
      System.out.println("centerTag: null");
      SmartDashboard.putNumber("tagOnscreenX",0);
    }
  }

  // photovision does not include best target feature yet, so we have to do it ourselves
 public PhotonTrackedTarget getCenterYTarget(){
  Pair<Boolean, List<PhotonTrackedTarget>>  targetInfo = getTargetInfo();
  
  if (targetInfo.getFirst()){
    // has targets
    // our best target is closest to x=0 (center of screen)

    PhotonTrackedTarget bestTarget = targetInfo.getSecond().get(0);
    System.out.println("bestTarget: "+bestTarget.getCorners());
    double bestTargetScore = Math.abs(getOnscreenYcoord(bestTarget));
    // sort through all targets and find the closest one to the center of the screen 
    for (PhotonTrackedTarget target : targetInfo.getSecond()){
      double targetScore = Math.abs(getOnscreenYcoord(target));
      if (targetScore < bestTargetScore){
        bestTarget = target;
        bestTargetScore = targetScore;
      }
     
    }
    return bestTarget;
  }
  else{
    // no targets
    System.out.println("Centertarget: no targets");
    return null;
  }
 }

// gets the target with a specific aprilTag id, otherwise returns null
 public PhotonTrackedTarget getTargetById(int id){
  Pair<Boolean, List<PhotonTrackedTarget>>  targetInfo = getTargetInfo();
  
  if (targetInfo.getFirst()){
    // has targets
    // sort through all targets and find the one with the matching id
    for (PhotonTrackedTarget target : targetInfo.getSecond()){
      if (target.getFiducialId() == id){
        return target;
      }
    }
    return null;
  }
  else{
    // no targets
    return null;
  }
 }

 


 // Y coordinate of the center of target on the camera, in cartesian
public double getOnscreenYcoord(PhotonTrackedTarget target){
  TargetCorner c1 = target.getCorners().get(0);
  TargetCorner c2 = target.getCorners().get(3);

  SmartDashboard.putNumber("c1.y", c1.y);
  SmartDashboard.putNumber("c2.y", c2.y);
  return ((c1.y+c2.y)/2)-(Constants.cameraYRes/2);
}

 // X coordinate of the center of target on the camera, in cartesian
 public double getOnscreenXcoord(PhotonTrackedTarget target){
  TargetCorner c1 = target.getCorners().get(0);
  TargetCorner c2 = target.getCorners().get(3);

  SmartDashboard.putNumber("c1.x", c1.x);
  SmartDashboard.putNumber("c2.x", c2.x);
  return ((c1.x+c2.x)/2)-(Constants.cameraXRes/2);
}


 /**
   * calculates rpm for shooter to spin at given distance in inches from goal
   * @param dist false for rpm for low
   * @param shootHigh true for rpm for high goal
   * @return returns the rpm to hit specified goal
   */
  public int distRpm(double dist, boolean shootHigh) {

    if(shootHigh == true){
      return (int) ((24*dist) + 880);

    }
    else{

      return (int) (325 * (Math.sqrt(dist)));

    }
    
  } 


}