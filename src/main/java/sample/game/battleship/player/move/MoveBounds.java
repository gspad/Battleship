package sample.game.battleship.player.move;

/**
 * MoveBounds is used to determine if the player has made a valid move (a move within the bounds provided).
 */
public class MoveBounds
{
    private char rowsLowerBound;
    private char rowsUpperBound;
    private int colsLowerBound;
    private int colsUpperBound;


    /**
     * @returns a new instance of MoveBounds.
     */
    public MoveBounds(char rowsLowerBound, char rowsUpperBound, int colsLowerBound, int colsUpperBound)
    {
        this.rowsLowerBound = rowsLowerBound;
        this.rowsUpperBound = rowsUpperBound;
        this.colsLowerBound = colsLowerBound;
        this.colsUpperBound = colsUpperBound;
    }

    /**
     * sets the lower bound for rows.
     */
    public void setRowsLowerBound(char rowsLowerBound)
    {
        this.rowsLowerBound = rowsLowerBound;
    }

    /**
     * sets the upper bound for rows.
     */
    public void setRowsUpperBound(char rowsUpperBound)
    {
        this.rowsUpperBound = rowsUpperBound;
    }

    /**
     * sets the lower bound for columns.
     */
    public void setColsLowerBound(int colsLowerBound)
    {
        this.colsLowerBound = colsLowerBound;
    }

    /**
     * sets the upper bound for columns.
     */
    public void setColsUpperBound(int colsUpperBound)
    {
        this.colsUpperBound = colsUpperBound;
    }

    /**
     * @returns the lower bound for rows.
     */
    public int getRowsLowerBound()
    {
        return rowsLowerBound;
    }

    /**
     * @returns the upper bound for rows.
     */
    public int getRowsUpperBound()
    {
        return rowsUpperBound;
    }

    /**
     * @returns the lower bound for columns.
     */
    public int getColsLowerBound()
    {
        return colsLowerBound;
    }

    /**
     * @returns the upper bound for columns.
     */
    public int getColsUpperBound()
    {
        return colsUpperBound;
    }


}
