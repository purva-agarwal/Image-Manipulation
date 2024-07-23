package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code ColorCorrectImageCommand} class represents a command to perform color correction.
 * It uses a {@link ImageProcessor}. It implements the {@link ImageCommand} interface.
 */
public class ColorCorrectImageCommand implements ImageCommand {

  /**
   * Executes the command to perform color correction on an image.
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
    if (args.length > 3) {
      try {
        if (Integer.parseInt(args[(args.length - 1)]) >= 0) {
          processor.colorCorrectImage(args[1], args[2],
                  "split", Integer.parseInt(args[(args.length - 1)]));
          viewer.showString("Image is color corrected with split.");
        } else {
          viewer.showErrorMessage("Split value cannot be negative.");
        }
      } catch (NumberFormatException e) {
        viewer.showErrorMessage("Invalid split value. Please provide a valid number.");
      }
    } else if (args.length == 3) {
      processor.colorCorrectImage(args[1], args[2], "no-split", 0);
      viewer.showString("Image is color corrected.");
    }
  }
}
