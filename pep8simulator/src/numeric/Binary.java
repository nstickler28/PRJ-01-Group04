package numeric;

public class Binary extends Number {


    public Binary(int theValue) {
        super(theValue);
    }

    public Binary(Number theNumber) {
        super(theNumber);
    }

    @Override
    public String toString() {
        return Integer.toBinaryString(getValue());
    }
}
