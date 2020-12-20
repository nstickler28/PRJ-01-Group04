package numeric;

public class Decimal extends Number {

    public Decimal(int theValue) {
        super(theValue);
    }

    public Decimal(Number theNumber) {
        super(theNumber);
    }

    @Override
    public String toString() {
        return Integer.toString(getValue());
    }
}
