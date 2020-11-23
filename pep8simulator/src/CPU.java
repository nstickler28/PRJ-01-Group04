
/**
 * 
 * @author Chun Kit Kwong
 * @author Nathan Stickler
 * @author Loren Mendoza
 * @author Wynn Siripanich
 * 
 * This class handles all the arithmetic of the CPU.
 * The instruction in memory is fetched, decoded and executed.
 *
 */

public class CPU {
    
	/**
	 * Constant for STOP operand.
	 */
    private static final int STOP_EXECUTION = 0b00000;
    /**
     * Constant for CHAR input.
     */
    private static final int CHAR_INPUT = 0b01001;
    /**
     * Constant for CHAR output.
     */
    private static final int CHAR_OUTPUT = 0b01010;
    /**
     * Constant for ADD operand.
     */
    private static final int ADD_OPERAND_INTO_REGISTER = 0b01110;
    /**
     * Constant for SUBTRACT operand.
     */
    private static final int SUBTRACT_OPERAND = 0b10000;
    /**
     * Constant for OR operand.
     */
    private static final int OR_OPERAND = 0b10100;
    /**
     * Constant for LOAD operand.
     */
    private static final int LOAD_OPERAND_INTO_REGISTER = 0b11000;
    /**
     * Constant for STORE register into operand.
     */
    private static final int STORE_REGISTER_INTO_OPERAND = 0b11100;
    /**
     * Constant for STORE byte into operand.
     */
    private static final int STORE_BYTE_INTO_OPERAND = 0b11110;
    /**
     * Constant for IMMEDIATE mode instruction.
     */
    private static final int IMMEDIATE = 0;
    /**
     * Constant for DIRECT mode instruction.
     */
    private static final int DIRECT = 1;

    // CPU registers
    private boolean halt = true;
    int aRegister;
    int instructionRegister;
    int programCounter;

    // CPU instruction decoding variable
    int instructionSpecifier;
    int operandSpecifier;
    private int operationCode;
    private int addressingMode;
    private int operandContent;

    /**
     * Run the object code from memory and IO.
     * @param memory the memory
     * @param io the io
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


    // fetch-execute cycle
    private int singleStep(Memory memory, IO io) {
        fetchInstruction(memory);
        decodeInstruction();
        fetchData(memory);
        return executeInstruction(memory, io);
    }

    // fetch instruction from memory at pc address
    private void fetchInstruction(Memory memory) {
        byte[] bytes = memory.getBytes(programCounter, 3);
        instructionRegister =
                ((bytes[0] & 0xff) << 16) | ((bytes[1] & 0xff) << 8) | (bytes[2] & 0xff);
        programCounter += 3;
    }

    // decode instruction, get instruction specifier and operand specifier
    private void decodeInstruction() {
        instructionSpecifier = instructionRegister >> 16;
        operandSpecifier = instructionRegister & 0xffff;
        operationCode = instructionSpecifier >> 3;
        addressingMode = instructionSpecifier & 0b111;
    }

    // fetch data from memory if needed
    private void fetchData(Memory memory) {
        if (addressingMode == IMMEDIATE) {
            operandContent = operandSpecifier;
        } else {
            switch (operationCode) {
                case LOAD_OPERAND_INTO_REGISTER:
                case ADD_OPERAND_INTO_REGISTER:
                case SUBTRACT_OPERAND:
                case OR_OPERAND:
                    byte[] bytes = memory.getBytes(operandSpecifier, 2);
                    operandContent = ((bytes[0] & 0xff) << 8) | (bytes[1] & 0xff);
                    break;
                case CHAR_OUTPUT:
                    operandContent = memory.getByte(operandSpecifier);
            }
        }
    }

    // execute instruction
    private int executeInstruction(Memory memory, IO io) {
        switch (operationCode) {
            case STOP_EXECUTION:
                halt = true;
                break;
            case LOAD_OPERAND_INTO_REGISTER:
                aRegister = operandContent;
                break;
            case STORE_REGISTER_INTO_OPERAND:
                memory.putWord(operandSpecifier, aRegister);
                break;
            case STORE_BYTE_INTO_OPERAND:
                memory.putByte(operandSpecifier, (byte) (aRegister & 0xff));
            case ADD_OPERAND_INTO_REGISTER:
                aRegister += operandContent;
                break;
            case SUBTRACT_OPERAND:
                aRegister -= operandContent;
                break;
            case OR_OPERAND:
                aRegister |= operandContent;
            case CHAR_INPUT:
                memory.putByte(operandSpecifier, io.readByteFromInput());
                break;
            case CHAR_OUTPUT:
                io.putByte((byte) (operandContent & 0xff));
                break;
            default:
                return -1;
        }
        return 0;
    }

    /**
     * Resets the CPU.
     */
    public void reset() {
        halt = true;
        aRegister = 0;
        instructionRegister = 0;
        programCounter = 0;
        instructionSpecifier = 0;
        operandSpecifier = 0;
        operationCode = 0;
        addressingMode = 0;
        operandContent = 0;
    }
}
