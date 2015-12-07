//@author alieb
package Items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Spot extends Item {
    public boolean hit = false;
    private Planet planet;
    private float spinSteps=0;
    public float spin;
    public Color color = Color.GREEN;
    Random random = new Random();
    public Spot(Planet creator) {
        planet = creator;
        spin = (random.nextFloat()*10)-5 ;
        spinSteps = random.nextFloat()*360;
    }
    public void hit() {
        color = Color.ORANGE;
    }
    public void step() {
        spinSteps+=spin;
        double angle = Math.toRadians(spinSteps/4);
        double x = Math.cos(angle) * planet.size/2 + planet.location.x;
        double y = Math.sin(angle) * planet.size/2 + planet.location.y;
        int intX = (int)x;
        int intY = (int)y;
        location=new Point(intX, intY);
        drawLocation=new Point(intX-3, intY-3);
    }
    public void draw(Graphics g) {
        step();
        g.setColor(color);
        g.fillOval(drawLocation.x, drawLocation.y, 6, 6);
    }
    
}
