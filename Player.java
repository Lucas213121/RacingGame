import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
public class Player extends JComponent implements Updatable
{
	
	private long start = System.currentTimeMillis();
	private int w = 15, h = 25; 
	private double x, y, dx, dy, dr, rotation, velocity;
	private Color color;
	private JLabel timerTag;
	private JFrame frame;
	private boolean vroom, accelerating;
	
	public Player(double x, double y, Color c, JFrame frame)
	{
		
		this.setSize(new Dimension(100,100));
		this.x = x;
		this.y = y;
		this.setLocation((int)x, (int)y);
		
		timerTag = new JLabel("Loading...", SwingConstants.RIGHT);
		
		timerTag.setFont(new Font("Helvetica", Font.BOLD, 13));
		timerTag.setSize(100,20);
		rotation = 0;
		timerTag.setForeground(Color.red);
		//timerTag.setLocation(100, 100);
		color = c;
		this.frame = frame;
		frame.add(timerTag, 0);
		
	}
	public Rectangle getHitbox()
	{
		return new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
		
	}
	
	public void setName(String s) { timerTag.setText(s);}
	public String getName() {return timerTag.getText();}
	
	public void setDr(double dr) { this.dr = dr;}
	public double getDr() {return dr;}
	
	public void setDx(double dx) { this.dx = dx;}
	public double getDx() {return dx;}
	public void setDy(double dy) { this.dy = dy;}
	public double getDy() { return dy;}
	
	public void setX(double i) { x = i;}
	public void setY(double i) { y = i;}
	
	public void setAccelerating(Boolean condition) {accelerating = condition;}
	public boolean getAccelerating() {return accelerating;}
	
	public void setVroom(boolean i) { vroom = i;}
	public boolean getVroom() { return vroom;}
	
	public Point getPos()
	{
		return new Point((int)(x + 7.5),(int)y + 15);
	}
	public void setPos(double x,double y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	public void update()
	{
		
		rotation += dr * 2;

		
		dx = Math.sin(-rotation) * velocity; // /2;
		dy = Math.cos(-rotation) * velocity;
		
		
		double max = 1;
		if(vroom)
		{
			max = 2;
			dx *= 2;
			dy *= 2;
		}
//		if(Math.abs(dx) > max * Math.sin(-rotation))
//		{
//			dx = max * Math.sin(-rotation);//* Math.abs(dx)/dx;
//		}
//		if(Math.abs(dy) > max * Math.cos(-rotation))
//		{
//			dy = max * Math.cos(-rotation);//  * Math.abs(dy)/dy;
//		}
		x += dx;
		y += dy;
		timerTag.setText(""+((-start+System.currentTimeMillis()) / 1000.0));
		/*
		if(x < - 30)
		{
			x = 600;
		}
		
		if(x > 600)
		{
			x = 30;
		}
		if(y < - 30)
		{
			y = 600;
		}
		
		if(y > 600)
		{
			y = 30;
		}
		
		*/
		//this.setLocation((int)(x), (int)(y));
		
		if (!accelerating && !vroom)
		{
			velocity -= .02;
		}
		else if (velocity < 2)
			velocity += .01;
		if (velocity <= 0)
			velocity = 0;
		//System.out.println("Vel: " + velocity);
		
		this.setPos(x, y);
		
		
		
		
		
		//timerTag.setLocation((int)x + w/2 - timerTag.getWidth()/2, (int)y - 20);
		
		repaint();
		
	}
	//naming this "setColor" causes errors 
	
	public void setObjectColor(Color c)
    {
		this.color = c; 
    }
	
	
	public void paintComponent(Graphics g)
	{
		AffineTransform transform = new AffineTransform();
		
		transform.translate(20, 20);//+  Math.sin(rotation)*7.5, 20 + Math.cos(rotation)*7.5);
		transform.rotate(rotation, 7.5, 15);
		
		Rectangle rect = new Rectangle(15, 30);
		
		Shape s = transform.createTransformedShape( rect );
		g.setColor(color);
		((Graphics2D) g).fill(s);
		
	}
	public double getR() 
	{
		return rotation;
	}
	public void setR(double i) 
	{
		rotation = i;
	}
	
	
	
		
}