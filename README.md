# CS5010: Assignment 6
- Purva Sanjay Agarwal

IME: Image Manipulation and Enhancement
This README file provides an overview of the Image Processing code and explains its functionality and usage.

## Table of Contents

1. [Introduction](#introduction)
2. [Code Structure](#code-structure)
3. [Prerequisites](#prerequisites)
4. [Usage](#usage)
5. [Model](#model-imageprocessor)
6. [View](#view-imageviewer)
7. [Controller](#controller-imagecontroller)
8. [Testing and Validation](#testing-and-validation)
9. [Summary of New Updates](#summary-of-new-updates)
---

## 1. Introduction

The Image Processing is a Java program that allows users to perform various image processing operations on image files. It provides a Command Line Interface (CLI), Graphical User Interface (GUI) and a Script File Execution to load, manipulate, save images and also show the equivalent histogram of the image. The code is organized into a controller, model, and view to manage and process images efficiently.

## 2. Code Structure

The code consists of the following main components:

- **Model (ImageProcessor)**: The model class encompasses image processing functionalities, including the application of filters, generation of a corresponding image histogram, compression, and various transformations.
- **View (ImageViewer, GUIViewer)**: The view class is responsible for presenting user prompts and messages, facilitating interaction with the user through the command line for code execution using either the Command Line Interface (CLI) or Graphical User Interface (GUI), as well as the execution of script files.
- **Controller (ImageController)**: The controller class assumes the role of overseeing user interactions, managing commands, and orchestrating image processing operations. Additionally, it manages input/output (I/O) operations, including the loading and saving of images.

## 3. Prerequisites

- Java Development Kit (JDK) 8 or later


## 4. Usage

To use the Image Processing, follow these steps:

Go to \res folder of the Project and open the command prompt/powershell here.

#### 1. Script File Execution: 
Run the below command. When invoked, the program opens the script file, executes it and then shuts down. All the images will be saved inside the \res folder.

```bash
PS D:\Study\PDP\Assignment6\res> java -jar Assignment6.jar -file Input_Script.txt
```
#### 2. Command Line Interface:
Run the below command. When invoked, program opens in an interactive text mode, allowing the user to type the script/command and execute it one line at a time. All the images will be saved inside the \res folder.
```bash
PS D:\Study\PDP\Assignment6\res> java -jar Assignment6.jar -text
```
#### 3. Graphical User Interface:
Run the below command. When invoked, program opens the graphical user interface. You can also run this by just double-clicking on the jar file.
```bash
PS D:\Study\PDP\Assignment6\res> java -jar Assignment6.jar
```   

## 5. Model

The `ImageOperations` class implements the `ImageProcessor`, is the model component that encapsulates image processing operations such as applying filters, and transformations, compression and creating histogram of the image. Makes use of the `Image` Class to get the `height`, `width`, `rgb` values of the image.
#### Image Operations

The `ImageOperations` class is part of a Java application that performs various operations on images. This class is responsible for image manipulation and provides methods to perform tasks such as flipping, splitting, combining, modifying brightness, applying effects, color-correction, level adjustment, compression, creating histogram and more.

#### Features

- **Horizontal Flip**: Flips the source image horizontally and saves it as the destination image.
- **Vertical Flip**: Flips the source image vertically and saves it as the destination image.
- **RGB Split**: Splits the source image into its red, green, and blue channels and saves them as separate images.
- **RGB Combine**: Combines separate red, green, and blue channel images into a single color image.
- **Create Red Component**: Creates a new image by keeping only the red component of the source image.
- **Create Green Component**: Creates a new image by keeping only the green component of the source image.
- **Create Blue Component**: Creates a new image by keeping only the blue component of the source image.
- **Image Brighten**: Brightens the source image and saves it as the destination image.
- **Image Sepia**: Applies a sepia tone effect to the source image and saves it as the destination image.
- **Grayscale Image**: Converts the source image to grayscale and saves it as the destination image.
- **Image Sharpen**: Applies a sharpening filter to the source image and saves it as the destination image.
- **Image Darker**: Darkens the source image and saves it as the destination image.
- **Image Blur**: Applies a blur effect to the source image and saves it as the destination image.
- **Create Value Component**: Creates a new image by keeping only the value component of the source image.
- **Create Intensity Component**: Creates a new image by keeping only the intensity component of the source image.
- **Create Luma Component**: Creates a new image by keeping only the luma component of the source image.
- **Image Compression**: Supports creating a compressed version of an image.
- **Histogram Generation**: Generates an image representing the histograms for the red, green, and blue channels of a given image.
- **Color Correction**: Aligns meaningful peaks of the histogram to color-correct an image.
- **Adjust Levels**: Allows adjusting the black, mid, and white values of an image.
- **Split View Operations**: Supports applying various operations (blur, sharpen, sepia, greyscale, color correction, and levels adjustment) to specific percentages of an image, creating split views.

## 6. View

#### 1. ImageViewer
The `ImageViewer` implements the `ImageView` class is responsible for providing a text-based user interface for displaying messages and options to the user. It implements the `ImageView` interface and is a crucial component for interacting with the Image Processing Controller. Here are the key features and methods of the `ImageViewer` class:

- **Constructor**: The class is constructed with a specified `PrintStream` for output, allowing messages to be directed to the console.

- **Displaying Messages**: The `showString(String s)` method displays a string message to the user.

- **Displaying Options**: The `showOptions()` method displays the available options to the user.

- **Prompting for Script File**: The `showScriptFileEntry()` method prompts the user to enter a script file name.

- **Prompting for Commands**: The `showCommandEntry()` method prompts the user to enter commands or type 'exit' to quit.

- **Error Handling**: The class includes methods for displaying error messages. The `showOptionError()` method informs the user of an invalid option, while the `showErrorMessage(String errorMessage)` method displays a custom error message.

#### 2. GUIViewer
The `GUIViewer` class is responsible for providing the graphical user interface to the user to perform image operations. It interacts with the Image Processing Controller to invoke the various processing commands. Here are the key features and methods of the `GUIViewer` class where te user works on one image at a time which is visible in the UI:
- **Display Image & Histogram**: SHow the user the image on the left and its equivalent histgram on the right.
- **Load**: Select an image in PPM/JPG/JPEG/PNG formats to be loaded. 
- **Save**: Save the currently visible image in PPM/JPG/JPEG/PNG formats.
 If the currently shown image is not saved, the program shows the status to the user accordingly.
- **RGB Component**: Visualize the red/green/blue components of an image.
- **Flip**: The image is flipped vertically or horizontally.
- **Blur/Sharpen**: Blurring/sharpening the image. Show a Preview of the operation using the split functionality where the user enters a split percentage.
- **Luma**: Converting the image to greyscale using luma. 
- **Greyscale/Sepia**: Converting the image to greyscale/sepia. Show a Preview of the operation using the split functionality where the user enters a split percentage.
- **Compression**: View the image with compression artifacts. The user enters the compression factor.
- **Color-correction**: Viewing the color-corrected version of this image. Show a Preview of the operation using the split functionality where the user enters a split percentage.
- **Adjust levels**: The user enters the black, white and mid-values and the GUI shows the updated image. Show a Preview of the operation using the split functionality where the user enters a split percentage. 

## 7. Controller 

The `ImageController` class implements the `ImageControllerInterface`, is responsible for managing user interactions, handling commands, and coordinating image processing operations. It follows a Command Design Pattern.

#### Features

- **Command Line Interface (CLI)**: Users can enter commands to perform operations such as loading, saving, and manipulating images through the terminal.
- **Graphical User Interface (GUI)**: The program provides a graphical interface for users to interact with images, displaying them and executing commands through buttons and menus.
- **Script Execution**: Users can create and run scripts containing a series of image processing commands, automating complex operations.

#### I/O Operations
- **Image Loading**: Supports loading images from various formats, including PPM and standard image file formats (PNG, JPEG, JPG, PPM). Stores the image instance in a hashmap.
- **Save**: Saves the source image in various supported formats (PPM, PNG, JPEG, JPG). 

## 8. Testing and Validation

#### 1. Script File Format

You can automate image processing by creating script files. Script files contain a sequence of commands, with one command per line. Lines starting with "#" are considered comments and are ignored. The controller reads and executes each command from the script file.

#### 2. Commands

The Image Processing Controller supports the following commands:

- `load <imagePath> <imageName>`: Load and process an image from the specified path. The `imageName` is used as the identifier for further operations on this image.

- `save <imageName> <outputPath>`: Save the processed image with the given `imageName` to the specified `outputPath`.

- Various image processing commands, such as:
    - `red-component <sourceImageName> <destinationImageName>`
    - `green-component <sourceImageName> <destinationImageName>`
    - `blue-component <sourceImageName> <destinationImageName>`
    - `value-component <sourceImageName> <destinationImageName>`
    - `luma-component <sourceImageName> <destinationImageName>`
    - `intensity-component <sourceImageName> <destinationImageName>`
    - `horizontal-flip <sourceImageName> <destinationImageName>`
    - `vertical-flip <sourceImageName> <destinationImageName>`
    - `brighten <brightnessChange> <sourceImageName> <destinationImageName>`
    - `rgb-split <sourceImageName> <destRedImageName> <destGreenImageName> <destBlueImageName>`
    - `rgb-combine <destinationImageName> <sourceRedImageName> <sourceBlueImageName> <sourceGreenImageName>`
    - `blur <sourceImageName> <destinationImageName>`
    - `sharpen <sourceImageName> <destinationImageName>`
    - `sepia <sourceImageName> <destinationImageName>`
    - `greyscale <sourceImageName> <destinationImageName>`
    - `color-correct <sourceImageName> <destinationImageName>`
    - `levels-adjust <b> <m> <w> <sourceImageName> <destinationImageName>`
    - `compress <compressPercentage> <sourceImageName> <destinationImageName>`
    - `histogram <sourceImageName> <destinationImageName>`
    - `Split Functionality is added for blur, sharpen, greyscale, sepia, color-correct, levels-adjust. Add "<split> <splitPercentage>" to the above mentioned commands.`
    - `-file <InputFileName>`

## 9. Summary of New Updates 
1. Project runs using the .jar in three ways: CLI, GUI, Script File Execution. If project executed throught Main Class, it runs in GUI Mode.
2. The Controller is updated to support GUI View. Action listeners for various buttons are added.
3. New GUIViewer class is created to run the program in GUI Mode 
---
###### Testing Image Reference: [Image Processing in Java - CSE 143 Project 5](https://courses.cs.washington.edu/courses/cse143/04su/projects/p5/p5.html)

