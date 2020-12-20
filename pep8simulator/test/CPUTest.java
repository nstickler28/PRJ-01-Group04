//import controller.CPU;
//import model.Memory;
//import view.IO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CPUTest {
	private CPU cpu;
	private Memory memory;
	private IO io;
	private JTextArea input;
	private JTextArea output;

	@BeforeEach
	public void setup() {
		cpu = new CPU();
		memory = new Memory();
		io = new IO();
		input = new JTextArea();
		output = new JTextArea();
		io.setInputDevice(input);
		io.setOutputDevice(output);
	}

	@Test
	public void testCharOutputHi() {
		String objCode = "51 00 07 51 00 08 00 48 69 zz";
		loadObjIntoMemory(objCode);
		cpu.run(memory, io);
		assertEquals("Hi", output.getText());
	}


	@Test
	public void testCharInput() {
		String objCode = "49 00 0D 49 00 0E 51 00 0E 51 00 0D 00 zz";
		loadObjIntoMemory(objCode);
		input.setText("up");
		cpu.run(memory, io);
		assertEquals("pu", output.getText());
	}

	@Test
	public void testWrongInstructionExecptionCapture() {
		String objCode = "e0 zz";
		loadObjIntoMemory(objCode);
		int ret = cpu.run(memory, io);
		assertEquals(-1, ret);
	}

	@Test
	public void testADDAi(){
		String objCode = "70 00 05 00 zz";
		loadObjIntoMemory(objCode);
		int ret = cpu.run(memory, io);
		assertEquals(5, cpu.aRegister);
//		assertEquals(5, cpu.getRegisters().getAccumulator());
	}


	@Test
	public void testADDXi(){
		String objCode = "78 00 05 00 zz";
		loadObjIntoMemory(objCode);
		int ret = cpu.run(memory, io);
//		assertEquals(5, cpu.aRegister);
//		assertEquals(5, cpu.getRegisters().getIndexRegister());
	}

	@Test
	public void testADDAd(){
		String objCode = "71 00 04 00 00 05 zz";
		loadObjIntoMemory(objCode);
		int ret = cpu.run(memory, io);
		assertEquals(5, cpu.aRegister);
//		assertEquals(5, cpu.getRegisters().getAccumulator());
	}

	@Test
	public void testADDAn(){
		String objCode = "72 00 04 00 00 06 00 01 zz";
		loadObjIntoMemory(objCode);
		int ret = cpu.run(memory, io);
		assertEquals(1, cpu.aRegister);
//		assertEquals(1, cpu.getRegisters().getAccumulator());
	}

	@Test
	public void testSUBAi(){
		String objCode = "70 00 05 80 00 03 00 zz";
		loadObjIntoMemory(objCode);
		int ret = cpu.run(memory, io);
		assertEquals(2, cpu.aRegister);
//		assertEquals(2, cpu.getRegisters().getAccumulator());
	}

	@Test
	public void testORAi(){
		String objCode = "70 00 01 a0 00 02 00 zz";
		loadObjIntoMemory(objCode);
		int ret = cpu.run(memory, io);
		assertEquals(3, cpu.aRegister);
//		assertEquals(3, cpu.getRegisters().getAccumulator());
	}

	@Test
	public void testANDAi(){
		String objCode = "70 00 01 90 00 02 00 zz";
		loadObjIntoMemory(objCode);
		int ret = cpu.run(memory, io);
		assertEquals(0, cpu.aRegister);
//		assertEquals(0, cpu.getRegisters().getAccumulator());
	}

	@Test
	public void testAddAndOr() {
		String objCode = "C1 00 11 71 00 13 71 00 15 F1 00 10 51 00 10 00 00 00 05 00 03 00 30 zz";
		loadObjIntoMemory(objCode);
		int ret = cpu.run(memory, io);
		assertEquals("8", output.getText());
		assertEquals(0, ret);
	}

	@Test
	public void testReset() {
		String objCode = "C1 00 11 71 00 13 71 00 15 F1 00 10 51 00 10 00 00 00 05 00 03 00 30 zz";
		loadObjIntoMemory(objCode);
		int ret = cpu.run(memory, io);
		cpu.reset();
		assertEquals(0, cpu.aRegister);
//		assertEquals(0, cpu.getRegisters().getProgramCounter());

	}

//	@Test
//	public void testForLoop() {
//		String objCode = "C0 00 00 68 00 02 C0 00 05 E3 00 00 C0 00 00 C3 00 00 70 00 01 E3 00 00 41 00 28 3B 00 00" +
//                         " B0 00 0A 06 00 0C 00 6E 75 6D 2B 2B 20 52 65 73 75 6C 74 3A 20 zz";
//		loadObjIntoMemory(objCode);
//		int ret = cpu.run(memory, io);
//		cpu.reset();
//		assertEquals("67891011", io.getOutputDevice().getText());
//	}

	private void loadObjIntoMemory(String objCode) {
		String[] byteStrings = objCode.split(" ");
		List<Byte> byteList = new ArrayList<>();
		for (String byteString : byteStrings) {
			// obj code ends when meeting 'zz'
			if (byteString.startsWith("zz")) {
				break;
			}
			byteList.add(Integer.valueOf(byteString, 16).byteValue());
		}

		// load obj bytes into memory and display them
		byte[] bytes = new byte[byteList.size()];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = byteList.get(i);
		}
		memory.load(bytes);
	}
}
