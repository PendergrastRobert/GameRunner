package window;

import Objects.Block;
import framework.GameObject;
import framework.ObjectId;

import java.awt.*;
import java.util.LinkedList;

import static window.Game.songLength;

/**
 * Created by Ryan on 6/9/2016.
 */
public class Handler {
    public LinkedList<GameObject> object = new LinkedList<GameObject>();

    private GameObject tempObject;

    public void tick(){
        for (int i=0; i<object.size();i++)
        {
            tempObject = object.get(i);
            tempObject.tick(object);
        }
    }

    public void render(Graphics g){
        for (int i=0; i< object.size();i++)
        {
            tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject object){
        this.object.add(object);
    }

    public void removeObject(GameObject object){
        this.object.remove(object);
    }

    public void createLevel(){
        for(int i =0;i<(songLength*32)*16;i+=32)
            addObject(new Block(i,Game.HEIGHT-32, ObjectId.Block));
    }
}
