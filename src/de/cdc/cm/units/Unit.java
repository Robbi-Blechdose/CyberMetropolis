package de.cdc.cm.units;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
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
    private BetterCharacterControl betterCharacterControl;
    
    public Unit(UnitType t, Node unitNode, AssetManager assetManager, BulletAppState bulletAppState, Vector3f startPos)
    {
        this.unitType = t;
        
        switch(unitType)
        {
            case SOLDIER:
            {
                model = (Node) assetManager.loadModel("Models/Units/Soldier.j3o");
                unitNode.attachChild(model);
                break;
            }
        }
        
        model.setLocalTranslation(startPos);
        betterCharacterControl = new BetterCharacterControl(0.2f, 0.5f, 1f);
        model.addControl(betterCharacterControl);
        bulletAppState.getPhysicsSpace().add(betterCharacterControl);
        
        //TODO
    }
    
    //TODO
    
    public void cleanup(Node unitNode)
    {
        unitNode.detachChild(model);
    }
}