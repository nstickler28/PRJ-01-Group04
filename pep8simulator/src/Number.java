
public abstract class Number {
    protected String myNumber;
    
    public Number() {
        this.myNumber = "";
    }
    
    public String getValue() {
        return this.myNumber;
    }
    
    void setValue(final int theNumber) {
        this.myNumber = String.valueOf(this.myNumber) + theNumber;
    }
    
    @Override
    public boolean equals(final Object other) {
        return this.myNumber.toString().equals(other.toString());
    }
    
    public int compareTo(final Object other) {
        final String numberString = this.myNumber.toString();
        if (numberString.compareTo(other.toString()) > 0) {
            return 1;
        }
        if (numberString.compareTo(other.toString()) == 0) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(this.getClass().getName());
        result.append("number: ");
        result.append(this.myNumber);
        result.append("\n");
        return result.toString();
    }
}
