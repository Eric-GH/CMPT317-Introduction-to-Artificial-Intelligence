/**
 * Name: Hao Li
 * NSID: hal356
 * Student#: 11153054
 * CMPT 317 A4
 *
 * This is the Help class to about the queens
 *
 */

public class Queen {
    int positionX; // the x position of the queens
    int positionY; // the y position of the queens
    Queen(int positionX, int positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * Get the x position of the queen
     * @return x position
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Get the y position of the queen
     * @return y position
     */
    public int getPositionY() {
        return positionY;
    }


}
