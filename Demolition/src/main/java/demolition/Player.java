package demolition;

import processing.core.PImage;
import processing.core.PApplet;

import java.util.HashMap;
import java.util.ArrayList;
/**
 *The Player class is a abstract class for all the movable object in the game such as BombGuy, Red enemy and Yellow enemy. It is inherited from the GameObject
 */
public abstract class Player extends GameObject{
  /**
   *Each grid space in term of pixel size, so Player can move one grid
   */
  protected final int speed = 32;
  /**
   *The grid position x of the Player, it indicates which row the Player is in
   */
  protected int matrixX;
  /**
   *The grid position y of the Player, it indicates which column the Player is in
   */
  protected int matrixY;
  /**
   *The 2-D array that store all the wall object and its child
   */
  protected Wall[][] wallMap;
  /**
   *The image HashMap that store Direction as key and an array of PImage as value
   */
  protected HashMap<Direction, PImage[]> imageHashMap;
  /**
   *Use a to cycle the walk animation
   */
  protected int counter;
  /**
   *The direction that Player face
   */
  protected Direction move;
  /**
   *The direction array store the values from Direction enum
   */
  protected Direction[] direction;
  /**
   *An index to help us in walk anaimation cycle
   */
  protected int index;
  /**
   *Constructor for Player
   *@param x  x-coordinate of the Player
   *@param y  y-coordinate of the Player
   *@param matrixX  the grid position x of the Player, it indicates which row the Player is in
   *@param matrixY  the grid position y of the Player, it indicates which column the Player is in
   *@param wallMap  the 2-D array that store all the wall object and its child
   *@see GameObject
   *@see Wall
   */
  public Player(int x, int y, int matrixX, int matrixY, Wall[][] wallMap) {
    super(x, y-16);
    this.matrixX = matrixX;
    this.matrixY = matrixY;
    this.wallMap = wallMap;
    this.counter = 0;
    this.imageHashMap = new HashMap<Direction, PImage[]>();
    this.direction = Direction.values();
    this.move = direction[2];
    this.index = 0;
  }
  /**
   *Return the column number of the Player
   *@return the int value of the column number
   */
  public int getMatrixY() {
    return this.matrixY;
  }
  /**
  *Return the index of the walk animation cycle
  *@return the int value of index
  */
  public int getIndex() {
    return this.index;
  }
  /**
   *Return the row number of the Player
   *@return the int value of the row number
   */
  public int getMatrixX() {
    return this.matrixX;
  }
  /**
   *Set which direction the player is facing
   *@param direction the Direction value from Direction enmu
   *@see Direction
   */
  public void setDraw(Direction direction) {
    this.move = direction;
  }
  /**
   *Draw the player animation walk cycle
   *@param app the PApplet object
   *@see GameObject#setSprite(PApplet app, String pathe)
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#image-processing.core.PImage-float-float->image()</a>
   */
   @Override
   public void draw(PApplet app) {
      PImage[] spriteArray = this.imageHashMap.get(move);
      app.image(spriteArray[this.index], this.x, this.y);
    }
  /**
   *Cycle the animation by tick
   */
  public void tick() {
    this.index = (this.counter/12) % 4;
    this.counter += 1;
  }
  /**
  *Set the Pimage and store into player pImageArray and it is overloading method
  *@param app the PApplet object
  *@param path a String that lead to the image need to load
  *@see GameObject#setSprite(PApplet app, String path)
  *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#image-processing.core.PImage-float-float->image()</a>
  */
  @Override
  public void setSprite(PApplet app, String path) {
    PImage [] pImageArray = new PImage[4];
    String str;
    //set the walk cycle animation into array
    for (int i = 0; i < 4; i++) {
      pImageArray = new PImage[4];
      //image number start in 1
      for (int y = 1; y < 5; y++) {
        str = path + "_" + direction[i] + Integer.toString(y) +".png";
        pImageArray[y-1] = app.loadImage(str);
      }
      this.imageHashMap.put(direction[i], pImageArray);
    }
  }
  /**
   *Check if the player is collided to the SolidWall or BroeknWall
   *@param move, the Direction value from Direction enmu
   *@return true, if the player did not collide the wall and the postion is updated, else return false and Player is collided to the wall
   *@see SolidWall
   *@see BrokenWall
   */
  public boolean checkCollide(Direction move) {
    //create temp grid postion
    int checkMoveX = this.matrixX;
    int checkMoveY = this.matrixY;
    //defence programming
    if (this.wallMap == null) {
      return false;
    }
    //check if collsion is happened
    //check if the up position is blocked by updated the temp position
    if (move == direction[0]) {
      checkMoveX = this.matrixX - 1;
    //check if the right position is blocked by updated the temp position
    } else if (move == direction[1]) {
      checkMoveY = this.matrixY + 1;
    //check if the down position is blocked by updated the temp position
    } else if (move == direction[2]) {
      checkMoveX = this.matrixX + 1;
      //check if the left position is blocked by updated the temp position
    } else if (move == direction[3]) {
      checkMoveY = this.matrixY - 1;
    }
      //check if the position is blcoked on the current temp position
    if (this.wallMap[checkMoveX][checkMoveY].isSolid() == false) {
      //update position if it is true
      this.matrixX = checkMoveX;
      this.matrixY = checkMoveY;
      return true;
    } else {
      //dont updat if it is false and return false
      return false;
    }
  }
  /**
   *Move the Player depend on the Player type such as BombGuys and enemies
   */
   public abstract void move();
  /**
   *Check if the Player object is killed by the Bomb
   *@param explosionX the row number of the bomb explosion impact position in the wallMap
   *@param explosionY the column number of the bomb explosion impact position in teh wallMap
   *@return true, if the bomb explosion did impact the player, else return false
   */
  public boolean blastDie (int explosionX, int explosionY) {
    if ( this.matrixX == explosionX && this.matrixY == explosionY) {
      return true;
    }
    return false;
  }
  /**
   *Check if the Player object is one the grid position that is provided
   *@param x the row number in the wallMap grid
   *@param y the column number in the wallMap grid
   *@return true, if the player is on the postion grid, else return false
   */
  public boolean checkGrid(int x, int y) {
    if (this.matrixX == x && this.matrixY == y) {
      return true;
    }
    return false;
  }
}
