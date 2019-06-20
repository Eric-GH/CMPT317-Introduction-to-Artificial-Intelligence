/**
 * Name: Hao Li
 * NSID: hal356
 * Student#: 11153054
 * CMPT 317 A4
 *
 * This is the Problem state class
 * this class implements most needed functions to help solve the game
 * There are two utility functions to determine different variations 1a/1b
 *
 */



import java.util.ArrayList;
import java.util.Collections;

public class Problem implements game_search{
    int queen_size; // the size of the game board
    State initial_state; // the initial state of the game
    Problem(int queen_size){
        this.queen_size = queen_size;
        this.initial_state = create_initial_state();
    }

    /**
     * Create all initial elements to the initial state
     * @return initial state
     */
    State create_initial_state(){
        int board_size = queen_size * queen_size;
        Queen[] board = new Queen[board_size];
        int counter=0;
        int row=0;
        for (int i = 0; i<board_size;i++){
            if (counter!=0&&counter%queen_size==0){
                counter = 0;
                row++;
            }
            board[i] = new Queen(row,counter);
            counter++;

        }
        return new State(board,new Queen[queen_size],queen_size,true,1,1);
    }


    /**
     * Determine whether the game is can still continue
     * @param state the current state of the game
     * @return true the game should be end
     */
    @Override
    public boolean is_terminal(State state) {
        // check if the state not legal
        if (!state.legal){
            return true;
        }

        // check if the queens is full on board
        int counter=0;
        for (Queen q: state.queen
        ) {
            if (q!=null){
                counter++;
            }
        }
        return counter >= state.size;
    }


    /**
     * The utility function to return the minimax value
     * for variation 1a of the N-queens game
     * @param state current state
     * @return the Minimax value
     */
    @Override
    public int utility_For_Va(State state) {

        // count how many queens on the board
        int count = 0;
        for (int i=0;i<state.queen.length;i++){
            if (state.queen[i]!=null){
                count++;
            }
        }

        // determine which player's turn right now
        if (count % 2 ==0){
            return -1;
        }
        return 1;
    }


    /**
     * The utility function to return the minimax value
     * for variation 1b of the N-queens game
     * @param state current state
     * @return the Minimax value
     */
    @Override
    public int utility_For_Vb(State state) {
        // count how many queens on the board
        int count = 0;
        for (int i=0;i<state.queen.length;i++){
            if (state.queen[i]!=null){
                count++;
            }
        }

        // determine which player's turn right now
        if (count % 2 ==0){
            return -count;
        }
        return count;
    }


    /**
     * This function will find all the available moves
     * for current state
     * @param a_state current state
     * @return all possible moves
     */
    @Override
    public ArrayList<Queen> actions(State a_state) {

        boolean flag_blank = false; // help to find the available moves
        int current_column=0;// find current column we are looking for

        // find next available column for current state
        for (int i=0;i<a_state.size;i++) {
            for (Queen queen:a_state.queen) {
                if (queen!= null && i == queen.getPositionY()){
                    flag_blank = true;
                }
            }
            if (!flag_blank){
                current_column = i;
                break;
            }
            else {
                flag_blank = false;
            }
        }


        Queen[] current = new Queen[a_state.size]; // list of nodes of current column

        int counter = 0;// index of current column array

        /*
            Find all nodes' position of current column
         */
        for (Queen board:a_state.board) {
            if (board.getPositionY() == current_column){
                current[counter] = board;
                counter++;
            }
        }

        // find available moves for current column
        ArrayList<Queen> actions = new ArrayList<>();
        for (Queen cur: current) {
            if (cur!=null){
                flag_blank = false;
                for (Queen queen:a_state.queen) {
                    if (queen!= null){
                        if (cur.getPositionX() == queen.getPositionX() || cur.getPositionY() == queen.getPositionY() || (cur.getPositionX()-cur.getPositionY()) == (queen.getPositionX() - queen.getPositionY()) || (cur.getPositionX()+cur.getPositionY()) == (queen.getPositionX() + queen.getPositionY())){
                            flag_blank = true;
                        }
                    }

                }
                if (!flag_blank){
                    actions.add(cur);
                }
            }
        }

        /*
            no available move for current column
            legal = false
         */
        if (actions.size()==0){
            a_state.legal = false;
        }

        // return the list of moves for current column of current state
        return actions;
    }


    /**
     * This function will add the moves to the current state
     * @param a_state current state
     * @param action moves for current state
     * @return current state with the move
     */
    @Override
    public State result(State a_state, Queen action) {
        State state = a_state.copy();// copy the entered state
        /*
            add the move/queens on the list of queen
         */
        for (int q=0;q<state.queen.length;q++){
            if (state.queen[q] == null){
                state.queen[q] = new Queen(action.getPositionX(),action.getPositionY());
                break;
            }
        }
        return state;
    }

    /**
     * Determine whether it is player1's turn
     * @param state current state
     * @return true if current is player1's turn
     */
    @Override
    public boolean is_maxs_turn(State state) {
        return state.turn == 1;
    }


    /**
     * Determine whether it is player2's turn
     * @param state current state
     * @return true if current is player2's turn
     */
    @Override
    public boolean is_mins_turn(State state) {
        return state.turn == 2;
    }


    /**
     * This function will determine whether the current state need to cut off
     * @param state current state
     * @param depth the depth of need to cut off
     * @return true if current state need to cut off
     */
    @Override
    public boolean cutoff_test(State state, int depth) {
        return state.deep >= depth;
    }


    /**
     * This function will give a random minimax value
     * if current state is cut off
     * @param state current state
     * @return random minimax value
     */
    @Override
    public int eval(State state) {
        ArrayList<Integer> random = new ArrayList<>();
        random.add(1);
        random.add(-1);
        Collections.shuffle(random);
        return random.get(0);
    }
}
