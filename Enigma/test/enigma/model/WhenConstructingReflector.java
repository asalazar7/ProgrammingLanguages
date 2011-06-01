package enigma.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Validates stated behavior when creating a {@link Reflector}. This test also
 * validates initial state after construction.
 * 
 * @author Aaron Kuehler
 * 
 */
public class WhenConstructingReflector {

    @Test
    public void shouldFailIfMappingIsTooShort() throws Exception {
	try {
	    new Reflector("ABCDEFGHIJKLMNOPQRSTUVWXY");
	    fail(); // If the construction succeeds without throwing an
		    // exception, this test should fail.
	} catch (IllegalArgumentException exception) {
	    // Consume this exception because it indicates an affirmative in
	    // this test case.
	}
    }

    @Test
    public void shouldFailIfMappingIsTooLong() throws Exception {
	try {
	    new Reflector("ABCDEFGHIJKLMNOPQRSTUVWXYZA");
	    fail(); // If the construction succeeds without throwing an
		    // exception, this test should fail.
	} catch (IllegalArgumentException exception) {
	    // Consume this exception because it indicates an affirmative in
	    // this test case.
	}
    }

    @Test
    public void shouldSucceedIfMappingIsOfCorrectLength() throws Exception {
	Reflector reflector = new Reflector("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	assertNotNull(reflector);
    }
}
