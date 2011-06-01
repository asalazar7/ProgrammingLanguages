package enigma.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import enigma.model.Reflector;

/**
 * Validates stated behavior when encoding a string with a {@link Reflector}.
 * 
 * @author Aaron Kuehler
 * 
 */
public class WhenMappingWithReflector {

    @Test
    public void shouldEncodeLRQWERTYToJDVIGB() throws Exception {

	String source = "QWERTY";
	String expected = "JDVIGB";
	StringBuilder encodedStringBuilder = new StringBuilder();
	Reflector reflector = new Reflector("ZYXWVUTSRQPONMLKJIHGFEDCBA");
	for (char character : source.toCharArray()) {
	    encodedStringBuilder.append(reflector.encodeLR(character));
	}
	assertEquals(expected, encodedStringBuilder.toString());
    }
}
