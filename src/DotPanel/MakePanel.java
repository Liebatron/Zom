package DotPanel;

import java.awt.Color;

import javax.swing.JFrame;

public class MakePanel {
	public MakePanel() {
		prepareGui();
	}
	public void prepareGui() {
        JFrame f = new JFrame("");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DisplayPanel panel = new DisplayPanel();
        panel.setBackground(Color.WHITE);
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        f.add(panel);
        f.pack();
        f.setVisible(true);
	}
}