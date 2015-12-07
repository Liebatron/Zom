package Items;

import Positioning.PointMath;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.Random;

public class Planet extends Item {
	public int distance;
	public int size;
        public float well;
	public float steps=0;
	public float speed;
        public boolean armed = false;
        public boolean canArm= false;
        public boolean spotted = false;
        public boolean hit = false;
        public Spot spot;
        public Color color = Color.GRAY;
        public PointMath geo = new PointMath();
        public Planet(boolean first) {
            color = Color.gray;
            location=origin;
            speed=0;
            well=0;
            size=6;
            distance=0;
            armed=false;
            canArm=false;
            drawLocation = new Point(origin.x-3, origin.y-3);
        }
	public Planet(List<Planet> list) {
		Random random = new Random();
		int sizeLast=0;
		if(list.size()>0) {
                    distance=list.get(list.size()-1).distance;
                    sizeLast=list.get(list.size()-1).size*3/4;
		} else {
                    distance = 10;
                }
		size = random.nextInt(((list.size()+1)*10)+list.size()*10)+10;
                distance += sizeLast+size;
		location = new Point(origin.x+distance, origin.y);
                drawLocation = new Point(location.x-(size/2), location.y-(size/2));
                well = size*4;
		speed = (float)3*(((float)size*(float)size)/((float)distance*(float)distance));
                if(random.nextInt(10)>2) {
                    spot = new Spot(this);
                    spotted=true;
                    canArm=true;
                }
	}
        public void hit(Point hitPoint) {
            if(spotted && geo.findDistance(hitPoint, spot.location)<12) {
                spot.hit();
                hit=true;
            }
        }
	public void step() {
		steps+=speed;
		double angle = Math.toRadians(steps/4);
		double x = Math.cos(angle) * distance + origin.x;
		double y = Math.sin(angle) * distance + origin.y;
		int intX = (int)x;
		int intY = (int)y;
		location.move(intX, intY);
                drawLocation.move(intX-(size/2), intY-(size/2));
	}
	public void draw(Graphics g) {
                step();
		//g.setColor(Color.LIGHT_GRAY);
		//g.drawOval(origin.x-distance, origin.y-distance, distance*2, distance*2);
                g.setColor(color);
		g.fillOval(drawLocation.x, drawLocation.y, size, size);
                if(spotted) {spot.draw(g);}
	}
	
	
}
