package demolition;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.*;
import java.util.Arrays;

import processing.core.PImage;
import processing.core.PApplet;
/**
 * The Bomb class determine where the bomb going to explode and how does the explosion impact on other GameObject such as wall and player
 */
public class Bomb extends GameObject {
  /**
   *The ExplosionDirection enum has three direction that Bomb can impact on
   */
  public enum ExplosionDirection {
    /**
     *Explosion impact along the x asix
     */
    horizontal,
    /**
     *Explosion impact along the y asix
     */
    vertical,
    /**
     *Explosion impact on where the Bomb is place
     */
    centre
  }
  private int matrixX;
  private int matrixY;
  private int timeTillExplorsion;
  private PImage[] pImageArray;
  private HashMap<ExplosionDirection, PImage> explosionHashMap;
  private ExplosionDirection[] direction;
  private Wall[][] wallMap;
  private HashMap<int[], PImage> explosionAnimationHashMap;
  private ArrayList<Player> enemyArrayList;
  private BombGuy bombGuy;
  private int index;
  /**
   *Constructor for Bomb
   *@param x  x-coordinate of the Bomb
   *@param y  y-coordinate of the Bomb
   *@param matrixX  the grid position x of the Bomb, it indicates which row the Bomb is in
   *@param matrixY  the grid position y of the Bomb, it indicates which column the Bomb is in
   *@param wallMap  the 2-D array that store all the wall object and its child
   *@param enemyArrayList the arrayList that store players that classify as enemies
   *@param bombGuy the BombGuy that placed the bomb
   *@see Player
   *@see GameObject
   *@see Wall
  */
  public Bomb(int x, int y, int matrixX, int matrixY, Wall[][] wallMap, ArrayList<Player> enemyArrayList, BombGuy bombGuy) {
    super (x, y+16);
    this.matrixX = matrixX;
    this.matrixY = matrixY;
    this.timeTillExplorsion = 149;
    this.pImageArray = new PImage[8];
    this.explosionHashMap = new HashMap<ExplosionDirection, PImage>();
    this.explosionAnimationHashMap = new HashMap<int[], PImage>();
    this.direction = ExplosionDirection.values();
    this.wallMap = wallMap;
    this.enemyArrayList = enemyArrayList;
    this.bombGuy = bombGuy;
    this.index = 0;
  }
  /**
   *Set the Pimage that represent the Bomb ticking animation and store into Bomb pImageArray and it is overloading method
   *@param app the PApplet object
   *@param path a String that lead to the image need to load
   *@see GameObject#setSprite(PApplet app, String path)
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#image-processing.core.PImage-float-float->image()</a>
  */
  @Override
  public void setSprite(PApplet app, String path) {
    String str = path +".png";
    pImageArray[0] = app.loadImage(str);

    for (int y = 1; y < 8; y++) {
      str = path + Integer.toString(y) +".png";
      pImageArray[y] = app.loadImage(str);
    }
  }
  /**
   *Animated the explosion on the wallMap, if there is solid or broken wall, animation stop. Destroy the broken wall upon explosion
   *@param direction ExplosionDirection value to indicate which PImage should be used
   *@param x the pixel location of x-axis the explorsion should take placed
   *@param y the pixel location of y-axis the explorsion should take placed
   */
  public void blockExplosion(ExplosionDirection direction, int x, int y) {
    int checkMatrixX;
    int checkMatrixY;
    int[] explosionCoord = new int [4];

    for (int i = 1; i <= 2; i++) {
      explosionCoord = new int [4];
      explosionCoord[0] = this.x + (y*32*i);
      explosionCoord[1] = this.y + (x*32*i);
      explosionCoord[2] = this.matrixX + x*i;
      explosionCoord[3] = this.matrixY + y*i;
      //check if it is a solid wall/broken wall
      if (this.wallMap[explosionCoord[2]][explosionCoord[3]].isSolid() == false) {
        this.explosionAnimationHashMap.put(explosionCoord, this.explosionHashMap.get(direction));
      } else {
        //if it is a broken wall stop upon explosion
        if (this.wallMap[explosionCoord[2]][explosionCoord[3]].isBroken()) {
          this.explosionAnimationHashMap.put(explosionCoord, this.explosionHashMap.get(direction));
        }
        break;
      }
    }
  }
  /**
   *Set the Explosion animation by the given path
   *@param app the PApplet object
   *@param path a stirng that lead to the image location
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#image-processing.core.PImage-float-float->image()</a>
   */
  public void setExplosionSprite(PApplet app, String path) {
    String str;
    //loop through the diection value so the explosion can load in the corresponding key
    for (int i = 0; i < direction.length; i++) {
      str = path + direction[i] + ".png";
      this.explosionHashMap.put(direction[i], app.loadImage(str));
    }
    this.blockExplosion(direction[2], 0, 0);
    this.blockExplosion(direction[1], 1, 0);
    this.blockExplosion(direction[1],-1, 0);
    this.blockExplosion(direction[0],0, 1);
    this.blockExplosion(direction[0],0, -1);
  }
  /**
   *Return the explorsion direction array
   *@return ExplosionDirection enmu value
   */
  public ExplosionDirection[] getExplosionDirection() {
    return this.direction;
  }
  /**
   *Return the count down time that Bomb going to explode
   *@return the Bomb explosion count down time
   */
  public int getTime() {
    return this.timeTillExplorsion;
  }
  /**
   *Check if the explosion impact have killed the BombGuy
   *@see BombGuy
   */
  public void checkDie() {
    for (int[] i: this.explosionAnimationHashMap.keySet()) {
      if (this.bombGuy.blastDie(i[2], i[3])) {
        this.bombGuy.lossLive();
      }
    }
  }
  /**
   *Check if the Bomb have killed the enemy in the enemyArrayList and remove if there is any
   *@see Player#blastDie
   */
  public void blastDieBomb() {
    for (int[] i: this.explosionAnimationHashMap.keySet()) {
      this.enemyArrayList.removeIf((x) -> x.blastDie(i[2], i[3]));
    }
  }
  /**
   *Draw the entire Bomb anaimatin by combine all the methods above
   *@param app the PApplet object
   */
  @Override
  public void draw(PApplet app) {
    //Bomb explode in the last 0.5 second
    if (this.index <= 1) {
      this.explosionAnimationHashMap.forEach((x, y) -> this.wallMap[x[2]][x[3]].destory(app));
      this.explosionAnimationHashMap.forEach((x,y) -> app.image(y, x[0], x[1]));
    } else {
      //i used reversed order
      app.image (this.pImageArray[9-this.index], x, y);
    }
  }
  /**
   *Tick the bomb
   */
  public void tick() {
    //use index to check which frame we are on and determine which second the time should explode
    //the entire 150 = 60 fps in sec * 2 + 30 fps in 0.5 sec
    //divde each part in 10 so from 2-9 the Bomb tick and 0,1 0.5 sec the Bomb explode
    this.index = (this.timeTillExplorsion/15) % 10;
    //each time a frame pass, time going down. noted the inital Bomb time is 149, because 0 - 149 = 150
    this.timeTillExplorsion -= 1;
    //During the explorsion, player die in the impact same as broekenWall
    if (this.index == 1) {
      this.blastDieBomb();
      this.checkDie();
    }
  }
}
