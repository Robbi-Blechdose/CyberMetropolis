package de.cdc.cm;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
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
                generateEdge();
    }
    
    public Vector3f[][] getWorldPositions()
    {
        return positions;
    }
    
    private void generateMountains()
    {
        for(int i = 0; i < 4; i++)
        {
            Node berg;
            if(i % 2 == 0)
            {
                berg = (Node) assetManager.loadModel("Models/berge/berge.j3o");
                berg.setLocalTranslation(-21.8f, 5.7f, 20+(80*i));
            }
            else
            {
                berg = (Node) assetManager.loadModel("Models/berge4/berge4.j3o");
                berg.setLocalTranslation(-22.2f, 5f, 20+(80*i));
            }
            berg.setLocalScale(10);
            world.attachChild(berg);
        }
        for(int i = 0; i < 4; i++)
        {
            Node berg;
            if(i % 2 == 0)
            {
                berg = (Node) assetManager.loadModel("Models/berge/berge.j3o");
                berg.setLocalTranslation(-21.6f+1.445f*3+162.8f*2, 5.7f, 20+(80*i));
            }
            else
            {
                berg = (Node) assetManager.loadModel("Models/berge4/berge4.j3o");
                berg.setLocalTranslation(-22f+1.445f*3+162.8f*2, 5f, 20+(80*i));
            }
            berg.setLocalScale(10);
            world.attachChild(berg);
        }
        for(int i = 0; i < 4; i++)
        {
            Node berg;
            if(i % 2 == 0)
            {
                berg = (Node) assetManager.loadModel("Models/berge/berge.j3o");
                berg.setLocalTranslation(-21.6f+1.445f*3+162.8f*2, 5.7f, 20+(80*i));
            }
            else
            {
                berg = (Node) assetManager.loadModel("Models/berge4/berge4.j3o");
                berg.setLocalTranslation(-22f+1.445f*3+162.8f*2, 5f, 20+(80*i));
            }
            berg.setLocalScale(10);
            world.attachChild(berg);
        }
        //berg.setLocalRotation(Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*90, new Vector3f(0,0,1)));
    }
    
    private void generateEdge()
    {
        
        for(int o = 1; o < 4; o++)
        {
            for(int i = 0; i < 100; i++)
            {
                Node hex;
                hex = (Node) assetManager.loadModel("Models/Hexagon/Hexagon_MountainEdge.j3o");  
                world.attachChild(hex);       
                hex.setLocalTranslation(i * 1.43f * 2, -1.2f+1.8f*o, -1.22f * 2); 
            }
        }
        for(int o = 1; o < 4; o++)
        {
            for(int i = 0; i < 100; i++)
            {
                Node hex;
                hex = (Node) assetManager.loadModel("Models/Hexagon/Hexagon_MountainEdge.j3o");  
                world.attachChild(hex);       
                hex.setLocalTranslation(i * 1.43f * 2+1.445f, -1.2f+1.8f*o, 122f * 2); 
            }
        }
        for(int o = 1; o < 4; o++)
        {
            for(int i = 0; i < 102; i++)
            {
                Node hex;
                hex = (Node) assetManager.loadModel("Models/Hexagon/Hexagon_MountainEdge.j3o");  
                world.attachChild(hex);  
                float x = 0f;
                if(i % 2 == 0)
                {
                    x = 1.445f;
                }
                hex.setLocalTranslation(-1.43f * 2-x+1.445f, -1.2f+1.8f*o, i * 1.22f * 2-2.44f); 
            }
            for(int i = 0; i < 102; i++)
            {
                Node hex;
                hex = (Node) assetManager.loadModel("Models/Hexagon/Hexagon_MountainEdge.j3o");  
                world.attachChild(hex);  
                float x = 0f;
                if(i % 2 == 0)
                {
                    x = 1.445f;
                }
                hex.setLocalTranslation(-1.43f * 2-x+1.445f*3+143f*2, -1.2f+1.8f*o, i * 1.22f * 2-2.44f); 
            }
        }
    }
    
}
//1.2888689