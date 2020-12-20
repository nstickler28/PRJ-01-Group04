package controller;

import constants.InstructionTypes;
import model.CPURegisters;
import model.Memory;
import view.IO;

import static constants.AddressingMode.*;
import static constants.InstructionTypes.*;
import static constants.RegisterFieldType.ACCUMULATOR;
import static constants.RegisterFieldType.INDEX_REGISTER;

/**
 * @author Chun Kit Kwong
 * @author Nathan Stickler
 * @author Loren Mendoza
 * @author Wynn Siripanich
 * <p>
 * This class handles all the arithmetic of the controller.CPU.
 * The instruction in memory is fetched, decoded and executed.
 */

public class CPU {


	/**
	 * CPU registers
	 */
	private CPURegisters registers = new CPURegisters();

	/**
	 * CPU state variables
	 */
	private boolean halt = true;

	/**
	 * instruction decoding variable
	 */
	private int instructionType;
	private int addressingMode;
	private int registerField;
	private int operandSpecifier;
	private int finalOperand;

	/**
	 * Run the object code from memory and view.IO.
	 *
	 * @param memory the memory
	 * @param io     the io
	 *
	 * @return return 0
	 */
	public int run(Memory memory, IO io) {
		halt = false;
		while (!halt) {
			int ret = singleStep(memory, io);
			if (ret != 0) {
				return ret;
			}
		}
		return 0;
	}


