/**
 * Name: Hao Li
 * NSID: hal356
 * Student#: 11153054
 * CMPT 317 A4
 *
 * This is Minimax Search Algorithm code base, contained all the minimax search algorithm for question 1 to 9
 * To make the question and the algorithm clear, I separated each search algorithm for question 1 to 9
 * So there are 9 sections of the minimax search algorithm for each 1 to 9 questions except question 8
 *
 * I know it seems a lot, and many duplicate functions. But I just want to make each question clear
 * to help markers organize each question in my assignment easier. I apologize if I make things worse or messier.
 *
 * Some functions will be the similar with previous question's functions, I just labeled
 * one time of each function.
 *
 */




import java.util.ArrayList;

class Minimax {

    game_search game; // the game interface
    final int ifny = Integer.MAX_VALUE; // a variable contained infinite value
    int best_opening_move; // the best opening move
    State state; // the state of the game
    int depth; // the required depthe
    long starting; // the start time of current game
    long ending; // the end time of current game

    /**
     * This construction for question 1 to 4
     * those question doesn't need the depth-cut off
     * @param game current game
     * @param state current state
     */
    Minimax(Problem game, State state){
        this.game = game;
        this.state = state;
    }

    /**
     * This construction for question 5 to 10
     * those question need the depth-cut off parameter
     * @param game current game
     * @param state current state
     */
    Minimax(Problem game, State state,int depth){
        this.game = game;
        this.state = state;
        this.depth = depth;
    }




/**   MiniMax Search Algorithm For Q1 Start Here   */


    /**
     * Minimax Search Algorithm to find the Minimax Value
     * and the best opening move
     * @return The Minimax Value
     */
    int minimax_search_a4q1(){
        starting = System.nanoTime();// start time count
        int minimax_value; // initial minimax value
        // start the search
        while(!game.is_terminal(state)){
            State game_state = state.copy();// copy current state
            Queen current_move = Minimax_Decision_Q1va(game_state);// find the current move for current player

            // if current move is null, means end of the game
            if (current_move == null){
                minimax_value = game.utility_For_Va(state);// get the utility
                best_opening_move = state.queen[0].getPositionX(); //get the best opening move
                ending = System.nanoTime(); // end the time count
                return minimax_value;
            }
            // set up the move of current player
            for (int q=0;q<state.queen.length;q++){
                if (state.queen[q]==null){
                    state.queen[q] = new Queen(current_move.getPositionX(),current_move.getPositionY());
                    break;
                }
            }

            // after set the move, go next player
            if (state.turn ==1){
                state.turn =2;
            }
            else {
                state.turn = 1;
            }
        }

        minimax_value = game.utility_For_Va(state);// get the utility
        best_opening_move = state.queen[0].getPositionX();//get the best opening move
        ending = System.nanoTime();// end the time count
        return minimax_value;
    }


    /**
     * This is the max value search algorithm for variation 1a
     * @param state current state
     * @return the max value for current state
     */
    int Max_Value_va(State state){
        int best; // the best max value
        // determine if the game is ended or not
        if (game.is_terminal(state)){
            best = game.utility_For_Va(state);
        }
        else {
            // if the game still continue, then iterate every available move
            best = -ifny;
            ArrayList<Queen> actions = game.actions(state);
            if (actions.size()==0){
                best = game.utility_For_Va(state);
                return best;
            }
            //iterate every available move
            for (Queen act : actions) {
                int val = Min_Value_va(game.result(state,act));
                if (val > best){
                    best = val;
                }
            }
        }
        return best;
    }


    /**
     * This is the min value search algorithm for variation 1a
     * @param state current state
     * @return the min value for current state
     */
    int Min_Value_va(State state){
        // the best max value
        int best;
        // determine if the game is ended or not
        if (game.is_terminal(state)){
            best = game.utility_For_Va(state);
        }
        else {
            // if the game still continue, then iterate every available move
            best = ifny;
            ArrayList<Queen> actions = game.actions(state);
            if (actions.size()==0){
                best = game.utility_For_Va(state);
                return best;
            }
            //iterate every available move
            for (Queen act : game.actions(state)) {
                int val = Max_Value_va(game.result(state,act));
                if (val < best){
                    best = val;
                }
            }
        }
        return best;
    }

