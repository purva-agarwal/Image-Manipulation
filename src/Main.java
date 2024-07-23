import java.io.IOException;

import controller.ImageController;
import view.ImageViewer;

/**
 * This Main class is the entry point of our application.
 * It initializes an ImageViewer and an ImageController and starts the application.
 */
public class Main {
  /**
   * The main method is the entry point of our application.
   *
   * @param args The command-line arguments.
   * @throws IOException If there is an input/output error while running the application.
   */

  public static void main(String[] args) throws IOException {
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);

    if (args.length == 0) {
      controller.openGUIMode();
    } else if (args.length == 1) {
      if (args[0].equals("-text")) {
        controller.openTextMode();
      } else {
        viewer.showErrorMessage("Invalid argument. "
                + "Please use '-text' for text mode or no arguments for GUI.");
      }
    } else if (args.length == 2 && args[0].equals("-file")) {
      controller.openAndExecuteFile(args[1]);
    } else {
      viewer.showErrorMessage("Invalid argument combination."
          + " Use '-text' or '-file path/to/script/file'");
    }
  }

}
