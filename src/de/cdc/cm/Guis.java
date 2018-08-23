package de.cdc.cm;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;
import de.cdc.cm.units.Unit;
import de.cdc.cm.units.Unit.UnitType;

/**
 *
 * @author Julius
 * 
 */
public class Guis
{
    private Main app;
    
    private Node guiNode;
    private Node gameNode;
    
    public Guis(Main app)
    {
        this.app = app;
        this.guiNode = app.getGuiNode();
        
        GuiGlobals.initialize(app);
        BaseStyles.loadGlassStyle();
        
        initGuis();
    }
    
    private void initGuis()
    {
        gameNode = new Node();
        guiNode.attachChild(gameNode);
        
        Button addUnit = new Button("Create Unit");
        addUnit.setName("addUnit");
        addUnit.setPreferredSize(new Vector3f(app.getSettings().getWidth() / 16, app.getSettings().getHeight() / 9, 1));
        addUnit.setLocalTranslation(0, app.getSettings().getHeight(), 0);
        addUnit.addClickCommands(new Command<Button>()
        {
            @Override
            public void execute(Button source)
            {
                app.getStateManager().getState(GameState.class).addUnit(UnitType.SOLDIER);
            }
        });
        gameNode.attachChild(addUnit);
    }
}