package controller.commands;

import java.io.IOException;

import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code ImageCommand} interface defines a contract for implementing image processing commands.
 * Classes that implement this interface must provide an execution method that takes ImageProcessor,
 * an ImageViewer, and an array of command arguments.
 */
public interface ImageCommand {

  /**
   * Executes the image processing command.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command.
   * @throws IOException If an I/O error occurs during image processing.
   */
  void execute(ImageProcessor processor, ImageViewer viewer, String[] args) throws IOException;
}
