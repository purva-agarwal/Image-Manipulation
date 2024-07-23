package model;

/**
 * The Image class represents a two-dimensional image with pixel information in RGB format.
 * It provides methods to set and retrieve pixel values, as well as other methods for pixel
 * manipulation.
 */
public class Image {

  private final int[][][] pixels;

  /**
   * Constructs an Image object with the specified height and width.
   *
   * @param height The height of the image.
   * @param width  The width of the image.
   */
  public Image(int height, int width) {
    pixels = new int[height][width][3];
  }

  /**
   * Sets the RGB values for a pixel at the specified position (x, y).
   *
   * @param x The x-coordinate of the pixel.
   * @param y The y-coordinate of the pixel.
   * @param r The red component value (0-255).
   * @param g The green component value (0-255).
   * @param b The blue component value (0-255).
   */
  public void setImage(int x, int y, int r, int g, int b) {
    if (x >= 0 && x < pixels.length && y >= 0 && y < pixels[0].length) {
      pixels[x][y][0] = r;
      pixels[x][y][1] = g;
      pixels[x][y][2] = b;
    }
  }

  /**
   * Retrieves the RGB values for a pixel at the specified position (x, y).
   *
   * @param x The x-coordinate of the pixel.
   * @param y The y-coordinate of the pixel.
   * @return An integer array representing the RGB values [r, g, b] of the pixel.
   */
  public int[] getImage(int x, int y) {
    if (x >= 0 && x < pixels.length && y >= 0 && y < pixels[0].length) {
      return pixels[x][y];
    } else {
      return null;
    }
  }

  /**
   * Gets the height of the image.
   *
   * @return The height of the image.
   */
  public int getHeight() {
    return pixels.length;
  }

  /**
   * Gets the width of the image.
   *
   * @return The width of the image.
   */
  public int getWidth() {
    return pixels[0].length;
  }

}