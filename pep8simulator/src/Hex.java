
public class Hex extends Number {
    String hexNumber;
    
    public Hex() {
        this.hexNumber = "0";
    }
    
    public Hex(final int number) {
        super.myNumber = Integer.toHexString(number);
        this.hexNumber = super.myNumber;
    }
    
    public Hex(final Hex number) {
        super.myNumber = number.getValue();
        this.hexNumber = super.myNumber;
    }
    
    public Hex(final String numberString) {
        final int hexValue = Integer.parseInt(numberString);
        super.myNumber = Integer.toHexString(hexValue);
        this.hexNumber = super.myNumber;
    }
    
    @Override
    public String getValue() {
        String value = "";
        value = String.valueOf(value) + this.hexNumber;
        return value;
    }
    
    public void setValue(final int number) {
        super.myNumber = Integer.toHexString(number);
        this.hexNumber = super.myNumber;
    }
    
    public int compareTo(final Hex theOther) {
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
                if (super.myNumber.charAt(i) > (int)theOther.getValue().charAt(i)) {
                    result = 1;
                }
                else if (super.myNumber.charAt(i) < (int)theOther.getValue().charAt(i)) {
                    result = -1;
                }
            }
        }
        return result;
    }
    
    public boolean equals(final Hex theHex) {
        return super.myNumber.equals(theHex.getValue());
    }
    
    public String toBinary() {
        return Integer.toBinaryString(Integer.parseInt(super.myNumber, 16));
    }
    
    public String toDecimal() {
        return Integer.toString(Integer.parseInt(super.myNumber, 16));
    }
    
    public String binaryToHex(final String binaryString) {
        final int decimalValue = Integer.parseInt(binaryString, 2);
        final String hexString = Integer.toString(decimalValue, 16);
        return hexString;
    }
    
    public String extendedBinaryToHex(final String binaryString) {
        final int decimalValue = Integer.parseInt(binaryString, 2);
        final StringBuilder hexString = new StringBuilder(Integer.toString(decimalValue, 16));
        while (hexString.length() % 2 != 0) {
            hexString.insert(0, "0");
        }
        return hexString.toString();
    }
}
