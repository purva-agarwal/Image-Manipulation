package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code RedComponentImageCommand} class represents a command to create the red component.
 * It implements the {@link ImageCommand} interface.
 */
public class RedComponentImageCommand implements ImageCommand {

  /**
   * Executes the command to create the red component of an image.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects three arguments:
   *                  args[1] - the name of the source image
   *                  args[2] - the name to assign to the created red component image
   * @throws IOException If an I/O error occurs during image processing.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
          throws IOException {
    processor.createColorComponent(args[1], args[2], 0);
    viewer.showString("Red Component of Image Created.");
  }
}
