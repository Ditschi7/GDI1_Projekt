import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * This class contains a main method which runs the gorilla game.
 * Create and set up the game
 */
public class MainClass {
   public static void main(String[] args) {
      final int TICKS_PER_SECOND = 40;
      
      // Process command line args
      //  - Time limits
      // If first two args are -t <time>,
      //   first remove them and then apply the list below
      int timeLimit = 0;
      if (args.length > 1) {
         if (args[0].equals("-t")) {
            timeLimit = Integer.parseInt(args[1]);
            args = Arrays.copyOfRange(args, 2, args.length);
         }
      }
      // args[0] Width of the game area
      // args[1] Height of the game area
      // args[2] Number of rounds
      // args[3] Number of overtime rounds
      // args[4] Gravity constant
      // The remaining command line arguments are player names. At least two
      //   should be specified.
      if (args.length < 7) {
         System.err.println("Insufficient command line arguments.");
         System.err.println("Check the \"Running Your Program\" section of the specification");
         System.exit(-1);
      }
      int width = Integer.parseInt(args[0]);
      int height = Integer.parseInt(args[1]);
      int numRounds = Integer.parseInt(args[2]);
      int numOvertimeRounds = Integer.parseInt(args[3]);
      double gravity = Double.parseDouble(args[4]);
      String[] names = Arrays.copyOfRange(args, 5, args.length);
      
      // Create and set up the game
      Game game = null;
      if (timeLimit != 0) {
         // We use reflection for the added constructor because we are not
         //   guaranteed that students implement the turn time limits feature
         try {
            Constructor<?> c = Class.forName("Game").getConstructor(new Class<?>[]{
                  int.class, int.class, String[].class, int.class,
                  int.class, double.class, int.class
            });
            game = (Game)c.newInstance(numRounds, numOvertimeRounds, names, height, width, gravity, 
                  timeLimit  * TICKS_PER_SECOND);
         } catch (ClassNotFoundException e) {
            System.err.println("Can't find Game class");
            System.exit(-1);
         } catch (NoSuchMethodException e) {
            System.err.println("Missing constructor for turn time limits");
            e.printStackTrace();
            System.exit(-1);
         } catch (InvocationTargetException e) {
            System.err.println("Game constructor crashed");
            e.printStackTrace();
            System.exit(-1);
         } catch (IllegalAccessException e) {
            System.err.println("Cannot call Game constructor");
            e.printStackTrace();
            System.exit(-1);
         } catch (InstantiationException e) {
            System.err.println("Cannot call Game constructor");
            e.printStackTrace();
            System.exit(-1);
         }
      } else {
         game = new Game(numRounds, numOvertimeRounds, names, height, width, gravity);
      }

      // Set up the threads that will run the game and continuously display
      //   it in the window (which also gets set up).  
      // We are using the MVC design pattern. The model part is the
      //   CarGame object, the view part is the CarGameGUI object,
      //   and the controller part is the CarGameController object.
      GameController controller = new GameController(game, TICKS_PER_SECOND);
      GameGUI viewer = new GameGUI("Gorillas", controller);
      controller.setViewer(viewer); // Controller needs a reference to the viewer, but
      //   the viewer did not exist at the point when the
      //   controller was instantiated.

      // Now that the thread has been set up, we can start it and show the viewer
      controller.start();
      viewer.showMe();
      viewer.setLocation(0, 0);

      // Once the thread finishes, we close the window and the program terminates.
      try {
         controller.join();
      } catch (InterruptedException e) {}
      viewer.close();
   }

}
