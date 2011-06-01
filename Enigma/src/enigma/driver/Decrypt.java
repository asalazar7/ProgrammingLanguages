package enigma.driver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import enigma.model.EnigmaMachine;
import enigma.model.Reflector;
import enigma.model.Rotor;

public class Decrypt {

    // Path to input file which contains the encrypted text
    private static final String INPUT_FILE_PATH = "src/enigma/resource/encrypted.text";

    // Starting position of rotor 1
    private static final int ROTOR1_START_POSITION = 12;

    // Starting position of rotor 2
    private static final int ROTOR2_START_POSITION = 9;

    // Starting position of rotor 3
    private static final int ROTOR3_START_POSITION = 12;

    /**
     * A consumer of the {@link EnigmaMachine}. This class starts a main thread
     * to compute the rotor settings needed to decrypt a text file.
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
	
	// Configure machine parts
	Rotor rotor1 = new Rotor("QWERTYUIOPLKJHGFDSAZXCVBNM");
	Rotor rotor2 = new Rotor("ZAQWSXCDERFVBGTYHNMJUIKLOP");
	Rotor rotor3 = new Rotor("PLOKMIJNUHBYGVTFCRDXESZWAQ");
	Reflector reflector = new Reflector("NPKMSLZTWQCFDAVBJYEHXOIURG");

	// Instantiate machine
	EnigmaMachine enigmaMachine = new EnigmaMachine(rotor1, rotor2, rotor3,
		reflector);
	enigmaMachine.setRotors(ROTOR1_START_POSITION, ROTOR2_START_POSITION,
		ROTOR3_START_POSITION);

	// Get decrypt the file at the given path
	decrypt(INPUT_FILE_PATH, enigmaMachine);
    }

    /**
     * Decrypts the file at the given inputFilePath using the input
     * {@link EnigmaMachine} instance.
     * 
     * @param inputFilePath
     * @param enigmaMachine
     * @throws IOException
     */
    private static void decrypt(String inputFilePath,
	    EnigmaMachine enigmaMachine) throws IOException {
	BufferedReader reader = new BufferedReader(
		new FileReader(inputFilePath));
	String line = reader.readLine();
	while (line != null) {
	    if (!line.isEmpty()) {
		System.out.println(enigmaMachine.encodeLine(line));
	    }
	    line = reader.readLine();
	}
    }
}
