package sample.game.battleship.gameboard;

import sample.game.battleship.Ship.ShipPart;

/**
 * BoardCell represents a cell on the game board. A cell can either be empty (represented by symbol '~'
 * or it can contain a ShipPart (represented by 'S' but hidden for the enemy's board side).
 */
public class BoardCell
{
    private int x;
    private int y;
    private ShipPart shipPart = null;
    private String symbol = "~";

    /**
     * @returns a new instanceof BoardCell
     */
    public BoardCell()
    {

    }

    /**
     * hits this board cell. If there is a ShipPart in this board cell, then that ShipPart is consequently hit.
     */
    public void hit()
    {
        if(shipPart != null)
        {
            shipPart.hit();
            this.symbol = "X";
        }
        else
            this.symbol = "M";
    }

    /**
     * sets a ShipPart for this cell.
     * @param shipPart the ship part
     */
    public void setShipPart(ShipPart shipPart)
    {
        this.shipPart = shipPart;
    }

    /**
     * @returns true if this cell contains a ShipPart.
     */
    public boolean containsShipPart()
    {
        return this.shipPart != null;
    }

    /**
     * prints a String representation of this board cell.
     */
    @Override
    public String toString()
    {
        if(shipPart == null) return this.symbol;
        return this.shipPart.toString();
    }

    /**
     * @returns a String representation of this board cell, but hides ship symbols 'S'. This is called when a half of the board should remain hidden for one of the players
     * (in this case, the computer's half is hidden, showing every cell as a '~'.
     */
    public String toStringHidden()
    {
        return this.symbol;
    }
}
