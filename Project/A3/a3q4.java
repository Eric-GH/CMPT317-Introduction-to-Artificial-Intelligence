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
 * Variable class
 * Position class
 *
 */
import java.io.*;
import java.util.*;
/**
 *  This is the main class to execute the program in A3q1
 */
public class a3q4 {
    /**
     * The main to read files and run the search algorithm
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader(new File("LatinSquares.txt"));// create a file reader
        //FileReader fileReader = new FileReader(new File("src/hard_examples.txt"));
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
        search = new Search(problem,1000);
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
    ArrayList<Identifier> blank_list;// the list of position need to fill
    int size; // the size of matrix
    Identifier current_pos; // current position
    ArrayList<ArrayList<Integer>> matrix; // the 2-dimensional list of integer
    boolean flag;
    State(ArrayList<ArrayList<Integer>> matrix, int size, Identifier current_pos, ArrayList<Identifier> blank_list,boolean flag){
        this.matrix = matrix;
        this.blank_list = blank_list;
        this.size = size;
        this.current_pos = current_pos;
        this.flag = flag;
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

    public Identifier getCurrent_pos() {
        return current_pos;
    }

    public void setCurrent_pos(Identifier current_pos) {
        this.current_pos = current_pos;
    }

    /**
     * This function to help the clone the current state
     * @return
     */
    State copy(){
        ArrayList<ArrayList<Integer>> temp_matrix = new ArrayList<>();// create new matrix
        int size= this.size;
        Identifier current = (Identifier) this.current_pos.clone();

        // copy the matrix
        for (ArrayList<Integer> temp_m: this.matrix
        ) {
            ArrayList<Integer> temp_mm = new ArrayList<>(temp_m);
            temp_matrix.add(temp_mm);
        }

        // copy position
        ArrayList<Identifier> temp_pos = new ArrayList<>(this.blank_list);
        boolean temp_flag = this.flag;
        // return the new state
        return new State(temp_matrix,size,current,temp_pos,temp_flag);
    }
}


/**
 * This is Problem class
 */
class Problem {
    Pos p;
    boolean back=false;
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

        ArrayList<Identifier> posArrayList = new ArrayList<>();// list to contain blank position
        ArrayList<ArrayList<Integer>> state_m = new ArrayList<>(); // 2-d list of square
        ArrayList<Integer> domain;
        ArrayList<Integer> temp_domain = new ArrayList<>();// create temp domain's value
        int size = Integer.parseInt(this.square_size);// get size

        for (int d=1;d<size+1;d++){
            temp_domain.add(d);
        }
        /*
            Find blank position save to the list of blank position
            and save all elements to the matrix list
         */
        for (int i=0;i<this.matrix.size();i++){
            ArrayList<Integer> tempArray = new ArrayList<>();
            for (int k=0;k<this.matrix.get(i).size();k++){
                if (this.matrix.get(i).get(k).equals("_")){
                    domain = new ArrayList<>(temp_domain);// create new domain
                    posArrayList.add(new Identifier(new Pos(i,k),new Variable(0,domain,new ArrayList<>()),new ArrayList<>(),false));
                    tempArray.add(0);
                }
                else {
                    tempArray.add(Integer.parseInt(this.matrix.get(i).get(k)));
                }

            }
            state_m.add(tempArray);
        }

        /*
            delete domain value which already in same row or column
         */
        for (Identifier aPosArrayList : posArrayList) {
            aPosArrayList.getV().setDomain(TestLatin(state_m, aPosArrayList));
        }

        // return initial state
        return new State(state_m,size,null,posArrayList,true);
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

        if (!a_state.flag){

            return false;
        }
        // test assign for value
        for (Identifier p : a_state.blank_list
        ) {
            if (!p.getResult()){

                return false;
            }
        }
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
                            System.out.println(i+", "+k);
                            System.out.println(i+", "+m);
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
    ArrayList<Actions> actions(State a_state) {

        ArrayList<Actions> act;

        if (!a_state.flag){

            act = new ArrayList<>();
            act.add(new Actions(a_state.getCurrent_pos().getV(),0));
            return act;
        }

        Variable variable;

        for (int i=0;i<a_state.blank_list.size();i++){
            if (!a_state.blank_list.get(i).getResult()){
                a_state.setCurrent_pos(a_state.blank_list.get(i));

                act = new ArrayList<>();


                for (int k=0;k<a_state.blank_list.get(i).getV().getDomain().size();k++){
                    int temp_value = a_state.blank_list.get(i).getV().getDomain().get(k);// the value is the one of domain for n to n
                    variable = new Variable(temp_value,a_state.blank_list.get(i).getV().getDomain(),a_state.blank_list.get(i).getV().getConstraint());//set action's variable and current value
                    act.add(new Actions(variable,temp_value)); // add to list of action
                }

                return act;
            }
        }
        // There should never be reached
        ArrayList<Actions> empty = new ArrayList<>();
        empty.add(new Actions(null,0));
        return empty;
    }


