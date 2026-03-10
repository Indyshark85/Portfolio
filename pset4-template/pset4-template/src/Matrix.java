class Matrix {

    private int numRows;
    private int numCols;
    private int[][] matrix;

    Matrix(int numRows, int numCols, int[][] m) {
        this.matrix = new int[numRows][numCols];
        this.numCols = numCols;
        this.numRows = numRows;

        for(int i=0; i<numRows;i++){
            for(int j=0;j<numCols;j++){
                this.matrix[i][j]=m[i][j];

            }
        }
    }

    /**
     * method, which sets the value at row 𝑖
     * and column 𝑗 to val.
     *
     * @param i int.
     * @param j int.
     * @param val int.
     */
    void set(int i, int j, int val) {
        if (i>=0 && i<numRows && j>=0 && j<numRows){
            matrix[i][j]=val;
        }
    }

    /**
     * method, which adds the values of the passed matrix
     * to the current matrix.
     *
     * @param m matrix (but like the movie this time).
     * @return boolean.
     */
    boolean add(Matrix m) {
        if (m.numRows != this.numRows || m.numCols != this.numCols){
            return false;
        }
        for(int i=0; i<numRows;i++){
            for(int j=0;j<numCols;j++){
                this.matrix[i][j] += m.matrix[i][j];
            }
        }
      return true;
    }

    /**
     * method, which multiplies the values of the
     * passed matrix to the current matrix.
     *
     * @param m Matrix (but like the blue pill version).
     * @return boolean.
     */
    boolean multiply(Matrix m) {
        if (this.numCols != m.numRows){
            return false;
        }
        int[][] result = new int[this.numRows][m.numCols];

        for(int i=0; i<numRows;i++){
            for(int j=0;j<m.numCols;j++){
              result[i][j]=0;
              for(int k=0; k<this.numCols;k++){
                  result[i][j] += this.matrix[i][k] * m.matrix[k][j];
              }
            }
        }
        this.matrix=result;
        this.numCols=m.numCols;
        return true;

    }

    /**
     * method, which transposes the matrix. That is, the rows
     * become the columns and the columns become the rows.
     */
    void transpose() {
        int[][] result = new int[this.numCols][this.numRows];
        for(int i=0; i<numRows;i++){
            for(int j=0;j<numCols;j++){
                result[j][i] = matrix[i][j];
            }
        }
        this.matrix=result;
        int temporaryCol = this.numCols;
        this.numCols=numRows;
        this.numRows=temporaryCol;

    }

    /**
     *  method, rotates the matrix 90 degrees clockwise. To rotate
     * a matrix, compute the transposition and then reverse the row.
     */
    void rotate() {
        int[][] result = new int[this.numCols][this.numRows];
        for(int i=0; i<numRows;i++){
            for(int j=0;j<numCols;j++){
                result[j][numRows-1-i] = matrix[i][j];
            }
        }
        this.matrix=result;
        int temporaryCol = this.numCols;
        this.numCols=this.numRows;
        this.numRows=temporaryCol;
    }

    /**
     *  method to return a stringified version of the
     * matrix.
     *
     * @return string.
     */
    @Override
    public String toString() {
        StringBuilder STR = new StringBuilder("[");
        for(int i=0; i<numRows;i++){
            STR.append("[");
            for(int j=0;j<numCols;j++){
                STR.append(this.matrix[i][j]);
                if (j<numCols-1) STR.append(", ");
            }
            STR.append("]");
            if (i<numRows-1) STR.append(", ");
        }
        STR.append("]");
        return STR.toString();
    }

    int getRows() {
        return numRows;
    }

    int getCols() {
        return numCols;
    }

    int[][] getMatrix() {
        return matrix;
    }
}
