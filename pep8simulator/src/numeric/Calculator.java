package numeric;

public abstract class Calculator implements Convertible {
    public abstract Number add(Number theFirstNum, Number theSecondNum);
    public abstract Number subtract(Number theFirstNum, Number theSecondNum);
    public abstract Number multiply(Number theFirstNum, Number theSecondNum);
    public abstract Number divide(Number theFirstNum, Number theSecondNum);
}