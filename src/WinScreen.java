import java.awt.Font;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class WinScreen extends JPanel implements MouseListener{
	int winner; //which player wins
	
	//constructor
	public WinScreen(int w) {
		super();
		winner = w;
		this.setSize(1280, 720);
		addMouseListener(this);
	}
	
	//graphics here
	public void paint(Graphics g) {
		super.paint(g);
		System.out.println("BANG DRAWN");
		Font winFont = new Font(Font.SANS_SERIF, Font.ROMAN_BASELINE, 90);
		g.setFont(winFont);
		g.drawString("Player " + winner + " wins! Congratulations!", 20, 300);
		Font subText = new Font(Font.SERIF, Font.PLAIN, 22);
		g.setFont(subText);
		g.drawString("Click anywhere to play again...", 500, 400);
	}
	
	// all these next methods are from the Java API
	public void mouseClicked(MouseEvent arg0) {
		Game.getInstance().reset();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
