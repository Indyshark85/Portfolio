import javax.print.attribute.standard.PresentationDirection;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

class BigInt implements Comparable<BigInt> {

    private List<Integer> DigitStorage;
    private boolean isNeg;

    /**
     * constructor that receives a string
     * @param number String
     */
    public BigInt(String number) {
        DigitStorage = new ArrayList<>();
        isNeg=false;
        if (number.startsWith("-")){
            isNeg=true;
            number=number.substring(1);
        } else if (number.startsWith("+")) {
            number=number.substring(1);
        }
        number = number.replaceFirst("^0+(?!$)", "");
        if (number.isEmpty()) number="0";
        for (int i= number.length()-1; i>=0; i--){
            DigitStorage.add(Character.getNumericValue(number.charAt(i)));
        }
    }

    /**
     *method to return whether this instance represents the same integer as the parameter
     * @param o object
     * @return boolean
     */
    @Override
    public boolean equals(Object o){
        if (this ==o)
            return true;
        if (!(o instanceof BigInt))
            return false;
        BigInt other = (BigInt) o;
        return this.isNeg == other.isNeg && this.DigitStorage.equals(other.DigitStorage);
    }

    /**
     * Return the result of comparing this instance with
     * the parameter. That is, if 𝑎 < 𝑏, return −1, if 𝑎 > 𝑏, return 1, and otherwise return 0, where 𝑎
     * is this and 𝑏 is b2.
     *
     * @param o BigInt.
     * @return int.
     */
    @Override
    public int compareTo(BigInt o) {
        if(this.equals(o)) return 0;
        if(this.isNeg && !o.isNeg)return -1;
        if(!this.isNeg && o.isNeg) return 1;

        boolean BothNeg =this.isNeg;
        int thisSize=this.DigitStorage.size();
        int otherSize= o.DigitStorage.size();

        if(thisSize != otherSize){
            return (thisSize>otherSize)^BothNeg ? 1: -1;
        }

        for (int i= thisSize - 1; i >= 0; i--) {
            int d1= this.DigitStorage.get(i);
            int d2 =o.DigitStorage.get(i);
            if (d1 !=d2) {
                return (d1 > d2) ^ BothNeg ? 1: -1;
            }
        }
        return 0;
    }

    /**
     * method to return a stringified version of the
     * number.
     *
     * @return String.
     */
    @Override
    public String toString() {
        StringBuilder rString = new StringBuilder();
        if (isNeg == true){
            rString.append("-");
        }
        for (int i = DigitStorage.size() - 1; i >= 0; i--) {
            rString.append(DigitStorage.get(i));
        }
        return rString.toString();
    }

    /**
     * method that returns the
     * sum/difference of this and b respectively. .
     *
     * @param b2 BigInt.
     * @return BigInt.
     */
    BigInt add(BigInt b2) {
        if (this.isNeg == b2.isNeg) {
            BigInt result = this.addPositive(b2);
            result.isNeg = this.isNeg;
            return result;
        }
        int cmp= this.AddHelper(b2);
        if (cmp ==0){
            return new BigInt("0");
        }else if (cmp > 0) {
            BigInt result = this.subPositive(b2);
            result.isNeg = this.isNeg;
            return result;
        } else {
            BigInt result = b2.subPositive(this);
            result.isNeg = b2.isNeg;
            return result;
        }
    }
    private int AddHelper(BigInt b){
        if (this.DigitStorage.size() != b.DigitStorage.size()) {
            return this.DigitStorage.size() - b.DigitStorage.size();
        }
        for (int i= this.DigitStorage.size() - 1; i >= 0; i--) {
            int diff= this.DigitStorage.get(i) -b.DigitStorage.get(i);
            if (diff != 0) return diff;
        }
        return 0;
    }

    /**
     *  method that returns the
     *  sum/difference of this and b respectively.
     *
     * @param b2 BigInt.
     * @return BigInt.
     */
    BigInt sub(BigInt b2) {
       return this.add(b2.negate());
    }

    /**
     *  method that returns the product of this and b. The
     * product of two negative integers is a positive integer, and the product of exactly one positive
     * and exactly one negative is a negative integer.
     *
     * @param b2 BigInt.
     * @return BigInt.
     */
    BigInt mul(BigInt b2) {
        BigInt returnVal = this.mulPositive(b2);
        returnVal.isNeg=this.isNeg != b2.isNeg;
        if (this.equals(new BigInt("0")) || b2.equals(new BigInt("0"))) {
            return new BigInt("0");
        }
        return returnVal;
    }


