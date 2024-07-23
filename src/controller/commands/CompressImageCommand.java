package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code CompressImageCommand} class represents a command to compress an image.
 * It is using an {@link ImageProcessor}.
 * It implements the {@link ImageCommand} interface.
 */
public class CompressImageCommand implements ImageCommand {

  /**
   * Executes the command to compress an image.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects at least three arguments:
   *                  args[1] - the compression percentage (a double between 0 and 100)
   *                  args[2] - the source image name
   *                  args[3] - the destination image name
   * @throws IOException           If an I/O error occurs during image processing.
   * @throws NumberFormatException If the compression percentage is not a valid double.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
          throws IOException, NumberFormatException {
    try {
      double compressionPercentage = Double.parseDouble(args[1]);

      if (compressionPercentage < 0 || compressionPercentage > 100 || args[1].isBlank()) {
        viewer.showErrorMessage("The Compression Percentage should be between 0 and 100.");
      } else {
        processor.compressImage(compressionPercentage, args[2], args[3]);
        viewer.showString("Successfully Compressed the Image.");
      }
    } catch (NumberFormatException e) {
      viewer.showErrorMessage("Invalid compression percentage. Please provide a valid number.");
    }
  }
}
