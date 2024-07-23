package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code BlurImageCommand} class represents a command to apply a blur filter to an image.
 * It uses an {@link ImageProcessor}. It implements the {@link ImageCommand} interface.
 */
public class BlurImageCommand implements ImageCommand {

  /**
   * Executes the command to apply a Gaussian blur filter to an image.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects at least two arguments:
   *                  args[1] - the source image name
   *                  args[2] - the destination image name
   *                  Additional optional argument (args[3]) to specify the split value.
   *                  The split value should be a non-negative integer.
   *                  If not provided or invalid, default is used.
   * @throws IOException If an I/O error occurs during image processing.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
          throws IOException {
    double[][] gaussianBlurKernel = {
            {1.0 / 16.0, 2.0 / 16.0, 1.0 / 16.0},
            {2.0 / 16.0, 4.0 / 16.0, 2.0 / 16.0},
            {1.0 / 16.0, 2.0 / 16.0, 1.0 / 16.0}
    };

    if (args.length > 3) {
      try {
        if (Integer.parseInt(args[(args.length - 1)]) >= 0) {
          processor.applyConvolutionFilter(args[1], args[2], gaussianBlurKernel,
                  "split", Integer.parseInt(args[(args.length - 1)]));
          viewer.showString("Image blurred.");
        } else {
          viewer.showErrorMessage("Split value cannot be negative.");
        }
      } catch (NumberFormatException e) {
        viewer.showErrorMessage("Invalid split value. Please provide a valid number.");
      }
    } else if (args.length == 3) {
      processor.applyConvolutionFilter(args[1], args[2], gaussianBlurKernel,
              "no-split", 0);
      viewer.showString("Image blurred.");
    }
  }
}
