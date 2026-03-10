class Problem14 {

    /**
     * Determines whether we can convert a given string into an integer datatype.
     * @param s the string to check.
     * @return true if s can be represented as an int, false otherwise.
     */
    private static boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * method that, when given a string, determines whether or
     * not it represents a valid IPv4 address. Each octet must be an integer between zero and 255 inclusive.
     * Note that some IPv4 addresses are, in reality, nonsensical, e.g., 0.0.0.0, but we will not consider
     * these as invalid.
     *
     * @param s string of IPv4.
     * @return a boolean determined on whether or not it is a valid IP address.
     */
    static boolean isValidIpv4(String s) {
        if (s==null || s.isEmpty()){
            return false;
        }
        int onep = s.indexOf('.');
        if (onep == -1){
            return false;}
        String partone = s.substring(0,onep);
        if (isNumeric(partone)==false || Integer.parseInt(partone)<0 || Integer.parseInt(partone)>255){
            return false;
        }
        int twop = s.indexOf('.',onep+1);
        if (twop == -1){
            return false;}
        String parttwo = s.substring(onep+1,twop);
        if (isNumeric(parttwo)==false || Integer.parseInt(parttwo)<0 || Integer.parseInt(parttwo)>255){
            return false;
        }
        int threep = s.indexOf('.',twop+1);
        if (threep == -1){
            return false;}
        String partthree = s.substring(twop+1,threep);
        if (isNumeric(partthree)==false || Integer.parseInt(partthree)<0 || Integer.parseInt(partthree)>255){
            return false;
        }

        String partfour = s.substring(threep+1);
        if (isNumeric(partfour)==false || Integer.parseInt(partfour)<0 || Integer.parseInt(partfour)>255){
            return false;
        }
        return true;
    }
}
