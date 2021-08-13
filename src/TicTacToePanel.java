import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;

import javax.swing.JPanel;

public class TicTacToePanel extends JPanel implements MouseListener{
	int[][][] displayedGrid;
	int selectedGrid = 0;
	int length = 4;
	
	TicTacToePanel(){
		super();
		this.setSize(640, 720); // half of 720p
		this.setBounds(0, 0, 640, 720);
		this.setBackground(new Color(0, 255, 0));
		addMouseListener(this);		
	}
	
	//graphics here
	public void paint(Graphics g){
		super.paint(g);
	
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setFont(new Font("Serif", Font.BOLD, 22));

		//vars for spacing the lines (vertical)
		int startX = 105;
		int endX = 295;
		int startY = 150;
		int endY = 45;
		
		//vars for spacing ttt lines (horizontal)
		
		int startHorizontalX = 200;
		int endHorizontalX = 510;
		int horizontalY = 65; //only need one cuz its a horizontal line
		
		int lineHorizontalOffsetX = 60; // how the horizontal lines shift
		int lineHorizontalOffsetY = 30; // how the horizontal lines shift
		
		int lineOffset = 90; // space between lines (vertical)
		int gridOffset = 165; // space between each of the grids
		int selectedOffset = 0; // selected grid will move to the side so the player knows that its selected
		
		g2.setStroke(new BasicStroke(5));
		
		//outside for loop for drawing repeated grids
		for(int outside = 0; outside < length; outside++) {
			for(int a = 0; a < length - 1; a++) {
				
				// apply the selected grid offset to all the X vars
				if(outside == selectedGrid) {
					startX += selectedOffset;
					endX += selectedOffset;
					startHorizontalX += selectedOffset;
					endHorizontalX += selectedOffset;
					g2.setColor(new Color(255,255,255));
				}
				
				//draw vertical lines of grid
				g2.draw(new Line2D.Double(startX + a * lineOffset, startY + outside * gridOffset, endX + a * lineOffset, endY + outside * gridOffset));	
				
				//draw horizontal lines of grid
				g2.draw(new Line2D.Double(startHorizontalX - a*lineHorizontalOffsetX, horizontalY + a *lineHorizontalOffsetY + outside * gridOffset, endHorizontalX - a*lineHorizontalOffsetX, horizontalY + a *lineHorizontalOffsetY + outside * gridOffset));
				
				// remove the selected grid offset to all X vars
				if(outside == selectedGrid) {
					startX -= selectedOffset;
					endX -= selectedOffset;
					startHorizontalX -= selectedOffset;
					endHorizontalX -= selectedOffset;
					g2.setColor(new Color(0,0,0));
				}
			}
			// font!
			g2.drawChars(("" + (outside+1)).toCharArray(), 0, 1,  50, 40 + gridOffset*outside);
		}
		String r = "click to rotate 90 degrees clockwise";
		g2.drawChars(r.toCharArray(),0,r.length(), 150, 690);
		
		//draw the X's and O's
		for(int z = 0; z < length; z++) {
			for(int y = 0; y < length; y++) {
				for(int x = 0; x < length; x++) {
					if(displayedGrid[z][y][x] == 1) {
						drawX(g2,z,y,x);
					}
					if(displayedGrid[z][y][x] == -1) {
						drawO(g2,z,y,x);
					}
				}
			}
		}	
	}
	
	public int getSelectedGrid() {
		return selectedGrid;
	}
	
	

	public void setSelectedGrid(int a) {
		selectedGrid = a;
		Game.getInstance().setSelectedGrid(a);
		repaint();
	}
	
	public void setdisplayedGrid(int[][][] d) {
		displayedGrid = d;
		repaint();
	}
	
	//draws an O at a given location
	//defined by two cubic functions
	public void drawO(Graphics2D g2, int x, int y, int z) {
		g2.setStroke(new BasicStroke(5));
		Point location = parseArrayDimensions(x,y,z);
		CubicCurve2D top = new CubicCurve2D.Double();
		CubicCurve2D bottom = new CubicCurve2D.Double();
		
		g2.setColor(new Color(0,0,255));
		
		bottom.setCurve(location.x - 30 + 10 , location.y + 15, location.x - 30, location.y + 30, location.x + 20, location.y + 30, location.x - 30 + 90 - 20, location.y + 15);
		//g2.draw(new Line2D.Double(location.x - 30  +  10, location.y + 15, location.x - 30 + 90 - 20, location.y + 15));
		top.setCurve(location.x - 30 + 10, location.y + 15, location.x, location.y, location.x + 60, location.y ,location.x - 30 + 90 - 20, location.y + 15);
					
		g2.draw(top);
		g2.draw(bottom);
		
		g2.setColor(new Color(255,255,255)); //resets the color
	}
	
	//draws an X at a given coordinate
	//defined by 2 lines
	public void drawX(Graphics2D g2, int x, int y, int z) {
		Point location = parseArrayDimensions(x,y,z);
		g2.setStroke(new BasicStroke(5));
		g2.setColor(new Color(255,0,255));
		g2.draw(new Line2D.Double());
		g2.draw(new Line2D.Double(location.x - 7, location.y + 8, location.x + 90 - 60, location.y + 20)); // 31.5 is the difference in x
		g2.draw(new Line2D.Double(location.x - 31.5, location.y + 20, location.x + 90 - 30 - 8, location.y + 7));
		//g2.draw(new Line2D.Double(location.x, location.y, location.x, location.y));
		// ^ basis for the corner
		g2.setColor(new Color(255,255,255));
		}
	
	//updates the tttp
	public void refresh(int[][][] grid) {
		displayedGrid = grid;
		repaint();
	}
	
	//turns a location on-screen into which grid is selected or when to rotate
	public void parseSelectedGridCoordinates(Point p) {
		if((p.y)/165 < 4) {
			setSelectedGrid((p.y)/165);
		} else {
			Game.getInstance().rotateDisplay();
		}
		System.out.println((p.y)/165);
	}
	
	// turns an array dimension that correlates to the game into a pixel location
	public Point parseArrayDimensions(int z, int y, int x) {
		Point p = new Point();
		p.y = (z)*165 + y*30 + 40 - 5;
		p.x = x*90 + 227 - 52 * y;
		if(z == selectedGrid) {
			p.x += 0;
		}
		//System.out.println("z: " + z + " y: " + y + "(" + p.y + ")" + "x:" + x + "(" + p.x + ")");
		return p;
	}
	
	
	/*
	 * gives the coords of the mouse click to the parser method, which hands it off to the set method,
	 *  which hands it back to the Game class to update the displayed Grid
	 *  all of these methods are part of the MouseListener class included in the Java API
	 */
	public void mouseClicked(MouseEvent e) {
		parseSelectedGridCoordinates(e.getPoint());
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
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
