class Problem6 {

    /**
     * This is my cheap IU version of Wordle it takes a word and the input and tell you if you are close it is super.
     * duper fun
     *
     * @param W the word string.
     * @param G the guess string.
     * @return A string indicating matches with the target word, or null if lengths do not match.
     */
    static String guessWord(String W, String G) {
        String WorkingStr="";
        if (!(G.length()==W.length())){
            return null;
        }

        for (int i=0; i<W.length(); i++){
            if (W.charAt(i)==G.charAt(i))
                WorkingStr+=W.charAt(i);
            else if(W.indexOf(G.charAt(i)) != -1 && W.charAt(i) != G.charAt(i)){
                WorkingStr+='*';
            }else{
                WorkingStr+='-';
            }
        }
        return WorkingStr;
    }
}
