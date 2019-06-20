/**
 * Name: Hao Li
 * Student#: 11153054
 * NSID: hal356
 * CMPT 317 A3Q1
 *
 * The classes in this file is:
 * main class
 * state class
 * problem state class
 * identifier class (position)
 *
 * This algorithm has a 2-d list as matrix square for state
 *
 * The action function will find the first blank position in matrix, then give it list of action from allowed size of square
 * The result function will check if the action not 0, then do the action to the first blank position
 * If the action is 0, means all blank position are filled but sill not touch the goal, then go back to the first blank position and try other value
 */
import java.io.*;
import java.util.*;
/**
 *  This is the main class to execute the program in A3q1
 */
public class a3q1 {
    /**
     * The main to read files and run the search algorithm
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader(new File("LatinSquares.txt"));// create a file reader
        //FileReader fileReader = new FileReader(new File("src/temp.txt"));
        BufferedReader bufferedReader = new BufferedReader(fileReader); // create a buffered reader
        //elements need to use later
        Scanner scanner;// scan each line
        String line; // save each line
        Problem problem;// the problem
        Search search;// the search
        ArrayList<ArrayList<String>> matrix = new ArrayList<>(); // the matrix in each state
        String size=""; // the size of each matrix
        int max_squares;// total matrix in the file
        int index =1;// the index of the matrix we are dealing with
        //get the number of matrix in the file
        if ((line = bufferedReader.readLine())!=null){
            max_squares = Integer.parseInt(line);
            System.out.println("There are "+max_squares+" matrix squares need to solve!");
            System.out.println();
        }
        //Read each line of the file
        while ((line=bufferedReader.readLine())!=null) {
            //If finish read each whole matrix, then solve it
            if (line.length() ==0){
                problem = new Problem(size,matrix);
                System.out.print(index+". ");
                index++;
                search = new Search(problem,10);
                State state = problem.initial_state;
                SearchResult temp_result = search.DepthFirstSearch(state);
                System.out.println(temp_result.toString());
                matrix = new ArrayList<>();
            }
            //read the matrix's body line by line
            scanner = new Scanner(line);
            if (scanner.hasNext()){
                String temp = scanner.next();
                if (!scanner.hasNext()){
                    size = temp;
                }
                else {
                    ArrayList<String> temp_list = new ArrayList<>();
                    temp_list.add(temp);
                    while (scanner.hasNext()){
                        temp_list.add(scanner.next());
                    }
                    matrix.add(temp_list);
                }
                scanner.close();
            }
        }
        //Search the last matrix
        problem = new Problem(size,matrix);
        search = new Search(problem,10);
        System.out.print(index+". ");
        State state = problem.initial_state;
        //SearchResult temp_result = OptionSearch(option,state,search,timelimit);
        SearchResult temp_result = search.DepthFirstSearch(state);
        System.out.println(temp_result.toString());
        // close readers
        bufferedReader.close();
        fileReader.close();
    }

}

/**
 * This is the state class
 */
class State {
    /**
     * This class have a 2-dimensional list of integer
     * and several helpful variables
     */
    ArrayList<Pos> blank_list;// the list of position need to fill
    int size; // the size of matrix
    Pos current_pos; // current position
    ArrayList<ArrayList<Integer>> matrix; // the 2-dimensional list of integer
    State(ArrayList<ArrayList<Integer>> matrix, int size, Pos current_pos, ArrayList<Pos> blank_list){
        this.matrix = matrix;
        this.blank_list = blank_list;
        this.size = size;
        this.current_pos = current_pos;
    }


    /**
     * This function to display the state's matrix
     */
    void Display(){
        System.out.println("This matrix is "+this.size+" * "+this.size+" size");
        for (ArrayList<Integer> temp_list:this.matrix
        ) {
            for (int temp: temp_list
            ) {
                System.out.print(temp+"\t");
            }
            System.out.println();
        }
    }

    /**
     * This function to help the clone the current state
     * @return
     */
    State copy(){
        ArrayList<ArrayList<Integer>> temp_matrix = new ArrayList<>();// create new matrix
        int size= this.size;
        Pos current = this.current_pos;

        // copy the matrix
        for (ArrayList<Integer> temp_m: this.matrix
        ) {
            ArrayList<Integer> temp_mm = new ArrayList<>(temp_m);
            temp_matrix.add(temp_mm);
        }

        // copy position
        ArrayList<Pos> temp_pos = new ArrayList<>(this.blank_list);

        // return the new state
        return new State(temp_matrix,size,current,temp_pos);
    }
}


/**
 * This is Problem class
 */
class Problem {

