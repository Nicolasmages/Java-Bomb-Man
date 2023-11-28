package demolition;

import processing.core.PApplet;
import processing.core.PFont;
/**
 * This is an text interface that use to write the text in the Level and Levels
 */
interface Text {
  /**
   *Create the text font and write the string
   *@param app a PApplet object
   *@param text the String that need to write
   *@param positionX the pixel position of X-axis that the text need to be placed
   *@param positionY the pixel position that Y-axis the text need to be placed
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#image-processing.core.PImage-float-float->image()</a>
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PFont.html>PFont</a>
   */
  public default void text(PApplet app, String text, int positionX, int positionY) {
    PFont f = app.createFont("PressStart2P-Regular.ttf",16,true);
    app.textFont(f,16);
    app.fill(0);
    app.text(text,positionX, positionY);
  }
}
