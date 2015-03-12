import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Iterator;

/**
 * Displays the game in a window.
 */
@SuppressWarnings("serial")
public class GameGUI extends JPanel {
   
   private GameController gameController;
	private JFrame frame;
   private int whichBanana;  
	private static final Color[] GORILLA_COLORS = {
	   new Color(231,178,57),
	   new Color(137,106,34),
	   new Color(255,169,8),
	   new Color(160,137,93)
	};
	
	//  - Time limits
   // We use reflection here so that the GUI doesn't break for students who
   //   do not implement this turn limit feature
	private Class<?> gameClass;
	private Method getTicksPerTurn;
	private Method getTicksLeftThisTurn;
	private boolean hasTimeLimitExtraCredit;

	// Possible ways of aligning displayed text
	private static final int ALIGN_LEFT = 0;
	private static final int ALIGN_RIGHT = 1;
	private static final int ALIGN_CENTER = 2;
	
   /**
	 * 
	 * Creates a window for displaying the car game.
	 * 
	 * @param title Name to be printed in the window's titlebar.
	 */
	public GameGUI(String title, GameController r) {
		super();
		frame = new JFrame(title);
		Container c = frame.getContentPane();
      c.add(this);
      this.gameController = r;
		frame.addKeyListener(r);
		frame.addWindowListener(r);
		frame.setResizable(false);

      //  - Time limits
		// We use reflection here so that the GUI doesn't break for students who
		//   do not implement this turn limit feature
      try {
         this.gameClass = Class.forName("Game");
         this.getTicksPerTurn = this.gameClass.getMethod("getTicksPerTurn", 
               (Class<?>[])null);
         this.getTicksLeftThisTurn = this.gameClass.getMethod("getTicksLeftThisTurn", 
               (Class<?>[])null); 
         // If we get this far, we assume they attempted the turn limit implementation
         this.hasTimeLimitExtraCredit = true;
      } catch (ClassNotFoundException e) {
         // This is just bad, so we might as well crash
         System.err.println("Can't find the Game class");
         System.exit(-1);
      } catch (NoSuchMethodException e) {
         // This may be because they didn't implement this  features, so no
         //   error message apperas here
      }
	}
	
	
	/**
	 * Closes the window and allows the main program to exit cleanly.
	 * If a program ends without calling this method, the image window, 
	 * although able to hide, will remain in memory indefinitely.
	 */
	public void close() {
		frame.dispose();
	}

	
	/**
	 * Paints the window contents using the provided <tt>Graphics</tt> object.
	 */
	public void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   this.update(g);
	}
	
	
	/**
	 * The actual painting code is here instead of <tt>paintComponent()</tt>.
	 */
	public void update(Graphics gr) {
	   // Get the right data type for the graphics object and make sure that
	   //   it draws antialiased lines (which look much prettier)
	   Graphics2D g = (Graphics2D)gr;
	   g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	   
	   // Some variables useful for the drawing process. These will save us the
	   //   hassle of repeatedly calling the same methods and having super long
	   //   lines of code as a aresult.
	   Game game = this.gameController.getGame();
	   int gameHeight = game.getHeight(); // useful for conversions of coordinates
	   Gorilla cg = game.getCurrentGorilla();
	   
	   // First draw the background.
	   g.setColor(new Color(0, 0, 200));
	   g.fillRect(0, 0, game.getWidth(), 
	         game.getHeight());
	   
	   // Now draw the game state unless the game is over, in which case we'll
	   //   just say who won.
	   if (game.isGameOver()) {
	      g.setColor(Color.RED);
	      g.drawString("GAME OVER", (game.getWidth() - g.getFontMetrics().stringWidth("GAME OVER"))/ 2, 
               game.getHeight() / 2 - g.getFont().getSize());
	      if (game.getWinningGorillas().size() > 1) {
	         g.drawString("It's a tie", (game.getWidth() - g.getFontMetrics().stringWidth("It's a tie"))/ 2, 
	               game.getHeight() / 2);
	      } else {
	         // Really, this should be done using event-driven programming, but
	         //   that is way beyond the scope of this course. We would not want
	         //   students to add firing events to the Game class.
	         Iterator<Gorilla> gi = game.getGorillaIterator();
	         int num = 0;
	         int toFind = game.getWinningGorillas().get(0);
	         Gorilla p = gi.next();
	         while (num != toFind) {
	            num++;
	            p = gi.next(); 
	         }
	         String winnerString = p.getName() + " wins";
	         g.drawString(winnerString, (game.getWidth() - g.getFontMetrics().stringWidth(winnerString))/ 2, 
                  game.getHeight() / 2);
	      }
	   } else {
	      // Draw buildings. Note that we need to flip y coordinates. While y = 0 is
	      //   at the bottom of the coordinate system in the Game class, which
	      //   I would argue is more intuitive to just about everyone, it is
	      //   at the top for just about every graphics drawing system known to man.
	      Iterator<Building> bi = game.getBuildingIterator();
	      while (bi.hasNext()) {
	         Building b = bi.next();
	         int top = game.getHeight() - b.getBottom() - b.getHeight();
	         g.setColor(b.getColor());
	         g.fillRect(b.getLeft(), top, b.getWidth(), b.getHeight());
	         // Now draw windows ... yay for fancy for loops?
	         g.setColor(Color.YELLOW);
	         int numJ = (b.getWidth() - 5) / 12;
	         int firstJ = b.getLeft() + 5 + (b.getWidth() - numJ * 7 - (numJ + 1) * 5) / 2;
	         int numK = (b.getHeight() - 5) / 15;
	         int firstK = top + 5 + (b.getHeight() - numK * 10 - (numK + 1) * 5) / 2;
	         for (int j = firstJ; j <= b.getLeft() + b.getWidth() - 12; j += 12) { 
	            for (int k = firstK; k <= top + b.getHeight() - 15; k += 15) {
	               g.fillRect(j, k, 7, 10);
	            }
	         }
	      }
	      
	      // Draw gorillas
	      Iterator<Gorilla> gi = null;
	      try {
	         gi = game.getGorillaIterator();
	      } catch (NullPointerException e) {}
	      if (gi != null) {
	         int gorillaNumber = 0;
	         while (gi.hasNext()) {
	            Gorilla p = gi.next();
	            if (!p.wasHit() && p.isStillPlaying()) {
	               g.drawImage(getGorillaImage(gorillaNumber), p.getLeft(),
	                     game.getHeight() - p.getBottom() - Gorilla.HEIGHT, new Color(0,0,200), null);
	            }
	            gorillaNumber++;
	         }
	      }
	      
	      // Draw banana if applicable
	      Banana b = game.getBanana();
	      if (b != null) {
	         // Draw height indicator if the banana is off the top of the screen
	         if (b.getY() > gameHeight + Banana.RADIUS) {
	            String toDraw = "" + ((int)b.getY() - game.getHeight());
	            FontMetrics fm = g.getFontMetrics();
	            int textWidth = fm.stringWidth(toDraw);
	            g.setColor(Color.BLACK);
	            g.drawLine((int)b.getX(), 0, (int)b.getX(), 15);
	            g.drawLine((int)b.getX() + 3, 5, (int)b.getX(), 0);
	            g.drawLine((int)b.getX() - 3, 5, (int)b.getX(), 0);
	            g.drawRect((int)b.getX() - textWidth / 2 - 16, 15, textWidth + 32, 
	                  g.getFont().getSize() + 10);
	            g.setColor(Color.DARK_GRAY);
	            g.fillRect((int)b.getX() - textWidth / 2 - 16, 15, textWidth + 32, 
                     g.getFont().getSize() + 10);
	            g.setColor(Color.YELLOW);
	            g.drawImage(getBananaImage(), (int)b.getX() - textWidth / 2 - 15, 
                     16, null);
	            this.whichBanana = (this.whichBanana + 2) % 36;
	            g.drawString(toDraw, (int)b.getX() - textWidth / 2 + 12, 
	                  20 + g.getFont().getSize());
	         } else {
	            // Otherwise figure out whether to draw a shockwave or a banana image
	            // Each call to the update() method draws a slightly rotated 
	            //   banana compared to the previous call so that the banana
	            //   appears rotating
	            double radius = b.isSplatting() ? b.getCurrentShockWaveRadius() : Banana.RADIUS;
	            if (Game.circleIntersectsRectangle(b.getX(), b.getY(), radius, 
	                  0, 0, game.getWidth(), 
	                  game.getHeight())) {
	               if (b.isSplatting()) {
	                  g.setColor(Color.WHITE);
	                  g.fillOval((int)(b.getX() - radius), 
	                        (int)(game.getHeight() - b.getY() - radius), 
	                        (int)(2 * radius), (int)(2 * radius));
	               } else {
	                  g.drawImage(getBananaImage(), (int)(b.getX() - Banana.RADIUS), 
	                        (int)(gameHeight - b.getY() - (int)Banana.RADIUS), null);
	                  this.whichBanana = (this.whichBanana + 2) % 36;
	               }
	            }
	         }
	      }
	      
	      // Draw aiming arrow and info if we are aiming and not paused
	      if (!this.gameController.isPaused() && game.getTurnState() == Game.AIMING) {
	         if (cg != null) {
	            // Draw the arrow above the middle of the gorilla, 20 points above
	            //   its head (since zero is at the top, we subtract 20)
	            double x = cg.getMiddle();
	            double y = gameHeight - cg.getBottom() - Gorilla.HEIGHT - 20;
	            double scale = this.gameController.getVelocity() / 
	                  (double)(GameController.MAX_VELOCITY - GameController.MIN_VELOCITY);
	            double dx = 100 * Math.sin(this.gameController.getAngle() * Math.PI / 180);
	            double dy = 100 * Math.cos(this.gameController.getAngle() * Math.PI / 180);
	            // Red line is for angle
	            g.setColor(Color.RED);	            
	            g.draw(new Line2D.Double(x, y, x + dx, y - dy));
	            // Yellow line is for velocity - the higher the velocity, the longer
	            //   the line
	            g.setColor(Color.YELLOW);
	            g.draw(new Line2D.Double(x, y, x + scale * dx, y - scale * dy));
	            // Also display angle and velocity in the status bar
	            g.setColor(Color.BLACK);
	            g.drawString("Angle: " + this.gameController.getAngle(), 110, gameHeight + 15);
	            g.drawString("Velocity: " + this.gameController.getVelocity(), 210, gameHeight + 15);
	            	            
	            //  - Time limits
	            // We use reflection here so that the GUI doesn't break for students who
	            //   do not implement this turn limit feature
	            if (this.hasTimeLimitExtraCredit) {
	               // First check if we want a time limit
	               int timeLimit = 0;
	               try {
	                  timeLimit = (Integer)this.getTicksPerTurn.invoke(game, (Object[])null);
	               } catch (IllegalAccessException e) {
	                  System.err.println("Cannot call getTicksPerTurn");
	               } catch (InvocationTargetException e) {
	                  System.err.println("Call to getTicksPerTurn failed");
	               }
	               if (timeLimit > 0 && game.getTurnState() == Game.AIMING) {
	                  // And if we do, check if time is low. If it is, display a 
	                  //   warning or a countdown above the gorilla
	                  int ticksLeft = 0;
	                  try {
	                     ticksLeft = (Integer)this.getTicksLeftThisTurn.invoke(game, (Object[])null);
	                  } catch (IllegalAccessException e) {
	                     System.err.println("Cannot call getTicksLeftThisTurn");
	                  } catch (InvocationTargetException e) {
	                     System.err.println("Call to getTicksLeftThisTurn failed");
	                  }
	                  int secondsLeft = (ticksLeft + this.gameController.getTicksPerSecond() - 1) / 
	                        this.gameController.getTicksPerSecond();
	                  if (secondsLeft <= 5) {
	                     String timeLeft = "" + secondsLeft;
	                     Font f = new Font(g.getFont().getName(), Font.BOLD,
	                           g.getFont().getSize() + 2 * (5 - secondsLeft));
	                     displayString(timeLeft, cg.getMiddle(), 
	                           gameHeight - cg.getBottom() - Gorilla.HEIGHT - 30,
                              g, f, Color.RED, ALIGN_CENTER);
	                  } else if (secondsLeft <= 10) {
	                     String hurry = "HURRY!";
	                     displayString(hurry, cg.getMiddle(), 
	                           gameHeight - cg.getBottom() - Gorilla.HEIGHT - 30,
	                           g, null, Color.RED, ALIGN_CENTER);
	                  }
	               }
	            }
	            
	            // TODO: Add your code for displaying aiming stats and whose
	            //   turn it is at this location
	         }
	      }
	      
	      // Are we paused? If so, desplay paused text across the midle of the 
	      //   screen in yellow text on a half-transparent white background
	      //   inside of a black box.
	      if (this.gameController.isPaused()) {
	         displayString("PAUSED", game.getWidth() / 2, game.getHeight() / 2, 
	               g, null, Color.YELLOW, ALIGN_CENTER, 5, Color.BLACK, 
	               new Color(255, 255, 255, 128));
	      }
	   }
	   
	   // Display round information in the status bar
	   g.setColor(Color.BLACK);
	   if (game.getOvertimeNumber() == 0) {
	      g.drawString("Round: " + game.getRound() + "/" + game.getNumRounds(), 
	            10, gameHeight + 15);
	   } else {
	      g.drawString("OT " + game.getOvertimeNumber() + " rd " + game.getRound() + "/" + 
	            game.getNumOvertimeRounds(), 10, gameHeight + 15);
	   }
	   
      // Display player information in the status bar
      Font boldFont = g.getFont().deriveFont(Font.BOLD);
      Font normalFont = g.getFont();
      Iterator<Gorilla> gi = null;
      try {
         gi = game.getGorillaIterator();
      } catch (NullPointerException e) {}
      if (gi != null) {
         int playerNumber = 1;
         while (gi.hasNext()) {
            Gorilla p = gi.next();
            if (p == game.getCurrentGorilla()) {
               g.setFont(boldFont);
            } else {
               g.setFont(normalFont);
            }
            if (p.wasHit() || !p.isStillPlaying()) {
               g.setColor(Color.GRAY);
            } else {
               g.setColor(GORILLA_COLORS[playerNumber - 1]);
            }
            g.drawString("Player " + playerNumber + ": " + p.getScore(), 
                  250 + 100 * playerNumber, gameHeight + 15);
            playerNumber++;
         }
      }
      
      // And display wind in the status bar
      String windString = "Wind: " + Double.toString(game.getWindSpeed());
      int decimalPointIndex = windString.indexOf('.');
      if (decimalPointIndex >= 0) {
         windString = windString.substring(0, Math.min(decimalPointIndex + 3, windString.length()));
      }
      g.setColor(Color.BLACK);
      g.setFont(normalFont);
      g.drawString(windString, 400 + 100 * game.getNumGorillas(), gameHeight + 15);
	} 
	/**
	 * Displays text with the specified font, color, and alignment.
    *   <tt>null</tt> for font and color means keep current setting
	 * @param text
	 * @param x The x coordinate where the left, right or middle of the string
	 *   (depends on alignment) should be
	 * @param y The y coordinate of the baseline
	 * @param g The graphics object where drawing happens
	 * @param f The font that should be used. If <tt>null</tt>, current font will
	 *   be used
	 * @param c The color that should be used. If <tt>null</tt>, current color will
    *   be used
	 * @param alignment One of <tt>ALIGN_LEFT</tt>, <tt>ALIGN_RIGHT</tt> or
	 *   <tt>ALIGN_CENTER</tt>
	 */
	private static void displayString(String text, double x, double y, Graphics2D g, Font f,
	      Color c, int alignment) {
	   displayString(text, x, y, g, f, c, alignment, -1, null, null);
	}
	/**
    * Displays text with the specified font, color, and alignment.
    *   <tt>null</tt> for font and color means keep current setting. Also draws a box
    *   around the text.
    * @param text
    * @param x The x coordinate where the left, right or middle of the string
    *   (depends on alignment) should be
    * @param y The y coordinate of the baseline
    * @param g The graphics object where drawing happens
    * @param f The font that should be used. If <tt>null</tt>, current font will
    *   be used
    * @param c The color that should be used. If <tt>null</tt>, current color will
    *   be used
    * @param alignment One of <tt>ALIGN_LEFT</tt>, <tt>ALIGN_RIGHT</tt> or
    *   <tt>ALIGN_CENTER</tt>
    */
   private static void displayString(String text, double x, double y, Graphics2D g, Font f,
         Color c, int alignment, int padding, Color borderColor, Color fillColor) {
      // Back up old font and color and set the font afterwards (we'll set color
      //   later as we may be drawing a bounding box too)
      Font oldFont = g.getFont();
      Color oldColor = g.getColor();
      if (f != null) {
         g.setFont(f);
      }
      if (c == null) {
         c = g.getColor();
      }
      
      // Now figure out the true x coordinate of the text placement. This depends
      //   on the alignment and the text width. We don't need to do anything for
      //   text that is left-aligned
      FontMetrics fm = g.getFontMetrics();
      int width = fm.stringWidth(text);
      switch (alignment) {
      case ALIGN_RIGHT:
         x = x - width;
         break;
      case ALIGN_CENTER:
         x = x - width / 2.0;
         break;
      }
      
      // Draw bounding box if applicable
      // We use the Rectangle2D.Double class for this. The constructor parameters
      //   are the x and y coordinates of the top left corner (not bottom left
      //   like for buildings), followed by the width and the height. We use the
      //   text width from the font metrics to figure out the width, and we use
      //   the font size to figure out the height.
      if (padding >= 0) {
         Rectangle2D.Double box = new Rectangle2D.Double(x - padding, y - g.getFont().getSize() - padding,
               2 * padding + width, 2 * padding + g.getFont().getSize());
         if (fillColor != null) {
            g.setColor(fillColor);
            g.fill(box); // The fill method fills the area whose shape is specified 
                         //   by box using the current color
         }
         if (borderColor != null) {
            g.setColor(borderColor);
            g.draw(box); // The draw method draws the boundary of the shape
                         //   specified by box using the current color
         }
      }
      // Finally, draw the text. We need to typecast doubles to floats
      //   because Graphics2D's drawString() does not understand doubles
      g.setColor(c);
      g.drawString(text, (float)x, (float)y);
      
      // Restore font and color so that the caller doesn't need to.
      g.setFont(oldFont);
      g.setColor(oldColor);
   }
   

	/**
	 * Displays the window.
	 */
	public void showMe() {
	   // Set the size of the contents of the window so that we get the right size
	   Dimension d = new Dimension(this.gameController.getGame().getWidth(),
            this.gameController.getGame().getHeight() + 20);
	   Container c = frame.getContentPane();
	   c.setMaximumSize(d);
	   c.setPreferredSize(d);
	   c.setMinimumSize(d);
	   // And now we can show the window
	   frame.pack();
	   frame.setVisible(true);
		repaint();
	}
	
   
   /**
    * Places the window. The upper left coordinate of the window will be
    * placed at position (x,y) with respect to the upper left corner of the
    * screen.
    */
   public void setLocation(int x, int y) {
      frame.setLocation(x, y);
   }
   
   private Image getBananaImage() {
      try {
         return ImageIO.read(new File("images/banana" + (this.whichBanana < 10 ? "0" : "") + 
               this.whichBanana + ".png"));
      } catch (IOException e) {
         return null;
      }
   }
   
   private Image getGorillaImage(int number) {
      try {
         return ImageIO.read(new File("images/gorilla" + number + ".png"));
      } catch (IOException e) {
         return null;
      }
   }
}
