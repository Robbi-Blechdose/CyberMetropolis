package de.cdc.cm.units;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.control.BillboardControl;
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
        SOLDIER, SNIPER, MECH
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
    
    private int startHealth;
    private int health;
    private int damage;
    private float range;
    private boolean dead = false;
    
    public Unit(UnitType t, Node unitNode, AssetManager assetManager, Vector3f startPos, int id, boolean isPlayerA)
    {
        this.unitType = t;
        if(unitType == UnitType.MECH)
        {
            if(isPlayerA)
            {
                model = (Node) assetManager.loadModel("Models/Units/Mech_blue.j3o");
            }
            else
            {
                model = (Node) assetManager.loadModel("Models/Units/Mech_red.j3o");
            }
        }
        else
        {
            if(isPlayerA)
            {
                model = (Node) assetManager.loadModel("Models/Units/Unit_blue.j3o");
            }
            else
            {
                model = (Node) assetManager.loadModel("Models/Units/Unit_red.j3o");
            }
        }
        
        switch(unitType)
        {
            case SOLDIER:
            {
                ((Node) model.getChild("Weapon")).attachChild(assetManager.loadModel("Models/Weapons/Sword.j3o"));
                attackSFX = new AudioNode(assetManager, "Sounds/knife.wav", AudioData.DataType.Buffer);
                startHealth = health = 100;
                damage = 40;
                range = 2.9f * 2;
                break;
            }
            case SNIPER:
            {
                ((Node) model.getChild("Weapon")).attachChild(assetManager.loadModel("Models/Weapons/Pistol.j3o"));
                attackSFX = new AudioNode(assetManager, "Sounds/sniper.wav", AudioData.DataType.Buffer);
                startHealth = health = 60;
                damage = 55;
                range = 2.9f * 5;
                break;
            }
            case MECH:
            {
                attackSFX = new AudioNode(assetManager, "Sounds/sniper.wav", AudioData.DataType.Buffer);
                startHealth = health = 140;
                damage = 50;
                range = 2.9f;
                break;
            }
        }
        
        model.setLocalTranslation(startPos);
        model.setName("UNIT" + id);
        this.id = id;
        
        healthLabel = new Label(health + "/" + startHealth);
        healthLabel.setLocalTranslation(0, 2.5f, 0);
        healthLabel.setLocalScale(0.016f, 0.009f, 1);
        model.attachChild(healthLabel);
        
        BillboardControl ctrl = new BillboardControl();
        ctrl.setAlignment(BillboardControl.Alignment.Camera);
        healthLabel.addControl(ctrl);
        
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
        if(a.distance(b) <= (2.9f * 5))
        {
            this.oldPos = model.getLocalTranslation().clone();
            this.targetPos = targetPos;
            isMoving = true;
        }
        
        Vector3f targetCopy = targetPos.clone().setY(oldPos.y);
        model.getChild("Cube").lookAt(targetCopy, Vector3f.UNIT_Y);
    }
    
    public void update(float tpf)
    {
        if(isMoving)
        {
            if(model.getLocalTranslation().distance(targetPos) < 0.15f)
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
        healthLabel.setText(health + "/" + startHealth);
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