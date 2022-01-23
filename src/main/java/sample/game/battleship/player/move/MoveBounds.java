package sample.game.battleship.player.move;

/**
 * MoveBounds is used to determine if the player has made a valid move (a move within the bounds provided).
 */
public class MoveBounds
{
    private int rowsLowerBound;
    private int rowsUpperBound;
    private int colsLowerBound;
    private int colsUpperBound;

    public static LowerRowLimitStep builder()
    {
        return new MoveBoundsBuilder();
    }

    public interface LowerRowLimitStep
    {
        UpperRowLimitStep withLowerRowLimit(int lowerRowLimit);
    }

    public interface UpperRowLimitStep
    {
        LowerColLimitStep withUpperRowLimit(int upperRowLimit);
    }

    public interface LowerColLimitStep
    {
        UpperColLimitStep withLowerColLimit(int lowerColLimit);
    }

    public interface UpperColLimitStep
    {
        Build withUpperColLimit(int upperColLimit);
    }

    public interface Build
    {
        MoveBounds build();
    }


    /**
     * @returns a new instance of MoveBounds.
     */
    private MoveBounds(int rowsLowerBound, int rowsUpperBound, int colsLowerBound, int colsUpperBound)
    {
        this.rowsLowerBound = rowsLowerBound;
        this.rowsUpperBound = rowsUpperBound;
        this.colsLowerBound = colsLowerBound;
        this.colsUpperBound = colsUpperBound;
    }

    /**
     * sets the lower bound for rows.
     */
    public void setRowsLowerBound(int rowsLowerBound)
    {
        this.rowsLowerBound = rowsLowerBound;
    }

    /**
     * sets the upper bound for rows.
     */
    public void setRowsUpperBound(int rowsUpperBound)
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

    public static class MoveBoundsBuilder implements LowerRowLimitStep, UpperRowLimitStep, LowerColLimitStep, UpperColLimitStep, Build
    {
        private int rowsLowerBound;
        private int rowsUpperBound;
        private int colsLowerBound;
        private int colsUpperBound;

        @Override
        public UpperRowLimitStep withLowerRowLimit(int lowerRowLimit)
        {
            if(lowerRowLimit < 0) throw new IllegalArgumentException("invalid lowerRowLimit");
            this.rowsLowerBound = lowerRowLimit;
            return this;
        }

        @Override
        public LowerColLimitStep withUpperRowLimit(int upperRowLimit)
        {
            if(upperRowLimit <= this.rowsLowerBound) throw new IllegalArgumentException("invalid upperRowLimit");
            this.rowsUpperBound = upperRowLimit;
            return this;
        }

        @Override
        public UpperColLimitStep withLowerColLimit(int lowerColLimit)
        {
            if(lowerColLimit < 0) throw new IllegalArgumentException("lowerColLimit is negative");
            this.colsLowerBound = lowerColLimit;
            return this;
        }

        @Override
        public Build withUpperColLimit(int upperColLimit)
        {
            if(upperColLimit <= this.colsLowerBound) throw new IllegalArgumentException("upperColLimit is too big");
            this.colsUpperBound = upperColLimit;
            return this;
        }

        @Override
        public MoveBounds build() {
            return new MoveBounds(this.rowsLowerBound, this.rowsUpperBound, this.colsLowerBound, this.colsUpperBound);
        }
    }
}
