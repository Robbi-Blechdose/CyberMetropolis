package de.cdc.cm.networking;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import de.cdc.cm.units.Unit.UnitType;

/**
 *
 * @author Julius
 * 
 */
@Serializable
public class UnitCreatedMessage
{
    private UnitType type;
    private Vector3f location;
    
    public UnitCreatedMessage() {}
    
    public UnitCreatedMessage(UnitType t, Vector3f pos)
    {
        this.type = t;
        this.location = pos;
    }

    public UnitType getType()
    {
        return type;
    }

    public Vector3f getLocation()
    {
        return location;
    }
}