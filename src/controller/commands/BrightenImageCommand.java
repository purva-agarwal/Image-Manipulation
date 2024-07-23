package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code BrightenImageCommand} class represents a command to adjust the brightness of an image.
 * It uses {@link ImageProcessor}. It implements the {@link ImageCommand} interface.
 */
public class BrightenImageCommand implements ImageCommand {

  /**
   * Executes the command to adjust the brightness of an image.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects at least three arguments:
   *                  args[1] - the brightness adjustment value (an integer)
   *                  args[2] - the source image name
   *                  args[3] - the destination image name
   * @throws IOException           If an I/O error occurs during image processing.
   * @throws NumberFormatException If the brightness adjustment value is not a valid integer.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
          throws IOException, NumberFormatException {
    try {
      int brightnessChange = Integer.parseInt(args[1]);

      if (brightnessChange != 0) {
        processor.adjustBrightness(brightnessChange, args[2], args[3]);
        viewer.showString("Image Brightened by " + args[1]);
      } else {
        processor.adjustBrightness(brightnessChange, args[2], args[3]);
        viewer.showString("Image Brightened by " + args[1]);
      }
    } catch (NumberFormatException e) {
      viewer.showErrorMessage("Brightness Change is not entered.");
    }
  }
}
