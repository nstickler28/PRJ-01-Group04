import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Chun Kit Kwong
 * @author Nathan Stickler
 * @author Loren Mendoza
 * @author Wynn Siripanich
 * 
 * This class provides the read and write functions 
 * of the memory address
 *
 */

public class Memory {
    // memory dump data
    private Map<Integer, Byte> memoryDump = new HashMap<>();
    
    private int ROWS = 10;
    private int COLUMNS = 8;
    private int rowCount = 0;
    private int colCount = 0;
    private int mapIndex = 0;
    private String[][] memoryText = new String[ROWS][COLUMNS];
    private char[][] asciiText = new char[ROWS][COLUMNS];

    // load bytes into memory from address 0
    void load(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            memoryDump.put(i, bytes[i]); }
        
        for (int i = 0; i < asciiText.length; i ++) {
        	for (int j = 0; j < asciiText[0].length; j++) {
        		asciiText[i][j] = '.';
        	} }
        
    }

    // get one byte from the address
    byte getByte(int address) {
        return memoryDump.getOrDefault(address, (byte) 0);
    }

    // get bytes starting from the address
    byte[] getBytes(int address, int count) {
        byte[] bytes = new byte[count];
        for (int i = 0; i < count; i++) {
            bytes[i] = memoryDump.getOrDefault(address + i, (byte) 0);
        }
        return bytes;
    }

    // put one bytes into the address
    void putByte(int address, byte data) {
        memoryDump.put(address, data);
    }

    // put two bytes into the location starting from the address
    void putWord(int address, int word) {
        memoryDump.put(address, (byte) ((word >> 8) & 0xff));
        memoryDump.put(address + 1, (byte) (word & 0xff));
    }

    // put bytes into the location starting from the address
    void putBytes(int address, byte[] data) {
        for (int i = 0; i < data.length; i++) {
            memoryDump.put(address + i, data[i]);
        }
    }
    
    //fill Memory Dump text
    public void loadText(String hex) {
    	if (!hex.equals("zz")) {
	    	memoryText[rowCount][colCount] = hex;
	    	if ((mapIndex + 1) % 3 == 0) {
	    		asciiText[rowCount][colCount] = (char) Integer.parseInt(hex, 16);
	    	}
	    	colCount++;
	    	mapIndex++;
	    	if (colCount == 7) {
	    		rowCount++;
	    		colCount -= 7;
	    	} }
    }
    
    public char[][] getASCII() {
    	return asciiText;
    }
    
    public String[][] getDump() {
    	return memoryText;
    }

    // clear memory
    public void reset() {
    	colCount = 0;
    	rowCount = 0;
    	mapIndex = 0;
    	for (int i = 0; i < ROWS; i++) {
    		for (int j = 0; j < COLUMNS; j++) {
    			memoryText[i][j] = "";
    			asciiText[i][j] = '.';
    		} }
        memoryDump.clear();
    }
}

