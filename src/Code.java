import java.util.Hashtable;

/**
 * @author Brian Song
 * @description Translates given field into corresponding value
 */
public class Code {
	//Storing specifications of each field: dest, jump, comp
	private static Hashtable<String, String> destTable = new Hashtable<String, String>(8);
    private static Hashtable<String, String> jumpTable = new Hashtable<String, String>(8);
    private static Hashtable<String, String> compTable = new Hashtable<String, String>(28);

    /**
     * Stores destination codes
     */
    private static void initDestTable() {
    	destTable.put("null", "000");
    	destTable.put("M", "001");	// Memory (RAM)
    	destTable.put("D", "010");	// D-register
    	destTable.put("MD", "011");
    	destTable.put("A", "100");	// A-register
    	destTable.put("AM", "101");
    	destTable.put("AD", "110");
    	destTable.put("AMD", "111");
    }
    
    /**
     * Stores jump codes
     */
    private static void initJumpTable() {
    	jumpTable.put("null", "000");	// no jump
    	jumpTable.put("JGT", "001");	// jump if greater than zero
    	jumpTable.put("JEQ", "010");	// jump if equal to zero
    	jumpTable.put("JGE", "011");	// jump is greater than or equal to zero
    	jumpTable.put("JLT", "100");	// jump if less than zero
        jumpTable.put("JNE", "101");	// jump if not equal to zero
        jumpTable.put("JLE", "110");	// jump if less than or equal to zero
        jumpTable.put("JMP", "111");	// jump
    }
    
    /**
     * Stores comp codes
     */
    private static void initCompTable() {
    	compTable.put("0", "0101010");
    	compTable.put("1", "0111111");
    	compTable.put("-1", "0111010");
    	compTable.put("D", "0001100");
    	compTable.put("A", "0110000");
    	compTable.put("!D", "0001101");
    	compTable.put("!A", "0110001");
    	compTable.put("-D", "0001111");
    	compTable.put("-A", "0110011");
    	compTable.put("D+1", "0011111");
    	compTable.put("A+1", "0110111");
    	compTable.put("D-1", "0001110");
    	compTable.put("A-1", "0110010");
    	compTable.put("D+A", "0000010");
    	compTable.put("D-A", "0010011");
    	compTable.put("A-D", "0000111");
    	compTable.put("D&A", "0000000");
    	compTable.put("D|A", "0010101");
    	compTable.put("M", "1110000");
    	compTable.put("!M", "1110001");
    	compTable.put("-M", "1110011");
    	compTable.put("M+1", "1110111");
    	compTable.put("M-1", "1110010");
    	compTable.put("D+M", "1000010");
    	compTable.put("D-M", "1010011");
    	compTable.put("M-D", "1000111");
    	compTable.put("D&M", "1000000");
    	compTable.put("D|M", "1010101");
    }
    
    /**
     * Retrieves binary code given by key, representing computation bits
     * @param Symbolic key for computation bits
     * @return 7-bit binary string - computation instruction
     */
    public static String getCompCode(final String key) {
    	initCompTable();
    	return compTable.get(key);
    }
    
    /**
     * Retrieves binary code given by key, representing computation instruction
     * @param Symbolic key for destination instruction
     * @return 3-bit binary string - destination instruction
     */
    public static String getDestCode(final String key) {
    	initDestTable();
    	return destTable.get(key);
    }
    
    /**
     * Retrieves binary code given by key, representing jump instruction
     * @param Symbolic key for jump instruction
     * @return 3-bit binary string - jump instruction
     */
    public static String getJumpCode(final String key) {
    	initJumpTable();
    	return jumpTable.get(key);
    }  
}
