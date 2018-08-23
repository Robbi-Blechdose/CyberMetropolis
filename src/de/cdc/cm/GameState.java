package de.cdc.cm;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.ChaseCamera;
import com.jme3.input.controls.ActionListener;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
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
    private ChaseCamera chaseCam;
    
    private Node world;
    private RigidBodyControl worldPhysics;
    private Node unitNode;
    
    private DirectionalLight sun;
    private AmbientLight ambient;
    
    private BulletAppState bulletAppState;
    
    private List<Unit> units;
    
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
        
        chaseCam = new ChaseCamera(cam, world, inputManager);
        chaseCam.setMinDistance(10f);
        chaseCam.setMaxDistance(50f);
        chaseCam.setDefaultDistance(50f);
        
        //inputManager.addMapping("Debug", new KeyTrigger(settingVariables.getKeybinding("Debug")));
        //inputManager.addListener(this, "Debug");
        
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);
        
        //Generate world
        WorldGenerator wg = new WorldGenerator(world, assetManager);
        GeometryBatchFactory.optimize(world);
        CollisionShape worldShape = CollisionShapeFactory.createMeshShape(world);
        worldPhysics = new RigidBodyControl(worldShape, 0); //Static, thus mass = 0!
        world.addControl(worldPhysics);
        bulletAppState.getPhysicsSpace().add(worldPhysics);
        
        sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        rootNode.addLight(sun);
        
        ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.1f, 0.1f, 0.1f, 0.1f));
        rootNode.addLight(ambient);
        
        units = new ArrayList<Unit>();
    }
    
    @Override
    public void update(float tpf)
    {
        
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf)
    {
        
    }
    
    public void addUnit(UnitType t)
    {
        Unit unit = new Unit(t, unitNode, assetManager, bulletAppState, new Vector3f(0, 5f, 0));
        units.add(unit);
    }
}