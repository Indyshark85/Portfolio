class Problem11 {

    // No skeleton code is provided - you need to write all
    // of the method headers and implementations yourself.
    // This is a *very* simple problem, so don't overthink it!

    /**
     *method which represents a conditional statement in TFL
     * @param p boolean value
     * @param q boolean value
     * @return the result of the TFL statement in boolean
     */
    static boolean cond(boolean p,boolean q){
        if (p==true && q==false){
            return false;}
        else{
            return true;
        }
    }

    /**
     *method which represents a biconditional statement in TFL
     * @param p boolean value
     * @param q boolean value
     * @return the result of the TFL statement in boolean
     */
    static boolean bicond(boolean p,boolean q){
        if (p==true && q==true){
            return true;
        } else if (p==false && q==false) {
            return true;
        } else{
            return false;
        }
    }

    /**
     * method which represents an and statement in TFL
     * @param p boolean value
     * @param q boolean value
     * @return the result of the TFL statement in boolean
     */
    static boolean and(boolean p,boolean q){
        if (p==true && q==true){
            return true;}
        else{
            return false;
        }
    }

    /**
     * method which represents an or statement in TFL
     * @param p boolean value
     * @param q boolean value
     * @return the result of the TFL statement in boolean
     */
    static boolean or(boolean p,boolean q){
        if (p==false && q==false){
            return false;}
        else{
            return true;
        }
    }

    /**
     * method which represents a negation statement in TFL
     * @param p boolean value
     * @return the result of the TFL statement in boolean
     */
    static boolean not(boolean p){
        if (p==true){
            return false;}
        else{
            return true;}
    }



}
