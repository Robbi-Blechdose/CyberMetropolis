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
//        width = 1280;
//        height = 720;
        width = 640;
        height = 360;
        vSync = true;
        aaSamples = 2;
        fullscreen = false;
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