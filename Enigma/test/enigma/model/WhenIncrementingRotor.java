package enigma.model;

import static org.junit.Assert.*;

import org.junit.Test;

import enigma.model.Rotor;

/**
 * Validates stated behavior when incrementing/rotating the rotor.
 * 
 * @author Aaron Kuehler
 * 
 */
public class WhenIncrementingRotor {

    @Test
    public void shouldIncrementToNextPositionAndReturnFalse() throws Exception {
	Rotor rotor = new Rotor("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	assertFalse(rotor.inc());
	assertEquals(1, rotor.getCurrentPosition());
    }

    @Test
    public void shouldRotateBackToStartPosition() throws Exception {
	String mapping = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	Rotor rotor = new Rotor(mapping);
	
	// Index the rotor to the finial position
	for (int index = 0; index < mapping.length() - 1; index++) {
	    assertFalse(rotor.inc());
	}
	
	// Index past back around to the start position
	assertTrue(rotor.inc());
	assertEquals(0, rotor.getCurrentPosition());
    }
}
