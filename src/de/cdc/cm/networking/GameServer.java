package de.cdc.cm.networking;

import com.jme3.math.Vector3f;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Julius
 * 
 */
public class GameServer implements MessageListener<HostedConnection>, ConnectionListener
{
    private Server server;
 
    private List<Vector3f> unitAPositions;
    private List<Vector3f> unitBPositions;
    
    public GameServer()
    {
        try
        {
            server = Network.createServer(5110);
            server.addConnectionListener(this);
            server.addMessageListener(this);
            server.start();
            System.out.println("Game server started.");
        }
        catch(IOException e)
        {
            //Do nothing, heh
            System.out.println("Could not start server.");
            e.printStackTrace();
        }
        
        unitAPositions = new ArrayList<Vector3f>();
        unitBPositions = new ArrayList<Vector3f>();
    }
    
    public void update(float tpf)
    {
        sendUnitUpdateMessage();
    }

    @Override
    public void messageReceived(HostedConnection source, Message m)
    {
        if(m instanceof UnitUpdateMessage)
        {
            if(((UnitUpdateMessage) m).isPlayerA())
            {
                unitAPositions = ((UnitUpdateMessage) m).getUnitPositions();
            }
            else
            {
                unitBPositions = ((UnitUpdateMessage) m).getUnitPositions();
            }
        }
        else if(m instanceof UnitCreatedMessage)
        {
            server.broadcast(m);
        }
        else if(m instanceof UnitDestroyedMessage)
        {
            server.broadcast(m);
        }
    }

    @Override
    public void connectionAdded(Server server, HostedConnection conn)
    {
        
    }

    @Override
    public void connectionRemoved(Server server, HostedConnection conn)
    {
        
    }
    
    private void sendUnitUpdateMessage()
    {
        UnitUpdateMessage messageA = new UnitUpdateMessage(unitAPositions, true);
        server.broadcast(messageA);
        UnitUpdateMessage messageB = new UnitUpdateMessage(unitBPositions, false);
        server.broadcast(messageB);
    }
}