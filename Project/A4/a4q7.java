/**
 * Name: Hao Li
 * NSID: hal356
 * Student#: 11153054
 * CMPT 317 A4
 *
 * This class is a script to execute/test the a4q7
 *
 */

public class a4q7 {
    a4q7(){
        main(null);
    }

    /**
     * The main function will test the question a4q7
     * which test two variations with minimax search algorithm with alpha-beta pruning
     * with depth-cut off and heuristic evaluation function
     * @param args
     */
    public static void main(String[] args){

        /**
         * This section for test variation 1b
         */
        System.out.println("\n\t\t\t\t\tVariation 1b\n");
        System.out.println(" Size\tMinimax Value\tBest Opening Move\tTime in Seconds");
        for (int i = 1; i < 21; i++){
            Problem problem = new Problem(i);// initial the problem class
            Minimax minimax = new Minimax(problem,problem.initial_state,4);// initial the minimax class
            int minimax_value = minimax.minimax_search_a4q7a();// start search by minimax search algorithm
            int best_open = minimax.best_opening_move; // get the best opening move
            /*
                calculate the time spend for current script
             */
            long start_time = minimax.starting;
            long end_time = minimax.ending;
            double start = (double) start_time;
            double end = (double) end_time;
            double time = end - start;
            double finaltime = time/1000000000;

            System.out.println("   "+i+"\t\t    "+minimax_value+"\t\t('X', "+best_open+")  \t"+finaltime);
        }

        System.out.println();


        /**
         * This section for test variation 1b
         */
        System.out.println("\n\t\t\t\t\tVariation 1b\n");
        System.out.println(" Size\tMinimax Value\tBest Opening Move\tTime in Seconds");
        for (int i = 1; i < 21; i++){
            Problem problem = new Problem(i);
            Minimax minimax = new Minimax(problem,problem.initial_state,4);
            int minimax_value = minimax.minimax_search_a4q7b();
            int best_open = minimax.best_opening_move;
            long start_time = minimax.starting;
            long end_time = minimax.ending;
            double start = (double) start_time;
            double end = (double) end_time;
            double time = end - start;
            double finaltime = time/1000000000;

            System.out.println("   "+i+"\t\t    "+minimax_value+"\t\t('X', "+best_open+")  \t"+finaltime);
        }
    }
}
