package de.cdc.cm.units;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Julius
 * 
 */
public class Unit
{
    public static enum UnitType
    {
        SOLDIER
    };
    
    private UnitType unitType;
    private Node model;
    
    private Vector3f oldPos;
    private Vector3f targetPos;
    private boolean isMoving = false;
    
    public Unit(UnitType t, Node unitNode, AssetManager assetManager, Vector3f startPos, int index)
    {
        this.unitType = t;
        
        switch(unitType)
        {
            case SOLDIER:
            {
                model = (Node) assetManager.loadModel("Models/Units/Soldier.j3o");
                break;
            }
        }
        
        model.setLocalTranslation(startPos);
        model.setName("UNIT" + index);
        
        unitNode.attachChild(model);
    }
    
    public void setTargetPosition(Vector3f targetPos)
    {
        this.oldPos = model.getLocalTranslation().clone();
        this.targetPos = targetPos;
        isMoving = true;
    }
    
    public void update(float tpf)
    {
        if(isMoving)
        {
            if(model.getLocalTranslation().distance(targetPos) < 0.1f)
            {
                isMoving = false;
            }
            else
            {
                model.setLocalTranslation(targetPos.subtract(oldPos).normalize().mult(tpf).mult(5).add(model.getLocalTranslation()));
            }
        }
    }
    
    public void cleanup(Node unitNode)
    {
        unitNode.detachChild(model);
    }
    
    public Node getModel()
    {
        return model;
    }
}