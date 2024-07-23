package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * The ImageOperations class implements the ImageProcessor interface, providing various.
 * image processing operations.
 */

public class ImageOperations implements ImageProcessor {

  private static Map<String, Image> imageInstances = new HashMap<>();
  protected int height;
  protected int width;
  protected int redComponentOfImage;
  protected int greenComponentOfImage;
  protected int blueComponentOfImage;

  /**
   * Constructs a new `ImageOperations` instance. Initializes the image storage if null.
   */
  public ImageOperations() {
    if (imageInstances == null) {
      imageInstances = new HashMap<>();
    }

  }

  /**
   * Retrieves an image with the specified name and prints it to the console.
   *
   * @param imageName The name of the image to retrieve.
   * @return The Image object associated with the specified image name.
   */

  public Image getImageImage(String imageName) {
    Image image = getImage(imageName);
    return image;
  }

  /**
   * Retrieves an image from the imageInstances map based on the provided image name.
   *
   * @param imageName The name of the image to retrieve from the map
   * @return The Image object associated with the specified image name
   */
  public Image getImage(String imageName) {
    return imageInstances.get(imageName);
  }

  /**
   * Flips the specified image horizontally or vertically and saves the result as a new image.
   *
   * @param sourceImageName The name of the source image to flip.
   * @param destImageName   The name to assign to the flipped image.
   * @param isHorizontal    A boolean indicating whether to flip the image horizontally (true)
   *                        or vertically (false).
   */
  @Override
  public void flipImage(String sourceImageName, String destImageName, boolean isHorizontal) {
    Image sourceImage = getImageImage(sourceImageName);
    height = sourceImage.getHeight();
    width = sourceImage.getWidth();
    Image flippedImage = new Image(height, width);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] pixelArrayPrimary;
        int[] pixelArraySecondary;

