//@author alieb
package DotPanel;

public class Key {
    public boolean pressed = false;
    public int code;
    public String name;
    public Key() {
    }
    public Key(int keyCode, String name) {
      this.code = keyCode;
      this.name = name;
    }
    public void down() {
      pressed = true;
    }
    public void up() {
      pressed = false;
    }
    public boolean check() {
      return pressed;
    }
}
