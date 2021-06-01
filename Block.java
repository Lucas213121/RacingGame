import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
public class Block extends JComponent 
{

	private Rectangle rect;
	private double absX, absY, relX, relY;
	private Color color = Color.RED;
	
	public Block(int x, int y, int w, int h, Color c)
	{
		this.setSize(new Dimension(w, h));
		this.setLocation(x,y);
		absX = x;
		absY = y;
		color = c;
		rect = new Rectangle(0,0,w,h);
	}

	public Rectangle getRect()
	{
		return new Rectangle((int)(absX + relX), (int)(absY + relY), this.getWidth(), this.getHeight());
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.fill(rect);
		g2.draw(rect);
		
	}
	public void setPosition(int relX, int relY)
	{
		this.setLocation((int)(absX + relX), (int)(absY + relY)); 
	}
	public Point getPosition()
	{
		return(new Point((int)(absX + relX), (int)(absY + relY))); 
	}
	public int getRelX() 
	{
		return (int)relX;
	}
	public int getRelY() 
	{
		return (int)relY;
	}

	public void setObjectColor(Color c) 
	{	
		color = c;
	}
	
}
