package controller.commands;

import java.io.IOException;

import controller.ImageController;
import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code ScriptExecutionCommand} class represents a command to execute a script file.
 * It implements the {@link ImageCommand} interface.
 */
public class ScriptExecutionCommand implements ImageCommand {

  /**
   * Executes the command to run a script file.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects at least two arguments:
   *                  args[1] - the script file name to execute
   * @throws IOException If an I/O error occurs during script execution.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
          throws IOException {
    if (args.length < 2) {
      viewer.showErrorMessage("No script file provided.");
      return;
    }
    String scriptFileName = args[1];
    new ImageController(viewer).runScript(scriptFileName);
  }
}
