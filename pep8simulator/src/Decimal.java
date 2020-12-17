
public class Decimal extends Number {
    Binary binaryClass;
    String decimalNumber;
    
    public Decimal() {
        this.binaryClass = new Binary();
        this.decimalNumber = "0";
    }
    
    public Decimal(final int number) {
        this.binaryClass = new Binary();
        super.myNumber = Integer.toString(number);
        this.decimalNumber = super.myNumber;
    }
    
    public Decimal(final Decimal number) {
        this.binaryClass = new Binary();
        super.myNumber = number.getValue();
        this.decimalNumber = super.myNumber;
    }
    
    public Decimal(final String numberString) {
        this.binaryClass = new Binary();
        super.myNumber = numberString;
        this.decimalNumber = super.myNumber;
    }
    
    @Override
    public String getValue() {
        String value = "";
        value = String.valueOf(value) + this.decimalNumber;
        return value;
    }
    
    public void setValue(final int theNumber) {
        super.myNumber = Integer.toString(theNumber);
        this.decimalNumber = super.myNumber;
    }
    
    public int compareTo(final Decimal theOther) {
        int result = 0;
        if (Integer.valueOf(this.decimalNumber) > Integer.valueOf(theOther.getValue())) {
            result = 1;
        }
        else if (Integer.valueOf(this.decimalNumber) < Integer.valueOf(theOther.getValue())) {
            result = -1;
        }
        return result;
    }
    
    public boolean equals(final Decimal theDecimal) {
        return this.decimalNumber.equals(theDecimal.getValue());
    }
    
    public String toHex() {
        return Integer.toHexString(Integer.valueOf(super.myNumber));
    }
    
    public String toBinary() {
        return Integer.toBinaryString(Integer.valueOf(super.myNumber));
    }
    
    public String extendedDecimalToBinary(final String value) {
        final StringBuilder decimalToBinary = new StringBuilder();
        decimalToBinary.append(this.binaryClass.decimalToBinary(value));
        while (decimalToBinary.length() % 8 != 0) {
            decimalToBinary.insert(0, "0");
        }
        return decimalToBinary.toString();
    }
    
    public String decimalToAscii(final String value) {
        final int decimalValue = Integer.parseInt(value);
        final char c = (char)decimalValue;
        final StringBuilder ascii = new StringBuilder();
        ascii.append(c);
        return ascii.toString();
    }
}
