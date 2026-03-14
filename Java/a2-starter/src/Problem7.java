 class Problem7 {

    /**
     * method, which receives a string
     * and two integers 𝑎, 𝑏, and returns the substring between these indices. If either are out of bounds of
     * the string, return null.
     *
     * @param s    The original string from which the substring is to be extracted.
     * @param low  The starting index of the substring.
     * @param high The ending index of the substring.
     * @return The extracted substring, an empty string, or null depending on input validity.
     */
    static String substring(String s, int low, int high) {
        if (s.isEmpty() || low >= high) {
            return "";
        }

            if (low<0 || high>s.length()||s == null){
            return null;
        }
        String Rstring="";
        while (low<high){
            Rstring+=s.charAt(low);
            low++;
        }
        return Rstring;
    }
}
