import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

class SpellChecker {

    /**
     * method reads two files: a “dictionary” and a content file. The
     * content file contains a single sentence that may or may not have misspelled words. Your job is to
     * check each word in the file and determine whether or not they are spelled correctly, according to
     * the dictionary of (line-separated) words. If a word is not spelled correctly, wrap it inside brackets
     * [].
     *
     * @param dict String.
     * @param in file name.
     */
    static void spellCheck(String dict, String in) {
        try{
            Set<String> dictionary = new HashSet<>();
            BufferedReader Dictreader = new BufferedReader(new FileReader(dict));
            String word;
            while ((word = Dictreader.readLine()) != null){
                dictionary.add(word.toLowerCase());
            }
            Dictreader.close();

            StringBuilder Sentence = new StringBuilder();
            BufferedReader SentRead = new BufferedReader(new FileReader(in));
            String line;
            while ((line = SentRead.readLine()) != null){
                Sentence.append(line).append(" ");
            }
            SentRead.close();

            String[] words= Sentence.toString().trim().split("\\s+");
            StringBuilder returnString = new StringBuilder();

            for(String w: words){
                if (dictionary.contains(w.toLowerCase())){
                    returnString.append(w);
                }else{
                    returnString.append("[").append(w).append("]");
                }
                returnString.append(" ");
            }

            String outputFile= in.replaceAll("\\.[^.]*$", "") +".out";
            BufferedWriter writer= new BufferedWriter(new FileWriter(outputFile));
            writer.write(returnString.toString().trim());
            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
