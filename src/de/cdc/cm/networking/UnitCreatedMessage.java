package de.cdc.cm.networking;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import de.cdc.cm.units.Unit.UnitType;

/**
 *
 * @author Julius
 * 
 */
@Serializable
public class UnitCreatedMessage extends AbstractMessage
{
    private int id;
    private UnitType type;
    private Vector3f location;
    private boolean isPlayerA;
    
    public UnitCreatedMessage() {}
    
    public UnitCreatedMessage(int id, UnitType t, Vector3f pos, boolean isPlayerA)
    {
        this.id = id;
        this.type = t;
        this.location = pos;
        this.isPlayerA = isPlayerA;
    }
    
    public int getId()
    {
        return id;
    }

    public UnitType getType()
    {
        return type;
    }

    public Vector3f getLocation()
    {
        return location;
    }
    
    public boolean isPlayerA()
    {
        return isPlayerA;
    }
}