    /**
     * This is decision search algorithm
     * to find the best move decision for each player
     * and find the minimax value for each player
     * @param state current state
     * @return the best move and minimax value for each player
     */
    Queen Minimax_Decision_Q1va(State state){
        Queen best_action = null;// initial best action
        int best; // inital best value
        /*
            iterate max value search algorithm for player 1
         */
        if (game.is_maxs_turn(state)){
            best = -ifny;
            for (Queen act : game.actions(state)) {
                int val = Min_Value_va(game.result(state,act));
                if (val > best){
                    best = val;
                    best_action = act;
                }
            }
        }
        /*
            iterate min value search algorithm for player 2
         */
        else {
            best = ifny;
            for (Queen act : game.actions(state)) {
                int val = Max_Value_va(game.result(state,act));
                if (val < best){
                    best = val;
                    best_action = act;
                }
            }
        }

        return best_action;
    }

/************************/











/**  MiniMax Search Algorithm For Q2 Start Here   */
    /**
     * Minimax Search Algorithm to find the Minimax Value and best opening move
     * @return The Minimax Value
     */
    int minimax_search_a4q2(){
        starting = System.nanoTime();
        int minimax_value;
        // start the search
        while(!game.is_terminal(state)){
            State game_state = state.copy();// copy current state
            Queen current_move = Minimax_Decision__Q2vb(game_state);// find the current move for current player

            // if current move is null, means end of the game
            if (current_move == null){
                minimax_value = game.utility_For_Vb(state);
                best_opening_move = state.queen[0].getPositionX();
                ending = System.nanoTime();
                return minimax_value;
            }
            // set up the move of current player
            for (int q=0;q<state.queen.length;q++){
                if (state.queen[q]==null){
                    state.queen[q] = new Queen(current_move.getPositionX(),current_move.getPositionY());
                    break;
                }
            }
            // after set the move, go next player
            if (state.turn ==1){
                state.turn =2;
            }
            else {
                state.turn = 1;
            }
        }

        minimax_value = game.utility_For_Vb(state);
        best_opening_move = state.queen[0].getPositionX();
        ending = System.nanoTime();
        return minimax_value;
    }

    /**
     * This is the max value search algorithm for variation 1b
     * @param state current state
     * @return the max value for current state
     */
    int Max_Value_vb(State state){
        int best;
        if (game.is_terminal(state)){
            best = game.utility_For_Vb(state);
        }
        else {
            best = -ifny;
            ArrayList<Queen> actions = game.actions(state);
            if (actions.size()==0){
                best = game.utility_For_Vb(state);
                return best;
            }
            for (Queen act : actions) {
                int val = Min_Value_vb(game.result(state,act));
                if (val > best){
                    best = val;
                }
            }
        }
        return best;
    }


    /**
     * This is the min value search algorithm for variation 1b
     * @param state current state
     * @return the min value for current state
     */
    int Min_Value_vb(State state){

        int best;
        if (game.is_terminal(state)){
            best = game.utility_For_Vb(state);
        }
        else {
            best = ifny;
            ArrayList<Queen> actions = game.actions(state);
            if (actions.size()==0){
                best = game.utility_For_Vb(state);
                return best;
            }
            for (Queen act : game.actions(state)) {
                int val = Max_Value_vb(game.result(state,act));
                if (val < best){
                    best = val;
                }
            }
        }
        return best;
    }


    /**
     * This is decision search algorithm
     * to find the best move decision for each player
     * and find the minimax value for each player
     * @param state current state
     * @return the best move and minimax value for each player
     */
    Queen Minimax_Decision__Q2vb(State state){
        Queen best_action = null;
        int best;
        if (game.is_maxs_turn(state)){
            best = -ifny;
            for (Queen act : game.actions(state)) {
                int val = Min_Value_vb(game.result(state,act));
                if (val > best){
                    best = val;
                    best_action = act;
                }
            }
        }
        else {
            best = ifny;
            for (Queen act : game.actions(state)) {
                int val = Max_Value_vb(game.result(state,act));
                if (val < best){
                    best = val;
                    best_action = act;
                }
            }
        }

        return best_action;
    }

/************************/




















/**   MiniMax Search Algorithm For Q3 Start Here   */
    /**
     * Minimax Search Algorithm to find the Minimax Value and best opening move
     * @return The Minimax Value
     */
    int minimax_search_a4q3(){
        starting = System.nanoTime();
        int minimax_value;
        // start the search
        while(!game.is_terminal(state)){
            State game_state = state.copy();// copy current state
            Queen current_move = Minimax_Decision_Q3va(game_state);// find the current move for current player

            // if current move is null, means end of the game
            if (current_move == null){
                minimax_value = game.utility_For_Va(state);
                best_opening_move = state.queen[0].getPositionX();
                ending = System.nanoTime();
                return minimax_value;
            }
            // set up the move of current player
            for (int q=0;q<state.queen.length;q++){
                if (state.queen[q]==null){
                    state.queen[q] = new Queen(current_move.getPositionX(),current_move.getPositionY());
                    break;
                }
            }

            // after set the move, go next player
            if (state.turn ==1){
                state.turn =2;
            }
            else {
                state.turn = 1;
            }
        }

        minimax_value = game.utility_For_Va(state);
        best_opening_move = state.queen[0].getPositionX();
        ending = System.nanoTime();
        return minimax_value;
    }


