import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


/**
 * This class lets the user play the gorilla game. It processes key presses
 * made by players and keeps the game running.
 */
public class GameController extends Thread implements KeyListener, WindowListener {
   private Game game;
   private GameGUI gui;
   private boolean quitting;
   private int ticksPerSecond;
   private boolean paused = false;
   private int[] lastAngles;
   private int[] lastVelocities;
   // Aiming pointer is NOT part of the game. It's part of the controller.
   //   Same for the two instance arrays above.
   private int angle;
   private int velocity;
   public static final int MIN_VELOCITY = 0;
   public static final int MAX_VELOCITY = 250;
   public static final int MIN_ANGLE = -90;
   public static final int MAX_ANGLE = 90;
   
   public GameController(Game game, int ticksPerSecond) {
      this.game = game;
      this.ticksPerSecond = ticksPerSecond;
      this.lastAngles = new int[game.getNumGorillas()];
      this.lastVelocities = new int[game.getNumGorillas()];
   }
   
   public void run() {
      int lastTurn;
      int nextTurn;
      while (!quitting) {
         if (!this.paused) {
            lastTurn = game.getTurn();
            game.tick(1.0 / this.ticksPerSecond); // Do not want integer division
            nextTurn = game.getTurn();
            // Store aiming stats of the player whose turn just ended and
            //   restore the aiming stats of the player whose turn it is now.
            if (lastTurn != nextTurn) {
               this.lastAngles[lastTurn] = this.angle;
               this.lastVelocities[lastTurn] = this.velocity;
               this.angle = this.lastAngles[nextTurn];
               this.velocity = this.lastVelocities[nextTurn];
            }
         }
         gui.repaint();
         try {
            Thread.sleep(20);
         } catch (InterruptedException e) {}
      }
   }

   public Game getGame() {
      return this.game;
   }

   public boolean isPaused() {
      return this.paused;
   }

   public int getAngle() {
      return this.angle;
   }
   
   public int getVelocity() {
      return this.velocity;
   }
   
   public int getTicksPerSecond() {
      return this.ticksPerSecond;
   }
   
   public void setViewer(GameGUI gui) {
      this.gui = gui;
   }
   
   @Override
   /**
    * Processes key presses made by players.
    */
   public void keyPressed(KeyEvent e) {
      boolean ctrlPressed =
            (e.getModifiers() & KeyEvent.CTRL_MASK) == KeyEvent.CTRL_MASK;
      if (this.game.getTurnState() == Game.AIMING) {
         switch (e.getKeyCode()) {
         case KeyEvent.VK_UP:
            if (ctrlPressed) {
               this.velocity = Math.min(this.velocity + 10, MAX_VELOCITY);
            } else {
               this.velocity = Math.min(this.velocity + 1, MAX_VELOCITY);
            }
            break;
         case KeyEvent.VK_DOWN:
            if (ctrlPressed) {
               this.velocity = Math.max(this.velocity - 10, MIN_VELOCITY);
            } else {
               this.velocity = Math.max(this.velocity - 1, MIN_VELOCITY);
            }
            break;
         case KeyEvent.VK_RIGHT:
            if (ctrlPressed) {
               this.angle = Math.min(this.angle + 10, MAX_ANGLE);
            } else {
               this.angle = Math.min(this.angle + 1, MAX_ANGLE);
            }
            break;
         case KeyEvent.VK_LEFT:
            if (ctrlPressed) {
               this.angle = Math.max(this.angle - 10, MIN_ANGLE);
            } else {
               this.angle = Math.max(this.angle - 1, MIN_ANGLE);
            }
            break;         
         }
      }
   }

   @Override
   /**
    * Processes key presses made by players
    */
   public void keyTyped(KeyEvent e) {
      switch (Character.toUpperCase(e.getKeyChar())) {
      case 'N':
         this.game.startNewGame();
         break;
      case 'P':
         this.paused = !this.paused;
         break;
      case 'Q':
         this.quitting = true;
         break;
      case 'S':
         System.out.println(this.game);
         break;
      case 'T':
         this.game.tick(1.0 / this.ticksPerSecond);
         break;
      }
   }

   @Override
   /**
    * You will modify this method for airborne splatting feature. This method changes what
    *   happens in the game depending on which key the player released.
    */
   public void keyReleased(KeyEvent e) {
	   // Firing happens as key is released. This allows us to do banana explosions
	   //   mid-air (keyPressed() might be too sensitive as
      //   the banana might splat right away if the enter key is held too long)
      // TODO: Modify the code for this method to implement banana splatting
      //   mid-air
	   switch (e.getKeyCode()) {
	   case KeyEvent.VK_ENTER:
		   if (this.game.getTurnState() == Game.AIMING) {
			   this.game.tossBanana(this.angle * Math.PI / 180, this.velocity);
		   }
         break;
	   }
   }
   
   @Override
   public void windowActivated(WindowEvent arg0) {
   }

   @Override
   public void windowClosed(WindowEvent arg0) {
   }

   @Override
   public void windowClosing(WindowEvent arg0) {
      this.quitting = true; 
   }

   @Override
   public void windowDeactivated(WindowEvent arg0) {
   }

   @Override
   public void windowDeiconified(WindowEvent arg0) {
   }

   @Override
   public void windowIconified(WindowEvent arg0) {
   }

   @Override
   public void windowOpened(WindowEvent arg0) {
   }
}
