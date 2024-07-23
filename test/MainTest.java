import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.Test;

/**
 * This class contains test cases for the Main class functionalities.
 */
public class MainTest {

  /**
   * Tests the behavior when no arguments are passed, verifying the GUI mode opening.
   */
  @Test
  public void testOpenGUIMode_NoArgs() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    String[] args = {};
    try {
      Main.main(args);
    } catch (IOException e) {
      e.printStackTrace();
    }
    String expectedOutput = "GUI Mode Opened.";
    assertEquals(expectedOutput, outputStream.toString().trim());
  }

  /**
   * Tests the behavior when '-text' argument is passed, verifying the text mode opening.
   */
  @Test
  public void testOpenTextMode_TextArg() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    String[] args = {"-text"};
    try {
      Main.main(args);
    } catch (IOException e) {
      e.printStackTrace();
    }
    String expectedOutput = "Text Mode Opened.";
    assertEquals(expectedOutput, outputStream.toString().trim());
  }

  /**
   * Tests the behavior when an invalid argument is passed, verifying the error message display.
   */
  @Test
  public void testInvalidArgs_ShowErrorMessage() {
    ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errorStream));
    String[] args = {"-invalid"};
    try {
      Main.main(args);
    } catch (IOException e) {
      e.printStackTrace();
    }
    String expectedOutput = "Invalid argument. Please use '-text' for"
        + " text mode or no arguments for GUI.";
    String actualErrorMessage = errorStream.toString().trim();

    assertEquals(expectedOutput, actualErrorMessage);
  }

  /**
   * Tests the behavior when '-file' argument is passed, verifying file execution.
   */
  @Test
  public void testOpenAndExecuteFile_ValidArgs() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    String[] args = {"-file", "res/smalltest.txt"};
    try {
      Main.main(args);
    } catch (IOException e) {
      e.printStackTrace();
    }
    String expectedOutput = String.format(
        "File Opened and Executed.%n" + "load res/testimage.png testimage%n" + "IO Image Loaded.");
    assertEquals(expectedOutput, outputStream.toString().trim());
  }

  /**
   * Tests the behavior when an invalid combination of arguments is passed, verifying the error
   * message display.
   */
  @Test
  public void testInvalidArgCombination_ShowErrorMessage() {
    ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errorStream));

    String[] args = {"-invalid", "path/to/script/file"};

    try {
      Main.main(args);
    } catch (IOException e) {
      e.printStackTrace();
    }

    String expectedErrorMessage = "Invalid argument combination. "
        + "Use '-text' or '-file path/to/script/file'";
    String actualErrorMessage = errorStream.toString().trim();

    assertEquals(expectedErrorMessage, actualErrorMessage);
  }
}
