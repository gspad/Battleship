package sample.game.battleship.Ship;

import sample.game.battleship.observable.Observable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * this class represents a ShipPart. Each board cell can either be empty or contain a Ship Part, and each Ship can also contain a list of ShipParts.
 * This is the smallest unit in the game and each BoardCell can hold exactly one ShipPart.
 */
public class ShipPart implements Observable {
    private int hp = 1;
    private final PropertyChangeSupport listenerManager = new PropertyChangeSupport(this);
    private String symbol = "S";

    /**
     * adds an object as listener to this ShipPart. Currently, Ship objects are the listeners for ShipPart objects.
     * @param listener the listener that will listen to this ShipPart.
     */
    @Override
    public void addListener(PropertyChangeListener listener) {
        this.listenerManager.addPropertyChangeListener(listener);
    }

    /**
     * removes an object from this ShipPart's list of listeners. Currently, Ship objects are the listeners for ShipPart objects.
     * @param listener the listener to be removed from this ShipPart's list of listeners.
     */
    @Override
    public void removeListener(PropertyChangeListener listener) {
        this.listenerManager.removePropertyChangeListener(listener);
    }

    /**
     * hit this ShipPart. A hit reduces a ShipPart's hp by 1. Once hp is 0, an event is fired to notify the Ship that one of its ShipPart's has been destroyed.
     */
    public void hit()
    {
        if(hp == 0)
        {
            System.out.println("you hit an already destroyed section of a ship!");
            return;
        }

        this.symbol = "X";
        hp--;

        if(hp <= 0)
            listenerManager.firePropertyChange(new PropertyChangeEvent(this,"hp", 1, 0));
    }

    /**
     * @return this ShipPart's hp.
     */
    public int getHp()
    {
        return this.hp;
    }

    /**
     * returns a String representation of this ShipPart. Currently, this is the ShipPart's symbol.
     */
    @Override
    public String toString()
    {
        return this.symbol;
    }
}

