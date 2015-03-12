/**
 * This class represents a banana. It keeps track of the position of the banana,
 *   its radius, its direction and its velocity. The banana produces a shock wave
 *   when it splats after hitting a gorilla or a building. 
 *   The class keeps track of the range in which a banana's 
 *   shock wave hurts gorillas as well as the progress of the shock wave.
 */
public class Banana {
   /**
    * The radius of all bananas
    */
   public static final double RADIUS = 10.0;
   /**
    * The reach of the bananas' explosion shock waves
    */
   public static final int MAX_SHOCK_WAVE_RADIUS = 30;
   
   /**
    * Makes a new banana with the specified position. The banana moves along
    *   the two coordinate axes at the specified velocities.
    * @param x The x coordinate of the center of the banana.
    * @param y The y coordinate of the center of the banana.
    * @param xVel The velocity of the banana in the direction of the x axis.
    * @param yVel The velocity of the banana in the direction of the y axis.
    */
   public Banana(double x, double y, double xVel, double yVel) {
      // TODO
   }
   
   
   /**
    * Returns the x coordinate of the center of the banana
    * @return the x coordinate of the center of the banana
    */
   public double getX() {
      // TODO
      return 0;
   }
   
   
   /**
    * Returns the y coordinate of the center of the banana
    * @return the y coordinate of the center of the banana
    */
   public double getY() {
      // TODO
      return 0;
   }
   
   
   /**
    * Simulates the behavior of the banana for one tick.
    * @param time The length of a tick
    * @param gravity Gravitational constant
    */
   public void tick(double time, double gravity) {
      // TODO
   }
   
   
   /**
    * Indicates whether the banana splatted already.
    * @return <tt>true</tt> if the banana is splatting, <tt>false</tt> otherwise
    */
   public boolean isSplatting() {
      // TODO
      return false;
   }
   
   
   /**
    * Sets the banana to the splatting state. The banana should not move anymore
    */
   public void splat() {
      // TODO
   }


   /**
    * Returns the current radius of the banana's shock wave
    * @return the current radius of the banana's shock wave
    */
   public int getCurrentShockWaveRadius() {
      // TODO
      return 0;
   }
   
   
   /**
    * Returns information about the banana as a string. This will help you
    *    with debugging. 
    */
   public String toString() {
      // TODO
      return "";
   }
}
