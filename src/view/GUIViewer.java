package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


/**
 * GUIViewer class represents an Image Viewer and UI functionalities. It extends JFrame to manage
 * the graphical user interface. It provides methods to handle image loading, processing, and
 * display.
 */
public class GUIViewer extends JFrame {

  private final ActionListener loadAction;
  private final ActionListener histogramAction;
  private final ActionListener redAction;
  private final ActionListener greenAction;
  private final ActionListener blueAction;
  private final ActionListener blurAction;
  private final ActionListener brightenAction;
  private final ActionListener colorCorrectAction;
  private final ActionListener greyAction;
  private final ActionListener sepiaAction;
  private final ActionListener compressAction;
  private final ActionListener saveAction;
  private final ActionListener lumaAction;
  private final ActionListener horizontalFlipAction;
  private final ActionListener verticalFlipAction;
  private final ActionListener sharpenAction;
  private final ActionListener adjustLevelsAction;
  protected JTextField sharpeAdjustLevelsPercentage;
  private JTextField compressionValueField;
  private JTextField blurSplitPercentage;
  private JTextField sepiaSplitPercentage;
  private JTextField greySplitPercentage;
  private JTextField sharpeSplitPercentage;
  private JTextField colorCorrectSplitPercentage;
  private JTextField brightnessValue;
  private JPanel imagePanel;
  private JPanel histogramPanel;
  private JTextField shadow;
  private JTextField mid;
  private JTextField highlight;
  private String destImage;
  private String imagePath;
  private JLabel saveStatus;

  /**
   * Constructs a GUIViewer with specified action listeners for various image processing
   * operations.
   */

  public GUIViewer(ActionListener loadAction, ActionListener histogramAction,
      ActionListener redAction, ActionListener greenAction, ActionListener blueAction,
      ActionListener lumaAction, ActionListener horizontalFlipAction,
      ActionListener verticalFlipAction, ActionListener compressAction, ActionListener blurAction,
      ActionListener sharpenAction, ActionListener brightenAction,
      ActionListener colorCorrectAction, ActionListener adjustLevelsAction,
      ActionListener greyAction, ActionListener sepiaAction, ActionListener saveAction) {
    this.loadAction = loadAction;
    this.histogramAction = histogramAction;
    this.redAction = redAction;
    this.greenAction = greenAction;
    this.blueAction = blueAction;
    this.lumaAction = lumaAction;
    this.horizontalFlipAction = horizontalFlipAction;
    this.verticalFlipAction = verticalFlipAction;
    this.brightenAction = brightenAction;
    this.blurAction = blurAction;
    this.sharpenAction = sharpenAction;
    this.colorCorrectAction = colorCorrectAction;
    this.adjustLevelsAction = adjustLevelsAction;
    this.greyAction = greyAction;
    this.compressAction = compressAction;
    this.sepiaAction = sepiaAction;
    this.saveAction = saveAction;
    this.destImage = destImage;
    setTitle("Image Viewer");
    initializeUI();
  }


