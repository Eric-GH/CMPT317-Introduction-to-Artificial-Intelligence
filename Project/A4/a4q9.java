/**
 * Name: Hao Li
 * NSID: hal356
 * Student#: 11153054
 * CMPT 317 A4
 *
 * This class is a script to execute/test the a4q9
 *
 */

public class a4q9 {
    a4q9(){
        main(null);
    }

    /**
     * The main function will get the cut off numbers for each player,
     * and take the repeated time to repeat each scripts several times
     * and use the win minimax value from previous questions
     * to determine how many win/loss from each script
     * @param args null
     */
    public static void main(String[] args){
        int win,loss;

        System.out.println(" N\tCut-off P1\tCut-off P2\tWin/Loss P1");

        /**
         * This the section for test N-queens size 10, each script runs 10 times
         */
        Problem problem = new Problem(10); // initial the problem class
        Minimax minimax = new Minimax(problem,problem.initial_state); // initial the minimax class
        win = minimax.minimax_search_a4q9(4,4,10,1); // start search by minimax search algorithm
        loss = 10-win; // get the win and loss
        System.out.println(10+"\t\t"+4+"\t\t "+4+"    \t  "+win+"W, "+loss+"L");



        problem = new Problem(10);
        minimax = new Minimax(problem,problem.initial_state);
        win = minimax.minimax_search_a4q9(5,5,10,1);
        loss = 10-win;
        System.out.println(10+"\t\t"+5+"\t\t "+5+"    \t  "+win+"W, "+loss+"L");



        problem = new Problem(10);
        minimax = new Minimax(problem,problem.initial_state);
        win = minimax.minimax_search_a4q9(5,3,10,1);
        loss = 10-win;
        System.out.println(10+"\t\t"+5+"\t\t "+3+"    \t  "+win+"W, "+loss+"L");



        problem = new Problem(10);
        minimax = new Minimax(problem,problem.initial_state);
        win = minimax.minimax_search_a4q9(3,5,10,1);
        loss = 10-win;
        System.out.println(10+"\t\t"+3+"\t\t "+5+"    \t  "+win+"W, "+loss+"L");


        /**
         * This the section for test N-queens size 20, each script runs 10 times
         */
        System.out.println();
        problem = new Problem(20);
        minimax = new Minimax(problem,problem.initial_state);
        win = minimax.minimax_search_a4q9(4,4,10,1);
        loss = 10-win;
        System.out.println(20+"\t\t"+4+"\t\t "+4+"    \t  "+win+"W, "+loss+"L");



        problem = new Problem(20);
        minimax = new Minimax(problem,problem.initial_state);
        win = minimax.minimax_search_a4q9(5,5,10,1);
        loss = 10-win;
        System.out.println(20+"\t\t"+5+"\t\t "+5+"    \t  "+win+"W, "+loss+"L");




        problem = new Problem(20);
        minimax = new Minimax(problem,problem.initial_state);
        win = minimax.minimax_search_a4q9(5,3,10,1);
        loss = 10-win;
        System.out.println(20+"\t\t"+5+"\t\t "+3+"    \t  "+win+"W, "+loss+"L");



        problem = new Problem(20);
        minimax = new Minimax(problem,problem.initial_state);
        win = minimax.minimax_search_a4q9(3,5,10,1);
        loss = 10-win;
        System.out.println(20+"\t\t"+3+"\t\t "+5+"    \t  "+win+"W, "+loss+"L");

    }




}
