class Problem8 {

    /**
     * Compares two file names based on the numeric part of their base names.
     * If the numeric parts are equal, it performs a lexicographical comparison of the full file names.
     * @param f1 The first file name to be compared.
     * @param f2 The second file name to be compared.
     * @return A negative integer, zero, or a positive integer as the first file name is less than, equal to, or greater than the second.
     */
    static int compareFiles(String f1, String f2) {
        String Name1= f1.substring(0,f1.indexOf('.'));
        String Name2= f2.substring(0,f2.indexOf('.'));

        int int1= Integer.parseInt(FindNum(Name1));
        int int2= Integer.parseInt(FindNum(Name2));

        if (int1!=int2){
            return Integer.compare(int1, int2);
        }

        return f1.compareTo(f2);
    }

    private static String FindNum(String str){
        int index=0;
        while ((index < str.length() && !Character.isDigit(str.charAt(index)))){
            index++;
        }
        return str.substring(index);
    }
}


