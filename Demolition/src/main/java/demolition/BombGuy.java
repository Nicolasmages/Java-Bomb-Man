package demolition;

import processing.core.PImage;
import processing.core.PApplet;

import java.util.ArrayList;
/**
 *The BombGuy class is the character that user can control and interact with the game, it is inherited from Player
 */
public class BombGuy extends Player{

  private boolean keyPressed;
  private ArrayList<Bomb> bombArrayList;
  private ArrayList<Player> enemyArrayList;
  private int lives;
  private int keyCode;
  /**
   *Constructor for BombGuy inherited from Player
   *@param x x-coordinate of the BombGuy
   *@param y y-coordinate of the BombGuy
   *@param matrixX  the grid position x of the BombGuy, it indicates which row the BombGuy is in
   *@param matrixY  the grid position y of the BombGuy, it indicates which column the BombGuy is in
   *@param wallMap  the 2-D array that store all the wall object and its child
   *@see Player#Player
   *@see GameObject
   *@see Wall
   */
  public BombGuy(int x, int y, int matrixX, int matrixY, Wall[][] wallMap) {
    super(x, y, matrixX, matrixY, wallMap);
    this.keyPressed = false;
    this.bombArrayList = new ArrayList<Bomb>();
    this.enemyArrayList = new ArrayList<Player>();
    this.lives = 1;
    this.keyCode = 0;
  }
  /**
   *BombGuy loss life and update the life in bombGuy
   */
  public void lossLive() {
    this.lives -= 1;
  }
  /**
   *Set the enemyArrayList to a new ArrayList that conatins Player
   *@param newEnemyArrayList arrayList that conatin Player
   */
  public void setEnemyArrayList(ArrayList<Player> newEnemyArrayList) {
    if (newEnemyArrayList != null || newEnemyArrayList.size() != 0){
      this.enemyArrayList = newEnemyArrayList;
    } else {
      this.enemyArrayList = null;
    }
  }
  /**
   *Return the ArrayList that store all the Bomb objects
   *@return the arraylist that store all the Bombs
   *@see <a href=https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html>ArrayList</a>
   */
  public ArrayList<Bomb> getbombArrayList() {
    return this.bombArrayList;
  }
  /**
   *return lives that BombGuy have
   *@return the lives of the BombGuy
   */
  public int getLives() {
    return this.lives;
  }
  /**
   *Check if the BombGoal is on the GoalTile
   *@return true, if the BombGuy is on the GoalTile, else return false
   */
  public boolean isOnGoal() {
    return this.wallMap[matrixX][matrixY].isOnGoal();
  }
  /**
   *Determine if the user is enter an input and change the boolean keyPressed to true
   *@param newKeyCode, int value keyCode for move
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#keyCode>keyCode</a>
   */
  public void pressKey(int newKeyCode) {
    this.keyPressed = true;
    this.keyCode = newKeyCode;
  }
  /**
   *Place a Bomb in the current BombGuy postion and add it to the bombArrayList
   *@param app, PApplet object
   *@see Bomb#Bomb
   */
  public void placeBomb(PApplet app) {
    Bomb bomb = new Bomb (this.x, this.y, this.matrixX, this.matrixY, this.wallMap, this.enemyArrayList, this);
    bomb.setSprite(app, "src/main/resources/bomb/bomb");
    bomb.setExplosionSprite(app, "src/main/resources/explosion/");
    this.bombArrayList.add(bomb);
  }
  /**
   *Check if the BombGuy is collide with other Players
   *@param enemy, Player object or its child
   *@return true, if BombGuy collides with other Player object, else return false
   */
  public boolean checkPlayerCollide(Player enemy) {
    if ( this.matrixX == enemy.matrixX && this.matrixY == enemy.matrixY) {
      return true;
    }
    return false;
  }
  /**
   *Check if the Bomb is detonated and remove the Bomb in the bombArrayList if it exploded
   */
  public void bombExplode() {
    this.bombArrayList.removeIf(x -> x.getTime() == -1);
  }
  /**
   *Control the BombGuy by the user input
   *@see Player#move
   *@see <a href=https://docs.oracle.com/javafx/2/api/javafx/scene/input/KeyCode.html>KeyCode</a>
   */
  @Override
  public void move() {
    if (this.keyPressed){
      //BombGuy move up if the keyCode is 38
      if (this.keyCode == 38) {
        //If bombGuy does not collide with the wall update the y axis position going up with 32 pixel
        if (this.checkCollide(direction[0])) {
          this.y -= speed;
        }
        //No matter if the position is updated or not the BombGuy will cycle the walk animation the corresponding the user input
        this.setDraw(direction[0]);
      //If bombGuy does not collide with the wall update the x axis position going right with 32 pixel
      }else if (this.keyCode == 39) {
        if (this.checkCollide(direction[1])) {
          this.x += speed;
        }
        //No matter if the position is updated or not the BombGuy will cycle the walk animation the corresponding the user input
        this.setDraw(direction[1]);
        //If bombGuy does not collide with the wall update the y axis position going down with 32 pixel
      }else if (this.keyCode == 40) {
        if (this.checkCollide(direction[2])) {
          this.y += speed;
        }
        //No matter if the position is updated or not the BombGuy will cycle the walk animation the corresponding the user input
        this.setDraw(direction[2]);
        //If bombGuy does not collide with the wall update the x axis position going left with 32 pixel
      }else if (this.keyCode == 37) {
        if (this.checkCollide(direction[3])) {
          this.x-=speed;
        }
        //No matter if the position is updated or not the BombGuy will cycle the walk animation the corresponding the user input
        this.setDraw(direction[3]);
      }
      //Because the user is already pressed the key, the program will not run anymore input until next input
      this.keyPressed = false;
    }
  }
}
