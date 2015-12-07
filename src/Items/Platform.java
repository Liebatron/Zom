//@author alieb
package Items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Platform extends Item {
    public Point Left;
    public Point Right;
    public int ground;
    public Platform(Point Left, int size) {
      this.Left=Left;
      this.Right=new Point(Left.x+size, Left.y);
      ground=Left.y;
    }
    public void draw(Graphics g) {
      g.setColor(Color.BLACK);
      g.drawLine(Left.x, Left.y, Right.x, Right.y);
    }
}
