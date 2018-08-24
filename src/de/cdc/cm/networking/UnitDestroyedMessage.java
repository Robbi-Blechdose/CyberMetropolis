package de.cdc.cm.networking;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Julius
 * 
 */
@Serializable
public class UnitDestroyedMessage extends AbstractMessage
{
    private int id;
    private boolean playerA;
    
    public UnitDestroyedMessage() {}
    
    public UnitDestroyedMessage(int id, boolean isPlayerA)
    {
        this.id = id;
    }
    
    public int getId()
    {
        return id;
    }
    
    public boolean isPlayerA()
    {
        return playerA;
    }
}