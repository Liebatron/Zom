//@author alieb
package Items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.Random;
import Positioning.PointMath;

public class Projectile extends Item {
    public Color color = Color.black;
    public Color fillColor = Color.WHITE;
    public Color transparent = new Color(0f, 0f, 0f, 0f);
    public float xmomentum;
    public float ymomentum;
    public float xCurrent;
    public float yCurrent;
    public boolean remove=false;
    private PointMath math = new PointMath();
    private Random random = new Random();
    
    public Projectile(int x, int y, float xmom, float ymom) {
      xmomentum= xmom;
      ymomentum= ymom;
      location = new Point(x, y);
      drawLocation = new Point(x-1, y-1);
      xCurrent=(float)location.x;
      yCurrent=(float)location.y;
      color = Color.red;
      fillColor = Color.ORANGE;
    }
    public Projectile(int x, int y) {
      xmomentum= random.nextInt(40)-20;
      ymomentum= random.nextInt(40)-20;
      xCurrent=(float)location.x;
      yCurrent=(float)location.y;
      location = new Point(x, y);
      drawLocation = new Point(x-1, y-1);
    }
    public Projectile(float speed, int angleOffset, Point launch, Point target) {
      location=new Point(launch.x, launch.y); // Launched By Human
      drawLocation=new Point(launch.y-1, launch.y-1);
      xCurrent=(float)location.x;
      yCurrent=(float)location.y;
      Point direction = math.getSeparation(launch, target);
      int totalSeparation=Math.abs(direction.x)+Math.abs(direction.y);
      xmomentum=(((float)direction.x/(float)totalSeparation)*speed)+((random.nextInt(angleOffset)-(angleOffset/2))/150);
      ymomentum=(((float)direction.y/(float)totalSeparation)*speed)+((random.nextInt(angleOffset)-(angleOffset/2))/150);
      color = Color.red;
      fillColor = Color.ORANGE;
    }
    
    public void step() {
      xCurrent = xCurrent + xmomentum;
      yCurrent = yCurrent + ymomentum;
      location.move((int)xCurrent, (int)yCurrent);
      drawLocation.move(location.x-1, location.y-1);
      //xmomentum=xmomentum*0.999f;
      //ymomentum=ymomentum*0.999f;
      //if(math.hypotenuse(xmomentum, ymomentum)>=0.1) {
      //  remove=true;
  //}
    }
    public void draw(Graphics g) {
      step();
      g.setColor(fillColor);
      g.fillOval(drawLocation.x, drawLocation.y, 3, 3);
      g.setColor(color);
      g.drawOval(drawLocation.x, drawLocation.y, 3, 3);
    }
}
