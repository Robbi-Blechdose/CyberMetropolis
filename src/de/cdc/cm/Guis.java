package de.cdc.cm;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.TextField;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.style.BaseStyles;
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
    private Node menuNode;
    
    private TextField serverIp;
    
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
        
        menuNode = new Node();
        
        Panel menuBG = new Panel();
        menuBG.setPreferredSize(new Vector3f(app.getSettings().getWidth(), app.getSettings().getHeight(), 0));
        menuBG.setLocalTranslation(0, app.getSettings().getHeight(), 0);
        menuBG.setBackground(new QuadBackgroundComponent(app.getAssetManager().loadTexture("Interface/MenuBackground.png")));
        menuNode.attachChild(menuBG);
        
        Button join = new Button("Join Game");
        join.setName("JoinGame");
        join.setFontSize(60f);
        join.setColor(new ColorRGBA(0f, 0f, 1f, 1f));
        join.setHighlightColor(ColorRGBA.Yellow);
        join.setPreferredSize(new Vector3f(app.getSettings().getWidth() / 4, app.getSettings().getHeight() / 9, 1));
        join.setLocalTranslation(app.getSettings().getWidth() / 2.6f, app.getSettings().getHeight() / 1.46f, 1);
        join.addClickCommands(new Command<Button>()
        {
            @Override
            public void execute(Button source)
            {
                app.getStateManager().getState(MenuState.class).join();
            }
        });
        menuNode.attachChild(join);
        
        serverIp = new TextField("localhost");
        serverIp.setFontSize(17f);
        serverIp.setColor(ColorRGBA.Red);
        serverIp.setPreferredSize(new Vector3f(app.getSettings().getWidth() / 6, app.getSettings().getHeight() / 28, 1));
        serverIp.setLocalTranslation(app.getSettings().getWidth() / 1.24f, app.getSettings().getHeight() / 1.15f, 1);
        menuNode.attachChild(serverIp);
        
        Button host = new Button("Host Game");
        host.setName("HostGame");
        host.setFontSize(60f);
        host.setColor(new ColorRGBA(0f, 0f, 1f, 1f));
        host.setHighlightColor(ColorRGBA.Yellow);
        host.setPreferredSize(new Vector3f(app.getSettings().getWidth() / 4, app.getSettings().getHeight() / 9, 1));
        host.setLocalTranslation((app.getSettings().getWidth() / 2) - (host.getPreferredSize().x / 2),
                app.getSettings().getHeight() / 2.2f, 1);
        host.addClickCommands(new Command<Button>()
        {
            @Override
            public void execute(Button source)
            {
                app.getStateManager().getState(MenuState.class).host();
            }
        });
        menuNode.attachChild(host);    
        Button quit = new Button("Quit Game");
        quit.setName("QuitGame");
        quit.setFontSize(60f);
        quit.setColor(new ColorRGBA(0f, 0f, 1f, 1f));
        quit.setHighlightColor(ColorRGBA.Yellow);
        quit.setPreferredSize(new Vector3f(app.getSettings().getWidth() / 4, app.getSettings().getHeight() / 8, 1));
        quit.setLocalTranslation((app.getSettings().getWidth() / 2) - (host.getPreferredSize().x / 2),
                app.getSettings().getHeight() / 4.4f, 1);
        quit.addClickCommands(new Command<Button>()
        {
            @Override
            public void execute(Button source)
            {
                app.getStateManager().getState(MenuState.class).quit();
            }
        });
        menuNode.attachChild(quit);
    }
    
    public void gameGui(boolean display)
    {
        if(display)
        {
            guiNode.attachChild(gameNode);
        }
        else
        {
            guiNode.detachChild(gameNode);
        }
    }
    
    public void menuGui(boolean display)
    {
        if(display)
        {
            guiNode.attachChild(menuNode);
        }
        else
        {
            guiNode.detachChild(menuNode);
        }
    }
    
    public String getServerIp()
    {
        return serverIp.getText();
    }
}