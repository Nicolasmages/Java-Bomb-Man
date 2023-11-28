package demolition;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexTest {
    @Test
    //test if the program can handle the null docunment
    public void failLoadingTest() {

      //initalise the app
      App app = new App();
      app.noLoop();
      app.setConfig("config");
      PApplet.runSketch(new String[] {"App"}, app);
      app.setup();
      app.delay(1000);
      app.draw();

      //there should be no file and there should be no level created;
      assertTrue(app.getLevels().getNoFile() == true);
      assertEquals(0, app.getLevels().numOfLevel());
    }
    @Test
    //test the program can run if there is no BombGuy
    public void NoBombGuyTest() {
      //initalise the app
      App app = new App();
      app.noLoop();
      app.setConfig("src/test/resources/NoBombGuy.json");
      PApplet.runSketch(new String[] {"App"}, app);
      app.setup();
      app.delay(1000);
      app.draw();
      //there should be no level created and there is no bombGuy in the levels
      assertNull(app.getLevels().getLevel().getBombGuy());
    }

    @Test
    //test the bombGuyMovement againt tile and soldWall + broekenWall
    public void threeSixityOogaBoogaTest() {

        //initalise the app
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/configLoading.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.setup();
        app.delay(1000);
        app.draw();

        //move two grid space in each direction
        for (int i = 37; i < 41; i++) {
          for (int y = 0; y < 2; y++) {
            app.keyCode = i;
            app.keyPressed();
            app.keyReleased();
            app.draw();
          }
        }

        //check if the BombGuy is on the new position after action
        assertFalse(app.getLevels().getLevel().getBombGuy().isOnGoal());
        assertEquals(2,app.getLevels().getLevel().getBombGuy().getMatrixX());
        assertEquals(3,app.getLevels().getLevel().getBombGuy().getMatrixY());
    }


    @Test
    //place a bomb and kill bombguy himself
    public void threeSixityBombTest() {

      //initalise the app
      App app = new App();
      app.noLoop();
      app.setConfig("src/test/resources/configLoading.json");
      PApplet.runSketch(new String[] {"App"}, app);
      app.setup();
      app.delay(1000);
      app.draw();

      //place a bomb
      app.keyCode = 32;
      app.keyPressed();
      app.keyReleased();
      app.draw();

      //check if the bombGuy placed a bomb or not
      assertFalse(app.getLevels().getLevel().getBombGuy().getbombArrayList().size() == 0);

      //tick the time, 2sec bomb BOOOM!!!!
      for (int i = 0; i <= 119; i++) {
        app.draw();
      }

      //confirmed it is 2 sec later
      assertEquals(198, app.getLevels().getLevel().getTimer());

      //check if the Bombguy loss life
      assertTrue(app.getLevels().getLevel().lossLive());
      //
      //tick the time 2 sec
      for (int i = 0; i <= 120; i++) {
        app.draw();
      }

      //bombGuy loss life
      assertEquals(2,app.getLevels().getLives());
    }

    @Test
    //check the enemy behaviour
    public void loadEnemy() {

      //initalise the app
      App app = new App();
      app.noLoop();
      app.setConfig("src/test/resources/EnemyTest.json");
      PApplet.runSketch(new String[] {"App"}, app);
      app.setup();
      app.delay(1000);
      app.draw();

      //check if the enemy is loaded
      assertEquals(2, app.getLevels().getLevel().getEnemyList().size());

      //check the enemy position, 0 is for yellow enemy
      assertEquals(4, app.getLevels().getLevel().getEnemyList().get(0).getMatrixX());
      assertEquals(1, app.getLevels().getLevel().getEnemyList().get(0).getMatrixY());

      //check position for red enemy
      assertEquals(8, app.getLevels().getLevel().getEnemyList().get(1).getMatrixX());
      assertEquals(1, app.getLevels().getLevel().getEnemyList().get(1).getMatrixY());

      //tick the time
      for (int i = 0; i <= 180; i++) {
        app.draw();
      }

      //check if the position is change accordingly
      assertEquals(4, app.getLevels().getLevel().getEnemyList().get(0).getMatrixX());
      assertEquals(2, app.getLevels().getLevel().getEnemyList().get(0).getMatrixY());
      assertEquals(8, app.getLevels().getLevel().getEnemyList().get(1).getMatrixX());
      assertEquals(4, app.getLevels().getLevel().getEnemyList().get(1).getMatrixY());

    }

    @Test
    //check if the bomb destry the broken wall
    public void bombTest() {

      //initalise the app
      App app = new App();
      app.noLoop();
      PApplet.runSketch(new String[] {"App"}, app);
      app.setConfig("src/test/resources/BombTest.json");
      //app.setup();
      app.delay(1000);
      app.draw();

      //move right 6 grid space
      app.keyCode = 39;
      for (int i = 0; i <= 6; i ++) {
        app.keyPressed();
        app.keyReleased();
        app.draw();
      }

      //check if the position is updated and check if it blocked by the broken wall
      assertEquals(6, app.getLevels().getLevel().getBombGuy().getMatrixX());
      assertEquals(6, app.getLevels().getLevel().getBombGuy().getMatrixY());
      assertEquals(3,app.getLevels().getLives());

      //place a bomb and wait
      app.delay(1000);
      app.keyCode = 32;
      app.keyPressed();
      app.keyReleased();
      app.draw();

      //check if the bomb is placed
      assertFalse(app.getLevels().getLevel().getBombGuy().getbombArrayList().size() == 0);

      //move left so the bomb doesnt kill bombguy
      app.keyCode = 37;
      for (int i = 0; i <= 6; i ++) {
        app.keyPressed();
        app.keyReleased();
        app.draw();
      }

      //check if the bombGuy is out of the range of the explosion
      assertEquals(6, app.getLevels().getLevel().getBombGuy().getMatrixX());
      assertEquals(1, app.getLevels().getLevel().getBombGuy().getMatrixY());

      //wait 3 sec
      for (int i = 0; i <= 180; i++) {
        app.draw();
      }

      //check if the bomb is detonated
      assertTrue(app.getLevels().getLevel().getBombGuy().getbombArrayList().size() == 0);

      //move right to check if the broken wall is gone
      app.keyCode = 39;
      for (int i = 0; i <= 11; i ++) {
        app.keyPressed();
        app.keyReleased();
        app.draw();
      }

      //check if the bomb kill bombGuy
      assertEquals(3,app.getLevels().getLives());

      //check the level
      assertEquals(1,app.getLevels().getIndex());
    }

    @Test
    //check if the enemy can kill bombGuy
    public void enemyCollideTest() {

      //initalise the app
      App app = new App();
      app.noLoop();
      PApplet.runSketch(new String[] {"App"}, app);
      app.setConfig("src/test/resources/enemyCollideTest.json");
      app.setup();
      app.delay(1000);
      app.draw();

      //confirmed the position of bombGuy
      assertEquals(1, app.getLevels().getLevel().getBombGuy().getMatrixX());
      assertEquals(1, app.getLevels().getLevel().getBombGuy().getMatrixY());

      //move right
      app.keyCode = 39;
      for (int i = 0; i < 3; i ++) {
        app.keyPressed();
        app.keyReleased();
        app.draw();
      }

      //confirmed new position
      assertEquals(1, app.getLevels().getLevel().getBombGuy().getMatrixX());
      assertEquals(4, app.getLevels().getLevel().getBombGuy().getMatrixY());

      //wait 3 sec
      for (int i = 0; i <= 180; i++) {
        app.draw();
      }

      //confirmed the bombGuy is killed by enemy
      assertEquals(2, app.getLevels().getLives());
    }
}
