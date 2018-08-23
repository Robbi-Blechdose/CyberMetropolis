package de.cdc.cm;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.ChaseCamera;
import com.jme3.input.controls.ActionListener;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Julius
 * 
 */
public class GameState extends GenericState implements ActionListener
{
    private ChaseCamera chaseCam;
    
    private Node world;
    private Node npcs;
    
    private DirectionalLight sun;
    private AmbientLight ambient;
    
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
        
        npcs = new Node();
        rootNode.attachChild(npcs);
        
        chaseCam = new ChaseCamera(cam, world, inputManager);
        chaseCam.setMinDistance(10f);
        chaseCam.setMaxDistance(50f);
        chaseCam.setDefaultDistance(50f);
        
        //inputManager.addMapping("Debug", new KeyTrigger(settingVariables.getKeybinding("Debug")));
        //inputManager.addListener(this, "Debug");
        
        //Test model loading
        rootNode.attachChild(assetManager.loadModel("Models/Hexagon/Hexagon.j3o"));
        
        sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        rootNode.addLight(sun);
        
        ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.1f, 0.1f, 0.1f, 0.1f));
        rootNode.addLight(ambient);
    }
    
    @Override
    public void update(float tpf)
    {
        
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf)
    {
        
    }
}