package de.cdc.cm;

/**
 *
 * @author Julius
 * 
 */
public class Settings
{
    private int width;
    private int height;
    
    private boolean vSync;
    private int aaSamples;
    private boolean fullscreen;
    
    public Settings()
    {
//        width = 640;
//        height = 360;
//        fullscreen = false;
        vSync = true;
        aaSamples = 2;
        width = 1920;
        height = 1080;
        fullscreen = true;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public boolean isVSync()
    {
        return vSync;
    }

    public boolean isvSync()
    {
        return vSync;
    }

    public int getAaSamples()
    {
        return aaSamples;
    }

    public boolean isFullscreen()
    {
        return fullscreen;
    }
}