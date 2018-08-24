package de.cdc.cm;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import com.jme3.scene.Node;
import de.cdc.cm.networking.GameServer;
import de.cdc.cm.networking.UnitCreatedMessage;
import de.cdc.cm.networking.UnitUpdateMessage;
import de.cdc.cm.units.Unit;
import de.cdc.cm.units.Unit.UnitType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import jme3tools.optimize.GeometryBatchFactory;

/**
 *
 * @author Julius
 * 
 */
public class GameState extends GenericState implements ActionListener, ClientStateListener, MessageListener<Client>
{
    private Client client;
    private GameServer server;
    private boolean isHosting;
    
    private FlyCamAppState flycam;
    
    private Node world;
    private RigidBodyControl worldPhysics;
    private Node unitNode;
    
    private DirectionalLight sun;
    private AmbientLight ambient;
    
    private List<Unit> units;
    private List<Unit> enemyUnits;
    
    private Unit selectedUnit;
    private Vector3f targetPosition;
    
    private boolean uiModeEnabled = false;
    
    public GameState(boolean isHosting)
    {
        super();
        this.isHosting = isHosting;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {
	super.initialize(stateManager, app);
        
        world = new Node();
        rootNode.attachChild(world);
        
        unitNode = new Node();
        rootNode.attachChild(unitNode);
        
        //Generate world
        WorldGenerator wg = new WorldGenerator(world, assetManager);
        GeometryBatchFactory.optimize(world);
        
        sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        rootNode.addLight(sun);
        
        ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.1f, 0.1f, 0.1f, 0.1f));
        rootNode.addLight(ambient);
        
        units = new ArrayList<Unit>();
        enemyUnits = new ArrayList<Unit>();
        
        //Register actions
        inputManager.addMapping("Select", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, "Select");
        inputManager.addMapping("UIMode", new KeyTrigger(KeyInput.KEY_TAB));
        inputManager.addListener(this, "UIMode");
        
        //Create flycam
        flycam = new FlyCamAppState();
        stateManager.attach(flycam);
        
        guis.gameGui(true);
        
        Serializer.registerClass(UnitUpdateMessage.class);
        Serializer.registerClass(UnitCreatedMessage.class);
        
        if(isHosting)
        {
            server = new GameServer();
        }
        
        try
        {
            client = Network.connectToServer(guis.getServerIp(), 5110);
            client.addClientStateListener(this);
            client.addMessageListener(this);
            client.start();
        }
        catch(IOException e)
        {
            //Do nothing
            System.out.println("Could not connect.");
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(float tpf)
    {
        if(isHosting)
        {
            server.update(tpf);
        }
        
        List<Vector3f> unitPositions = new ArrayList<Vector3f>();
        
        for(Unit unit : units)
        {
            unit.update(tpf);
            unitPositions.add(unit.getModel().getLocalTranslation());
        }
        
        client.send(new UnitUpdateMessage(unitPositions, isHosting));
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf)
    {
        if(name.equals("Select") && !isPressed)
        {
            CollisionResults results = new CollisionResults();
            Vector3f click3d = cam.getWorldCoordinates(inputManager.getCursorPosition().clone(), 0f).clone();
            Vector3f dir = cam.getWorldCoordinates(inputManager.getCursorPosition().clone(), 1f).subtractLocal(click3d).normalizeLocal();
            Ray ray = new Ray(click3d, dir);
            unitNode.collideWith(ray, results);
            
            boolean found = false;
            
            if(results.size() > 0)
            {
                CollisionResult closest = results.getClosestCollision();
                
                for(int i = 0; i < units.size(); i++)
                {
                    if(closest.getGeometry().getParent().getParent().getName().equals("UNIT" + i))
                    {
                        selectedUnit = units.get(i);
                        tryMoveActiveUnit();
                        found = true;
                        break;
                    }
                }
            }
            
            if(!found)
            {
                CollisionResults results2 = new CollisionResults();
                Vector3f click3d2 = cam.getWorldCoordinates(inputManager.getCursorPosition().clone(), 0f).clone();
                Vector3f dir2 = cam.getWorldCoordinates(inputManager.getCursorPosition().clone(), 1f).subtractLocal(click3d2).normalizeLocal();
                Ray ray2 = new Ray(click3d2, dir2);
                world.collideWith(ray2, results2);
                
                if(results2.size() > 0)
                {
                    CollisionResult closest = results2.getClosestCollision();
                    targetPosition = closest.getGeometry().getLocalTranslation().add(0, 2, 0);
                    System.out.println(targetPosition);
                    tryMoveActiveUnit();
                }
            }
        }
        else if(name.equals("UIMode") && !isPressed)
        {
            uiModeEnabled = !uiModeEnabled;
            
            if(uiModeEnabled)
            {
                flycam.setEnabled(false);
                inputManager.setCursorVisible(true);
            }
            else
            {
                flycam.setEnabled(true);
                inputManager.setCursorVisible(false);
            }
        }
    }
    
    //TODO: proper startpos
    public void addUnit(UnitType t)
    {
        Vector3f startPos = new Vector3f(0, 2, 0);
        
        client.send(new UnitCreatedMessage(t, startPos, isHosting));
    }
    
    private void tryMoveActiveUnit()
    {
        if(selectedUnit != null && targetPosition != null)
        {
            selectedUnit.setTargetPosition(targetPosition);
            selectedUnit = null;
            targetPosition = null;
        }
    }

    @Override
    public void clientConnected(Client c)
    {
        
    }

    @Override
    public void clientDisconnected(Client c, DisconnectInfo info)
    {
        
    }

    @Override
    public void messageReceived(Client source, final Message m)
    {
        if(m instanceof UnitUpdateMessage)
        {
            if((isHosting && !((UnitUpdateMessage) m).isPlayerA()) || (!isHosting && ((UnitUpdateMessage) m).isPlayerA()))
            {
                for(int i = 0; i < ((UnitUpdateMessage) m).getUnitPositions().size(); i++)
                {
                    final int j = i;
                    app.enqueue(new Callable()
                    {
                        @Override
                        public Object call()
                        {
                            enemyUnits.get(j).getModel().setLocalTranslation(((UnitUpdateMessage) m).getUnitPositions().get(j));
                            return null;
                        }
                    });
                }
            }
        }
        else if(m instanceof UnitCreatedMessage)
        {
            if((isHosting && !((UnitCreatedMessage) m).isPlayerA()) || (!isHosting && ((UnitCreatedMessage) m).isPlayerA()))
            {
                app.enqueue(new Callable()
                {
                    @Override
                    public Object call()
                    {
                        Unit unit = new Unit(((UnitCreatedMessage) m).getType(), unitNode, assetManager,
                                ((UnitCreatedMessage) m).getLocation(), units.size());
                        enemyUnits.add(unit);
                        return null;
                    }
                });
            }
            else
            {
                app.enqueue(new Callable()
                {
                    @Override
                    public Object call()
                    {
                        Unit unit = new Unit(((UnitCreatedMessage) m).getType(), unitNode, assetManager,
                                ((UnitCreatedMessage) m).getLocation(), units.size());
                        units.add(unit);
                        return null;
                    }
                });
            }
        }
    }
    
    @Override
    public void cleanup()
    {
        super.cleanup();
        
        client.close();
    }
}