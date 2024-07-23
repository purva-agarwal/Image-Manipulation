package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code LevelsAdjustment} class represents a command to perform levels adjustment on an image.
 * It is using an {@link ImageProcessor}. It implements the {@link ImageCommand} interface.
 */
public class LevelsAdjustment implements ImageCommand {

  private static final int MIN_BOUND = 0;
  private static final int MAX_BOUND = 255;

  /**
   * Executes the command to perform levels adjustment on an image.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects at least six arguments:
   *                  args[1] - black point (b)
   *                  args[2] - mid point (m)
   *                  args[3] - white point (w)
   *                  args[4] - source image name
   *                  args[5] - destination image name
   *                  Additional optional argument (args[6]) to specify the split value.
   *                  The split value should be a non-negative integer.
   *                  If not provided or invalid, default is used.
   * @throws IOException           If an I/O error occurs during image processing.
   * @throws NumberFormatException If the split value is not a valid integer.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
          throws IOException, NumberFormatException {
    if (args[1].isBlank() || args[2].isBlank() || args[3].isBlank()) {
      viewer.showErrorMessage("Missing b or m or w value.");
      return;
    }

    int b = Integer.parseInt(args[1]);
    int m = Integer.parseInt(args[2]);
    int w = Integer.parseInt(args[3]);

    if (b < MIN_BOUND || b >= m || m >= w || w > MAX_BOUND) {
      viewer.showErrorMessage("Invalid input: Ensure 0 <= b < m < w <= 255.");
      return;
    }

    if (args.length > 6) {
      try {
        if (Integer.parseInt(args[(args.length - 1)]) >= 0) {
          processor.adjustLevels(Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                  Integer.parseInt(args[3]), args[4], args[5],
                  "split", Integer.parseInt(args[(args.length - 1)]));
          viewer.showString("Level Adjustment is completed on image.");
        } else {
          viewer.showErrorMessage("Split value cannot be negative.");
        }
      } catch (NumberFormatException e) {
        viewer.showErrorMessage("Invalid split value. Please provide a valid number.");
      }
    } else if (args.length == 6) {
      processor.adjustLevels(Integer.parseInt(args[1]), Integer.parseInt(args[2]),
              Integer.parseInt(args[3]), args[4], args[5], "no-split", 0);
      viewer.showString("Level Adjustment is completed on image.");
    }
  }
}
