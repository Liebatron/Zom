//@author alieb
package Items;

import DotPanel.ImageReader;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class You extends Item {
    private BufferedImage img = ImageReader.getImage("PinkiePieSmall");
    public float xmomentum=0;
    public float ymomentum=0;
    public float xCurrent; 
    public float yCurrent;
    public float timeFalling=0;
    public Color transparent = new Color(0f, 0f, 0f, 0f);
    public float ground = 600f;
    public int respawn=0;
  public You() {
    location = origin;
    xCurrent = origin.x;
    yCurrent = origin.y;
  }
  
  public void jump() {
    if(yCurrent==ground) {
      ymomentum-=12;
    }
  }
  
  public void step() {
    if(yCurrent<ground) {
      if(yCurrent+ymomentum >= ground) {
        timeFalling=1;
        yCurrent=ground;
        ymomentum=0;
      } else {  
      timeFalling+=0.01 ;
      ymomentum+=timeFalling*timeFalling/2;
      }
    }
    xCurrent+=xmomentum;
    yCurrent+=ymomentum;
    location=new Point((int)xCurrent, (int)yCurrent);
    System.out.println(location.y);
    if(location.y==800) {
      respawn+=1;
      if(respawn == 120) {
        xCurrent = origin.x;
        yCurrent = origin.y;
        respawn=0;
      }
    }
  }
  
  public void draw(Graphics g) {
    step(); 
    g.drawImage(img, location.x-35, location.y-35, transparent, null);
  }
        
}
