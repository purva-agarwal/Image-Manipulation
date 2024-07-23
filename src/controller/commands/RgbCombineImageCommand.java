package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code RgbCombineImageCommand} class represents a command to combine R,G,B values
 * It implements the {@link ImageCommand} interface.
 */
public class RgbCombineImageCommand implements ImageCommand {

  /**
   * Executes the command to combine red, green, and blue color components into a full-color image.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects four arguments:
   *                  args[1] - the name of the red component image
   *                  args[2] - the name of the green component image
   *                  args[3] - the name of the blue component image
   *                  args[4] - the name to assign to the combined full-color image
   * @throws IOException If an I/O error occurs during image processing.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
          throws IOException {
    processor.rgbCombine(args[1], args[2], args[3], args[4]);
    viewer.showString("Image Combined.");
  }
}
