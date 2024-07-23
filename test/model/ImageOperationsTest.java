package model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * This class defines a set of test cases for the ImageOperations class. It contains methods to
 * perform various image processing operations.
 */
public class ImageOperationsTest {

  private ImageOperations processor;

  /**
   * This method is called before each test to initialize the ImageOperations object.
   */
  @Before
  public void setUp() {
    processor = new ImageOperations();
  }

  /**
   * This test case checks the horizontal flip for an image.
   */

  @Test
  public void testHorizontalFlip() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int width = 3;
    int height = 3;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    processor.flipImage(sourceImageName, destImageName, true);
    Image destImage = processor.getImage(destImageName);

    int[][] expectedPixels = {{200, 150, 100}, {100, 75, 50}, {75, 50, 25}};
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j], expectedPixels[i][j], expectedPixels[i][j]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * This test case checks the vertical flip for an image.
   */
  @Test
  public void testVerticalFlip() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int width = 3;
    int height = 3;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    processor.flipImage(sourceImageName, destImageName, false);
    Image destImage = processor.getImage(destImageName);

    int[][] expectedPixels = {{25, 50, 75}, {50, 75, 100}, {100, 150, 200}};
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j], expectedPixels[i][j], expectedPixels[i][j]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * This test case checks the intensity component for an image.
   */
  @Test
  public void testCreateIntensityComponent() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int width = 3;
    int height = 3;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    double[] intensityCoefficients = {1.0 / 3, 1.0 / 3, 1.0 / 3};
    processor.createComponent(sourceImageName, destImageName, intensityCoefficients);
    Image destImage = processor.getImage(destImageName);

    int[][] expectedPixels = {{99, 150, 199}, {49, 75, 99}, {24, 49, 75}};
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j], expectedPixels[i][j], expectedPixels[i][j]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * This test case checks the luma component for an image.
   */
  @Test
  public void testLumaCreateComponent() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int width = 2;
    int height = 2;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    double[] coefficients = {0.2126, 0.7152, 0.0722};
    processor.createComponent(sourceImageName, destImageName, coefficients);
    Image destImage = processor.getImage(destImageName);

    int[][] expectedPixels = {{100, 150, 147}, {50, 75, 71}};
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j], expectedPixels[i][j], expectedPixels[i][j]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * This test case checks the value component for an image.
   */
  @Test
  public void testValueCreateComponent() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int width = 3;
    int height = 3;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    double[] coefficients = {1, 1, 1};
    processor.createComponent(sourceImageName, destImageName, coefficients);
    Image destImage = processor.getImage(destImageName);

    int[][] expectedPixels = {{300, 450, 600}, {150, 225, 300}, {75, 150, 225}};
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j], expectedPixels[i][j], expectedPixels[i][j]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * This test case checks the sharpen filter for an image.
   */
  @Test
  public void testImageSharpen() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int width = 3;
    int height = 3;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    double[][] sharpeningFilter = {
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };
    processor.applyConvolutionFilter(sourceImageName, destImageName, sharpeningFilter,
            "no-split", 0);
    Image destImage = processor.getImage(destImageName);

    int[][] expectedPixels = {{108, 255, 240}, {100, 255, 212}, {0, 76, 68}};
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j], expectedPixels[i][j], expectedPixels[i][j]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * This test case checks the brightening functionality application to an image.
   */
  @Test
  public void testImageBrighten() {
    String sourceImageName = "sourceImage";
    int width = 2;
    int height = 2;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));
    String destImageName = "destImage";
    processor.adjustBrightness(50, sourceImageName, destImageName);
    Image destImage = processor.getImage(destImageName);
    int[][] expectedPixels = {{150, 200, 255}, {100, 125, 150}};

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j], expectedPixels[i][j], expectedPixels[i][j]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * This test case checks the darkening functionality application to an image.
   */
  @Test
  public void testImageDarken() {
    String sourceImageName = "sourceImage";
    int width = 2;
    int height = 2;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));
    String destImageName = "destImage";
    processor.adjustBrightness(-50, sourceImageName, destImageName);
    Image destImage = processor.getImage(destImageName);
    int[][] expectedPixels = {{50, 100, 255}, {0, 25, 150}};

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j], expectedPixels[i][j], expectedPixels[i][j]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * This test case verifies the functionality of the getImageImage method when the image is found.
   */
  @Test
  public void testGetImageImageImageFound() {
    String imageName = "validImage";
    Image expectedImage = new Image(2, 2);
    processor.addImage(imageName, expectedImage);
    Image result = processor.getImageImage(imageName);
    assertNotNull(result);
    assertSame(expectedImage, result);
  }

  /**
   * This test case verifies that the `getImageImage` method correctly handles the case when the
   * image is not found.
   */
  @Test
  public void testGetImageImageImageNotFound() {
    String imageName = "nonExistentImage";
    Image result = processor.getImageImage(imageName);
    assertEquals(null, result);
  }

  /**
   * Creates a new Image object with the specified width and height. Initializing it with pixel
   * values from a 2D array of integers.
   *
   * @param width  The width of the image.
   * @param height The height of the image.
   * @param pixels A 2D array of integers representing pixel values. The dimensions of this array
   *               should match the width and height.
   * @return A new Image object with pixel values based on the provided array.
   */
  private Image createImageFromArray(int width, int height, int[][] pixels) {
    Image image = new Image(height, width);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = pixels[i][j];
        int g = pixels[i][j];
        int b = pixels[i][j];
        image.setImage(i, j, r, g, b);
      }
    }
    return image;
  }

  /**
   * This test case checks when image does not exist.
   */
  @Test
  public void testGetImageNonExistentImage() {
    String nonExistentImageName = "nonExistentImage";
    Image retrievedImage = processor.getImage(nonExistentImageName);
    assertNull(retrievedImage);
  }

  /**
   * This test case checks the blur filter application to an image.
   */
  @Test
  public void testImageBlur() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int width = 3;
    int height = 3;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));
    double[][] gaussianBlurKernel = {
            {1.0 / 16.0, 2.0 / 16.0, 1.0 / 16.0},
            {2.0 / 16.0, 4.0 / 16.0, 2.0 / 16.0},
            {1.0 / 16.0, 2.0 / 16.0, 1.0 / 16.0}
    };
    processor.applyConvolutionFilter(sourceImageName, destImageName, gaussianBlurKernel,
            "no-split", 0);
    Image destImage = processor.getImage(destImageName);
    int[][] expectedPixels = {{53, 92, 84}, {48, 83, 80}, {22, 42, 40}};
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j], expectedPixels[i][j], expectedPixels[i][j]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * This test case checks the rgb split for an image.
   */
  @Test
  public void testRgbSplit() {
    String sourceImageName = "sourceImage";
    int width = 2;
    int height = 2;
    int[][] sourcePixels = {{100, 150}, {50, 75}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    String destRedImageName = "destRedImage";
    String destGreenImageName = "destGreenImage";
    String destBlueImageName = "destBlueImage";

    processor.rgbSplit(sourceImageName, destRedImageName, destGreenImageName, destBlueImageName);

    Image redChannel = processor.getImage(destRedImageName);
    Image greenChannel = processor.getImage(destGreenImageName);
    Image blueChannel = processor.getImage(destBlueImageName);

    int[][] expectedRedPixels = {{100, 150}, {50, 75}};

    int[][] expectedGreenPixels = {{100, 150}, {50, 75}};

    int[][] expectedBluePixels = {{100, 150}, {50, 75}};

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] redPixel = redChannel.getImage(i, j);
        int[] expectedRedPixel = {expectedRedPixels[i][j], 0, 0};
        assertArrayEquals(expectedRedPixel, redPixel);

        int[] greenPixel = greenChannel.getImage(i, j);
        int[] expectedGreenPixel = {0, expectedGreenPixels[i][j], 0};
        assertArrayEquals(expectedGreenPixel, greenPixel);

        int[] bluePixel = blueChannel.getImage(i, j);
        int[] expectedBluePixel = {0, 0, expectedBluePixels[i][j]};
        assertArrayEquals(expectedBluePixel, bluePixel);
      }
    }
  }

  /**
   * This test case checks the rgb combine for an image.
   */
  @Test
  public void testRgbCombine() {
    String destImageName = "colorImage";
    String sourceRedImageName = "redChannel";
    String sourceBlueImageName = "blueChannel";
    String sourceGreenImageName = "greenChannel";

    int width = 2;
    int height = 2;

    int[][] redPixels = {{100, 100}, {100, 100}};

    int[][] bluePixels = {{50, 50}, {50, 50}};

    int[][] greenPixels = {{0, 0}, {0, 0}};

    processor.addImage(sourceRedImageName, createImageFromArray(width, height, redPixels));
    processor.addImage(sourceBlueImageName, createImageFromArray(width, height, bluePixels));
    processor.addImage(sourceGreenImageName, createImageFromArray(width, height, greenPixels));

    processor.rgbCombine(destImageName, sourceRedImageName, sourceGreenImageName,
            sourceBlueImageName);

    Image colorImage = processor.getImage(destImageName);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] pixel = colorImage.getImage(i, j);
        int[] expectedPixel = {redPixels[i][j], greenPixels[i][j], bluePixels[i][j]};
        assertArrayEquals(expectedPixel, pixel);
      }
    }
  }

  /**
   * This test case checks the sepia filter application to an image.
   */
  @Test
  public void testImageSepia() {
    // Create a source image with known pixel values
    int width = 2;
    int height = 2;
    int[][] sourcePixels = {{100, 100, 100}, {50, 50, 50}};

    // Add the source image to the processor
    String sourceImageName = "sourceImage";
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    // Specify the destination image name
    String destImageName = "destImage";

    // Apply the imageSepia method
    double[][] sepiaMatrix = {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};
    processor.graySepia(sourceImageName, destImageName,
            "no-split", 0, sepiaMatrix);

    // Get the resulting sepia image
    Image sepiaImage = processor.getImage(destImageName);

    // Define the expected sepia values
    int[][] expectedPixels = {{135, 120, 93}, {67, 60, 46}};

    // Check if the actual sepia image matches the expected values
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] actualPixel = sepiaImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][0], expectedPixels[i][1], expectedPixels[i][2]};
        assertArrayEquals(expectedPixel, actualPixel);
      }
    }
  }

  /**
   * This test case checks the grayscale conversion of an image.
   */
  @Test
  public void testGrayscaleImage() {
    String sourceImageName = "sourceImage";
    int width = 2;
    int height = 2;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));
    String destImageName = "destImage";
    double[][] grayscaleMatrix = {
            {0.299, 0.587, 0.114},
            {0.299, 0.587, 0.114},
            {0.299, 0.587, 0.114}
    };
    processor.graySepia(sourceImageName, destImageName,
            "no-split", 0, grayscaleMatrix);
    Image destImage = processor.getImage(destImageName);
    int[][] expectedPixels = {{100, 150, 147}, {50, 75, 71}};

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j], expectedPixels[i][j], expectedPixels[i][j]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * This test case checks the red component functionality of an image.
   */
  @Test
  public void testCreateRedComponent() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int width = 3;
    int height = 3;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    processor.createColorComponent(sourceImageName, destImageName, 0);
    Image destImage = processor.getImage(destImageName);

    int[][] expectedPixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {0, expectedPixels[i][j], expectedPixels[i][j]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * This test case checks the green component functionality of an image.
   */
  @Test
  public void testCreateGreenComponent() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int width = 3;
    int height = 3;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    processor.createColorComponent(sourceImageName, destImageName, 1);
    Image destImage = processor.getImage(destImageName);

    int[][] expectedPixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j], 0, expectedPixels[i][j]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * This test case checks the blue component functionality of an image.
   */
  @Test
  public void testCreateBlueComponent() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int width = 3;
    int height = 3;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    processor.createColorComponent(sourceImageName, destImageName, 2);
    Image destImage = processor.getImage(destImageName);

    int[][] expectedPixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j], expectedPixels[i][j], 0};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * Test to check the sepia image split functionality.
   */
  @Test
  public void testSepiaWithSplit() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int width = 3;
    int height = 3;

    int[][] sourcePixels = {
            {100, 150, 200},
            {50, 75, 100},
            {25, 50, 75, 100},
            {175, 125, 100, 225}
    };

    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    double[][] sepiaMatrix = {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};

    processor.graySepia(sourceImageName, destImageName,
            "split", 50, sepiaMatrix);

    Image destImage = processor.getImage(destImageName);

    int[][] expectedPixels = {{135, 120, 93}, {67, 60, 46}, {33, 30, 23}};

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width - 2; j += 2) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j],
                expectedPixels[i][j + 1], expectedPixels[i][j + 2]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * Test to check the grey image split functionality.
   */
  @Test
  public void testGreyscaleWithSplit() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int width = 3;
    int height = 3;

    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};

    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    double[][] greyscaleMatrix = {
            {0.299, 0.587, 0.114},
            {0.299, 0.587, 0.114},
            {0.299, 0.587, 0.114}
    };

    processor.graySepia(sourceImageName, destImageName,
            "split", 50, greyscaleMatrix);

    Image destImage = processor.getImage(destImageName);

    int[][] expectedPixels = {{100, 100, 100}, {50, 50, 50}, {25, 25, 25}};

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width - 2; j += 2) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j],
                expectedPixels[i][j + 1], expectedPixels[i][j + 2]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * Test to check the blur image split functionality.
   */
  @Test
  public void testImageBlurSplit() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int width = 3;
    int height = 3;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));
    double[][] gaussianBlurKernel = {
            {1.0 / 16.0, 2.0 / 16.0, 1.0 / 16.0},
            {2.0 / 16.0, 4.0 / 16.0, 2.0 / 16.0},
            {1.0 / 16.0, 2.0 / 16.0, 1.0 / 16.0}
    };
    processor.applyConvolutionFilter(sourceImageName, destImageName, gaussianBlurKernel,
            "split", 50);
    Image destImage = processor.getImage(destImageName);
    int[][] expectedPixels = {{53, 150, 200}, {48, 75, 100}, {22, 50, 75}};
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j], expectedPixels[i][j], expectedPixels[i][j]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * Test to check sharpen image with split functionality.
   */
  @Test
  public void testImageSharpenSplit() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int width = 3;
    int height = 3;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}, {25, 50, 75}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    double[][] sharpeningFilter = {
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };
    processor.applyConvolutionFilter(sourceImageName, destImageName, sharpeningFilter,
            "split", 50);
    Image destImage = processor.getImage(destImageName);

    int[][] expectedPixels = {{108, 150, 200}, {100, 75, 100}, {0, 50, 75}};
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedPixel = {expectedPixels[i][j], expectedPixels[i][j], expectedPixels[i][j]};
        assertArrayEquals(expectedPixel, destPixel);
      }
    }
  }

  /**
   * Test the functionality to plot a histogram.
   */
  @Test
  public void testPlotHistogram() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int[][] sourcePixels = {
            {255, 0, 0}, {0, 255, 0}, {0, 0, 255}, {255, 255, 255}, {0, 0, 0}, {255, 0, 0},
            {0, 255, 0}, {0, 0, 255}, {255, 0, 255}, {0, 0, 0}, {255, 0, 0}, {0, 255, 0},
            {0, 255, 255}, {255, 0, 255}, {0, 0, 0}, {255, 0, 0}
    };
    int width = sourcePixels[0].length;
    int height = sourcePixels.length;
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    processor.plotHistogram(sourceImageName, destImageName);
    Image destImage = processor.getImage(destImageName);
    int[][][] expectedPixels = {
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}},
        {{0, 0, 255}, {255, 255, 255}, {255, 255, 255}}
    };

    int[][][] actualPixels = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        actualPixels[i][j] = destPixel;
      }
    }
    String expected = Arrays.deepToString(expectedPixels);
    String actual = Arrays.deepToString(actualPixels);
    assertEquals(expected, actual);
  }

  /**
   * Test the functionality to check color correction.
   */
  @Test
  public void testColorCorrection() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int[][] sourcePixels = {
            {255, 0, 0}, {0, 255, 0}, {0, 0, 255}, {255, 255, 255}, {0, 0, 0}, {255, 0, 0},
            {0, 255, 0}, {0, 0, 255}, {255, 0, 255}, {0, 0, 0}, {255, 0, 0}, {0, 255, 0},
            {0, 255, 255}, {255, 0, 255}, {0, 0, 0}, {255, 0, 0}
    };
    int width = sourcePixels[0].length;
    int height = sourcePixels.length;
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    processor.colorCorrectImage(sourceImageName, destImageName,
            "no-split", 0);
    Image destImage = processor.getImage(destImageName);

    int[][][] expectedPixels = {
            {{255, 255, 255}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{255, 255, 255}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}, {255, 255, 255}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{255, 255, 255}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}},
            {{0, 0, 0}, {255, 255, 255}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}, {255, 255, 255}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{255, 255, 255}, {0, 0, 0}, {0, 0, 0}}
    };
    int[][][] actualPixels = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        actualPixels[i][j] = destPixel;
      }
    }
    String expected = Arrays.deepToString(expectedPixels);
    String actual = Arrays.deepToString(actualPixels);
    assertEquals(expected, actual);
  }

  /**
   * Test the functionality to check level of adjustment.
   */
  @Test
  public void testLevelOfAdjustment() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int[][] sourcePixels = {
            {255, 0, 0}, {0, 255, 0}, {0, 0, 255}, {255, 255, 255}, {0, 0, 0}, {255, 0, 0},
            {0, 255, 0}, {0, 0, 255}, {255, 0, 255}, {0, 0, 0}, {255, 0, 0}, {0, 255, 0},
            {0, 255, 255}, {255, 0, 255}, {0, 0, 0}, {255, 0, 0}
    };
    int width = sourcePixels[0].length;
    int height = sourcePixels.length;
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    processor.adjustLevels(20, 100, 255, sourceImageName, destImageName,
            "no-split", 0);
    Image destImage = processor.getImage(destImageName);

    int[][][] expectedPixels = {
            {{255, 255, 255}, {0, 0, 0}, {0, 0, 0}}, {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}, {{255, 255, 255}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}}, {{0, 0, 0}, {0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}, {255, 255, 255}}, {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{255, 255, 255}, {0, 0, 0}, {0, 0, 0}}, {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}},
            {{0, 0, 0}, {255, 255, 255}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}, {255, 255, 255}}, {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{255, 255, 255}, {0, 0, 0}, {0, 0, 0}}
    };

    int[][][] actualPixels = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        actualPixels[i][j] = destPixel;
      }
    }
    String expected = Arrays.deepToString(expectedPixels);
    String actual = Arrays.deepToString(actualPixels);
    assertEquals(expected, actual);
  }

  /**
   * Test the functionality to compress a given image.
   */
  @Test
  public void testImageCompression() {
    String sourceImageName = "sourceImage";
    String destImageName = "destImage";
    int[][] sourcePixels = {
            {255, 0, 0}, {0, 255, 0}, {0, 0, 255}, {255, 255, 255}, {0, 0, 0}, {255, 0, 0},
            {0, 255, 0}, {0, 0, 255}, {255, 0, 255}, {0, 0, 0}, {255, 0, 0}, {0, 255, 0},
            {0, 255, 255}, {255, 0, 255}, {0, 0, 0}, {255, 0, 0}
    };
    int width = sourcePixels[0].length;
    int height = sourcePixels.length;
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));
    processor.compressImage(13.0, sourceImageName, destImageName);
    Image destImage = processor.getImage(destImageName);

    int[][][] expectedPixels = {
            {{255, 255, 255}, {0, 0, 0}, {0, 0, 0}}, {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}}, {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{255, 255, 255}, {0, 0, 0}, {0, 0, 0}}, {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {255, 255, 255}}, {{255, 255, 255}, {0, 0, 0}, {255, 255, 255}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}, {{255, 255, 255}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}}, {{0, 0, 0}, {255, 255, 255}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}, {255, 255, 255}}, {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{255, 255, 255}, {0, 0, 0}, {0, 0, 0}}
    };

    int[][][] actualPixels = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        actualPixels[i][j] = destPixel;
      }
    }
    String expected = Arrays.deepToString(expectedPixels);
    String actual = Arrays.deepToString(actualPixels);
    assertEquals(expected, actual);
  }


  /**
   * Test to check multiple operations on an image.
   */
  @Test
  public void testGrayscaleAndBrightenImage() {
    String sourceImageName = "sourceImage";
    int width = 2;
    int height = 2;
    int[][] sourcePixels = {{100, 150, 200}, {50, 75, 100}};
    processor.addImage(sourceImageName, createImageFromArray(width, height, sourcePixels));

    String grayscaleImageName = "grayscaleImage";
    double[][] grayscaleMatrix = {
            {0.299, 0.587, 0.114},
            {0.299, 0.587, 0.114},
            {0.299, 0.587, 0.114}
    };
    processor.graySepia(sourceImageName, grayscaleImageName,
            "no-split", 0, grayscaleMatrix);
    Image grayscaleImage = processor.getImage(grayscaleImageName);

    int[][] expectedGrayscalePixels = {{100, 150, 147}, {50, 75, 71}};
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] grayscalePixel = grayscaleImage.getImage(i, j);
        int[] expectedGrayscalePixel = {expectedGrayscalePixels[i][j],
                expectedGrayscalePixels[i][j], expectedGrayscalePixels[i][j]};
        assertArrayEquals(expectedGrayscalePixel, grayscalePixel);
      }
    }

    String destImageName = "destImage";
    processor.adjustBrightness(50, grayscaleImageName, destImageName);
    Image destImage = processor.getImage(destImageName);

    int[][] expectedBrightenPixels = {{150, 200, 255}, {100, 125, 150}};
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] destPixel = destImage.getImage(i, j);
        int[] expectedBrightenPixel = {expectedBrightenPixels[i][j],
                expectedBrightenPixels[i][j], expectedBrightenPixels[i][j]};
        assertArrayEquals(expectedBrightenPixel, destPixel);
      }
    }
  }

}






