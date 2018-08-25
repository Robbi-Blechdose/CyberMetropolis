package de.cdc.cm.units;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
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
    
    private int id;
    
    private UnitType unitType;
    private Node model;
    
    private Vector3f oldPos;
    private Vector3f targetPos;
    private boolean isMoving = false;
    
    private int health;
    private int damage;
    private boolean dead = false;
    
    public Unit(UnitType t, Node unitNode, AssetManager assetManager, Vector3f startPos, int id, boolean isPlayerA)
    {
        this.unitType = t;
        if(isPlayerA)
        {
            model = (Node) assetManager.loadModel("Models/Units/Unit_blue.j3o");
        }
        else
        {
            model = (Node) assetManager.loadModel("Models/Units/Unit_red.j3o");
        }
        
        switch(unitType)
        {
            case SOLDIER:
            {
                ((Node) model.getChild("Weapon")).attachChild(assetManager.loadModel("Models/Weapons/Sword.j3o"));
                health = 100;
                damage = 40;
                break;
            }
        }
        
        model.setLocalTranslation(startPos);
        model.setName("UNIT" + id);
        this.id = id;
        
        unitNode.attachChild(model);
    }
    
    public void attackUnit(Unit enemy)
    {
        setTargetPosition(enemy.getModel().getLocalTranslation());
        enemy.damageUnit(damage);
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
    
    public void damageUnit(int dmg)
    {
        health -= dmg;
        if(health <= 0)
        {
            dead = true;
        }
    }
    
    public void dontDead()
    {
        dead = false;
    }
    
    public void cleanup(Node unitNode)
    {
        unitNode.detachChild(model);
    }
    
    public Node getModel()
    {
        return model;
    }
    
    public boolean isDead()
    {
        return dead;
    }
    
    public int getId()
    {
        return id;
    }
}