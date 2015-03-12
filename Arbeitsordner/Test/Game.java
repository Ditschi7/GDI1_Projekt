import java.util.*;
import java.awt.Color;


/**
 * This class represents the gorilla game.  It keeps track of buildings,
 *    players and the banana. This class handles all the rules of the game.
 */
public class Game {
   
   private ArrayList<Gorilla> gorillas;
   private ArrayList<Building> buildings;
   private Random r = new Random();
   
   
   /**
    * Indicates that a gorilla is aiming.
    */
   public static final int AIMING = 0;
   /**
    * Inidcates that a gorilla threw the banana and the banana has not hit
    *   a gorilla or a building yet.
    */
   public static final int BANANA_THROWN = 1;
   /**
    * Inidcates that a banana hit a gorilla or a building and the shock wave
    *   is now expanding.
    */
   public static final int BANANA_SPLATTING = 2;


   /**
    * Makes a new game with the characteristics specified by the parameter values.
    *   This constructor should also start the first round of the game.
    * @param numRounds The number of rounds this game will run for.
    * @param numOvertimeRounds The number of rounds each overtime will consist of.
    * @param playerNames The names of the players. This array will have between 
    *   2 and 4 elements in it.
    * @param height The height of the game area
    * @param width The width of the game area
    * @param gravity Gravitational constant for the purpose of computing
    *   how the banana flies.
    *   TODO Make the above a class constant?
    */
   public Game(int numRounds, int numOvertimeRounds,
         String[] playerNames, int height, int width, double gravity) {
      // Suggestions for making houses
      // - The height of any house should not exceed the height of the game area minus 200
      //     so that the aiming display is visible
      // - The width of a building should be at least the width of a gorilla plus
      //     30 so that a gorilla doesn't end up entrenched in between
      //     two really high buildings. On the other hand, the houses should
      //     not be too wide because we need enough of them in order to place
      //     all the gorillas so that no two gorillas stand on adjacent houses
      // - Use the provided random number generator (this.r) to make the buildings'
      //     dimensions and colors random.
      
      // Suggestions for placing gorillas
      // - There should be one gorilla for each element of the playerNames parameter
      // - Place each gorilla so that it's in the center of the building's top
   }
   
   
   /**
    * Returns the width of the game area
    * @return the width of the game area
    */
   public int getWidth() {
      // TODO
      return 0;
   }
   
   
   /**
    * Returns the height of the game area
    * @return the height of the game area
    */
   public int getHeight() {
      // TODO
      return 0;
   }
   
   
   /**
    * Returns the number of gorillas playing the game, including those that
    *   were hit or are not participating in overtime.
    * @return the number of gorillas playing the game
    */
   public int getNumGorillas() {
      // TODO
      return 0;
   }
   
   
   /**
    * Returns whose turn it is.
    * @return a number between 0 and the number of players minus one.
    */
   public int getTurn() {
      // TODO
      return 0;
   }
   
   
   /**
    * Returns a reference to the gorilla whose turn it is.
    * @return a reference to the gorilla whose turn it is
    */
   public Gorilla getCurrentGorilla() {
      // TODO
      return null;
   }
   
   
   /**
    * Returns which part of the current turn the game is in
    * @return one of <tt>Game.AIMING</tt>, <tt>Game.BANANA_THROWN</tt> and
    *    <tt>BANANA_SPLATTING</tt>.
    */
   public int getTurnState() {
      // TODO
      return 0;
   }
   
   
   /**
    * Returns a reference to the banana
    * @return <tt>null</tt> if a player is aiming, otherwise a reference
    *   to the banana that's flying or whose shock wave is spreading through
    *   the game area
    */
   public Banana getBanana() {
      // TODO
      return null;
   }
   
   
   /**
    * Makes the gorilla whose turn it is toss a banana, but only if the game is
    *   in the aiming state.
    * @param angle The angle at which the gorilla should toss the banana. The
    *   value is in radians and is between -Math.PI/2 and Math.PI/2
    * @param velocity The velocity at which the gorilla should toss the banana.
    *   The value is a non-negative floating point number.
    */
   public void tossBanana(double angle, double velocity) {
   
   }
   
   
   /**
    * Runs one tick of the game.
    * @param time The tick length
    */
   public void tick(double time) {
      
   }
   
   
   /**
    * Returns the number of the current round. The value should be an integer
    *   between 1 and the number of rounds (or number of overtime rounds if it
    *   is overtime).
    * @return the number of the current round
    */
   public int getRound() {
      // TODO
      return 0;
   }
   
   
   /**
    * Returns the number of rounds.
    * @return the number of rounds
    */
   public int getNumRounds() {
      // TODO
      return 0;   
   }
   
   
   /**
    * Returns whether the game is over
    * @return <tt>true</tt> if the game is over, <tt>false</tt> otherwise
    */
   public boolean isGameOver() {
      // TODO
      return false;
   }
   
   
   /**
    * Returns the numbers of gorillas that have the highest score. For example,
    *   it should return an <tt>ArrayList</tt> whose two elements are 0 and 3 
    *   if there are four gorillas whose scores are 7, 3, -2 and 7, respectively. 
    * @return an <tt>ArrayList</tt> with numbers of the players sharing the
    *   highest score
    */
   public ArrayList<Integer> getWinningGorillas() {
      // TODO
      return null;
   }   
   
   
   /**
    * Returns how many rounds an overtime consists of.
    * @return how many rounds an overtime consists of
    */
   public int getNumOvertimeRounds() {
      // TODO
      return 0;
   }
   
   
   /**
    * Returns whether it's overtime
    * @return <tt>true</tt> if it's overtime, <tt>false</tt> otherwise
    */
   public boolean isItOvertime() {
      // TODO
      return false;
   }
   
   
   /**
    * Returns the number of overtimes.
    * @return 0 if it's not overtime, otherwise the overtime number
    */
   public int getOvertimeNumber() {
      // TODO
      return 0;
   }
   
   
   /**
    * Returns the wind speed in the game area
    * @return the wind speed in the game area
    */
   public double getWindSpeed() {
      // TODO
      return 0;
   }
   
   
   /**
    * Starts a new game.
    */
   public void startNewGame() {
      
   }
   
   
   /**
    * Returns information about the game as a string. This will be useful
    *   for debugging.
    */
   public String toString() {
      // TODO
      return "";
   }
   
   
   
