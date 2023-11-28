package demolition;

import processing.core.PImage;
import processing.core.PApplet;
/**
 * The Tile object to indicate that BombGuy can walk through
 */
public class Tile extends Wall {
  /**
   *The Constructor for Tile and it is inherited by Wall object
   *@see Wall
   *@param x x-coordinate of the GameObject
   *@param y y-coordinate of the GameObject
   */
  public Tile(int x, int y) {
    super(x,y);
    this.solid = false;
    this.onGoal = false;
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
