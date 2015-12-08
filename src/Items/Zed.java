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

public class Zed extends Item {
    private BufferedImage img = ImageReader.getImage("PinkiePieSmall");
    public float xmomentum=0;
    public float ymomentum=0;
    public float xCurrent; 
    public float yCurrent;
    public Color dotColor;
    public int respawn=0;
    public float acceleration;
    public float maxSpeed;
    public int tagTimer=0;
    public boolean stunned=false;
    private Random random = new Random();
    private PointMath geo = new PointMath();
  public Zed(Point spawnArea) {
    float colorFloat=((float)random.nextInt(60)+40f)/100f;
    dotColor = new Color(colorFloat, colorFloat, colorFloat);
    xCurrent = spawnArea.x+random.nextInt(40)-20;
    yCurrent = spawnArea.y+random.nextInt(40)-20;
    location = new Point((int)xCurrent, (int)yCurrent);
    drawLocation = new Point(location.x-2, location.y-2);
    acceleration=0.05f+((float)random.nextInt(2)/95f);
    maxSpeed=((float)random.nextInt(30)+110)/100f;
  }
  
  public void seekObjective(Point objective, List<Human> humans) {
    if(!stunned) {
        float totalSpeed=Math.abs(xmomentum)+Math.abs(ymomentum);
        boolean seesHuman=false;
        for(int i=0;i<humans.size();i++) {
          if(geo.findDistance(location, humans.get(i).location)<70) {
            seesHuman=true;
            xmomentum=xmomentum*0.98f;
            ymomentum=ymomentum*0.98f;
            totalSpeed=Math.abs(xmomentum)+Math.abs(ymomentum);
            if(totalSpeed<maxSpeed) {
              xmomentum-=(0.002*geo.XDistance(location, humans.get(i).location));
              ymomentum-=(0.002*geo.YDistance(location, humans.get(i).location));
            }
          }
        }
        if(!seesHuman && geo.findDistance(location, objective)>50 && totalSpeed<maxSpeed) {
          xmomentum-=(0.0001*geo.XDistance(location, objective));
          ymomentum-=(0.0001*geo.YDistance(location, objective));
        }
    }
  }
  
  public void step(List<Human> humans, List<Zed> zeds) {
    if(tagTimer>0) {
      tagTimer--;
    }
    if(stunned) {
      respawn++;
      if(respawn>=150) {
        stunned=false;
        respawn=0;
      }
      xmomentum=0;
      ymomentum=0;
    } else {
      xCurrent+=xmomentum;
      yCurrent+=ymomentum;
      location=new Point((int)xCurrent, (int)yCurrent);
      drawLocation.move(location.x-2, location.y-2);
      int human=findHuman(humans);
      if(human==-1) {
        cluster(zeds);
        if(xmomentum+ymomentum!=0) {
          xmomentum=xmomentum*0.985f;
          ymomentum=ymomentum*0.985f;
          if(xmomentum<0.0001 && xmomentum>-0.0001) {
            xmomentum=0;
          }
          if(ymomentum<0.0001 && ymomentum>-0.0001) {
            ymomentum=0;
          }
        }
      } else {
        float totalSpeed=Math.abs(xmomentum)+Math.abs(ymomentum);
        if(totalSpeed<maxSpeed) {
          xmomentum-=(0.002*geo.XDistance(location, humans.get(human).location));
          ymomentum-=(0.002*geo.YDistance(location, humans.get(human).location));
        }
      }
      for(int i=0;i<humans.size() && tagTimer==0;i++) {
        if(geo.findDistance(location, humans.get(i).location)<=4) {
          Point deathPoint = humans.get(i).location;
          humans.remove(i);
          Zed zed = new Zed(deathPoint);
          zed.stunned=true;
          zeds.add(zed);
          tagTimer=40;
        }
      }
    }
    
  }
  
  public int findHuman(List<Human> humans) {
    int human=-1;
    for(int i=0;i<humans.size() && human==-1;i++) {
      if(geo.findDistance(location, humans.get(i).location)<70) {
        human=i;
      }
    }
    return human;
  }
  
  public void cluster(List<Zed> zeds) {
    float Xtotal=0;
    float Ytotal=0;
    for(int i=0;i<zeds.size();i++) {
      Xtotal+=zeds.get(i).xCurrent;
      Ytotal+=zeds.get(i).yCurrent;
    }
    Point groupCenter = new Point((int)(Xtotal/zeds.size()), (int)(Ytotal/zeds.size()));
    float totalSpeed=Math.abs(xmomentum)+Math.abs(ymomentum);
    if(geo.findDistance(location, groupCenter)>40 && totalSpeed<maxSpeed) {
      xmomentum-=(0.0004*geo.XDistance(location, groupCenter));
      ymomentum-=(0.0004*geo.YDistance(location, groupCenter));
    }
  }
  
  public void draw(Graphics g, List<Human> humans, List<Zed> zeds) {
    step(humans, zeds); 
    if(stunned) {
      g.setColor(Color.PINK);
    } else {
      g.setColor(dotColor);
    }
    g.fillOval(drawLocation.x, drawLocation.y, 5, 5);
    g.setColor(Color.BLACK);
    g.drawOval(drawLocation.x, drawLocation.y, 5, 5);
  }
        
}