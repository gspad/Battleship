package sample.game.battleship.player.playerimpl;

import sample.game.battleship.gameboard.GameBoard;
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
    public void makeMove(GameBoard gameBoard, MoveBounds moveBounds)
    {
        //print board
        System.out.println(gameBoard.toString(super.getBoardSide()));
        int[] move;

        do
        {
            move = getPlayerMove();
        }
        while(!isValidMove(move[0],move[1],moveBounds));

        if(!gameBoard.containsShipAt(move[0],move[1])) System.out.println("You have missed!");
        gameBoard.hit(move[0],move[1]);
    }

    private int[] getPlayerMove()
    {
        String moveInput = "";
        int[] move = new int[2];

        while(moveInput.length() < 2)
        {
            System.out.println("Enter coordinates (type QUIT to quit the game...coward.):");
            moveInput = scanner.next();
        }

        if(moveInput.equalsIgnoreCase("quit"))
            this.forcePlayerToLose();

        try
        {
            move[0] = Character.toLowerCase(moveInput.charAt(0)) - 97;
            move[1] = Integer.parseInt(moveInput.substring(1)) - 1;
        }
        catch(NumberFormatException e)
        {
            return new int[] {Integer.MIN_VALUE, Integer.MIN_VALUE};
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

    @Override
    public void cleanup()
    {
        this.scanner.close();
    }
}
