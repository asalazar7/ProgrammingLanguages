package enigma.driver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import enigma.model.EnigmaMachine;
import enigma.model.Reflector;
import enigma.model.Rotor;
import enigma.util.language.English;

/**
 * A consumer of the {@link EnigmaMachine}. This class starts a main thread to
 * compute the rotor settings needed to decrypt a text file.
 * 
 * @param args
 * @throws IOException
 */
public class FindRotorSettings {

    // Path to input file which contains the encrypted text
    private static final String INPUT_FILE_PATH = "src/enigma/resource/encrypted.text";

    // Sample size, in number of lines, to use in search for rotor settings.
    private static final int numberOfLines = 3;

    // Maximum number of errors allowed in the frequency deviation calculation
    // before a rotor settings candidate is disqualified.
    private static final int errorsAllowed = 7;

    // A bias used to adjust the frequency deviation.
    private static final double multiplier = 1.6;

    /**
     * Starts a main thread which uses a decoded sample from the file at
     * FILE_INPUT_PATH to attempt to discover the rotor settings used to create
     * the encoding.
     * 
     * Certain facts about the language are used to compare known alphabetic
     * symbol frequencies to computed occurrences of alphabetic symbols in the
     * sample.
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

	// Get a sample from the encrypted file.
	File inputFile = new File(INPUT_FILE_PATH);
	String sample = takeSample(inputFile);

	// Brute force and analyze decryption results for matches against the
	// English language; essentially try to hunt down the rotor settings
	// which yield the original clear text.
	String candidate;
	for (int rotor3Position = 0; rotor3Position < Rotor.MAX; rotor3Position++) {
	    for (int rotor2Position = 0; rotor2Position < Rotor.MAX; rotor2Position++) {
		for (int rotor1Position = 0; rotor1Position < Rotor.MAX; rotor1Position++) {

		    // Configure the staring position of the Enigma machine
		    enigmaMachine.setRotors(rotor1Position, rotor2Position,
			    rotor3Position);

		    // Encode the sample
		    candidate = enigmaMachine.encodeLine(sample);

		    // Analyze the candidate
		    if (English.isProbablyEnglish(candidate, multiplier, errorsAllowed)) {
			System.out.println("ROTOR CANDIDATE:" + "\n\tRotor 1: "
				+ rotor1Position + "\n\tRotor 2: "
				+ rotor2Position + "\n\tRotor 3: "
				+ rotor3Position + "\n");
		    }
		}
	    }
	}
    }

    /**
     * Retrieves a sample from the input file.
     * 
     * @param inputFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static String takeSample(File inputFile)
	    throws FileNotFoundException, IOException {

	// Read a sample from the inputFile
	String line;
	BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	StringBuilder sample = new StringBuilder();
	for (int index = 0; index < numberOfLines; index++) {
	    line = reader.readLine();
	    if (!line.isEmpty()) {
		sample.append(line).append(" ");
	    }

	}
	return sample.toString();
    }
}
