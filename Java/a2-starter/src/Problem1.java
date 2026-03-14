class Problem1 {
    /**
     * Determines if the given string is a palindrome using tail recursion.
     *
     * @param s s The string to be checked.
     * @return True if the string is a palindrome, false otherwise.
     */
    static boolean isPalindromeTR(String s) {

        return isPralindromeTRHelper(s,s.length()-1,"");
    }

    /**
     * Helper method for tail recursion palindrome check.
     * @param s  The original string.
     * @param Index  The current index being processed.
     * @param acc  The accumulated reversed string.
     * @return if the accumulated reversed string matches the original string, false otherwise
     */
    private static Boolean isPralindromeTRHelper(String s, int Index,String acc){
        if (Index<0){
            return (s.equals(acc));}
        else{
            return isPralindromeTRHelper(s,Index-1,acc+s.charAt(Index));
        }
    }

    /**
     * Determines if the given string is a palindrome using a loop.
     *
     * @param s The string to be checked.
     * @return if the string is a palindrome, false otherwise.
     */
    static boolean isPalindromeLoop(String s) {
        String backwards="";
        for (int i=s.length()-1;i>=0;i--) {
            backwards=backwards+(s.charAt(i));
        }
        if (backwards.equals(s))
            return true;
        else
            return false;
    }
}