    /**
     * This is the max value search algorithm with alpha-beta pruning for variation 1a
     * @param state current state
     * @return the max value for current state
     */
    int Max_Value_Q3va(State state, int maxs_best, int mins_best){

        int best;// the best max value
        // determine the game need to end or not
        if (game.is_terminal(state)){
            best = game.utility_For_Va(state);
        }
        else {
            // if the game can continue
            best = -ifny;
            // get all available moves for current state
            ArrayList<Queen> actions = game.actions(state);
            // determine if there no available moves then return
            if (actions.size()==0){
                best = game.utility_For_Va(state);
                return best;
            }
            // iterate all available moves for current state
            for (Queen act : actions) {
                State res = game.result(state,act);
                int val = Min_Value_Q3va(res,maxs_best,mins_best);
                if (val > best){
                    best = val;
                }
                // determine if current best already bigger than min best
                // then do the alpha-beta cut
                if (best >= mins_best){
                    return best;
                }
                maxs_best = Math.max(maxs_best,best);
            }
        }
        return best;
    }


    /**
     * This is the min value search algorithm with alpha-beta pruning for variation 1a
     * @param state current state
     * @return the min value for current state
     */
    int Min_Value_Q3va(State state, int maxs_best, int mins_best){
        int best;// the best max value
        // determine the game need to end or not
        if (game.is_terminal(state)){
            best = game.utility_For_Va(state);
        }
        else {
            // if the game can continue
            best = ifny;
            // get all available moves for current state
            ArrayList<Queen> actions = game.actions(state);
            // determine if there no available moves then return
            if (actions.size()==0){
                best = game.utility_For_Va(state);
                return best;
            }
            // iterate all available moves for current state
            for (Queen act : game.actions(state)) {

                State res = game.result(state,act);
                int val = Max_Value_Q3va(res,maxs_best,mins_best);
                if (val < best){
                    best = val;
                }
                // determine if current best already bigger than min best
                // then do the alpha-beta cut
                if (best <= maxs_best){
                    return best;
                }
                mins_best = Math.min(mins_best,best);
            }
        }
        return best;
    }

    /**
     * This algorithm will get the best move and minimax value for player 1
     * @param state current state
     * @return best move and minimax value for player 1
     */
    Queen max_decision_va(State state){
        int maxs_best = -ifny; // initial max best value
        int mins_best = ifny; // initial min best value
        int best = -ifny; // initial best value
        // get all available moves for current state
        ArrayList<Queen> actions = game.actions(state);
        Queen best_action = null;
        // iterate all the available move for current state
        for (Queen act :actions) {
            State res = game.result(state,act);
            int val = Min_Value_Q3va(res,maxs_best,mins_best);
            if (val > best){
                best = val;
                best_action = act;
            }
            maxs_best = Math.max(maxs_best,best);
        }

        return best_action;
    }


    /**
     * This algorithm will get the best move and minimax value for player 2
     * @param state current state
     * @return best move and minimax value for player 2
     */
    Queen min_decision_va(State state){
        int maxs_best = -ifny;// initial max best value
        int mins_best = ifny; // initial min best value
        int best = ifny;// initial best value
        // get all available moves for current state
        ArrayList<Queen> actions = game.actions(state);
        Queen best_action = null;
        // iterate all the available move for current state
        for (Queen act :actions) {
            State res = game.result(state,act);
            int val = Max_Value_Q3va(res,maxs_best,mins_best);
            if (val < best){
                best = val;
                best_action = act;
            }
            mins_best = Math.min(mins_best,best);
        }


        return best_action;
    }


    /**
     * this is a construction for each player's move
     * @param state current state
     * @return the best decision for each player
     */
    Queen Minimax_Decision_Q3va(State state){
        if (state.turn==1){
            return max_decision_va(state);
        }
        else {
            return min_decision_va(state);
        }
    }

/************************/













/** MiniMax Search Algorithm For Q4 Start Here   */

