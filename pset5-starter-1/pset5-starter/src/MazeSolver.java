import java.io.*;

class MazeSolver {

    private char[][] maze;
    private int rows;
    private int col;

    /**
     * THIS IS THE CONSTRUCTOR it makes the maze from the file
     * @param FileName
     */
    public MazeSolver(String FileName) {
        try{
            BufferedReader reader=new BufferedReader(new FileReader(FileName));
            String[] dimen = reader.readLine().split(" ");
            rows= Integer.parseInt(dimen[0]);
            col= Integer.parseInt(dimen[1]);
            maze = new char[rows][col];

            for (int i =0; i<rows; i++){
                String line = reader.readLine();
                for (int j=0; j<col; j++){
                    maze[i][j]=line.charAt(j);
                }
            }
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Recursively solves the maze, returning a solution if it exists,
     * and null otherwise. We use a simple backtracking algorithm
     * in the helper.
     * @return a solution to the maze, or null if it does not exist.
     */
    char[][] solve() {
        char[][] copy = new char[rows][col];
        for (int i=0; i<rows; i++){
            copy[i]= maze[i].clone();
        }

        if (solveHelper(0,0,copy)){
            return copy;
        }else{
            return null;
        }
    }


    /**
     * Recursively solves the maze, returning true if we ever reach
     * the exit. We try al possible paths from the current cell, if
     * they are reachable. If a path ends up being a dead end, we
     * backtrack and try another path.
     * @param r the row of the current cell.
     * @param c the column of the current cell.
     * @param sol the current solution to the maze.
     * @return true if we are at the exit, false otherwise.
     */
    private boolean solveHelper(int r, int c, char[][] sol) {
        if (r<0||r>=rows||c<0||c>=col||sol[r][c]!='.'){
            return false;
        }
        sol[r][c]='*';
        if(r==rows-1 &&c==col-1){
            return true;
        }
        if (solveHelper(r + 1, c, sol)||solveHelper(r - 1, c, sol)||
                solveHelper(r, c + 1, sol)||solveHelper(r, c - 1, sol)) {
            return true;
        }
        sol[r][c]='.';
        return false;

    }


    /**
     * outputs the given solution to the
     * maze to a file specified by the parameter. Refer to the above description for the format of the
     * output file and the input char[][] solution.
     * @param fileName file.
     * @param soln the solution in char[][] format.
     */
    void output(String fileName, char[][] soln) {
        try {
            BufferedWriter WRITE = new BufferedWriter(new FileWriter(fileName));
            for (char[] row: soln){
                for (char c: row){
                    WRITE.write(c);
                }
                WRITE.newLine();
            }
            WRITE.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
