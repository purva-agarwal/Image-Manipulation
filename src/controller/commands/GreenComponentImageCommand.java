package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code GreenComponentImageCommand} class represents a command to create the green component.
 * It is using an {@link ImageProcessor}. It implements the {@link ImageCommand} interface.
 */
public class GreenComponentImageCommand implements ImageCommand {

  /**
   * Executes the command to create the green component of an image.
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
    processor.createColorComponent(args[1], args[2], 1);
    viewer.showString("Green Component of Image Created.");
  }
}
