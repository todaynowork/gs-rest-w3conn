package common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
	public static String readFile(String filePath){
		String my_string = null;
		try {
			my_string = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} ;
		return my_string;
	}
}
