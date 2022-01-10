package sample.game.battleship.gamerunner;

import sample.game.battleship.gameboard.GameBoard;
import sample.game.battleship.player.Player;

/**
 * GameContext encapsulates the game board, player 1 and player 2.
 */
public class GameContext
{
    private final GameBoard gameBoard;
    private final Player p1;
    private final Player p2;

    /**
     * @returns a new instance of GameContext
     */
    public GameContext(GameBoard gameBoard, Player p1, Player p2)
    {
        this.gameBoard = gameBoard;
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * @returns the GameContext's gameBoard.
     */
    public GameBoard getGameBoard()
    {
        return this.gameBoard;
    }

    /**
     * @returns the GameContext's player 1.
     */
    public Player getPlayer1()
    {
        return this.p1;
    }

    /**
     * @returns the GameContext's player 2.
     */
    public Player getPlayer2()
    {
        return this.p2;
    }
}
