class Problem9 {

    /**
     * Sums all the numeric values found in the given string.
     *Iterates through the string, identifies numeric sequences, converts them to integers, and adds them to the result
     * @param s The string containing the numeric values to be summed.
     * @return The sum of all numeric values in the string.
     */
    static int strSumNums(String s) {
        int Rint = 0;
        int index=0;

        while (index<s.length()){
            if (Character.isDigit(s.charAt(index))){
                int n=0;
                StringBuilder buildInt = new StringBuilder();
                while (index + n < s.length() && Character.isDigit(s.charAt(index + n))){
                    buildInt.append(s.charAt(index + n));
                    n+=1;
                }
                Rint+=Integer.parseInt(buildInt.toString());
                index+=n;
            }else{
                index+=1;
            }
        }



        return Rint;
    }
}
