package Objects;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Ryan on 6/10/2016.
 */
public class Player extends GameObject{
    private float width =32,height =64;
    private float gravity = 0.5f;
    private final float MAX_SPEED = 10;
    private Handler handler;
    public Player(float x, float y, Handler handler, ObjectId id) {
        super(x, y, id);
        this.handler=handler;
    }

    public void tick(LinkedList<GameObject> object) {
        x+=velX;
        y+=velY;

        //adds velocity to the right
        velX=8f;

        if (falling||jumping) {
            velY += gravity;
            if (velY > MAX_SPEED)
                velY = MAX_SPEED;
        }
        collision(object);
    }


    private void collision(LinkedList<GameObject> object){
        for(int i=0;i<handler.object.size();i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId()==ObjectId.Block){
                if(getBounds().intersects(tempObject.getBounds())){
                    y=tempObject.getY()-height;
                    velY=0;
                    falling=false;
                    jumping=false;
                }
                else
                    falling=true;
            }
        }

    }
    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect((int)x,(int)y,(int)width,(int)height);

        Graphics2D g2d = (Graphics2D)g;
        g.setColor(Color.red);
        g2d.draw(getBounds());
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsLeft());
        g2d.draw(getBoundsTop());
    }

    public Rectangle getBounds() {
        return new Rectangle((int)((int)x+(width/2)-((width/2)/2)),(int)((int)y+(height/2)),(int)width/2,(int)height/2);
    }
    public Rectangle getBoundsRight() {
        return new Rectangle((int) ((int)x+width-5),(int)y+5,5,(int)height-10);
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle((int)x,(int)y,5,(int)height-10);
    }
    public Rectangle getBoundsTop() {
        return new Rectangle((int)((int)x+(width/2)-((width/2)/2)),(int)y,(int)width/2,(int)height/2);
    }
}
