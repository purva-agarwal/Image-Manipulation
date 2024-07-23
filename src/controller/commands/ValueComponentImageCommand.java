package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code ValueComponentImageCommand} class represents a command to create the value component.
 * It implements the {@link ImageCommand} interface.
 */
public class ValueComponentImageCommand implements ImageCommand {

  /**
   * Executes the command to create the value component of an image using given coefficients.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects at least two arguments:
   *                  args[1] - the source image name
   *                  args[2] - the destination image name
   * @throws IOException If an I/O error occurs during image processing.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
          throws IOException {
    double[] coefficients = {1, 1, 1};
    processor.createComponent(args[1], args[2], coefficients);
    viewer.showString("Value Component of Image Created.");
  }
}
