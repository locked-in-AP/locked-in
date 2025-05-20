package com.locked_in.util;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.http.Part;

/**
 * Utility class for handling image file uploads.
 * 
 * This utility class provides methods for:
 * - Extracting filenames from uploaded files
 * - Managing image file uploads to server directories
 * - Handling file system operations for images
 * 
 * The class ensures proper file handling by:
 * - Validating file names and paths
 * - Creating necessary directories
 * - Managing file system operations safely
 */
public class ImageUtil {

	/**
	 * Extracts the file name from the given Part object.
	 * 
	 * This method:
	 * 1. Retrieves the content-disposition header
	 * 2. Parses the header to find the filename
	 * 3. Returns a default name if no filename is found
	 * 
	 * @param part the Part object representing the uploaded file
	 * @return the extracted file name, or "download.png" if no filename is found
	 */
	public String getImageNameFromPart(Part part) {
		// Retrieve the content-disposition header from the part
		String contentDisp = part.getHeader("content-disposition");

		// Split the header by semicolons to isolate key-value pairs
		String[] items = contentDisp.split(";");

		// Initialize imageName variable to store the extracted file name
		String imageName = null;

		// Iterate through the items to find the filename
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				// Extract the file name from the header value
				imageName = s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}

		// Check if the filename was not found or is empty
		if (imageName == null || imageName.isEmpty()) {
			// Assign a default file name if none was provided
			imageName = "download.png";
		}

		// Return the extracted or default file name
		return imageName;
	}

	/**
	 * Uploads an image file to a specified directory.
	 * 
	 * This method:
	 * 1. Validates and creates the target directory if needed
	 * 2. Extracts the image filename
	 * 3. Writes the file to the server's file system
	 * 4. Handles any I/O errors that occur
	 * 
	 * @param part the Part object containing the uploaded image
	 * @param rootPath the root path for file storage
	 * @param saveFolder the subfolder to save the image in
	 * @return true if the upload was successful, false otherwise
	 */
	public boolean uploadImage(Part part, String rootPath, String saveFolder) {
		String savePath = getSavePath(saveFolder);
		File fileSaveDir = new File(savePath);

		// Ensure the directory exists
		if (!fileSaveDir.exists()) {
			if (!fileSaveDir.mkdir()) {
				return false; // Failed to create the directory
			}
		}
		try {
			// Get the image name
			String imageName = getImageNameFromPart(part);
			// Create the file path
			String filePath = savePath + "/" + imageName;
			// Write the file to the server
			part.write(filePath);
			return true; // Upload successful
		} catch (IOException e) {
			e.printStackTrace(); // Log the exception
			return false; // Upload failed
		}
	}
	
	/**
	 * Generates the full path for saving an image.
	 * 
	 * Constructs the complete file system path by combining:
	 * - The web application's resource directory
	 * - The images subdirectory
	 * - The specified save folder
	 * 
	 * @param saveFolder the subfolder to save the image in
	 * @return the complete path for saving the image
	 */
	public String getSavePath(String saveFolder) {
		return "C:/Users/Prithivi/eclipse-workspace/islington-student/src/main/webapp/resources/images/"+saveFolder+"/";
	}
}