    /**
     * Minimax Search Algorithm to find the Minimax Value and best opening move
     * @return The Minimax Value
     */
    int minimax_search_a4q4(){
        starting = System.nanoTime();
        int minimax_value;
        // start the search
        while(!game.is_terminal(state)){
            State game_state = state.copy();// copy current state
            Queen current_move = Minimax_Decision_Q4vb(game_state);// find the current move for current player

            // if current move is null, means end of the game
            if (current_move == null){
                minimax_value = game.utility_For_Vb(state);
                best_opening_move = state.queen[0].getPositionX();
                ending = System.nanoTime();
                return minimax_value;
            }
            // set up the move of current player
            for (int q=0;q<state.queen.length;q++){
                if (state.queen[q]==null){
                    state.queen[q] = new Queen(current_move.getPositionX(),current_move.getPositionY());
                    break;
                }
            }

            // after set the move, go next player
            if (state.turn ==1){
                state.turn =2;
            }
            else {
                state.turn = 1;
            }
        }

        minimax_value = game.utility_For_Vb(state);
        best_opening_move = state.queen[0].getPositionX();
        ending = System.nanoTime();
        return minimax_value;
    }



    /**
     * This is the max value search algorithm with alpha-beta pruning for variation 1a
     * @param state current state
     * @return the max value for current state
     */
    int Max_Value_Q4vb(State state, int maxs_best, int mins_best){
        int best;
        if (game.is_terminal(state)){
            best = game.utility_For_Vb(state);
        }
        else {
            best = -ifny;
            ArrayList<Queen> actions = game.actions(state);
            if (actions.size()==0){
                best = game.utility_For_Vb(state);
                return best;
            }
            for (Queen act : actions) {
                State res = game.result(state,act);
                int val = Min_Value_Q4vb(res,maxs_best,mins_best);
                if (val > best){
                    best = val;
                }
                if (best >= mins_best){
                    return best;
                }
                maxs_best = Math.max(maxs_best,best);
            }
        }
        return best;
    }

    /**
     * This is the min value search algorithm with alpha-beta pruning for variation 1a
     * @param state current state
     * @return the min value for current state
     */
    int Min_Value_Q4vb(State state, int maxs_best, int mins_best){
        int best;
        if (game.is_terminal(state)){
            best = game.utility_For_Vb(state);
        }
        else {
            best = ifny;
            ArrayList<Queen> actions = game.actions(state);
            if (actions.size()==0){
                best = game.utility_For_Vb(state);
                return best;
            }
            for (Queen act : game.actions(state)) {

                State res = game.result(state,act);
                int val = Max_Value_Q4vb(res,maxs_best,mins_best);
                if (val < best){
                    best = val;
                }
                if (best <= maxs_best){
                    return best;
                }
                mins_best = Math.min(mins_best,best);
            }
        }
        return best;
    }



    /**
     * This algorithm will get the best move and minimax value for player 1
     * @param state current state
     * @return best move and minimax value for player 1
     */
    Queen max_decision_vb(State state){
        int maxs_best = -ifny;
        int mins_best = ifny;
        int best = -ifny;
        ArrayList<Queen> actions = game.actions(state);
        Queen best_action = null;
        for (Queen act :actions) {
            State res = game.result(state,act);
            int val = Min_Value_Q4vb(res,maxs_best,mins_best);
            if (val > best){
                best = val;
                best_action = act;
            }
            maxs_best = Math.max(maxs_best,best);
        }


        return best_action;
    }



    /**
     * This algorithm will get the best move and minimax value for player 2
     * @param state current state
     * @return best move and minimax value for player 2
     */
    Queen min_decision_vb(State state){
        int maxs_best = -ifny;
        int mins_best = ifny;
        int best = ifny;
        ArrayList<Queen> actions = game.actions(state);
        Queen best_action = null;
        for (Queen act :actions) {
            State res = game.result(state,act);
            int val = Max_Value_Q4vb(res,maxs_best,mins_best);
            if (val < best){
                best = val;
                best_action = act;
            }
            mins_best = Math.min(mins_best,best);
        }


        return best_action;
    }

    /**
     * this is a construction for each player's move
     * @param state current state
     * @return the best decision for each player
     */
    Queen Minimax_Decision_Q4vb(State state){
        if (state.turn==1){
            return max_decision_vb(state);
        }
        else {
            return min_decision_vb(state);
        }
    }
/************************/











/**   MiniMax Search Algorithm For Q5 Start Here   */

