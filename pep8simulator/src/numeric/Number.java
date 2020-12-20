package numeric;

public abstract class Number {
    private int myValue;

    public Number(int theValue) {
        myValue = theValue;
    }

    public Number(Number theNumber) {
        myValue = theNumber.getValue();
    }

    public boolean equals(Number theNum) {
        return myValue == theNum.getValue();
    }

    public int compareTo(Number theNum){
        return myValue - theNum.getValue();
    }

    @Override
    public abstract String toString();

    public int getValue(){
        return myValue;
    }

    public void setValue(int theValue){
        myValue = theValue;
    }

    public void setValue(Number theNumber){
        myValue = theNumber.getValue();
    }
}
