package de.cdc.cm;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

/**
 *
 * @author Julius
 * 
 */
public class MenuState extends GenericState
{
    public MenuState() {}
    
    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {
	super.initialize(stateManager, app);
        
        guis.menuGui(true);
    }
    
    public void join()
    {
        guis.menuGui(false);
        getStateManager().attach(new GameState(false));
    }
    
    public void host()
    {
        guis.menuGui(false);
        getStateManager().attach(new GameState(true));
    }
    
    public void quit()
    {
        System.exit(0);
    }
}