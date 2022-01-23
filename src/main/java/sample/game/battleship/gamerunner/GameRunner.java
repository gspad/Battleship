package sample.game.battleship.gamerunner;

import sample.game.battleship.enums.BoardSide;
import sample.game.battleship.enums.GameStatus;
import sample.game.battleship.gameboard.GameBoard;
import sample.game.battleship.player.Player;
import sample.game.battleship.player.move.MoveBounds;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Scanner;

/**
 * This class is responsible for running the game. It handles user turns as well as player move restrictions and
 * is in charge of determining when a game is over.
 */
public class GameRunner implements PropertyChangeListener
{
    private final GameBoard gameBoard;
    private final Player p1;
    private final Player p2;
    private GameStatus gameStatus = GameStatus.RUNNING;

    /**
     * @param gameBoard the game board.
     * @param p1 player 1.
     * @param p2 player 2.
     * @return a new instance of GameRunner
     */
    private GameRunner(GameBoard gameBoard, Player p1, Player p2)
    {
        this.gameBoard = gameBoard;
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * @param gameBoard the game board.
     * @param p1 player 1.
     * @param p2 player 2.
     * @return a new instance of GameRunner
     */
    public static GameRunner newInstance(GameBoard gameBoard, Player p1, Player p2)
    {
        GameRunner gameRunner = new GameRunner(gameBoard,p1,p2);
        gameRunner.setup();
        return gameRunner;
    }

    /**
     * sets the game up by adding GameRunner as a listener to each player, assigning each player a half of the board, and setting up the letterToRowDict
     * which is used to translate user coordinates for the row (A-Z) into an array index (0-9).
     */
    private void setup()
    {
        p1.addListener(this);
        p2.addListener(this);
        p1.setBoardSide(BoardSide.BOTTOM);
        p2.setBoardSide(BoardSide.TOP);

        if(p1.getName() == null)
            p1.setName("Player 1");

        if(p2.getName() == null)
            p2.setName("Player 2");
    }

    /**
     * runs the game. For each player, it creates a MoveBounds object which is used to determine the bounds for each player's moves. MoveBounds is passed into
     * each player's makeMove(MoveBounds) method. If a player chooses a spot outside of the bounds
     * indicated by MoveBounds, then the move is considered an illegal move.
     */
    public void run()
    {
        //checks for player input command to begind the game.
        this.checkForStartCommand();
        this.runGameLoop();
    }

    /**
     * receives a property change event from a player if the player has lost (it has 0 ships left),
     * then sets the game's status to OVER.
     * @param evt the property change event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if(evt.getSource() == p2)
            System.out.println("You Won!");
        if(evt.getSource() == p1)
            System.out.println("You Lost!");

        this.setGameStatus(GameStatus.OVER);
    }

    /**
     * sets the game's status.
     * @param gameStatus the game status.
     */
    private void setGameStatus(GameStatus gameStatus)
    {
        this.gameStatus = gameStatus;
    }

    private void checkForStartCommand()
    {
        System.out.println("Type KITTENS to start the game");
        String beginCommand = "";

        Scanner scanner = new Scanner(System.in);
        while(!beginCommand.equalsIgnoreCase("KITTENS")) {
            beginCommand = scanner.next();
            if(!beginCommand.equalsIgnoreCase("KITTENS"))
                System.out.println("invalid command");
        }
    }

    private void runGameLoop()
    {
        MoveBounds[] moveBounds = getPlayerMoveBounds();
        Player currentPlayer = p1;

        while(this.gameStatus == GameStatus.RUNNING)
        {
            //print board
            System.out.println("\n" + currentPlayer.getName() + "'s turn....");
            //make current player move
            MoveBounds currentPlayerMoveBounds = currentPlayer.getBoardSide() == BoardSide.BOTTOM ? moveBounds[0] : moveBounds[1];
            currentPlayer.makeMove(gameBoard,currentPlayerMoveBounds);
            //switch players
            currentPlayer = currentPlayer == p1? p2 : p1;
        }
        p1.cleanup();
        p2.cleanup();
    }

    private MoveBounds[] getPlayerMoveBounds()
    {
        MoveBounds p1MoveBounds = MoveBounds.builder()
                .withLowerRowLimit(0)
                .withUpperRowLimit(gameBoard.getNumRows()/2-1)
                .withLowerColLimit(0)
                .withUpperColLimit(gameBoard.getNumColumns()-1)
                .build();

        MoveBounds p2MoveBounds = MoveBounds.builder()
                .withLowerRowLimit(gameBoard.getNumRows()/2)
                .withUpperRowLimit(gameBoard.getNumRows()-1)
                .withLowerColLimit(0)
                .withUpperColLimit(gameBoard.getNumColumns()-1)
                .build();

        return new MoveBounds[]{p1MoveBounds,p2MoveBounds};
    }
}
