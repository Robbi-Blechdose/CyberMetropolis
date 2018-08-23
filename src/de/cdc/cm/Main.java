package de.cdc.cm;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.audio.AudioListenerState;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.simsilica.lemur.event.PopupState;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jme3tools.savegame.SaveGame;

/**
 * @author Julius
 */
public class Main extends SimpleApplication
{
    
    private static final Logger logger = Logger.getLogger(de.cdc.cm.Main.class.getName());
    
    private static AppSettings appSettings;
    
    public PopupState popupState;
    
    private static final DateFormat df = new SimpleDateFormat("dd/MM/YYYY");
    public static String date = df.format(Calendar.getInstance().getTime());
    
    public static final String workingDirectory = System.getProperty("user.home");
    
    private static Settings settings;
    
    public static void main(String[] args) throws IOException
    {
        //Logging
        Logger.getGlobal().setLevel(Level.ALL);
	
	FileHandler fh;
	fh = new FileHandler(workingDirectory + "/" + Static.name + ".log", false);
	fh.setLevel(Level.ALL);
	fh.setFormatter(new de.rbgs.srcb.Main.LogFormatter());
        
	Logger globalLogger = LogManager.getLogManager().getLogger("");
	globalLogger.addHandler(fh);
        
        Logger.getLogger("com.jme3.material").setLevel(Level.SEVERE);
	
        logger.log(Level.INFO, "Loading {0}...", Static.name);
        logger.log(Level.INFO, "Version is {0}", Static.version);
        
        logger.log(Level.INFO, "Compile date is {0}", Static.getCompileDate());

        logger.log(Level.INFO, "Current date is: {0}", date);
	
        Main app = new Main(new AudioListenerState());
	app.setPauseOnLostFocus(false);
        app.setShowSettings(false);
        
        app.settings = (Settings) SaveGame.loadGame(Static.gamePath, "Settings.sav");
        if(app.settings == null)
	{
	    app.settings = new Settings();
	}
		
	//Settings
	appSettings = new AppSettings(true);
        appSettings.setUseJoysticks(true);
	appSettings.setResolution(settings.getWidth(), settings.getHeight());
	appSettings.setTitle(Static.name);
	appSettings.setVSync(settings.isVSync());
	appSettings.setSamples(settings.getAaSamples());
	appSettings.setFullscreen(settings.isFullscreen());
	
        appSettings.setIcons(new BufferedImage[]
        {
            ImageIO.read(Main.class.getResourceAsStream("/Interface/Icon16.png")),
            ImageIO.read(Main.class.getResourceAsStream("/Interface/Icon32.png")),
            ImageIO.read(Main.class.getResourceAsStream("/Interface/Icon64.png")),
            ImageIO.read(Main.class.getResourceAsStream("/Interface/Icon128.png"))
        });
	//Settings End
        
	app.setSettings(appSettings);
	app.start();
    }
    
    public Main(AppState... initialStates)
    {
        super(initialStates);
    }

    @Override
    public void simpleInitApp()
    {
        this.getStateManager().attach(new GameState());
        this.getStateManager().getState(GameState.class).setEnabled(true);
    }
    
    @Override
    public void handleError(String errMsg, Throwable t)
    {
        logger.log(Level.SEVERE, "Severe exception reached main error handler. Program terminating...");
        logger.log(Level.SEVERE, errMsg, t);
        
        if(context.getType() != JmeContext.Type.Headless)
        {
            //Just give the user directions on what to do, not the error message itself
            JFrame frame = new JFrame();
            frame.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(frame, Static.name + " has encountered a fatal error.",
                    "A team of monkeys has been dispatched to fix this.", JOptionPane.ERROR_MESSAGE);
        }
        
        stop();
    }
    
    public Settings getSettings()
    {
    	return settings;
    }
    
    @Override
    public void destroy()
    {
        super.destroy();
        logger.log(Level.INFO, "Stopping...");
    }
}