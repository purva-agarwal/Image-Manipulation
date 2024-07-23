package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code RgbSplitImageCommand} class represents a command to split a full-color image in r,g,b.
 * It implements the {@link ImageCommand} interface.
 */
public class RgbSplitImageCommand implements ImageCommand {

  /**
   * Executes the command to split a full-color image into its individual r, g, b color components.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects four arguments:
   *                  args[1] - the name of the full-color image to split
   *                  args[2] - the name to assign to the red component image
   *                  args[3] - the name to assign to the green component image
   *                  args[4] - the name to assign to the blue component image
   * @throws IOException If an I/O error occurs during image processing.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
          throws IOException {
    processor.rgbSplit(args[1], args[2], args[3], args[4]);
    viewer.showString("Image Splitted into its Red, Green, and Blue Components.");
  }
}
