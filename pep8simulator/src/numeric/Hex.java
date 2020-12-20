package numeric;

public class Hex extends Number {
    public Hex(int theValue) {
        super(theValue);
    }

    public Hex(Number theNumber) {
        super(theNumber);
    }

    @Override
    public String toString() {
        return Integer.toHexString(getValue());
    }

}
