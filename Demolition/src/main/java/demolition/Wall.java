package demolition;

import processing.core.PImage;
import processing.core.PApplet;
/**
 *The Wall object store all the attributes that wall needed to interacte with Player object and Bomb object
 */
public abstract class Wall extends GameObject{
  /**
   *Determine if the wall is solid or broken that Player can not walk through
   *@see Player
   */
  protected boolean solid;
  /**
   *Determine if the wall is a GoalTile, that BombGuy can advance to next Level
   *@see BombGuy
   */
  protected boolean onGoal;
  /**
   *Determine if the wall is BroeknWall that it can be break by Bomb
   *@see Bomb
   */
  protected boolean broken;
  /**
   *Constructor for Wall object and it is inherited from gameObject
   *@param x  x-coordinate of the GameObject
   *@param y  y-coordinate of the GameObject
   *@see GameObject
   */
  public Wall(int x, int y) {
    super(x, y);
  }
  /**
   *Check if the wall object is solid
   *@return true if the wall is solid, or false if the wall is not
   */
  public boolean isSolid() {
    return this.solid;
  }
  /**
   *Check if the wall object is goal
   *@return true if the wall is GoalTile, or false if it is not
   */
  public boolean isOnGoal() {
    return this.onGoal;
  }
  /**
   *Check if the wall object is broken
   *@return true if the wall is broken, else return false
   */
  public boolean isBroken() {
    return this.broken;
  }
  /**
   *Abstact class
   *@param app, PApplet object
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#image-processing.core.PImage-float-float->image()</a>
   */
  public abstract void destory(PApplet app);
}
