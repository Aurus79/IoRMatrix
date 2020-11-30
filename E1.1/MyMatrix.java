import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class MyMatrix extends JPanel {

	private static final long serialVersionUID = 1L;

	private int marge = 60 ;		// in pixels
	private int boxSize = 60 ;		// in pixels
	private int n = 8 ;
	private int x = 0 ; 
	private int y = 0 ;
	private boolean afficherBalle = false ;
	
	public MyMatrix() {
		setBackground(Color.WHITE);
		init();
	}
	
	@Override
	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		Graphics2D g = (Graphics2D) g2;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int sizeInPixels = boxSize * n ;
		g.setColor(Color.LIGHT_GRAY);
		for(int i=0; i<n+1; i++) {
			g.drawLine(marge+i*boxSize, marge, marge+i*boxSize, marge+sizeInPixels);
			g.drawLine(marge, marge+i*boxSize, marge+sizeInPixels, marge+i*boxSize);
		}
		if(afficherBalle) {
			g.setColor(Color.ORANGE);
			g.fillOval(marge+boxSize*x, marge+boxSize*y, boxSize, boxSize);
			g.setColor(Color.DARK_GRAY);
			g.drawOval(marge+boxSize*x, marge+boxSize*y, boxSize, boxSize);
		}
	}
	
	public void incX() {
		if(++x>n-1) x=0;
		repaint();
	}
	
	public void decX() {
		if(--x<0) x=n-1;
		repaint();
	}

	public void incY() {
		if(++y>n-1) y=0;
		repaint();
	}
	
	public void decY() {
		if(--y<0) y=n-1;
		repaint();
	}
	
	public void init() {
		x = y = n/2+n%2-1;
		System.out.println(x);
		repaint();
	}
	
	public void afficher() {
		afficherBalle = true ;
		repaint();
	}
	
	public void cacher() {
		afficherBalle = false ;
		repaint();
	}
	
}