import java.util.Arrays;

class MiniStringBuilder {


    private char[] character;
    private int size;

     MiniStringBuilder() {
        this.character = new char[20];
        this.size = 0;
    }
    MiniStringBuilder(String s) {
        character = new char[s.length()*2];
        size= s.length();
        System.arraycopy(s.toCharArray(),0,character,0,s.length());
    }

    /**
     * method, which appends the given string 𝑠 onto the
     * end of the current string stored in the buffer.
     *
     * @param s String.
     */
    void append(String s) {
        if(s==null||s.isEmpty()){
            return;
        }
        if (size+s.length()>character.length){
            char[] newStr = new char[(character.length+s.length())];
            System.arraycopy(character, 0, newStr, 0, size);
            character = newStr;
        }
        System.arraycopy(s.toCharArray(), 0, character, size, s.length());
        size += s.length();
    }

    /**
     * method, which resets the char[] array to the default size of 20
     * and clears the character buffer.
     */
    void clear() {
        character=new char[20];
        size = 0;
    }

    /**
     *  method to return whether two Min-
     * iStringBuilder objects represent the same string.
     *
     * @param obj Object (But like whatever the joke I made in the matrix one).
     * @return boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if(!(obj instanceof MiniStringBuilder))
            return false;
        MiniStringBuilder TheThing = (MiniStringBuilder) obj;
        return Arrays.equals(
                Arrays.copyOf(character,size),
                Arrays.copyOf(TheThing.character,TheThing.size)
        );
    }

    /**
     *  method to return the hash code of the MiniString-
     * Builder object.
     * @return int (hash browns made of numbers yummy)
     */
    @Override
    public int hashCode(){
        return (Arrays.hashCode(this.character));
    }

    /**
     *  method, which returns the char[] array as a
     * String object. .
     *
     * @return string.
     */
    @Override
    public String toString() {
        return new String(Arrays.copyOf(character,size));
    }

}
