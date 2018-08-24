package de.cdc.cm;

import com.jme3.asset.AssetManager;
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
    
    public WorldGenerator(Node world, AssetManager assetManager)
    {
        this.world = world;
        this.assetManager = assetManager;
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
                float x = 0;
                boolean rotate = false;
                if(i%2 == 0)
                {
                    x = 1.445f;
                    rotate = true;
                }
                hex.setLocalTranslation(o*1.43f*2+x, 0, i*1.22f*2);
                /*if(rotate == true)
                {
                    hex.setLocalRotation(new Quaternion().fromAngles(0, 90, 0));
                }*/
            }
        }
    }
    
    public float[][] GenerateWhiteNoise(int width, int height)
    {
      Random random = new Random(0); //Seed to 0 for testing
      float[][] noise = GetEmptyArray(width, height);
 
      for (int i = 0; i < width; i++)
      {
          for (int j = 0; j < height; j++)
         {
             noise[i][j] = (float)random.NextDouble() % 1;
          }
      }
 
    return noise;
   }
}