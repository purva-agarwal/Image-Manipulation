package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code GreyscaleImageCommand} class represents a command to change image to grayscale.
 * It uses an {@link ImageProcessor}. It implements the {@link ImageCommand} interface.
 */
public class GreyscaleImageCommand implements ImageCommand {

  /**
   * Executes the command to change the tone of an image to grayscale.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects at least two arguments:
   *                  args[1] - the source image name
   *                  args[2] - the destination image name
   *                  Additional optional argument (args[3]) to specify the split value.
   *                  The split value should be a non-negative integer.
   *                  If not provided or invalid, default is used.
   * @throws IOException           If an I/O error occurs during image processing.
   * @throws NumberFormatException If the split value is not a valid integer.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
          throws IOException, NumberFormatException {
    double[][] grayscaleMatrix = {
            {0.299, 0.587, 0.114},
            {0.299, 0.587, 0.114},
            {0.299, 0.587, 0.114}
    };

    if (args.length > 3) {
      try {
        if (Integer.parseInt(args[(args.length - 1)]) >= 0) {
          processor.graySepia(args[1], args[2],
                  "split", Integer.parseInt(args[(args.length - 1)]), grayscaleMatrix);
          viewer.showString("Image Tone Changed to GreyScale.");
        } else {
          viewer.showErrorMessage("Split value cannot be negative.");
        }
      } catch (NumberFormatException e) {
        viewer.showErrorMessage("Invalid split value. Please provide a valid number.");
      }
    } else if (args.length == 3) {
      processor.graySepia(args[1], args[2],
              "no-split", 0, grayscaleMatrix);
      viewer.showString("Image Tone Changed to GreyScale.");
    }
  }
}
