package controller.commands;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import javax.imageio.ImageIO;
import model.Image;
import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code LoadImageCommand} class represents a command to load an image from a file path. It
 * then adds it to the image processor using an {@link ImageProcessor}. It implements the
 * {@link ImageCommand} interface.
 */
public class LoadImageCommand implements ImageCommand {

  /**
   * Executes the command to load an image from the specified file path. Adds it to the image
   * processor with the given image name.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects three arguments: args[1] - the
   *                  file path to the image args[2] - the name to assign to the loaded image
   * @throws IOException If an I/O error occurs during file reading or image processing.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
      throws IOException {
    if (args.length < 3) {
      viewer.showErrorMessage("The 'load' command is invalid."
          + " Please use valid file formats such as ppm, png, jpg, or jpeg.");
      return;
    }

    String imagePath = String.join(" ",
        Arrays.copyOfRange(args, 1, args.length - 1));
    String imageName = args[args.length - 1];

    if (imagePath.endsWith(".ppm")) {
      ppmImageLoader(processor, viewer, imagePath, imageName);
      viewer.showString("PPM Image Loaded.");
    } else {
      ioImageLoader(processor, viewer, imagePath, imageName);
      viewer.showString("IO Image Loaded.");
    }
  }

  /**
   * Loads a PPM image from the specified file path. Adds it to the image processor with the given
   * image name.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param filePath  The file path to the PPM image file.
   * @param imageName The name to assign to the loaded PPM image.
   * @throws IOException If an error occurs during file reading or image processing.
   */
  public void ppmImageLoader(ImageProcessor processor, ImageViewer viewer, String filePath,
      String imageName) throws IOException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      viewer.showErrorMessage("File " + filePath + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    sc = new Scanner(builder.toString());
    String token = sc.next();
    if (!token.equals("P3")) {
      viewer.showErrorMessage("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();

    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    Image pixel = new Image(height, width);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int redComponentOfImage = sc.nextInt();
        int greenComponentOfImage = sc.nextInt();
        int blueComponentOfImage = sc.nextInt();
        pixel.setImage(i, j, redComponentOfImage, greenComponentOfImage, blueComponentOfImage);
      }
    }
    processor.addImage(imageName, pixel);

  }

  /**
   * Loads an IO image from the specified file path. Adds it to the image processor with the given
   * image name.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param filePath  The file path to the image file.
   * @param imageName The name to assign to the loaded image.
   * @throws IOException If an error occurs during file reading or image processing.
   */
  public void ioImageLoader(ImageProcessor processor, ImageViewer viewer, String filePath,
      String imageName) throws IOException {
    File file = new File(filePath);
    BufferedImage img = ImageIO.read(file);
    Image pixel = new Image(img.getHeight(), img.getWidth());
    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        int iopixel = img.getRGB(x, y);
        Color color = new Color(iopixel, true);
        int redComponentOfImage = color.getRed();
        int greenComponentOfImage = color.getGreen();
        int blueComponentOfImage = color.getBlue();
        pixel.setImage(y, x, redComponentOfImage, greenComponentOfImage, blueComponentOfImage);
      }
    }
    processor.addImage(imageName, pixel);

  }
}
