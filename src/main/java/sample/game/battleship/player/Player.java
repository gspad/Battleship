package sample.game.battleship.player;

import sample.game.battleship.Ship.Ship;
import sample.game.battleship.enums.BoardSide;
import sample.game.battleship.observable.Observable;
import sample.game.battleship.player.move.MoveBounds;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * abstract class Player represents a BattleShip Player. Both HumanPlayer and ComputerPlayer extend this class.
 */
public abstract class Player implements PropertyChangeListener,Observable
{
    private final PropertyChangeSupport listenerManager = new PropertyChangeSupport(this);
    private int numberOfShipsLeft;
    private final List<Ship> ships;
    private BoardSide boardSide;
    private MoveBounds moveRestrictions;
    private String name = null;

    /**
     * @returns a valid instance of Player
     */
    public Player(List<Ship> ships)
    {
        this.numberOfShipsLeft = ships.size();
        this.ships = ships;
    }

    /**
     * destroys one of the player's current ships.
     */
    public void destroyShip()
    {
        this.numberOfShipsLeft--;
        if(this.numberOfShipsLeft == 0)
            this.firePlayerLostEvent();
    }

    /**
     * sets this player's board side.
     * @param boardSide the board side.
     */
    public void setBoardSide(BoardSide boardSide)
    {
        this.boardSide = boardSide;
    }

    /**
     * @returns this player's board side.
     */
    public BoardSide getBoardSide()
    {
        return this.boardSide;
    }

    /**
     * adds a listener for this player object.
     * @param listener the listener who will listen to this Player.
     */
    public void addListener(PropertyChangeListener listener)
    {
        this.listenerManager.addPropertyChangeListener(listener);
    }

    /**
     * removes listener from this player's list of listeners.
     * @param listener the listener.
     */
    public void removeListener(PropertyChangeListener listener)
    {
        this.listenerManager.removePropertyChangeListener(listener);
    }

    /**
     * @returns this player's list of ships.
     */
    public List<Ship> getShips()
    {
        return this.ships;
    }

    /**
     * fires an event that indicates that this player has lost.
     */
    private void firePlayerLostEvent()
    {
        listenerManager.firePropertyChange(new PropertyChangeEvent(this,"numberOfShipsLeft",1,0));
    }

    /**
     * prompts the player to make a move. HumanPlayer and ComputerPlayer handle this differently.
     * @param moveRestrictions encapsulates this player's move restrictions with respect to the chosen coordinates.
     */
    public abstract String makeMove(MoveBounds moveRestrictions);

    /**
     * sets this player's name
     * @param name the player's name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @returns this player's name.
     */
    public String getName()
    {
        return this.name;
    }


}


