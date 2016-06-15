package window;

import Objects.Block;
import Objects.Player;
import framework.KeyInput;
import framework.ObjectId;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * Created by Ryan on 6/9/2016.
 */
public class Game extends Canvas implements Runnable{

    private boolean running = false;
    private Thread thread;

    Handler handler;
    Camera cam;
    public static int WIDTH, HEIGHT;
    public static int songLength;
    public static int songBPM;
    private int fps = 0;
    public void init(){
        WIDTH = getWidth();
        HEIGHT = getHeight();
        handler=  new Handler();
        cam = new Camera(0,-300);
        handler.addObject(new Player(100,100, handler, ObjectId.Player));
        handler.createLevel();
        this.addKeyListener(new KeyInput(handler));
    }

    public synchronized void start(){
        if (running)
            return;
        running=true;
        thread=new Thread(this);
        thread.start();
    }

    public void run() {
        init();
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void tick(){
        handler.tick();
        for(int i=0;i<handler.object.size();i++){
            if(handler.object.get(i).getId()==ObjectId.Player)
                cam.tick(handler.object.get(i));
        }
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs==null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        ////////
        //draw here
        g.setColor(Color.black);
        g.fillRect(0,0,getWidth(),getHeight());
        //beginning of camera
        g2d.translate(cam.getX(),cam.getY());

        handler.render(g);

        g.setColor(Color.red);
        g.setFont(new Font("Dialog", Font.BOLD, 18));
        g.drawString(""+fps,5,5);


        //end of camera
        g2d.translate(-cam.getX(),-cam.getY());

        ////////
        g.dispose();
        bs.show();
    }

    public static void main(String args[]) throws InterruptedException {
        Scanner length =  new Scanner(System.in);
        Scanner bpmInput = new Scanner(System.in);
        System.out.println("Enter Song Length in Seconds");
        songLength = length.nextInt();
        Thread.sleep(40);
   //     System.out.println("Enter Song BPM");
   //     songBPM = bpmInput.nextInt();
        System.out.println("Creating Level of Length " + songLength);
        Thread.sleep(40);

        new Window(800,600,"New Wave Runner", new Game());
        int i,j;
        for(j=0;j>=0;j++)
            for(i=0;i<60;i++) {
                System.out.println(j + ": " + i);
                if(i>songLength+1)
                    exit(1);
                try{
                    Thread.sleep(1000);}
                catch(InterruptedException ie){
                    System.out.println(ie.getMessage());
                }
            }
    }
}
