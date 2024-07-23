package model;

/**
 * The ImageProcessor interface defines a set of image processing operations that can be applied to
 * images. Classes implementing this interface must provide concrete implementations for these
 * methods.
 */
public interface ImageProcessor {
  /**
   * Splits the source image into its RGB channels and saves them as separate images.
   *
   * @param sourceImageName    The name of the source image.
   * @param destRedImageName   The name of the destination image for the red channel.
   * @param destGreenImageName The name of the destination image for the green channel.
   * @param destBlueImageName  The name of the destination image for the blue channel.
   */
  void rgbSplit(String sourceImageName, String destRedImageName,
                String destGreenImageName, String destBlueImageName);

  /**
   * Combines separate red, green, and blue channel images into a single color image.
   *
   * @param destImageName        The name of the destination color image.
   * @param sourceRedImageName   The name of the source image for the red channel.
   * @param sourceBlueImageName  The name of the source image for the blue channel.
   * @param sourceGreenImageName The name of the source image for the green channel.
   */
  void rgbCombine(String destImageName, String sourceRedImageName,
                  String sourceBlueImageName, String sourceGreenImageName);

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
  void applyConvolutionFilter(String sourceImageName, String destImageName, double[][] kernel,
                              String splitKeyword, int splitPercentage);

  /**
   * Adds an image to the image processing system, associating it with a specified image name.
   *
   * @param imageName The name under which the image will be stored in the image processing system.
   * @param pixel     The image to be added to the image processing system.
   */
  void addImage(String imageName, Image pixel);

  /**
   * Plots the histogram of the specified source image and saves the result as a new image.
   *
   * @param sourceImage   The name of the source image to generate the histogram from.
   * @param destImageName The name of the destination image to save the histogram plot.
   */
  void plotHistogram(String sourceImage, String destImageName);

  /**
   * Color corrects an image based on the distribution of color intensity peaks in its histograms.
   * The color correction is applied separately to each channel (Red, Green, and Blue).
   *
   * @param sourceImage     The name of the source image.
   * @param destImageName   The name of the destination image where the color-corrected result
   * @param splitKeyword    A keyword indicating whether to split the color correction
   *                        Valid values are "split" or any other string to disable splitting
   * @param splitPercentage The percentage of the image width at which to split is applied
   */
  void colorCorrectImage(String sourceImage, String destImageName, String splitKeyword,
                         int splitPercentage);

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
  void adjustLevels(int shadow, int mid, int highlight, String sourceImageName,
                    String destImageName, String splitKeyword, int splitPercentage);

  /**
   * Compresses the source image by applying Haar wavelet transformation and thresholding.
   *
   * @param compressionPercentage The percentage of compression to be applied
   * @param sourceImageName       The filename of the source image
   * @param destImageName         The filename for the compressed image
   */
  void compressImage(Double compressionPercentage, String sourceImageName, String destImageName);

  /**
   * Applies a sepia tone transformation to the specified portion of an image.
   *
   * @param sourceImageName The name of the source image
   * @param destImageName   The name of the destination image
   * @param splitKeyword    A keyword indicating whether to apply the "split"
   * @param splitPercentage The percentage of the width to split the image
   * @param grayscaleMatrix The 3x3 matrix representing the sepia tone transformation
   *                        Each element of the matrix is the weight of the color channel
   *                        The matrix is applied to each pixel's RGB values
   */
  void graySepia(String sourceImageName, String destImageName, String splitKeyword,
                 int splitPercentage, double[][] grayscaleMatrix);

  /**
   * Adjusts the brightness of an image by adding a specified brightness change value to each pixel.
   * The resulting image is saved with the specified destination image name.
   *
   * @param change          The amount to change the brightness. A positive value increases
   *                        brightness, while a negative value decreases it
   * @param sourceImageName The name of the source image to adjust
   * @param destImageName   The name to assign to the adjusted image
   */
  void adjustBrightness(int change, String sourceImageName, String destImageName);

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
  void createColorComponent(String sourceImageName, String destImageName, int componentIndex);

  /**
   * Creates a new image by extracting a specific component from the image using coefficients.
   * The resulting image is saved with the specified destination image name.
   *
   * @param sourceImageName The name of the source image from which to extract the component
   * @param destImageName   The name to assign to the newly created image
   * @param coefficients    The coefficients used to calculate the component value from the image
   */
  void createComponent(String sourceImageName, String destImageName, double[] coefficients);

  /**
   * Flips the specified image horizontally or vertically and saves the result as a new image.
   *
   * @param sourceImageName The name of the source image to flip.
   * @param destImageName   The name to assign to the flipped image.
   * @param isHorizontal    A boolean indicating whether to flip the image horizontally (true)
   *                        or vertically (false).
   */
  void flipImage(String sourceImageName, String destImageName, boolean isHorizontal);

  /**
   * Retrieves an image with the specified name and prints it to the console.
   *
   * @param sourceImageName The name of the image to retrieve.
   * @return The Image object associated with the specified image name.
   */
  Image getImageImage(String sourceImageName);

  /**
   * Retrieves an image from the imageInstances map based on the provided image name.
   *
   * @param sourceImageName The name of the image to retrieve from the map
   * @return The Image object associated with the specified image name
   */
  Image getImage(String sourceImageName);

}
