package de.cdc.cm;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import de.cdc.cm.units.Unit;
import de.cdc.cm.units.Unit.UnitType;
import java.util.ArrayList;
import java.util.List;
import jme3tools.optimize.GeometryBatchFactory;

/**
 *
 * @author Julius
 * 
 */
public class GameState extends GenericState implements ActionListener
{
    private FlyCamAppState flycam;
    
    private Node world;
    private RigidBodyControl worldPhysics;
    private Node unitNode;
    
    private DirectionalLight sun;
    private AmbientLight ambient;
    
    private List<Unit> units;
    
    private Unit selectedUnit;
    
    public GameState()
    {
        super();
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
        
        //Register actions
        inputManager.addMapping("Select", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, "Select");
        
        //Create flycam
        flycam = new FlyCamAppState();
        stateManager.attach(flycam);
    }
    
    @Override
    public void update(float tpf)
    {
        for(Unit unit : units)
        {
            unit.update(tpf);
        }
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf)
    {
        if(name.equals("Select") && !isPressed)
        {
            CollisionResults results = new CollisionResults();
            Ray ray = new Ray(cam.getLocation(), cam.getDirection());
            unitNode.collideWith(ray, results);
            
            if(results.size() > 0)
            {
                CollisionResult closest = results.getClosestCollision();
                
                for(int i = 0; i < units.size(); i++)
                {
                    if(closest.getGeometry().getParent().getParent().getName().equals("UNIT" + i))
                    {
                        selectedUnit = units.get(i);
                        break;
                    }
                }
            }
        }
    }
    
    public void addUnit(UnitType t)
    {
        Unit unit = new Unit(t, unitNode, assetManager, new Vector3f(0, 5f, 0), units.size());
        units.add(unit);
        
//        unit.setTargetPosition(new Vector3f(20, 2, -5));
    }
}