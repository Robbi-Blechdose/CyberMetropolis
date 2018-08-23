package de.cdc.cm;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Julius
 * 
 */
public class Static
{
    public static final String name = "Cyber Metropolis";
    public static final String version = "Pre-Alpha 0.0.0.0.0.0.1";
    
    public static final String gamePath = "CyberMetropolis/Saves";
    
    private static final DateFormat compDf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
    
    /**
     * Returns the date the game was compiled as a string
     * 
     * @return 
     */
    public static final String getCompileDate()
    {
        try
        {
            return compDf.format(new Date(Static.class.getResource("Static.class").openConnection().getLastModified()));
        }
        catch(IOException e)
        {
            return "Error";
        }
    }
}