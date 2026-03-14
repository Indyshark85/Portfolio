import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Capitalize {

    /**
     * method reads a file of sentences (that are not necessarily line-separated),
     * and outputs the capitalized versions of the sentences to a file of the same name, just with the .out
     * extension (you must remove whatever extension existed previously).
     *
     * @param in String.
     */
    static void capitalize(String in) {
        //looked up a new function to try and find SOMETHING that works
        try {
            BufferedReader reader = new BufferedReader(new FileReader(in));
            StringBuilder text = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append(" ");
            }
            reader.close();

            String textholder = text.toString();
            ArrayList theCollection = new ArrayList<>();
            int start =0;

            for (int x =0; x<textholder.length(); x++){
                if (textholder.charAt(x)== '.'){
                    String sentence = (textholder.substring(0,x+1).trim());
                    theCollection.add(sentence);
                    start=x+1;
                }
            }

            for(int i=0; i<theCollection.size();i++){
                String sentence = theCollection.get(i).toLowerCase();
            }
            StringBuilder result = new StringBuilder();
            for(int things =0; things<theCollection.size();things++){
                result.append(theCollection.get(things));
            }

            String outputFile = in.replaceAll("\\.[^.]*$", "") + ".out";
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(result.toString());

            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



//frankly I dont know what the problem is with this code and the auto grader no resource can seemingly point me to a fix
