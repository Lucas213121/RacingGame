import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.Timer;


/*
public void setTryGrab(boolean b) {tryGrab = b;}
public boolean isTryGrab() {return tryGrab;}
public void setHeldBy(Player p) {heldBy = p;}
public Player getHeldBy() {return heldBy;}
public void makeInvolentaryFriend(Player p) {holding = p;}
public Player getInvolentaryFriend() {return holding;}
 */
public class WheelyBad extends JFrame implements ActionListener
{
	public static ArrayList<Updatable> characters;
	public static ArrayList<Block> blocks;
	public static ArrayList<Player> players;
	public static JFrame frame; 
	private static int numPlayers = 0;
	public boolean left = false;
	public boolean right = false;
	public Block center;
	public WheelyBad()
	{
	
		characters = new ArrayList<Updatable>();
		blocks = new ArrayList<Block>();
		players = new ArrayList<Player>();
			
		setBounds(100, 100, 600, 600);
		setResizable(false);
		setTitle("Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		
		ArrayList<ArrayList<String>> map = new ArrayList<ArrayList<String>>();
		Scanner read = null;
		File myFile = new File("RagebowRoad.txt");
		try
		{
			read = new Scanner(myFile);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		center = new Block(this.getWidth()/2 - 3,this.getHeight()/2 - 3, 6, 6, Color.BLACK);
		add(center);
		ArrayList<String> row = new ArrayList<String>();
		while(read.hasNext())
		{
			String str = read.next();
			if(str.equals("e"))
			{
				map.add(row);
				row = new ArrayList<String>();
			}
			else
			{
				row.add(str);
			}
		}
		for(int i = 0; i < map.size(); i++)
		{
			for(int j = 0; j < map.get(i).size(); j++)
			{
				
				int w = 80;
				//System.out.print((map.get(i)).get(j));
				//System.out.print(Double.parseDouble("1.0"));
				if((map.get(i)).get(j).equals("0.0"))
				{
					Block b = new Block(j*w - 100, i*w - 2100, w, w, Color.BLACK);
					add(b);
					blocks.add(b);
				}
				if((map.get(i)).get(j).equals("f"))
				{
					Block b = new Block(j*w - 100, i*w - 2100, w, w, Color.GREEN);
					add(b);
					blocks.add(b);
				}
				try
				{
					//map.get(i).get(j)
					
					if(Double.parseDouble(map.get(i).get(j)) > 0.1)
					{
						
						double d = Double.parseDouble(map.get(i).get(j));
						double e = d % 1;
						e *= 10;
						e /= 1;
						e += 0.5;
						e = Math.floor(e);
						d /= d;
						
						System.out.print("   " + d + "|" +e);
						//Block b = new Block(j*w+100, i*w, w, w, Color.MAGENTA, (int) d, );
						//add(b);
						//blocks.add(b);
					}
				}
				catch(Exception e)
				{
				}
			}
		}
		
		frame = this;

		characters = new ArrayList<Updatable>();
		setBounds(100, 100, 600, 600);
		setResizable(false);
		setTitle("Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		Timer timer = new Timer(2,this);
		timer.start();
		final Player host = new Player(this.getWidth()/2 - 20 - 7.5, this.getHeight()/2 - 20 - 15, Color.BLUE, frame);
		add(host);
		characters.add(host);
		players.add(host);
		host.setName("Vroom");
		
		final double turn = 0.01;
		
		this.addKeyListener(new KeyListener()
		{

			
			
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == e.VK_W)
				{
					host.setAccelerating(true);
				}
				
				if(e.getKeyCode() == e.VK_SPACE)
				{
					host.setVroom(true);
				}
				else if(e.getKeyCode() == e.VK_A)
				{
					
					if(!right)
						host.setDr(-turn);
					left = true;
				}
				else if(e.getKeyCode() == e.VK_D)
				{
					if(!left)
						host.setDr(turn);
					right = true;
					
				}
			}
			public void keyReleased(KeyEvent e) 
			{
				if (e.getKeyCode() == e.VK_W)
				{
					host.setAccelerating(false);
				}
				if(e.getKeyCode() == e.VK_SPACE)
				{
					
					host.setVroom(false);
					
				}
				else if(e.getKeyCode() == e.VK_A)
				{
					if(!right)
					{
						host.setDr(0);	
					}
					else
					{
						host.setDr(turn);
					}
					left = false;
				}
				else if(e.getKeyCode() == e.VK_D)
				{
					if(!left)
					{
						host.setDr(0);
					}
					else
					{
						host.setDr(-turn);
					}
					right = false;
				}
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	public static void main(String[] args) throws IOException 
	{
		new WheelyBad();
		int port = 9999;
		ServerSocket server = new ServerSocket(port);
		System.out.println("clients: " + numPlayers);
		
		
		while(true)//for(;;)
		{
			try
			{
				
				System.out.println("Server listening...");
				Socket client = server.accept();
				System.out.println("server accepted client");
				
				
				Player player = new Player(Math.random()*200,Math.random()*200,Color.RED, frame);
				
				Handler clientThread = new Handler(client,numPlayers, player);
				
				characters.add(player);
				players.add(player);
				frame.add(player);
				
				/*
				player = new Player(Math.random()*200,Math.random()*200,Color.RED, frame);
				characters.add(player);
				players.add(player);
				frame.add(player);
				*/
				
				frame.repaint();
				new Thread(clientThread).start();
				add_client();
				
				
				
			}
			catch(Exception e)
			{
				System.out.println("there was an issue");
			}
		}
	}
	public static void add_client()
	{
		System.out.println("clients: "+ (++numPlayers));
	}

	public static void remove_client()
	{
		System.out.println("clients: "+ (--numPlayers));
	}

	@Override
	
	
	public void actionPerformed(ActionEvent e) 
	{
		for(Updatable character: characters)
		{		
			character.update();
		}
		for(Player p : players)
		{
			for(Block b : blocks)
			{
				b.setPosition(-p.getPos().x,-p.getPos().y);
			}
			for(Block b : blocks)
			{
				//center.setPosition(0,0);
				center.setObjectColor(Color.MAGENTA);
				
				if(b.getRect().contains(p.getPos().x + 300, p.getPos().y + 300))
				{
					if(b.getColor().equals(Color.GREEN))
					{
						p.setTimer(false);
						p.setRecord();
					}
					b.setObjectColor(Color.RED);
					p.setObjectColor(Color.RED);
					p.setR(p.getR()+Math.PI);
					p.update();
					
					p.setR(p.getR()+Math.PI);
					
				}
				
				
			}
		}
		
		repaint();
	}
}

class Handler implements Runnable
{
	private Socket client;
	private String name;
	private final int ID;
	private Player player;
	public Handler(Socket s, int id, Player p)
	{
		client = s;
		this.ID = id;
		this. player = p;
	}
	
	@Override
	public void run() 
	{
		try
		{
					
			
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(),true);
			
			String message;
			
			name = in.readLine();
			player.setName(name);
			
			while((message = in.readLine()) != null)
			{
				System.out.print(message);
				switch(message)
				{	
				case "A":
					break;
				case "a":
					break;

				}
					
			}
			System.out.print("why are you like this");
			client.close();
			WheelyBad.remove_client();
		}
		catch(Exception e)
		{
			System.out.println("there was an issue");
		}
		
	}
	
}