/**
 *Name: Hao Li     NSID: hal356    Student#: 11153054  CMPT 317 Assignment 2
 *
 * This a a2q6_PairNum class, is class create a new variable type, which contained pair of operator and number.
 */


import java.util.Objects;

public class a2q6_PairNum {
    int operator;
    private int number;
    a2q6_PairNum(int op, int num){
        number = num;
        operator = op;
    }


    int getOperator() {
        return operator;
    }

    int getNumber() {
        return number;
    }

    void setOperator(int operator) {
        this.operator = operator;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        a2q6_PairNum pairNum = (a2q6_PairNum) o;
        return operator == pairNum.operator &&
                number == pairNum.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, number);
    }

    /*
        This is help function to help the print of the pairs
     */
    String getOperator(int number){
        if (number == 1){
            return "MUL";
        }
        if (number == 2){
            return "SUB";
        }
        if (number == 0){
            return "ADD";
        }
        if (number == 3){
            return "DIV";
        }
        else {
            return "NOP";
        }

    }

    @Override
    public String toString() {
        return " "+getOperator(this.operator)+ " " + this.number;
    }
}
