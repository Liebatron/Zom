//@author alieb
package Items;

import java.awt.Color;
import java.awt.Graphics;

public class Status extends Item {
    public int turn=1;
    public int shots;
    public int shotLimit=3;
    public int win;
    public Status() {
        
    }
    public void win(int victor) {
        win=victor;
    }
    public void turn() {
        if(turn==1) {
            turn=2;
        } else {
            turn=1;
        }
        shots=0;
    }
    public void shot() {
        shots++;
        if(shots==shotLimit) {
            turn();
        }
    }
    public void step(){
    }
    public void draw(Graphics g) {
        if(turn==1) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.CYAN);
        }
        g.fillRect(0, 0, (shotLimit*12)+8, 20);
        g.setColor(Color.WHITE);     
        for(int i=shotLimit-shots-1;i>=0;i--) {
            
            g.drawOval(4+(12*i), 6, 8, 8);
        }
        
        if(win==1) {
            g.setColor(Color.RED);
            g.drawString("Player 1 wins!", 46, 10);
        } else if(win==2) {
            g.setColor(Color.CYAN);
            g.drawString("Player 2 wins!", 46, 10);
        }

    }
    
    
}
