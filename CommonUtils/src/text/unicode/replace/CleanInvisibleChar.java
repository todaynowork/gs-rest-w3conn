package text.unicode.replace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import common.Utils;

public class CleanInvisibleChar {

	public static void main(String[] args){
		String filePath = "/Users/zhiguodu/git/mtss_mobile/mtss_cli4_cn_ios/www/index.html";
		String my_string = Utils.readFile(filePath);
		
		String newString = my_string.replaceAll("\u200B", "");
		//replaceAll("[\\p{C}\\p{Z}]", "")
		System.out.print(newString);
		
	}
}
