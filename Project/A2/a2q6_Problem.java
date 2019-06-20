/**
 * Name: Hao Li     NSID: hal356    Student#: 11153054  CMPT 317 Assignment 2
 *
 * This is a2q6_Problem class, this class defined all problem initial and help functions
 * This class with 2 variables, the each line from the file and the cut off time from user
 * This class contained create_initial_list, to create list from the file by each script
 * I will write some details above each function, to make it easily understand
 */



import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class a2q6_Problem {

    private Scanner line; // contained each line from the file
    private ArrayList<Integer> initial_list; // initial list from each script
    private double target; // the target number of each script
    private int timecut; //  number of iterations of the main loop.


    // initialize all elements in problem
    a2q6_Problem(Scanner line, int timecut){
        this.line = line;
        this.timecut = timecut;
        initial_list= create_initial_list();
    }

    // get time of cut
    int getTimecut() {
        return timecut;
    }

    // get target
    double getTarget() {
        return target;
    }

    /**
     * This function will create initial list from each script from files.
     * @return the initial list from each script
     */
    private ArrayList<Integer> create_initial_list(){

        ArrayList<Integer> list;// create list to contain the script
        Scanner scanner = new Scanner(line.nextLine());// scan each element from each line
        /*
         make sure the line is not empty
         */
        if (!scanner.hasNext()){
            System.out.print("Error with empty file");
        }
        // initial the list
        list = new ArrayList<>();
        // initial target first
        this.target = Double.parseDouble(scanner.next());

        // add each elements in each line to the list
        while (scanner.hasNext()) {
            list.add(scanner.nextInt());
        }
        //close the scanner
        scanner.close();

        return list;
    }


    /**
     * This function will create a random neighbour for the enter state
     * @param state the state entered
     * @return neighbour is the neighbour of the entered state
     */
    a2q6_State Neighbours(a2q6_State state){
        // create the range of random number
        //range of operator
        final int Operator_MAX = 4;
        final int Operator_MIN = 0;
        //range of index
        final int Index_MAX = state.getPairList().size()-1;
        final int Index_MIN = 0;

        // create random operator
        final int operator_random = new Random().nextInt((Operator_MAX-Operator_MIN)+1)+Operator_MIN;

        //create random index
        final int Index_random = new Random().nextInt((Index_MAX-Index_MIN)+1)+Index_MIN;

        // create new pair list to change the operator
        ArrayList<a2q6_PairNum> newList = new ArrayList<>();

        // deep copy the original pair of list to the new list
        for (int i = 0;i<state.getPairList().size();i++){
            a2q6_PairNum temp = new a2q6_PairNum(state.getPairList().get(i).getOperator(),state.getPairList().get(i).getNumber());
            newList.add(temp);
        }

        // create the neighbour state, add the new pair list in the state
        a2q6_State neighbour = new a2q6_State(newList,0);

        // change one instruction randomly
        neighbour.getPairList().get(Index_random).setOperator(operator_random);

        // check and make sure the neighbour is different with entered state
        while (neighbour.getPairList().get(Index_random).getOperator() == state.getPairList().get(Index_random).getOperator()){
            //if the neighbour is same as entered state, then random change again until different
            neighbour.getPairList().get(Index_random).setOperator(new Random().nextInt((Operator_MAX-Operator_MIN)+1)+Operator_MIN);

        }

        //calculate the answer from neighbour's list
        neighbour.setNow_answer(machine_exec(neighbour.getPairList()));
        return neighbour;
    }




    /**
     * This function will create a neighbour state which is better than entered state and return it
     * @param state the entered state
     * @param target the target for the script
     * @return the neighbour state
     */
    a2q6_State RandomBetterNeighbour(a2q6_State state, double target){

        ArrayList<a2q6_PairNum> temp_list;// list to contain neighbour's pair list
        ArrayList<Integer> operators = new ArrayList<>();// list to contained all operators

        // add all operator to the list
        for (int o = 0; o<5; o++){
            operators.add(o);
        }

        a2q6_State better_neighbour = new a2q6_State(state.getPairList(),state.getNow_answer());// create a neighbour state

        /*
            First for loop control all the elements in the neighbour stat's pair list iterations
         */
        for (int s=0;s<better_neighbour.getPairList().size();s++){

            temp_list = new ArrayList<>();//initial neighbour state's pair list
            /*
                second for loop to copy all entered state's pair list to the neighbour state's list
            */
            for (int i =0;i<state.getPairList().size(); i++){
                a2q6_PairNum temp_pair = new a2q6_PairNum(state.getPairList().get(i).getOperator(),state.getPairList().get(i).getNumber());
                temp_list.add(temp_pair);
            }

            better_neighbour = new a2q6_State(temp_list,machine_exec(temp_list)); // initial neighbour state


            /*
                third for loop control all the operator iterations
            */
            for (int f=0;f<operators.size();f++){
                better_neighbour.getPairList().get(s).setOperator(f);// change the element's operator
                better_neighbour.setNow_answer(machine_exec(better_neighbour.getPairList()));

                // check which one more close to the target
                if ((Math.abs(target - better_neighbour.getNow_answer())) < (Math.abs(target - state.getNow_answer()))){
                    // find the better one, return the neighbour
                    return better_neighbour;
                }
            }
        }
        // if no neighbour better than entered state, then return the entered state
        return state;
    }






    /**
     * This function will test all neighbours from entered state, and return the best one
     * @param state the entered state
     * @param target the target number
     * @return best_neighbour, which is the largest neighbour from entered state
     */
    a2q6_State BestNeighbour(a2q6_State state, double target){

        ArrayList<a2q6_PairNum> bestPairList = new ArrayList<>();// a list contained best pair of neighbours
        ArrayList<a2q6_PairNum> tempPairList;
        ArrayList<Integer> operators = new ArrayList<>();// a list contained all operators
        // push operators to the list
        for (int o=0;o<5;o++){
            operators.add(o);
        }

        // add the entered state's list to the best pair list
        for (int i =0; i<state.getPairList().size();i++){
            a2q6_PairNum temp_best = new a2q6_PairNum(state.getPairList().get(i).getOperator(),state.getPairList().get(i).getNumber());
            bestPairList.add(temp_best);
        }

        // initial the best neighbour
        a2q6_State best_neighbour = new a2q6_State(bestPairList,0);
        // set the answer for the best neighbour
        best_neighbour.setNow_answer(machine_exec(best_neighbour.getPairList()));


        /*
            the first for loop is control the each elements in pair list one by one
         */
        for (int c=0;c<best_neighbour.getPairList().size();c++){
            tempPairList = new ArrayList<>();
            for (int i =0; i<state.getPairList().size();i++){
                a2q6_PairNum temp = new a2q6_PairNum(state.getPairList().get(i).getOperator(),state.getPairList().get(i).getNumber());
                tempPairList.add(temp);
            }
            // create temp neighbour to test each neighbour
            a2q6_State temp_neighbour = new a2q6_State(tempPairList,0);

            // the second for loop is control the each operators for each elements one by one
            for (Integer operator : operators) {
                // test every neighbour with each operator, find the best one
                temp_neighbour.getPairList().get(c).setOperator(operator);
                temp_neighbour.setNow_answer(machine_exec(temp_neighbour.getPairList()));
                if ((Math.abs(target - temp_neighbour.getNow_answer())) < (Math.abs(target - best_neighbour.getNow_answer()))) {
                    bestPairList = new ArrayList<>();
                    for (int k = 0; k < temp_neighbour.getPairList().size(); k++) {
                        a2q6_PairNum newBest = new a2q6_PairNum(temp_neighbour.getPairList().get(k).getOperator(), temp_neighbour.getPairList().get(k).getNumber());
                        bestPairList.add(newBest);
                    }
                    best_neighbour = new a2q6_State(bestPairList, 0);
                    best_neighbour.setNow_answer(machine_exec(best_neighbour.getPairList()));
                }
            }
        }

        return best_neighbour;
    }
    /**
     * Function RandomState
     * this function can create a random state to start the search
     * @return state with random operator
     */
    a2q6_State RandomState(){
        a2q6_State initial_state;
        int max = 4;
        int min = 0;
        Random random = new Random();
        ArrayList<a2q6_PairNum> temp = new ArrayList<>();
        for (int i = 0; i< initial_list.size(); i++){
            int ranNum = random.nextInt((max - min) + 1) + min;
            temp.add(new a2q6_PairNum(ranNum, initial_list.get(i)));
        }
        initial_state = new a2q6_State(temp,machine_exec(temp));
        return initial_state;
    }

    /**
     * Function machine_exec
     * The search use numbers be the operators, this function can transfer the number to the right instructions and calculate the answer
     * @param list a pair list to transfer the operators, and calculate the total value of the list
     * @return the total value of the list
     */
    private double machine_exec(ArrayList<a2q6_PairNum> list){
        double num =0;
        for (a2q6_PairNum aList : list) {
            if (aList.getOperator() == 0) {
                num = num + aList.getNumber();
            } else if (aList.getOperator() == 1) {
                num = num * aList.getNumber();
            } else if (aList.getOperator() == 2) {
                num = num - aList.getNumber();
            } else if (aList.getOperator() == 3) {
                num = num / aList.getNumber();
            } else {
                num = num;
            }
        }
        return num;
    }



}
