package demolition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.Scanner;

import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.core.PImage;
import processing.core.PApplet;

import java.util.ArrayList;
/**
 *The collection of all the Level, manage if it is game over, or winner winner chicken dinner
 */
public class Levels implements Text{

  private ArrayList<Level> levels;
  private int lives;
  private GameObject icon;
  private int index;
  private ArrayList<Level> orgionalLevels;
  private ArrayList<String> pathArrayList;
  private ArrayList<Integer> timeArrayList;
  private boolean noFile;
  private String path;

  /**
   *Constructor for Levels that store all the Level in ArrayList
   */
  public Levels(){
    this.levels = new ArrayList<Level>();
    this.icon = new GameObject (100,15);
    this.index = 0;
    this.lives = 0;
    this.pathArrayList = new ArrayList<String>();
    this.timeArrayList = new ArrayList<Integer>();
    this.path = "config.json";
  }
  /**
   *Return the cureent Level that BombGuy is on
   *@return Level object
   */
  public Level getLevel() {
    return this.levels.get(this.index);
  }
  /**
   *Return the index of the current Level
   *@return the index integer
   *@see <a href=https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html>ArrayList</a>
   */
  public int getIndex() {
    return this.index;
  }
  /**
   *Determine if the file is corretly loaded
   *@return true, if the file is not loaded corretly, else false
   */
  public boolean getNoFile() {
      return this.noFile;
  }
  /**
   *Return the number of Level store in the Levels
   *@return the integer value of number of levels
   */
  public int numOfLevel() {
    return this.levels.size();
  }
  /**
   *Chaneg the new Json file that need to read
   *@param newPath a string that lead to the new Json file
   */
  public void changePath(String newPath) {
    this.path = newPath;
  }
  /**
   *Diplay the loss screen
   *@param app PApplet object
   */
  public void drawDie(PApplet app) {
    this.text(app, "GAME OVER", 170, 240);
  }
  /**
   *Diplay the win screen
   *@param app PApplet object
   */
  public void drawWin(PApplet app) {
    this.text(app, "YOU WIN", 170, 240);
  }
  /**
   *Return how many lives left in this Levels
   *@return the number of live left
   */
  public int getLives() {
    return this.lives;
  }
  /**
   *Check if the BombGuy is dead or not
   *@return true, if the BombGuy is Alive or false
   */
  public boolean isAlive() {
    return this.lives != 0;
  }
  /**
   *Read the Json file and store the information in Json file in corresponding Level
   *@param app,the PApplet object
   */
  public void setup(PApplet app) {
    try {
      //initalise the json object so we can read
      JSONObject obj = new JSONObject(new FileReader(path));
      JSONArray subjects = obj.getJSONArray("levels");
      //loop through the JSon array and store each Level information in the corresponding Level
      for (int i = 0; i < subjects.size(); i++ ) {
        //store information such as text file name and timer for each Level
        JSONObject y = subjects.getJSONObject(i);
        pathArrayList.add(y.getString("path"));
        timeArrayList.add(y.getInt("time"));
        //the file can be read, so ther is file
        this.noFile = false;
      }
      //get the lives for the Levels
      this.lives  = obj.getInt("lives");
    //catch error
    } catch (FileNotFoundException e) {
      System.out.println("Bad file");
      this.noFile = true;
    }

    //run if there file is loaded corretly
    if (!noFile) {
      //loop through the file path store in the ArrayList
      for (int i = 0; i < pathArrayList.size(); i++) {
        //create the Level by the information given
        Level level = new Level();
        level.createLevel(app, pathArrayList.get(i), timeArrayList.get(i));
        this.levels.add(level);
      }
      //set the icon to represent lives
      this.icon.setSprite(app, "src/main/resources/icons/player.png");
    }
  }
  /**
   *Draw the current Level
   *@param app PApplet object
   */
  public void draw(PApplet app) {
    //no file, no game
    if (noFile) {
      this.text(app, "No File", 170, 240);
    //check if the BombGuys is finished the game
    } else if (this.index >= this.levels.size()) {
        this.drawWin(app);
    //check if the game even have bombGuy
    } else if (this.levels.get(this.index).getBombGuy() == null) {
        this.text(app, "No bombGuy", 170, 240);
    //check if the BombGuy is dead or the time run out
    } else if (this.lives != 0 && this.levels.get(index).getTimer() != 0) {
      //if the game loss life the Level need to be reset
      if (this.levels.get(this.index).lossLive()) {
          this.lives -= 1;
          Level level = new Level();
          level.createLevel(app, this.pathArrayList.get(index), this.timeArrayList.get(index));
          this.levels.set(this.index, level);
      }
      //draw the current Level
      this.levels.get(this.index).tick();
      this.levels.get(this.index).draw(app);
      icon.draw(app);
      this.text(app, Integer.toString(this.lives),140, 40);
      //advance to the next Level
      if (this.levels.get(index).advanceLevel()) {
        this.index += 1;
      }
    //Bomb guy loss everything, what a loser and draw die
    } else {
      this.drawDie(app);
    }
  }
}
