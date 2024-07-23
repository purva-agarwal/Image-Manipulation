package controller;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import controller.commands.BlueComponentImageCommand;
import controller.commands.BlurImageCommand;
import controller.commands.BrightenImageCommand;
import controller.commands.ColorCorrectImageCommand;
import controller.commands.CompressImageCommand;
import controller.commands.GreenComponentImageCommand;
import controller.commands.GreyscaleImageCommand;
import controller.commands.HistogramImageCommand;
import controller.commands.HorizontalFlipImageCommand;
import controller.commands.IntensityComponentImageCommand;
import controller.commands.LevelsAdjustment;
import controller.commands.LoadImageCommand;
import controller.commands.LumaComponentImageCommand;
import controller.commands.RedComponentImageCommand;
import controller.commands.RgbSplitImageCommand;
import controller.commands.SaveImageCommand;
import controller.commands.ScriptExecutionCommand;
import controller.commands.SepiaImageCommand;
import controller.commands.SharpenImageCommand;
import controller.commands.ValueComponentImageCommand;
import controller.commands.VerticalFlipImageCommand;
import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.JButton;
import model.ImageOperations;
import model.ImageProcessor;
import org.junit.Before;
import org.junit.Test;
import view.ImageViewer;


/**
 * This class contains a collection of JUnit test cases to validate the functionality. It covers
 * various scenarios, such as loading and processing images. Also, executing different image
 * processing commands, handling invalid commands.
 */
public class ImageControllerTest {

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private final PrintStream printStream = new PrintStream(outputStream);
  private final ImageViewer mockViewer = new ImageViewer(printStream);
  private final ImageProcessor mockProcessor = new ImageOperations();
  private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
  private final String imagePath = "res/TestOutput/redComponentPpm.ppm";
  private ImageController controller;

  /**
   * Set up the necessary streams and initialize the ImageController and ImageViewer instances.
   */
  @Before
  public void setUpStreams() {
    ImageViewer viewer;
    ByteArrayOutputStream outContent;
    ByteArrayOutputStream errContent;
    outContent = new ByteArrayOutputStream();
    errContent = new ByteArrayOutputStream();

    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
    viewer = new ImageViewer(System.out);
    controller = new ImageController(viewer);
  }

  @Before
  public void setUpOutput() {
    System.setOut(new PrintStream(testOut));
  }


  /**
   * Test executing a command with a null command string, expecting a NullPointerException.
   *
   * @throws IOException If there is an error during image processing.
   */
  @Test(expected = NullPointerException.class)
  public void testExecuteCommandWithNullCommand() throws IOException {
    controller.executeCommand(null);
  }


