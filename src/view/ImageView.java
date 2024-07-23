package view;

/**
 * The ImageView interface defines methods for displaying messages and options to the user.
 */
public interface ImageView {

  /**
   * Displays a string message to the user.
   *
   * @param s The string message to be displayed.
   */
  void showString(String s);

  /**
   * Displays the available options to the user.
   */
  void showOptions();

  /**
   * Prompts the user to enter commands or to type 'exit' to quit.
   */
  void showCommandEntry();

  /**
   * Displays an error message to the user regarding an invalid option.
   */
  void showOptionError();

  /**
   * Displays an error message to the user.
   *
   * @param errorMessage The error message to be displayed.
   */
  void showErrorMessage(String errorMessage);

}
