package demolition;

import processing.core.PImage;
import processing.core.PApplet;
/**
* The BroeknWall object to indicate that BombGuy can not walk through and bomb can destry the BroekenWall
*/
public class BrokenWall extends Wall {
    /**
    * The Constructor for BroeknWall and it is inherited by Wall object
    *@see Wall
    *@param x x-coordinate of the GameObject
    *@param y y-coordinate of the GameObject
    */
    public BrokenWall(int x, int y){
      super(x,y);
      this.solid = true;
      this.onGoal = false;
      this.broken = true;
    }
    /**
    *Change the attribute of the Borken Wall after the explosion impact
    *@see Wall
    *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#image-processing.core.PImage-float-float->image()</a>
    */
    @Override
    public void destory(PApplet app) {
      //Because it is BroeknWall so it change after Bomb impact
      this.sprite = app.loadImage("src/main/resources/empty/empty.png");
      this.solid = false;
    }
}
