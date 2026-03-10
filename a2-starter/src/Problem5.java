class Problem5 {

    /**
     * Converts a string to an integer, similar to the C `atoi` function.
     * It handles leading non-numeric characters, optional leading zeros, and positive/negative signs
     *
     * @param s The string to be converted to an integer.
     * @return The integer representation of the string. If the string does not contain any valid digits, returns 0.
     */
    static int atoi(String s) {
        int rInt =0;
        int posORneg= 1;
        int i= 0;
        boolean DigitChecker= false;

        //skip non-digit nums
        while (i < s.length() && !Character.isDigit(s.charAt(i)) && s.charAt(i) != '+' && s.charAt(i) != '-') {
            i++;
        }
        //added to skip leading 0's
        while (i<s.length() && s.charAt(i)=='0'){
            i++;
        }
        //checks for pos or neg signs
        if (i < s.length() && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
            posORneg = s.charAt(i) == '-' ? -1 : 1;
            i++;
        }
        //Conversion to Int code
        while (i< s.length()){
            char c = s.charAt(i);
            if (c<'0'||c>'9'){
                break;
            }
            DigitChecker=true;
            rInt=rInt*10+(c-'0');
            i++;
        }
        //Added to account for outlier case of no valid digits being found
        if (!DigitChecker){
            return 0;
        }
        // return <--- it returns
        return rInt*posORneg;
    }
}
