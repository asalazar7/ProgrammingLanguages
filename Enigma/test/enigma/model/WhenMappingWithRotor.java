package enigma.model;

import static org.junit.Assert.*;

import org.junit.Test;

import enigma.model.Rotor;

/**
 * Validates stated behavior when encoding a string with a {@link Rotor}.
 * 
 * @author Aaron Kuehler
 * 
 */
public class WhenMappingWithRotor {

    @Test
    public void shouldEncodeLRQWERTYToJDVIGB() throws Exception {

	String source = "QWERTY";
	String expected = "JDVIGB";
	StringBuilder encodedStringBuilder = new StringBuilder();
	Rotor rotor = new Rotor("ZYXWVUTSRQPONMLKJIHGFEDCBA");
	for (char character : source.toCharArray()) {
	    encodedStringBuilder.append(rotor.encodeLR(character));
	}
	assertEquals(expected, encodedStringBuilder.toString());
    }
    
    @Test
    public void shouldEncodeRLJDVIGBToQWERTY() throws Exception {

	String source = "JDVIGB";
	String expected = "QWERTY";
	StringBuilder encodedStringBuilder = new StringBuilder();
	Rotor rotor = new Rotor("ZYXWVUTSRQPONMLKJIHGFEDCBA");
	for (char character : source.toCharArray()) {
	    encodedStringBuilder.append(rotor.encodeRL(character));
	}
	assertEquals(expected, encodedStringBuilder.toString());
    }
}
