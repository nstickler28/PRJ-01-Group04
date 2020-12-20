package model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chun Kit Kwong
 * @author Nathan Stickler
 * @author Loren Mendoza
 * @author Wynn Siripanich
 * <p>
 * This class provides the read and write functions
 * of the memory address
 */

public class Memory {
	// memory dump data
	private Map<Integer, Byte> memoryDump = new HashMap<>();

	/**
	 * load bytes into memory from address 0.
	 *
	 * @param bytes data to be loaded into memory
	 */
	public void load(byte[] bytes) {
		putBytes(0, bytes);
	}

	/**
	 * get one byte at given address in memory.
	 *
	 * @param address
	 *
	 * @return byte content at given address
	 */
	public byte getByte(int address) {
		return memoryDump.getOrDefault(address, (byte) 0);
	}

	/**
	 * get some bytes starting from given address in memory.
	 *
	 * @param address
	 * @param count   byte numbers
	 *
	 * @return bytes content starting from given address
	 */
	public byte[] getBytes(int address, int count) {
		byte[] bytes = new byte[count];
		for (int i = 0; i < count; i++) {
			bytes[i] = memoryDump.getOrDefault(address + i, (byte) 0);
		}
		return bytes;
	}

	public int getWord(int address) {
		byte first = memoryDump.getOrDefault(address, (byte) 0);
		byte second = memoryDump.getOrDefault(address + 1, (byte) 0);
		return ((first & 0xff) << 8) | (second & 0xff);
	}


	/**
	 * put one byte into given address in memory.
	 *
	 * @param address
	 * @param data
	 */
	public void putByte(int address, byte data) {
		memoryDump.put(address, data);
	}

	/**
	 * put two bytes into given address in memory.
	 *
	 * @param address
	 * @param word
	 */
	public void putWord(int address, int word) {
		memoryDump.put(address, (byte) ((word >> 8) & 0xff));
		memoryDump.put(address + 1, (byte) (word & 0xff));
	}

	/**
	 * put any number of bytes into given address in memory.
	 *
	 * @param address
	 * @param data
	 */
	void putBytes(int address, byte[] data) {
		for (int i = 0; i < data.length; i++) {
			memoryDump.put(address + i, data[i]);
		}
	}

	/**
	 * clear memory.
	 */
	public void reset() {
		memoryDump.clear();
	}
}

