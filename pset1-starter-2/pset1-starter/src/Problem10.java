class Problem10 {

    /**
     * A method which detects and removes any occurance of the str "try" it is not a big fan of "try" I guess.
     *
     * @param s a str which possibly contains the str "try" (which the method cutTry will not stand for bc "try" is a Chiefs fan).
     * @return A string purged of any occurance of "try" bc cutTry is very good at its job.
     */
    static String cutTry(String s) {
        String subStr="try";
        if (s.endsWith(subStr)){
            return s.substring(0,s.length()-subStr.length());
        }
        return s;
    }
}
