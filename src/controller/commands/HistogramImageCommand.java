package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code HistogramImageCommand} class represents a command to generate histogram for an image.
 * It is using an {@link ImageProcessor}. It implements the {@link ImageCommand} interface.
 */
public class HistogramImageCommand implements ImageCommand {

  /**
   * Executes the command to generate a histogram for an image.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects at least two arguments:
   *                  args[1] - the source image name
   *                  args[2] - the destination image name for the histogram (optional)
   * @throws IOException If an I/O error occurs during image processing.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
          throws IOException, NullPointerException {
    try {
      if (args.length == 3) {
        processor.plotHistogram(args[1], args[2]);
        viewer.showString("Histogram is Generated.");
      }
    } catch (NullPointerException e) {
      return;
    }
  }
}
