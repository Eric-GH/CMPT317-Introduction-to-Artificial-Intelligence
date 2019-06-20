/**
 * Name: Hao Li     NSID: hal356    Student#: 11153054  CMPT 317 Assignment 2
 *
 *
 * This is the main file to control all the search algorithms to functional
 * Basically, this file give user some usage to help user run the function
 * the file will check all the stuffs that user entered, make sure the date is correct
 * then we use the option, which user entered, to run the search algorithm
 * we also calculate the average time use for each script, and the total RMSE from the algorithm
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class a2q6_main {

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {

        File file = new File("a2q6_data2.txt");
        //if the file name is valid, then enter to scanner
        Scanner scanner = new Scanner(file);



        //this is counter to count the number of script the file has.
        double script_count = 0;

        // This is limit time to cut
        int timecut=1000;

        // this is the error we count from each script for each search algorithm
        double guess_error=0;
        double search_error=0;
        double climbing_error=0;
        double restart_error=0;
        double stochastic_error=0;

        // this is the time spend we record from each algorithm
        long guess_time=0;
        long search_time = 0;
        long climbing_time = 0;
        long restart_time = 0;
        long stochastic_time = 0;




        // this is the total start time by ms
        long start_time = System.currentTimeMillis();



        // the a2q6Search stated, we create new problem and a2q6Search
        a2q6_Problem problem;
        a2q6_Search a2q6Search = new a2q6_Search();

        /*
            The while loop will keep the a2q6Search running until end of line of the script
         */
        while (scanner.hasNext()){

            // the temp state to help print the final answer after a2q6Search
            a2q6_State temp;
            // initial the problem
            problem = new a2q6_Problem(scanner,timecut);

            // print the target for each script
            System.out.println("The Target for the script is: "+problem.getTarget());


            /*
                the under codes are calculate the average time spend for each search algorithm
                and calculate the errors for each search algorithm
                and print the best answer they find for each algorithm for each script
             */
            long start_time_guess = System.currentTimeMillis();
            temp = a2q6Search.random_guessing(problem);
            long end_time_guess = System.currentTimeMillis();
            guess_time = guess_time + (end_time_guess-start_time_guess);
            System.out.println("The best Answer for Random Guessing is: "+ temp.getNow_answer());
            guess_error = guess_error + Math.pow((problem.getTarget() - temp.getNow_answer())/problem.getTarget(),2);



            long start_time_search = System.currentTimeMillis();
            temp = a2q6Search.random_search(problem);
            long end_time_search = System.currentTimeMillis();
            search_time = search_time + (end_time_search-start_time_search);
            System.out.println("The best Answer for Random Search is: "+ temp.getNow_answer());
            search_error = search_error + Math.pow((problem.getTarget() - temp.getNow_answer())/problem.getTarget(),2);

            long start_time_climbing = System.currentTimeMillis();
            temp = a2q6Search.hill_climbing(problem);
            long end_time_climbing = System.currentTimeMillis();
            climbing_time = climbing_time + (end_time_climbing-start_time_climbing);
            System.out.println("The best Answer for hill climbing Search is: "+ temp.getNow_answer());
            climbing_error = climbing_error + Math.pow((problem.getTarget() - temp.getNow_answer())/problem.getTarget(),2);


            long start_time_restart = System.currentTimeMillis();
            temp = a2q6Search.Random_Restart_Hill_Climbing_search(problem);
            long end_time_restart = System.currentTimeMillis();
            restart_time = restart_time + (end_time_restart - start_time_restart);
            System.out.println("The best Answer for Random restart hill climbing Search is: "+ temp.getNow_answer());
            restart_error = restart_error + Math.pow((problem.getTarget() - temp.getNow_answer())/problem.getTarget(),2);

            long start_time_stochastic = System.currentTimeMillis();
            temp = a2q6Search.Stochastic_Hill_Climbing_search(problem);
            long end_time_stochastic = System.currentTimeMillis();
            stochastic_time = stochastic_time + (end_time_stochastic - start_time_stochastic);
            System.out.println("The best Answer for Stochastic hill climbing Search is: "+ temp.getNow_answer());
            stochastic_error = stochastic_error + Math.pow((problem.getTarget() - temp.getNow_answer())/problem.getTarget(),2);



            System.out.println();
            System.out.println();
        // number of script
        script_count++;
        }

        //close the scanner.
        scanner.close();

        // this is the end of the a2q6Search time
        long end_time = System.currentTimeMillis();
        System.out.println();
        /*
            Calculate the average time use from each script and print it out
         */
        System.out.println("The total average time for all search algorithm for each equation is: "+((end_time-start_time)/script_count)+"ms");




        /*
            Calculate the RMSE and average time spend for each algorithm and print it out
         */
        System.out.print("RMSE For Random Guessing is: "+Math.sqrt((guess_error/script_count))+"\t");
        System.out.println("The average time for each equation is: "+(guess_time/script_count)+"ms");


        System.out.print("RMSE For Random Search is: "+Math.sqrt((search_error/script_count))+"\t");
        System.out.println("The average time for each equation is: "+(search_time/script_count)+"ms");



        System.out.print("RMSE For Hill Climbing Search is: "+Math.sqrt((climbing_error/script_count))+"\t");
        System.out.println("The average time for each equation is: "+(climbing_time/script_count)+"ms");


        System.out.print("RMSE For Random Restart Hill Climbing Search is: "+Math.sqrt((restart_error/script_count))+"\t");
        System.out.println("The average time for each equation is: "+(restart_time/script_count)+"ms");


        System.out.print("RMSE For Stochastic Hill Climbing Search is: "+Math.sqrt((stochastic_error/script_count))+"\t");
        System.out.println("The average time for each equation is: "+(stochastic_time/script_count)+"ms");
        System.out.println();
        System.out.println();
    }

}
