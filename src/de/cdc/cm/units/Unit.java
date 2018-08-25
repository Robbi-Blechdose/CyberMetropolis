package de.cdc.cm.units;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.effect.ParticleEmitter;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Node;
import com.simsilica.lemur.Label;

/**
 *
 * @author Julius
 * 
 */
public class Unit
{
    public static enum UnitType
    {
        SOLDIER, SNIPER
    };
    
    private int id;
    
    private UnitType unitType;
    
    private Node model;
    
    private Label healthLabel;
    private AudioNode pootis;
    private AudioNode attackSFX;
    
    private Vector3f oldPos;
    private Vector3f targetPos;
    private boolean isMoving = false;
    
    private int health;
    private int damage;
    private float range;
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
                attackSFX = new AudioNode(assetManager, "Sounds/knife.wav", AudioData.DataType.Buffer);
                health = 100;
                damage = 40;
                range = 2.9f;
                break;
            }
            case SNIPER:
            {
                ((Node) model.getChild("Weapon")).attachChild(assetManager.loadModel("Models/Weapons/Pistol.j3o"));
                attackSFX = new AudioNode(assetManager, "Sounds/sniper.wav", AudioData.DataType.Buffer);
                health = 60;
                damage = 55;
                range = 5.8f;
                break;
            }
        }
        
        model.setLocalTranslation(startPos);
        model.setName("UNIT" + id);
        this.id = id;
        
        healthLabel = new Label("" + health);
        healthLabel.setLocalTranslation(0, 20, 0);
        healthLabel.setLocalScale(1000, 200, 1);
        model.attachChild(healthLabel);
        
        unitNode.attachChild(model);
        
        //SFX 'n' stuff
        pootis = new AudioNode(assetManager, "Sounds/Pootis.wav", AudioData.DataType.Buffer);
        pootis.setVolume(0.4f);
        model.attachChild(pootis);
        
        attackSFX.setVolume(0.4f);
        model.attachChild(attackSFX);
    }
    
    public void attackUnit(Unit enemy)
    {
        Vector3f a = enemy.getModel().getLocalTranslation().clone().setY(0);
        Vector3f b = model.getLocalTranslation().clone().setY(0);
        if(a.distance(b) <= range)
        {
            if(!enemy.damageUnit(damage))
            {
                attackSFX.play();
            }
        }
    }
    
    public void setTargetPosition(Vector3f targetPos)
    {
        Vector3f a = targetPos.clone().setY(0);
        Vector3f b = model.getLocalTranslation().clone().setY(0);
        if(a.distance(b) <= 2.9f)
        {
            this.oldPos = model.getLocalTranslation().clone();
            this.targetPos = targetPos;
            isMoving = true;
        }
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
    
    public boolean damageUnit(int dmg)
    {
        health -= dmg;
        if(health <= 0)
        {
            dead = true;
            pootis.play();
            return true;
        }
        return false;
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