  /**
   * Initializes the user interface for an image viewer application. Configures the main frame,
   * panels, buttons, and UI components. Sets up image display, histograms, and image operation
   * controls. Manages layout, dimensions, and action listeners for UI elements.
   */
  private void initializeUI() {
    JFrame frame = new JFrame("ImageUI");
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();
    int frameWidth = (int) (screenSize.getWidth() * 0.8);
    int frameHeight = (int) (screenSize.getHeight() * 0.8);
    frame.setSize(frameWidth, frameHeight);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    JScrollPane mainScrollPane = new JScrollPane(panel);
    frame.add(mainScrollPane);

    JPanel contentPanel = new JPanel(new GridLayout(1, 2, 10, 10));
    JButton loadButton = createButton("Load Image", this::handleLoadAction);
    JButton saveButton = createButton("Save Image", this::handleSaveAction);
    JButton redButton = createButton("Red Component of Image", this::handleRedAction);
    JButton greenButton = createButton("Green Component of Image", this::handleGreenAction);
    JButton blueButton = createButton("Blue Component of Image", this::handleBlueAction);
    JButton blurButton = createButton("Blur Image", this::handleBlurAction);
    JButton brightenButton = createButton("Brighten Image", this::handleBrightenAction);
    JButton colorCorrectButton = createButton("Color Correct Image",
        this::handleColorCorrectAction);
    JButton compressButton = createButton("Compress Image", this::handleCompressImage);
    JButton greyButton = createButton("Grey Image", this::handleGreyAction);
    JButton sepiaButton = createButton("Sepia Image", this::handleSepiaAction);
    JButton lumaButton = createButton("Luma Component of Image", this::handleLumaAction);
    JButton horizontalFlipButton = createButton("Horizontal Flip Image",
        this::handleHorizontalFlipAction);
    JButton verticalFlipButton = createButton("Vertical Flip Image",
        this::handleVerticalFlipAction);
    JButton sharpenButton = createButton("Sharpen Image", this::handleSharpenAction);
    JButton adjustLevelsButton = createButton("Level Adjusted Image",
        this::handleAdjustLevelAction);

    imagePanel = createTitledBorderPanel(contentPanel, "Image", new Dimension(400, 600), 1, 0, 10,
        10);
    histogramPanel = createTitledBorderPanel(contentPanel, "Histogram", new Dimension(400, 600), 0,
        1, 10, 10);

    panel.add(contentPanel, BorderLayout.CENTER);

    JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
    buttonsPanel.setBorder(BorderFactory.createTitledBorder("Image Operations"));
    panel.add(buttonsPanel, BorderLayout.SOUTH);

    JPanel ioPanel = new JPanel();
    ioPanel.setLayout(new GridLayout(1, 1, 10, 10));
    JPanel loadSavePanel = new JPanel(new GridLayout(7, 3, 10, 10));

    saveStatus = new JLabel("Image Status : Unsaved");
    loadSavePanel.add(saveStatus);
    loadSavePanel.add(loadButton);
    loadSavePanel.add(saveButton);
    buttonsPanel.add(ioPanel);
    ioPanel.add(loadSavePanel);
    loadSavePanel.add(brightenButton);

    addLabelToPanel(loadSavePanel, "Brightness Change: ");
    brightnessValue = addTextField(loadSavePanel, 5);

    loadSavePanel.add(compressButton);
    addLabelToPanel(loadSavePanel, "Compress Percentage: ");
    compressionValueField = addTextField(loadSavePanel, 5);

    loadSavePanel.add(adjustLevelsButton);
    addLabelToPanel(loadSavePanel, "Level - Adjust Split Percentage: ");
    sharpeAdjustLevelsPercentage = addTextField(loadSavePanel, 5);

    addLabelToPanel(loadSavePanel, "");
    addLabelToPanel(loadSavePanel, "Shadow: ");
    shadow = addTextField(loadSavePanel, 5);

    addLabelToPanel(loadSavePanel, "");
    addLabelToPanel(loadSavePanel, "Mid: ");
    mid = addTextField(loadSavePanel, 5);

    addLabelToPanel(loadSavePanel, "");
    addLabelToPanel(loadSavePanel, "Highlight: ");
    highlight = addTextField(loadSavePanel, 5);

    JPanel operationsPanel = new JPanel();
    operationsPanel.setLayout(new GridLayout(1, 1, 10, 10));
    JPanel buttonsLinePanel = new JPanel(new GridLayout(7, 3, 10, 10));
    addButtonsToPanel(buttonsLinePanel, redButton, greenButton, blueButton, lumaButton,
        horizontalFlipButton, verticalFlipButton);

    buttonsLinePanel.add(blurButton);
    addLabelToPanel(buttonsLinePanel, "Blur Split Percentage: ");
    blurSplitPercentage = addTextField(buttonsLinePanel, 5);
    buttonsLinePanel.add(colorCorrectButton);
    addLabelToPanel(buttonsLinePanel, "Color Correct Split Percentage: ");
    colorCorrectSplitPercentage = addTextField(buttonsLinePanel, 5);

    buttonsLinePanel.add(greyButton);
    addLabelToPanel(buttonsLinePanel, "Grey Split Percentage: ");
    greySplitPercentage = addTextField(buttonsLinePanel, 5);

    buttonsLinePanel.add(sepiaButton);
    addLabelToPanel(buttonsLinePanel, "Split Sepia Percentage: ");
    sepiaSplitPercentage = addTextField(buttonsLinePanel, 5);

    buttonsLinePanel.add(sharpenButton);
    addLabelToPanel(buttonsLinePanel, "Sharpen Split Percentage: ");
    sharpeSplitPercentage = addTextField(buttonsLinePanel, 5);

    operationsPanel.add(buttonsLinePanel);

    buttonsPanel.add(operationsPanel);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setVisible(true);
    pack();
  }


