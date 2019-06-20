/**
 * Name: Hao Li
 * Student#: 11153054
 * NSID: hal356
 * CMPT 317 A3Q3
 *
 * The classes in this file is:
 * main class
 * state class
 * problem state class
 * variable class
 * identifier class (position)
 */


import java.io.*;
import java.util.*;

/**
 *  This is the main class to execute the program in A3q2
 */
public class a3q3 {
    /**
     * The main to read files and run the search algorithm
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader(new File("LatinSquares.txt"));// create a file reader
        //FileReader fileReader = new FileReader(new File("src/hard_examples.txt"));
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
                System.out.println();
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
        SearchResult temp_result = search.DepthFirstSearch(state);
        System.out.println(temp_result.toString());
        // close readers
        bufferedReader.close();
        fileReader.close();
    }

}




/**
 *  This is the STATE class, which contained several necessary elements
 */
class State{
    ArrayList<ArrayList<Variable>> matrix; // the matrix square
    int size; // the size of the matrix
    Pos current_pos;// the  identifier for current state
    ArrayList<Pos> blank_list; // A list contained all blank points' identifier
    State(ArrayList<ArrayList<Variable>> matrix, int size,Pos current_pos, ArrayList<Pos> blank_list){
        this.matrix = matrix;
        this.size = size;
        this.current_pos = current_pos;
        this.blank_list = blank_list;
    }

    /**
     * Display the matrix square if needed
     */
    void Display(){
        System.out.println("This matrix is "+this.size+" * "+this.size+" size");
        for (ArrayList<Variable> temp_list:this.matrix
        ) {
            for (Variable temp: temp_list
            ) {
                System.out.print(temp.getValue()+"\t");
            }
            System.out.println();
        }
        System.out.println();
    }




    public void setCurrent_pos(Pos current_pos) {
        this.current_pos = current_pos;
    }
    /**
     * This function to help the clone the current state
     * @return
     */
    State copy(){
        ArrayList<ArrayList<Variable>> temp_matrix = new ArrayList<>();// create new matrix
        int size= this.size;
        Pos current = this.current_pos;

        // copy the matrix
        for (ArrayList<Variable> temp_m: this.matrix
        ) {
            ArrayList<Variable> temp_mm = new ArrayList<>(temp_m);
            temp_matrix.add(temp_mm);
        }

        // copy position
        ArrayList<Pos> temp_pos = new ArrayList<>(this.blank_list);

        // return the new state
        return new State(temp_matrix,size,current,temp_pos);
    }
}



/**
 * This is Problem state class
 */
class Problem {

    String square_size; //the size of enteren square
    ArrayList<ArrayList<String>> matrix; // the original matrix square
    State initial_state; // original state
    Problem(String square_size, ArrayList<ArrayList<String>> matrix){
        this.square_size = square_size;
        this.matrix = matrix;
        this.initial_state = create_initial_state();
    }
    /**
     * This function will create a initial state at beginning of search
     * @return initial state
     */
    State create_initial_state(){
        int size = Integer.parseInt(this.square_size);// set the size
        ArrayList<Pos> posArrayList = new ArrayList<>();
        // create the domain for unassigned variables
        ArrayList<Integer> domain = new ArrayList<>();
        for (int i=1;i<size+1;i++){
            domain.add(i);
        }
        // add value to the matrix and the variables, find the blank positions
        ArrayList<ArrayList<Variable>> state_m = new ArrayList<>();
        for (int i=0;i<this.matrix.size();i++){
            ArrayList<Variable> temp_array = new ArrayList<>();
            for (int k=0;k<this.matrix.get(i).size();k++){
                Variable temp_variable;
                if (this.matrix.get(i).get(k).equals("_")){
                    // create new position and add to unassigned list
                    Pos temp_pos = new Pos(i,k,false);
                    posArrayList.add(temp_pos);
                    // create new variable, add to the temp array list
                    temp_variable =  new Variable(0,domain,new ArrayList<>());
                    temp_array.add(temp_variable);
                }
                else {
                    // add variable to the temp array list
                    temp_variable = new Variable(Integer.parseInt(this.matrix.get(i).get(k)),new ArrayList<>(),new ArrayList<>());
                    temp_array.add(temp_variable);
                }
            }
            state_m.add(temp_array);
        }
        for (int d=0;d<posArrayList.size();d++){
            state_m.get(posArrayList.get(d).getRow()).get(posArrayList.get(d).getColumn()).setDomain(TestLatin(state_m,posArrayList.get(d)));
        }

        // return initial state
        return new State(state_m,size,null,posArrayList);
    }

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
                if ((a_state.matrix.get(i).get(k).getValue() ==0)){
                    return false;
                }
                int row=k+1;
                if (row< a_state.matrix.size()){
                    for (int m = row;m<a_state.matrix.size(); m++){
                        if (a_state.matrix.get(i).get(k).getValue() == a_state.matrix.get(i).get(m).getValue()){
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
                    if (a_state.matrix.get(k).get(i).getValue() == (a_state.matrix.get(column).get(i)).getValue()){
                        return false;
                    }
                }
            }
        }
        return true;
    }



