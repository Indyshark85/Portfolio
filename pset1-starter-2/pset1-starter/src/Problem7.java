class Problem7 {

    /**
     * x@y.z
     * A method that will take an email and cut it down to the username(x) only.
     *
     * @param email full email string.
     * @return username(x).
     */
    static String cutUsername(String email) {
        int atFinder=email.indexOf('@');
        String user=email.substring(0, atFinder);
        return user;
    }
}
