package demolition;

import processing.core.PImage;
import processing.core.PApplet;
/**
 * The gameObject class conatin the position of the each gameObject refers to the game window screens and the Pimage store for corresponding object
 */
public class GameObject {
  /**
   *x-coordinate of the GameObject inside the game window screen
   */
  protected int x;
  /**
   *y-coordinate of the GameObject inside the game window screen
   */
  protected int y;
  /**
   *The PImage of the gameObject
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PImage.html>PImage()</a>
   */
  protected PImage sprite;
  /**
   *Constructor for gameObject
   *@param x x-coordinate of the GameObject
   *@param y y-coordinate of the GameObject
   */
  public GameObject(int x, int y) {
    //Use int instead of double because Integer are the best number
    this.x = x;
    this.y = y;
  }
  /**
   *Set the Pimage of the gameObject
   *@param app, Papplet object
   *@param path, String to lead which file should be read
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PImage.html>PImage()</a>
   */
  public void setSprite(PApplet app, String path) {
    this.sprite = app.loadImage(path);
  }
  /**
   *Draw the gameObject PImage on the screen with the corresponding X and Y axis position use Papplet.image()
   *@param app, PApplet object
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#image-processing.core.PImage-float-float->image()</a>
   */
  public void draw(PApplet app) {
    app.image(this.sprite, this.x, this.y);
  }
}
