package constants;

import java.util.HashSet;
import java.util.Set;

/**
 * Constant for instruction types.
 */
public class InstructionTypes {

	/**
	 * 8bits fixed long instruction specifier
	 */
	public static final int STOP = 0b00000000;
	public static final int RETTR = 0b00000001;
	public static final int MOVSPA = 0b00000010;
	public static final int MOVFLGA = 0b00000011;

	/**
	 * 7bits fixed long instruction specifier
	 */
	public static final int BR = 0b00000100;
	public static final int BRLE = 0b00000110;
	public static final int BRLT = 0b00001000;
	public static final int BREQ = 0b00001010;
	public static final int BRNE = 0b00001100;
	public static final int BRGE = 0b00001110;
	public static final int BRGT = 0b00010000;
	public static final int BRV = 0b00010010;
	public static final int BRC = 0b00010100;
	public static final int CALL = 0b00010110;
	public static final int NOTR = 0b00011000;
	public static final int NEGR = 0b00011010;
	public static final int ASLR = 0b00011100;
	public static final int ASRR = 0b00011110;
	public static final int ROLR = 0b00100000;
	public static final int RORR = 0b00100010;

	/**
	 * 6bits fixed long instruction specifiers.
	 */
	public static final int NOPN = 0b00100100;

	/**
	 * 5bits fixed long instruction specifiers.
	 */
	public static final int NOP = 0b00101000;
	public static final int DECI = 0b00110000;
	public static final int DECO = 0b00111000;
	public static final int STRO = 0b01000000;
	public static final int CHARI = 0b01001000;
	public static final int CHARO = 0b01010000;
	public static final int RETN = 0b01011000;
	public static final int ADDSP = 0b01100000;
	public static final int SUBSP = 0b01101000;

	/**
	 * 4bits fixed long instruction specifiers.
	 */
	public static final int ADDR = 0b01110000;
	public static final int SUBR = 0b10000000;
	public static final int ANDR = 0b10010000;
	public static final int ORR = 0b10100000;
	public static final int CPR = 0b10110000;
	public static final int LDR = 0b11000000;
	public static final int LDBYTER = 0b11010000;
	public static final int STR = 0b11100000;
	public static final int STBYTER = 0b11110000;

	public static Set<Integer> UNARY_INSTRUCTIONS = new HashSet<>();
	public static Set<Integer> INSTRUCTIONS_HAVING_8_FIXED_BITS = new HashSet<>();
	public static Set<Integer> INSTRUCTIONS_HAVING_7_FIXED_BITS = new HashSet<>();
	public static Set<Integer> INSTRUCTIONS_HAVING_6_FIXED_BITS = new HashSet<>();
	public static Set<Integer> INSTRUCTIONS_HAVING_5_FIXED_BITS = new HashSet<>();
	public static Set<Integer> INSTRUCTIONS_HAVING_4_FIXED_BITS = new HashSet<>();

	static {
		UNARY_INSTRUCTIONS.add(STOP);
		UNARY_INSTRUCTIONS.add(RETTR);
		UNARY_INSTRUCTIONS.add(MOVSPA);
		UNARY_INSTRUCTIONS.add(MOVFLGA);
		UNARY_INSTRUCTIONS.add(NOTR);
		UNARY_INSTRUCTIONS.add(NEGR);
		UNARY_INSTRUCTIONS.add(ASLR);
		UNARY_INSTRUCTIONS.add(ASRR);
		UNARY_INSTRUCTIONS.add(ROLR);
		UNARY_INSTRUCTIONS.add(RORR);
		UNARY_INSTRUCTIONS.add(NOPN);
		UNARY_INSTRUCTIONS.add(RETN);

		INSTRUCTIONS_HAVING_8_FIXED_BITS.add(STOP);
		INSTRUCTIONS_HAVING_8_FIXED_BITS.add(RETTR);
		INSTRUCTIONS_HAVING_8_FIXED_BITS.add(MOVSPA);
		INSTRUCTIONS_HAVING_8_FIXED_BITS.add(MOVFLGA);

		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(BR);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(BRLE);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(BRLT);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(BREQ);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(BRNE);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(BRGE);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(BRGT);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(BRV);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(BRC);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(CALL);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(NOTR);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(NEGR);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(ASLR);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(ASRR);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(ROLR);
		INSTRUCTIONS_HAVING_7_FIXED_BITS.add(RORR);

		INSTRUCTIONS_HAVING_6_FIXED_BITS.add(NOPN);

		INSTRUCTIONS_HAVING_5_FIXED_BITS.add(NOP);
		INSTRUCTIONS_HAVING_5_FIXED_BITS.add(DECI);
		INSTRUCTIONS_HAVING_5_FIXED_BITS.add(DECO);
		INSTRUCTIONS_HAVING_5_FIXED_BITS.add(STRO);
		INSTRUCTIONS_HAVING_5_FIXED_BITS.add(CHARI);
		INSTRUCTIONS_HAVING_5_FIXED_BITS.add(CHARO);
		INSTRUCTIONS_HAVING_5_FIXED_BITS.add(RETN);
		INSTRUCTIONS_HAVING_5_FIXED_BITS.add(ADDSP);
		INSTRUCTIONS_HAVING_5_FIXED_BITS.add(SUBSP);

		INSTRUCTIONS_HAVING_4_FIXED_BITS.add(ADDR);
		INSTRUCTIONS_HAVING_4_FIXED_BITS.add(SUBR);
		INSTRUCTIONS_HAVING_4_FIXED_BITS.add(ANDR);
		INSTRUCTIONS_HAVING_4_FIXED_BITS.add(ORR);
		INSTRUCTIONS_HAVING_4_FIXED_BITS.add(CPR);
		INSTRUCTIONS_HAVING_4_FIXED_BITS.add(LDR);
		INSTRUCTIONS_HAVING_4_FIXED_BITS.add(LDBYTER);
		INSTRUCTIONS_HAVING_4_FIXED_BITS.add(STR);
		INSTRUCTIONS_HAVING_4_FIXED_BITS.add(STBYTER);

	}

	public static boolean isUnaryInstruction(int instruction) {
		return UNARY_INSTRUCTIONS.contains(instruction);
	}

	public static int getInstructionType(int instruction) {
		if (INSTRUCTIONS_HAVING_8_FIXED_BITS.contains(instruction)) {
			return instruction;
		}

		// clear 8th bit
		instruction &= (~(1));
		if (INSTRUCTIONS_HAVING_7_FIXED_BITS.contains(instruction)) {
			return instruction;
		}

		// clear 7th bit
		instruction &= (~(1 << 1));
		if (INSTRUCTIONS_HAVING_6_FIXED_BITS.contains(instruction)) {
			return instruction;
		}

		// clear 6th bit
		instruction &= (~(1 << 2));
		if (INSTRUCTIONS_HAVING_5_FIXED_BITS.contains(instruction)) {
			return instruction;
		}

		// clear 5th bit
		instruction &= (~(1 << 3));
		if (INSTRUCTIONS_HAVING_4_FIXED_BITS.contains(instruction)) {
			return instruction;
		}
		return -1;
	}

	public static boolean isStoringTypeInstruction(int instructionType){
		return instructionType==STR||
		       instructionType==STBYTER;
	}

}
