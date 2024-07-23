package controller;

import controller.commands.BlueComponentImageCommand;
import controller.commands.BlurImageCommand;
import controller.commands.BrightenImageCommand;
import controller.commands.ColorCorrectImageCommand;
import controller.commands.CompressImageCommand;
import controller.commands.GreenComponentImageCommand;
import controller.commands.GreyscaleImageCommand;
import controller.commands.HistogramImageCommand;
import controller.commands.HorizontalFlipImageCommand;
import controller.commands.ImageCommand;
import controller.commands.IntensityComponentImageCommand;
import controller.commands.LevelsAdjustment;
import controller.commands.LoadImageCommand;
import controller.commands.LumaComponentImageCommand;
import controller.commands.RedComponentImageCommand;
import controller.commands.RgbCombineImageCommand;
import controller.commands.RgbSplitImageCommand;
import controller.commands.SaveImageCommand;
import controller.commands.ScriptExecutionCommand;
import controller.commands.SepiaImageCommand;
import controller.commands.SharpenImageCommand;
import controller.commands.ValueComponentImageCommand;
import controller.commands.VerticalFlipImageCommand;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.Image;
import model.ImageOperations;
import model.ImageProcessor;
import view.GUIViewer;
import view.ImageViewer;

/**
 * The ImageController class controls, coordinates image processing operations based on user input.
 * It handles the loading and processing of images, executing commands, and running scripts.
 */
public class ImageController implements ImageControllerInterface {

  private final Scanner scanner;
  private final ImageProcessor processor;
  private final Map<String, ImageCommand> commandMap;
  private final ImageViewer viewer;
  private GUIViewer guiViewer;

  /**
   * Creates an ImageController instance. A map that associates command names with corresponding
   * ImageCommand implementations.
   *
   * @param viewer The ImageViewer to display messages and images.
   */
  public ImageController(ImageViewer viewer) {
    this.viewer = viewer;
    this.scanner = new Scanner(System.in);
    this.processor = new ImageOperations();

    commandMap = new HashMap<>();
    commandMap.put("load", new LoadImageCommand());
    commandMap.put("save", new SaveImageCommand());
    commandMap.put("red-component", new RedComponentImageCommand());
    commandMap.put("green-component", new GreenComponentImageCommand());
    commandMap.put("blue-component", new BlueComponentImageCommand());
    commandMap.put("value-component", new ValueComponentImageCommand());
    commandMap.put("luma-component", new LumaComponentImageCommand());
    commandMap.put("intensity-component", new IntensityComponentImageCommand());
    commandMap.put("horizontal-flip", new HorizontalFlipImageCommand());
    commandMap.put("vertical-flip", new VerticalFlipImageCommand());
    commandMap.put("brighten", new BrightenImageCommand());
    commandMap.put("rgb-split", new RgbSplitImageCommand());
    commandMap.put("rgb-combine", new RgbCombineImageCommand());
    commandMap.put("blur", new BlurImageCommand());
    commandMap.put("sharpen", new SharpenImageCommand());
    commandMap.put("sepia", new SepiaImageCommand());
    commandMap.put("greyscale", new GreyscaleImageCommand());
    commandMap.put("histogram", new HistogramImageCommand());
    commandMap.put("color-correct", new ColorCorrectImageCommand());
    commandMap.put("levels-adjust", new LevelsAdjustment());
    commandMap.put("compress", new CompressImageCommand());
    commandMap.put("-file", new ScriptExecutionCommand());
  }

