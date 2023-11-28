package demolition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.*;

import java.lang.String;

import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.core.PImage;
import processing.core.PApplet;
/**
 * The Level class store all the GameObject that interact with BombGuy.
 */
public class Level implements Text{

  private BombGuy bombGuy;
  private Wall[][] wallMap;
  private char[][] map;
  private int timer;
  private GameObject clock;
  private int timeCounter;
  private ArrayList<Player> enemyArrayList;
  private ArrayList<Bomb> bombExplodeArrayList;
  /**
   *Constructor for Level
   */
  public Level(){
      this.wallMap = new Wall[13][15];
      this.map = new char[13][15];
      this.timer = 0;
      this.clock = new GameObject (300,15);
      this.timeCounter = 0;
      this.enemyArrayList = new ArrayList<Player>();
      this.bombExplodeArrayList = new ArrayList<Bomb>();
  }
  /**
   *Set the time with a new time
   *@param newTime a new Timeer for this level
   */
  public void setTimer(int newTime) {
    this.timer = newTime;
  }
  /**
   *Return the timer in the Level
   *@return timer a timer to indicate how many seconds that BombGuy have until loss
   */
  public int getTimer() {
    return this.timer;
  }
  /**
   *Return the char map of this Level
   *@return map the char[][], a 2D character array
   */
  public char[][] getMap() {
    return this.map;
  }
  /**
   *Return the wallMap of this Level
   *@return WallMap a 2D Wall array that store object array and its child
   */
  public Wall[][] getWallMap() {
    return this.wallMap;
  }
  /**
   *Return the BombGuy in this Level
   *@return the BombGuy object
   *@see BombGuy
   */
  public BombGuy getBombGuy() {
    return this.bombGuy;
  }
  /**
   *Determine if the BombGuy can advance the next Level
   *@return true, if the BombGuy can advance to next level, else false
   */
  public boolean advanceLevel() {
    return this.bombGuy.isOnGoal();
  }
  /**
   *Return the ArrayList that conatin Player objects
   *@return the enemeies store in ArrayList
   *@see <a href=https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html>ArrayList</a>
   */
  public ArrayList<Player> getEnemyList() {
    return this.enemyArrayList;
  }
  /**
   *determine if the BombGuy loss lives
   *@return true, if BombGuy loss live, else return false
   *@see BombGuy
   */
  public boolean lossLive() {
    //check if BombGuy loss live
    if (this.bombGuy.getLives() <= 0) {
      return true;
    }
    //check collide for each enemy
    for (int i = 0; i < enemyArrayList.size(); i++ ) {
      Player enemy = this.enemyArrayList.get(i);
      if (bombGuy.checkPlayerCollide(enemy)) {
        return true;
      }
    }
    return false;
  }
  /**
   *Load the 2D char game map from path string
   *@param str the txt file name that need to read
   */
  public void loadMap(String str) {
    int counter = 0;
    //read file
    try{
      File myFile = new File(str);
      Scanner reader = new Scanner(myFile);
      while (reader.hasNextLine()) {
        //read each line in the file and change it to char array
        char[] line = reader.nextLine().trim().toCharArray();
        for (int i = 0; i < line.length; i++){
          map[counter][i] = line[i];
        }
        counter += 1;
      }
      reader.close();
      //cactch error
    } catch (FileNotFoundException e) {
      System.out.println("No file");
      return;
    }
  }
  /**
   *Create all the gameObject from the 2D char arrau
   *@param app a PApplet object
   *@see Wall
   *@see Player
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#image-processing.core.PImage-float-float->image()</a>
   */
  public void createMap(PApplet app){
    //make the pixel, because the map need 64 pixel space on top
    int piexelCounterX = 0;
    int piexelCounterY = 64;
    //load the time PImage
    this.clock.setSprite(app, "src/main/resources/icons/clock.png");
    //loop through the row of the 2D char arry
    for (int i = 0; i < wallMap.length; i++) {
      piexelCounterX = 0;
      //the column of 2D char array
      for (int y = 0; y < wallMap[i].length; y++) {
        //create solidWall object
        if (map[i][y] == 'W') {
          wallMap[i][y] = new SolidWall(piexelCounterX, piexelCounterY);
          wallMap[i][y].setSprite(app, "src/main/resources/wall/solid.png");
        //create broken wall object
        } else if (map[i][y] == 'B') {
          wallMap[i][y] = new BrokenWall(piexelCounterX, piexelCounterY);
          wallMap[i][y].setSprite(app, "src/main/resources/broken/broken.png");
        //create gaoltile object
        } else if (map[i][y] == 'G') {
          wallMap[i][y] = new GoalTile(piexelCounterX, piexelCounterY);
          wallMap[i][y].setSprite(app, "src/main/resources/goal/goal.png");
        //else it is am empty space which is tile object
        } else {
          wallMap[i][y] = new Tile(piexelCounterX, piexelCounterY);
          wallMap[i][y].setSprite(app, "src/main/resources/empty/empty.png");
          //create BombGuys object
          if (map[i][y] == 'P') {
            this.bombGuy = new BombGuy(piexelCounterX, piexelCounterY, i, y, wallMap);
            this.bombGuy.setSprite(app, "src/main/resources/player/player");
          //create RedEnemy and add to the enemyArrayList
          } else if (map[i][y] == 'R') {
            EnemyRed enemyRed = new EnemyRed(piexelCounterX, piexelCounterY, i, y, wallMap);
            enemyRed.setSprite(app, "src/main/resources/red_enemy/red");
            enemyArrayList.add(enemyRed);
          //create YelloEnemy and add to the enemyArrayList
          } else if (map[i][y] == 'Y') {
            EnemyYellow enemyYellow = new EnemyYellow(piexelCounterX, piexelCounterY, i, y, wallMap);
            enemyYellow.setSprite(app, "src/main/resources/yellow_enemy/yellow");
            enemyArrayList.add(enemyYellow);
          }
        }
        piexelCounterX += 32;
      }
      piexelCounterY += 32;
    }
    //pass the enemyArrayList to the BombGuy object if the bombGuy exsists
    if (this.bombGuy != null) {
      this.bombGuy.setEnemyArrayList(enemyArrayList);
      this.bombExplodeArrayList = bombGuy.getbombArrayList();
    }
  }
  /**
   *Load all the in-game Object to this Level
   *@param app a PApplet object
   *@param str the txt file name that need to read
   *@param time the given time for this Level
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#image-processing.core.PImage-float-float->image()</a>
   */
  public void createLevel(PApplet app, String str, int time) {
    this.loadMap(str);
    this.createMap(app);
    this.setTimer(time);
  }
  /**
   *Tick eveything in this level, such as PlayerMove and bombExplode
   *@see Player#tick
   *@see Bomb#tick
   */
  public void tick() {
    //Bomb Tick
    bombExplodeArrayList.forEach((x) -> x.tick());
    //BombGuy tick
    this.bombGuy.tick();
    //Enemy tick
    for (int x = 0; x < enemyArrayList.size(); x++ ) {
      this.enemyArrayList.get(x).tick();
    }
    //Enemy Move
    for (int x = 0; x < enemyArrayList.size(); x++ ) {
      if (this.timeCounter == 60) {
        this.enemyArrayList.get(x).move();
        }
    }
    //BombGuy move
    bombGuy.move();
    //remove all the bomb that is exploded
    this.bombGuy.bombExplode();
    //count down the timer for each 60 frame
    if (timeCounter == 60) {
      timer -= 1;
      timeCounter = 0;
    }
    timeCounter += 1;
  }
  /**
   *Draw all the Game object store in the this Level
   *@param app a Papplet object
   *@see Player#draw
   *@see Wall#draw
   *@see Bomb#draw
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#image-processing.core.PImage-float-float->image()</a>
   */
  public void draw(PApplet app) {
    //draw clock
    this.clock.draw(app);
    this.text(app, Integer.toString(timer),340, 40);
    //draw all the wall object store in the wallMap
    for (int i = 0; i < wallMap.length; i++) {
      for (int y = 0; y < wallMap[i].length; y++) {
        wallMap[i][y].draw(app);
      }
    }
    //draw all the Bomb that is placed if there is any
    bombExplodeArrayList.forEach((x) -> x.draw(app));
    //render all the player, where the lower level render first
    for (int i = 0; i < 13; i++) {
      for (int y = 0; y < 15; y++) {
        //bomb guy draw\
        if (this.bombGuy.checkGrid(i, y))
          this.bombGuy.draw(app);
        //enemy draw
        for (int x = 0; x < enemyArrayList.size(); x++ ) {
          if (this.enemyArrayList.get(x).checkGrid(i, y)) {
            this.enemyArrayList.get(x).draw(app);
          }
        }
      }
    }
  }
}
