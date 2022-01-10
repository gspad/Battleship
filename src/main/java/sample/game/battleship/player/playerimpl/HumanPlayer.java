package sample.game.battleship.player.playerimpl;

import sample.game.battleship.ship.Ship;
import sample.game.battleship.enums.BoardSide;
import sample.game.battleship.player.Player;
import sample.game.battleship.player.move.MoveBounds;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Scanner;

/**
 * The human player. This player takes a turn by choosing a coordinate.
 */
public class HumanPlayer extends Player
{
    private Scanner scanner;

    /**
     * @returns an instance of humanPlayer.
     */
    private HumanPlayer(List<Ship> ships)
    {
        super(ships);
    }

    /**
     * @returns an instance of humanPlayer.
     */
    public static HumanPlayer withShips(List<Ship> ships)
    {
        HumanPlayer humanPlayer = new HumanPlayer(ships);
        humanPlayer.addAsListenerTo(ships);
        humanPlayer.setBoardSide(BoardSide.BOTTOM);
        humanPlayer.setInputScanner(new Scanner(System.in));
        return humanPlayer;
    }

    /**
     * sets this player's input scanner.
     */
    private void setInputScanner(Scanner scanner)
    {
        this.scanner = scanner;
    }

    /**
     * sets this HumanPlayer as listener to the given ships.
     */
    private void addAsListenerTo(List<Ship> ships)
    {
        for(Ship ship : ships)
        {
            ship.addListener(this);
        }
    }

    /**
     * allows player to choose a coordinate and then checks if the move is valid (within bounds of provided MoveBounds).
     * @returns a String with the player's input if valid.
     */
    @Override
    public String makeMove(MoveBounds moveBounds)
    {
        int rowLowerBound = moveBounds.getRowsLowerBound();
        boolean isValidMove = false;
        String move = "";

        while(!isValidMove)
        {
            System.out.println("Enter a coordinate:");
            move = scanner.next();
            if(move.isBlank() || move.isEmpty()) continue;

            int rowChoice = Character.toLowerCase(move.charAt(0));
            int colChoice = Character.getNumericValue(move.charAt(1));

            isValidMove = rowChoice >= moveBounds.getRowsLowerBound() && rowChoice <= moveBounds.getRowsUpperBound()
                    && colChoice >= moveBounds.getColsLowerBound() && colChoice <= moveBounds.getColsUpperBound();

            if(!isValidMove) System.out.println("Please make a valid move!");
        }
        return move;
    }

    /**
     * receives a property change event. This propertyChangeEvent likely indicates that a ship has been hit or destroyed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent shipHitEvent)
    {
        Integer newValue = (Integer) shipHitEvent.getNewValue();
        if(newValue == 0)
        {
            this.destroyShip();
            System.out.println("The enemy has destroyed your ship!");
        }
        else
            System.out.println("The enemy has hit your ship!");
    }
}
