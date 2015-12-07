package DotPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class DisplayPanel extends JPanel {
    private Timer timer = null;
    private Keys keys = new Keys();
    private MouseEvent e;
    private ItemList list = new ItemList();
    public boolean click=false;
    public Point currentMouse = new Point(0,0);
    public boolean spaceTyped = false;
    
    public DisplayPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouse();
        addMouseMotion();
        addKeyboard();
        timer = new Timer(20, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
              repaint();
              if(click) {
                click = false;
                mouseInput();
              }
              heldKeys();
              if(spaceTyped) {
              spaceTyped=false;
              list.space();
              }
            }
        });
        timer.start();
    }
    private void heldKeys() {
      boolean left=(keys.held("A") || keys.held("left"));
      boolean right=(keys.held("D") || keys.held("right"));
      boolean up=(keys.held("W") || keys.held("up"));
      boolean down=(keys.held("S") || keys.held("down"));
      boolean shift=(keys.held("shift"));
      list.HumanCommands(left, right, up, down, shift);
    }
    private void mouseInput() {
        if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
            list.rightClick(e.getX(), e.getY()); // Right click - Create projectile
        } else if((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
            list.middleClick(e.getX(), e.getY());// Middle click - Designate Armed Planet
        } else {
            list.leftClick(e.getX(), e.getY());  // Left click - Launch projectile
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        list.draw(g);
    }
    public Dimension getPreferredSize() {
        return new Dimension(800,700);
    }
    
    public void addMouseMotion() {
        addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent me) {
              currentMouse = me.getPoint();
            }
            public void mouseMoved(MouseEvent me) {
              currentMouse = me.getPoint();
            }
        });
    }
    public void addMouse() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouse) {
              click=true;
              e=mouse;
            }
        });
    }
    public void addKeyboard() {
        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent k) {
            }
            public void keyPressed(KeyEvent k) {
              keys.down(k.getKeyCode());
              if(k.getKeyCode()==KeyEvent.VK_SPACE) {
                spaceTyped=true;
              }
            }
            public void keyReleased(KeyEvent k) {
              keys.up(k.getKeyCode());
            }
        });
    }
    
}
