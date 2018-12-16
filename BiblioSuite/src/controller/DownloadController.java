package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.LinkedList;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import dao.DatabaseException;
import dao.ScanningWorkProjectQuerySet;
import dao.TranscriptionWorkProjectQuerySet;
import model.Page;
import model.ScanningWorkProject;
import model.TranscriptionWorkProject;
import vo.UUIDUser;

public class DownloadController {

	/**
	 * Genera il pdf di tutte le trascrizioni dell'opera selezionata
	 * 
	 * @param doc      documento aperto
	 * @param destPath destinazione
	 * 
	 */
	public static void getOperaTranscription(model.Document doc, File destPath) {

		// destinazione del file
		PdfWriter writer = null;

		try {
			writer = new PdfWriter(destPath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Creazione PdfDocument
		PdfDocument pdf = new PdfDocument(writer);

		// Creazione documento
		Document document = new Document(pdf);

		// header prima pagina
		Table headerTable = new Table(2);

		String imFile = "resources/favicon/LogoUnivaq.png";
		ImageData data1 = null;

		try {
			data1 = ImageDataFactory.create(imFile);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Color colorLeft = WebColors.getRGBColor("#ddd");
		Color colorRight = WebColors.getRGBColor("#eee");

		// Creating an Image object
		Image image = new Image(data1);
		image.setWidth(100);
		image.setHeight(100);
		String data = "Document: " + doc.getTitle() + "\n Author: " + doc.getMetaData().getAuthor() + "\n";

		if (doc.getMetaData().getCompositionDate().equals("0"))
			data = data + "Composition Date : " + String.valueOf(doc.getMetaData().getCompositionDate()) + "\n";
		else
			data = data + "Composition From : " + String.valueOf(doc.getMetaData().getCompositionPeriod().getFrom())
					+ "\n" + "Composition To : " + String.valueOf(doc.getMetaData().getCompositionPeriod().getTo())
					+ "\n";

		data = data + "Preservation State : " + doc.getMetaData().getPreservationState();

		Paragraph header = new Paragraph(data);

		Cell immagine = new Cell();
		immagine.add(image);
		immagine.setBackgroundColor(colorLeft);
		headerTable.addCell(immagine);

		Cell c = new Cell();
		c.setWidth(415);
		c.setBackgroundColor(colorRight);
		c.add(header);
		headerTable.addCell(c);

		Cell descr = new Cell();
		descr.add(new Paragraph("Description:"));
		descr.setBackgroundColor(colorLeft);
		headerTable.addCell(descr);

		Paragraph description = new Paragraph(doc.getMetaData().getDescription());
		description.setWidth(400);
		description.setRelativePosition(10, 0, 0, 0);

		Cell descript = new Cell();
		descript.setBackgroundColor(colorRight);
		descript.add(description);
		headerTable.addCell(descript);

		TranscriptionWorkProject tPrj = null;
		try {
			tPrj = TranscriptionWorkProjectQuerySet.loadTranscriptionWorkProject(doc.getTranscriptionWorkProject());
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Cell cord = new Cell();
		cord.add(new Paragraph("Coordinator:"));
		cord.setBackgroundColor(colorLeft);
		headerTable.addCell(cord);

		Paragraph cord1 = new Paragraph("- " + AdministrationController.loadUsername(tPrj.getCoordinator()));
		cord1.setWidth(400);
		cord1.setRelativePosition(10, 0, 0, 0);

		Cell cord2 = new Cell();
		cord2.setBackgroundColor(colorRight);
		cord2.add(cord1);
		headerTable.addCell(cord2);

		Cell transcribers = new Cell();
		transcribers.add(new Paragraph("Transcribers:"));
		transcribers.setBackgroundColor(colorLeft);
		headerTable.addCell(transcribers);

		Paragraph transcribersList = new Paragraph();
		transcribersList.setWidth(400);
		transcribersList.setRelativePosition(10, 0, 0, 0);

		for (UUIDUser t : tPrj.getTranscribers()) {
			transcribersList.add("- " + AdministrationController.loadUsername(t) + "\n");
		}

		Cell listT = new Cell();
		listT.setBackgroundColor(colorRight);
		listT.add(transcribersList);
		headerTable.addCell(listT);

		Cell revisers = new Cell();
		revisers.add(new Paragraph("Revisers:"));
		revisers.setBackgroundColor(colorLeft);
		headerTable.addCell(revisers);

		Paragraph revisersList = new Paragraph();
		revisersList.setWidth(400);
		revisersList.setRelativePosition(10, 0, 0, 0);

		for (UUIDUser t : tPrj.getRevisers()) {
			revisersList.add("- " + AdministrationController.loadUsername(t) + "\n");
		}

		Cell listR = new Cell();
		listR.setBackgroundColor(colorRight);
		listR.add(revisersList);
		headerTable.addCell(listR);

		document.add(headerTable);

		Paragraph footer = new Paragraph(
				"BIBLIO SUITE - DOCUMENT: " + doc.getTitle().toUpperCase() + " PAGE 0/" + doc.getPagesNumber());
		footer.setFontSize(11);
		footer.setFixedPosition(200, 10, 300);
		document.add(footer);

		LinkedList<Page> list = doc.getPageList();
		Collections.sort(list);

		for (Page p : list) {
			String t = p.getTranscription().getText().getText();
			if (t != null) {
				document.add(new AreaBreak());
				Paragraph transcription = new Paragraph();
				transcription.add(p.getTranscription().getText().getText());
				document.add(transcription);
				footer = new Paragraph("BIBLIO SUITE - DOCUMENT: " + doc.getTitle().toUpperCase() + " PAGE "
						+ p.getPageNumber() + "/" + doc.getPagesNumber());
				footer.setFontSize(11);
				footer.setFixedPosition(200, 10, 300);
				document.add(footer);
			} else
				continue;
		}

		document.close();

	}

	public static void getOperaDigitalization(model.Document doc, File destPath) {

		// destinazione del file
		PdfWriter writer = null;

		try {
			writer = new PdfWriter(destPath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Creazione PdfDocument
		PdfDocument pdf = new PdfDocument(writer);

		// Creazione documento
		Document document = new Document(pdf);

		// header prima pagina
		Table headerTable = new Table(2);

		String imFile = "resources/favicon/LogoUnivaq.png";
		ImageData data1 = null;

		try {
			data1 = ImageDataFactory.create(imFile);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Color colorLeft = WebColors.getRGBColor("#ddd");
		Color colorRight = WebColors.getRGBColor("#eee");

		// Creating an Image object
		Image image = new Image(data1);
		image.setWidth(100);
		image.setHeight(100);
		String data = "Document: " + doc.getTitle() + "\n Author: " + doc.getMetaData().getAuthor() + "\n";

		if (doc.getMetaData().getCompositionDate().equals("0"))
			data = data + "Composition Date : " + String.valueOf(doc.getMetaData().getCompositionDate()) + "\n";
		else
			data = data + "Composition From : " + String.valueOf(doc.getMetaData().getCompositionPeriod().getFrom())
					+ "\n" + "Composition To : " + String.valueOf(doc.getMetaData().getCompositionPeriod().getTo())
					+ "\n";

		data = data + "Preservation State : " + doc.getMetaData().getPreservationState();

		Paragraph header = new Paragraph(data);

		Cell immagine = new Cell();
		immagine.add(image);
		immagine.setBackgroundColor(colorLeft);
		headerTable.addCell(immagine);

		Cell c = new Cell();
		c.setWidth(415);
		c.setBackgroundColor(colorRight);
		c.add(header);
		headerTable.addCell(c);

		Cell descr = new Cell();
		descr.add(new Paragraph("Description:"));
		descr.setBackgroundColor(colorLeft);
		headerTable.addCell(descr);

		Paragraph description = new Paragraph(doc.getMetaData().getDescription());
		description.setWidth(400);
		description.setRelativePosition(10, 0, 0, 0);

		Cell descript = new Cell();
		descript.setBackgroundColor(colorRight);
		descript.add(description);
		headerTable.addCell(descript);

		ScanningWorkProject sPrj = null;
		try {
			sPrj = ScanningWorkProjectQuerySet.loadScanningWorkProject(doc.getScanningWorkProject());
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Cell cord = new Cell();
		cord.add(new Paragraph("Coordinator:"));
		cord.setBackgroundColor(colorLeft);
		headerTable.addCell(cord);

		Paragraph cord1 = new Paragraph("- " + AdministrationController.loadUsername(sPrj.getCoordinator()));
		cord1.setWidth(400);
		cord1.setRelativePosition(10, 0, 0, 0);

		Cell cord2 = new Cell();
		cord2.setBackgroundColor(colorRight);
		cord2.add(cord1);
		headerTable.addCell(cord2);

		Cell transcribers = new Cell();
		transcribers.add(new Paragraph("Transcribers:"));
		transcribers.setBackgroundColor(colorLeft);
		headerTable.addCell(transcribers);

		Paragraph transcribersList = new Paragraph();
		transcribersList.setWidth(400);
		transcribersList.setRelativePosition(10, 0, 0, 0);

		for (UUIDUser t : sPrj.getDigitalizers()) {
			transcribersList.add("- " + AdministrationController.loadUsername(t) + "\n");
		}

		Cell listT = new Cell();
		listT.setBackgroundColor(colorRight);
		listT.add(transcribersList);
		headerTable.addCell(listT);

		Cell revisers = new Cell();
		revisers.add(new Paragraph("Revisers:"));
		revisers.setBackgroundColor(colorLeft);
		headerTable.addCell(revisers);

		Paragraph revisersList = new Paragraph();
		revisersList.setWidth(400);
		revisersList.setRelativePosition(10, 0, 0, 0);

		for (UUIDUser t : sPrj.getRevisers()) {
			revisersList.add("- " + AdministrationController.loadUsername(t) + "\n");
		}

		Cell listR = new Cell();
		listR.setBackgroundColor(colorRight);
		listR.add(revisersList);
		headerTable.addCell(listR);

		document.add(headerTable);

		Paragraph footer = new Paragraph(
				"BIBLIO SUITE - DOCUMENT: " + doc.getTitle().toUpperCase() + " PAGE 0/" + doc.getPagesNumber());
		footer.setFontSize(11);
		footer.setFixedPosition(200, 10, 300);
		document.add(footer);

		LinkedList<Page> list = doc.getPageList();
		Collections.sort(list);

		for (Page p : list) {
			String path = p.getScan().getImage().getUrl();
			ImageData imgData = null;

			try {
				imgData = ImageDataFactory.create(path);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (imgData != null) {
				document.add(new AreaBreak());
				Image imgScan = new Image(imgData);
				imgScan.setAutoScale(true);
				document.add(image);
			} else
				continue;

		}

		document.close();
	}
}
