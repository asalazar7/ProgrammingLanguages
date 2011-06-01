package enigma.model;

import static org.junit.Assert.*;

import org.junit.Test;

import enigma.model.Rotor;

/**
 * Validates stated behavior when setting the initial position of the rotor.
 * 
 * @author Aaron Kuehler
 * 
 */
public class WhenSettingInitialRotorPosition {

    @Test
    public void shouldFailIfInitialPositionIsTooSmall() throws Exception {
	try {
	    Rotor rotor = new Rotor("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	    rotor.set(-1);
	    fail(); // If the construction succeeds without throwing an
		    // exception, this test should fail.
	} catch (IllegalArgumentException exception) {
	    // Consume this exception because it indicates an affirmative in
	    // this test case.
	}
    }

    @Test
    public void shouldFailIfInitialPositionIsTooLarge() throws Exception {
	try {
	    String mapping = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    Rotor rotor = new Rotor(mapping);
	    rotor.set(mapping.length() + 1);
	    fail(); // If the construction succeeds without throwing an
		    // exception, this test should fail.
	} catch (IllegalArgumentException exception) {
	    // Consume this exception because it indicates an affirmative in
	    // this test case.
	}
    }

    @Test
    public void shouldSucceedIfInitalPositionIsWithinRange() throws Exception {
	String mapping = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	int initialPosition = mapping.length();
	Rotor rotor = new Rotor(mapping);
	rotor.set(initialPosition);
	assertEquals(initialPosition, rotor.getCurrentPosition());
    }
}
