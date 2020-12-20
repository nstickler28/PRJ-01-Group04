package numeric;

public class HexCalculator extends Calculator {

    @Override
    public Number add(Number theFirstNum, Number theSecondNum) {
        return new Hex(theFirstNum.getValue() + theSecondNum.getValue());
    }

    @Override
    public Number subtract(Number theFirstNum, Number theSecondNum) {
        return new Hex(theFirstNum.getValue() - theSecondNum.getValue());
    }

    @Override
    public Number multiply(Number theFirstNum, Number theSecondNum) {
        return new Hex(theFirstNum.getValue() * theSecondNum.getValue());
    }

    @Override
    public Number divide(Number theFirstNum, Number theSecondNum) {
        return new Hex(theFirstNum.getValue() / theSecondNum.getValue());
    }

    @Override
    public Number convert(Number theNum) {
        return new Hex(theNum.getValue());
    }
}
