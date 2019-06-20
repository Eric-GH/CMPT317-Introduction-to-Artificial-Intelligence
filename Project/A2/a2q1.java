/**
 * Name: Hao Li     NSID: hal356    Student#: 11153054  CMPT 317 Assignment 2
 */


public class Search {
    /**
     * This function is question 1, the random guess will start with random state,
     * keep create random state to find the best one until the time cuted
     * @param problem the entered problem
     * @return the best guess will be returned
     * @throws CloneNotSupportedException exception for clone
     */
    State random_guessing (Problem problem) throws CloneNotSupportedException {
        int RG_timecut = problem.getTimecut();// the limit time from user entered
        State best_guess = (State) problem.RandomState().clone(); // create the best guess from the random state
        State guess; // create a guess state
        
        /*
         compare the best guess and guess until time to cut
         */
        while (RG_timecut!=0){
            guess = problem.RandomState(); // initial guess be the random state
            // use the judge function to judge which state is more close to the target
            if (heuristic_Judge(problem.getTarget(),guess.getNow_answer(),best_guess.getNow_answer())){
                best_guess = (State)guess.clone(); // if the guess is more close to the target, then best guest clone the guess
            }
            // limit the time
            RG_timecut--;
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
