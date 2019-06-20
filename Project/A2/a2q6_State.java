/**
 *Name: Hao Li     NSID: hal356    Student#: 11153054  CMPT 317 Assignment 2
 *
 * This is a2q6_Problem a2q6_State Class
 * This class has two elements, one is a pair of array list, other is the answer from the pair list
 */

import java.util.ArrayList;

public class a2q6_State implements Cloneable{


    double now_answer;
    ArrayList<a2q6_PairNum> pairList;


    /**
     * a2q6_State
     * @param pairList the list carry the pair of number with operator as elements
     * @param now_answer the answer from each script
     */
    a2q6_State(ArrayList<a2q6_PairNum> pairList, double now_answer){
        this.pairList = pairList;
        this.now_answer = now_answer;
    }


    /*
     Set and Get function for elements in state
     */
    double getNow_answer() {
        return now_answer;
    }

    void setNow_answer(double now_answer) {
        this.now_answer = now_answer;
    }

    ArrayList<a2q6_PairNum> getPairList() {
        return pairList;
    }


    /**
     * Clone
     * @return make sure each state are cloneable
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return (a2q6_State)super.clone();
    }

}
