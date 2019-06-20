/**
 * Name: Hao Li     NSID: hal356    Student#: 11153054  CMPT 317 Assignment 2
 */
public class Search {

/**
 * this is the question 3, this is the hill climbing search algorithm,
 * this function will start with random state, then create a best neighbour of the initial state
 * compare best neighbour and the best guess state until the best guess is the bigger one, then return best guess
 * @param problem the entered problem
 * @return the largest best guess
 * @throws CloneNotSupportedException exception for clone
 */
    State hill_climbing(Problem problem) throws CloneNotSupportedException {
        return hill_climbing_search(problem,problem.getTimecut()); //call the main body of the hill climbing search
    }
    
    /**
     * The main body for question 3(hill climbing search algorithm)
     * @param problem the entered problem
     * @param timecut the limit time to cut
     * @return the largest best guess
     * @throws CloneNotSupportedException exception for clone
     */
    private State hill_climbing_search(Problem problem, int timecut) throws CloneNotSupportedException {
        int HC_timecut = timecut;// the limit time from user entered
        State best_guess = (State) problem.RandomState().clone();// create the best guess from the random state
        State guess; // create a guess state
        /*
         compare the best guess and guess until time to cut or returned
         */
        while (HC_timecut !=0){
            guess = problem.BestNeighbour(best_guess,problem.getTarget());// initial guess from the best neighbour from the best guess
            // use the judge function to judge which state is more close to the target
            if (heuristic_Judge(problem.getTarget(),guess.getNow_answer(),best_guess.getNow_answer())){
                best_guess = (State)guess.clone();// if the guess is more close to the target, then best guest clone the guess
            }
            else{
                return best_guess; // if no neighbour more close to the target, return the best guess(closest)
            }
            // limit the time
            HC_timecut --;
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
