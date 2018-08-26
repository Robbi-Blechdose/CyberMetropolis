package de.cdc.cm.networking;

import com.jme3.math.Quaternion;
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
    private List<Float> unitRotations;
    private boolean isPlayerA;
    
    public UnitUpdateMessage() {}
    
    public UnitUpdateMessage(List<Vector3f> unitPositions, List<Float> unitRotations, boolean isPlayerA)
    {
        this.unitPositions = unitPositions;
        this.unitRotations = unitRotations;
        this.isPlayerA = isPlayerA;
    }

    public List<Vector3f> getUnitPositions()
    {
        return unitPositions;
    }
    
    public List<Float> getUnitRotations()
    {
        return unitRotations;
    }

    public boolean isPlayerA()
    {
        return isPlayerA;
    }
}