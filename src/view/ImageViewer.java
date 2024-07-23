package view;

import java.io.PrintStream;

import static java.lang.System.err;

/**
 * The ImageViewer class provides a text-based user interface for displaying messages and options to
 * the user. It implements the ImageView interface and can be used to interact with the
 * application.
 */
public class ImageViewer implements ImageView {

  private final PrintStream out;
  private GUIViewer guiViewer;

  /**
   * Constructs an ImageViewer with the specified PrintStream for output.
   *
   * @param out The PrintStream to which output messages will be written.
   */
  public ImageViewer(PrintStream out) {
    this.out = out;
  }

  public void setGUIViewer(GUIViewer guiViewer) {
    this.guiViewer = guiViewer;
  }

  /**
   * Displays a string message to the user.
   *
   * @param s The string message to be displayed.
   */
  public void showString(String s) {
    out.println(s);
  }

  /**
   * Displays the available options to the user.
   */
  public void showOptions() {
    out.println("Choose an option:");
    out.println("1. Run a script file");
    out.println("2. Enter commands inline");
    out.println("3. Exit");
    out.print("Enter your choice: ");
  }

  /**
   * Prompts the user to enter commands or to type 'exit' to quit.
   */
  public void showCommandEntry() {
    out.print("\nEnter commands or type 'exit' to quit.\n");
  }

  /**
   * Displays an error message to the user regarding an invalid option.
   */
  public void showOptionError() {
    out.print("\nInvalid option. Please try again.");
  }

  /**
   * Displays an error message to the user.
   *
   * @param errorMessage The error message to be displayed.
   */
  public void showErrorMessage(String errorMessage) {
    err.println(errorMessage);
    if (guiViewer != null) {
      guiViewer.displayErrorMessage(errorMessage);
    }
  }


}
