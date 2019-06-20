/**
 * Name: Hao Li     NSID: hal356    Student#: 11153054  CMPT 317 Assignment 2
 */
public class Search {
    /**
     * This is question 4 for random restart hill climbing search algorithm
     * this function will restart hill climbing with random initial states in several times
     * and return the largest state
     * @param problem the entered problem
     * @return the largest best guess state
     * @throws CloneNotSupportedException exception for clone
     */
    State Random_Restart_Hill_Climbing_search (Problem problem) throws CloneNotSupportedException {
        int RR_timecut = (problem.getTimecut()/100);
        int sub_hill_time = (problem.getTimecut()/10);
        if (RR_timecut < 1){
            RR_timecut = 1;
        }
        if (sub_hill_time<1){
            sub_hill_time = 1;
        }
        State best_guess = (State) problem.RandomState().clone();// create the best guess from the random state
        State guess; // create a guess state
        while (RR_timecut !=0){
            guess = hill_climbing_search(problem,sub_hill_time); // initial guess from the answer of the hill climbing search algorithm
            // use the judge function to judge which state is more close to the target
            if (heuristic_Judge(problem.getTarget(),guess.getNow_answer(),best_guess.getNow_answer())){
                best_guess = (State)guess.clone();// if the guess is more close to the target, then best guest clone the guess
            }
            // limit the time
            RR_timecut --;
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
