package enigma.model;

import enigma.util.language.English;

/**
 * Models the reflector of the Enigma Machine. The reflector maps symbols of the
 * alphabet as binary pairs used for substitution in the reflecting phases of
 * the encoding/decoding process.
 * 
 * @author Aaron Kuehler
 * 
 */
public class Reflector extends AlphabetEncoder {

    /**
     * Constructs a new Reflector with the mapping described by the input
     * string. The character at position 0 in the string will map to the
     * character 'A' on the left side of the mapping. The character at position
     * 1 will map to the character 'B' on the left side, and so on.
     * 
     * @param mapping
     */
    public Reflector(String mapping) {
	super(mapping);
    }

    /*
     * (non-Javadoc)
     * @see enigma.model.AlphabetEncoder#encodeLR(char)
     */
    @Override
    public char encodeLR(char character) {
	int alphabeticIndex = English.alphabeticIndexOf(character);
	if(alphabeticIndex < 0 || alphabeticIndex > MAX) {
	    throw new IllegalArgumentException("The reflector cannot encode the character because it is not contained within the alphabet.");
	}
	return leftToRightWiring[English.alphabeticIndexOf(character)];
    }
}