    String square_size; // the size of current matrix
    ArrayList<ArrayList<String>> matrix; // the matrix first read from file
    State initial_state; // the initial state in the problem

    /**
     * this constructor  to read option from main class and initial the state
     * @param square_size
     * @param matrix
     */
    Problem(String square_size,ArrayList<ArrayList<String>> matrix){
        this.square_size = square_size;
        this.matrix = matrix;
        initial_state = create_initial_state(); // initial the state

    }

    /**
     * create_initial_state
     * @return a initial state
     */
    State create_initial_state(){

        ArrayList<Pos> posArrayList = new ArrayList<>();// list to contain blank position
        ArrayList<ArrayList<Integer>> state_m = new ArrayList<>(); // 2-d list of square
        int size = Integer.parseInt(this.square_size);// get size

        /*
            Find blank position save to the list of blank position
            and save all elements to the matrix list
         */
        for (int i=0;i<this.matrix.size();i++){
            ArrayList<Integer> tempArray = new ArrayList<>();
            for (int k=0;k<this.matrix.get(i).size();k++){
                if (this.matrix.get(i).get(k).equals("_")){
                    Pos temp_pos = new Pos(i,k,false);
                    posArrayList.add(temp_pos);
                    tempArray.add(0);
                }
                else {
                    tempArray.add(Integer.parseInt(this.matrix.get(i).get(k)));
                }

            }
            state_m.add(tempArray);
        }

        // return initial state
        return new State(state_m,size,null,posArrayList);
    }


    /**
     * Check if the state is a latin Square
     * @param a_state the entered state
     * @return the result after check the goal
     */
    /**
     * Check the state reach the goal or not
     * @param a_state
     * @return the boolean result
     */
    boolean is_goal(State a_state){
        // iterate all row list in the square O(n^2)
        for (int i=0;i<a_state.matrix.size();i++){
            // iterate all elements in the row
            for (int k = 0; k< a_state.matrix.get(i).size();k++){
                // if there any element still a zero, then false
                if ((a_state.matrix.get(i).get(k) ==0)){
                    return false;
                }
                int row=k+1;
                if (row< a_state.matrix.size()){
                    for (int m = row;m<a_state.matrix.size(); m++){
                        if (a_state.matrix.get(i).get(k).equals(a_state.matrix.get(i).get(m))){
                            return false;
                        }
                    }
                }
            }
        }
        // check each column of the matrix O(n^2)
        for (int i=0;i<a_state.matrix.size();i++){
            for (int k=0;k<a_state.matrix.size();k++){
                int column=k+1;
                if (column<a_state.matrix.size()){
                    if (a_state.matrix.get(k).get(i).equals(a_state.matrix.get(column).get(i))){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Find the first blank and give it property list of actions
     * @param a_state a state entered
     * @return list of actions for the current blank position
     */
    ArrayList<Integer> actions(State a_state) {
        // create 1..N as options list
        ArrayList<Integer> options = new ArrayList<>();
        for (int i=1;i<a_state.size+1;i++){
            options.add(i);
        }
        Collections.shuffle(options);
        a_state.current_pos = null;
        for (int i=0;i<a_state.blank_list.size();i++){
            if (!a_state.blank_list.get(i).getResult()){
                a_state.current_pos = a_state.blank_list.get(i);
                //System.out.println(a_state.current_pos.toString());
                return options;
            }
        }
        if (a_state.current_pos == null){
            ArrayList<Integer> empty = new ArrayList<>();
            empty.add(0);
            return empty;
        }

        return options;
    }


    /**
     *  The function will do the action in current state then create next step of state
     * @param a_state entered state
     * @param action the action for current state
     * @return the next step after do the action for current state
     */
    State result(State a_state, int action){
        State state = a_state.copy();
        if (action == 0 ){
            return create_initial_state();
        }
        state.matrix.get(state.current_pos.getRow()).set(state.current_pos.getColumn(),action);
        for (int i=0;i<state.blank_list.size();i++){
            if (state.current_pos.equals(state.blank_list.get(i))){
                state.blank_list.get(i).setResult(true);
            }
        }
        return state;
    }
}


/**
 * This is help class to create identifier for each state
 */
class Pos {
    int row, column;// position row and column
    boolean result; // check current position already filled or not

    Pos(int row, int column,boolean result){
        this.row = row;
        this.column = column;
        this.result = result;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
    public boolean getResult(){
        return this.result;
    }

    @Override
    public String toString() {
        return "row: "+ this.row+", "+"column: "+ this.column;
    }
}



















