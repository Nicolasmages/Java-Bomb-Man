package demolition;

import processing.core.PImage;
import processing.core.PApplet;
/**
 *The BroeknWall object to indicate that BombGuy can not walk throughl
 */
public class SolidWall extends Wall {
  /**
   *The Constructor for SolidWall and it is inherited by Wall object
   *@see Wall
   *@param x x-coordinate of the GameObject
   *@param y y-coordinate of the GameObject
   */
  public SolidWall(int x, int y) {
    super(x, y);
    this.solid = true;
    this.onGoal = false;
    this.broken = false;
  }
  /**
   *@see Wall
   */
  @Override
  public void destory(PApplet app) {
    //Because it is a solid wall so it can not be destroyed
    return;
  }
}
