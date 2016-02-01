package hello;

import org.springframework.context.annotation.Bean;


public class ConsoleOutputRecord implements Recorder {

	@Override
	public boolean record(String resourceName) {
		// TODO Auto-generated method stub
		System.out.println("record");
		System.out.println(resourceName);
		return true;
	}

}
