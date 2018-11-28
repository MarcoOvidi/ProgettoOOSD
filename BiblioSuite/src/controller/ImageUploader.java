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

	/*
	 * Carica un'immagine e restituisce il nuovo URL
	 * @return String URL della nuova immagine
	 */
	public static String uploadImage (Image image, String subpath) {
		String resultingURL = "images/" + subpath;
		File outputFile = new File(resultingURL);
		BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		try {
			ImageIO.write(bImage, "png", outputFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return resultingURL;
	}
	
	//TODO delete image

}
