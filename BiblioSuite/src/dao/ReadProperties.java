package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
	
	public static Properties getConnectionProperties(){
		Properties prop = null; 
		try {
			File file = new File("src/dao/DBParameter.properties");
			FileInputStream inputStream = new FileInputStream(file);
			prop = new Properties();
			prop.load(inputStream);
			inputStream.close();
			
			
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
