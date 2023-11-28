package demolition;

import processing.core.PImage;
import processing.core.PApplet;
/**
 * The GoalTile object to indicated that BombGuy can advance to next level
 */
public class GoalTile extends Wall {
  /**
   * The Constructor for GoalTile and it is inherited by Wall object
   *@see Wall
   *@param x x-coordinate of the GameObject
   *@param y y-coordinate of the GameObject
   */
  public GoalTile(int x, int y) {
      super(x,y);
      this.solid = false;
      this.onGoal = true;
      this.broken = false;
  }
  /**
   *@see Wall
   */
  @Override
  public void destory(PApplet app) {
    //Because it is not BrokenWall so doesnt change
    return;
  }
}
