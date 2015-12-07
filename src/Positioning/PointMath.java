//@author alieb
package Positioning;

import Items.Launcher;
import Items.Projectile;
import java.awt.Point;
import java.util.Random;

public class PointMath {
    private Random random = new Random();
    public double hypotenuse(float a, float b) {
        a=Math.abs(a);
        b=Math.abs(b);
      return Math.sqrt((a*a)+(b*b));
    }
    public float findGravity(float distance, int size) {
        return size/distance;
    }
    public float findDistance(Point a, Point b) {
        float closex = Math.abs(a.x)-Math.abs(b.x);
        float closey = Math.abs(a.y)-Math.abs(b.y);
        float apart = Math.round((float)Math.sqrt((closex*closex)+(closey*closey)));
        return apart;
    }
    
    public Point getSeparation(Point a, Point b) {
        return new Point((a.x-b.x)*-1, (a.y-b.y)*-1);
    }
    
    public float absXDistance(Point a, Point b) {
        return Math.abs(a.x)-Math.abs(b.x);
    }
    public float absYDistance(Point a, Point b) {
        return Math.abs(a.y)-Math.abs(b.y);
    }
    public float XDistance(Point a, Point b) {
        return a.x-b.x;
    }
    public float YDistance(Point a, Point b) {
        return a.y-b.y;
    }
}
