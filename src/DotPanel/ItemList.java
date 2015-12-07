package DotPanel;

import Items.Human;
import Items.Item;
import Items.Projectile;
import Items.Status;
import Items.Zed;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import Positioning.PointMath;
import java.util.Random;

public class ItemList {
	private List<Projectile> projectileList = new ArrayList<Projectile>();
        private List<Human> humanList = new ArrayList<Human>();
        private List<Zed> zedList = new ArrayList<Zed>();
        private Status status;
        private Point humanDest;
        private Point zombieDest;
        private boolean space=false;
        private int objTimer=0;
        private boolean skip = true;
        PointMath geo = new PointMath();
        Random random = new Random();
	public ItemList() {
            humanDest=new Point(random.nextInt(800), random.nextInt(700));
            zombieDest=new Point(random.nextInt(800), random.nextInt(700));
            status = new Status();
            Human human = new Human();
            for(int i=0;i<8;i++) {
              human = new Human();
              humanList.add(human);
            }
            Point spawn = new Point(random.nextInt(800), random.nextInt(700));
            Zed zed = new Zed(spawn);
            for(int i=0;i<16;i++) {
              zed = new Zed(spawn);
              zedList.add(zed);
            }
	}
        
        public void restart() {
          humanDest=new Point(random.nextInt(800), random.nextInt(700));
          zombieDest=new Point(random.nextInt(800), random.nextInt(700));
          Human human = new Human();
          humanList = new ArrayList<Human>();
          zedList = new ArrayList<Zed>();
          projectileList = new ArrayList<Projectile>();
          for(int i=0;i<8;i++) {
            human = new Human();
            humanList.add(human);
          }
          Point spawn = new Point(random.nextInt(800), random.nextInt(700));
          Zed zed = new Zed(spawn);
          for(int i=0;i<16;i++) {
            zed = new Zed(spawn);
            zedList.add(zed);
          }
        }
        
        public void middleClick(int x, int y) {
            Point click = new Point(x, y);
            // Also doesn't do anything
        }
	public void rightClick(int x, int y) {
            Point click = new Point(x, y);
            // Also doesn't do anything
        }
        public void leftClick(int x, int y) {
            // Also doesn't do anything
        }
	public void space() {
          space=!space;
        }
        
        public void HumanCommands(boolean left, boolean right, boolean up, boolean down, boolean shift) {
          float strideFactor;
          Human human;
          Point groupCenter;
          if(shift) {
            float Xtotal=0;
            float Ytotal=0;
            for(int i=0;i<humanList.size();i++) {
              Xtotal+=humanList.get(i).xCurrent;
              Ytotal+=humanList.get(i).yCurrent;
            }
            groupCenter = new Point((int)(Xtotal/humanList.size()), (int)(Ytotal/humanList.size()));
            for(int i=0;i<humanList.size();i++) {
              humanList.get(i).cluster(groupCenter);
            }
          }
          for(int i=0;i<humanList.size();i++) {
            human=humanList.get(i);
            strideFactor = ((float)(random.nextInt(10)+90)/100);
            if(right) {
              human.xmomentum+=human.acceleration*strideFactor;
            }
            if(left) {
              human.xmomentum-=human.acceleration*strideFactor;
            }
            if(down) {
              human.ymomentum+=human.acceleration*strideFactor;
            }
            if(up) {
              human.ymomentum-=human.acceleration*strideFactor;
            }
            while(human.maxSpeed<geo.hypotenuse(human.xmomentum, human.ymomentum)) {
              human.xmomentum=human.xmomentum*0.97f;
              human.ymomentum=human.ymomentum*0.97f;
            }
          }
          if(right || left || down || up) {
            skip=true;
          } else {
            slowHumans();
          }
        }
        
        public void slowHumans() {
          if(!skip) {
            for(int i=0;i<humanList.size();i++) {
              humanList.get(i).xmomentum=humanList.get(i).xmomentum*0.95f;
              humanList.get(i).ymomentum=humanList.get(i).ymomentum*0.95f;
              if(humanList.get(i).xmomentum<0.01 && humanList.get(i).xmomentum>-0.01) {
                humanList.get(i).xmomentum=0;
              }
              if(humanList.get(i).ymomentum<0.01 && humanList.get(i).ymomentum>-0.01) {
                humanList.get(i).ymomentum=0;
              }
            }
          }
          skip=false;
        }
        
	public void draw(Graphics g) {
          objTimer++;
          if(objTimer>700) {
            humanDest=new Point(random.nextInt(800), random.nextInt(700));
            zombieDest=new Point(random.nextInt(800), random.nextInt(700));
            objTimer=0;
          }
          if(humanList.isEmpty()) {
            restart();
          }
          for(int i=0;i<projectileList.size();i++) {
            Projectile pro = projectileList.get(i);
            pro.draw(g);
            if(pro.remove || isOffScreen(pro)) {
              projectileList.remove(i);
              i--;
            }
          }
          for(int i=0;i<humanList.size();i++) {
            humanList.get(i).react(humanList, zedList, projectileList);
            for(int j=0;j<humanList.size();j++) {
              if(geo.findDistance(humanList.get(i).location, humanList.get(j).location)<7) {
                humanList.get(i).xCurrent+=geo.XDistance(humanList.get(i).location, humanList.get(j).location)/4;
                humanList.get(i).yCurrent+=geo.YDistance(humanList.get(i).location, humanList.get(j).location)/4;
              }
            }
            humanList.get(i).draw(g);
            if(space) {
              humanList.get(i).seekObjective(humanDest, zedList);
            }
          }
          boolean hit;
          for(int i=0;i<zedList.size();i++) {
            zedList.get(i).draw(g, humanList, zedList);
            hit=false;
            for(int j=0;j<zedList.size();j++) {
              if(geo.findDistance(zedList.get(i).location, zedList.get(j).location)<4) {
                zedList.get(i).xmomentum+=geo.XDistance(zedList.get(i).location, zedList.get(j).location)/24;
                zedList.get(i).ymomentum+=geo.YDistance(zedList.get(i).location, zedList.get(j).location)/24;
              }
            }
            for(int j=0;j<projectileList.size() && !hit;j++) {
              if(geo.findDistance(projectileList.get(j).location, zedList.get(i).location)<2.25 && !zedList.get(i).stunned) {
                zedList.get(i).stunned=true;
                projectileList.get(j).remove=true;
              }
            }
            if(space) {
              zedList.get(i).seekObjective(zombieDest, humanList);
            }
          }
	}
        // Collision Detection
	public int zomAt(int x, int y) {
          int onPlanet = -1;
          return onPlanet;
        }
        
        public boolean isOffScreen(Item item) {
          return (item.location.x<-15 || item.location.x>815 || item.location.y<-15 || item.location.y>715);
        }
	
}