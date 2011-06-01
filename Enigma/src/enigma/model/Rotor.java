package enigma.model;

import enigma.util.language.English;

/**
 * Models a rotor in the Enigma Machine. The rotor maps symbols on either side
 * of the rotor to one another as binary pairs used in substitutions made during
 * the encoding/decoding phases.
 * 
 * @author Aaron Kuehler
 * 
 */
public class Rotor extends AlphabetEncoder {

    // Instance Variables
    private int position;
    protected char[] rightToLeftWiring = new char[MAX];

    // Instance Methods
    /**
     * Constructs a new rotor with the right side mapping described by the input
     * string. The character at position 0 in the string will map to the
     * character 'A' on the left side of the mapping. The character at position
     * 1 will map to the character 'B' on the left side, and so on.
     * 
     * @param mapping
     */
    public Rotor(String mapping) {
	super(mapping);
	
	// Builds the inverse, right-to-left, mappings
	int index = 0;
	for (char character : leftToRightWiring) {
	    rightToLeftWiring[English.alphabeticIndexOf(character)] = (char) (index + English.ALPHABET_START_UNICODE_OFFSET);
	    index++;
	}
	position = 0;
    }

    /**
     * Increment the position by one "click, 0..25, and return whether the rotor
     * made a complete revolution.
     * 
     * @return If the rotor's position rolls over the maximum size of the
     *         alphabet, then return true; false otherwise.
     */
    public boolean inc() {
	position++;
	if (position % MAX == 0) {
	    position = 0;
	    return true;
	}
	return false;
    }

    /**
     * Interrogates the rotor's state to determine the current position of the
     * rotor.
     * 
     * @return
     */
    protected int getCurrentPosition() {
	return Integer.valueOf(position);
    }

    /**
     * Sets the initial position of the rotor to the input number.
     * 
     * @param n
     */
    public void set(int number) {

	// Validate that the input start position is within bounds of the rotor
	if (number < 0 || number > MAX) {
	    throw new IllegalArgumentException(
		    "The starting position of the rotor must be an integer between [0] and ["
			    + MAX + "].");
	}
	position = number;
    }

    /*
     * (non-Javadoc)
     * @see enigma.model.AlphabetEncoder#encodeLR(char)
     */
    @Override
    public char encodeLR(char character) {
	int targetIndex = (English.alphabeticIndexOf(character) + position) % MAX;
	return leftToRightWiring[targetIndex ];
    }

    /**
     * Return the character that the input character is mapped to going R->L
     * across the rotor.
     * 
     * @param character
     * @return Left side of the rotor mapping.
     */
    public char encodeRL(char character) {
	int characterIndex = English.alphabeticIndexOf(character);
	int targetIndex = English.alphabeticIndexOf(rightToLeftWiring[characterIndex]) - position;
	targetIndex = targetIndex < 0 ? MAX - (targetIndex * -1) : targetIndex;
	targetIndex = targetIndex % MAX;
	return (char)(targetIndex += English.ALPHABET_START_UNICODE_OFFSET);
    }
}
