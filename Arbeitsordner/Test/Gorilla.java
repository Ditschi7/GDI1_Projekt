/**
 * This class represents a player in the game. It keeps track of the following
 * informpation about the player:
 * <ul>
 *   <li>name</li>
 *   <li>position</li> 
 *   <li>height and width (we will assume the player is a rectangle)</li>
 *   <li>score</li>
 *   <li>whether the player was hit by a banana</li>
 *   <li>whether the player is still in the game (it is in the game unless it's
 *     overtime and the gorilla did not share the high score at the beginning of 
 *     overtime)</li>
 *  </ul>
 */
public class Gorilla {
   /**
    * The height of a gorilla
    */
   public static final int HEIGHT = 60;
   /**
    * The width of a gorilla
    */
   public static final int WIDTH = 40;
   
   
   /**
    * Creates a new gorilla with the specified name.
    * @param name The name for the new gorilla.
    */
   public Gorilla(String name) {
      // TODO
   }

   
   /**
    * Returns the name of this gorilla.
    * @return the name of this gorilla
    */
   public String getName() {
      // TODO
      return "";
   }

   
   /**
    * Returns the x coordinate of the bottom left corner of the gorilla.
    * @return the x coordinate of the bottom left corner of the gorilla
    */
   public int getLeft() {
      // TODO
      return 0;
   }
   
   
   /**
    * Returns the x coordinate of the bottom left corner of the gorilla
    *   plus half the gorilla's width rounded down. This method is provided for convenience.
    * @return the x coordinate of the bottom left corner of the gorilla
    *   plus half the gorilla's width rounded down.
    */
   public int getMiddle() {
      // TODO
      return 0;
   }
   
   
   /**
    * Returns the y coordinate of the bottom left corner of the gorilla.
    * @return the y coordinate of the bottom left corner of the gorilla
    */
   public int getBottom() {
      // TODO
      return 0;
   }
   
   
   /**
    * Sets where this gorilla is in the game area by specifying the position
    * of the gorillas bottom left corner.
    * @param left The x coordinate of the bottom left corner of the gorilla.
    * @param bottom The y coordinate of the bottom left corner of the gorilla.
    */
   public void place(int left, int bottom) {
      // TODO
   }
   
   
   /**
    * Returns how many points this gorilla has.
    * @return how many points this gorilla has
    */
   public int getScore() {
      // TODO
      return 0;
   }
   
   
   /**
    * Changes the gorilla's score by the given amount.
    * @param points The number of points to add to the gorilla's score (can be negative)
    */
   public void addToScore(int points) {
      // TODO
   }
      
   
   /**
    * Indicates whether this gorilla was hit by a banana or an explosion shock wave
    *   this round.
    * @return whether this gorilla was hit by a banana or an explosion shock wave
    *   this round.
    */
   public boolean wasHit() {
      // TODO
      return false;
   }
   
   
   /**
    * Set whehther the gorilla was hit by a banana this round.
    * @param wasHit true if the gorilla was hit, false otherwise
    */
   public void setWasHit(boolean wasHit) {
      // TODO
   }
   
   
   /**
    * Sets the gorilla's score to zero.
    */
   public void resetScore() {
      // TODO
   }
   
   
   /**
    * Indicates whether this gorilla is still in the game. This method should
    *   return true unless it's overtime and the gorilla did not share the
    *   high score at the beginning of overtime.
    * @return
    */
   public boolean isStillPlaying() {
      // TODO
      return true;
   }
   
   
   /**
    * The gorilla is not participating in the game after this method is called.
    */
   public void removeFromGame() {
      // TODO
   }
   
   
   /**
    * The gorilla is now again participating in the game after this method is called.
    */
   public void putInGame() {
      // TODO
   }
   
   
   /**
    * 
    */
   public String toString() {
      // TODO
      return "";
   }
}
