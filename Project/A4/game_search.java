/**
 * Name: Hao Li
 * NSID: hal356
 * Student#: 11153054
 * CMPT 317 A4
 *
 * This is the interface described requirement of the methods
 * Specifically, this interface gives two variations 1a/1b to return different utility.
 *
 */

import java.util.ArrayList;

public interface game_search {

    boolean is_terminal(State state); //returns a Boolean, True if the given state has no more legal moves

    int utility_For_Va(State state); //returns a numeric value. The state must be a terminal state

    int utility_For_Vb(State state); //returns a numeric value. The state must be a terminal state

    ArrayList<Queen> actions(State state); // returns a list of actions (i.e., moves) that are legal in the given state.
                                            // The function needs to examine the state to determine whose turn it is to move
    State result(State state, Queen action); //returns a new Problem state which is the result of taking the given action in the given state.

    boolean cutoff_test(State state, int depth); // Returns a Boolean, True if the search should be terminated at this state and depth.

    int eval(State state); // returns an estimate of the Minimax value of the given state.

    //optional
    boolean is_maxs_turn(State state); //Returns a Boolean value, True if it’s Player1’s turn(Max) in the given state

    boolean is_mins_turn(State state); //Returns a Boolean value, True if it’s Player2’s turn(Min) in the given state


}
