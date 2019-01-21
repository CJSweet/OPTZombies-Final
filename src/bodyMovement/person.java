package bodyMovement;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.*; // Imports events for buttons. 
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Color;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.Timer;


public class person implements KeyListener, MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	ArrayList<Bullet> DartInMag = new ArrayList<Bullet>();
	player labelGameObject;
	private JFrame frame;
	JPanel panel;
	KeyEvent e;
	Timer timer;
	int delay = 10;
	Integer mouseX = 0; // Position of 
	Integer mouseY = 0;
	static double angleDirection;
	
	static int darts = 24;
	boolean dartAlive = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					person window = new person();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public person() {
		initialize();
	}

	private void initialize() {
		
		for (int i = 0; i < 24; i++) {
			Bullet l = new Bullet(new ImageIcon("C:\\Users\\mcaird22\\Music\\New folder\\src\\dart.png"));
			DartInMag.add(l);
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 702, 531);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 686, 492);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		labelGameObject = new player(new ImageIcon("C:\\Users\\mcaird22\\Music\\New folder\\src\\smiley.png"));
		labelGameObject.setBackground(Color.ORANGE);
		labelGameObject.addKeyListener(this);
		labelGameObject.setFocusable(true);
		panel.addMouseMotionListener(this);
		panel.addMouseListener(this);
		labelGameObject.setHorizontalAlignment(SwingConstants.CENTER);
		labelGameObject.setBounds(349, 219, 25, 23);
		panel.add(labelGameObject);
	}
	
	/**
	 * If a key a,s,d,w is pushed, the gameLabelObject is moved to that direction
	 * @param e
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
		// Captures the key pressed down
		int key = e.getKeyCode();
		
		// If a knwon key is pressed down, then the location of the object increases or decreases depending on the
		// key option.
		if (key == KeyEvent.VK_D) {
			labelGameObject.setLocation(labelGameObject.getLocation().x+4, labelGameObject.getLocation().y);
			System.out.println("you pushed D");
		}
		else if (key == KeyEvent.VK_A) {
			labelGameObject.setLocation(labelGameObject.getLocation().x-4, labelGameObject.getLocation().y);
			System.out.println("you pushed A");
		}
		else if (key == KeyEvent.VK_W) {
			labelGameObject.setLocation(labelGameObject.getLocation().x,  labelGameObject.getLocation().y-4);
			System.out.println("you pushed W");
		}
		else if (key == KeyEvent.VK_S) {
			labelGameObject.setLocation(labelGameObject.getLocation().x, labelGameObject.getLocation().y+4);
			System.out.println("You pushed S");
		}
		
		// if user taps a key that wasnt programmed, nothing will happen
		else {System.out.println("That is key is not made");}

		// used vector and found the degree and converted it to angle and is now the resulting direction
		angleDirection =  (Math.toDegrees(Math.atan2(mouseX, mouseY))+120);
		labelGameObject.setRotation(angleDirection);
		frame.repaint();
		// Prints the info to the system.
		System.out.println("Angle " + angleDirection);
		System.out.printf("(%d, %d)\n", mouseX, mouseY);
		
	}
	
	/**
	 * checks to see if the mouse is being moved and if it, rotate the gameLabelObject
	 * I will have a JLabel and a picture. The picture will be the one rotating. 
	 * I need to figure out how will keep the object moving too.
	 * @param e
	 */

	@Override
	public void mouseMoved(MouseEvent e) {
		
		// This is to make the origin relative to labelGameObject and find vector between two points
		mouseX = e.getX() - (labelGameObject.getX()+9);
		mouseY = e.getY() - (labelGameObject.getY()+9);

		// used vector and found the degree and converted it to angle and is now the resulting direction
		double angleDirection =  (Math.toDegrees(Math.atan2(mouseX, mouseY))+120);
		labelGameObject.setRotation(angleDirection);
		
		// rotates the labelGameObject without having to push asdw.
		frame.repaint();
		
		// Prints the info to the system.
		System.out.println("Angle " + angleDirection);
		System.out.printf("(%d, %d)\n", mouseX, mouseY);

	}
	
	
	static class player extends JLabel {
		
		private static final long serialVersionUID = 1L;
		private double angleFaced = 0; // determines how far to rotate.
		
		public player(String text, int x, int y) {
			super(text);
			int width = getPreferredSize().width;
			int height = getPreferredSize().height;
			
			setBounds(x,y, width, height);
		}
		
		public player(ImageIcon i) {
			super(i);
			int width = getPreferredSize().width;
			int height = getPreferredSize().height;
		}
		
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2D = (Graphics2D) g;
			g2D.rotate(-1*Math.toRadians(angleFaced)+200, getWidth()/2, getHeight()/2);
			super.paintComponent(g);
		}
		
		public void setRotation(double angle) {this.angleFaced = angle;}
	}

	/**
	 * Shooting the dart across the screen
	 * @param e
	 */
	@Override
	public void mouseClicked(MouseEvent e) {  
			//System.out.printf(String.valueOf(e.getClickCount()));
		if (!(DartInMag.isEmpty())) {
			DartInMag.get(darts-1).setBounds(labelGameObject.getX(), labelGameObject.getY(), 30, 30);
			DartInMag.get(darts-1).setVisible(true);
			panel.add(DartInMag.get(darts-1));
			DartInMag.get(darts-1).Movement();
			panel.repaint();
			//DartInMag.remove(darts-1);
			System.out.printf("Fired the " + String.valueOf(darts) + " bullet.\n");
			//darts--;
		}
		else if ((DartInMag.isEmpty())) {
			System.out.println("You have no bullets");
		}
	}
	
	//public void collision(JLabel objects, int x, int y) {
		
//		if ((labelDart.getY() > 0 && labelDart.getY() < 492) || (labelDart.getX() > 0 && labelDart.getY() < 686)) {
//			labelDart.setLocation(labelDart.getLocation().x, labelDart.getLocation().y-3);
//		}
//		
//		else {labelDart.remove(labelDart);}
//		
//	}

	public void mouseDragged(MouseEvent e) {return;}
	public void keyReleased(KeyEvent e) {return;}
	public void keyTyped(KeyEvent e) {return;}
	public void mouseEntered(MouseEvent arg0) {return;}
	public void mouseReleased(MouseEvent arg0) {return;}
	public void mouseExited(MouseEvent arg0) {return;}
	public void mousePressed(MouseEvent arg0) {return;}
		
}