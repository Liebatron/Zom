//@author alieb
package Items;

import Positioning.PointMath;
import java.awt.Point;
import java.util.Random;

public class Launcher {
  private Random random = new Random();
  private PointMath geo = new PointMath();
  public float speed;
  public int angleOffset;
  public int cooldown;
  public int cooldownTimer=0;
  public Launcher() {
    speed=(random.nextFloat()/2f)+1f;
    cooldown=random.nextInt(20)+30;
    angleOffset=random.nextInt(4)+1;
  }
  
}