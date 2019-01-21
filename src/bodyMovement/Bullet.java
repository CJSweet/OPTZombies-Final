package bodyMovement;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Bullet extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double angle = person.angleDirection;
	Timer timer;
	int delay = 10;
	
	public Bullet(String text, int x, int y) {
		super(text);
		int width = getPreferredSize().width;
		int height = getPreferredSize().height;
		
		setBounds(x,y, width, height);
	}
	
	public Bullet(ImageIcon i) {
		super(i);
		int width = getPreferredSize().width;
		int height = getPreferredSize().height;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.rotate(-1*Math.toRadians(angle)+200, getWidth()/2, getHeight()/2);
		super.paintComponent(g);
	}
	
	public void Movement() {
		while (this.getX() > 0 && this.getX() < 702 && this.getY() > 0 && this.getY() < 531) {
			int x = this.getX();
			int y = this.getY();
			this.setLocation(x+1, y+1);
			this.repaint();	
		}
	}
}
