package controller;

import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The ImageControllerInterface defines the contract for controlling and coordinating
 * image processing operations based on user input.
 */
public interface ImageControllerInterface {

  /**
   * Runs a script file containing a series of image processing commands.
   *
   * @param scriptFilePath The path to the script file.
   * @throws IOException If there is an error reading the script file.
   */
  void runScript(String scriptFilePath) throws IOException;


  /**
   * Executes an image processing command based on the provided input command string.
   *
   * @param cmd The command to execute.
   * @throws IOException If an I/O error occurs during image processing or if the provided
   *                     command is invalid or not recognized.
   */
  void executeCommand(String cmd) throws IOException;

  /**
   * Opens and executes a script file specified by the given file name.
   *
   * @param scriptFileName The name of the script file to be opened and executed.
   * @throws IOException If an I/O error occurs while reading the script file.
   */
  void openAndExecuteFile(String scriptFileName) throws IOException;

  /**
   * Opens the text mode, allowing the user to enter commands interactively.
   *
   * @throws IOException If an I/O error occurs during command entry or execution.
   */
  void openTextMode() throws IOException;

  /**
   * Opens the graphical user interface (GUI) mode by initializing a new GUIViewer
   * and setting up various action listeners for GUI components.
   * The method associates the GUIViewer with the main viewer, enabling interaction with the GUI.
   */
  void openGUIMode();

  /**
   * Returns an ActionListener for the "Load" action, handling the loading of an image
   * and displaying it in the GUI viewer.
   *
   * @return ActionListener for the "Load" action.
   */
  ActionListener getLoadActionListener();

  /**
   * Returns an ActionListener for the "Save" action, handling the saving of the current image.
   *
   * @return ActionListener for the "Save" action.
   */
  ActionListener getSaveActionListener();

  /**
   * Returns an ActionListener for the "Histogram" action, handling the generation and
   * display of the histogram for the current image.
   *
   * @return ActionListener for the "Histogram" action.
   */
  ActionListener getHistogramActionLister();

  /**
   * Returns an ActionListener for the "Brighten" action, handling the brightening of the image.
   *
   * @return ActionListener for the "Brighten" action.
   */
  ActionListener getBrightenActionListener();

  /**
   * Returns an ActionListener for the "Compress" action, handling the compression of the image.
   *
   * @return ActionListener for the "Compress" action.
   */
  ActionListener getCompressActionListener();

  /**
   * Returns an ActionListener for the "Luma" action, handling the Luma adjustment of the image.
   *
   * @return ActionListener for the "Luma" action.
   */
  ActionListener getLumaActionListener();

  /**
   * Returns an ActionListener for the "Horizontal Flip" action on the current image.
   *
   * @return ActionListener for the "Horizontal Flip" action.
   */
  ActionListener getHorizontalFlipActionListener();

  /**
   * Returns an ActionListener for the "Vertical Flip" action on the current image.
   *
   * @return ActionListener for the "Vertical Flip" action.
   */
  ActionListener getVerticalFlipActionListener();

  /**
   * Returns an ActionListener for the "Red" action, handling the adjustment of the red component.
   *
   * @return ActionListener for the "Red" action.
   */
  ActionListener getRedActionListener();

  /**
   * Returns an ActionListener for the "Green" action, adjusting the green component of the image.
   *
   * @return ActionListener for the "Green" action.
   */
  ActionListener getGreenActionListener();

  /**
   * Returns an ActionListener for the "Blue" action, adjusting the blue component of the image.
   *
   * @return ActionListener for the "Blue" action.
   */
  ActionListener getBlueActionListener();

  /**
   * Returns an ActionListener for the "Adjust Level" action, handles the adjustment of image level.
   *
   * @return ActionListener for the "Adjust Level" action.
   */
  ActionListener getAdjustLevelActionListener();

  /**
   * Returns an ActionListener for the "Sharpen" action, handling the sharpening of the image.
   *
   * @return ActionListener for the "Sharpen" action.
   */
  ActionListener getSharpenActionListener();

  /**
   * Returns an ActionListener for the "Blur" action, handling the blurring of the current image.
   *
   * @return ActionListener for the "Blur" action.
   */
  ActionListener getBlurActionListener();

  /**
   * Returns an ActionListener for the "Color Correct" action, handles color correction of an image.
   *
   * @return ActionListener for the "Color Correct" action.
   */
  ActionListener getColorCorrectActionListener();

  /**
   * Returns an ActionListener for the "Grey" action, handling the conversion of image to grayscale.
   *
   * @return ActionListener for the "Grey" action.
   */
  ActionListener getGreyActionListener();

  /**
   * Returns an ActionListener for the "Sepia" action, handling the application of sepia tone.
   *
   * @return ActionListener for the "Sepia" action.
   */
  ActionListener getSepiaActionListener();

}