    /**
     * Minimax Search Algorithm to find the Minimax Value and best opening move
     * @return The Minimax Value
     */
    int minimax_search_a4q5(){
        starting = System.nanoTime();
        int minimax_value;
        // start the search
        while(!game.is_terminal(state)){

            state.deep=1; // reset the depth for current state

            State game_state = state.copy();// copy current state
            Queen current_move = Minimax_Decision_Q5va(game_state);// find the current move for current player

            // if current move is null, means end of the game
            if (current_move == null){
                minimax_value = game.utility_For_Va(state);
                best_opening_move = state.queen[0].getPositionX();
                ending = System.nanoTime();
                return minimax_value;
            }
            // set up the move of current player
            for (int q=0;q<state.queen.length;q++){
                if (state.queen[q]==null){
                    state.queen[q] = new Queen(current_move.getPositionX(),current_move.getPositionY());
                    break;
                }
            }

            // after set the move, go next player
            if (state.turn ==1){
                state.turn =2;
            }
            else {
                state.turn = 1;
            }
        }

        minimax_value = game.utility_For_Va(state);
        best_opening_move = state.queen[0].getPositionX();
        ending = System.nanoTime();
        return minimax_value;
    }

    /**
     * This is the max value search algorithm with depth-cut off and heuristic for variation 1a
     * @param state current state
     * @return the max value for current state
     */
    int Max_Value_q5va(State state){
        int best;
        if (game.is_terminal(state)){
            best = game.utility_For_Va(state);
        }
        else {

            if (game.cutoff_test(state,depth)){
                best = game.eval(state);
                return best;
            }
            best = -ifny;
            ArrayList<Queen> actions = game.actions(state);
            if (actions.size()==0){
                best = game.utility_For_Va(state);
                return best;
            }


            state.deep++;//go in next node, depth increasing


            for (Queen act : actions) {
                int val = Min_Value_q5va(game.result(state,act));
                if (val > best){
                    best = val;
                }
            }
        }
        return best;
    }


    /**
     * This is the min value search algorithm with depth-cut off and heuristic for variation 1a
     * @param state current state
     * @return the min value for current state
     */
    int Min_Value_q5va(State state){
        int best;
        if (game.is_terminal(state)){
            best = game.utility_For_Va(state);
        }
        else {

            if (game.cutoff_test(state,depth)){
                best = game.eval(state);
                return best;
            }

            best = ifny;
            ArrayList<Queen> actions = game.actions(state);
            if (actions.size()==0){
                best = game.utility_For_Va(state);
                return best;
            }


            state.deep++;//go in next node, depth increasing


            for (Queen act : game.actions(state)) {
                int val = Max_Value_q5va(game.result(state,act));
                if (val < best){
                    best = val;
                }
            }
        }
        return best;
    }


    /**
     * This is decision search algorithm
     * to find the best move decision for each player
     * and find the minimax value for each player
     * @param state current state
     * @return the best move and minimax value for each player
     */
    Queen Minimax_Decision_Q5va(State state){
        Queen best_action = null;
        int best;
        if (game.is_maxs_turn(state)){
            best = -ifny;
            for (Queen act : game.actions(state)) {
                int val = Min_Value_q5va(game.result(state,act));
                if (val > best){
                    best = val;
                    best_action = act;
                }
            }
        }
        else {
            best = ifny;
            for (Queen act : game.actions(state)) {
                int val = Max_Value_q5va(game.result(state,act));
                if (val < best){
                    best = val;
                    best_action = act;
                }
            }
        }
        return best_action;
    }


/************************/













/**  MiniMax Search Algorithm For Q6 Start Here   */

    /**
     * Minimax Search Algorithm to find the Minimax Value and best opening move
     * @return The Minimax Value
     */
    int minimax_search_a4q6(){
        starting = System.nanoTime();
        int minimax_value;
        // start the search
        while(!game.is_terminal(state)){
            state.deep=1;// reset the depth for current state
            State game_state = state.copy();// copy current state

            Queen current_move = Minimax_Decision_Q6vb(game_state);// find the current move for current player

            // if current move is null, means end of the game
            if (current_move == null){
                minimax_value = game.utility_For_Vb(state);
                best_opening_move = state.queen[0].getPositionX();
                ending = System.nanoTime();
                return minimax_value;
            }
            // set up the move of current player
            for (int q=0;q<state.queen.length;q++){
                if (state.queen[q]==null){
                    state.queen[q] = new Queen(current_move.getPositionX(),current_move.getPositionY());
                    break;
                }
            }

            // after set the move, go next player
            if (state.turn ==1){
                state.turn =2;
            }
            else {
                state.turn = 1;
            }
        }

        minimax_value = game.utility_For_Vb(state);
        best_opening_move = state.queen[0].getPositionX();
        ending = System.nanoTime();
        return minimax_value;
    }



