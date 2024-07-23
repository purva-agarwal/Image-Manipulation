package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code SharpenImageCommand} class represents a command to apply sharpening to an image.
 * It implements the {@link ImageCommand} interface.
 */
public class SharpenImageCommand implements ImageCommand {

  /**
   * Executes the command to apply sharpening to an image using a sharpening filter.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects at least two arguments:
   *                  args[1] - the source image name
   *                  args[2] - the destination image name
   *                  Optional: args[3] - "split" or "no-split" to indicate whether to split
   *                  args[4] - the split value (positive integer) for parallel processing
   * @throws IOException If an I/O error occurs during image processing.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
          throws IOException {
    double[][] sharpeningFilter = {
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };

    if (args.length > 3) {
      try {
        if (Integer.parseInt(args[(args.length - 1)]) >= 0) {
          processor.applyConvolutionFilter(args[1], args[2], sharpeningFilter,
                  "split", Integer.parseInt(args[(args.length - 1)]));
          viewer.showString("Image sharpened.");
        } else {
          viewer.showErrorMessage("Split value cannot be negative.");
        }
      } catch (NumberFormatException e) {
        viewer.showErrorMessage("Invalid split value. Please provide a valid number.");
      }
    } else if (args.length == 3) {
      processor.applyConvolutionFilter(args[1], args[2], sharpeningFilter,
              "no-split", 0);
      viewer.showString("Image sharpened.");
    }
  }
}
