package sample.game.battleship.player.playerimpl;

import sample.game.battleship.gameboard.GameBoard;
import sample.game.battleship.ship.Ship;
import sample.game.battleship.enums.BoardSide;
import sample.game.battleship.player.Player;
import sample.game.battleship.player.move.MoveBounds;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Random;

/**
 * A ComputerPlayer. This player has its own game turn logic.
 */
public class ComputerPlayer extends Player
{
    private BoardSide boardSide;

    /**
     * @returns an instance of ComputerPlayer.
     */
    private ComputerPlayer(List<Ship> ships)
    {
        super(ships);
    }

    /**
     * @returns an instance of ComputerPlayer.
     */
    public static ComputerPlayer withShips(List<Ship> ships)
    {
        ComputerPlayer computerPlayer = new ComputerPlayer(ships);
        computerPlayer.addAsListenerTo(ships);
        computerPlayer.setBoardSide(BoardSide.TOP);
        computerPlayer.setName("Computer");
        return computerPlayer;
    }

    /**
     * adds this player as listener to the given ships.
     * @param ships the player's ships.
     */
    private void addAsListenerTo(List<Ship> ships)
    {
        for(Ship ship : ships)
        {
            ship.addListener(this);
        }
    }

    /**
     * makes a random move within the specified moveBounds.
     * @param moveBounds the move bounds, indicating where the player can and cannot fire.
     */
    @Override
    public void makeMove(GameBoard gameBoard, MoveBounds moveBounds)
    {
        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        int[] move = new int[] {Integer.MIN_VALUE,Integer.MIN_VALUE};
        Random random = new Random();

        do
        {
            move[0] = random.nextInt(moveBounds.getRowsLowerBound(), moveBounds.getRowsUpperBound()+1);
            move[1] = random.nextInt(moveBounds.getColsLowerBound(), moveBounds.getColsUpperBound());
        }
        while(!isValidMove(move[0],move[1],moveBounds));
        gameBoard.hit(move[0],move[1]);
    }

    /**
     * receiving a propertyChangeEvent here means that one of the player's ships has either been hit or destroyed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent shipDestroyedEvent)
    {
        Integer newValue = (Integer) shipDestroyedEvent.getNewValue();
        if(newValue == 0)
        {
            System.out.println("You have destroyed an enemy ship!!");
            this.destroyShip();
        }
        else
            System.out.println("You hit an enemy ship!!");
    }

    //nothing to clean up, for now.
    public void cleanup()
    {

    }
}