    /**
     *  The function will do the action in current state then create next step of state
     * @param a_state entered state
     * @param action the action for current state
     * @return the next step after do the action for current state
     */
    State result(State a_state, Actions action){
        State state = a_state.copy();
        back = false;
        if (action.getV() == 0 ){
;

            return BackTrack(state);
        }
        state.matrix.get(state.getCurrent_pos().getPos().getRow()).set(state.getCurrent_pos().getPos().getColumn(),action.getV());
        for (int i=0;i<state.blank_list.size();i++){
            // find the position in blank_list
            if (state.getCurrent_pos().getPos().equals(state.blank_list.get(i).getPos()) && state.blank_list.get(i).getV().getDomain().equals(action.getX().getDomain())){
                state.blank_list.get(i).getV().setValue(action.getV());
                state.blank_list.get(i).setResult(true); // set the result as true
            }
        }

        return ForwardChecking(state,action);
    }

    /**
     * This will test if the action already in the row or column
     * @param matrix
     * @param p
     * @return the boolean result
     */
    ArrayList<Integer> TestLatin(ArrayList<ArrayList<Integer>> matrix, Identifier p){
        ArrayList<Integer> temp_column = new ArrayList<>();
        ArrayList<Integer> temp_row = new ArrayList<>();
        // test all elements in this column
        for (int k=0; k< matrix.size();k++){
            if (k!=p.getPos().getRow()){
                temp_column.add(matrix.get(k).get(p.getPos().getColumn()));
            }

        }
        // test all element in the same column
        for (int k=0;k<matrix.get(p.getPos().getRow()).size();k++) {
            if (k != p.getPos().getColumn()) {
                temp_row.add(matrix.get(p.getPos().getRow()).get(k));

            }
        }
        ArrayList<Integer> exist = new ArrayList<>(p.getV().getDomain());
        exist.removeAll(temp_column);
        exist.removeAll(temp_row);

        return exist;
    }

    /**
     * forward checking function
     * @param state
     * @return removed all domain's elements which same as action
     */
    State ForwardChecking(State state,Actions action){

        if (p!=null && !back){
            if (p.getRow() == state.getCurrent_pos().getPos().getRow() && p.getColumn() == state.getCurrent_pos().getPos().getColumn()){

                for (int s=0;s<state.blank_list.size();s++){
                    if (!state.blank_list.get(s).getResult()){
                        for (int m=0;m<state.blank_list.get(s).getDeleted().size();m++){
                            if (state.blank_list.get(s).getDeleted().get(m).getRow()==(state.getCurrent_pos().getPos().getRow())&&state.blank_list.get(s).getDeleted().get(m).getColumn()==(state.getCurrent_pos().getPos().getColumn())){
                                state.blank_list.get(s).getV().getDomain().add(state.getCurrent_pos().getV().getValue());
                            }
                        }
                        // after adding, delete this position from the id

                        state.blank_list.get(s).getDeleted().removeIf(pos -> (state.getCurrent_pos().getPos().getRow() == pos.getRow() && state.getCurrent_pos().getPos().getColumn() == pos.getColumn()));
                    }
                }
            }
        }
        p = state.getCurrent_pos().getPos();
        state.getCurrent_pos().getV().setValue(action.getV());


        /*
            Find same row and same column's position's domain, delete the number in domain which same as the action
         */
        for (int i=0; i<state.blank_list.size();i++){
            // the position must be unassigned one
            if (!state.blank_list.get(i).getResult()){
                // find the position

                if (state.blank_list.get(i).getPos().getRow() == state.getCurrent_pos().getPos().getRow() || state.blank_list.get(i).getPos().getColumn() == state.getCurrent_pos().getPos().getColumn()){

                    // make sure after deletion, the domain still has value in it

                    int size = state.blank_list.get(i).getV().getDomain().size();
                    state.blank_list.get(i).getV().getDomain().removeIf(integer -> state.getCurrent_pos().getV().getValue() == integer);
                    // make sure the deletion are correct

                    if (size>state.blank_list.get(i).getV().getDomain().size()){
                        state.blank_list.get(i).getDeleted().add(new Pos(state.getCurrent_pos().getPos().getRow(),state.getCurrent_pos().getPos().getColumn()));//new Pos(state.current_pos.getRow(),state.current_pos.getColumn()));

                    }
                }
                // make sure check the domain is not empty
                if (state.blank_list.get(i).getV().getDomain().size()==0){
                    state.flag = false;// if any domain are empty, return flag as false

                }
            }
        }
        // if flag is false, then this time's deletion undo // maybe not need
        if (!state.flag){
            for (int s=0;s<state.blank_list.size();s++){
                if (!state.blank_list.get(s).getResult()){
                    for (int m=0;m<state.blank_list.get(s).getDeleted().size();m++){
                        if (state.blank_list.get(s).getDeleted().get(m).getRow()==(state.getCurrent_pos().getPos().getRow())&&state.blank_list.get(s).getDeleted().get(m).getColumn()==(state.getCurrent_pos().getPos().getColumn())){

                            state.blank_list.get(s).getV().getDomain().add(state.getCurrent_pos().getV().getValue());

                        }
                    }
                    // after adding, delete this position from the id
                    state.blank_list.get(s).getDeleted().removeIf(pos -> (state.getCurrent_pos().getPos().getRow() == pos.getRow() && state.getCurrent_pos().getPos().getColumn() == pos.getColumn()));
                }
            }
            return state;
        }
        return state;
    }


