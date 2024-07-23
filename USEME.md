# Image Processing Application - USEME 
- Purva Sanjay Agarwal - 002696945
- Deep Rahul Shah - 002222660

## USAGE
Go to \res folder of the Project and open the command prompt/powershell in that folder.
## How to Use Script File Execution:
Run the below command. When invoked, the program opens the script file, executes it and then shuts down. All the images will be saved inside the \res folder.

```bash
PS D:\Study\PDP\Assignment6\res> java -jar Assignment6.jar -file Input_Script.txt
```

## How to Use Command Line Interface:
Run the below command. When invoked, program opens in an interactive text mode, allowing the user to type the script/command and execute it one line at a time. All the images will be saved inside the \res folder.
```bash
PS D:\Study\PDP\Assignment6\res> java -jar Assignment6.jar -text
```
- `load <imagePath> <imageName>`
- `save <imageName> <outputPath>`
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


## How to Use GUI Mode

Run the below command. When invoked, program opens the graphical user interface. You can also run this by just double-clicking on the jar file.
```bash
PS D:\Study\PDP\Assignment6\res> java -jar Assignment6.jar
```   

You can also Run the Main code and it will open in GUIMode by defalut

At all times, the histogram of the visible image on the left will be visible on the right side. Histogram will not be updated if Split Preview is applied.

### 1. Load Image
Description: Loads an image from the specified path/file. Image is visible in the left side of the GUI.
1. Click on `Load Image` Button which opens the File Browser.
2. Navigate to the image to be loaded and click `Open` button.
3. If there are any errors while loading, the user will be prompted and asked to load the correct image.

### 2. Save Image
Description: Saves the currently visible image to the specified path/file. Before Saving, `Image Satus: ` will be `Unsaved`.
1. Click on `Save` Button which opens the File Browser.
2. Navigate to the path where the image needs to be saved and enter the `filename.extenstion`. Click on `Save` button.
3. If there are any errors while saving, the user will be prompted and asked to load the correct image.
4. Once the Image is saved, `Image Status: ` will be updated to `Saved`.

### 3. Brighten Image
Description: Increases/Decreases brightness of the input image by the specified units.
1. Enter the `Brightness Change` Percentage in the text box.
2. Click on `Brighten Image` button.
3. Current Image will be updated.
4. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

### 4. Compress Image
Description: Compresses the image by the specified units.
1. Enter the `Compression Percentage` Change in the text box.
2. Click on `Compress Image` button.
3. Current Image will be updated.
4. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

### 5. Horizontal Flip
Description: Creates a horizontally flipped version of the input image.
1. Click on `Horizontal Flip Image`.
2. Current Image will be updated.
3. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

### 6. Vertical Flip
Description: Creates a vertically flipped version of the input image.
1. Click on `Vertical Flip Image`.
2. Current Image will be updated.
3. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

### 7. Red Component Removal
Description: Creates a version of the input image without the red values.
1. Click on `Red Component of Image`.
2. Current Image will be updated.
3. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

### 8. Green Component Removal
Description: Creates a version of the input image without the green values.
1. Click on `Green Component of Image`.
2. Current Image will be updated.
3. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

### 9. Blue Component Removal
Description: Creates a version of the input image without the blue values.
1. Click on `Blue Component of Image`.
2. Current Image will be updated.
3. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

### 10. Luma Component Extraction
Description: Creates a greyscale version of the input image.
1. Click on `Luma Component of Image`.
2. Current Image will be updated.
3. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

### 11. Blur Image
Description: Blurs the entire image. A preview of applying this filter will be visible if the split percentage is entered. 
1. Click on `Blur Image` button.
2. Current Image will be updated.
3. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

- #### Using the Split Functionality
1. Enter the `Blur Split Percentage` in the text box.
2. Click on `Blur Image` button.
3. Current image will be split, with filter applied to the left and original image to the right. This is the preview of the blurred image. 
4. If you want to apply the filter to the entire image, click on `Blur Image` button
5. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

### 12. Color-Correct Image
Description: Color correct is applied the entire image. A preview of applying this filter will be visible if the split percentage is entered. 
1. Click on `Color Correct Image` button.
2. Current Image will be updated.
3. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

- #### Using the Split Functionality
1. Enter the `Color Correct Split Percentage` in the text box.
2. Click on `Color Correct Image` button.
3. Current image will be split, with filter applied to the left and original image to the right. This is the preview of the color-corrected image. 
4. If you want to apply the filter to the entire image, click on `Color Correct Image` button
5. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

### 13. Grey Image
Description: Greys out the entire image. A preview of applying this filter will be visible if the split percentage is entered. 
1. Click on `Grey Image` button.
2. Current Image will be updated.
3. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

- #### Using the Split Functionality
1. Enter the `Grey Split Percentage` in the text box.
2. Click on `Grey Image` button.
3. Current image will be split, with filter applied to the left and original image to the right. This is the preview of the grey image. 
4. If you want to apply the filter to the entire image, click on `Blur Image` button
5. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

### 14. Sepia Image
Description: Applies sepia filter to the entire image. A preview of applying this filter will be visible if the split percentage is entered. 
1. Click on `Sepia Image` button.
2. Current Image will be updated.
3. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

- #### Using the Split Functionality
1. Enter the `Sepia Split Percentage` in the text box.
2. Click on `Sepia Image` button.
3. Current image will be split, with filter applied to the left and original image to the right. This is the preview of the sepia image. 
4. If you want to apply the filter to the entire image, click on `Sepia Image` button
5. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

### 15. Sharpen Image
Description: Sharpens the entire image. A preview of applying this filter will be visible if the split percentage is entered. 
1. Click on `Sharpen Image` button.
2. Current Image will be updated.
3. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

- #### Using the Split Functionality
1. Enter the `Sharpen Split Percentage` in the text box.
2. Click on `Sharpen Image` button.
3. Current image will be split, with filter applied to the left and original image to the right. This is the preview of the sharpened image. 
4. If you want to apply the filter to the entire image, click on `Sharpen Image` button
5. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

### 16. Level Adjusted Image
Description: Adjusts the RGB levels of the entire image. A preview of applying this filter will be visible if the split percentage is entered. 
1. Enter the `Shadow`, `Mid`, `Highlight` values.
2. Click on `Level Adjusted Image` button.
3. Current Image will be updated.
4. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

- #### Using the Split Functionality
1. Enter the `Sharpen Split Percentage` in the text box.
2. Enter the `Shadow`, `Mid`, `Highlight` values.
3. Click on `Level Adjusted Image` button.
4. Current image will be split, with filter applied to the left and original image to the right. This is the preview of the level-adjusted image. 
5. If you want to apply the filter to the entire image, enter the `Shadow`, `Mid`, `Highlight` values and click on `Sharpen Image` button.
6. If there are any errors while executing, the user will be prompted and asked to perform the operation again.

## Important Notes
- Ensure proper file paths are provided for all commands.
- Use valid commands with appropriate parameters to avoid exceptions.