    /**
     * Put an action to current state
     * @param a_state
     * @return put action under the constraint of domain
     */
    ArrayList<Integer> actions(State a_state){

        a_state.setCurrent_pos(null);
        //iterate the list of blank positions
        for (int i =0;i<a_state.blank_list.size();i++) {
            // if find the first blank then save it
            if (!a_state.blank_list.get(i).getResult()) {
                a_state.setCurrent_pos(a_state.blank_list.get(i));
                if (a_state.matrix.get(a_state.blank_list.get(i).getRow()).get(a_state.blank_list.get(i).getColumn()).getDomain().size() == 0){
                    for (int k=1;k<a_state.size+1;k++){
                        a_state.matrix.get(a_state.blank_list.get(i).getRow()).get(a_state.blank_list.get(i).getColumn()).getDomain().add(k);
                    }
                }
                Collections.shuffle(a_state.matrix.get(a_state.blank_list.get(i).getRow()).get(a_state.blank_list.get(i).getColumn()).getDomain());
                return a_state.matrix.get(a_state.blank_list.get(i).getRow()).get(a_state.blank_list.get(i).getColumn()).getDomain();
            }
        }

        if (a_state.current_pos == null){
            ArrayList<Integer> empty = new ArrayList<>();
            empty.add(0);
            return empty;
        }
        return null;
    }


    /**
     * This class will dealing with the entered action to copied State
     * @param a_state
     * @param action
     * @return
     */
    State result(State a_state, int action){
        State state = a_state.copy();
        if (action == 0 ){
            return create_initial_state();
        }
        state.matrix.get(state.current_pos.getRow()).get(state.current_pos.getColumn()).setValue(action);
        for (int i=0;i<state.blank_list.size();i++){
            if (state.current_pos.equals(state.blank_list.get(i))){
                state.blank_list.get(i).setResult(true);
            }
        }
        return state;
    }

    /**
     * This will test if the action already in the row or column
     * @param matrix
     * @param p
     * @return the boolean result
     */
    ArrayList<Integer> TestLatin(ArrayList<ArrayList<Variable>> matrix, Pos p){
        ArrayList<Integer> temp_column = new ArrayList<>();
        ArrayList<Integer> temp_row = new ArrayList<>();
        // test all elements in this column
        for (int k=0; k< matrix.size();k++){
            if (k!=p.getRow()){
                temp_column.add(matrix.get(k).get(p.getColumn()).getValue());
            }

        }
        // test all element in the same column
        for (int k=0;k<matrix.get(p.getRow()).size();k++) {
            if (k != p.getColumn()) {
                temp_row.add(matrix.get(p.getRow()).get(k).getValue());

            }
        }
        ArrayList<Integer> exist = new ArrayList<>(matrix.get(p.getRow()).get(p.getColumn()).getDomain());
        exist.removeAll(temp_column);
        exist.removeAll(temp_row);

        return exist;
    }
}





/**
 * This is Variable class
 */
class Variable implements Cloneable{
    int value;// value of the variable
    ArrayList<Integer> domain; // domain of current variable
    ArrayList<Integer> constraint; // constraint of current variable
    Variable(int value, ArrayList<Integer> domain,ArrayList<Integer> constraint){
        this.value = value;
        this.domain = domain;
        this.constraint = constraint;
    }

    public ArrayList<Integer> getConstraint() {
        return constraint;
    }

    public void setConstraint(ArrayList<Integer> constraint) {
        this.constraint = constraint;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ArrayList<Integer> getDomain() {
        return domain;
    }

    public void setDomain(ArrayList<Integer> domain) {
        this.domain = domain;
    }


    /**
     * Clone current variable
     * @return
     */
    public Variable clone() {
        Variable v = null;
        try{
            v= (Variable)super.clone();
        } catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return v;
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

