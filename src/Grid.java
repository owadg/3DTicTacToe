import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;
	
public class Grid extends JPanel implements MouseListener{
	int[][] displayedGrid;
	int length = 4;
	
	Grid(){
		super();
		this.setSize(640, 720); // half of 720p
		this.setBounds(640, 0, 640, 720);
		this.setBackground(new Color(255, 255, 255));
		displayedGrid = new int[length][length];
		addMouseListener(this);
	}
	
	// graphics here
	public void paint(Graphics g){
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		
		//text at top
		g2.setFont(new Font("Serif", Font.BOLD, 22));
		
		String temp = "Grid #" + (Game.getInstance().selectedGrid + 1);
		System.out.println(temp);
		char[] text = temp.toCharArray();
		g2.drawChars(text, 0, temp.length(), 290, 40);
		
		String player = "" + Game.getInstance().player;
		if(player.equals("-1")) {
			player = "2";
		}
		
		temp = "Player " + player + "'s turn";
		System.out.println(temp);
		text = temp.toCharArray();
		g2.drawChars(text, 0, temp.length(), 260, 60);
		
		
		//vars for spacing the grid
		int startX = 75;
		int endX = 575;
		int startY = 105;
		int endY = 605;
		int offset = 125; // space between lines
		
		g2.setStroke(new BasicStroke(5)); // thickness of 5
		g2.setColor(new Color(0,0,0)); // black
		
		for(int a = 0; a < length+1; a++) {
			//draw horizontal lines of Grid
			g2.draw(new Line2D.Double(startX, startY + offset*a, endX, startY + offset*a));	
			
			//draw vertical lines of Grid
			g2.draw(new Line2D.Double(startX  + offset*a, startY, startX + offset*a, endY));
		}
		
		for(int y = 0; y < displayedGrid[0].length; y++){
			for(int x = 0; x < displayedGrid.length; x++) {
				if(displayedGrid[y][x] == 1) {
					drawX(g2,y,x);
				}
				if(displayedGrid[y][x] == -1) {
					drawO(g2,y,x);
				}
			}
		}
	}
	
	public void setGrid(int[][] g) {
		displayedGrid = g;
		repaint();
	}
	
	public void drawO(Graphics2D g2, int y, int x) {
		g2.setColor(new Color(0,0,255));
		Point location = parseArrCoords(y,x);
		g2.draw(new Ellipse2D.Double(location.x + 12.5, location.y + 12.5,100,100));		
		g2.setColor(new Color(255,255,255));
		
	}
	
	public void drawX(Graphics2D g2, int y, int x) {
		g2.setColor(new Color(255,0,255));
		Point location = parseArrCoords(y,x);
		g2.draw(new Line2D.Double(location.x + 15, location.y + 15, location.x + 110, location.y + 110));
		g2.draw(new Line2D.Double(location.x + 15, location.y + 110, location.x + 110, location.y + 15));
		g2.setColor(new Color(255,255,255));		
	}

	
	public void setdisplayedGrid(int[][] d) {
		displayedGrid = d;
		repaint();
	}
	
	// turns an array dimension into a pixel location
	public Point parseArrCoords(int y, int x) {
		Point p = new Point();
		p.x = 75 + 125*x;
		p.y = 105 + 125*y;
		return p;
	}
	
	
	@Override
	/*
	 * from event --> game class parser (needs selected Grid) ---> updates game masterGrid ---> tells graphics to reupdate
	 * from the Java API
	 */
	public void mouseClicked(MouseEvent e) {
		System.out.println("CLICKY");
		System.out.println((int)(e.getX() - 75)/125 + ", " + (int)(e.getY()-105)/125);
		Game.getInstance().parseGridCoord(e.getX(),e.getY(),this.getWidth(),this.getHeight());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("PRESSEY!!!~~");
	
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("RELEASEY~");

		
	}



	
}
