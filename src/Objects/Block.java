package Objects;

import framework.GameObject;
import framework.ObjectId;

import java.awt.*;
import java.util.LinkedList;

import static window.Game.songBPM;
import static window.Game.songLength;

public class Block extends GameObject {
    public Block(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    public void tick(LinkedList<GameObject> object) {

    }
    public void render(Graphics g) {
        int totalBeats = songLength/60;
       /* for(int i=0;i<totalBeats;i++){
            if(i%20==0) {
                g.setColor(Color.cyan);
                g.drawRect((int) x, (int) y, 32, 32);
            }
            else
                g.setColor(Color.white);
                g.drawRect((int)x,(int)y,32,32);
        }*/
        g.setColor(Color.white);
        g.drawRect((int)x,(int)y,32,32);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }
}
