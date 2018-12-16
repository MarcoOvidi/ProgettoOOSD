package controller;


import com.itextpdf.io.image.ImageData; 
import com.itextpdf.io.image.ImageDataFactory; 

import com.itextpdf.kernel.pdf.PdfDocument; 
import com.itextpdf.kernel.pdf.PdfWriter; 

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;  

public class Prova {      
   public static void main(String args[]) throws Exception{              
//      // Creating a PdfWriter       
//      String dest = "/users/Marco/Desktop/provissima.pdf";       
//      PdfWriter writer = new PdfWriter(dest);               
//      
//      // Creating a PdfDocument       
//      PdfDocument pdfDoc = new PdfDocument(writer);              
//      
//      // Creating a Document        
//      Document document = new Document(pdfDoc);              
//      
//      // Creating an ImageData object       
//      //String imFile = "/users/Marco/Desktop/pippo.jpg";       
//      //ImageData data = ImageDataFactory.create(imFile);              
//      
//      // Creating an Image object        
//      //Image image = new Image(data);                
//      
//      // Setting the position of the image to the center of the page       
//      //image.setFixedPosition(100,250); 
//      
//      image.setAutoScale(true);
//      document.on
//      // Adding image to the document       
//      document.add(image);              
//      
//      // Closing the document       
//      document.close();
//      System.out.println("Image Scaled");    
	  
	   
	   // Creating a PdfWriter       
	      String dest = "/users/Marco/Desktop/provissima.pdf";       
	      PdfWriter writer = new PdfWriter(dest);           
	      
	      // Creating a PdfDocument       
	      PdfDocument pdf = new PdfDocument(writer);              
	      
	      // Creating a Document        
	      Document document = new Document(pdf);   
	      
	      String para1 = "UNIVAQ-BIBLIO SUITE  DOCUMENT:PROVIAMO  PAGINA 1/1";  
	      
	      String para2 = "Questa è la trascrizione della pagina jfhskjhfjvhjhwjfhxrhhxkewhfxjhewjxw"
	      		+ "jwqxdqwdhxjkwhqdxjkqwehdjxkhweqjkdxhewjqkdxhjwekqhdx"
	      		+ "xjdheqwdhxjkewhdxjkwehqdjkxehwqjkdxhwejkqdhxjkeqwhdxjkhjkewhdjxkwqhexd"
	      		+ "xwjkdhjwekqdhxjkeqwhdxjkweqhdjxkeqwjdxhjekqwdhxjkd"
	      		+ "jekwhdxjkewdhxjkewhdxjkehqwjkdxhjekqwdhxjkhq"
	      		+ "xjkeqwhxdjkewhdxjkehwqdjxqw";              
	      
	      // Creating Paragraphs       
	      Paragraph paragraph1 = new Paragraph(para1);             
	      Paragraph paragraph2 = new Paragraph(para2);  
	      
	    //Creating an ImageData object       
	      String imFile = "/users/Marco/Desktop/pippo.jpg";       
	      ImageData data = ImageDataFactory.create(imFile);              
	      
	      // Creating an Image object        
	      Image image = new Image(data);                
	      
	      // Setting the position of the image to the center of the page       
	      image.setFixedPosition(100,250); 
	      
	      image.setAutoScale(true);
	      
	      // Adding image to the document       
	      document.add(image);              
	      
	      // Adding paragraphs to document       
	      document.add(paragraph1);       
	      document.add(paragraph2);   
	      
	      document.add(new AreaBreak());
	      
	      document.add(paragraph1);       
	      document.add(paragraph2);
	      document.add(image);
	      
	      
	      // Closing the document       
	      document.close();              
   } 
}  