package demolition;

import processing.core.PImage;
import processing.core.PApplet;
/**
 *The yellow Enemy that move in a certain direction cycle after collided the solid or broken wall
 */
public class EnemyYellow extends Player implements Enemy {
  private int moveCounter;
  /**
  *Constructor for Yellwo Enemy inherited from Player
  *@param x x-coordinate of the Yellow Enemy
  *@param y y-coordinate of the Yellow Enemy
  *@param matrixX  the grid position x of the Yellow Enemy, it indicates which row the Yellow Enemy is in
  *@param matrixY  the grid position y of the Yellow Enemy, it indicates which column the Yellow Enemy is in
  *@param map  the 2-D array that store all the wall object and its child
  *@see Player#Player
  *@see GameObject
  *@see Wall
  */
  public EnemyYellow(int x, int y, int matrixX, int matrixY, Wall[][] map) {
    super(x, y, matrixX, matrixY, map);
    this.moveCounter = 1;
  }
  /**
   *Yellow Enemy can auto find path after collide the wall with the help of enmu Direction cycle
   *@see Player#move
   *@see Enemy
   *@see Direction
   */
  @Override
  public void move() {
    //Keep looking for way out until it is not collide with the wall
    while (this.checkCollide(this.move) == false) {
      //Use the Modular arithmetic to cycle the Direction enum
      int num = (this.move.ordinal() + this.moveCounter) % 4;
      this.move = direction[num];
    }
    this.enemyMovePattern(this);
  }
}