	/**
	 * fetch-execute cycle
	 *
	 * @param memory
	 * @param io
	 *
	 * @return
	 */
	public int singleStep(Memory memory, IO io) {
		try {
			fetchInstruction(memory);
			decodeInstruction();
			fetchData(memory);
			return executeInstruction(memory, io);
		} catch (Exception e) {
//			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * fetch instruction from memory at pc address.
	 * update instruction register and program counter.
	 *
	 * @param memory
	 */
	private void fetchInstruction(Memory memory) {
		int instruction = memory.getByte(registers.getProgramCounter()) & 0xff;
		if (InstructionTypes.isUnaryInstruction(instruction)) {
			operandSpecifier = 0;
			registers.setInstructionRegister(instruction << 16);
			registers.setProgramCounter(registers.getProgramCounter() + 1);
		} else {
			int pc = registers.getProgramCounter();
			operandSpecifier = ((memory.getByte(pc + 1) & 0xff) << 8) | (memory.getByte(pc + 2) & 0xff);
			registers.setInstructionRegister((instruction << 16) | operandSpecifier);
			registers.setProgramCounter(registers.getProgramCounter() + 3);
		}
	}


	/**
	 * decode instruction: get instruction type, operation code, addressing mode and register
	 * field.
	 */
	private void decodeInstruction() {
		instructionType = getInstructionType(registers.getInstructionSpecifier());
		addressingMode = UNARY;
		registerField = -1;
		if (INSTRUCTIONS_HAVING_7_FIXED_BITS.contains(instructionType)) {
			int lastBit = registers.getInstructionSpecifier() & 0b1;
			if (instructionType == NOTR || instructionType == NEGR || instructionType == ASLR ||
			    instructionType == ASRR || instructionType == ROLR || instructionType == RORR) {
				registerField = lastBit;
			} else {
				addressingMode = lastBit == 0 ? IMMEDIATE : INDEXED;
			}
		} else if (INSTRUCTIONS_HAVING_5_FIXED_BITS.contains(instructionType)) {
			if (instructionType != RETN) {
				addressingMode = registers.getInstructionSpecifier() & 0b111;
			}
		} else if (INSTRUCTIONS_HAVING_4_FIXED_BITS.contains(instructionType)) {
			addressingMode = registers.getInstructionSpecifier() & 0b111;
			registerField = (registers.getInstructionSpecifier() & 0b1000) >> 3;
		}
	}

	/**
	 * fetch data from memory if needed
	 *
	 * @param memory
	 */
	private void fetchData(Memory memory) throws Exception {
		finalOperand = operandSpecifier;
		if (addressingMode != UNARY) {
			if (isStoringTypeInstruction(instructionType)) {
				switch (addressingMode) {
					case IMMEDIATE:
						throw new Exception("illegal instruction");
					case DIRECT:
						finalOperand = operandSpecifier;
						break;
					case INDIRECT:
						finalOperand = memory.getWord(operandSpecifier);
						break;
//					case STACK_RELATIVE:
//						finalOperand = registers.getStackPoint() + operandSpecifier;
//						break;
//					case STACK_RELATIVE_DEFERRED:
//						finalOperand = memory.getWord(registers.getStackPoint() + operandSpecifier);
//						break;
					case INDEXED:
						finalOperand = operandSpecifier + registers.getIndexRegister();
						break;
//					case STACK_INDEXED:
//						finalOperand = operandSpecifier + registers.getStackPoint() + registers.getIndexRegister();
//						break;
//					case STACK_INDEXED_DEFERRED:
//						finalOperand = memory.getWord(operandSpecifier + registers.getStackPoint()) +
//						               registers.getIndexRegister();
//						break;
					default:
						break;
				}
			} else {
				switch (addressingMode) {
					case IMMEDIATE:
						finalOperand = operandSpecifier;
						break;
					case DIRECT:
						finalOperand = memory.getWord(operandSpecifier);
						break;
					case INDIRECT:
						finalOperand = memory.getWord(memory.getWord(operandSpecifier));
						break;
//					case STACK_RELATIVE:
//						finalOperand = memory.getWord(registers.getStackPoint() + operandSpecifier);
//						break;
//					case STACK_RELATIVE_DEFERRED:
//						finalOperand = memory.getWord(memory.getWord(registers.getStackPoint() + operandSpecifier));
//						break;
					case INDEXED:
						finalOperand = memory.getWord(operandSpecifier + registers.getIndexRegister());
						break;
//					case STACK_INDEXED:
//						finalOperand = memory
//								.getWord(operandSpecifier + registers.getStackPoint() + registers.getIndexRegister());
//						break;
//					case STACK_INDEXED_DEFERRED:
//						finalOperand = memory.getWord(memory.getWord(operandSpecifier + registers.getStackPoint()) +
//						                              registers.getIndexRegister());
//						break;
					default:
						break;
				}
			}

		}
	}

	/**
	 * execute instruction.
	 *
	 * @param memory
	 * @param io
	 *
	 * @return
	 */
	private int executeInstruction(Memory memory, IO io) {
		if (instructionType == STOP) {
			halt = true;
//		} else if (instructionType == RETTR) {
//
//		} else if (instructionType == MOVSPA) {
//			registers.setAccumulator(registers.getStackPoint());
//		} else if (instructionType == MOVFLGA) {
//			int temp = 0;
//			if (registers.isStatusBitN()){
//				temp |= (1<<3);
//			}
//			if (registers.isStatusBitZ()){
//				temp |= (1<<2);
//			}
//			if (registers.isStatusBitV()){
//				temp |= (1<<1);
//			}
//			if (registers.isStatusBitC()){
//				temp |= (1);
//			}
//			registers.setAccumulator(temp);
		} else if (instructionType == BR) {
			registers.setProgramCounter(finalOperand);
		} else if (instructionType == BRLE) {
			if (registers.isStatusBitZ() || registers.isStatusBitN()) {
				registers.setProgramCounter(finalOperand);
			}
		} else if (instructionType == BRLT) {
			if (registers.isStatusBitN()) {
				registers.setProgramCounter(finalOperand);
			}
		} else if (instructionType == BREQ) {
			if (registers.isStatusBitZ()) {
				registers.setProgramCounter(finalOperand);
			}
		} else if (instructionType == BRNE) {
			if (!registers.isStatusBitZ()) {
				registers.setProgramCounter(finalOperand);
			}
		} else if (instructionType == BRGE) {
			if (!registers.isStatusBitN()) {
				registers.setProgramCounter(finalOperand);
			}
		} else if (instructionType == BRGT) {
			if (!registers.isStatusBitN() && !registers.isStatusBitZ()) {
				registers.setProgramCounter(finalOperand);
			}
		} else if (instructionType == BRV) {
			if (registers.isStatusBitV()) {
				registers.setProgramCounter(finalOperand);
			}
		} else if (instructionType == BRC) {
			if (registers.isStatusBitC()) {
				registers.setProgramCounter(finalOperand);
			}
//		} else if (instructionType == CALL) {
//			registers.setStackPoint(registers.getStackPoint() - 2);
//			memory.putWord(registers.getStackPoint(), registers.getProgramCounter());
//			registers.setProgramCounter(finalOperand);
//		} else if (instructionType == NOTR) {
//			if (registerField == ACCUMULATOR) {
//				registers.setAccumulator(~registers.getAccumulator());
//				registers.setStatusBitN(registers.getAccumulator() < 0);
//				registers.setStatusBitZ(registers.getAccumulator() == 0);
//			} else if (registerField == INDEX_REGISTER) {
//				registers.setIndexRegister(~registers.getIndexRegister());
//				registers.setStatusBitN(registers.getIndexRegister() < 0);
//				registers.setStatusBitZ(registers.getIndexRegister() == 0);
//			}
//		} else if (instructionType == NEGR) {
//			if (registerField == ACCUMULATOR) {
//				registers.setAccumulator(-registers.getAccumulator());
//				registers.setStatusBitN(registers.getAccumulator() < 0);
//				registers.setStatusBitZ(registers.getAccumulator() == 0);
//				registers.setStatusBitV(registers.getAccumulator() >= (1 << 15));
//				registers.setAccumulator(registers.getAccumulator() & ((1 << 16) - 1));
//			} else if (registerField == INDEX_REGISTER) {
//				registers.setIndexRegister(-registers.getIndexRegister());
//				registers.setStatusBitN(registers.getIndexRegister() < 0);
//				registers.setStatusBitZ(registers.getIndexRegister() == 0);
//				registers.setStatusBitV(registers.getIndexRegister() >= (1 << 15));
//				registers.setIndexRegister(registers.getIndexRegister() & ((1 << 16) - 1));
//			}
//		} else if (instructionType == ASLR) {
//
//		} else if (instructionType == ASRR) {
//
//		} else if (instructionType == ROLR) {
//
//		} else if (instructionType == NOPN) {
//
//		} else if (instructionType == NOP) {
//
//		} else if (instructionType == DECI) {
//
//		} else if (instructionType == DECO) {
//			io.putDecimal(finalOperand);
//		} else if (instructionType == STRO) {

		} else if (instructionType == CHARI) {
			memory.putByte(operandSpecifier, io.readByteFromInput());
		} else if (instructionType == CHARO) {
			io.putByte((byte) ((finalOperand >> 8) & 0xff));
//		} else if (instructionType == RETN) {
//			int n = registers.getInstructionSpecifier() & 0b111;
//			registers.setStackPoint(registers.getStackPoint() + n);
//			memory.putWord(registers.getStackPoint(), registers.getProgramCounter());
//			registers.setProgramCounter(finalOperand);
//		} else if (instructionType == ADDSP) {
//			// TODO: V FLAG AND C FLAG
//			registers.setStackPoint(registers.getStackPoint() + finalOperand);
//			registers.setStatusBitN(registers.getStackPoint() < 0);
//			registers.setStatusBitZ(registers.getStackPoint() == 0);
//			registers.setStatusBitV(registers.getStackPoint() >= (1 << 15) || registers.getStackPoint() < (-1 << 15));
//			registers.setStatusBitC(registers.getStackPoint() >= (1 << 15));
//		} else if (instructionType == SUBSP) {
//			// TODO: V FLAG AND C FLAG
//			registers.setStackPoint(registers.getStackPoint() - finalOperand);
//			registers.setStatusBitN(registers.getStackPoint() < 0);
//			registers.setStatusBitZ(registers.getStackPoint() == 0);
//			registers.setStatusBitV(registers.getStackPoint() >= (1 << 15) || registers.getStackPoint() < (-1 << 15));
//			registers.setStatusBitC(registers.getStackPoint() >= (1 << 15));
		} else if (instructionType == ADDR) {
			if (registerField == ACCUMULATOR) {
				registers.setAccumulator(registers.getAccumulator() + finalOperand);
				registers.setStatusBitN(registers.getAccumulator() < 0);
				registers.setStatusBitZ(registers.getAccumulator() == 0);
				registers.setStatusBitV(
						registers.getAccumulator() >= (1 << 15) || registers.getStackPoint() < (-1 << 15));
				registers.setStatusBitC(registers.getAccumulator() >= (1 << 15));
			} else if (registerField == INDEX_REGISTER) {
				registers.setIndexRegister(registers.getIndexRegister() + finalOperand);
				registers.setStatusBitN(registers.getIndexRegister() < 0);
				registers.setStatusBitZ(registers.getIndexRegister() == 0);
				registers.setStatusBitV(
						registers.getIndexRegister() >= (1 << 15) || registers.getStackPoint() < (-1 << 15));
				registers.setStatusBitC(registers.getIndexRegister() >= (1 << 15));
			}
		} else if (instructionType == SUBR) {
			if (registerField == ACCUMULATOR) {
				registers.setAccumulator(registers.getAccumulator() - finalOperand);
			} else if (registerField == INDEX_REGISTER) {
				registers.setIndexRegister(registers.getIndexRegister() - finalOperand);
			}
		} else if (instructionType == ANDR) {
			if (registerField == ACCUMULATOR) {
				registers.setAccumulator(finalOperand & registers.getAccumulator());
				registers.setStatusBitN(registers.getAccumulator() < 0);
				registers.setStatusBitZ(registers.getAccumulator() == 0);
			} else if (registerField == INDEX_REGISTER) {
				registers.setIndexRegister(finalOperand & registers.getIndexRegister());
				registers.setStatusBitN(registers.getIndexRegister() < 0);
				registers.setStatusBitZ(registers.getIndexRegister() == 0);
			}
		} else if (instructionType == ORR) {
			if (registerField == ACCUMULATOR) {
				registers.setAccumulator(finalOperand | registers.getAccumulator());
			} else if (registerField == INDEX_REGISTER) {
				registers.setIndexRegister(finalOperand | registers.getIndexRegister());
			}
		} else if (instructionType == CPR) {
			int diff = 0;
			if (registerField == ACCUMULATOR) {
				diff = registers.getAccumulator() - finalOperand;
			} else if (registerField == INDEX_REGISTER) {
				diff = registers.getIndexRegister() - finalOperand;
			}
			registers.setStatusBitN(diff < 0);
			registers.setStatusBitZ(diff == 0);
			registers.setStatusBitV(diff < (-1 << 15) || diff >= (1 << 15));
			registers.setStatusBitC(diff >= (1 << 15));
		} else if (instructionType == LDR) {
			if (registerField == ACCUMULATOR) {
				registers.setAccumulator(finalOperand);
				registers.setStatusBitN(registers.getAccumulator() < 0);
				registers.setStatusBitZ(registers.getAccumulator() == 0);
			} else if (registerField == INDEX_REGISTER) {
				registers.setIndexRegister(finalOperand);
				registers.setStatusBitN(registers.getIndexRegister() < 0);
				registers.setStatusBitZ(registers.getIndexRegister() == 0);
			}
		} else if (instructionType == LDBYTER) {
			if (registerField == ACCUMULATOR) {
				registers.setAccumulator(finalOperand & 0xff);
				registers.setStatusBitN(registers.getAccumulator() < 0);
				registers.setStatusBitZ(registers.getAccumulator() == 0);
			} else if (registerField == INDEX_REGISTER) {
				registers.setIndexRegister(finalOperand & 0xff);
				registers.setStatusBitN(registers.getIndexRegister() < 0);
				registers.setStatusBitZ(registers.getIndexRegister() == 0);
			}
		} else if (instructionType == STR) {
			if (registerField == ACCUMULATOR) {
				memory.putWord(finalOperand, registers.getAccumulator());
			} else if (registerField == INDEX_REGISTER) {
				memory.putWord(finalOperand, registers.getIndexRegister());
			}
		} else if (instructionType == STBYTER) {
			if (registerField == ACCUMULATOR) {
				memory.putByte(finalOperand, (byte) (registers.getAccumulator() & 0xff));
			} else if (registerField == INDEX_REGISTER) {
				memory.putByte(finalOperand, (byte) (registers.getIndexRegister() & 0xff));
			}
		}
		return 0;
	}


	public CPURegisters getRegisters() {
		return registers;
	}

	public void setRegisters(CPURegisters registers) {
		this.registers = registers;
	}

	/**
	 * Resets the CPU.
	 */
	public void reset() {
		halt = true;
		registers.setAccumulator(0);
		registers.setInstructionRegister(0);
		registers.setProgramCounter(0);
	}
}
