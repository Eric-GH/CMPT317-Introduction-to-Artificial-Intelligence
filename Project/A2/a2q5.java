/**
 * Name: Hao Li     NSID: hal356    Student#: 11153054  CMPT 317 Assignment 2
 */
public class Search {
    /**
     * This is question 5 for stochastic hill climbing search algorithm
     * this function most same as hill climbing search algorithm, but it not always create the best neighbour to compare
     * it will create neighbour which is better than entered state
     *  @param problem the entered problem
     *  @return the largest best guess state
     * @throws CloneNotSupportedException exception for clone
     */
    State Stochastic_Hill_Climbing_search (Problem problem) throws CloneNotSupportedException {
        int SH_timecut = problem.getTimecut();// the limit time from user entered
        State best_guess = (State) problem.RandomState().clone();// create the best guess from the random state
        State guess; // create a guess state
        /*
         compare the best guess and guess until time to cut
         */
        while (SH_timecut !=0){
            guess = problem.RandomBetterNeighbour(best_guess,problem.getTarget()); // initial guess from neighbour which is better than current state
            // use the judge function to judge which state is more close to the target
            if (heuristic_Judge(problem.getTarget(),guess.getNow_answer(),best_guess.getNow_answer())){
                best_guess = (State)guess.clone();// if the guess is more close to the target, then best guest clone the guess
            }
            // limit the time
            SH_timecut --;
        }
        return best_guess;//return the best guess(closest)
    }
    
    /**
     * This is a function to check which is more close to target between guess and best guess
     * @param target the script's target
     * @param firstNum the guess's answer
     * @param secondNum the best guess's answer
     * @return true if the guess is more close to target, false if other wish.
     */
    private boolean heuristic_Judge(double target, double firstNum, double secondNum){
        return ((Math.abs(target-firstNum))< (Math.abs(target - secondNum)));
    }
}
