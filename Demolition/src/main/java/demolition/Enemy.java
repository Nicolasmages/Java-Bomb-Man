package demolition;

import processing.core.PApplet;
/**
 * This is an Enemy interface that provide move pattern for all the Enemy.
 */
interface Enemy {
  /**
   *This the enemyMovePattern, the enemy walk in one direction and cycle it is walk anaimation
   *@param player, Player that have the enemy moveing pattern
   *@see <a href=https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#image-processing.core.PImage-float-float->image()</a>
   */
  public default void enemyMovePattern(Player player) {
    //Keep walking up with corresponding walk animation cycle and update the pixel postion
    if (player.move == player.direction[0]) {
      player.y -= player.speed;
      player.setDraw(player.direction[0]);
    //Keep walking right with corresponding walk animation cycle and update the pixel postion
    } else if (player.move == player.direction[1]) {
      player.x += player.speed;
      player.setDraw(player.direction[1]);
    //Keep walking down with corresponding walk animation cycle and update the pixel postion
    } else if (player.move == player.direction[2]) {
      player.y += player.speed;
      player.setDraw(player.direction[2]);
    //Keep walking left with corresponding walk animation cycle and update the pixel postion
    } else if (player.move == player.direction[3]) {
      player.x -= player.speed;
      player.setDraw(player.direction[3]);
    }
  }
}