    /**
     * This function will find which position make a domain became empty
     * @param a_state
     * @return redo the error position with other value
     */
    State BackTrack(State a_state){

        if (a_state.getCurrent_pos().getV().getDomain().size()>1){
            boolean flag_same=false;
            int index=0;
            int used = a_state.getCurrent_pos().getV().getValue();
            for (int f=0;f<a_state.blank_list.size();f++){

                if (a_state.getCurrent_pos().getPos().getRow() == a_state.blank_list.get(f).getPos().getRow() && a_state.getCurrent_pos().getPos().getColumn() == a_state.blank_list.get(f).getPos().getColumn()){

                    a_state.blank_list.get(f).getV().getConstraint().add(used);
                    a_state.blank_list.get(f).getV().getDomain().removeIf(integer -> used == integer);
                    a_state.getCurrent_pos().getV().getConstraint().add(used);
                    a_state.getCurrent_pos().getV().getDomain().removeIf(integer -> used==integer);
                    index = f;
                }
            }


            /*
                find the current value in domain, and delete it
             */
            int value;
            for (int i=0;i<a_state.getCurrent_pos().getV().getDomain().size();i++){

                if (!a_state.getCurrent_pos().getV().getDomain().get(i).equals(a_state.matrix.get(a_state.getCurrent_pos().getPos().getRow()).get(a_state.getCurrent_pos().getPos().getColumn()))){

                    value =a_state.getCurrent_pos().getV().getDomain().get(i);
                    a_state.matrix.get(a_state.getCurrent_pos().getPos().getRow()).set(a_state.getCurrent_pos().getPos().getColumn(),value);

                    Actions actions = new Actions(new Variable(value,a_state.getCurrent_pos().getV().getDomain(),a_state.getCurrent_pos().getV().getConstraint()),value);
                    a_state.flag = true;
                    back = true;
                    return ForwardChecking(a_state,actions);
                }
            }

        }
        else {
            // if current action's domain only has one value
            int index=0;
            ArrayList<Integer> temp_con;
            // find last current id and change it result flag
            for (int i=0;i<a_state.blank_list.size();i++){
                if (a_state.getCurrent_pos().getPos().getRow() == (a_state.blank_list.get(i).getPos().getRow()) &&a_state.getCurrent_pos().getPos().getColumn() == (a_state.blank_list.get(i).getPos().getColumn())){
                    temp_con = new ArrayList<>();
                    for (int p: a_state.blank_list.get(i).getV().getConstraint()) {
                        temp_con.add(p);
                    }

                    a_state.blank_list.get(i).getV().getDomain().addAll(temp_con);
                    a_state.blank_list.get(i).getV().setConstraint(new ArrayList<>());
                    a_state.blank_list.get(i).setResult(false);
                    index=i;// get current id's index
                }
            }
            if (index!=0){
                index = index-1;// find index of last blank position
            }
            Identifier id = (Identifier) a_state.blank_list.get(index).clone();
            a_state.setCurrent_pos(id);// reset the current position as last one
            int value = a_state.matrix.get(a_state.getCurrent_pos().getPos().getRow()).get(a_state.getCurrent_pos().getPos().getColumn());
            a_state.getCurrent_pos().getV().setValue(value);
            /*
                Add all value back from deletion of current position
             */
            for (int s=0;s<a_state.blank_list.size();s++){
                if (!a_state.blank_list.get(s).getResult()){
                    for (int m=0;m<a_state.blank_list.get(s).getDeleted().size();m++){
                        if (a_state.blank_list.get(s).getDeleted().get(m).getRow()==a_state.getCurrent_pos().getPos().getRow() && a_state.blank_list.get(s).getDeleted().get(m).getColumn()==a_state.getCurrent_pos().getPos().getColumn()){
                            a_state.blank_list.get(s).getV().getDomain().add(value);
                        }
                    }
                    // after adding, delete this position from the id
                    a_state.blank_list.get(s).getDeleted().removeIf(pos -> (a_state.getCurrent_pos().getPos().getRow() == pos.getRow() && a_state.getCurrent_pos().getPos().getColumn() == pos.getColumn()));
                }
            }
            // try if this time the current action has more than one value
            return BackTrack(a_state);
        }
        return null;
    }





}




