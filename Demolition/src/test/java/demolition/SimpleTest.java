package demolition;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SimpleTest {

    private static GoalTile goalTile;
    private static Tile tile;
    private static BrokenWall brokenWall;
    private static SolidWall solidWall;
    private static Wall[][] wallMap;
    private static BombGuy bombGuy;
    private static EnemyRed enemyRed;
    private static EnemyYellow enemyYellow;
    private static ArrayList<Player> playerList;
    private static Bomb bomb;

    @BeforeAll
    public static void InitialiseObjects() {

      //Run the all the inital instance before running the method
      goalTile = new GoalTile(32, 32);
      tile = new Tile(32,32);
      brokenWall = new BrokenWall(32,32);
      solidWall = new SolidWall(32,32);
      wallMap = new Wall[][] {{solidWall, solidWall, solidWall, solidWall, solidWall},
                              {solidWall, solidWall, tile, solidWall, solidWall},
                              {solidWall, tile, tile, tile, solidWall},
                              {solidWall, solidWall, tile, solidWall, solidWall},
                              {solidWall, tile, tile, tile, solidWall},
                              {solidWall, tile, tile, tile, solidWall},
                              {solidWall, solidWall, brokenWall, solidWall, solidWall},
                              {solidWall, solidWall, goalTile,solidWall, solidWall},
                            };
      bombGuy = new BombGuy(32, 32, 1, 2, wallMap);
      enemyRed = new EnemyRed(32, 32, 3, 2, wallMap);
      enemyYellow = new EnemyYellow(32, 32, 2, 2, wallMap);
      playerList = new ArrayList<Player>();
      playerList.add(enemyRed);
      playerList.add(enemyYellow);
      bomb = new Bomb(32, 32, 2, 2, wallMap, playerList, bombGuy);
      bombGuy.setEnemyArrayList(playerList);
    }

    @Test
    @Order(1)
    //check BombGuy initial position and ensure it have no touch anything yet.
    public void playerBehaviourTest() {

      //check BombGuy position
      assertEquals(1, bombGuy.getMatrixX());
      assertEquals(2, bombGuy.getMatrixY());

      //No reach goal
      assertFalse(bombGuy.isOnGoal());

      //collide with other player
      assertFalse(bombGuy.checkPlayerCollide(enemyRed));
      assertFalse(bombGuy.checkPlayerCollide(enemyYellow));

      //enemy test
      enemyRed.move();
      assertFalse(bombGuy.checkPlayerCollide(enemyRed));
      assertEquals(4, enemyRed.getMatrixX());
      assertEquals(2, enemyRed.getMatrixY());

      enemyYellow.move();
      assertEquals(3, enemyYellow.getMatrixX());
      assertEquals(2, enemyYellow.getMatrixY());
      enemyYellow.move();
      assertEquals(4, enemyYellow.getMatrixX());
      assertEquals(2, enemyYellow.getMatrixY());
      enemyYellow.move();
      assertEquals(5, enemyYellow.getMatrixX());
      assertEquals(2, enemyYellow.getMatrixY());
      enemyYellow.move();
      assertEquals(5, enemyYellow.getMatrixX());
      assertEquals(1, enemyYellow.getMatrixY());
      enemyYellow.move();
      assertEquals(4, enemyYellow.getMatrixX());
      assertEquals(1, enemyYellow.getMatrixY());

      enemyYellow.move();
      enemyYellow.move();
      enemyYellow.move();
      assertEquals(5, enemyYellow.getMatrixX());
      assertEquals(3, enemyYellow.getMatrixY());

      //check if the animation cycle through for one seconds
      for (int i = 0; i < 60; i++) {
          enemyRed.tick();
      }
      assertEquals(0, enemyRed.getIndex());

      //defence Programming check
      EnemyRed enemyRed01 = new EnemyRed(1,1,1,1,null);
      assertFalse(enemyRed01.checkCollide(Direction.up));
    }

    @Test
    @Order(2)
    //check the movment behaviour of BombGuy and associate with wall
    public void wallBehaviourTest() {

      //check differnet types of wall impact Bombguy
      assertFalse(bombGuy.checkCollide(Direction.up));
      assertFalse(bombGuy.checkCollide(Direction.left));
      assertTrue(bombGuy.checkCollide(Direction.down));
      assertTrue(bombGuy.checkCollide(Direction.right));
      assertFalse(bombGuy.checkCollide(Direction.right));
      assertFalse(bombGuy.checkCollide(Direction.down));

      //No reach goal
      assertFalse(bombGuy.isOnGoal());

      //collide with enemy
      assertFalse(bombGuy.checkPlayerCollide(enemyRed));
      assertFalse(bombGuy.checkPlayerCollide(enemyYellow));
    }

    @Test
    @Order(3)
    //check the bomb impact on player
    public void bombBehaviourTest() {

      //check the ticking
      assertTrue(bomb.getTime() == 149);

      //initialise the animation for the explosion and the impact
      bomb.blockExplosion(bomb.getExplosionDirection()[2], 0, 0);
      bomb.blockExplosion(bomb.getExplosionDirection()[1].vertical, 1, 0);
      bomb.blockExplosion(bomb.getExplosionDirection()[1].vertical,-1, 0);
      bomb.blockExplosion(bomb.getExplosionDirection()[0].horizontal,0, 1);
      bomb.blockExplosion(bomb.getExplosionDirection()[0].horizontal,0, -1);

      //kill the players
      for (int i = 0; i < 150; i++) {
          bomb.tick();
      };

      //check if the players are dead
      assertTrue(bombGuy.getLives() < 1);
      assertEquals(1, playerList.size());
    }

    @Test
    @Order(4)
    public void bomberManBehaviourTest() {
        //Null BombList
        assertTrue(bombGuy.checkGrid(2,3));
        assertTrue(bombGuy.getbombArrayList().size() == 0);
        bombGuy.pressKey(37);
        bombGuy.move();
        assertTrue(bombGuy.checkGrid(2,2));
        bombGuy.pressKey(40);
        bombGuy.move();
        assertTrue(bombGuy.checkGrid(3,2));
        bombGuy.pressKey(38);
        bombGuy.move();
        assertTrue(bombGuy.checkGrid(2,2));
        bombGuy.pressKey(39);
        bombGuy.move();
        assertTrue(bombGuy.checkGrid(2,3));

        bombGuy.pressKey(37);
        bombGuy.move();
        assertTrue(bombGuy.checkGrid(2,2));
        for (int i = 0; i < 3; i++) {
            bombGuy.pressKey(40);
            bombGuy.move();
        }
        assertTrue(bombGuy.checkGrid(5,2));
        bombGuy.pressKey(39);
        bombGuy.move();
        assertFalse(bombGuy.checkGrid(5,2));
        assertTrue(bombGuy.checkPlayerCollide(enemyYellow));
    }
}
