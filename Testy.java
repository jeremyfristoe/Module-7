package m7v5;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class Testy {

	@Test
	public void testForIOE() {
		assertThrows(IOException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {

			}
		});
		
	}

}