  /**
   * Test executing a command to load a PPM image.
   *
   * @throws IOException If there is an error during image processing.
   */
  @Test
  public void testExecuteCommand_LoadPPMImage() throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    ImageViewer mockViewer = new ImageViewer(printStream);
    ImageController controller = new ImageController(mockViewer);
    controller.executeCommand("load images/koala.ppm koala");
    String output = outputStream.toString().trim();
    assertEquals("PPM Image Loaded.", output);
  }


  /**
   * Test executing a command to load an IO image.
   *
   * @throws IOException If there is an error during image processing.
   */
  @Test
  public void testExecuteCommand_LoadIOImage() throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    ImageViewer mockViewer = new ImageViewer(printStream);
    ImageController controller = new ImageController(mockViewer);
    controller.executeCommand("load res/testimage-bluePNG.png blue");
    String output = outputStream.toString().trim();
    assertEquals("IO Image Loaded.", output);
  }

  /**
   * Test executing a command with an invalid image path, expecting an IIOException.
   *
   * @throws IOException If there is an error during image processing.
   */
  @Test(expected = javax.imageio.IIOException.class)
  public void testExecuteCommand_InvalidImage() throws IOException {
    controller.executeCommand("load InvalidImage.png null");
  }

  /**
   * Test executing a command with an invalid image type, expecting an IIOException.
   *
   * @throws IOException If there is an error during image processing.
   */
  @Test(expected = javax.imageio.IIOException.class)
  public void testExecuteCommand_InvalidImageType() throws IOException {
    controller.executeCommand("load InvalidImage.def null");
  }

  /**
   * Test executing a blue component creation command.
   *
   * @throws IOException If there is an error during image processing.
   */
  @Test
  public void testExecute_BlueComponentCreation() throws IOException {
    BlueComponentImageCommand blueCommand = new BlueComponentImageCommand();

    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});
    blueCommand.execute(mockProcessor, mockViewer,
        new String[]{"blue-component", "koala", "blueOutputImage"});
    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Blue Component of Image Created."};
    String[] actualOutputs = output.split("\\R"); // Split by newlines
    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Test executing a blur image command.
   *
   * @throws IOException If there is an error during image processing.
   */
  @Test
  public void testExecute_BlurImage() throws IOException {
    BlurImageCommand blurCommand = new BlurImageCommand();
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});
    blurCommand.execute(mockProcessor, mockViewer, new String[]{"blur", "koala", "blurredImage"});
    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Image blurred."};
    String[] actualOutputs = output.split("\\R");
    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Test case to verify the execution of the BlurImageCommand with split.
   *
   * @throws IOException if an I/O error occurs during image processing or validation.
   */
  @Test
  public void testExecute_BlurImageCommand_WithSplit() throws IOException {
    BlurImageCommand blurCommand = new BlurImageCommand();
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});
    blurCommand.execute(mockProcessor, mockViewer,
        new String[]{"blur", "koala", "blurredImage", "split", "3"});

    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Image blurred."};
    String[] actualOutputs = output.split("\\R");

    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Validates the behavior of the 'BlurImageCommand' when provided with an invalid split value.
   *
   * @throws IOException If an error occurs during the image processing.
   */
  @Test
  public void testExecute_BlurImageCommand_WithInvalidSplitValue() throws IOException {
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    BlurImageCommand blurCommand = new BlurImageCommand();
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});
    blurCommand.execute(mockProcessor, mockViewer,
        new String[]{"blur", "koala", "blurredImage", "split", "invalid"});
    String errorMessage = errStream.toString().trim();
    System.setErr(System.err);
    assertEquals("Invalid split value. Please provide a valid number.", errorMessage);
  }


  /**
   * Test executing a command to brighten an image.
   *
   * @throws IOException If there is an error during image processing.
   */
  @Test
  public void testExecute_BrightenImage() throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    ImageViewer mockViewer = new ImageViewer(printStream);
    ImageProcessor mockProcessor = new ImageOperations();
    BrightenImageCommand brightenCommand = new BrightenImageCommand();
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});
    brightenCommand.execute(mockProcessor, mockViewer,
        new String[]{"brighten", "50", "koala", "brightenedImage"});
    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Image Brightened by 50"};
    String[] actualOutputs = output.split("\\R");
    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Test executing a color correction command for an image.
   *
   * @throws IOException If there is an error during image processing.
   */
  @Test
  public void testExecute_ColorCorrectImage() throws IOException {
    ColorCorrectImageCommand colorCorrectCommand = new ColorCorrectImageCommand();
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});
    colorCorrectCommand.execute(mockProcessor, mockViewer,
        new String[]{"color-correct", "koala", "correctedImage"});
    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Image is color corrected."};
    String[] actualOutputs = output.split("\\R");
    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Validates the behavior of the 'ColorCorrectImageCommand' when provided with an invalid split
   * value.
   *
   * @throws IOException If an error occurs during image processing.
   */
  @Test
  public void testExecute_ColorCorrectImageCommand_WithInvalidSplitValue() throws IOException {
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    ColorCorrectImageCommand colorCorrectCommand = new ColorCorrectImageCommand();
    colorCorrectCommand.execute(mockProcessor, mockViewer,
        new String[]{"color-correct", "koala", "correctedImage", "split", "invalid"});
    String errorMessage = errStream.toString().trim();
    System.setErr(System.err);
    assertEquals("Invalid split value. Please provide a valid number.", errorMessage);
  }


  /**
   * Validates the behavior of the 'CompressImageCommand' when given an invalid compression
   * percentage below 100.
   *
   * @throws IOException If an error occurs during image processing.
   */
  @Test
  public void testExecute_CompressImageCommand_WithInvalidCompressionPercentage()
      throws IOException {
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    CompressImageCommand compressCommand = new CompressImageCommand();
    compressCommand.execute(mockProcessor, mockViewer,
        new String[]{"compress", "-50", "koala", "compressedImage"});

    String errorMessage = errStream.toString().trim();
    System.setErr(System.err);
    assertEquals("The Compression Percentage should be between 0 and 100.", errorMessage);
  }


  /**
   * Validates the behavior of the 'CompressImageCommand' when given an invalid compression
   * percentage above 100.
   *
   * @throws IOException If an error occurs during image processing.
   */
  @Test
  public void testExecute_CompressImageCommand_WithInvalidGreaterPercentage() throws IOException {
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    CompressImageCommand compressCommand = new CompressImageCommand();
    compressCommand.execute(mockProcessor, mockViewer,
        new String[]{"compress", "101", "koala", "compressedImage"});

    String errorMessage = errStream.toString().trim();
    System.setErr(System.err);
    assertEquals("The Compression Percentage should be between 0 and 100.", errorMessage);
  }

  /**
   * Test executing an image compression command.
   *
   * @throws IOException If there is an error during image processing.
   */
  @Test
  public void testExecute_CompressImage() throws IOException {
    CompressImageCommand compressCommand = new CompressImageCommand();
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});
    compressCommand.execute(mockProcessor, mockViewer,
        new String[]{"compress", "50", "koala", "compressedImage"});
    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Successfully Compressed the Image."};
    String[] actualOutputs = output.split("\\R");
    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Test executing GreenComponentImageCommand and verifying its functionality.
   *
   * @throws IOException If there is an error during image processing.
   */
  @Test
  public void testExecute_GreenComponentCreation() throws IOException {
    GreenComponentImageCommand greenCommand = new GreenComponentImageCommand();

    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});
    greenCommand.execute(mockProcessor, mockViewer,
        new String[]{"green-component", "koala", "greenOutputImage"});
    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Green Component of Image Created."};
    String[] actualOutputs = output.split("\\R"); // Split by newlines
    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Validates the execution of the 'GreyscaleImageCommand' with a valid split value.
   *
   * @throws IOException If an error occurs during image processing.
   */
  @Test
  public void testExecute_GreyscaleImageCommand_WithValidSplit() throws IOException {
    GreyscaleImageCommand greyscaleCommand = new GreyscaleImageCommand();

    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});

    // Execute the GreyscaleImageCommand with a valid split value
    greyscaleCommand.execute(mockProcessor, mockViewer,
        new String[]{"greyscale", "koala", "outputImage", "split", "2"});

    String output = outputStream.toString().trim();

    // Assuming expected output messages after successful execution
    String[] expectedOutputs = {"PPM Image Loaded.", "Image Tone Changed to GreyScale."};
    String[] actualOutputs = output.split("\\R");

    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Validates the execution of the 'GreyscaleImageCommand' with an invalid split value.
   *
   * @throws IOException If an error occurs during image processing.
   */
  @Test
  public void testExecute_GreyscaleImageCommand_WithInvalidSplit() throws IOException {
    GreyscaleImageCommand greyscaleCommand = new GreyscaleImageCommand();
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});

    greyscaleCommand.execute(mockProcessor, mockViewer,
        new String[]{"greyscale", "koala", "outputImage", "split", "-5"});
    String errorMessage = errStream.toString().trim();
    System.setErr(System.err);
    assertEquals("Split value cannot be negative.", errorMessage);
  }

  /**
   * Validates the execution of the 'HistogramImageCommand'.
   *
   * @throws IOException If an error occurs during image processing.
   */
  @Test
  public void testExecute_HistogramImageCommand() throws IOException {
    HistogramImageCommand histogramCommand = new HistogramImageCommand();

    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});

    histogramCommand.execute(mockProcessor, mockViewer,
        new String[]{"histogram", "koala", "histogramOutput"});

    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Histogram is Generated."};
    String[] actualOutputs = output.split("\\R");

    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Tests the execution of the IntensityComponentImageCommand by simulating the command execution
   * and checking if the intensity component of the image is created correctly.
   *
   * @throws IOException if an error occurs during image processing or validation.
   */
  @Test
  public void testExecute_IntensityComponentImageCommand() throws IOException {
    IntensityComponentImageCommand intensityCommand = new IntensityComponentImageCommand();
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "image"});

    intensityCommand.execute(mockProcessor, mockViewer,
        new String[]{"create-intensity", "image", "intensityImage"});

    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Intensity Component of Image Created."};
    String[] actualOutputs = output.split("\\R");

    assertArrayEquals(expectedOutputs, actualOutputs);
  }


  /**
   * Tests the execution of the HorizontalFlipImageCommand by simulating the command execution and
   * checking if the output messages match the expected behavior when flipping an image
   * horizontally.
   *
   * @throws IOException if an error occurs during image processing or validation.
   */
  @Test
  public void testExecute_HorizontalFlipImageCommand() throws IOException {
    HorizontalFlipImageCommand flipCommand = new HorizontalFlipImageCommand();

    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "image"});

    flipCommand.execute(mockProcessor, mockViewer,
        new String[]{"flip-horizontal", "image", "flippedImage"});

    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Image Flipped Horizontally."};
    String[] actualOutputs = output.split("\\R");

    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Test for executing LevelsAdjustment with split values. It checks if the LevelsAdjustment
   * command is completed on an image with split values.
   *
   * @throws IOException If an I/O error occurs during execution.
   */
  @Test
  public void testExecute_LevelsAdjustment_WithSplit() throws IOException {
    LevelsAdjustment levelsCommand = new LevelsAdjustment();
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "image"});

    levelsCommand.execute(mockProcessor, mockViewer,
        new String[]{"levels", "10", "20", "30", "image", "outputImage", "split", "3"});

    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Level Adjustment is completed on image."};
    String[] actualOutputs = output.split("\\R");

    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Test for executing LevelsAdjustment without split. It checks if the LevelsAdjustment command is
   * completed on an image without using split.
   *
   * @throws IOException If an I/O error occurs during execution.
   */
  @Test
  public void testExecute_LevelsAdjustment_WithoutSplit() throws IOException {
    LevelsAdjustment levelsCommand = new LevelsAdjustment();
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "image"});

    levelsCommand.execute(mockProcessor, mockViewer,
        new String[]{"levels", "10", "20", "30", "image", "outputImage", "no-split", "0"});

    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Level Adjustment is completed on image."};
    String[] actualOutputs = output.split("\\R");

    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Test for executing LevelsAdjustment with a negative split value. It verifies that a negative
   * split value triggers an error message.
   *
   * @throws IOException If an I/O error occurs during execution.
   */
  @Test
  public void testExecute_LevelsAdjustment_WithNegativeSplit() throws IOException {
    LevelsAdjustment levelsCommand = new LevelsAdjustment();
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});

    levelsCommand.execute(mockProcessor, mockViewer,
        new String[]{"levels", "10", "20", "30", "inputImage", "outputImage", "split", "-5"});
    String errorMessage = errStream.toString().trim();
    System.setErr(System.err);
    assertEquals("Split value cannot be negative.", errorMessage);
  }

  /**
   * Test for executing LevelsAdjustment with invalid input (b < m < w). It validates that an
   * invalid input range triggers an error message.
   *
   * @throws IOException If an I/O error occurs during execution.
   */
  @Test
  public void testExecute_LevelsAdjustment_WithInvalidBMW() throws IOException {
    LevelsAdjustment levelsCommand = new LevelsAdjustment();
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});

    levelsCommand.execute(mockProcessor, mockViewer,
        new String[]{"levels-adjust", "20", "30", "310", "koala", "outputImage", "split", "5"});
    String errorMessage = errStream.toString().trim();
    System.setErr(System.err);
    assertEquals("Invalid input: Ensure 0 <= b < m < w <= 255.", errorMessage);
  }

  /**
   * Test for executing LevelsAdjustment with b,m,w out of order. It validates that an invalid input
   * range triggers an error message.
   *
   * @throws IOException If an I/O error occurs during execution.
   */
  @Test
  public void testExecute_LevelsAdjustment_WithOutOfOrderBMW() throws IOException {
    LevelsAdjustment levelsCommand = new LevelsAdjustment();
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});
    levelsCommand.execute(mockProcessor, mockViewer,
        new String[]{"levels-adjust", "255", "20", "100", "koala", "outputImage", "split", "5"});
    String errorMessage = errStream.toString().trim();
    System.setErr(System.err);
    assertEquals("Invalid input: Ensure 0 <= b < m < w <= 255.", errorMessage);
  }

  /**
   * Test for executing LevelsAdjustment with invalid input (b < m < w). It verifies that another
   * invalid input range triggers an error message.
   *
   * @throws IOException If an I/O error occurs during execution.
   */
  @Test
  public void testExecute_LevelsAdjustment_WithInvalidBMW2() throws IOException {
    LevelsAdjustment levelsCommand = new LevelsAdjustment();
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});
    levelsCommand.execute(mockProcessor, mockViewer,
        new String[]{"levels", "20", "30", "310", "inputImage", "outputImage", "split", "5"});
    String errorMessage = errStream.toString().trim();
    System.setErr(System.err);
    assertEquals("Invalid input: Ensure 0 <= b < m < w <= 255.", errorMessage);
  }

  /**
   * Test for executing LevelsAdjustment with invalid input (b < m < w). It confirms that negative
   * values trigger an error message.
   *
   * @throws IOException If an I/O error occurs during execution.
   */
  @Test
  public void testExecute_LevelsAdjustment_WithInvalidBMW3() throws IOException {
    LevelsAdjustment levelsCommand = new LevelsAdjustment();
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});

    levelsCommand.execute(mockProcessor, mockViewer,
        new String[]{"levels", "-20", "30", "310", "inputImage", "outputImage", "split", "5"});
    String errorMessage = errStream.toString().trim();
    System.setErr(System.err);
    assertEquals("Invalid input: Ensure 0 <= b < m < w <= 255.", errorMessage);
  }

  /**
   * Test running a script with a null script file path, expecting a NullPointerException.
   *
   * @throws IOException If there is an error reading the script file.
   */
  @Test(expected = NullPointerException.class)
  public void testRunScriptWithNullScriptFilePath() throws IOException {
    controller.runScript(null);
  }

  /**
   * Test method to verify the execution of the LumaComponentImageCommand with valid coefficients.
   * It loads an image and applies the luma component operation, then compares the expected and
   * actual outputs.
   *
   * @throws IOException if an error occurs during the execution or reading of image files.
   */
  @Test
  public void testExecute_LumaComponentImageCommand_ValidCoefficients() throws IOException {
    LumaComponentImageCommand lumaCommand = new LumaComponentImageCommand();
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});
    lumaCommand.execute(mockProcessor, mockViewer, new String[]{"luma", "koala", "outputImage"});
    String output = outputStream.toString().trim();
    String[] actualOutputs = output.split("\\R");
    String[] expectedOutputs = {"PPM Image Loaded.", "Luma Component of Image Created."};
    assertEquals(actualOutputs, expectedOutputs);
  }

  /**
   * Tests the execution of the RedComponentImageCommand for valid input parameters. Verifies the
   * creation of the red component of an image after executing the command.
   *
   * @throws IOException if an error occurs during the execution or reading of image files.
   */
  @Test
  public void testExecute_RedComponentImageCommand_ValidInput() throws IOException {
    RedComponentImageCommand redCommand = new RedComponentImageCommand();
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});
    redCommand.execute(mockProcessor, mockViewer, new String[]{"red", "koala", "outputImage"});
    String output = outputStream.toString().trim();
    String[] actualOutputs = output.split("\\R");
    String[] expectedOutputs = {"PPM Image Loaded.", "Red Component of Image Created."};
    assertEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Tests the execution of the RgbSplitImageCommand.
   *
   * @throws IOException if an I/O exception occurs during the test.
   */
  @Test
  public void testExecute_RgbSplitImageCommand() throws IOException {
    RgbSplitImageCommand splitCommand = new RgbSplitImageCommand();
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "image"});
    splitCommand.execute(mockProcessor, mockViewer,
        new String[]{"split", "image", "redOutput", "greenOutput", "blueOutput"});
    String output = outputStream.toString().trim();
    String[] actualOutputs = output.split("\\R");
    String[] expectedOutputs
        = {"PPM Image Loaded.", "Image Splitted into its Red, Green, and Blue Components."};
    assertEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Tests the SaveImageCommand functionality.
   *
   * @throws IOException if an I/O exception occurs during the test.
   */
  @Test
  public void testExecute_SaveImageCommand() throws IOException {
    SaveImageCommand saveCommand = new SaveImageCommand();
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "image"});
    saveCommand.execute(mockProcessor, mockViewer, new String[]{"save", imagePath, "image"});
    String output = outputStream.toString().trim();
    String[] actualOutputs = output.split("\\R");
    String[] expectedOutputs = {"PPM Image Loaded.", "Image Saved."};
    assertEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Validates the execution of the 'SepiaImageCommand' with a valid split value.
   *
   * @throws IOException If an error occurs during image processing.
   */
  @Test
  public void testExecute_SepiaImageCommand_WithValidSplit() throws IOException {
    SepiaImageCommand sepiaCommand = new SepiaImageCommand();

    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});

    sepiaCommand.execute(mockProcessor, mockViewer,
        new String[]{"sepia", "koala", "outputImage", "split", "2"});

    String output = outputStream.toString().trim();

    String[] expectedOutputs = {"PPM Image Loaded.", "Image Tone Changed to Sepia."};
    String[] actualOutputs = output.split("\\R");

    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Validates the execution of the 'SepiaImageCommand' with an invalid split value.
   *
   * @throws IOException If an error occurs during image processing.
   */
  @Test
  public void testExecute_SepiaImageCommand_WithInvalidSplit() throws IOException {
    SepiaImageCommand sepiaCommand = new SepiaImageCommand();
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});

    sepiaCommand.execute(mockProcessor, mockViewer,
        new String[]{"sepia", "koala", "outputImage", "split", "-5"});
    String errorMessage = errStream.toString().trim();
    System.setErr(System.err);
    assertEquals("Split value cannot be negative.", errorMessage);
  }

  /**
   * Validates the execution of the 'ValueComponentImageCommand' and asserts the expected output.
   *
   * @throws IOException If an error occurs during image processing.
   */
  @Test
  public void testExecute_ValueComponentImageCommand_ValidCoefficients() throws IOException {
    ValueComponentImageCommand valueComponentCommand = new ValueComponentImageCommand();

    // Load the image
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});

    // Execute the ValueComponentImageCommand
    valueComponentCommand.execute(mockProcessor, mockViewer,
        new String[]{"value", "koala", "outputImage"});

    // Validate the output
    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Value Component of Image Created."};
    String[] actualOutputs = output.split("\\R");
    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Validates the execution of the 'SharpenImageCommand' with a valid split value and asserts the
   * expected output.
   *
   * @throws IOException If an error occurs during image processing.
   */
  @Test
  public void testExecute_SharpenImageCommand_WithValidSplit() throws IOException {
    SharpenImageCommand sharpenCommand = new SharpenImageCommand();

    // Load the image
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});

    // Execute the SharpenImageCommand with a valid split value
    sharpenCommand.execute(mockProcessor, mockViewer,
        new String[]{"sharpen", "koala", "outputImage", "split", "2"});

    // Validate the output
    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Image sharpened."};
    String[] actualOutputs = output.split("\\R");
    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Validates the execution of the 'SharpenImageCommand' with an invalid split value and asserts
   * the error message.
   *
   * @throws IOException If an error occurs during image processing.
   */
  @Test
  public void testExecute_SharpenImageCommand_WithInvalidSplit() throws IOException {
    SharpenImageCommand sharpenCommand = new SharpenImageCommand();
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "koala"});

    // Execute the SharpenImageCommand with an invalid split value
    sharpenCommand.execute(mockProcessor, mockViewer,
        new String[]{"sharpen", "koala", "outputImage", "split", "-5"});

    // Validate the error message
    String errorMessage = errStream.toString().trim();
    System.setErr(System.err);
    assertEquals("Split value cannot be negative.", errorMessage);
  }

  /**
   * Validates the execution of the 'VerticalFlipImageCommand' and verifies the expected output.
   *
   * @throws IOException If an error occurs during image processing.
   */
  @Test
  public void testExecute_VerticalFlipImageCommand() throws IOException {
    VerticalFlipImageCommand verticalFlipCommand = new VerticalFlipImageCommand();

    LoadImageCommand loadCommand = new LoadImageCommand();
    loadCommand.execute(mockProcessor, mockViewer, new String[]{"load", imagePath, "image"});

    // Execute the VerticalFlipImageCommand
    verticalFlipCommand.execute(mockProcessor, mockViewer,
        new String[]{"vertical-flip", "image", "flippedImage"});

    // Validate the output
    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"PPM Image Loaded.", "Image Flipped Vertically."};
    String[] actualOutputs = output.split("\\R");
    assertArrayEquals(expectedOutputs, actualOutputs);
  }


  /**
   * Validates the execution of the 'ScriptExecutionCommand' with a valid script file path. It tests
   * if the script file gets loaded correctly and displays the expected output message.
   *
   * @throws IOException If an error occurs during the script execution or output validation.
   */
  @Test
  public void testExecute_ScriptExecutionCommand_WithValidScript() throws IOException {
    ScriptExecutionCommand scriptCommand = new ScriptExecutionCommand();
    String scriptFilePath = "res/smalltest.txt";
    scriptCommand.execute(mockProcessor, mockViewer, new String[]{"script", scriptFilePath});
    String output = outputStream.toString().trim();
    String[] expectedOutputs = {"load res/testimage.png testimage", "IO Image Loaded."};
    String[] actualOutputs = output.split("\\R");
    assertArrayEquals(expectedOutputs, actualOutputs);
  }

  /**
   * Validates the execution of the 'ScriptExecutionCommand' with no script file provided. It tests
   * if an error message is displayed when no script file is provided.
   *
   * @throws IOException If an error occurs during the script execution or error message
   *                     validation.
   */
  @Test
  public void testExecute_ScriptExecutionCommand_WithNoScriptFile() throws IOException {
    ScriptExecutionCommand scriptCommand = new ScriptExecutionCommand();

    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));

    String errorMessage = errStream.toString().trim();
    System.setErr(System.err);
    scriptCommand.execute(mockProcessor, mockViewer, new String[]{"script", "invalid.txt"});
    assertNotNull(errorMessage);
  }

  /**
   * Tests the loading of a PPM image through the ActionListener.
   */
  @Test
  public void testLoadPPMImageActionListener() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    JButton loadButton = new JButton("Load");
    controller.openGUIMode();
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }


  /**
   * Tests the loading of a IO image through the ActionListener.
   */
  @Test
  public void testLoadIOImageActionListener() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    JButton loadButton = new JButton("Load");
    controller.openGUIMode();
    loadButton.addActionListener(controller.getLoadActionListener());
    String imagePath = "res/KoalaTest.png";
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nIO Image Loaded.\nHistogram is Generated.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }

  /**
   * Tests the execution of a valid 'Save' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_ValidSaveInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    JButton saveButton = new JButton("Save");
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));
    saveButton.addActionListener(controller.getSaveActionListener());
    saveButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(saveButton, ActionEvent.ACTION_PERFORMED, "Save "
            + imagePath + " load"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nImage Saved.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }

  /**
   * Tests the execution of a valid 'Compress' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_ValidCompressInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton compressButton = new JButton("Compress");
    compressButton.addActionListener(controller.getCompressActionListener());
    compressButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(compressButton, ActionEvent.ACTION_PERFORMED,
            "compress " + "90" + " " + "load" + " compress"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nSuccessfully Compressed the Image.\nHistogram is Generated.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }

  /**
   * Tests the execution of a valid 'Blue Component' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_BlueCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton blueComponentButton = new JButton("Blue Component");
    blueComponentButton.addActionListener(controller.getBlueActionListener());
    blueComponentButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(blueComponentButton, ActionEvent.ACTION_PERFORMED,
            "blue-component " + "load" + " blue-component"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nBlue Component of Image Created.\nHistogram is Generated.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }

  /**
   * Tests the execution of a valid 'Green Component' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_GreenCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton greenComponentButton = new JButton("Green Component");
    greenComponentButton.addActionListener(controller.getGreenActionListener());
    greenComponentButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(greenComponentButton, ActionEvent.ACTION_PERFORMED,
            "green-component " + "load" + " green-component"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nGreen Component of Image Created.\nHistogram is Generated.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }

  /**
   * Tests the execution of a valid 'Red Component' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_RedCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton redComponentButton = new JButton("Red Component");
    redComponentButton.addActionListener(controller.getRedActionListener());
    redComponentButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(redComponentButton, ActionEvent.ACTION_PERFORMED,
            "red-component " + "load" + " red-component"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nRed Component of Image Created.\nHistogram is Generated.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }

  /**
   * Tests the execution of a valid 'Blur' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_BlurCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton blurComponentButton = new JButton("Blur");
    blurComponentButton.addActionListener(controller.getBlurActionListener());
    blurComponentButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(blurComponentButton, ActionEvent.ACTION_PERFORMED,
            "blur" + "|" + "load" + "|" + "split" + "|" + "0"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nImage blurred.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }

  /**
   * Tests the execution of a valid 'Blur' command in Split Mode in the GUI mode.
   */
  @Test
  public void testExecuteCommand_BlurSplitCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton blurComponentButton = new JButton("Blur");
    blurComponentButton.addActionListener(controller.getBlurActionListener());
    blurComponentButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(blurComponentButton, ActionEvent.ACTION_PERFORMED,
            "blur" + "|" + "load" + "|" + "split" + "|" + "50"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nImage blurred.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }


  /**
   * Tests the execution of a valid 'Brighten' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_BrightenCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton brightButton = new JButton("Brighten");
    brightButton.addActionListener(controller.getBrightenActionListener());
    brightButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(brightButton, ActionEvent.ACTION_PERFORMED,
            "brighten " + "90" + " " + "load" + " brighten"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nImage Brightened by 90\nHistogram is Generated.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }


  /**
   * Tests the execution of a valid 'Color Correct' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_colorCorrectCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton colorCorrectButton = new JButton("Color Correct");
    colorCorrectButton.addActionListener(controller.getColorCorrectActionListener());
    colorCorrectButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(colorCorrectButton, ActionEvent.ACTION_PERFORMED,
            "color-correct" + "|" + "load" + "|" + "color-correct"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nImage is color corrected.\nHistogram is Generated.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }

  /**
   * Tests the execution of a valid 'Color Correct' with Split command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_colorCorrectSplitCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton colorCorrectButton = new JButton("Color Correct");
    colorCorrectButton.addActionListener(controller.getColorCorrectActionListener());
    colorCorrectButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(colorCorrectButton, ActionEvent.ACTION_PERFORMED,
            "color-correct" + "|" + "load" + "|" + "color-correct" + "|" + "90"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nImage is color corrected with split.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }


  /**
   * Tests the execution of a valid 'Greyscale' with Split command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_GreySplitCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton greyScaleCorrectButton = new JButton("GreyScale");
    greyScaleCorrectButton.addActionListener(controller.getGreyActionListener());
    greyScaleCorrectButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(greyScaleCorrectButton, ActionEvent.ACTION_PERFORMED,
            "greyscale" + "|" + "load" + "|" + "split" + "|" + "90"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nImage Tone Changed to GreyScale.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }


  /**
   * Tests the execution of a valid 'Greyscale' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_GreyCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton greyScaleCorrectButton = new JButton("GreyScale");
    greyScaleCorrectButton.addActionListener(controller.getGreyActionListener());
    greyScaleCorrectButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(greyScaleCorrectButton, ActionEvent.ACTION_PERFORMED,
            "greyscale" + "|" + "load" + "|" + "grey" + "|"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nImage Tone Changed to GreyScale.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }

  /**
   * Tests the execution of a valid 'Sepia' with Split command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_SepiaSplitCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton sepiaScaleCorrectButton = new JButton("Sepia");
    sepiaScaleCorrectButton.addActionListener(controller.getSepiaActionListener());
    sepiaScaleCorrectButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(sepiaScaleCorrectButton, ActionEvent.ACTION_PERFORMED,
            "sepia" + "|" + "load" + "|" + "split" + "|" + "90"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nImage Tone Changed to Sepia.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }


  /**
   * Tests the execution of a valid 'Sepia' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_SepiaCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath
            + " load"));

    JButton sepiaScaleCorrectButton = new JButton("Sepia");
    sepiaScaleCorrectButton.addActionListener(controller.getSepiaActionListener());
    sepiaScaleCorrectButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(sepiaScaleCorrectButton, ActionEvent.ACTION_PERFORMED,
            "sepia" + "|" + "load" + "|" + "split" + "|" + "0"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nImage Tone Changed to Sepia.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }


  /**
   * Tests the execution of a valid 'Luma' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_LumaCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath
            + " load"));

    JButton lumaScaleCorrectButton = new JButton("Luma");
    lumaScaleCorrectButton.addActionListener(controller.getLumaActionListener());
    lumaScaleCorrectButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(lumaScaleCorrectButton, ActionEvent.ACTION_PERFORMED,
            "luma-component " + "load " + "luma-component"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nLuma Component of Image Created."
        + "\nHistogram is Generated.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }


  /**
   * Tests the execution of a valid 'Horizontal Flip' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_HflipCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton hflipButton = new JButton("Horizontal Flip");
    hflipButton.addActionListener(controller.getHorizontalFlipActionListener());
    hflipButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(hflipButton, ActionEvent.ACTION_PERFORMED,
            "horizontal-flip " + "load " + "hflip"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nImage Flipped Horizontally.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }

  /**
   * Tests the execution of a valid 'Vertical Flip' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_VflipCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton vflipButton = new JButton("Vertical Flip");
    vflipButton.addActionListener(controller.getVerticalFlipActionListener());
    vflipButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(vflipButton, ActionEvent.ACTION_PERFORMED,
            "vertical-flip " + "load " + "vflip"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nImage Flipped Vertically.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }


  /**
   * Tests the execution of a valid 'Sharpen' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_SharpenCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load " + imagePath + " load"));

    JButton sharpenButton = new JButton("Sharpen");
    sharpenButton.addActionListener(controller.getVerticalFlipActionListener());
    sharpenButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(sharpenButton, ActionEvent.ACTION_PERFORMED,
            "sharpen " + "load " + "sharpen"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nImage sharpened.\nHistogram is Generated.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }

  /**
   * Tests the execution of a valid 'Sharpen' command with split in the GUI mode.
   */
  @Test
  public void testExecuteCommand_SharpenSplitCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton sharpenButton = new JButton("Sharpen");
    sharpenButton.addActionListener(controller.getVerticalFlipActionListener());
    sharpenButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(sharpenButton, ActionEvent.ACTION_PERFORMED,
            "sharpen " + "load " + "split " + "90"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nImage sharpened.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }


  /**
   * Tests the execution of a valid 'Adjust Color Levels' command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_adjustLevelsCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton levelAdjustButton = new JButton("Level Adjust");
    levelAdjustButton.addActionListener(controller.getAdjustLevelActionListener());
    levelAdjustButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(levelAdjustButton, ActionEvent.ACTION_PERFORMED,
            "levels-adjust" + " " + "20" + " " + "100" + " " + "255" + " "
                + "load" + " " + "levels-adjust"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nLevel Adjustment is completed on image.\nHistogram is Generated.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }

  /**
   * Tests the execution of a valid 'Adjust Color Levels' command with split in the GUI mode.
   */
  @Test
  public void testExecuteCommand_adjustLevelsWithSplitCommandInGui() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream oldStream = System.out;
    System.setOut(printStream);
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    controller.openGUIMode();
    JButton loadButton = new JButton("Load");
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath + " load"));

    JButton levelAdjustButton = new JButton("Level Adjust");
    levelAdjustButton.addActionListener(controller.getAdjustLevelActionListener());
    levelAdjustButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(levelAdjustButton, ActionEvent.ACTION_PERFORMED,
            "levels-adjust" + " " + "20" + " " + "100" + " " + "255" + " "
                + "load" + " " + "levels-adjust" + " 90"));
    System.out.flush();
    System.setOut(oldStream);
    String output = outputStream.toString().trim();
    String expectedOutput = "GUI Mode Opened.\nPPM Image Loaded.\nHistogram is Generated."
        + "\nLevel Adjustment is completed on image.";
    String[] expectedLines = expectedOutput.split("\\R");
    String[] actualLines = output.split("\\R");
    assertEquals(expectedLines, actualLines);
  }

  /**
   * This test verifies the behavior when attempting to load an image with an invalid image path.
   */
  @Test
  public void testLoadInvalidImagePathInGui() {
    PrintStream originalErr = System.err;
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    JButton loadButton = new JButton("Load");
    controller.openGUIMode();
    String imagePath = "InvalidImage.jpg";
    loadButton.addActionListener(controller.getLoadActionListener());
    loadButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(loadButton, ActionEvent.ACTION_PERFORMED, "load "
            + imagePath
            + " load"));
    System.setErr(originalErr);
    String errorMessage = errStream.toString().trim();
    String expectedErrorMessage = "Invalid Image Path.";
    assertEquals(expectedErrorMessage, errorMessage);
  }

  /**
   * Tests the handling of an invalid save command in the GUI mode.
   */
  @Test
  public void testExecuteCommand_InvalidSaveInGui() {
    PrintStream originalErr = System.err;
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    JButton saveButton = new JButton("Save");
    controller.openGUIMode();
    String imagePath = "InvalidImage.xyz";
    saveButton.addActionListener(controller.getSaveActionListener());
    saveButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(saveButton, ActionEvent.ACTION_PERFORMED, "Save "
            + imagePath
            + " load"));
    System.setErr(originalErr);
    String errorMessage = errStream.toString().trim();
    String expectedErrorMessage = "Unsupported image format. "
        + "Can only save PPM, PNG, JPG or JPEG images.";
    assertEquals(expectedErrorMessage, errorMessage);
  }


  /**
   * Tests the handling of an invalid save command without load in the GUI mode.
   */
  @Test
  public void testExecuteCommand_InvalidSaveInWithoutLoadGui() {
    PrintStream originalErr = System.err;
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    ImageViewer viewer = new ImageViewer(System.out);
    ImageController controller = new ImageController(viewer);
    JButton saveButton = new JButton("Save");
    controller.openGUIMode();
    String imagePath = "InvalidImage.jpeg";
    saveButton.addActionListener(controller.getSaveActionListener());
    saveButton.getActionListeners()[0].actionPerformed(
        new ActionEvent(saveButton, ActionEvent.ACTION_PERFORMED, "Save "
            + imagePath
            + " null"));
    System.setErr(originalErr);
    String errorMessage = errStream.toString().trim();
    String expectedErrorMessage = "Please load an image before performing Save.";
    assertEquals(expectedErrorMessage, errorMessage);
  }
}