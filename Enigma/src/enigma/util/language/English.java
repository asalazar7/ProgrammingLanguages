package enigma.util.language;

/**
 * A utility class which provides useful facts about the English language.
 * 
 * @author Aaron Kuehler
 * 
 */
public class English {

    /**
     * Returns the integer position of the character representing the start of
     * the alphabet in the Unicode character map.
     */
    public static final int ALPHABET_START_UNICODE_OFFSET = 'A';

    /**
     * Returns the integer position of the character representing the end of the
     * alphabet in the Unicode character map.
     */
    public static final int ALPHABET_END_UNICODE_OFFSET = 'Z';

    // The size of the alphabet
    public static final int MAX = 26;

    // Known letter frequencies
    private static final double[] letterFrequency = { 8.1, 1.6, 3.2, 3.6, 12.3,
	    2.3, 1.6, 5.1, 7.2, 0.1, 0.5, 4.0, 2.2, 7.2, 7.9, 2.3, 0.2, 6.0,
	    6.6, 9.6, 3.1, 0.9, 2.0, 0.2, 1.9, 0.1 };

    // Accepted frequency deviation
    private static final int[] letterDeviation = { 10, 50, 30, 30, 10, 20, 50,
	    20, 15, 100, 80, 30, 30, 20, 20, 30, 100, 30, 20, 15, 30, 60, 40,
	    100, 40, 100 };

    // Storage for observed letter frequencies
    private static int[] letterCount;

    // Stores the total number of letters read into a string during the
    // counting phase.
    private static int totalNumberOfLetters;

    /**
     * Tests the target string to see if it is reasonable to assume that it
     * contains English text.
     * 
     * This method is not thread-safe.
     * 
     * @param target
     * @param toleranceMultiplier
     * @param acceptedNumberOfErrors
     * @return
     */
    public static boolean isProbablyEnglish(String target,
	    double toleranceMultiplier, int acceptedNumberOfErrors) {
	countAllLetters(target);
	return (getErrorCount(toleranceMultiplier) <= acceptedNumberOfErrors);

    }

    /**
     * Counts the frequency of each character in the alphabet in the input
     * target string.
     * 
     * @param target
     *            The non-null string on which to operate.
     */
    private static void countAllLetters(String target) {

	// Validate that the input string
	if (target == null) {
	    throw new IllegalArgumentException(
		    "Cannot count the letter frequencies of [null]");
	}
	letterCount = new int[MAX];
	totalNumberOfLetters = 0;
	for (char character : target.toCharArray()) {
	    if (!Character.isWhitespace(character) && isInAlphabet(character)) {
		letterCount[alphabeticIndexOf(character)] += 1;
		totalNumberOfLetters++;
	    }
	}
    }

    /**
     * Inspects the current state of the letter counts against expected
     * frequencies. This method returns the number of instances in which the
     * actual frequency of a letter occurs outside the expected frequency range.
     * 
     * @param toleranceMultiplier
     *            Adjusts the tolerance of the distribution filter.
     * @return Total count of letters which fell outside the tolerance filter.
     */
    private static int getErrorCount(double toleranceMultiplier) {

	int errorCount = 0;
	int observedCharacterCount;
	double expectedFrequency;
	double observedFrequency;
	double toleranceLowerBound;
	double toleranceUpperBound;
	double deviation;
	for (int index = 0; index < MAX; index++) {

	    // Gather and compute facts about the current character's frequency
	    observedCharacterCount = letterCount[index];
	    expectedFrequency = letterFrequency[index];
	    observedFrequency = observedCharacterCount > 0 ? ((double) observedCharacterCount / (double) totalNumberOfLetters) * 100
		    : observedCharacterCount;

	    // Calculate the range of acceptance for the current character
	    deviation = (toleranceMultiplier * (letterDeviation[index] / 100D))
		    * expectedFrequency;
	    toleranceLowerBound = expectedFrequency - deviation;
	    toleranceUpperBound = expectedFrequency + deviation;

	    if (observedFrequency < toleranceLowerBound
		    || observedFrequency > toleranceUpperBound) {
		errorCount++;
	    }
	}
	return errorCount;
    }

    /**
     * Returns true if the input character is in the "English" alphabet; false
     * otherwise.
     * 
     * @param character
     * @return
     */
    public static boolean isInAlphabet(char character) {
	return (character >= ALPHABET_START_UNICODE_OFFSET && character <= ALPHABET_END_UNICODE_OFFSET);
    }

    /**
     * Calculates the alphabetic index of a character based on the mapping that
     * A = 0, B = 1... Z = 25.
     * 
     * @param character
     * @return
     */
    public static int alphabeticIndexOf(char character) {
	return character - ALPHABET_START_UNICODE_OFFSET;
    }
}
