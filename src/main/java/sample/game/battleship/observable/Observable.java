package sample.game.battleship.observable;

import java.beans.PropertyChangeListener;

/**
 * observable interface for use with the observer pattern.
 */
public interface Observable
{
    void addListener(PropertyChangeListener listener);
    void removeListener(PropertyChangeListener listener);
}