    /**
     * This is the max value search algorithm with depth-cut off and heuristic for variation 1b
     * @param state current state
     * @return the max value for current state
     */
    int Max_Value_q6vb(State state){
        int best;
        if (game.is_terminal(state)){
            best = game.utility_For_Vb(state);
        }
        else {

            if (game.cutoff_test(state,depth)){
                best = game.eval(state);
                return best;
            }
            best = -ifny;
            ArrayList<Queen> actions = game.actions(state);
            if (actions.size()==0){
                best = game.utility_For_Vb(state);
                return best;
            }


            state.deep++;//go in next node, depth increasing



            for (Queen act : actions) {
                int val = Min_Value_q6vb(game.result(state,act));
                if (val > best){
                    best = val;
                }
            }
        }
        return best;
    }



    /**
     * This is the min value search algorithm with depth-cut off and heuristic for variation 1b
     * @param state current state
     * @return the min value for current state
     */
    int Min_Value_q6vb(State state){
        int best;
        if (game.is_terminal(state)){
            best = game.utility_For_Vb(state);
        }
        else {

            if (game.cutoff_test(state,depth)){
                best = game.eval(state);
                return best;
            }

            best = ifny;
            ArrayList<Queen> actions = game.actions(state);
            if (actions.size()==0){
                best = game.utility_For_Vb(state);
                return best;
            }


            state.deep++;//go in next node, depth increasing



            for (Queen act : game.actions(state)) {
                int val = Max_Value_q6vb(game.result(state,act));
                if (val < best){
                    best = val;
                }
            }
        }
        return best;
    }




    /**
     * This is decision search algorithm
     * to find the best move decision for each player
     * and find the minimax value for each player
     * @param state current state
     * @return the best move and minimax value for each player
     */
    Queen Minimax_Decision_Q6vb(State state){
        Queen best_action = null;
        int best;
        if (game.is_maxs_turn(state)){
            best = -ifny;
            for (Queen act : game.actions(state)) {
                int val = Min_Value_q6vb(game.result(state,act));
                if (val > best){
                    best = val;
                    best_action = act;
                }
            }
        }
        else {
            best = ifny;
            for (Queen act : game.actions(state)) {
                int val = Max_Value_q6vb(game.result(state,act));
                if (val < best){
                    best = val;
                    best_action = act;
                }
            }
        }
        return best_action;
    }

/*************************/















/**   MiniMax Search Algorithm For Q7 Variation 1a Start Here   */

    /**
     * Minimax Search Algorithm to find the Minimax Value and best opening move
     * @return The Minimax Value
     */
    int minimax_search_a4q7a(){
        starting = System.nanoTime();
        int minimax_value;
        // start the search
        while(!game.is_terminal(state)){
            state.deep=1;// reset the depth for current state
            State game_state = state.copy();// copy current state
            Queen current_move = Minimax_Decision_q7va(game_state);// find the current move for current player

            // if current move is null, means end of the game
            if (current_move == null){
                minimax_value = game.utility_For_Va(state);
                best_opening_move = state.queen[0].getPositionX();
                ending = System.nanoTime();
                return minimax_value;
            }
            // set up the move of current player
            for (int q=0;q<state.queen.length;q++){
                if (state.queen[q]==null){
                    state.queen[q] = new Queen(current_move.getPositionX(),current_move.getPositionY());
                    break;
                }
            }

            // after set the move, go next player
            if (state.turn ==1){
                state.turn =2;
            }
            else {
                state.turn = 1;
            }
        }

        minimax_value = game.utility_For_Va(state);
        best_opening_move = state.queen[0].getPositionX();
        ending = System.nanoTime();
        return minimax_value;
    }



    /**
     * This is the max value search algorithm with alpha-beta pruning
     * and depth-cut off and heuristic for variation 1a
     * @param state current state
     * @return the max value for current state
     */
    int Max_Value_q7va(State state, int maxs_best, int mins_best){
        int best;
        if (game.is_terminal(state)){
            best = game.utility_For_Va(state);
        }
        else {
            if (game.cutoff_test(state,depth)){
                best = game.eval(state);
                return best;
            }
            best = -ifny;
            ArrayList<Queen> actions = game.actions(state);
            if (actions.size()==0){
                best = game.utility_For_Va(state);
                return best;
            }


            state.deep++;//go in next node, depth increasing



            for (Queen act : actions) {
                State res = game.result(state,act);
                int val = Min_Value_q7va(res,maxs_best,mins_best);
                if (val > best){
                    best = val;
                }
                if (best >= mins_best){
                    return best;
                }


                maxs_best = Math.max(maxs_best,best);
            }
        }
        return best;
    }


