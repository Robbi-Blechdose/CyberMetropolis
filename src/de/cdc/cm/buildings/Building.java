package de.cdc.cm.buildings;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Julius
 * 
 */
public class Building
{
    public static enum BuildingType
    {
        HEADQUARTERS
    };
    
    private Node model;
    
    public Building(Node buildingNode, AssetManager assetManager, boolean isPlayerA, BuildingType type, Vector3f position)
    {
        switch(type)
        {
            case HEADQUARTERS:
            {
                if(isPlayerA)
                {
                    model = (Node) assetManager.loadModel("Models/Buildings/HQ_blue.j3o");
                }
                else
                {
                    model = (Node) assetManager.loadModel("Models/Buildings/HQ_red.j3o");
                }
                break;
            }
        }
        
        model.setLocalTranslation(position);
        buildingNode.attachChild(model);
    }
    
    public void cleanup(Node buildingNode)
    {
        buildingNode.detachChild(model);
    }
    
    public Vector3f getPosition()
    {
        return model.getLocalTranslation();
    }
}