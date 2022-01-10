package sample.game.battleship.ship;

import sample.game.battleship.observable.Observable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * This class represents a Ship object. A Ship contains ShipParts which, when all destroyed, also prompt the ship to be destroyed.
 */
public class Ship implements PropertyChangeListener,Observable
{
    private final PropertyChangeSupport listenerManager = new PropertyChangeSupport(this);
    private final List<ShipPart> shipParts;
    private int hp;

    /**
     * @returns an instance of Ship.
     */
    private Ship(List<ShipPart> shipParts)
    {
        this.hp = shipParts.size();
        this.shipParts = shipParts;
    }

    /**
     * @returns an instance of Ship.
     */
    public static Ship ofSize(List<ShipPart> shipParts)
    {
        Ship ship = new Ship(shipParts);
        ship.addAsListenerTo(shipParts);
        return ship;
    }

    /**
     * receives a PropertyChangeEvent indicating that one of the ship's parts has been hit.
     * @param shipPartDestroyed the PropertyChangeEvent indicating that a ShipPart has been destroyed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent shipPartDestroyed)
    {
            this.hp--;
            this.fireShipHitEvent();
    }

    /**
     * fires an event to notify listeners (Player objects) that the ship has been hit or destroyed.
     */
    public void fireShipHitEvent()
    {
        listenerManager.firePropertyChange(new PropertyChangeEvent(this,"hp",this.hp+1,this.hp));
    }

    /**
     * adds this ship as listener for its Ship Parts
     */
    private void addAsListenerTo(List<ShipPart> shipParts)
    {
        for(ShipPart part : shipParts)
        {
            part.addListener(this);
        }
    }

    /**
     * adds an object as listener to this Ship. Currently, Player objects are the listeners for Ship objects.
     * @param listener Listener to add as one of this Ship's listeners.
     */
    @Override
    public void addListener(PropertyChangeListener listener)
    {
        this.listenerManager.addPropertyChangeListener(listener);
    }

    /**
     * removes an object as listener to this Ship. Currently, Player objects are the listeners for Ship objects.
     * @param listener Listener to remove from this Ship's listeners.
     */
    @Override
    public void removeListener(PropertyChangeListener listener)
    {
        this.listenerManager.removePropertyChangeListener(listener);
    }

    /**
     * @returns this Ship's size -- determined by the number of Ship Parts.
     */
    public int getSize()
    {
        return this.hp;
    }

    /**
     * @return this Ship's list of Ship Parts.
     */
    public List<ShipPart> getShipParts()
    {
        return this.shipParts;
    }
}