    /**
     * This is the min value search algorithm with alpha-beta pruning
     * and depth-cut off and heuristic for variation 1a
     * @param state current state
     * @return the min value for current state
     */
    int Min_Value_q7va(State state, int maxs_best, int mins_best){
        int best;
        if (game.is_terminal(state)){
            best = game.utility_For_Va(state);
        }
        else {
            if (game.cutoff_test(state,depth)){
                best = game.eval(state);
                return best;
            }
            best = ifny;
            ArrayList<Queen> actions = game.actions(state);
            if (actions.size()==0){
                best = game.utility_For_Va(state);
                return best;
            }


            state.deep++;//go in next node, depth increasing


            for (Queen act : game.actions(state)) {

                State res = game.result(state,act);
                int val = Max_Value_q7va(res,maxs_best,mins_best);
                if (val < best){
                    best = val;
                }
                if (best <= maxs_best){
                    return best;
                }
                mins_best = Math.min(mins_best,best);
            }
        }
        return best;
    }


    /**
     * This algorithm will get the best move and minimax value for player 1
     * @param state current state
     * @return best move and minimax value for player 1
     */
    Queen max_decision_q7va(State state){
        int maxs_best = -ifny;
        int mins_best = ifny;
        int best = -ifny;
        ArrayList<Queen> actions = game.actions(state);
        Queen best_action = null;
        for (Queen act :actions) {
            State res = game.result(state,act);
            int val = Min_Value_q7va(res,maxs_best,mins_best);
            if (val > best){
                best = val;
                best_action = act;
            }
            maxs_best = Math.max(maxs_best,best);
        }


        return best_action;
    }


    /**
     * This algorithm will get the best move and minimax value for player 2
     * @param state current state
     * @return best move and minimax value for player 2
     */
    Queen min_decision_q7va(State state){
        int maxs_best = -ifny;
        int mins_best = ifny;
        int best = ifny;
        ArrayList<Queen> actions = game.actions(state);
        Queen best_action = null;
        for (Queen act :actions) {
            State res = game.result(state,act);
            int val = Max_Value_q7va(res,maxs_best,mins_best);
            if (val < best){
                best = val;
                best_action = act;
            }
            mins_best = Math.min(mins_best,best);
        }


        return best_action;
    }

    /**
     * this is a construction for each player's move
     * @param state current state
     * @return the best decision for each player
     */
    Queen Minimax_Decision_q7va(State state){
        if (state.turn==1){
            return max_decision_q7va(state);
        }
        else {
            return min_decision_q7va(state);
        }
    }
/************************/










/**   MiniMax Search Algorithm For Q7 Variation 1b Start Here   */

    /**
     * Minimax Search Algorithm to find the Minimax Value and best opening move
     * @return The Minimax Value
     */
    int minimax_search_a4q7b(){
        starting = System.nanoTime();
        int minimax_value;
        // start the search
        while(!game.is_terminal(state)){
            state.deep=1;// reset the depth for current state
            State game_state = state.copy();// copy current state
            Queen current_move = Minimax_Decision_Q7vb(game_state);// find the current move for current player

            // if current move is null, means end of the game
            if (current_move == null){
                minimax_value = game.utility_For_Vb(state);
                best_opening_move = state.queen[0].getPositionX();
                ending = System.nanoTime();
                return minimax_value;
            }
            // set up the move of current player
            for (int q=0;q<state.queen.length;q++){
                if (state.queen[q]==null){
                    state.queen[q] = new Queen(current_move.getPositionX(),current_move.getPositionY());
                    break;
                }
            }

            // after set the move, go next player
            if (state.turn ==1){
                state.turn =2;
            }
            else {
                state.turn = 1;
            }
        }

        minimax_value = game.utility_For_Vb(state);
        best_opening_move = state.queen[0].getPositionX();
        ending = System.nanoTime();
        return minimax_value;
    }



    /**
     * This is the max value search algorithm with alpha-beta pruning
     * and depth-cut off and heuristic for variation 1a
     * @param state current state
     * @return the max value for current state
     */
    int Max_Value_q7vb(State state, int maxs_best, int mins_best){
        int best;
        if (game.is_terminal(state)){
            best = game.utility_For_Vb(state);
        }
        else {
            if (game.cutoff_test(state,depth)){
                best = game.eval(state);
                return best;
            }
            best = -ifny;
            ArrayList<Queen> actions = game.actions(state);
            if (actions.size()==0){
                best = game.utility_For_Vb(state);
                return best;
            }
            state.deep++;//go in next node, depth increasing
            for (Queen act : actions) {
                State res = game.result(state,act);
                int val = Min_Value_q7vb(res,maxs_best,mins_best);
                if (val > best){
                    best = val;
                }
                if (best >= mins_best){
                    return best;
                }
                maxs_best = Math.max(maxs_best,best);
            }
        }
        return best;
    }


