package demolition;

import processing.core.PApplet;
/**
 * The App class mainly use to create a game play screem with setted <b>WIDTH</b> 480 and <b>HEIGHT</b> 480,
 * <b>FPS</b> setted to 60.
 */
public class App extends PApplet{

  public static final int WIDTH = 480;
  public static final int HEIGHT = 480;
  public static final int FPS = 60;
  private Levels levels;
  private boolean noFile;
  private boolean switches;
  /**
   */
  public App() {
    this.switches = true;
    this.noFile = true;
    this.levels = new Levels();
  }
  /**
   *Set the game window in setted height and width
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#settings-->settings()</a>
   */
  public void settings() {
    size(WIDTH, HEIGHT);
  }
  /**
   *Set the game in 60 fps in default, and setup all the gameObject and game levels in the Levels object
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#setup-->setup()</a>
   */
  public void setup() {
    frameRate(FPS);
    this.levels.setup(this);
  }
  /**
   *(Begin auto-generated from draw.xml), similar to draw() in PApplet, but draw all the objects inside the levels class instead
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#draw-->draw()</a>
   */
  public void draw() {
    background(255,150,0);
    this.levels.draw(this);
  }
  /**
   *Chaneg the default path of the json file to a new json file
   *@param newPath, String must not be null and can not be ""
   */
  public void setConfig(String newPath) {
    if (newPath == null || newPath.length() == 0) {
      return;
    } else {
      levels.changePath(newPath);
    }
  }
  /**
   *Return the Levels object in the App
   *@return the Levels Object
   */
  public Levels getLevels() {
    return this.levels;
  }
  /**
   *Similar to keyPressed in PApplet and make sure it can detect user input if the gameObject bombGuy is alive and has not yet reach the goal, as well as make sure the usser doesnt hold the key
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#keyPressed>keyPressed()</a>
   */
  public void keyPressed() {
    if (this.levels.isAlive() && this.switches == true && this.levels.getIndex() < this.levels.numOfLevel()) {
      this.levels.getLevel().getBombGuy().pressKey(this.keyCode);
      //place the bomb. it might be a design fault..
      if (this.keyCode == 32 || this.key == 32) {
        this.levels.getLevel().getBombGuy().placeBomb(this);
      }
      this.switches = false;
    }
  }
  /**
   *Similar to keyReleased in Papplet and make sure to obtain the next user input if the key has been release
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#keyReleased>keyReleased()</a>
   */
  public void keyReleased() {
    this.switches = true;
  }
  /**
   *Run Papplet
   *@param args, String arrays
   */
  public static void main(String[] args) {
    PApplet.main("demolition.App");
  }
}
