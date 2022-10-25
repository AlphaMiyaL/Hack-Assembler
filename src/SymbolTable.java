import java.util.Hashtable;

/**
 * @author Brian Song
 * @description Manages a table of symbols including pre-defined/user-defined variables and labels. 
 */
public class SymbolTable {
	private int currentReg;								//Current register - stores register address of next var assigned in decimal
	private final Hashtable<String, Integer> symTable;	//Stores symbol table
	
	public SymbolTable() {
		currentReg = 16;
		symTable = new Hashtable<String, Integer>(25);
		
		//initalize pre-defined variables
		for(int i=0; i<16; i++) {
			final String key = "R" + i;
			symTable.put(key, i);
		}
		
		symTable.put("SCREEN", 16384);
		symTable.put("KBD", 24576);
		symTable.put("SP", 0);
		symTable.put("LCL", 1);
		symTable.put("ARG", 2);
		symTable.put("THIS", 3);
		symTable.put("THAT", 4);
	}
	
	/**
	 * Adds variable defined by 'symbol' if not in Symbol Table already
	 * New variables get value of currentReg starting from 16
	 * @param symbol The symbol added to table
	 * @return True if successfully added, false otherwise
	 */
	public boolean addVar(final String symbol) {
		if(!symTable.containsKey(symbol)) {
			symTable.put(symbol, currentReg);
			currentReg++;
			return true;
		}
		return false;
	}
	
	/**
	 * interface method for putting key-value pairs into Symbol Table
	 * @param symbol Symbol put into table
	 * @param value	Value associated with symbol
	 */
	public void put(final String symbol, final int value) {
		symTable.put(symbol, value);
	}
	
	/**
	 * interface method for checking if Symbol Table has given key
	 * @param symbol Symbol to check in table
	 * @return True if table contains symbol, false otherwise
	 */
	public boolean contains(final String symbol) {
		return symTable.containsKey(symbol);
	}
	
	/**
	 * interface method for retrieving value with given key
	 * @param symbol Key for table to retrieve value pair
	 * @return Value of symbol key in Symbol Table, null otherwise
	 */
	public int get(final String symbol) {
		return symTable.get(symbol);
	}
}
