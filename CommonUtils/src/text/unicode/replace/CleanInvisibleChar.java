package text.unicode.replace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Utils;

public class CleanInvisibleChar {

	private static final Logger logger = LoggerFactory.getLogger(CleanInvisibleChar.class); 
	public static void main(String[] args){
		String filePath = "/Users/zhiguodu/temp/index.html";
		String my_string = Utils.readFile(filePath);
		int originalIndex = my_string.indexOf("\u200B");
		
		String newString = my_string.replaceAll("\u200B", "");
		newString = newString.replaceAll("\u00A0", "");
		//newString = newString.replaceAll("[\\p{Zs}\\s]+", "");
		//replaceAll("[\\p{C}\\p{Z}]", "")
	
		logger.debug(newString);
		
		
		logger.debug(String.format("first index = %d", originalIndex));
		logger.debug(String.format("first index = %d", newString.indexOf("\u200B")));
	}
}
