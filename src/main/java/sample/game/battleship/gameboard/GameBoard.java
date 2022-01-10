package sample.game.battleship.gameboard;

import sample.game.battleship.Ship.*;
import sample.game.battleship.enums.BoardSide;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * The GameBoard class represents the BattleShip game's board.
 */
public class GameBoard
{
    private final BoardCell[][] board;
    private final List<Ship> p1Ships;
    private final List<Ship> p2Ships;

    /**
     * @returns new instance of GameBoard.
     * @param board the double array representing the 2D board.
     * @param p1Ships player 1's ships.
     * @param p2Ships player 2's ships.
     */
    private GameBoard(BoardCell[][] board, List<Ship> p1Ships, List<Ship> p2Ships)
    {
        this.board = board;
        this.p1Ships = p1Ships;
        this.p2Ships = p2Ships;
    }

    /**
     * @returns new instance of GameBoard.
     * @param board the double array representing the 2D board.
     * @param p1Ships player 1's ships.
     * @param p2Ships player 2's ships.
     */
    public static GameBoard newGameBoard(BoardCell[][] board, List<Ship> p1Ships, List<Ship> p2Ships)
    {
        GameBoard gameboard = new GameBoard(board,p1Ships,p2Ships);
        gameboard.placeShips(p1Ships,board.length/2,board.length-1);
        gameboard.placeShips(p2Ships,0,board.length/2-1);
        return gameboard;
    }

    /**
     * places the ships on the board within the lower and upper bounds passed into this method.
     * @param ships the list of ships to place on the board.
     * @param lowerBound lower bound for placing ships on the board.
     * @param upperBound upperBpund for placing the ships on the board.
     */
    private void placeShips(List<Ship> ships, int lowerBound, int upperBound)
    {
        Random randomGenerator = new Random();
        //Extract some of this shit into functions.
        for(Ship ship : ships)
        {
            int shipSize = ship.getSize();
            boolean isHorizontal = true;
            boolean isValidPlacement = false;

            while(!isValidPlacement)
            {
                isHorizontal = randomGenerator.nextBoolean();
                int x = randomGenerator.nextInt(lowerBound,upperBound);
                int y = randomGenerator.nextInt(0,board.length);
                Iterator<ShipPart> iterator = ship.getShipParts().iterator();
                //check for valid placement
                if(!isValid(ship.getSize(),x,y,lowerBound,upperBound,isHorizontal)) continue;
                //places ship according to horizontal or vertical direction.
                placeShip(ship,isHorizontal,x,y);
                isValidPlacement = true;
            }
        }
    }

    /**
     * @returns true if the ship will be placed in a valid cell(S).
     * @param shipSize the ship's size.
     * @param x the ships's row placement.
     * @param y the ship's column placeent.
     * @param lowerBound lower bound for placing ships on the board.
     * @param upperBound upperBpund for placing the ships on the board.
     * @param isHorizontal boolean that ss true if the ship is to be placed hoizontally.
     */

    private boolean isValid(int shipSize, int x, int y, int lowerBound, int upperBound, boolean isHorizontal)
    {
        if(!isHorizontal) return spaceIsFree(x,y,x+shipSize,y,lowerBound,upperBound);
        else return spaceIsFree(x,y,x,y+shipSize,lowerBound,upperBound);
    }

    /**
     * @param ship the ship to place.
     * @param x the ship's row placement.
     * @param y the ship's column placement
     * @param isHorizontal boolean that ss true if the ship is to be placed hoizontally.
     */
    private void placeShip(Ship ship, boolean isHorizontal, int x, int y)
    {
        Iterator<ShipPart> iterator = ship.getShipParts().iterator();

        if(isHorizontal)
        {
            for(int i = 0; i < ship.getSize(); i++)
            {
                board[x][y+i].setShipPart(iterator.next());
            }
        }
        else
        {
            for(int i = 0; i < ship.getSize(); i++)
            {
                board[x+i][y].setShipPart(iterator.next());
            }
        }

    }

    /**
     * @returns true if the space around cordinate x,y
     * @param x the ships's row placement beginning index.
     * @param y the ship's column placeent.
     * @param x1 the ship's column placement end endex.
     * @param y1
     * @param lowerBound lower bound for placing ships on the board.
     * @param upperBound upperBpund for placing the ships on the board.
     */
    private boolean spaceIsFree(int x, int y, int x1, int y1, int lowerBound, int upperBound)
    {
        if(x < lowerBound || y < 0 || x1 > upperBound || y1 >= board.length) return false;

        for(int i = x; i <= x1; i++)
        {
            for(int j = y; j <= y1; j++)
            {
                if(board[i][j].containsShipPart()) return false;
            }
        }
        return true;
    }

    /**
     * hits this particular cell on the board.
     * @param x the x-value to be hit.
     * @param y the y-value to be hit.
     */
    public void hit(int x, int y)
    {
        this.board[x][y].hit();
    }

    /**
     * @returns the number of columns in the board.
     */
    public int getNumColumns()
    {
        return this.board[0].length;
    }

    /**
     * @returns the number of rows in the board.
     */
    public int getNumRows()
    {
        return this.board.length;
    }

    /**
     * @returns a String representation of this board.
     */
    @Override
    public String toString()
    {
        String rows = "ABCDEFGHIJKLMNOP";
        StringBuilder sb = new StringBuilder();
        sb.append("\n  0123456789\n");

        for(int i = 0; i < board.length; i++)
        {
            if(i == board.length/2) sb.append("\n");
            sb.append(rows.charAt(i) + " ");

            for(int j = 0; j < board.length; j++)
            {
               sb.append(board[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * @returns the board but only half of it revealed (as indicated by the boardSide param.
     */
    public String toString(BoardSide boardSide)
    {
       return boardSide == BoardSide.BOTTOM ? revealBottomHalf() : revealTopHalf();
    }

    /**
     * @returns a String representing the bottom half of the board with everything revealed.
     */
    public String revealBottomHalf()
    {
        String rows = "ABCDEFGHIJKLMNOP";
        StringBuilder sb = new StringBuilder();
        sb.append("\n  0 1 2 3 4 5 6 7 8 9\n");

        for(int rowIdx = 0; rowIdx < board.length/2; rowIdx++)
        {
            sb.append(rows.charAt(rowIdx) + " ");
            for(int colIdx = 0; colIdx < board[0].length; colIdx++)
                sb.append(board[rowIdx][colIdx].toStringHidden() + " ");
            sb.append("\n");
        }

        sb.append("\n");

        for(int rowIdx = board.length/2; rowIdx < board.length; rowIdx++)
        {
            sb.append(rows.charAt(rowIdx) + " ");
            for(int colIdx = 0; colIdx < board[0].length; colIdx++)
                sb.append(board[rowIdx][colIdx].toString() + " ");
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * @returns a String representing the top half of the board with everything revealed.
     */
    public String revealTopHalf()
    {
        String rows = "ABCDEFGHIJKLMNOP";
        StringBuilder sb = new StringBuilder();
        sb.append("\n  0123456789\n");

        for(int rowIdx = 0; rowIdx < board.length/2; rowIdx++)
        {
            sb.append(rows.charAt(rowIdx) + " ");
            for(int colIdx = 0; colIdx < board[0].length; colIdx++)
                sb.append(board[rowIdx][colIdx].toString());
        }

        for(int rowIdx = board.length/2; rowIdx < board.length; rowIdx++)
        {
            for(int colIdx = 0; colIdx < board[0].length; colIdx++)
                sb.append(board[rowIdx][colIdx].toStringHidden());
        }
        return sb.toString();
    }
}
