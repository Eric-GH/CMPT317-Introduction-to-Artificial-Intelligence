
/**
 * Name: Hao Li     NSID: hal356    Student#: 11153054  CMPT 317 Assignment 2
 *
 * This class is main search class, all search function in this class
 */



public class a2q6_Search {


    /**
     * This function is question 1, the random guess will start with random state,
     * keep create random state to find the best one until the time cuted
     * @param problem the entered problem
     * @return the best guess will be returned
     * @throws CloneNotSupportedException exception for clone
     */
    a2q6_State random_guessing (a2q6_Problem problem) throws CloneNotSupportedException {
        int RG_timecut = problem.getTimecut();// the limit time from user entered
        a2q6_State best_guess = (a2q6_State) problem.RandomState().clone(); // create the best guess from the random state
        a2q6_State guess; // create a guess state

        /*
           compare the best guess and guess until time to cut
         */
        while (RG_timecut!=0){
            guess = problem.RandomState(); // initial guess be the random state
            // use the judge function to judge which state is more close to the target
            if (heuristic_Judge(problem.getTarget(),guess.getNow_answer(),best_guess.getNow_answer())){
                best_guess = (a2q6_State)guess.clone(); // if the guess is more close to the target, then best guest clone the guess
            }
            // limit the time
            RG_timecut--;
        }
        return best_guess;//return the best guess(closest)
    }


    /**
     * This function is question 2, it will start with random state,
     * then keep create the state's neighbour, until find the best one
     * @param problem the entered problem
     * @return the best guess from the search
     * @throws CloneNotSupportedException exception for clone
     */
    a2q6_State random_search (a2q6_Problem problem) throws CloneNotSupportedException {
        int RS_timecut = problem.getTimecut();// the limit time from user entered
        a2q6_State best_guess = (a2q6_State) problem.RandomState().clone();// create the best guess from the random state
        a2q6_State guess; // create a guess state
        /*
           compare the best guess and guess until time to cut
         */
        while (RS_timecut!=0){
            guess = problem.Neighbours(best_guess); // initial guess from random neighbour of the best guess
            // use the judge function to judge which state is more close to the target
            if (heuristic_Judge(problem.getTarget(),guess.getNow_answer(),best_guess.getNow_answer())){
                best_guess = (a2q6_State)guess.clone();// if the guess is more close to the target, then best guest clone the guess
            }
            // limit the time
            RS_timecut--;
        }
        return best_guess;//return the best guess(closest)
    }


    /**
     * this is the question 3, this is the hill climbing search algorithm,
     * this function will start with random state, then create a best neighbour of the initial state
     * compare best neighbour and the best guess state until the best guess is the bigger one, then return best guess
     * @param problem the entered problem
     * @return the largest best guess
     * @throws CloneNotSupportedException exception for clone
     */
    a2q6_State hill_climbing(a2q6_Problem problem) throws CloneNotSupportedException {
        return hill_climbing_search(problem,problem.getTimecut()); //call the main body of the hill climbing search
    }

    /**
     * The main body for question 3(hill climbing search algorithm)
     * @param problem the entered problem
     * @param timecut the limit time to cut
     * @return the largest best guess
     * @throws CloneNotSupportedException exception for clone
     */
    private a2q6_State hill_climbing_search(a2q6_Problem problem, int timecut) throws CloneNotSupportedException {
        int HC_timecut = timecut;// the limit time from user entered
        a2q6_State best_guess = (a2q6_State) problem.RandomState().clone();// create the best guess from the random state
        a2q6_State guess; // create a guess state
        /*
           compare the best guess and guess until time to cut or returned
         */
        while (HC_timecut !=0){
            guess = problem.BestNeighbour(best_guess,problem.getTarget());// initial guess from the best neighbour from the best guess
            // use the judge function to judge which state is more close to the target
            if (heuristic_Judge(problem.getTarget(),guess.getNow_answer(),best_guess.getNow_answer())){
                best_guess = (a2q6_State)guess.clone();// if the guess is more close to the target, then best guest clone the guess
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
     * This is question 4 for random restart hill climbing search algorithm
     * this function will restart hill climbing with random initial states in several times
     * and return the largest state
     * @param problem the entered problem
     * @return the largest best guess state
     * @throws CloneNotSupportedException exception for clone
     */
    a2q6_State Random_Restart_Hill_Climbing_search (a2q6_Problem problem) throws CloneNotSupportedException {
        int RR_timecut = (problem.getTimecut()/20);
        int sub_hill_time = (problem.getTimecut()/50);
        if (RR_timecut < 1){
            RR_timecut = 1;
        }
        if (sub_hill_time<1){
            sub_hill_time = 1;
        }
        a2q6_State best_guess = (a2q6_State) problem.RandomState().clone();// create the best guess from the random state
        a2q6_State guess; // create a guess state
        while (RR_timecut !=0){
            guess = hill_climbing_search(problem,sub_hill_time); // initial guess from the answer of the hill climbing search algorithm
            // use the judge function to judge which state is more close to the target
            if (heuristic_Judge(problem.getTarget(),guess.getNow_answer(),best_guess.getNow_answer())){
                best_guess = (a2q6_State)guess.clone();// if the guess is more close to the target, then best guest clone the guess
            }
            // limit the time
            RR_timecut --;
        }
        return best_guess;//return the best guess(closest)
    }

    /**
     * This is question 5 for stochastic hill climbing search algorithm
     * this function most same as hill climbing search algorithm, but it not always create the best neighbour to compare
     * it will create neighbour which is better than entered state
     *  @param problem the entered problem
     *  @return the largest best guess state
     * @throws CloneNotSupportedException exception for clone
     */
    a2q6_State Stochastic_Hill_Climbing_search (a2q6_Problem problem) throws CloneNotSupportedException {
        int SH_timecut = problem.getTimecut();// the limit time from user entered
        a2q6_State best_guess = (a2q6_State) problem.RandomState().clone();// create the best guess from the random state
        a2q6_State guess; // create a guess state
        /*
           compare the best guess and guess until time to cut
         */
        while (SH_timecut !=0){
            guess = problem.RandomBetterNeighbour(best_guess,problem.getTarget()); // initial guess from neighbour which is better than current state
            // use the judge function to judge which state is more close to the target
            if (heuristic_Judge(problem.getTarget(),guess.getNow_answer(),best_guess.getNow_answer())){
                best_guess = (a2q6_State)guess.clone();// if the guess is more close to the target, then best guest clone the guess
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
