/**
 * Name: Hao Li
 * NSID: hal356
 * Student#: 11153054
 * CMPT 317 A4
 *
 * This class is a script to execute/test All the previous questions
 * For question 10, all the requirement files are included
 * The files for question 10 is included:
 * Queens.java
 * State.java
 * Problem.java
 * Minimax.java
 * game_search.java
 *
 */
public class a4q10 {
    /**
     * This main function will take the option from user and run the exactly the question the user want
     * @param args
     */
    public static void main(String[] args){
        String num = args[0];
        switch (num){
            case "1" :
                a4q1 a4q1 = new a4q1(); // run the question a4q1
                break;
            case "2" :
                a4q2 a4q2 = new a4q2();// run the question a4q2
                break;
            case "3" :
                a4q3 a4q3 = new a4q3();// run the question a4q3
                break;
            case "4" :
                a4q4 a4q4 = new a4q4();// run the question a4q4
                break;
            case "5" :
                a4q5 a4q5 = new a4q5();// run the question a4q5
                break;
            case "6" :
                a4q6 a4q6 = new a4q6();// run the question a4q6
                break;
            case "7" :
                a4q7 a4q7 = new a4q7();// run the question a4q7
                break;
            case "9" :
                a4q9 a4q9 = new a4q9();// run the question a4q9
                break;
            default :
                System.out.println("Wrong Option. The Option should be 1 to 9 except 8. Example: java a4q10 1");
        }

    }
}
