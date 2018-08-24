package de.cdc.cm.networking;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import java.util.List;

/**
 *
 * @author Julius
 * 
 */
@Serializable
public class UnitUpdateMessage extends AbstractMessage
{
    private List<Vector3f> unitPositions;
    private boolean isPlayerA;
    
    public UnitUpdateMessage() {}
    
    public UnitUpdateMessage(List<Vector3f> unitPositions, boolean isPlayerA)
    {
        this.unitPositions = unitPositions;
        this.isPlayerA = isPlayerA;
    }

    public List<Vector3f> getUnitPositions()
    {
        return unitPositions;
    }

    public boolean isPlayerA()
    {
        return isPlayerA;
    }
}