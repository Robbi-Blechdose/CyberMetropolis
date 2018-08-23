package de.cdc.cm;

import com.jme3.input.controls.ActionListener;

/**
 *
 * @author Julius
 * 
 */
public class GameState extends GenericState implements ActionListener
{
    public GameState()
    {
        super();
        
        //inputManager.addMapping("Debug", new KeyTrigger(settingVariables.getKeybinding("Debug")));
        //inputManager.addListener(this, "Debug");
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