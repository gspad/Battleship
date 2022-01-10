package sample.game.battleship;

import sample.game.battleship.gamerunner.GameContext;
import sample.game.battleship.gameboard.BoardCell;
import sample.game.battleship.gameboard.GameBoard;
import sample.game.battleship.player.Player;
import sample.game.battleship.Ship.*;
import sample.game.battleship.player.playerimpl.ComputerPlayer;
import sample.game.battleship.player.playerimpl.HumanPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * The CompositionRoot is in charge of creating and injecting dependencies into the Project Graph tree.
 * This is essentially a poor man's dependency injection since using Spring or Spring Boot was overkill for this project.
 */
public class CompositionRoot
{
    private final Properties properties;

    /**
     * @param properties the game properties.
     */
    public CompositionRoot(Properties properties)
    {
        this.properties = properties;
    }

    /**
     * creates the game context, which consists of the game board, player 1 and player 2.
     */
    public GameContext createGameContext()
    {
        if(badProperties()) throw new IllegalArgumentException("property values not correct. Check config.properties");
        List<Ship> humanPlayerShips = createShips();
        List<Ship> computerPlayerShips = createShips();
        Player humanPlayer = HumanPlayer.withShips(humanPlayerShips);
        Player computerPlayer = ComputerPlayer.withShips(computerPlayerShips);
        GameBoard board = createGameBoard(humanPlayer.getShips(), computerPlayer.getShips());
        return new GameContext(board,humanPlayer,computerPlayer);
    }

    /**
     * creates ships according to the game properties (ship.min_size, ship.max_size).
     * NOTE: This function only creates ships for one player. It has to be called once for each player.
     */
    private List<Ship> createShips()
    {
        int minShipSize = Integer.parseInt(properties.getProperty("ship.min_size"));
        int maxShipSize = Integer.parseInt(properties.getProperty("ship.max_size"));
        List<Ship> ships = new ArrayList<>();

        for(int shipSize = minShipSize; shipSize <= maxShipSize; shipSize++)
        {
            List<ShipPart> shipParts = new ArrayList<ShipPart>();
            for(int i = 0; i < shipSize; i++) shipParts.add(new ShipPart());
            ships.add(Ship.ofSize(shipParts));
        }
        return ships;
    }

    /**
     * Creates a new game board.
     * @param p1ships player 1's ships.
     * @param p2ships player 2's ships.
     */
    private GameBoard createGameBoard(List<Ship> p1ships, List<Ship> p2ships)
    {
        int boardSize = Integer.parseInt(properties.getProperty("board.size"));
        BoardCell[][] board = new BoardCell[boardSize][boardSize];

        //initialize all cells in the board.
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board.length; j++)
            {
                board[i][j] = new BoardCell();
            }
        }
        return GameBoard.newGameBoard(board,p1ships,p2ships);
    }

    /**
     * @return true if the loaded properties are valid for the game.
     * For example, the game board's size should be big enough to hold all ships.
     */
    private boolean badProperties()
    {
        //board size divided by 2 for checking appropriate ship number and sizes for each sample.game.battleship.player since they each get half of the board.
        int boardSize = Integer.parseInt(properties.getProperty("board.size"));
        int minShipSize = Integer.parseInt(properties.getProperty("ship.min_size"));
        int maxShipSize = Integer.parseInt(properties.getProperty("ship.max_size"));
        int numberOfShips = Integer.parseInt(properties.getProperty("player.number_of_ships"));

        if(!(boardSize%2 == 0) || boardSize/2 < numberOfShips + 1) return true;
        if(minShipSize > maxShipSize) return true;
        return maxShipSize > boardSize;
    }
}
