//@author alieb
package DotPanel;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Keys {
    List<Key> keyList = new ArrayList<Key>();
    public Key Fail = new Key(KeyEvent.VK_PRINTSCREEN, "Fail");
    public Keys() {
      keyList.add(new Key(KeyEvent.VK_SPACE, "space"));
      keyList.add(new Key(KeyEvent.VK_SHIFT, "shift"));
      keyList.add(new Key(KeyEvent.VK_LEFT, "left"));
      keyList.add(new Key(KeyEvent.VK_RIGHT, "right"));
      keyList.add(new Key(KeyEvent.VK_DOWN, "up"));
      keyList.add(new Key(KeyEvent.VK_UP, "down"));
      keyList.add(new Key(KeyEvent.VK_A, "A"));
      keyList.add(new Key(KeyEvent.VK_D, "D"));
      keyList.add(new Key(KeyEvent.VK_S, "S"));
      keyList.add(new Key(KeyEvent.VK_W, "W"));
    }
    public void down(int code) {
      findKey(code).down();
    }
    public void down(String name) {
      findKey(name).down();
    }
    
    public void up(int code) {
      findKey(code).up();
    }
    public void up(String name) {
      findKey(name).up();
    }
    
    public boolean held(int code) {
      return findKey(code).pressed;
    }
    public boolean held(String name) {
      return findKey(name).pressed;
    }
    
    public Key findKey(String name) {
      Key key = new Key();
        for (int i=0;i<keyList.size();i++) {
          if (keyList.get(i).name.equals(name)) {
            return keyList.get(i);
          }
        }
      System.out.println("Not a tracked key.");
      return Fail;
    }
    public Key findKey(int code) {
      Key key = new Key();
        for (int i=0;i<keyList.size();i++) {
          if (keyList.get(i).code == code) {
            return keyList.get(i);
          }
        }
      System.out.println("Not a tracked key.");
      return Fail;
    }
}