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
		
		if (doc.getMetaData().getCompositionPeriod().getFrom() == 0 && doc.getMetaData().getCompositionPeriod().getTo() == 0)
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
			System.out.println(doc.getUUID());
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
		transcribers.add(new Paragraph("Digitalizers:"));
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
			System.out.println(path);
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
				document.add(imgScan);
			} else
				continue;

		}

		document.close();
	}
	
	public static void getOpera(model.Document doc, File destPath) {

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
		
		if (doc.getMetaData().getCompositionPeriod().getFrom() == 0 && doc.getMetaData().getCompositionPeriod().getTo() == 0)
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
			System.out.println(doc.getUUID());
			sPrj = ScanningWorkProjectQuerySet.loadScanningWorkProject(doc.getScanningWorkProject());
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

		Cell dCord = new Cell();
		dCord.add(new Paragraph("Digitalization \n Coordinator:"));
		dCord.setBackgroundColor(colorLeft);
		headerTable.addCell(dCord);

		Paragraph cord1 = new Paragraph("- " + AdministrationController.loadUsername(sPrj.getCoordinator()));
		cord1.setWidth(400);
		cord1.setRelativePosition(10, 0, 0, 0);

		Cell cord2 = new Cell();
		cord2.setBackgroundColor(colorRight);
		cord2.add(cord1);
		headerTable.addCell(cord2);
		
		//__
		Cell tCord = new Cell();
		tCord.add(new Paragraph("Transcription \n Coordinator:"));
		tCord.setBackgroundColor(colorLeft);
		headerTable.addCell(tCord);

		Paragraph tcord1 = new Paragraph("- " + AdministrationController.loadUsername(tPrj.getCoordinator()));
		tcord1.setWidth(400);
		tcord1.setRelativePosition(10, 0, 0, 0);

		Cell tcord2 = new Cell();
		tcord2.setBackgroundColor(colorRight);
		tcord2.add(tcord1);
		headerTable.addCell(tcord2);
		

		Cell digitalizers = new Cell();
		digitalizers.add(new Paragraph("Digitalizers:"));
		digitalizers.setBackgroundColor(colorLeft);
		headerTable.addCell(digitalizers);

		Paragraph digitalizersList = new Paragraph();
		digitalizersList.setWidth(400);
		digitalizersList.setRelativePosition(10, 0, 0, 0);

		for (UUIDUser t : sPrj.getDigitalizers()) {
			digitalizersList.add("- " + AdministrationController.loadUsername(t) + "\n");
		}

		Cell listD = new Cell();
		listD.setBackgroundColor(colorRight);
		listD.add(digitalizersList);
		headerTable.addCell(listD);
		
		//__

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
		revisers.add(new Paragraph("Digitalization \n Revisers:"));
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
		
		
		Cell revisersT = new Cell();
		revisersT.add(new Paragraph("Transcription \n Revisers:"));
		revisersT.setBackgroundColor(colorLeft);
		headerTable.addCell(revisersT);

		Paragraph revisersTList = new Paragraph();
		revisersTList.setWidth(400);
		revisersTList.setRelativePosition(10, 0, 0, 0);

		for (UUIDUser t : tPrj.getRevisers()) {
			revisersTList.add("- " + AdministrationController.loadUsername(t) + "\n");
		}

		Cell listTR = new Cell();
		listTR.setBackgroundColor(colorRight);
		listTR.add(revisersTList);
		headerTable.addCell(listTR);
		

		document.add(headerTable);

		Paragraph footer = new Paragraph(
				"BIBLIO SUITE - DOCUMENT: " + doc.getTitle().toUpperCase() + " PAGE 0/" + doc.getPagesNumber());
		footer.setFontSize(11);
		footer.setFixedPosition(200, 10, 300);
		document.add(footer);

		LinkedList<Page> list = doc.getPageList();
		Collections.sort(list);

		for (Page p : list) {
			document.add(new AreaBreak());
			
			Table table = new  Table(1);
			
			Cell tr = new Cell();
			
			String t = p.getTranscription().getText().getText();
			if(t != null) {
				Paragraph transcription = new Paragraph(p.getTranscription().getText().getText());
				tr.add(transcription);
			}else {
				Paragraph transcription = new Paragraph("No Transcription Available");
				tr.add(transcription);
			}
			
			Cell dt = new Cell();
			
			String path = p.getScan().getImage().getUrl();
			System.out.println(path);
			ImageData imgData = null;

			try {
				imgData = ImageDataFactory.create(path);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (imgData != null) {
				Image imgScan = new Image(imgData);
				imgScan.setMaxHeight(320);
				
				dt.add(imgScan);
			} else {
				Paragraph transcription = new Paragraph("Digitalization not Available");
				dt.add(transcription);
			}

			tr.setWidth(500);
			dt.setWidth(500);
			table.addCell(dt);
			table.addCell(tr);
			footer = new Paragraph("BIBLIO SUITE - DOCUMENT: " + doc.getTitle().toUpperCase() + " PAGE "
					+ p.getPageNumber() + "/" + doc.getPagesNumber());
			footer.setFontSize(11);
			footer.setFixedPosition(200, 10, 300);
			document.add(footer);
			document.add(table);
		
		
		
		
		}

		document.close();
	}
	
}
