package constants;
/**
 * Constant for addressing modes.
 */
public class AddressingMode {
    public static final int UNARY                   = -1;
    public static final int IMMEDIATE               = 0b000;
    public static final int DIRECT                  = 0b001;
    public static final int INDIRECT                = 0b010;
    public static final int STACK_RELATIVE          = 0b011;
    public static final int STACK_RELATIVE_DEFERRED = 0b100;
    public static final int INDEXED                 = 0b101;
    public static final int STACK_INDEXED           = 0b110;
    public static final int STACK_INDEXED_DEFERRED  = 0b111;
}
