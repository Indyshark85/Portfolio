import java.util.List;
import java.util.stream.Collectors;

class StreamMethods {

    /**
     * method that, when given a
     * list of two-element arrays representing 𝑥, 𝑦 coordinate pairs, returns whether or not any of the
     * 𝑦-coordinates are greater than 450.
     *
     * @param ls list.
     * @return boolean.
     */
    static boolean containsHigh(List<int[]> ls) {
        return ls.stream()
                .anyMatch(coord -> coord[1]>450);

    }

    /**
     * receives a list of numbers, returns a list of those numbers squared
     * and adds five to the result, omitting any of
     * the resulting numbers that end in 5 or 6.
     *
     * @param lon list.
     * @return list.
     */
    static List<Integer> sqAddFiveOmit(List<Integer> lon) {
        return lon.stream()
                .map(x->x*x+5)
                .filter(n->n%10 != 5 && n%10 !=6)
                .collect(Collectors.toList());

    }

    /**
     * method that
     * receives a list of strings, and removes all strings that contain more characters than a given integer 𝑛.
     * Return this result as a new list.
     *
     * @param los list.
     * @param n int.
     * @return list.
     */
    static List<String> removeLonger(List<String> los, int n) {
        return los.stream()
                .filter(word -> word.length()<=n)
                .collect(Collectors.toList());
    }

    /**
     * method that, when given a string 𝑠, removes all non-alphanumeric characters,
     * converts all letters to uppercase, and computes the sum
     * of the ASCII values of the letters. Digits should also be added, but use the digit itself and not its
     * ASCII value.
     *
     * @param s String.
     * @return int.
     */
    static int filterSumChars(String s) {
       return s.chars()
               .filter(Character::isLetterOrDigit)
               .map(ch -> {
                   if (Character.isDigit(ch)){
                       return ch -'0';
                   } else if (Character.isLetter(ch)) {
                       return Character.toUpperCase(ch);
                   }
                   return 0;
               })
               .sum();
    }
}