/**
 * This is Variable class
 */
class Variable implements Cloneable{
    int value;// value of the variable
    ArrayList<Integer> domain; // domain of current variable
    ArrayList<Integer> constraint;
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
        ArrayList<Integer> temp_d = new ArrayList<>();
        for (int i=0;i<this.domain.size();i++){
            int a = this.domain.get(i);
            temp_d.add(a);

        }
        ArrayList<Integer> temp_c = new ArrayList<>();
        for (int d=0;d<this.getConstraint().size();d++){
            int c = this.getConstraint().get(d);
            temp_c.add(c);
        }
        v.setDomain(temp_d);
        v.setConstraint(temp_c);
        return v;
    }

}

class Identifier implements Cloneable{
    Pos pos;// position row and column
    boolean result; // check current position already filled or not
    Variable v;
    ArrayList<Pos> deleted;
    Identifier(Pos pos,Variable v, ArrayList<Pos> deleted,boolean result){
        this.pos = pos;
        this.result = result;
        this.v = v;
        this.deleted = deleted;
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
    public boolean getResult(){
        return this.result;
    }

    public Variable getV() {
        return v;
    }

    public void setV(Variable v) {
        if (v!=null){
            this.v = v;
        }

    }

    public void setDeleted(ArrayList<Pos> deleted) {
        this.deleted = deleted;
    }

    public ArrayList<Pos> getDeleted() {
        return deleted;
    }

    public Identifier clone(){
        Identifier id = null;
        try {
            id = (Identifier)super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        Variable d = (Variable) this.getV().clone();
        ArrayList<Pos> temp_d = new ArrayList<>();
        for (int i=0;i<this.getDeleted().size();i++){
            Pos temp_p = this.getDeleted().get(i);
            temp_d.add(temp_p);
        }
        id.setV(d);
        id.setDeleted(temp_d);
        return id;
    }
    /*
        Identifier copy(){
            ArrayList<Pos> temp_d = new ArrayList<>();
            for (Pos d: this.getDeleted()
                 ) {
                temp_d.add(d);
            }
            Pos temp_p = this.getPos();
            boolean temp_r = this.getResult();
            Variable temp_v = this.getV().clone();
            return new Identifier(temp_p,temp_v,temp_d,temp_r);
        }
        */
    @Override
    public String toString() {
        return "Position "+ this.pos+" Variable is: "+this.v +" result is: "+this.result;
    }
}

/**
 * This is help class to create identifier for each state
 */
class Pos {
    int row, column;// position row and column
    Pos(int row, int column){
        this.row = row;
        this.column = column;

    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "row: "+ this.row+", "+"column: "+ this.column;
    }
}



class Actions{
    Variable x;
    int v;
    Actions(Variable x, int v){
        this.x = x;
        this.v = v;
    }

    public Variable getX() {
        return x;
    }

    public void setX(Variable x) {
        this.x = x;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

}














