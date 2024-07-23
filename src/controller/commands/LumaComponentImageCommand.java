package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code LumaComponentImageCommand} class represents a command to create the luma component.
 * It implements the {@link ImageCommand} interface.
 */
public class LumaComponentImageCommand implements ImageCommand {

  /**
   * Executes the command to create the luma component of an image.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects three arguments:
   *                  args[1] - the name of the source image
   *                  args[2] - the name to assign to the created luma component image
   * @throws IOException If an I/O error occurs during image processing.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
          throws IOException {
    double[] coefficients = {0.2126, 0.7152, 0.0722};
    processor.createComponent(args[1], args[2], coefficients);
    viewer.showString("Luma Component of Image Created.");
  }
}
