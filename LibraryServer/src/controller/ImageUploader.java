package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ImageUploader {
	
	private ImageUploader() {
	}

	//simulates uploading image to server
	public static boolean uploadImage (Image image, String subpath) {
		File outputFile = new File("images/" + subpath);
		BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		try {
			ImageIO.write(bImage, "png", outputFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return true;
	}
	
	//TODO delete image

}