   /**
    * Returns an iterator that lets one go through the list of all buildings.
    * @return an iterator into the list of all buildings
    */
   public Iterator<Building> getBuildingIterator() {
      return this.buildings.iterator();
   }
   
   
   /**
    * Returns an iterator that lets one go through the list of all gorillas.
    * @return an iterator into the list of all gorillas
    */
   public Iterator<Gorilla> getGorillaIterator() {
      return this.gorillas.iterator();
   }
   
   
   /**
    * This method checks whether a circle and a rectangle intersect. This is
    *   useful for detecting whether a banana hit a gorilla or a building
    * @param x The x coordinate of the center of the circle.
    * @param y The y coordinate of the center of the circle.
    * @param radius The radius of the circle.
    * @param left The x coordinate of the bottom left corner of the rectangle
    * @param bottom The y coordinate of the bottom left corner of the rectangle
    * @param width The height of the rectangle
    * @param height The width of the rectangle
    * @return <tt>true</tt> if the circle and the rectangle intersect,
    *   <tt>false</tt> otherwise
    */
   public static boolean circleIntersectsRectangle(double x, double y, double radius,
         int left, int bottom, int width, int height) {
      // Find horizontal distance
      double dx = 0;
      if (x < left) {
         dx = x - left;
      } else if (x > left + width) {
         dx = x - (left + width);
      }
      // Find vertical distance
      double dy = 0;
      if (y < bottom) {
         dy = y - bottom;
      } else if (y > bottom + height) {
         dy = y - (bottom + height);
      }
      // And now we can decide whether the banana hit the house
      return ((dx == 0 && dy == 0) || 
            Math.sqrt(dx * dx + dy * dy) <= radius);
   }
}
