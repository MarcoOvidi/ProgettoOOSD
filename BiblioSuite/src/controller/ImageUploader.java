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
	 * 
	 * @return String URL della nuova immagine
	 */
	public static String uploadImage(Image image, String subpath) throws IOException {
		String resultingURL = "images/" + subpath;
		File outputFile = new File(resultingURL);
		outputFile.createNewFile();
		BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		ImageIO.write(bImage, "png", outputFile);
		return resultingURL;
	}

	// TODO delete image

}