  /**
   * Runs a script file containing a series of image processing commands.
   *
   * @param scriptFilePath The path to the script file.
   * @throws IOException If there is an error reading the script file.
   */
  public void runScript(String scriptFilePath) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(scriptFilePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("#")) {
          continue;
        }
        viewer.showString(line);
        executeCommand(line);
      }
    } catch (IOException e) {
      viewer.showErrorMessage("Error reading the script file: " + e.getMessage());
    }
  }

  /**
   * Converts the given source image to a BufferedImage and displays it in the GUI viewer.
   *
   * @param sourceImage The source image to be converted.
   * @param destImage   The destination image type or identifier.
   */
  private void imageToBufferedImage(Image sourceImage, String destImage) {
    try {
      if (sourceImage != null) {
        int height = sourceImage.getHeight();
        int width = sourceImage.getWidth();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            int[] pixelArray = sourceImage.getImage(i, j);
            if (pixelArray != null) {
              raster.setPixel(j, i, pixelArray);
            }
          }
        }
        guiViewer.displayImage(image, destImage);
      }
    } catch (NullPointerException e) {
      return;
    }
  }

  /**
   * Converts the given source image to a BufferedImage, generates its histogram, and displays the
   * histogram in the GUI viewer.
   *
   * @param sourceImage The source image to be converted and displayed as a histogram.
   */
  private void imageToBufferedImageHistogram(Image sourceImage) {
    try {
      if (sourceImage != null) {
        int height = sourceImage.getHeight();
        int width = sourceImage.getWidth();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            int[] pixelArray = sourceImage.getImage(i, j);
            if (pixelArray != null) {
              raster.setPixel(j, i, pixelArray);
            }
          }
        }
        guiViewer.displayHistogram(image);
      }
    } catch (NullPointerException e) {
      return;
    }
  }

  /**
   * Returns an ActionListener for the "Load" action, handling the loading of an image and
   * displaying it in the GUI viewer.
   *
   * @return ActionListener for the "Load" action.
   */
  public ActionListener getLoadActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String[] parts = command.split(" ");
        try {
          executeCommand(command);
          Image sourceImage = processor.getImage(parts[parts.length - 1]);
          imageToBufferedImage(sourceImage, parts[0]);
        } catch (IOException ex) {
          viewer.showErrorMessage("Invalid Image Path.");
        }
      }
    };
  }

  /**
   * Returns an ActionListener for the "Save" action, handling the saving of the current image.
   *
   * @return ActionListener for the "Save" action.
   */
  public ActionListener getSaveActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String[] parts = command.split(" ");
        if (parts[parts.length - 1].equals("null") || parts[parts.length - 1].isEmpty()) {
          viewer.showErrorMessage("Please load an image before performing " + parts[0] + ".");
        } else {
          try {
            executeCommand(command);
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
    };
  }

  /**
   * Returns an ActionListener for the "Histogram" action, handling the generation and display of
   * the histogram for the current image.
   *
   * @return ActionListener for the "Histogram" action.
   */
  public ActionListener getHistogramActionLister() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String[] parts = command.split(" ");
        try {
          executeCommand(command);
          Image sourceImage = processor.getImage(parts[2]);
          imageToBufferedImageHistogram(sourceImage);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    };
  }

  /**
   * Returns an ActionListener for the "Brighten" action, handling the brightening of the image.
   *
   * @return ActionListener for the "Brighten" action.
   */
  public ActionListener getBrightenActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String[] parts = command.split(" ");
        if (parts[2].equals("null") || parts[2].isEmpty()) {
          viewer.showErrorMessage("Please load an image before performing " + parts[0] + ".");
        } else {
          try {
            executeCommand(command);
            Image sourceImage = processor.getImage(parts[3]);
            imageToBufferedImage(sourceImage, parts[0]);
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
    };
  }

  /**
   * Returns an ActionListener for the "Compress" action, handling the compression of the image.
   *
   * @return ActionListener for the "Compress" action.
   */
  public ActionListener getCompressActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String[] parts = command.split(" ");
        if (parts[2].equals("null") || parts[2].isEmpty()) {
          viewer.showErrorMessage("Please load an image before performing " + parts[0] + ".");
        } else {
          try {
            executeCommand(command);
            Image sourceImage = processor.getImage(parts[3]);
            imageToBufferedImage(sourceImage, parts[0]);
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
    };
  }

  /**
   * Returns an ActionListener for the "Luma" action, handling the Luma adjustment of the image.
   *
   * @return ActionListener for the "Luma" action.
   */
  public ActionListener getLumaActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
          getImageComponentAction(command);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    };
  }

  /**
   * Returns an ActionListener for the "Horizontal Flip" action on the current image.
   *
   * @return ActionListener for the "Horizontal Flip" action.
   */
  public ActionListener getHorizontalFlipActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
          getImageComponentAction(command);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    };
  }

  /**
   * Returns an ActionListener for the "Vertical Flip" action on the current image.
   *
   * @return ActionListener for the "Vertical Flip" action.
   */
  public ActionListener getVerticalFlipActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
          getImageComponentAction(command);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    };
  }

  /**
   * Returns an ActionListener for the "Red" action, handling the adjustment of the red component.
   *
   * @return ActionListener for the "Red" action.
   */
  public ActionListener getRedActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
          getImageComponentAction(command);
        } catch (IOException ex) {
          ex.printStackTrace();
        }

      }
    };
  }

  /**
   * Returns an ActionListener for the "Green" action, adjusting the green component of the image.
   *
   * @return ActionListener for the "Green" action.
   */
  public ActionListener getGreenActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
          getImageComponentAction(command);
        } catch (IOException ex) {
          ex.printStackTrace();
        }

      }
    };
  }

  /**
   * Returns an ActionListener for the "Blue" action, adjusting the blue component of the image.
   *
   * @return ActionListener for the "Blue" action.
   */
  public ActionListener getBlueActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
          getImageComponentAction(command);
        } catch (IOException ex) {
          ex.printStackTrace();
        }

      }
    };
  }

  /**
   * Handles image component adjustment actions (Red, Green, Blue) and Luma. It also handles command
   * execution and image display.
   *
   * @param command The action command received.
   * @throws IOException If an I/O error occurs.
   */
  private void getImageComponentAction(String command) throws IOException {
    String[] parts = command.split(" ");
    if (parts[1].equals("null") || parts[1].isEmpty()) {
      viewer.showErrorMessage("Please load an image before performing " + parts[0] + ".");
    } else {
      executeCommand(command);
      Image sourceImage = processor.getImage(parts[2]);
      imageToBufferedImage(sourceImage, parts[0]);
    }
  }

  /**
   * Returns an ActionListener for the "Adjust Level" action, handles the adjustment of image
   * level.
   *
   * @return ActionListener for the "Adjust Level" action.
   */
  public ActionListener getAdjustLevelActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String[] parts = command.split(" ");
        if (parts[4].equals("null") || parts[4].isEmpty()) {
          viewer.showErrorMessage("Please load an image before performing " + parts[0] + ".");
        } else {
          try {
            if (parts.length == 7 && Integer.parseInt(parts[6]) > 0) {
              executeCommand(
                  parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3] + " "
                      + parts[4] + " " + parts[5] + " split " + parts[6]);
              Image sourceImage = processor.getImage(parts[5]);
              imageToBufferedImage(sourceImage, "split");
            } else if (parts.length == 6) {
              executeCommand(
                  parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3]
                      + " " + parts[4] + " " + parts[5]);
              Image sourceImage = processor.getImage(parts[5]);
              imageToBufferedImage(sourceImage, parts[0]);
            }
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
    };
  }

  /**
   * Returns an ActionListener for the "Sharpen" action, handling the sharpening of the image.
   *
   * @return ActionListener for the "Sharpen" action.
   */
  public ActionListener getSharpenActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
          getImageSplitAction(command);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    };
  }

  /**
   * Returns an ActionListener for the "Blur" action, handling the blurring of the current image.
   *
   * @return ActionListener for the "Blur" action.
   */
  public ActionListener getBlurActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
          getImageSplitAction(command);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    };
  }

  /**
   * Returns an ActionListener for the "Color Correct" action, handles color correction of an
   * image.
   *
   * @return ActionListener for the "Color Correct" action.
   */
  public ActionListener getColorCorrectActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
          getImageSplitAction(command);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    };
  }

  /**
   * Returns an ActionListener for the "Grey" action, handling the conversion of image to
   * grayscale.
   *
   * @return ActionListener for the "Grey" action.
   */
  public ActionListener getGreyActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
          getImageSplitAction(command);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    };
  }

  /**
   * Returns an ActionListener for the "Sepia" action, handling the application of sepia tone.
   *
   * @return ActionListener for the "Sepia" action.
   */
  public ActionListener getSepiaActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
          getImageSplitAction(command);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    };
  }

  /**
   * Handles image split actions, including command execution and image display.
   *
   * @param command The action command received.
   * @throws IOException If an I/O error occurs.
   */
  private void getImageSplitAction(String command) throws IOException {
    String destType = null;
    String[] parts = command.split("\\|");
    String guiCommand = null;
    if (parts[1].equals("null") || parts[1].isEmpty()) {
      viewer.showErrorMessage("Please load an image before performing " + parts[0] + ".");
    } else {
      if (parts.length >= 3) {
        guiCommand =
            parts.length == 4 && Integer.parseInt(parts[3]) > 0 ? parts[0] + " " + parts[1] + " "
                + parts[2] + " split " + parts[3] : parts[0] + " " + parts[1] + " " + parts[2];
      }
      executeCommand(guiCommand);
      if (parts.length == 4 && Integer.parseInt(parts[3]) > 0) {
        destType = "split";
      } else if (parts.length == 3) {
        destType = parts[0];
      }
      Image sourceImage = processor.getImage(parts[2]);
      imageToBufferedImage(sourceImage, destType);
    }
  }

  /**
   * Executes an image processing command based on the provided input command string.
   *
   * @param cmd The command to execute. The command string should follow a specific format, and the
   *            method processes the command and performs the corresponding image operation.
   * @throws IOException IOException If there is an error during image processing or if the provided
   *                     command is invalid or not recognized.
   */
  public void executeCommand(String cmd) throws IOException {
    String[] command = cmd.split(" ");
    String commandName = command[0].toLowerCase();
    if (commandName.equals("-file")) {
      ImageCommand fileCommand = commandMap.get(commandName);
      if (fileCommand != null) {
        fileCommand.execute(processor, viewer, command);
      } else {
        viewer.showErrorMessage("Unknown command: " + commandName);
      }
    } else {
      ImageCommand imageCommand = commandMap.get(commandName);
      if (imageCommand != null) {
        imageCommand.execute(processor, viewer, command);
      } else {
        viewer.showErrorMessage("Unknown command: " + commandName);
      }
    }
  }

  /**
   * Opens the graphical user interface (GUI) mode by initializing a new {@code GUIViewer}. Also
   * sets up various action listeners for GUI components. The method associates the GUIViewer with
   * the main viewer, enabling interaction with the GUI.
   */

  public void openGUIMode() {
    viewer.showString("GUI Mode Opened.");
    this.guiViewer = new GUIViewer(getLoadActionListener(), getHistogramActionLister(),
        getRedActionListener(), getGreenActionListener(), getBlueActionListener(),
        getLumaActionListener(), getHorizontalFlipActionListener(), getVerticalFlipActionListener(),
        getCompressActionListener(), getBlurActionListener(), getSharpenActionListener(),
        getBrightenActionListener(), getColorCorrectActionListener(),
        getAdjustLevelActionListener(), getGreyActionListener(), getSepiaActionListener(),
        getSaveActionListener());
    viewer.setGUIViewer(guiViewer);

  }

  /**
   * Opens and executes a script file specified by the given file name. This method displays the
   * script file entry in the viewer and runs the script using the {@code runScript} method.
   *
   * @param scriptFileName The name of the script file to be opened and executed.
   * @throws IOException If an I/O error occurs while reading the script file.
   */
  public void openAndExecuteFile(String scriptFileName) throws IOException {
    viewer.showString("File Opened and Executed.");
    runScript(scriptFileName);
  }

  /**
   * Opens the text mode, allowing the user to enter commands interactively. The method continuously
   * prompts the user for commands until the "exit" command is entered. Each entered command is
   * executed using the {@code executeCommand} method.
   *
   * @throws IOException If an I/O error occurs during command entry or execution.
   */
  public void openTextMode() throws IOException {
    viewer.showString("Text Mode Opened.");
    while (true) {
      viewer.showCommandEntry();
      String command = scanner.nextLine();
      if (command.equals("exit")) {
        viewer.showString("Exiting the program.");
        break;
      } else {
        executeCommand(command);
      }
    }
  }
}
