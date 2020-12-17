import java.math.BigInteger;

public class Binary extends Number {
    String binaryNumber;
    
    public Binary() {
        this.binaryNumber = "0";
    }
    
    public Binary(final int number) {
        super.myNumber = Integer.toBinaryString(number);
        this.binaryNumber = super.myNumber;
    }
    
    public Binary(final Binary number) {
        super.myNumber = number.getValue();
        this.binaryNumber = super.myNumber;
    }
    
    public Binary(final String numberString) {
        final int number = Integer.parseInt(numberString);
        super.myNumber = Integer.toBinaryString(number);
        this.binaryNumber = super.myNumber;
    }
    
    public void setValue(final int theNumber) {
        super.myNumber = Integer.toBinaryString(theNumber);
        this.binaryNumber = super.myNumber;
    }
    
    public String getValue() {
        String value = "";
        value = String.valueOf(value) + this.binaryNumber;
        return value;
    }
    
    public int compareTo(final Binary theOther) {
        int result = 0;
        if (this.equals(theOther)) {
            result = 0;
        }
        if (super.myNumber.length() > theOther.getValue().length()) {
            result = 1;
        }
        else if (super.myNumber.length() < theOther.getValue().length()) {
            result = -1;
        }
        else {
            for (int i = 0; i < super.myNumber.length(); ++i) {
                if (super.myNumber.charAt(i) == '0' && theOther.getValue().charAt(i) == '1') {
                    result = -1;
                    break;
                }
                if (super.myNumber.charAt(i) == '1' && theOther.getValue().charAt(i) == '0') {
                    result = 1;
                    break;
                }
            }
        }
        return result;
    }
    
    boolean equals(final Binary theBinary) {
        return super.myNumber.equals(theBinary.getValue());
    }
    
    public String toDecimal() {
        return Integer.toString(Integer.parseInt(super.myNumber, 2));
    }
    
    public String toHex() {
        return Integer.toHexString(Integer.parseInt(super.myNumber, 2));
    }
    
    public String hexToBinary(final String hexString) {
        return String.format("%4s", new BigInteger(hexString, 16).toString(2)).replace(' ', '0');
    }
    
    public String extendedHexToBinary(final String hexString) {
        String binaryString = String.format("%4s", new BigInteger(hexString, 16).toString(2)).replace(' ', '0');
        final StringBuilder signExtend = new StringBuilder(binaryString);
        while (signExtend.length() % 8 != 0) {
            signExtend.insert(0, "0");
        }
        binaryString = signExtend.toString();
        return binaryString;
    }
    
    public String asciiToBinary(final char ASCII) {
        String binaryString = Integer.toBinaryString(ASCII);
        final StringBuilder signExtend = new StringBuilder(binaryString);
        while (signExtend.length() < 8) {
            signExtend.insert(0, "0");
        }
        binaryString = signExtend.toString();
        return binaryString;
    }
    
    public String decimalToBinary(final String value) {
        final String binaryString = Integer.toBinaryString(Integer.valueOf(value));
        return binaryString;
    }
}
