package demolition;

import processing.core.PImage;
import processing.core.PApplet;

import java.util.Random;
/**
 *The Red Enemy that can move in random position after collided the solid or broken wall
 */
public class EnemyRed extends Player implements Enemy {
  private Random random;
  private Direction previousMove;
  /**
   *Constructor for Red Enemy inherited from Player
   *@param x x-coordinate of the Red Enemy
   *@param y y-coordinate of the Red Enemy
   *@param matrixX  the grid position x of the Red Enemy, it indicates which row the Red Enemy is in
   *@param matrixY  the grid position y of the Red Enemy, it indicates which column the Red Enemy is in
   *@param map  the 2-D array that store all the wall object and its child
   *@see Player#Player
   *@see GameObject
   *@see Wall
   */
  public EnemyRed(int x, int y, int matrixX, int matrixY, Wall[][] map) {
    super(x, y, matrixX, matrixY, map);
    this.random = new Random();
    this.previousMove = this.move;
  }
  /**
   *Red Enemy can auto find path after collide the wall with the help of Random Object and implementation of enemy interface
   *@see Player#move
   *@see Enemy
   *@see <a href=https://docs.oracle.com/javase/8/docs/api/java/util/Random.html>Random</a>
   */
  @Override
  public void move() {
    while (this.checkCollide(this.move) == false) {
      this.move = direction[random.nextInt(4)];
    }
    this.enemyMovePattern(this);
  }
}
