package hello;

import java.util.HashMap;

import org.springframework.context.annotation.Bean;


public class ConsoleOutputRecord implements Recorder {

	private static final HashMap<String, Integer> files = new HashMap<String, Integer>();
	
	@Override
	public boolean record(String resourceName) {
		// TODO Auto-generated method stub
		System.out.println("record");
		System.out.println(resourceName);
		if (!files.containsKey(resourceName)){
			synchronized(ConsoleOutputRecord.class){
				if(!files.containsKey(resourceName)){
					files.put(resourceName, 1);
				}
			}
			
		}
		return true;
	}
	
	public static HashMap dumpFiles(){
		for (String file : files.keySet()){
			System.out.println(file);
		}
		
		return files;
	}

}
