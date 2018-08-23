package de.cdc.cm;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 *
 * @author Julius + Joscha
 * 
 */
public class WorldGenerator
{
    private AssetManager assetManager;
    private Node world;
    
    public WorldGenerator(Node world)
    {
        this.world = world;
        generateWorld();
    }
    
    private void generateWorld()
    {
        for(int i = 0; i < 100; i++)
        {
            for(int o = 0; o< 100; o++)
            {
                Node hex = (Node) assetManager.loadModel("Models/Hexagon/Hexagon.j3o");
                world.attachChild(hex);
                hex.setLocalTranslation(i, 0, o);
            }
        }
    }
}