        if (isHorizontal) {
          pixelArrayPrimary = sourceImage.getImage(i, j);
          pixelArraySecondary = sourceImage.getImage(i, width - j - 1);
          flippedImage.setImage(i, j,
                  pixelArraySecondary[0], pixelArraySecondary[1], pixelArraySecondary[2]);
          flippedImage.setImage(i, width - j - 1,
                  pixelArrayPrimary[0], pixelArrayPrimary[1], pixelArrayPrimary[2]);
        } else {
          pixelArrayPrimary = sourceImage.getImage(i, j);
          pixelArraySecondary = sourceImage.getImage(height - i - 1, j);
          flippedImage.setImage(i, j,
                  pixelArraySecondary[0], pixelArraySecondary[1], pixelArraySecondary[2]);
          flippedImage.setImage(height - i - 1, j,
                  pixelArrayPrimary[0], pixelArrayPrimary[1], pixelArrayPrimary[2]);
        }
      }
    }

    addImage(destImageName, flippedImage);
  }

  /**
   * Splits the source image into its RGB channels and saves them as separate images.
   *
   * @param sourceImageName    The name of the source image.
   * @param destRedImageName   The name of the destination image for the red channel
   * @param destGreenImageName The name of the destination image for the green channel
   * @param destBlueImageName  The name of the destination image for the blue channel
   */
  @Override
  public void rgbSplit(String sourceImageName, String destRedImageName,
                       String destGreenImageName, String destBlueImageName) {
    Image sourceImage = getImageImage(sourceImageName);
    height = sourceImage.getHeight();
    width = sourceImage.getWidth();
    Image redChannel = new Image(height, width);
    Image greenChannel = new Image(height, width);
    Image blueChannel = new Image(height, width);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] pixelArray = sourceImage.getImage(i, j);

        if (pixelArray != null) {
          redComponentOfImage = pixelArray[0];
          greenComponentOfImage = pixelArray[1];
          blueComponentOfImage = pixelArray[2];
          redChannel.setImage(i, j, redComponentOfImage, 0, 0);
          greenChannel.setImage(i, j, 0, greenComponentOfImage, 0);
          blueChannel.setImage(i, j, 0, 0, blueComponentOfImage);
        }
      }
    }
    addImage(destRedImageName, redChannel);
    addImage(destBlueImageName, blueChannel);
    addImage(destGreenImageName, greenChannel);
  }

  /**
   * Combines separate red, green, and blue channel images into a single color image.
   *
   * @param destImageName        The name of the destination color image
   * @param sourceRedImageName   The name of the source image for the red channel
   * @param sourceBlueImageName  The name of the source image for the blue channel
   * @param sourceGreenImageName The name of the source image for the green channel
   */
  @Override
  public void rgbCombine(String destImageName, String sourceRedImageName,
                         String sourceGreenImageName, String sourceBlueImageName) {

    Image redChannel = getImageImage(sourceRedImageName);
    Image blueChannel = getImageImage(sourceBlueImageName);
    Image greenChannel = getImageImage(sourceGreenImageName);
    height = redChannel.getHeight();
    width = redChannel.getWidth();
    Image colorImage = new Image(height, width);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        redComponentOfImage = redChannel.getImage(i, j)[0];
        greenComponentOfImage = greenChannel.getImage(i, j)[1];
        blueComponentOfImage = blueChannel.getImage(i, j)[2];
        colorImage.setImage(i, j, redComponentOfImage, greenComponentOfImage, blueComponentOfImage);
      }
    }
    addImage(destImageName, colorImage);
  }

  /**
   * Creates a new image by extracting a specific color component (Red, Green, or Blue)
   * from the source image and saves it with the specified destination image name.
   *
   * @param sourceImageName The name of the source image from which to extract the color component
   * @param destImageName   The name to assign to the newly created image
   * @param componentIndex  An integer representing the color component to extract:
   *                        - 0 for the Red component,
   *                        - 1 for the Green component,
   *                        - 2 for the Blue component
   */
  @Override
  public void createColorComponent(String sourceImageName,
                                   String destImageName, int componentIndex) {
    Image sourceImage = getImageImage(sourceImageName);
    if (sourceImage != null) {
      int height = sourceImage.getHeight();
      int width = sourceImage.getWidth();
      Image componentImage = new Image(height, width);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int[] pixelArray = sourceImage.getImage(i, j);
          if (pixelArray != null) {
            int r = (componentIndex == 0) ? 0 : pixelArray[0];
            int g = (componentIndex == 1) ? 0 : pixelArray[1];
            int b = (componentIndex == 2) ? 0 : pixelArray[2];

            componentImage.setImage(i, j, r, g, b);
          }
        }
      }
      addImage(destImageName, componentImage);
    }
  }

  /**
   * Adjusts the brightness of an image by adding a specified brightness change value to each pixel.
   * The resulting image is saved with the specified destination image name.
   *
   * @param brightnessChange The amount to change the brightness. A positive value increases
   *                         brightness, while a negative value decreases it
   * @param sourceImageName  The name of the source image to adjust
   * @param destImageName    The name to assign to the adjusted image
   */
  @Override
  public void adjustBrightness(int brightnessChange, String sourceImageName, String destImageName) {
    Image sourceImage = getImageImage(sourceImageName);
    int height = sourceImage.getHeight();
    int width = sourceImage.getWidth();
    Image adjustedImage = new Image(height, width);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] pixelArray = sourceImage.getImage(i, j);
        if (pixelArray != null) {
          int r = Math.max(0, Math.min(255, pixelArray[0] + brightnessChange));
          int g = Math.max(0, Math.min(255, pixelArray[1] + brightnessChange));
          int b = Math.max(0, Math.min(255, pixelArray[2] + brightnessChange));
          adjustedImage.setImage(i, j, r, g, b);
        }
      }
    }
    addImage(destImageName, adjustedImage);
  }

  /**
   * Applies a convolution filter to an image using the specified kernel matrix.
   * The resulting image is saved with the specified destination image name.
   *
   * @param sourceImageName The name of the source image to apply the convolution filter to
   * @param destImageName   The name to assign to the filtered image.
   * @param kernel          The convolution kernel matrix to apply to the image
   * @param splitKeyword    A keyword indicating whether to split
   * @param splitPercentage The percentage at which to split the filtering operation
   *                        Only applicable if splitKeyword is "split"
   */
  @Override
  public void applyConvolutionFilter(String sourceImageName, String destImageName,
                                     double[][] kernel, String splitKeyword, int splitPercentage) {
    Image sourceImage = getImageImage(sourceImageName);
    int height = sourceImage.getHeight();
    int width = sourceImage.getWidth();

    int splitPoint = (splitKeyword.equals("split")) ? (width * splitPercentage / 100) : -1;

    Image tempImage = new Image(height, width);

    int kernelSize = kernel.length;
    int kernelRadius = kernelSize / 2;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] result = {0, 0, 0};

        if (splitPoint == -1 || j < splitPoint) {
          for (int ky = -kernelRadius; ky <= kernelRadius; ky++) {
            for (int kx = -kernelRadius; kx <= kernelRadius; kx++) {
              int pixelX = j + kx;
              int pixelY = i + ky;

              if (pixelX >= 0 && pixelX < width && pixelY >= 0 && pixelY < height) {
                int[] pixelArray = sourceImage.getImage(pixelY, pixelX);
                for (int c = 0; c < 3; c++) {
                  result[c] += kernel[ky + kernelRadius][kx + kernelRadius] * pixelArray[c];
                }
              }
            }
          }
          for (int c = 0; c < 3; c++) {
            result[c] = Math.min(255, Math.max(0, result[c]));
          }
          tempImage.setImage(i, j, result[0], result[1], result[2]);
        } else {
          int[] pixelArray = sourceImage.getImage(i, j);
          tempImage.setImage(i, j, pixelArray[0], pixelArray[1], pixelArray[2]);
        }
      }
    }

    Image filteredImage = new Image(height, width);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] pixelArray = tempImage.getImage(i, j);
        filteredImage.setImage(i, j, pixelArray[0], pixelArray[1], pixelArray[2]);
      }
    }

    addImage(destImageName, filteredImage);
  }

  /**
   * Creates a new image by extracting a specific component from the image using coefficients.
   * The resulting image is saved with the specified destination image name.
   *
   * @param sourceImageName The name of the source image from which to extract the component
   * @param destImageName   The name to assign to the newly created image
   * @param coefficients    The coefficients used to calculate the component value from the image
   */
  @Override
  public void createComponent(String sourceImageName, String destImageName, double[] coefficients) {
    Image sourceImage = getImageImage(sourceImageName);
    height = sourceImage.getHeight();
    width = sourceImage.getWidth();
    Image componentImage = new Image(height, width);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] pixelArray = sourceImage.getImage(i, j);
        if (pixelArray != null) {
          int componentValue = calculateComponentValue(pixelArray, coefficients);
          componentImage.setImage(i, j, componentValue, componentValue, componentValue);
        }
      }
    }

    addImage(destImageName, componentImage);
  }

  /**
   * Calculates the component value based on the pixel array and coefficients.
   *
   * @param pixelArray   The pixel array containing the color components.
   * @param coefficients The coefficients used to calculate the component value.
   * @return The calculated component value.
   */
  private int calculateComponentValue(int[] pixelArray, double[] coefficients) {
    int redComponent = pixelArray[0];
    int greenComponent = pixelArray[1];
    int blueComponent = pixelArray[2];

    int componentValue = (int) (coefficients[0] * redComponent
            + coefficients[1] * greenComponent
            + coefficients[2] * blueComponent);

    return componentValue;
  }

  /**
   * Adds an image to the image processing system, associating it with a specified image name.
   *
   * @param imageName The name under which the image will be stored.
   * @param pixel     The image to be added to the image processing system.
   */
  public void addImage(String imageName, Image pixel) {
    imageInstances.put(imageName, pixel);
  }

  /**
   * Plots the histogram of the specified source image and saves the result as a new image.
   *
   * @param sourceImageName The name of the source image to generate the histogram from.
   * @param destImageName   The name of the destination image to save the histogram plot.
   */
  @Override
  public void plotHistogram(String sourceImageName, String destImageName) {
    Image sourceImage = getImageImage(sourceImageName);
    int width = Math.max(256,sourceImage.getWidth());
    int height = Math.max(256,sourceImage.getHeight());

    BufferedImage histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics g = histogramImage.getGraphics();

    // Set the background color to white
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);

    // Create and draw histogram for the original image
    createAndDrawHistogram(sourceImage, g, height);

    Image destHist = bufferedImageToImage(histogramImage);
    addImage(destImageName, destHist);
  }

  /**
   * Calculates the histogram of the specified channel in the given image.
   *
   * @param image   The image for which the histogram is calculated.
   * @param channel The channel for which the histogram is calculated (e.g., red, green, or blue).
   * @return An array representing the histogram for the specified channel.
   */
  private int[] calculateHistogram(Image image, int channel) {
    int[] histogram = new int[256];
    int width = Math.max(256,image.getWidth());
    int height = Math.max(256,image.getHeight());

    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        int[] pixel = image.getImage(x, y);
        if (pixel != null && pixel.length > channel) {
          histogram[pixel[channel]]++;
        }
      }
    }

    return histogram;
  }

  /**
   * Creates and draws an RGB histogram graph on the specified Graphics object.
   *
   * @param image  The image for which the RGB histogram is created and drawn.
   * @param g      The Graphics object on which the histogram graph will be drawn.
   * @param height The height of the histogram graph.
   */
  private void createAndDrawHistogram(Image image, Graphics g, int height) {
    int[] redHistogram = calculateHistogram(image, 0);
    int[] greenHistogram = calculateHistogram(image, 1);
    int[] blueHistogram = calculateHistogram(image, 2);

    // Find the maximum count among all channels
    int maxCount = 0;
    for (int i = 0; i < 256; i++) {
      maxCount = Math.max(maxCount, Math.max(redHistogram[i],
              Math.max(greenHistogram[i], blueHistogram[i])));
    }

    // Draw the RGB line graphs
    drawHistogram(g, redHistogram, Color.RED, maxCount, height);
    drawHistogram(g, greenHistogram, Color.GREEN, maxCount, height);
    drawHistogram(g, blueHistogram, Color.BLUE, maxCount, height);
  }

  /**
   * Draws a histogram line graph on the specified Graphics object using the provided data.
   *
   * @param g         The Graphics object on which the histogram graph will be drawn
   * @param histogram The histogram data to be visualized
   * @param color     The color of the histogram line
   * @param maxCount  The maximum count among all channels for normalization
   * @param height    The height of the histogram graph
   */
  private void drawHistogram(Graphics g, int[] histogram, Color color, int maxCount, int height) {
    g.setColor(color);
    for (int i = 1; i < 256; i++) {
      int currHeight = (int) ((histogram[i] / (double) maxCount) * (height));
      int prevHeight = (int) ((histogram[i - 1] / (double) maxCount) * (height));
      g.drawLine(i - 1, height - prevHeight, i, height - currHeight);
    }
  }

  /**
   * Color corrects an image based on the distribution of color intensity peaks in its histograms.
   * The color correction is applied separately to each channel (Red, Green, and Blue).
   *
   * @param sourceImageName The name of the source image.
   * @param destImageName   The name of the destination image where the color-corrected result
   * @param splitKeyword    A keyword indicating whether to split the color correction
   *                        Valid values are "split" or any other string to disable splitting
   * @param splitPercentage The percentage of the image width at which to split is applied
   */
  @Override
  public void colorCorrectImage(String sourceImageName, String destImageName,
                                String splitKeyword, int splitPercentage) {
    Image sourceImage = getImageImage(sourceImageName);
    int width = sourceImage.getWidth();
    int height = sourceImage.getHeight();
    int splitPoint = (splitKeyword.equals("split")) ? (width * splitPercentage / 100) : -1;

    // Calculate histograms for each channel
    int[] redHistogram = calculateHistogram(sourceImage, 0);
    int[] greenHistogram = calculateHistogram(sourceImage, 1);
    int[] blueHistogram = calculateHistogram(sourceImage, 2);

    // Find peaks for each channel
    int redPeak = findPeakInRange(redHistogram, 10, 245);
    int greenPeak = findPeakInRange(greenHistogram, 10, 245);
    int bluePeak = findPeakInRange(blueHistogram, 10, 245);

    // Calculate the average peak value
    int averagePeakValue = (redPeak + greenPeak + bluePeak) / 3;

    // Offset histograms for each channel
    int[] correctedRedHistogram = offsetChannel(redHistogram, averagePeakValue);
    int[] correctedGreenHistogram = offsetChannel(greenHistogram, averagePeakValue);
    int[] correctedBlueHistogram = offsetChannel(blueHistogram, averagePeakValue);
    // Apply offset to the color-corrected image
    Image colorCorrectedImage = new Image(height, width);
    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        int[] pixel = sourceImage.getImage(x, y);
        if (pixel != null) {
          int r = 0;
          int g = 0;
          int b = 0;
          if (splitPoint == -1 || y < splitPoint) {
            r = correctedRedHistogram[pixel[0]];
            g = correctedGreenHistogram[pixel[1]];
            b = correctedBlueHistogram[pixel[2]];
          } else {
            r = pixel[0];
            g = pixel[1];
            b = pixel[2];
          }
          colorCorrectedImage.setImage(x, y, r, g, b);
        }
      }
    }
    addImage(destImageName, colorCorrectedImage);
  }

  /**
   * Finds the position of the peak within a specified range in a histogram.
   *
   * @param histogram The histogram array representing the distribution of intensity values
   * @param start     The starting index of the range to search for the peak (inclusive)
   * @param end       The ending index of the range to search for the peak (exclusive)
   * @return The position of the peak within the specified range
   */
  private int findPeakInRange(int[] histogram, int start, int end) {
    int maxPeakValue = 0;
    int peakPosition = 0;

    for (int i = start; i < end; i++) {
      if (histogram[i] > maxPeakValue) {
        maxPeakValue = histogram[i];
        peakPosition = i;
      }

    }
    return peakPosition;
  }

  /**
   * Offsets the intensity values of a histogram to match a target peak value within a range.
   *
   * @param histogram       The histogram array representing the distribution of intensity values
   * @param targetPeakValue The desired peak value to which the histogram will be adjusted
   * @return The adjusted histogram array with intensity values offset to match the target peak
   */
  private int[] offsetChannel(int[] histogram, int targetPeakValue) {
    int currentPeakValue = findPeakInRange(histogram, 10, 245);
    int offset = targetPeakValue - currentPeakValue;

    for (int i = 0; i < histogram.length; i++) {
      histogram[i] = i + offset;

      // Ensure values are within the valid range [0, 255]
      histogram[i] = Math.max(0, Math.min(histogram[i], 255));
    }

    return histogram;
  }

  /**
   * Adjusts the levels of an image by modifying the shadow, mid-tone, and highlight intensities.
   *
   * @param shadow          The adjustment factor for the shadow intensity
   *                        Values should be in the range [-255, 255]
   * @param mid             The adjustment factor for the mid-tone intensity
   *                        Values should be in the range [-255, 255]
   * @param highlight       The adjustment factor for the highlight intensity
   *                        Values should be in the range [-255, 255]
   * @param sourceImageName The name of the source image.
   * @param destImageName   The name of the destination image where the adjusted image
   * @param splitKeyword    The keyword indicating whether to split the adjustment horizontally
   * @param splitPercentage The percentage of the width where the split should occur.
   */
  @Override
  public void adjustLevels(int shadow, int mid, int highlight, String sourceImageName,
                           String destImageName, String splitKeyword, int splitPercentage) {
    Image sourceImage = getImageImage(sourceImageName);
    int width = sourceImage.getWidth();
    int height = sourceImage.getHeight();
    int splitPoint = (splitKeyword.equals("split")) ? (width * splitPercentage / 100) : -1;
    Image levelAdjustedImage = new Image(height, width);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] pixelArray = sourceImage.getImage(i, j);
        if (pixelArray != null) {
          int r = 0;
          int g = 0;
          int b = 0;
          if (splitPoint == -1 || j < splitPoint) {
            int[] red = adjustSingleChannel(pixelArray, 0, shadow, mid, highlight);
            int[] green = adjustSingleChannel(pixelArray, 1, shadow, mid, highlight);
            int[] blue = adjustSingleChannel(pixelArray, 2, shadow, mid, highlight);
            r = red[0];
            g = green[1];
            b = blue[2];
          } else {
            r = pixelArray[0];
            g = pixelArray[1];
            b = pixelArray[2];
          }
          levelAdjustedImage.setImage(i, j, r, g, b);
        }
      }
    }
    addImage(destImageName, levelAdjustedImage);
  }

  /**
   * Adjusts the intensity of a single channel in an RGB pixel array based on the following.
   * Shadow, mid-tone, and highlight values.
   *
   * @param pixelArray   The RGB pixel array representing a color
   * @param channelIndex The index of the channel to be adjusted (0 for e, 1 for g, 2 for b)
   * @param shadow       The shadow intensity adjustment factor
   *                     Values should be in the range [0, 255]
   * @param mid          The mid-tone intensity adjustment factor
   *                     Values should be in the range [0, 255]
   * @param highlight    The highlight intensity adjustment factor
   *                     Values should be in the range [0, 255]
   * @return An int array representing the adjusted RGB values for the specified channel
   */
  private int[] adjustSingleChannel(int[] pixelArray, int channelIndex,
                                    int shadow, int mid, int highlight) {
    int[] adjustedArray = new int[3];

    if (pixelArray != null && pixelArray.length == 3) {
      int value = pixelArray[channelIndex];

      if (value <= shadow) {
        adjustedArray[channelIndex] = 0;
      } else if (value >= highlight) {
        adjustedArray[channelIndex] = 255;
      } else {
        double slope1 = 128.0 / (mid - shadow);
        double slope2 = 127.0 / (highlight - mid);

        if (value <= mid) {
          adjustedArray[channelIndex] = (int) (slope1 * (value - shadow));
        } else {
          adjustedArray[channelIndex] = 128 + (int) (slope2 * (value - mid));
        }
      }
    }
    return adjustedArray;
  }


  /**
   * Converts a BufferedImage to an Image object.
   *
   * @param bufferedImage The BufferedImage to be converted
   * @return An Image object representing the content of the BufferedImage
   * @throws IllegalArgumentException If the provided BufferedImage is null
   */
  private Image bufferedImageToImage(BufferedImage bufferedImage) {
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    Image image = new Image(height, width);

    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        Color color = new Color(bufferedImage.getRGB(y, x));
        int[] pixel = {color.getRed(), color.getGreen(), color.getBlue()};
        image.setImage(x, y, pixel[0], pixel[1], pixel[2]);
      }
    }

    return image;
  }


  /**
   * Applies a sepia tone transformation to the specified portion of an image.
   *
   * @param sourceImageName      The name of the source image
   * @param destImageName        The name of the destination image
   * @param splitKeyword         A keyword indicating whether to apply the "split"
   * @param splitPercentage      The percentage of the width to split the image
   * @param transformationMatrix The 3x3 matrix representing the sepia tone transformation
   *                             Each element of the matrix is the weight of the color channel
   *                             The matrix is applied to each pixel's RGB values
   */
  @Override
  public void graySepia(String sourceImageName, String destImageName,
                        String splitKeyword, int splitPercentage, double[][] transformationMatrix) {
    Image sourceImage = getImageImage(sourceImageName);
    int height = sourceImage.getHeight();
    int width = sourceImage.getWidth();
    int splitPoint = (splitKeyword.equals("split")) ? (width * splitPercentage / 100) : -1;

    Image transformedImage = new Image(height, width);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] pixelArray = sourceImage.getImage(i, j);
        if (pixelArray != null) {
          int r = 0;
          int g = 0;
          int b = 0;
          if (splitPoint == -1 || j < splitPoint) {
            r = Math.min(255, (int) (transformationMatrix[0][0] * pixelArray[0]
                    + transformationMatrix[0][1] * pixelArray[1]
                    + transformationMatrix[0][2] * pixelArray[2]));
            g = Math.min(255, (int) (transformationMatrix[1][0] * pixelArray[0]
                    + transformationMatrix[1][1] * pixelArray[1]
                    + transformationMatrix[1][2] * pixelArray[2]));
            b = Math.min(255, (int) (transformationMatrix[2][0] * pixelArray[0]
                    + transformationMatrix[2][1] * pixelArray[1]
                    + transformationMatrix[2][2] * pixelArray[2]));

          } else {
            r = pixelArray[0];
            g = pixelArray[1];
            b = pixelArray[2];
          }
          transformedImage.setImage(i, j, r, g, b);
        }
      }
    }
    addImage(destImageName, transformedImage);
  }

  /**
   * Compresses the source image by applying Haar wavelet transformation and thresholding.
   *
   * @param compressionPercentage The percentage of compression to be applied
   * @param sourceImageName       The filename of the source image
   * @param destImageName         The filename for the compressed image
   */
  @Override
  public void compressImage(Double compressionPercentage,
                            String sourceImageName, String destImageName) {
    Image sourceImage = getImageImage(sourceImageName);
    ArrayList<int[][]> channels = rgbSplitToArrayLists(sourceImage);

    List<List<Double>> redMatrixOriginal = convertToDoubleArrayList(channels.get(0));
    List<List<Double>> greenMatrixOriginal = convertToDoubleArrayList(channels.get(1));
    List<List<Double>> blueMatrixOriginal = convertToDoubleArrayList(channels.get(2));

    int originalRows = sourceImage.getHeight();
    int originalCols = sourceImage.getWidth();
    int maxDimension = Math.max(originalRows, originalCols);
    int nextPowerOfTwo = 1;
    while (nextPowerOfTwo < maxDimension) {
      nextPowerOfTwo *= 2;
    }


    List<List<Double>> redMatrixPadded = padImage(redMatrixOriginal, nextPowerOfTwo);
    List<List<Double>> greenMatrixPadded = padImage(greenMatrixOriginal, nextPowerOfTwo);
    List<List<Double>> blueMatrixPadded = padImage(blueMatrixOriginal, nextPowerOfTwo);

    List<List<Double>> redMatrixTransformed = haar2D(redMatrixPadded, nextPowerOfTwo);
    List<List<Double>> greenMatrixTransformed = haar2D(greenMatrixPadded, nextPowerOfTwo);
    List<List<Double>> blueMatrixTransformed = haar2D(blueMatrixPadded, nextPowerOfTwo);

    List<List<List<Double>>> thresholdedMatrices = applyThreshold(redMatrixTransformed,
            greenMatrixTransformed, blueMatrixTransformed, compressionPercentage);

    List<List<Double>> redMatrixThresholded = thresholdedMatrices.get(0);
    List<List<Double>> greenMatrixThresholded = thresholdedMatrices.get(1);
    List<List<Double>> blueMatrixThresholded = thresholdedMatrices.get(2);

    List<List<Double>> redMatrixInverted = inversehaar2D(redMatrixThresholded, nextPowerOfTwo);
    List<List<Double>> greenMatrixInverted = inversehaar2D(greenMatrixThresholded, nextPowerOfTwo);
    List<List<Double>> blueMatrixInverted = inversehaar2D(blueMatrixThresholded, nextPowerOfTwo);


    List<List<Double>> redMatrixUnpadded
            = unpadImage(redMatrixInverted, originalRows, originalCols);
    List<List<Double>> greenMatrixUnpadded
            = unpadImage(greenMatrixInverted, originalRows, originalCols);
    List<List<Double>> blueMatrixUnpadded
            = unpadImage(blueMatrixInverted, originalRows, originalCols);

    List<List<Integer>> finalRedMatrix
            = clampPixelValues(roundAndConvertToInt(redMatrixUnpadded));
    List<List<Integer>> finalGreenMatrix
            = clampPixelValues(roundAndConvertToInt(greenMatrixUnpadded));
    List<List<Integer>> finalBlueMatrix
            = clampPixelValues(roundAndConvertToInt(blueMatrixUnpadded));

    addImage(destImageName, combineChannels(finalRedMatrix, finalGreenMatrix, finalBlueMatrix,
            sourceImage.getHeight(), sourceImage.getWidth()));
  }


  /**
   * Performs a transformation on a sequence by calculating averages and differences.
   *
   * @param sequence The input sequence to be transformed
   * @return A list containing the transformed sequence
   */
  private List<Double> transform(List<Double> sequence) {
    List<Double> avg = new ArrayList<>();
    List<Double> diff = new ArrayList<>();
    for (int i = 0; i < sequence.size() - 1; i += 2) {
      double first_value = sequence.get(i);
      double second_value = sequence.get(i + 1);
      double average = calculateNormalizedAverage(first_value, second_value);
      double difference = calculateNormalizedDifference(first_value, second_value);
      avg.add(average);
      diff.add(difference);
    }
    avg.addAll(diff);
    return avg;
  }

  /**
   * Reverts the transformation applied to a sequence to retrieve the original sequence.
   *
   * @param sequence The transformed sequence
   * @return The reverted original sequence
   */
  private List<Double> inverse(List<Double> sequence) {
    List<Double> avg = new ArrayList<>();
    List<Double> diff = new ArrayList<>();
    int middle = sequence.size() / 2;
    int j = middle;
    for (int i = 0; i < middle; i++) {
      double a = sequence.get(i);
      double b = sequence.get(j);
      double average = calculateNormalizedAverage(a, b);
      double difference = calculateNormalizedDifference(a, b);

      avg.add(average);
      diff.add(difference);
      j++;
    }

    List<Double> result = new ArrayList<>();
    for (int k = 0; k < avg.size(); k++) {
      result.add(avg.get(k));
      result.add(diff.get(k));
    }

    return result;
  }

  /**
   * Performs a 2D Haar wavelet transformation on a matrix of sequences.
   *
   * @param sequence The input matrix containing sequences
   * @param size     The size of the matrix
   * @return The transformed matrix after the 2D Haar wavelet transformation
   */
  private List<List<Double>> haar2D(List<List<Double>> sequence, int size) {
    int c = size;
    while (c > 1) {
      // Transforming rows
      for (int i = 0; i < c; i++) {
        List<Double> row = sequence.get(i).subList(0, c);
        List<Double> transformedRow = transform(row);
        for (int j = 0; j < c; j++) {
          sequence.get(i).set(j, transformedRow.get(j));
        }
      }
      // Transforming columns
      for (int j = 0; j < c; j++) {
        List<Double> column = new ArrayList<>();
        for (int i = 0; i < c; i++) {
          column.add(sequence.get(i).get(j));
        }
        List<Double> transformedColumn = transform(column);
        for (int i = 0; i < c; i++) {
          sequence.get(i).set(j, transformedColumn.get(i));
        }
      }
      c /= 2;
    }
    return sequence;
  }

  /**
   * Reverts the 2D Haar wavelet transformation on a matrix of sequences.
   *
   * @param sequence The transformed matrix
   * @param size     The size of the matrix
   * @return The original matrix after inverse Haar transformation
   */
  private List<List<Double>> inversehaar2D(List<List<Double>> sequence, int size) {
    int c = 2;
    while (c <= size) {
      // Inverse transform for the first 'c' columns of X
      for (int j = 0; j < c; j++) {
        List<Double> column = new ArrayList<>();
        for (int i = 0; i < c; i++) {
          column.add(sequence.get(i).get(j));
        }
        List<Double> invertedColumn = inverse(column);
        for (int i = 0; i < c; i++) {
          sequence.get(i).set(j, invertedColumn.get(i));
        }
      }
      for (int i = 0; i < c; i++) {
        List<Double> row = sequence.get(i).subList(0, c);
        List<Double> invertedRow = inverse(row);
        for (int j = 0; j < c; j++) {
          sequence.get(i).set(j, invertedRow.get(j));
        }
      }
      c *= 2;
    }
    return sequence;
  }

  /**
   * Applies a threshold to the transformed matrices based on the compression percentage.
   *
   * @param transformedRedMatrix   Transformed red channel matrix
   * @param transformedGreenMatrix Transformed green channel matrix
   * @param transformedBlueMatrix  Transformed blue channel matrix
   * @param compressionPercentage  The percentage of compression to be applied
   * @return List of thresholded matrices
   */
  private List<List<List<Double>>> applyThreshold(List<List<Double>> transformedRedMatrix,
                                                  List<List<Double>> transformedGreenMatrix,
                                                  List<List<Double>> transformedBlueMatrix,
                                                  double compressionPercentage) {
    Set<Double> absValues = new TreeSet<>();
    for (List<Double> channel : transformedRedMatrix) {
      absValues.addAll(channel.stream().map(Math::abs).collect(Collectors.toList()));
    }
    for (List<Double> channel : transformedGreenMatrix) {
      absValues.addAll(channel.stream().map(Math::abs).collect(Collectors.toList()));
    }
    for (List<Double> channel : transformedBlueMatrix) {
      absValues.addAll(channel.stream().map(Math::abs).collect(Collectors.toList()));
    }
    ArrayList<Double> absList = new ArrayList<>(absValues);
    Collections.sort(absList);
    int thresholdIndex = Math.abs((int) (absList.size() * compressionPercentage / 100.0) - 1);
    double threshold = absList.get(thresholdIndex);
    for (List<List<Double>> channelMatrix : List.of(transformedRedMatrix, transformedGreenMatrix,
            transformedBlueMatrix)) {
      for (List<Double> channel : channelMatrix) {
        for (int i = 0; i < channel.size(); i++) {
          double currentValue = channel.get(i);
          if (Math.abs(currentValue) <= threshold) {
            channel.set(i, 0.0);
          }
        }
      }
    }
    List<List<List<Double>>> thresholdedMatrices = new ArrayList<>();
    thresholdedMatrices.add(transformedRedMatrix);
    thresholdedMatrices.add(transformedGreenMatrix);
    thresholdedMatrices.add(transformedBlueMatrix);

    return thresholdedMatrices;
  }


  /**
   * Splits the RGB channels of an image into separate arrays and stores them in an ArrayList.
   *
   * @param sourceImage The source image from which RGB channels are extracted
   * @return An ArrayList containing the red, green, and blue channel arrays
   */
  private ArrayList<int[][]> rgbSplitToArrayLists(Image sourceImage) {
    int height = sourceImage.getHeight();
    int width = sourceImage.getWidth();
    ArrayList<int[][]> channels = new ArrayList<>();

    int[][] redChannel = new int[height][width];
    int[][] greenChannel = new int[height][width];
    int[][] blueChannel = new int[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] pixelArray = sourceImage.getImage(i, j);
        if (pixelArray != null) {
          redChannel[i][j] = pixelArray[0];
          greenChannel[i][j] = pixelArray[1];
          blueChannel[i][j] = pixelArray[2];
        }
      }
    }
    channels.add(redChannel);
    channels.add(greenChannel);
    channels.add(blueChannel);
    return channels;
  }

  /**
   * Converts a 2D integer array to a List of Lists containing Double values.
   *
   * @param array The 2D integer array to be converted
   * @return A List of Lists containing Double values converted from the input array
   */
  private List<List<Double>> convertToDoubleArrayList(int[][] array) {
    List<List<Double>> resultList = new ArrayList<>();
    for (int[] row : array) {
      List<Double> newRow
              = Arrays.stream(row).asDoubleStream().boxed().collect(Collectors.toList());
      resultList.add(newRow);
    }
    return resultList;
  }

  /**
   * Combines the red, green, and blue matrices into a single Image object.
   *
   * @param redMatrix   The red channel matrix
   * @param greenMatrix The green channel matrix
   * @param blueMatrix  The blue channel matrix
   * @param height      The height of the combined image
   * @param width       The width of the combined image
   * @return An Image object containing the combined RGB channels
   */
  private Image combineChannels(List<List<Integer>> redMatrix,
                                List<List<Integer>> greenMatrix,
                                List<List<Integer>> blueMatrix,
                                int height, int width) {
    Image combinedImage = new Image(height, width);

    for (int i = 0; i < height && i < redMatrix.size(); i++) {
      for (int j = 0; j < width && j < redMatrix.get(i).size(); j++) {
        int red = redMatrix.get(i).get(j);
        int green = greenMatrix.get(i).get(j);
        int blue = blueMatrix.get(i).get(j);
        // Check if the pixel is within the bounds of the original image before setting values
        if (i < combinedImage.getHeight() && j < combinedImage.getWidth()) {
          combinedImage.setImage(i, j, red, green, blue);
        }
      }
    }
    return combinedImage;
  }

  /**
   * Clamps pixel values of a pixel matrix between 0 and 255.
   *
   * @param pixelMatrix The pixel matrix to clamp values for
   * @return The pixel matrix with clamped values
   */
  private List<List<Integer>> clampPixelValues(List<List<Integer>> pixelMatrix) {
    for (List<Integer> row : pixelMatrix) {
      for (int i = 0; i < row.size(); i++) {
        int clampedValue = Math.min(255, Math.max(0, row.get(i)));
        row.set(i, clampedValue);
      }
    }
    return pixelMatrix;
  }

  /**
   * Pads an image matrix with zeros to the next power of two in both dimensions.
   *
   * @param image          The image matrix to be padded
   * @param nextPowerOfTwo The next power of two value for padding
   * @return The padded image matrix
   */
  private List<List<Double>> padImage(List<List<Double>> image, int nextPowerOfTwo) {
    int rows = image.size();
    int cols = image.get(0).size();

    // Pad the image to the next power of two
    List<List<Double>> paddedImage = new ArrayList<>();
    for (int i = 0; i < nextPowerOfTwo; i++) {
      if (i < rows) {
        List<Double> row = new ArrayList<>(image.get(i));
        while (row.size() < nextPowerOfTwo) {
          row.add(0.0); // Pad with zeros
        }
        paddedImage.add(row);
      } else {
        // Pad rows with zeros until the image becomes square
        List<Double> zeroRow = new ArrayList<>(Collections.nCopies(nextPowerOfTwo, 0.0));
        paddedImage.add(zeroRow);
      }
    }

    return paddedImage;
  }


  /**
   * Removes padding from an image matrix based on original dimensions.
   *
   * @param image        The padded image matrix
   * @param originalRows The original number of rows
   * @param originalCols The original number of columns
   * @return The unpadded image matrix
   */
  private List<List<Double>> unpadImage(List<List<Double>> image,
                                        int originalRows, int originalCols) {
    List<List<Double>> unpaddedImage = new ArrayList<>();
    for (int i = 0; i < originalRows; i++) {
      unpaddedImage.add(new ArrayList<>(image.get(i).subList(0, originalCols)));
    }
    return unpaddedImage;
  }

  /**
   * Calculates the normalized average of two double values.
   *
   * @param a The first double value
   * @param b The second double value
   * @return The normalized average of the two values
   */
  private double calculateNormalizedAverage(double a, double b) {
    return (a + b) / Math.sqrt(2);
  }

  /**
   * Calculates the normalized difference between two double values.
   *
   * @param a The first double value
   * @param b The second double value
   * @return The normalized difference between the two values
   */
  private double calculateNormalizedDifference(double a, double b) {
    return (a - b) / Math.sqrt(2);
  }

  /**
   * Rounds and converts a matrix of Double values to Integer values.
   *
   * @param originalMatrix The original matrix of Double values
   * @return A matrix containing rounded Integer values from the input matrix
   */
  private List<List<Integer>> roundAndConvertToInt(List<List<Double>> originalMatrix) {
    List<List<Integer>> roundedMatrix = new ArrayList<>();

    for (List<Double> row : originalMatrix) {
      List<Integer> roundedRow = new ArrayList<>();
      for (Double element : row) {
        int roundedElement = (int) Math.round(element);
        roundedRow.add(roundedElement);
      }
      roundedMatrix.add(roundedRow);
    }
    return roundedMatrix;
  }



}
