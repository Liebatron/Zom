//@author alieb
package Items;

import DotPanel.ImageReader;
import Positioning.PointMath;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public class Human extends Item {
    private BufferedImage img = ImageReader.getImage("PinkiePieSmall");
    public float xmomentum=0;
    public float ymomentum=0;
    public float xCurrent; 
    public float yCurrent;
    public Color dotColor;
    public int respawn=0;
    public boolean firedShot=false;
    public float acceleration;
    public float maxSpeed;
    public Launcher weapon;
    private Random random = new Random();
    private PointMath geo = new PointMath();
  public Human() {
    dotColor = new Color((float)random.nextInt(10)/100f, random.nextFloat(), random.nextFloat());
    xCurrent = origin.x+random.nextInt(20)-10;
    yCurrent = origin.y+random.nextInt(20)-10;
    location = new Point((int)xCurrent, (int)yCurrent);
    drawLocation = new Point(location.x-2, location.y-2);
    weapon = new Launcher();
    acceleration=0.05f+((float)random.nextInt(2)/95f);
    maxSpeed=((float)random.nextInt(30)+110)/100f;
  }
  
  public void react(List<Human> humans, List<Zed> zeds, List<Projectile> shots) {
    locateGroup(humans);
    attemptShot(zeds, shots);
  }
  
  public void seekObjective(Point objective, List<Zed> zeds) {
    boolean seesZed=false;
    float totalSpeed=Math.abs(xmomentum)+Math.abs(ymomentum);
    for(int i=0;i<zeds.size();i++) {
      if(geo.findDistance(location, zeds.get(i).location)<25 && totalSpeed<maxSpeed) {
        xmomentum+=0.002*geo.XDistance(location, zeds.get(i).location);
        ymomentum+=0.002*geo.YDistance(location, zeds.get(i).location);
        seesZed=true;
      }
    } 
    if(!seesZed && geo.findDistance(location, objective)>25 && totalSpeed<maxSpeed) {
      xmomentum-=(0.002*geo.XDistance(location, objective));
      ymomentum-=(0.002*geo.YDistance(location, objective));
    }
  }
  
  public void attemptShot(List<Zed> zeds, List<Projectile> shots) {
    for(int i=0;i<zeds.size();i++) {
      if(geo.findDistance(location, zeds.get(i).location)<70 && !zeds.get(i).stunned) {
        if(true) { // If shift not held, future planning
          xmomentum=xmomentum*0.99f;
          ymomentum=ymomentum*0.99f;
        }
        if(weapon.cooldownTimer==0) {
          shoot(zeds.get(i).location, shots);
        }
      }
    }
  }
  
  public void locateGroup(List<Human> humans) {
    boolean nearHuman=false;
    for(int i=0;i<humans.size() && !nearHuman;i++) {
      if(geo.findDistance(drawLocation, humans.get(i).drawLocation)<20 && !drawLocation.equals(humans.get(i).drawLocation)) {
        nearHuman=true;
      }
    }
    if(!nearHuman) {
      cluster(humans);
    }
  }
  
  public void cluster(List<Human> humans) {
    float Xtotal=0;
    float Ytotal=0;
    for(int i=0;i<humans.size();i++) {
      Xtotal+=humans.get(i).xCurrent;
      Ytotal+=humans.get(i).yCurrent;
    }
    Point groupCenter = new Point((int)(Xtotal/humans.size()), (int)(Ytotal/humans.size()));
    float totalSpeed=Math.abs(xmomentum)+Math.abs(ymomentum);
    if(geo.findDistance(location, groupCenter)>20 && totalSpeed<maxSpeed) {
      xmomentum-=(0.003*geo.XDistance(location, groupCenter));
      ymomentum-=(0.003*geo.YDistance(location, groupCenter));
    }
  }
  public void cluster(Point groupCenter) {
    float totalSpeed=Math.abs(xmomentum)+Math.abs(ymomentum);
    if(geo.findDistance(location, groupCenter)>15 && totalSpeed<maxSpeed) {
      xmomentum-=(0.003*geo.XDistance(location, groupCenter));
      ymomentum-=(0.003*geo.YDistance(location, groupCenter));
    }
  }
  
  public void shoot(Point target, List<Projectile> shots) {
    firedShot=true;
    weapon.cooldownTimer=weapon.cooldown;
    shots.add(new Projectile((weapon.speed), weapon.angleOffset, new Point((int)xCurrent, (int)yCurrent), target));
  }
  
  public void step() {
    xCurrent+=xmomentum;
    yCurrent+=ymomentum;
    location=new Point((int)xCurrent, (int)yCurrent);
    drawLocation.move(location.x-2, location.y-2);
    if(weapon.cooldownTimer!=0) {
      weapon.cooldownTimer=weapon.cooldownTimer-1;
    }
  }
  
  public void draw(Graphics g) {
    step(); 
    g.setColor(dotColor);
    g.fillOval(drawLocation.x, drawLocation.y, 5, 5);
    g.setColor(Color.BLACK);
    g.drawOval(drawLocation.x, drawLocation.y, 5, 5);
    //g.drawImage(img, location.x-35, location.y-35, transparent, null);
  }
        
}