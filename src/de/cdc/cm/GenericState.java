package de.cdc.cm;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.event.PopupState;

/**
 * Generic Game State as superclass for all other game states
 * 
 * @author Robbi Blechdose
 *
 */
public class GenericState extends AbstractAppState
{
    protected Main app;
	
    protected AssetManager assetManager;
    protected AppStateManager stateManager;
    protected InputManager inputManager;
    protected AudioRenderer audioRenderer;
	
    protected ViewPort viewport;
    protected ViewPort guiViewport;
	
    protected Node rootNode;
    protected Node guiNode;
    protected Camera cam;
	
    public GenericState(){}
	
    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {
	super.initialize(stateManager, app);
		
	this.app = (Main) app;
		
	this.assetManager = this.app.getAssetManager();
	this.stateManager = this.app.getStateManager();
	this.inputManager = this.app.getInputManager();
		
	this.audioRenderer = this.app.getAudioRenderer();
	this.viewport = this.app.getViewPort();
	this.guiViewport = this.app.getGuiViewPort();
	this.rootNode = this.app.getRootNode();
	this.guiNode = this.app.getGuiNode();
	this.cam = this.app.getCamera();
    }
	
    @Override
    public void cleanup()
    {
	super.cleanup();
    }
    
    /**
     * @return 
     */
    public Main getApp()
    {
        return app;
    }

    /**
     * Returns the game's asset manager
     * 
     * @return 
     */
    public AssetManager getAssetManager()
    {
        return assetManager;
    }

    /**
     * Returns the game's state manager
     * 
     * @return 
     */
    public AppStateManager getStateManager()
    {
        return stateManager;
    }

    public InputManager getInputManager() 
    {
        return inputManager;
    }
    
    public AudioRenderer getAudioRenderer()
    {
        return audioRenderer;
    }

    public ViewPort getViewport()
    {
        return viewport;
    }

    public ViewPort getGuiViewport()
    {
        return guiViewport;
    }

    public Node getRootNode()
    {
        return rootNode;
    }

    public Camera getCamera()
    {
        return cam;
    }
}