import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Brian Song
 * @description Translates code in Hack Assembly to machine code
 */
public class HackAssembler {	
	private final SymbolTable symbols;		//predefined/userdefined symbols and labels
	private int currentCommand;				//current valid line in file I/O
	private Parser parser; 					//parses Hack Assembly commands into individual parts
	
	public HackAssembler() {
		symbols = new SymbolTable();
		currentCommand = 0;
	}
	
	/**
	 * First pass on file; Notes only labels
	 * Add labels to SymbolTable on first occurence
	 * @param filename The .asm file to parse
	 */
	private void firstPass(final String filename) {
		try{
			final BufferedReader input = new BufferedReader(new FileReader(filename));
			//parse err flag
			boolean parseSuccess;
			String command;
			
			while((command = input.readLine()) != null) {
				parser = new Parser();
				parseSuccess = parser.parseCommand(command);
				
				if(parseSuccess) {
					//Checking for Labels
					if(command.trim().charAt(0) == '(') {
						//extract label symbol
						final String symbol = command.trim().substring(command.indexOf("(")+1, command.lastIndexOf(")"));
						//add label to SymbolTable if new
						if(!symbols.contains(symbol)) {
							symbols.put(symbol, currentCommand);
						}
						//label declaration - stay on same command (subtract cuz add on out of loop)
						currentCommand--;
					}
					//Successfully parsed command - increment to next line
					currentCommand++;
				}
			}
			input.close();
		}
		catch(final IOException e) {
			//e.printStackTrace();
			System.out.println(e);
			return;
		}
	}
	
	/**
	 * (second pass)
	 * Translates Hack Assembly(.asm) into machine code(.hack) after first pass
	 * @param filename Assembly file to translate into machine code
	 */
	private void translate(final String filename) {
		try{
			//changing file extension from .asm to .hack
			final String outFileName = filename.substring(0, filename.indexOf(".")) + ".hack";
			final BufferedReader input = new BufferedReader(new FileReader(filename));
			final PrintWriter output = new PrintWriter(outFileName);
			//reset currentCommand counter
			currentCommand = 0;
			//parse err flag
			boolean parseSuccess;
			String command;
			
			while((command = input.readLine())!= null) {
				parser = new Parser();
				parseSuccess = parser.parseCommand(command);
				
				//discluding label declarations
				if(parseSuccess && command.trim().charAt(0) != '(') {
					//Parsing C instruction
					if(parser.getAddr() == null) {
						final String comp = Code.getCompCode(parser.getComp());
                        final String dest = Code.getDestCode(parser.getDest());
                        final String jump = Code.getJumpCode(parser.getJump());
                        output.printf("111%s%s%s\n", comp, dest, jump);
					}
					//Parsing A instruction
					else {
						final String var = parser.getAddr();
						final Scanner sc = new Scanner(var);
						//if var has an integer
						if(sc.hasNextInt()) {
							//convert to binary
							final String addrBinary = Integer.toBinaryString(Integer.parseInt(var));
							//write 16-bit binary to output
							output.println(padBinary(addrBinary));
						}
						else {
							symbols.addVar(var);
							final String addrBinary = Integer.toBinaryString(symbols.get(var));
							output.println(padBinary(addrBinary));
						}
						sc.close();
					}
					currentCommand++;
				}
			}
			input.close();
			output.close();
			System.out.println("File Successfully Assembled. Please check the file location for the hack file");
			System.out.println("(Since the asm files are currently located in and loaded from the resources folder, it will be created there)");
		}
		catch(final IOException e) {
			//e.printStackTrace();
			System.out.println(e);
			return;
		}
	}
	
	/**
	 * Pad binary String w/ zeros to ensure 16-bits
	 * @param unpaddedBinary Binary string w/o leading 0s
	 * @return 16-bit string with leading 0s
	 */
	private String padBinary(final String unpaddedBinary) {
		String paddedBinary = "";
		final int zeros = 16 - unpaddedBinary.length();
		
		for(int i=0; i<zeros; i++) {
			paddedBinary += "0";
		}
		
		return paddedBinary+unpaddedBinary;
	}
	
	/**
	 * !!!!!!!!!!!IMPORTANT!!!!!!!!!!!!
	 * Please uncomment the other filename line and comment the Scanner kb until kb close if wanting to use the 
	 * java HackAssembler FILENAME.asm command line method
	 * Otherwise, if using an IDE, keep as is
	 * 
	 * 
	 * Command Line Interface for running the Hack Assembler
	 * Input filename as resources/FILENAME.asm
	 * @param args
	 */
	public static void main(final String[] args) {
		//for IDE
		Scanner kb = new Scanner(System.in);
		System.out.print("Please enter the file name of the assembly file: ");
		final String filename = kb.nextLine();
		kb.close();
		//for command line version
//		final String filename = args[0];
		final HackAssembler assembler = new HackAssembler();
		assembler.firstPass(filename);
		assembler.translate(filename);
	}	
}
