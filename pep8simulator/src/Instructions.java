import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Instructions {

	private String endCall;
	
	Map<String, String> uMnemonic;
	
	Map<String, String> aMnemonic;
	
	Map<String, String> rMnemonic;
	
	Map<String, String> aaaMnemonic;
	
	Map<String, String> raaaMnemonic;
	
	Map<String, String> addrsModesMap;
	
	Map<String, String> shortAddrsModesMap;
	
	Map<String, String> registerFieldMap;
	
	ArrayList<String> dotCommands;
	
	
	public Instructions() {
		super();
		
		this.endCall = "";
		
		this.uMnemonic = new HashMap<>();
		this.aMnemonic = new HashMap<>();
		this.rMnemonic = new HashMap<>();
		this.aaaMnemonic = new HashMap<>();
		this.raaaMnemonic = new HashMap<>();
		
		this.addrsModesMap = new HashMap<>();
		this.shortAddrsModesMap = new HashMap<>();
		this.registerFieldMap = new HashMap<>();
		
		this.dotCommands = new ArrayList<>();
		
		opcodeMapCodes();
		addrsMapCodes();
		registerMapCodes();
		dotCommandArray();
	}
	
	private void dotCommandArray() {
		this.dotCommands.add(".ADDRSS");
		this.dotCommands.add(".ASCII");
		this.dotCommands.add(".BLOCK");
		this.dotCommands.add(".BURN");
		this.dotCommands.add(".BYTE");
		this.dotCommands.add(".END");
		this.dotCommands.add(".EQUATE");
		this.dotCommands.add(".WORD");
	}
	
	private void registerMapCodes() {
		this.registerFieldMap.put("A", "0");
		this.registerFieldMap.put("X", "1");
	}
	
	private void opcodeMapCodes() {
		this.uMnemonic.put("STOP", 		"00000000");
		this.uMnemonic.put("RETTR", 	"00000001");
		this.uMnemonic.put("MOVSPA", 	"00000010");
		this.uMnemonic.put("MOVFLGA", 	"00000011");
		
		this.aMnemonic.put("BR", 	"0000010");
		this.aMnemonic.put("BRLE", 	"0000011");
		this.aMnemonic.put("BRLT", 	"0000100");
		this.aMnemonic.put("BREQ", 	"0000101");
		this.aMnemonic.put("BRNE", 	"0000110");
		this.aMnemonic.put("BRGE", 	"0000111");
		this.aMnemonic.put("BRGT", 	"0001000");
		this.aMnemonic.put("BRV", 	"0001001");
		this.aMnemonic.put("BRC", 	"0001010");
		this.aMnemonic.put("CALL", 	"0001011");
		
		this.rMnemonic.put("NOTA", 	"00011000");
		this.rMnemonic.put("NEGA", 	"00011010");
		this.rMnemonic.put("ASLA", 	"00011100");
		this.rMnemonic.put("ASRA", 	"00011110");
		this.rMnemonic.put("ROLA", 	"00100000");
		this.rMnemonic.put("RORA", 	"00100010");
		
		this.rMnemonic.put("NOTX", 	"00011001");
		this.rMnemonic.put("NEGX", 	"00011011");
		this.rMnemonic.put("ASLX", 	"00011101");
		this.rMnemonic.put("ASRX", 	"00011111");
		this.rMnemonic.put("ROLX", 	"00100001");
		this.rMnemonic.put("RORX", 	"00100011");
		
		this.aaaMnemonic.put("NOP", 	"00101");
		this.aaaMnemonic.put("DECI", 	"00110");
		this.aaaMnemonic.put("DECO", 	"00111");
		this.aaaMnemonic.put("STRO", 	"01000");
		this.aaaMnemonic.put("CHARI", 	"01001");
		this.aaaMnemonic.put("CHARO", 	"01010");
		
		this.aaaMnemonic.put("ADDSP", 	"01100");
		this.aaaMnemonic.put("SUBSP", 	"01101");
		
		this.raaaMnemonic.put("ADDA", 		"0111");
		this.raaaMnemonic.put("SUBA", 		"1000");
		this.raaaMnemonic.put("ANDA", 		"1001");
		this.raaaMnemonic.put("ORA", 		"1010");
		this.raaaMnemonic.put("CPA", 		"1011");
		this.raaaMnemonic.put("LDA", 		"1100");
		this.raaaMnemonic.put("LDBYTEA", 	"1101");
		this.raaaMnemonic.put("STA", 		"1110");
		this.raaaMnemonic.put("STBYTEA", 	"1111");
	}
	
	private void addrsMapCodes() {
		this.addrsModesMap.put("i", "000");
		this.addrsModesMap.put("d", "001");
		this.addrsModesMap.put("n", "010");
		this.addrsModesMap.put("s", "011");
		this.addrsModesMap.put("sf", "100");
		this.addrsModesMap.put("x", "101");
		this.addrsModesMap.put("sx", "110");
		this.addrsModesMap.put("sfx", "111");
		
		this.shortAddrsModesMap.put("i", "0");
		this.shortAddrsModesMap.put("x", "1");
	}
}