  /**
   * Adds multiple buttons to a panel.
   *
   * @param panel   The panel to which buttons are added.
   * @param buttons Variable number of JButton objects to be added to the panel.
   */
  private void addButtonsToPanel(JPanel panel, JButton... buttons) {
    for (JButton button : buttons) {
      panel.add(button);
    }
  }

  /**
   * Creates a JPanel with a titled border, specified dimensions, and layout parameters, then adds
   * it to a parent container.
   *
   * @param parent        The parent container to which the created panel will be added.
   * @param title         The title for the titled border of the panel.
   * @param preferredSize The preferred size (Dimension) of the created panel.
   * @param rows          The number of rows in the GridLayout for the panel.
   * @param cols          The number of columns in the GridLayout for the panel.
   * @param hgap          The horizontal gap between components in the GridLayout.
   * @param vgap          The vertical gap between components in the GridLayout.
   * @return The created JPanel with the titled border, specified dimensions, and layout.
   */
  private JPanel createTitledBorderPanel(Container parent, String title, Dimension preferredSize,
      int rows, int cols, int hgap, int vgap) {
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createTitledBorder(title));
    panel.setPreferredSize(preferredSize);
    panel.setLayout(new GridLayout(rows, cols, hgap, vgap));
    parent.add(panel);
    return panel;
  }

  /**
   * Adds a JLabel with the provided text to a specified JPanel.
   *
   * @param panel     The JPanel where the JLabel will be added.
   * @param labelText The text content for the JLabel.
   */
  private void addLabelToPanel(JPanel panel, String labelText) {
    JLabel label = new JLabel(labelText);
    panel.add(label);
  }

  /**
   * Adds a JTextField with a specified number of columns to a given JPanel.
   *
   * @param panel   The JPanel where the JTextField will be added.
   * @param columns The number of columns for the JTextField.
   * @return The created JTextField added to the panel.
   */
  private JTextField addTextField(JPanel panel, int columns) {
    JTextField textField = new JTextField(columns);
    panel.add(textField);
    return textField;
  }

  /**
   * Handles the compression of an image triggered by an action event.
   *
   * @param actionEvent The ActionEvent triggering the compression.
   */
  private void handleCompressImage(ActionEvent actionEvent) {
    String compressionValue = compressionValueField.getText();
    compressAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
        "compress " + compressionValue + " " + destImage + " compress"));
    resetSaveStatus();

    compressionValueField.setText("");
  }

  /**
   * Creates a JButton with a specified label and associates an ActionListener.
   *
   * @param label          The label to display on the button.
   * @param actionListener The ActionListener to associate with the button.
   * @return The created JButton with the specified label and ActionListener.
   */
  private JButton createButton(String label, ActionListener actionListener) {
    JButton button = new JButton(label);
    button.addActionListener(actionListener);
    return button;
  }

  /**
   * Handles the action when the "Load Image" button is triggered. Opens a file chooser dialog for
   * selecting an image file. Invokes the load action on selecting an image file and resets the save
   * status.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleLoadAction(ActionEvent e) {
    JFileChooser fileChooser = new JFileChooser();
    int option = fileChooser.showOpenDialog(this);
    if (option == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      imagePath = file.getAbsolutePath();
      loadAction.actionPerformed(
          new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "load " + imagePath + " load"));
      resetSaveStatus();
    }
  }

  /**
   * Handles the action to extract the blue component from the image. Invokes the blue action and
   * resets the save status.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleBlueAction(ActionEvent e) {
    blueAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
        "blue-component " + destImage + " blue-component"));
    resetSaveStatus();
  }

  /**
   * Handles the action to extract the green component from the image. Invokes the green action and
   * resets the save status.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleGreenAction(ActionEvent e) {
    greenAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
        "green-component " + destImage + " green-component"));
    resetSaveStatus();
  }

  /**
   * Handles the action to extract the red component from the image. Invokes the red action and
   * resets the save status.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleRedAction(ActionEvent e) {
    redAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
        "red-component " + destImage + " red-component"));
    resetSaveStatus();
  }

  /**
   * Handles the action to apply blur effect on the image. Invokes the blur action based on the
   * split value and resets the save status.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleBlurAction(ActionEvent e) {
    String splitValue = blurSplitPercentage.getText();
    if (splitValue.equals("0") || splitValue.isBlank()) {
      blurAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
          "blur" + "|" + destImage + "|" + "blur" + "|" + splitValue));
      resetSaveStatus();
    } else {
      blurAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
          "blur" + "|" + destImage + "|" + "split" + "|" + splitValue));
      resetSaveStatus();
    }
    blurSplitPercentage.setText("");

  }


  /**
   * Handles the action to brighten the image. Retrieves the brightness value, performs the brighten
   * action, and resets the save status.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleBrightenAction(ActionEvent e) {
    String brightness = brightnessValue.getText();
    brightenAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
        "brighten " + brightness + " " + destImage + " brighten"));
    resetSaveStatus();

    brightnessValue.setText("");
  }

  /**
   * Handles the action to apply color correction to the image. Retrieves the split value and
   * performs the color correction action accordingly, resetting the save status afterward.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleColorCorrectAction(ActionEvent e) {
    String splitValue = colorCorrectSplitPercentage.getText();
    if (splitValue.equals("0") || splitValue.isBlank()) {
      colorCorrectAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
          "color-correct" + "|" + destImage + "|" + "color-correct" + "|" + splitValue));
      resetSaveStatus();
    } else {
      colorCorrectAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
          "color-correct" + "|" + destImage + "|" + "split" + "|" + splitValue));
      resetSaveStatus();
    }

    colorCorrectSplitPercentage.setText("");
  }

  /**
   * Handles the action to convert the image to greyscale. Retrieves the split value, performs the
   * greyscale action, and resets the save status.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleGreyAction(ActionEvent e) {
    String splitValue = greySplitPercentage.getText();
    if (splitValue.equals("0") || splitValue.isBlank()) {
      greyAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
          "greyscale" + "|" + destImage + "|" + "greyscale" + "|" + splitValue));
      resetSaveStatus();
    } else {
      greyAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
          "greyscale" + "|" + destImage + "|" + "split" + "|" + splitValue));
      resetSaveStatus();
    }

    greySplitPercentage.setText("");

  }

  /**
   * Handles the action to apply sepia effect to the image. Retrieves the split value, performs the
   * sepia action, and resets the save status.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleSepiaAction(ActionEvent e) {
    String splitValue = sepiaSplitPercentage.getText();
    if (splitValue.equals("0") || splitValue.isBlank()) {
      sepiaAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
          "sepia" + "|" + destImage + "|" + "sepia" + "|" + splitValue));
      resetSaveStatus();
    } else {
      sepiaAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
          "sepia" + "|" + destImage + "|" + "split" + "|" + splitValue));
      resetSaveStatus();
    }

    sepiaSplitPercentage.setText("");
  }

  /**
   * Resets the save status label to 'Image Status : Unsaved'.
   */
  private void resetSaveStatus() {
    saveStatus.setText("Image Status : Unsaved");
  }


  /**
   * Handles the action to save the image. Shows a file chooser dialog to select the destination to
   * save the image. Checks for a valid file format Performs the save action and updates the save
   * status accordingly.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleSaveAction(ActionEvent e) {
    JFileChooser fileChooser = new JFileChooser();
    int option = fileChooser.showSaveDialog(this);
    if (option == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      imagePath = file.getAbsolutePath();
      String extension = imagePath.substring(imagePath.lastIndexOf(".") + 1);
      if (!extension.equalsIgnoreCase("ppm") && !extension.equalsIgnoreCase("jpeg")
          && !extension.equalsIgnoreCase("png") && !extension.equalsIgnoreCase("jpg")) {
        displayErrorMessage("Invalid file format. Please save as .ppm, .jpg, .jpeg, or .png.");
        return;
      }
      saveAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
          "save " + imagePath + " " + destImage));
      saveStatus.setText("Image Status : Saved");
    } else if (option == JFileChooser.CANCEL_OPTION || option == JFileChooser.ERROR_OPTION) {
      resetSaveStatus();
    }
  }

  /**
   * Displays the provided image within the UI. Clears the existing content in the image panel and
   * sets the new image with appropriate dimensions.
   *
   * @param image         The BufferedImage to display.
   * @param destImageName The name of the destination image.
   */
  public void displayImage(BufferedImage image, String destImageName) {
    if (image != null) {
      imagePanel.removeAll();
      ImageIcon icon = new ImageIcon(image);
      JLabel imageLabel = new JLabel();

      JScrollPane imageScrollPane = new JScrollPane();
      imageLabel = new JLabel();
      imagePanel.add(imageLabel);
      imageLabel.setIcon(icon);
      imageScrollPane = new JScrollPane(imageLabel);
      imageScrollPane.setPreferredSize(new Dimension(100, 600));
      imagePanel.add(imageScrollPane);
      imagePanel.revalidate();
      imagePanel.repaint();
      if (!destImageName.equals("split")) {
        destImage = destImageName;
        histogramAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
            "histogram " + destImage + " histogramImage"));
      }
    }
  }

  /**
   * Displays the histogram of the provided image within the histogram panel. Clears the existing
   * content in the histogram panel and sets the new histogram with appropriate dimensions.
   *
   * @param image The BufferedImage of the histogram to display.
   */
  public void displayHistogram(BufferedImage image) {
    if (image != null) {
      histogramPanel.removeAll();
      ImageIcon icon = new ImageIcon(image);
      JLabel histogramLabel = new JLabel();
      JScrollPane histogramScrollPane = new JScrollPane();
      histogramLabel = new JLabel();
      histogramPanel.add(histogramLabel);
      histogramLabel.setIcon(icon);
      histogramScrollPane = new JScrollPane(histogramLabel);
      histogramScrollPane.setPreferredSize(new Dimension(100, 600));
      histogramPanel.add(histogramScrollPane);
      histogramPanel.revalidate();
      histogramPanel.repaint();
    }
  }

  /**
   * Handles the action to perform the luma component operation on the image. Checks if the image is
   * loaded and performs the luma action if loaded. Resets the save status afterward.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleLumaAction(ActionEvent e) {
    lumaAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
        "luma-component " + destImage + " luma-component"));
    resetSaveStatus();
  }


  /**
   * Handles the action to calculate the luma component of the image. Performs the luma component
   * action and resets the save status.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleHorizontalFlipAction(ActionEvent e) {
    horizontalFlipAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
        "horizontal-flip " + destImage + " horizontal-flip"));
    resetSaveStatus();
  }

  /**
   * Handles the action to perform a vertical flip on the image. Initiates the vertical flip action
   * and resets the save status.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleVerticalFlipAction(ActionEvent e) {
    verticalFlipAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
        "vertical-flip " + destImage + " vertical-flip"));
    resetSaveStatus();
  }

  /**
   * Handles the action to sharpen the image. Considers split values, if provided, to perform a
   * partial sharpening action. Resets the save status after execution.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleSharpenAction(ActionEvent e) {
    String splitValue = sharpeSplitPercentage.getText();
    if (splitValue.equals("0") || splitValue.isBlank()) {
      sharpenAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
          "sharpen" + "|" + destImage + "|" + "sharpen" + "|" + splitValue));
      resetSaveStatus();
    } else {
      sharpenAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
          "sharpen" + "|" + destImage + "|" + "split" + "|" + splitValue));
      resetSaveStatus();
    }

    sharpeSplitPercentage.setText("");
  }

  /**
   * Handles the action to adjust levels of the image. Utilizes shadow, mid, and highlight values
   * along with a split percentage for partial adjustments if provided. Resets the save status after
   * execution.
   *
   * @param e The ActionEvent associated with the button trigger.
   */
  private void handleAdjustLevelAction(ActionEvent e) {
    String splitValue = sharpeAdjustLevelsPercentage.getText();
    String b = shadow.getText();
    String m = mid.getText();
    String w = highlight.getText();
    if (splitValue.equals("0") || splitValue.isBlank()) {
      adjustLevelsAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
          "levels-adjust" + " " + b + " " + m + " " + w + " " + destImage + " " + "levels-adjust"
              + " " + splitValue));
      resetSaveStatus();
    } else {
      adjustLevelsAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
          "levels-adjust" + " " + b + " " + m + " " + w + " " + destImage + " " + "split" + " "
              + splitValue));
      resetSaveStatus();
    }
    sharpeAdjustLevelsPercentage.setText("");
    shadow.setText("");
    mid.setText("");
    highlight.setText("");
  }

  /**
   * Displays an error message dialog box with the provided error message.
   *
   * @param errorMessage The error message to display.
   */
  public void displayErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
  }
}