    /**
     * This is the min value search algorithm with alpha-beta pruning
     * and depth-cut off and heuristic for variation 1a
     * @param state current state
     * @return the min value for current state
     */
    int Min_Value_q7vb(State state, int maxs_best, int mins_best){
        int best;
        if (game.is_terminal(state)){
            best = game.utility_For_Vb(state);
        }
        else {
            if (game.cutoff_test(state,depth)){
                best = game.eval(state);
                return best;
            }
            best = ifny;
            ArrayList<Queen> actions = game.actions(state);
            if (actions.size()==0){
                best = game.utility_For_Vb(state);
                return best;
            }
            state.deep++;//go in next node, depth increasing
            for (Queen act : game.actions(state)) {

                State res = game.result(state,act);
                int val = Max_Value_q7vb(res,maxs_best,mins_best);
                if (val < best){
                    best = val;
                }
                if (best <= maxs_best){
                    return best;
                }
                mins_best = Math.min(mins_best,best);
            }
        }
        return best;
    }



    /**
     * This algorithm will get the best move and minimax value for player 1
     * @param state current state
     * @return best move and minimax value for player 1
     */
    Queen max_decision_q7vb(State state){
        int maxs_best = -ifny;
        int mins_best = ifny;
        int best = -ifny;
        ArrayList<Queen> actions = game.actions(state);
        Queen best_action = null;
        for (Queen act :actions) {
            State res = game.result(state,act);
            int val = Min_Value_q7vb(res,maxs_best,mins_best);
            if (val > best){
                best = val;
                best_action = act;
            }
            maxs_best = Math.max(maxs_best,best);
        }


        return best_action;
    }


    /**
     * This algorithm will get the best move and minimax value for player 2
     * @param state current state
     * @return best move and minimax value for player 2
     */
    Queen min_decision_q7vb(State state){
        int maxs_best = -ifny;
        int mins_best = ifny;
        int best = ifny;
        ArrayList<Queen> actions = game.actions(state);
        Queen best_action = null;
        for (Queen act :actions) {
            State res = game.result(state,act);
            int val = Max_Value_q7vb(res,maxs_best,mins_best);
            if (val < best){
                best = val;
                best_action = act;
            }
            mins_best = Math.min(mins_best,best);
        }


        return best_action;
    }

    /**
     * this is a construction for each player's move
     * @param state current state
     * @return the best decision for each player
     */
    Queen Minimax_Decision_Q7vb(State state){
        if (state.turn==1){
            return max_decision_q7vb(state);
        }
        else {
            return min_decision_q7vb(state);
        }
    }


/************************  END  **********************************  END   **************************************  END  ***********************************/












/**************************************************   MiniMax Search Algorithm For Q9 Start Here   ***********************************************************/

    /**
     * This function will search the minimax value for each player's step
     * with each player's cut-off number.
     * For each script, it will keep execute 10 times, to find more closest answer
     *
     * @param cut_off_p1 the depth of cut off for player 1
     * @param cut_off_p2 the depth of cut off for player 2
     * @param repeated_time the repeat time for each script
     * @param p1winvalue the win value for player1
     * @return the minimax value from the game
     */
    int minimax_search_a4q9(int cut_off_p1, int cut_off_p2, int repeated_time, int p1winvalue){
        int count_win = 0;
        State test_state;
        // repeat game on repeated_time
        while(repeated_time!=0) {
            test_state = state.copy();
            depth = cut_off_p1;// initial depth number for player 1
            int minimax_value;
            // start the search
            while (!game.is_terminal(test_state)) {
                test_state.deep = 1;// reset the depth for current state
                State game_state = test_state.copy();// copy current state
                Queen current_move = Minimax_Decision_q7va(game_state);// find the current move for current player

                // if current move is null, means end of the game
                if (current_move == null) {
                    break;
                }
                // set up the move of current player
                for (int q = 0; q < test_state.queen.length; q++) {
                    if (test_state.queen[q] == null) {
                        test_state.queen[q] = new Queen(current_move.getPositionX(), current_move.getPositionY());
                        break;
                    }
                }

                // after set the move, go next player. And change the depth cut off number for each player
                if (test_state.turn == 1) {
                    depth = cut_off_p2;
                    test_state.turn = 2;
                } else {
                    depth = cut_off_p1;
                    test_state.turn = 1;
                }
            }
            minimax_value = game.utility_For_Va(test_state);
            best_opening_move = test_state.queen[0].getPositionX();
            //return minimax_value;
            if (minimax_value == p1winvalue){
                count_win++;
            }

            repeated_time--;
        }

        return count_win;
    }
/************************  END  **********************************  END   **************************************  END  ***********************************/

}
