package enigma.driver;

import enigma.model.EnigmaMachine;
import enigma.model.Reflector;
import enigma.model.Rotor;

/**
 * A sample consumer of the {@link EnigmaMachine}. This class simply attempts to
 * show that the enigma machine can encode an known value into a known encoding.
 * 
 * @author Aaron Kuehler
 * 
 */
public class EnigmaMachineUser {

    /**
     * Starts a main thread which encodes a known cleartext string, then
     * attempts to decode the result back into the cleartext string.
     * 
     * @param args
     */
    public static void main(String[] args) {

	// Configure machine parts
	Rotor rotor1 = new Rotor("QWERTYUIOPLKJHGFDSAZXCVBNM");
	Rotor rotor2 = new Rotor("ZAQWSXCDERFVBGTYHNMJUIKLOP");
	Rotor rotor3 = new Rotor("PLOKMIJNUHBYGVTFCRDXESZWAQ");
	Reflector reflector = new Reflector("NPKMSLZTWQCFDAVBJYEHXOIURG");

	// Instantiate machine
	EnigmaMachine enigmaMachine = new EnigmaMachine(rotor1, rotor2, rotor3,
		reflector);

	// Encode known value
	String candidate = "AAAAAAAAAAAAAAAAAAAAAAAAAAA";
	System.out.println(candidate);

	enigmaMachine.setRotors(5, 9, 14);
	String encoded = enigmaMachine.encodeLine(candidate);
	System.out.println(encoded);

	enigmaMachine.setRotors(5, 9, 14);
	String decoded = enigmaMachine.encodeLine(encoded);
	System.out.println(decoded);
    }

}
