package de.cdc.cm.networking;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Julius
 * 
 */
@Serializable
public class DamageSyncMessage extends AbstractMessage
{
    private int id;
    private int damage;
    private boolean isPlayerA;
    
    public DamageSyncMessage() {}
    
    public DamageSyncMessage(int id, int damage, boolean isPlayerA)
    {
        this.id = id;
        this.damage = damage;
        this.isPlayerA = isPlayerA;
    }

    public int getId()
    {
        return id;
    }

    public int getDamage()
    {
        return damage;
    }
    
    public boolean isPlayerA()
    {
        return isPlayerA;
    }
}