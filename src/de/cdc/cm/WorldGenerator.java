package de.cdc.cm;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
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
    
    private Vector3f[][] positions;
    
    public WorldGenerator(Node world, AssetManager assetManager)
    {
        this.world = world;
        this.assetManager = assetManager;
        this.positions = new Vector3f[100][100];
        generateWorld();
    }
    
    private void generateWorld()
    {
        OpenSimplexNoise osn = new OpenSimplexNoise();
        for(int i = 0; i < 100; i++)
        {
            for(int o = 0; o < 100; o++)
            {
                float height = (float) osn.eval(i * 0.1f, o * 0.1f)* 3f;
                Node hex;
                if (height <= -1.2f)
                {
                    hex = (Node) assetManager.loadModel("Models/Hexagon/Hexagon_Water.j3o");
                    height = -1.2f;
                }
                else if(height < -0.3f)
                {
                    hex = (Node) assetManager.loadModel("Models/Hexagon/Hexagon_DeadGrass.j3o");
                }
                else
                {
                    hex = (Node) assetManager.loadModel("Models/Hexagon/Hexagon_Grass.j3o");
                }
                world.attachChild(hex);
                float x = 0;      
                if(i % 2 == 0)
                {
                    x = 1.445f;
                }
                hex.setLocalTranslation(o * 1.43f * 2 + x, height, i * 1.22f * 2); 
                
                positions[i][o] = hex.getLocalTranslation();
            }
        }
                generateMountains();
                //generateEdge();
    }
    
    public Vector3f[][] getWorldPositions()
    {
        return positions;
    }
    
    private void generateMountains()
    {
        Node berg;
        berg = (Node) assetManager.loadModel("Models/berge.j3o");
        berg.setLocalTranslation(-22, 0, 1);
        berg.setLocalScale(10);
        world.attachChild(berg);
    }
    
    private void generateEdge()
    {
        
        for(int i = 0; i < 100; i++)
        {
            Node hex;
            hex = (Node) assetManager.loadModel("Models/Hexagon/Hexagon_MountainEdge.j3o");  
            world.attachChild(hex);       
            hex.setLocalTranslation(i * 1.43f * 2, -2, -1.22f * 2); 
        }
                
    }
    
}