package enigma.model;

import enigma.util.language.English;

/**
 * An abstract class which represents an object used to encode symbols of
 * an alphabet.
 * 
 * @author Aaron Kuehler
 * 
 */
public abstract class AlphabetEncoder {

    // Instance Variables
    public final static int MAX = English.MAX;
    protected char[] leftToRightWiring = new char[MAX];

    // Instance Methods
    /**
     * Constructs a new alphabet encoder with the mapping described by the input
     * string. The character at position 0 in the string will map to the
     * character 'A' on the left side of the mapping. The character at position
     * 1 will map to the character 'B' on the left side, and so on.
     * 
     * @param mapping
     */
    protected AlphabetEncoder(String mapping) {

	// Validate the length of the input string
	if (mapping.length() != MAX) {
	    throw new IllegalArgumentException(
		    "The character mapping should be [" + MAX
			    + "] characters, but contained ["
			    + mapping.length() + "] characters instead.");
	}

	// Assign the left-to-right mappings
	leftToRightWiring = mapping.toCharArray();
    }

    /**
     * Return the character that input character is mapped to going L->R across
     * the encoder.
     * 
     * @param character
     * @return Right side symbol of the encoder mapping.
     */
    protected abstract char encodeLR(char character);
}
