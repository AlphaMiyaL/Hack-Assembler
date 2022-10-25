
/**
 * @author Brian Song
 * @description Unloads string containing instructions into fields by Hack Lang specification
 */
public class Parser {
	private String dest;	//destination instruction
	private String comp;	//computation instruction
	private String jump;	//jump instruction
	private String addr;	//16 bit address
	
	public Parser() {
		dest = "null";
		jump = "null";
	}
	
	/** 
	 * Parses line of assembly instructions into different fields via Hack Lang specification
	 * @param command line of assembly instructions for parsing
	 * @return True if parsing was successful, false otherwise
	 */
	public boolean parseCommand(String command) {
		//no leading/trailing whitespace
		command = command.trim();
		
		if(!command.isEmpty()) {
			//Checking if command is a comment
			if(command.charAt(0) != '/') {
				//A instruction
				if(command.contains("@")) {
					//contains either label, variable, or address nbr
					addr = command.split("@")[1].trim();
				}
				//C instruction
				else {
					//Contains dest, comp, and maybe jump fields
					if(command.contains("=")) {
						final String[] fields = command.split("=");
						dest = fields[0];
						//Check for jump field
						if(fields[1].contains(";")) {
							splitJump(fields[1]);
						}
						else {
							//Remove whitespace and comments
							comp = fields[1].split("/")[0].trim();
						}
					}
					//Contains comp and maybe jump field
					else if(command.contains("+") || command.contains("-")) {
						//Check for jump field
						if(command.contains(";")) {
							splitJump(command);
						}
						else {
							//Remove whitespace and comments
							comp = command.split("/")[0].trim();
						}
					}
					//Contains jump field and maybe comp
					else if(command.contains(";")) {
						//Handle jump field
						splitJump(command);
					}
					else {
						//Remove whitespace and comments
						jump = command.split("/")[0].trim();
					}
				}
				//Parsing Successful
				return true;
			}
		}
		//Parsing Failed
		return false;
	}
	
	/**
	 * Helper Fuction
	 * Checks for jump field and handles it  
	 * @param str string to check for jump field in
	 */
	private void splitJump(final String str) {
		final String[] parts = str.split(";");
		comp = parts[0].trim();
		jump = parts[1].split("/")[0].trim();
	}
	
	/**
	 * Getters for each intruction
	 * @return respective field of intruction
	 */
	
	public String getDest() {
		return dest;
	}
	
	public String getComp() {
		return comp;
	}
	
	public String getJump() {
		return jump;
	}
	
	// if A instruction, returns label, variable, or address nbr
	public String getAddr() {
		return addr;
	}
	
	
}