    /**
     *  method, which returns a (deep)-copy of instance representing
     * the same integer as this instance of BigInt.
     *
     * @return BigInt.
     */
    BigInt copy() {
        BigInt Copy = new BigInt("0");
        Copy.DigitStorage = new ArrayList<>(this.DigitStorage);
        Copy.isNeg =this.isNeg;
        return Copy;
    }

    /**
     * method, which returns a copy of this instance of BigInt,
     * but negated.
     *
     * @return BigInt.
     */
    BigInt negate() {
        BigInt negated=this.copy();
        if(!(DigitStorage.size()==1 && DigitStorage.get(0)==0)){
            negated.isNeg =!this.isNeg;
        }
        return negated;
    }

    /**
     * This is a placeholder so that the compiler doesnt hurt itself
     * @param b2 BigInt
     * @return BigInt
     */
    BigInt div(BigInt b2) {
        if (b2.DigitStorage.size()== 1 && b2.DigitStorage.get(0) == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        //just returns 0 for now so it compiles
        return new BigInt("0");
    }
    /**
     * method, which returns
     * whether this instance and b have different signs. That is, if one is positive and one is
     * negative, areDifferentSigns returns true, and false otherwise
     * @param b BigInt
     * @return boolean
     */
    private boolean areDifferentSigns(BigInt b){
        return this.isNeg != b.isNeg;
    }

    /**
     *method, which returns a BigInt
     * instance that is the sum of this and b under the assumption that this and b are non-negative.
     * @param b BigInt
     * @return BigInt
     */
    private BigInt addPositive (BigInt b){
        List<Integer> resultDigit= new ArrayList<>();
        int holder= 0;
        int maxL= Math.max(this.DigitStorage.size(), b.DigitStorage.size());

        for (int i= 0; i< maxL || holder !=0; i++) {
            int ADig= (i < this.DigitStorage.size()) ? this.DigitStorage.get(i) : 0;
            int BDig= (i < b.DigitStorage.size()) ? b.DigitStorage.get(i) : 0;

            int total= ADig + BDig+holder;
            resultDigit.add(total% 10);
            holder =total /10;
        }
        BigInt result = new BigInt("0");
        result.DigitStorage = resultDigit;
        return result;
    }

    /**
     *  method, which returns a BigInt
     * instance that is the difference of this and b under the assumption that this and b are non-negative,
     * and the minuend (the left-hand operand) is greater than or equal to the subtrahend
     * (the right-hand operand).
     * @param b BigInt
     * @return BigInt
     */
    private BigInt subPositive(BigInt b){
        BigInt result = new BigInt("0");
        result.DigitStorage = new ArrayList<>();
        int holder = 0;

        for (int i = 0; i < this.DigitStorage.size(); i++) {
            int ADig = this.DigitStorage.get(i);
            int BDig = (i <b.DigitStorage.size()) ? b.DigitStorage.get(i) : 0;
            int difference = ADig - BDig - holder;
            if (difference < 0) {
                difference +=10;
                holder =1;
            } else {
                holder =0;
            }
            result.DigitStorage.add(difference);
        }

        int last = result.DigitStorage.size() - 1;
        while (result.DigitStorage.size() > 1 && result.DigitStorage.get(result.DigitStorage.size() - 1) == 0) {
            result.DigitStorage.remove(result.DigitStorage.size() - 1);
        }
        return result;
    }

    /**
     * method, which returns a BigInt
     * instance that is the product of this and b under the assumption
     * that this and b are non-negative.
     * @param b BigInt
     * @return BigInt
     */
    private BigInt mulPositive(BigInt b){
        BigInt result = new BigInt("0");
        int sizeA = this.DigitStorage.size();
        int sizeB = b.DigitStorage.size();
        result.DigitStorage = new ArrayList<>();
        for (int i = 0; i < sizeA + sizeB; i++){
            result.DigitStorage.add(0);
        }
        for (int i = 0; i < sizeA; i++) {
            int carry = 0;
            for (int j = 0; j < sizeB || carry != 0; j++) {
                int BDig = (j < sizeB) ? b.DigitStorage.get(j) : 0;
                int sum = result.DigitStorage.get(i + j) + this.DigitStorage.get(i) * BDig + carry;
                result.DigitStorage.set(i + j, sum % 10);
                carry = sum / 10;
            }
        }
        while (result.DigitStorage.size() > 1 && result.DigitStorage.get(result.DigitStorage.size() - 1) == 0) {
            result.DigitStorage.remove(result.DigitStorage.size() - 1);
        }

        return result;
    }
}
