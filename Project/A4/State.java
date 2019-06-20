/**
 * Name: Hao Li
 * NSID: hal356
 * Student#: 11153054
 * CMPT 317 A4
 *
 * This is the State class
 * State carried most variables that needed for the program
 *
 */

public class State{
    Queen[] board; // the original board for the game
    Queen[] queen; // each queens in the board
    int size; // the size of the board
    boolean legal; // determine whether the current state is still legal to continue.
    int turn; // determine which player's turn
    int deep; // determine the depth of current state

    State(Queen[] board, Queen[] queen, int size, boolean legal, int turn){
        this.board = board;
        this.queen = queen;
        this.size =size;
        this.legal = legal;
        this.turn = turn;
    }
    State(Queen[] board, Queen[] queen, int size, boolean legal, int turn,int deep){
        this.board = board;
        this.queen = queen;
        this.size =size;
        this.legal = legal;
        this.turn = turn;
        this.deep = deep;
    }

    /**
     * Copy current state
     * @return the copy of current state
     */
    public State copy(){
        int temp_size = this.size; // copy size
        int temp_turn = this.turn; // copy turns' of player
        boolean temp_legal = this.legal; // copy the legal flag
        int temp_deep = this.deep; // copy the depth of current state

        /*
            Copy the game board
         */
        Queen[] temp_board = new Queen[temp_size*temp_size];
        for (int b=0;b<this.board.length;b++) {
            int bx = this.board[b].getPositionX();
            int by = this.board[b].getPositionY();
            temp_board[b] = new Queen(bx, by);
        }

        /*
            Copy the queens
         */
        Queen[] temp_queens = new Queen[temp_size];
        for (int i=0;i<this.queen.length;i++){
            if (this.queen[i] == null){
                break;
            }
            int x = this.queen[i].getPositionX();
            int y = this.queen[i].getPositionY();
            temp_queens[i] = new Queen(x,y);
        }


        return new State(temp_board,temp_queens,temp_size,temp_legal,temp_turn,temp_deep);
    }

}
