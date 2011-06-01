package enigma.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Validates stated behavior when encoding a string with an {@link EnigmaMachine}.
 * 
 * @author Aaron Kuehler
 * 
 */
public class WhenMappingWithEnigmaMachine {

    @Test
    public void shouldProduceEncodedCandidate() throws Exception {
	
	// Configure machine parts
	Rotor rotor1 = new Rotor("QWERTYUIOPLKJHGFDSAZXCVBNM");
	Rotor rotor2 = new Rotor("ZAQWSXCDERFVBGTYHNMJUIKLOP");
	Rotor rotor3 = new Rotor("PLOKMIJNUHBYGVTFCRDXESZWAQ");
	Reflector reflector = new Reflector("NPKMSLZTWQCFDAVBJYEHXOIURG");
	
	String expected = "WGJOUNHUPLJRETLGWMNRPIIXUDM";
	
	// Instantiate machine
	EnigmaMachine enigmaMachine = new EnigmaMachine(rotor1, rotor2, rotor3, reflector);
	
	// Encode known value
	String candidate = "AAAAAAAAAAAAAAAAAAAAAAAAAAA";
	
	enigmaMachine.setRotors(5, 9, 14);
	String encoding = enigmaMachine.encodeLine(candidate);
	assertEquals(expected, encoding);
    }
    
    @Test
    public void shouldEncodeBackToOriginalValue() throws Exception {
	
	// Configure machine parts
	Rotor rotor1 = new Rotor("QWERTYUIOPLKJHGFDSAZXCVBNM");
	Rotor rotor2 = new Rotor("ZAQWSXCDERFVBGTYHNMJUIKLOP");
	Rotor rotor3 = new Rotor("PLOKMIJNUHBYGVTFCRDXESZWAQ");
	Reflector reflector = new Reflector("NPKMSLZTWQCFDAVBJYEHXOIURG");
	
	String expected = "AAAAAAAAAAAAAAAAAAAAAAAAAAA";
	
	// Instantiate machine
	EnigmaMachine enigmaMachine = new EnigmaMachine(rotor1, rotor2, rotor3, reflector);
	
	// Encode known value
	String candidate = "WGJOUNHUPLJRETLGWMNRPIIXUDM";
	
	enigmaMachine.setRotors(5, 9, 14);
	String encoding = enigmaMachine.encodeLine(candidate);
	assertEquals(expected, encoding);
    }
}
