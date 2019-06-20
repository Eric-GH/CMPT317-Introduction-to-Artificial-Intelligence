/**
 * Name: Hao Li
 * NSID: hal356
 * Student#: 11153054
 * CMPT 317 A4
 *
 * This class is a script to execute/test the a4q3
 *
 */

public class a4q3 {
    a4q3(){
        main(null);
    }
    /**
     * This main function will test the question a4q3
     * which the minimax search algorithm with Alpha-beta pruning
     * for variation 1b
     * @param args null
     */
    public static void main(String[] args){
        Problem problem; // the problem class
        Minimax minimax; // the minimax class
        int minimax_value; // the minimax value from the search algorithm
        int best_open; // the best opening move
        long start_time; // the start time of current script
        long end_time; // the end time of current script
        System.out.println(" Size\tMinimax Value\tBest Opening Move\tTime in Seconds");
        for (int i = 1; i < 11; i++){
            problem = new Problem(i);// initial the problem class
            minimax = new Minimax(problem,problem.initial_state);// initial the minimax class
            minimax_value = minimax.minimax_search_a4q3();// start search by minimax search algorithm
            best_open = minimax.best_opening_move;// get the best opening move

            /*
                calculate the time spend for current script
             */
            start_time = minimax.starting;
            end_time = minimax.ending;
            double start = (double) start_time;
            double end = (double) end_time;
            double time = end - start;
            double finaltime = time/1000000000;

            System.out.println("   "+i+"\t\t    "+minimax_value+"\t\t('X', "+best_open+")  \t"+finaltime);
        }

        System.out.println("\n\t\t\t\tTest larger value");
        System.out.println(" Size\tMinimax Value\tBest Opening Move\tTime in Seconds");
        for (int j=11;j<13; j++){
            problem = new Problem(j);
            minimax = new Minimax(problem,problem.initial_state);
            minimax_value = minimax.minimax_search_a4q3();
            best_open = minimax.best_opening_move;
            start_time = minimax.starting;
            end_time = minimax.ending;
            double start = (double) start_time;
            double end = (double) end_time;
            double time = end - start;
            double finaltime = time/1000000000;

            System.out.println("   "+j+"\t\t    "+minimax_value+"\t\t('X', "+best_open+")  \t"+finaltime);
        }

    }
}
