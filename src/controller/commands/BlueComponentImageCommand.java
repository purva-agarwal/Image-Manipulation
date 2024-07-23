package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code BlueComponentImageCommand} class represents a command to create the blue component.
 * It uses a {@link ImageProcessor}. It implements the {@link ImageCommand} interface.
 */
public class BlueComponentImageCommand implements ImageCommand {

  /**
   * Executes the command to create the blue component of an image.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects at least two arguments:
   *                  args[1] - the source image name
   *                  args[2] - the destination image name
   * @throws IOException If an I/O error occurs during image processing.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
          throws IOException {
    processor.createColorComponent(args[1], args[2], 2);
    viewer.showString("Blue Component of Image Created.");
  }
}
