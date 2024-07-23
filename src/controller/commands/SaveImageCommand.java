package controller.commands;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import model.Image;
import model.ImageProcessor;
import view.ImageViewer;

/**
 * The {@code SaveImageCommand} class represents a command to save an image to an output path. It
 * implements the {@link ImageCommand} interface.
 */
public class SaveImageCommand implements ImageCommand {

  /**
   * Executes the command to save an image to a specified output path.
   *
   * @param processor The {@link ImageProcessor} to perform image processing operations.
   * @param viewer    The {@link ImageViewer} to display information or images.
   * @param args      The arguments passed to the command. Expects three arguments: args[1] - the
   *                  output path where the image should be saved args[2] - the name of the image to
   *                  save
   * @throws IOException If an I/O error occurs during image processing or saving.
   */
  @Override
  public void execute(ImageProcessor processor, ImageViewer viewer, String[] args)
      throws IOException {
    if (args.length < 3) {
      viewer.showErrorMessage("Invalid 'save' command. "
          + "Please use valid file formats such as ppm, png, jpg, or jpeg.");
      return;
    }

    String outputPath = String.join(" ",
        Arrays.copyOfRange(args, 1, args.length - 1));
    String imageName = args[args.length - 1];

    save(processor, viewer, outputPath, imageName);
    viewer.showString("Image Saved.");
  }

  /**
   * Saves the source image as a new image with the specified destination file name.
   *
   * @param destImageName   The name of the destination image file.
   * @param sourceImageName The name of the source image.
   */
  private void save(ImageProcessor processor, ImageViewer viewer,
      String destImageName, String sourceImageName) {
    Image sourceImage = processor.getImageImage(sourceImageName);
    if (destImageName.toLowerCase().endsWith(".ppm")) {
      savePPMImage(viewer, sourceImage, destImageName);
    } else if (destImageName.toLowerCase().endsWith(".png")
        || destImageName.toLowerCase().endsWith(".jpg")
        || destImageName.toLowerCase().endsWith(".jpeg")) {
      savePNGorJPEGImage(viewer, sourceImage, destImageName);
    } else {
      viewer.showErrorMessage("Unsupported image format. "
          + "Can only save PPM, PNG, JPG or JPEG images.");
    }
  }

  /**
   * Saves an image in the PPM format to the specified file.
   *
   * @param pixel    The image to be saved in PPM format.
   * @param filePath The path to the file where the image will be saved.
   */
  private void savePPMImage(ImageViewer viewer, Image pixel, String filePath) {
    int width = pixel.getWidth();
    int height = pixel.getHeight();
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
      writer.write("P3\n");
      writer.write(width + " " + height + "\n");
      int maxValue = 255;
      writer.write(maxValue + "\n");

      pixel.getImage(width, height);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int[] pixelArray = pixel.getImage(i, j);
          if (pixelArray != null) {
            writer.write(pixelArray[0] + " " + pixelArray[1] + " " + pixelArray[2] + " ");
          }
        }
        writer.write("\n");
      }
      writer.close();
    } catch (IOException e) {
      viewer.showErrorMessage("Error while saving the image: " + e.getMessage());
    }
  }

  /**
   * Saves an image in PNG or JPEG format to the specified file.
   *
   * @param pixel    The image to be saved in PNG or JPEG format.
   * @param filePath The path to the file where the image will be saved.
   */
  private void savePNGorJPEGImage(ImageViewer viewer, Image pixel, String filePath) {
    int height = pixel.getHeight();
    int width = pixel.getWidth();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    WritableRaster raster = image.getRaster();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] pixelArray = pixel.getImage(i, j);
        if (pixelArray != null) {
          raster.setPixel(j, i, pixelArray);
        }
      }
    }
    try {
      File outputFile = new File(filePath);
      ImageIO.write(image, "png", outputFile);
    } catch (IOException e) {
      viewer.showErrorMessage("Error while saving the image: " + e.getMessage());
    }

  }

}
