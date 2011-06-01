package enigma.model;

import enigma.util.language.English;

/**
 * A simplified model of the electro-mechanical rotor cryptographic devices
 * known as Enigma Machines.
 * 
 * @author Aaron Kuehler
 * 
 */
public class EnigmaMachine {

    // Instance Variables
    Rotor rotor1; // first rotor
    Rotor rotor2; // second rotor
    Rotor rotor3; // third rotor
    Reflector reflector;

    /**
     * Initializes an enigma machine with the 3 rotors and reflector passed as
     * arguments.
     * 
     * @param rotor1
     * @param rotor2
     * @param rotor3
     * @param reflector
     */
    public EnigmaMachine(Rotor rotor1, Rotor rotor2, Rotor rotor3,
	    Reflector reflector) {
	this.rotor1 = rotor1;
	this.rotor2 = rotor2;
	this.rotor3 = rotor3;
	this.reflector = reflector;
    }

    /**
     * Encodes a character using the current configuration of the rotors and
     * reflector. If the input character is an uppercase character, the rotors
     * are incremented after the character is encoded.
     * 
     * @param character
     * @return The encoded value of the character
     */
    private char encodeChar(char character) {

	if (!English.isInAlphabet(character)) {
	    return character;
	}
	char encodedCharacter = rotor1.encodeRL(
		rotor2.encodeRL(
			rotor3.encodeRL(
				reflector.encodeLR(
					rotor3.encodeLR(
						rotor2.encodeLR(
							rotor1.encodeLR(character)))))));
	incrementRotors();
	return encodedCharacter;
    }

    /**
     * Encodes all of the characters in a line of text.
     * 
     * @param line
     * @return The encoded value of the string
     */
    public String encodeLine(String line) {
	StringBuilder encodedLineBuilder = new StringBuilder();
	for (char character : line.toCharArray()) {
	    encodedLineBuilder.append(encodeChar(character));
	}
	return encodedLineBuilder.toString();
    }

    /**
     * Sets the positions of Rotors 1, 2, and 3 to the respective input indexes
     * 
     * @param rotor1Position
     * @param rotor2Position
     * @param rotor3Position
     */
    public void setRotors(int rotor1Position, int rotor2Position,
	    int rotor3Position) {
	rotor1.set(rotor1Position);
	rotor2.set(rotor2Position);
	rotor3.set(rotor3Position);
    }

    /**
     * "Rotates" the rotors of the enigma machine appropriately accorging to
     * their current state.
     */
    private void incrementRotors() {
	if (rotor1.inc()) {
	    if (rotor2.inc()) {
		rotor3.inc();
	    }
	}
    }
}
