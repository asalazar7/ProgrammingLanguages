package enigma.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import enigma.model.Rotor;

/**
 * Validates stated behavior when creating a {@link Rotor}. This test also
 * validates initial state after construction.
 * 
 * @author Aaron Kuehler
 * 
 */
public class WhenConstructingRotor {

    @Test
    public void shouldFailIfMappingIsTooShort() throws Exception {
	try {
	    new Rotor("ABCDEFGHIJKLMNOPQRSTUVWXY");
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
	    new Rotor("ABCDEFGHIJKLMNOPQRSTUVWXYZA");
	    fail(); // If the construction succeeds without throwing an
		    // exception, this test should fail.
	} catch (IllegalArgumentException exception) {
	    // Consume this exception because it indicates an affirmative in
	    // this test case.
	}
    }

    @Test
    public void shouldSucceedIfMappingIsOfCorrectLength() throws Exception {
	Rotor rotor = new Rotor("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	assertNotNull(rotor);
    }

    @Test
    public void shouldInitializeStartingPositionTo0() throws Exception {
	Rotor rotor = new Rotor("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	assertEquals(0, rotor.getCurrentPosition());
    }

    @Test
    public void shouldProduceValidMapping() throws Exception {
	String mapping = "QWERTYUIOPLKJHGFDSAZXCVBNM";
	String expected = "SXVQCPONHMLKZYIJADREGWBUFT";
	Rotor rotor = new Rotor(mapping);
	for (int index = 0; index < mapping.length(); index++) {
	    assertEquals(expected.charAt(index),
		    rotor.rightToLeftWiring[index]);
	}
    }
}
