package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code SepiaImageCommand} class represents a command to apply sepia tone to an image.
 * It implements the {@link ImageCommand} interface.
 */
public class SepiaImageCommand implements ImageCommand {

  /**
   * Executes the command to apply sepia tone to an image.
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
    double[][] sepiaMatrix = {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};

    if (args.length > 3) {
      try {
        if (Integer.parseInt(args[(args.length - 1)]) >= 0) {
          processor.graySepia(args[1], args[2], "split",
                  Integer.parseInt(args[(args.length - 1)]), sepiaMatrix);
          viewer.showString("Image Tone Changed to Sepia.");
        } else {
          viewer.showErrorMessage("Split value cannot be negative.");
        }
      } catch (NumberFormatException e) {
        viewer.showErrorMessage("Invalid split value. Please provide a valid number.");
      }
    } else if (args.length == 3) {
      processor.graySepia(args[1], args[2], "no-split", 0, sepiaMatrix);
      viewer.showString("Image Tone Changed to Sepia.");
    }
  }
}
