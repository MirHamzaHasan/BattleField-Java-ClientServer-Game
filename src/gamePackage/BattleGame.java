package gamePackage;

import java.awt.*;
import java.awt.event.*;
import java.net.Socket;


import javax.swing.*;

import java.io.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;



public class BattleGame extends BasicGame implements ActionListener{
	// Variables
	private Socket client;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private JFrame frame;
	private JTextField ip_t;
	private JTextField port_t;
	private JTextField name_t;
	private String name;
	private String port;
	private String ip;
	private boolean yes=true;
	private Animation an;
	private Animation an1;
	private Animation an2;
	private Animation an3;
	public Sprite mySprite;
	public Sprite others[]=new Sprite[3];
	public Image mySpriteImage;
	public Image othersImage[]=new Image[3];
	public String PlayerName;
	public boolean forward;
	public Vector2f velocity;
	public final float tangentialVelocty=9.0f;
	public final float friction=0.045f;
	public Image Background; 
	public Vector2f bull1vel;
	public Vector2f bull2vel;
	public Image bullet;
	// Implementation
	public BattleGame() {
		super("Battle Field");
		velocity=new Vector2f();
		frame=new JFrame("Connect To Server");
		frame.setLayout(new FlowLayout());
		ip_t=new JTextField(20);
		port_t=new JTextField(20);
		name_t=new JTextField(20);
		JLabel name_l=new JLabel("Your Name");
		JLabel ip_l=new JLabel("Server Ip");
		JLabel port_l=new JLabel("Application Port");
		JButton b=new JButton("Connect");
		b.addActionListener(this);
		frame.setSize(240,220);
		frame.add(name_l);
		frame.add(name_t);
		frame.add(ip_l);
		frame.add(ip_t);
		frame.add(port_l);
		frame.add(port_t);
		frame.add(b);
		
		//ImageIcon i=new ImageIcon("res/icon.png");
		//frame.setIconImage();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("res/icon.png"));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		System.out.println("Exiting Main");
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setAlwaysRender(true);
		others[0]=new Sprite();
		others[1]=new Sprite();
		others[2]=new Sprite();
		mySprite=new Sprite();
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		while(yes){
		}
		PlayerName=name;
		
		try{
			client=new Socket(ip,Integer.parseInt(port));		
			//gc.wait(100);
			client.setReceiveBufferSize(65536);
			client.setSendBufferSize(65536);
			client.setPerformancePreferences(1, 0, 2);
			client.setTcpNoDelay(true);
			ois=new ObjectInputStream(client.getInputStream());
			mySprite=(Sprite)ois.readObject();
			others[0]=(Sprite)ois.readObject();
			others[1]=(Sprite)ois.readObject();
			others[2]=(Sprite)ois.readObject();
			
			try{
			if(mySprite.isAlive){
				if(mySprite.PlayerId==0){
					mySpriteImage=new Image("res/tank.png");
				}
				else if(mySprite.PlayerId==1){
					mySpriteImage=new Image("res/plane.png");
				}
				else if(mySprite.PlayerId==2){
					mySpriteImage=new Image("res/spaceship.png");
				}
				else if(mySprite.PlayerId==3){
					mySpriteImage=new Image("res/sp2.png");
				}
			}}catch(SlickException ex){}
			
			InitializeImages();
			//Creating Blast Animation
			InitializeAnimation();
			// Ending Blast Animation
			
			
			
			gc.setSmoothDeltas(true);
			try{
				Background=new Image("res/background2.png");
				bullet=new Image("res/bullet.png");
				}catch(SlickException ex){}
		}catch(Exception e){e.printStackTrace();JOptionPane.showMessageDialog(frame, "Problem in Connection");System.exit(0);}
	
	}
	
	
	
	public void InitializeImages() throws SlickException{
		
		
		if(others[0].isAlive){
			if(others[0].PlayerId==0){
				othersImage[0]=new Image("res/tank.png");
			}
			else if(others[0].PlayerId==1){
				othersImage[0]=new Image("res/plane.png");
			}
			else if(others[0].PlayerId==2){
				othersImage[0]=new Image("res/spaceship.png");
			}
			else if(others[0].PlayerId==3){
				othersImage[0]=new Image("res/sp2.png");
			}
		}
		
		if(others[1].isAlive){
			if(others[1].PlayerId==0){
				othersImage[1]=new Image("res/tank.png");
			}
			else if(others[1].PlayerId==1){
				othersImage[1]=new Image("res/plane.png");
			}
			else if(others[1].PlayerId==2){
				othersImage[1]=new Image("res/spaceship.png");
			}
			else if(others[1].PlayerId==3){
				othersImage[1]=new Image("res/sp2.png");
			}
		}
		
		if(others[2].isAlive){
			if(others[2].PlayerId==0){
				othersImage[2]=new Image("res/tank.png");
			}
			else if(others[2].PlayerId==1){
				othersImage[2]=new Image("res/plane.png");
			}
			else if(others[2].PlayerId==2){
				othersImage[2]=new Image("res/spaceship.png");
			}
			else if(others[2].PlayerId==3){
				othersImage[2]=new Image("res/sp2.png");
			}
		}
		
		
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawImage(Background,0,0);
		if(mySprite.isAlive){	
			//g.drawImage(Background,0,0);
			g.setColor(org.newdawn.slick.Color.red);
			g.drawString("Your Score = "+mySprite.Score, 0, 0);
			g.setColor(org.newdawn.slick.Color.white);
			if(mySprite.killme==-1)
			{
				an.restart();
				g.drawImage(mySpriteImage,mySprite.SpritePos.x,mySprite.SpritePos.y);
			}
			else{
					g.drawAnimation(an, mySprite.SpritePos.x+(mySpriteImage.getWidth()/2),mySprite.SpritePos.y+(mySpriteImage.getHeight()/2));
					g.drawAnimation(an, mySprite.SpritePos.x+(mySpriteImage.getWidth()/2)-10,mySprite.SpritePos.y+(mySpriteImage.getHeight()/2)-10);
					g.drawAnimation(an, mySprite.SpritePos.x+10,mySprite.SpritePos.y+10);
					g.drawAnimation(an, mySprite.SpritePos.x-10,mySprite.SpritePos.y-10);
					an.update(500);
			}
			g.drawString(PlayerName,mySprite.SpritePos.x+mySpriteImage.getWidth()/2,mySprite.SpritePos.y+mySpriteImage.getHeight()/2);
			if(mySprite.isVisible1){
				g.drawImage(bullet,mySprite.bull1.x,mySprite.bull1.y);
			}
			if(mySprite.isVisible2){
				g.drawImage(bullet,mySprite.bull2.x,mySprite.bull2.y);
			}
		}
		
		if(others[0].isAlive){
			othersImage[0].setRotation(others[0].Rotation);
			if(others[0].killme==-1)
			{
				an1.restart();
				g.drawImage(othersImage[0], others[0].SpritePos.x, others[0].SpritePos.y);
			}
			else{
				g.drawAnimation(an1, others[0].SpritePos.x+(othersImage[0].getWidth()/2),others[0].SpritePos.y+(othersImage[0].getHeight()/2));
				g.drawAnimation(an1, others[0].SpritePos.x+(othersImage[0].getWidth()/2)-10,others[0].SpritePos.y+(othersImage[0].getHeight()/2)-10);
				g.drawAnimation(an1, others[0].SpritePos.x+10,others[0].SpritePos.y+10);
				g.drawAnimation(an1, others[0].SpritePos.x-10,others[0].SpritePos.y-10);
				an1.update(500);
			}
			if(others[0].isVisible1){
				g.drawImage(bullet,others[0].bull1.x,others[0].bull1.y);
			}
			if(others[0].isVisible2){
				g.drawImage(bullet,others[0].bull2.x,others[0].bull2.y);
			}
		}
		if(others[1].isAlive){
			othersImage[1].setRotation(others[1].Rotation);
			if(others[1].killme==-1)
			{
				an2.restart();
				g.drawImage(othersImage[1], others[1].SpritePos.x, others[1].SpritePos.y);
			}
			else{
				g.drawAnimation(an2, others[1].SpritePos.x+(othersImage[1].getWidth()/2),others[1].SpritePos.y+(othersImage[1].getHeight()/2));
				g.drawAnimation(an2, others[1].SpritePos.x+(othersImage[1].getWidth()/2)-10,others[1].SpritePos.y+(othersImage[1].getHeight()/2)-10);
				g.drawAnimation(an2, others[1].SpritePos.x+10,others[1].SpritePos.y+10);
				g.drawAnimation(an2, others[1].SpritePos.x-10,others[1].SpritePos.y-10);
				an2.update(500);
			}if(others[1].isVisible1){
				g.drawImage(bullet,others[1].bull1.x,others[1].bull1.y);
			}
			if(others[1].isVisible2){
				g.drawImage(bullet,others[1].bull2.x,others[1].bull2.y);
			}
		}
		if(others[2].isAlive){
			othersImage[2].setRotation(others[2].Rotation);
			if(others[2].killme==-1)
			{
				an3.restart();
				g.drawImage(othersImage[2], others[2].SpritePos.x, others[2].SpritePos.y);
			}
			else{
				g.drawAnimation(an3, others[2].SpritePos.x+(othersImage[2].getWidth()/2),others[2].SpritePos.y+(othersImage[2].getHeight()/2));
				g.drawAnimation(an3, others[2].SpritePos.x+(othersImage[2].getWidth()/2)-10,others[2].SpritePos.y+(othersImage[2].getHeight()/2)-10);
				g.drawAnimation(an3, others[2].SpritePos.x+10,others[2].SpritePos.y+10);
				g.drawAnimation(an3, others[2].SpritePos.x-10,others[2].SpritePos.y-10);
				an3.update(500);
			}if(others[2].isVisible1){
				g.drawImage(bullet,others[2].bull1.x,others[2].bull1.y);
			}
			if(others[2].isVisible2){
				g.drawImage(bullet,others[2].bull2.x,others[2].bull2.y);
			}
		}
		
	
	}

	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input=gc.getInput();
	
		try{
			ois=new ObjectInputStream(client.getInputStream());
			mySprite=(Sprite)ois.readObject();
			others[0]=(Sprite)ois.readObject();
			others[1]=(Sprite)ois.readObject();
			others[2]=(Sprite)ois.readObject();
			InitializeImages();
		}catch(Exception e){System.exit(0);}
		
		
		
		
		mySpriteImage.setRotation(mySprite.Rotation);
		  if(input.isKeyDown(Input.KEY_LEFT)){	   
			   mySprite.Rotation-=4.5f;
			   
		  }
		  else if(input.isKeyDown(Input.KEY_RIGHT)){
			   mySprite.Rotation+=4.5;
			   
		   }
		  
			// apply Friction
		  velocity.x-=friction*velocity.x;
		  velocity.y-=friction*velocity.y;
		 
		  if(input.isKeyDown(Input.KEY_SPACE)){
			  if(!mySprite.isVisible1){
				mySprite.bull1=new Vector2f(mySprite.SpritePos.x+mySpriteImage.getWidth()/2,mySprite.SpritePos.y+mySpriteImage.getHeight()/2);
				bull1vel=new Vector2f((float)((15.5f)*Math.cos((float)(Math.toRadians(mySprite.Rotation)))),(float)((15.5f)*Math.sin((float)(Math.toRadians(mySprite.Rotation)))));
				mySprite.isVisible1=true;
			  }
			  else if(!mySprite.isVisible2 && (Math.sqrt(mySprite.bull1.distanceSquared(mySprite.SpritePos))>=250)){
				  
				  mySprite.bull2=new Vector2f(mySprite.SpritePos.x+mySpriteImage.getWidth()/2,mySprite.SpritePos.y+mySpriteImage.getHeight()/2);
				  bull2vel=new Vector2f((float)((15.5f)*Math.cos((float)(Math.toRadians(mySprite.Rotation)))),(float)((15.5f)*Math.sin((float)(Math.toRadians(mySprite.Rotation)))));
				  mySprite.isVisible2=true;
			  }
		  }
		  if(mySprite.isVisible1){
			  
			  mySprite.bull1.x-=bull1vel.x;
			  mySprite.bull1.y-=bull1vel.y;
			  if(Math.sqrt(mySprite.bull1.distanceSquared(mySprite.SpritePos))>=500){
				  mySprite.isVisible1=false;
			  }
		  }
		  
		  if(mySprite.isVisible2){
			 
			  mySprite.bull2.x-=bull2vel.x;
			  mySprite.bull2.y-=bull2vel.y;  
			  if(Math.sqrt(mySprite.bull2.distanceSquared(mySprite.SpritePos))>=500){
				  mySprite.isVisible2=false;
			  }
		  }
		
		 
		   
		   // Dealing with Velocity only
		   if(input.isKeyDown(Input.KEY_UP)){
			   forward=true;
			   velocity.x=(float)(tangentialVelocty*Math.cos((float)(Math.toRadians(mySprite.Rotation))));
			   velocity.y=(float)(tangentialVelocty*Math.sin((float)(Math.toRadians(mySprite.Rotation))));
		   }
		   else if(input.isKeyDown(Input.KEY_DOWN)){
			   forward=false;
			   velocity.x=(float)(tangentialVelocty*Math.cos((float)(Math.toRadians(mySprite.Rotation))));
			   velocity.y=(float)(tangentialVelocty*Math.sin((float)(Math.toRadians(mySprite.Rotation))));
			
		   }
		   for(int i=0;i<3;i++){
			   if(others[i].isAlive){
				   if(CollisionDetection(i)){
					   if(forward){
						   forward=false;
					   }
					   else{
						   forward=true;
					   }
				   }
			   }
		   }
		   if(forward){
			   if((mySprite.SpritePos.x-velocity.x)>-20 && (mySprite.SpritePos.x-velocity.x)<800)
			   mySprite.SpritePos.x-=velocity.x;
			   if((mySprite.SpritePos.y-velocity.y)>0 && (mySprite.SpritePos.y-velocity.y)<660)
			   mySprite.SpritePos.y-=velocity.y;
			   }
			   
			   else{
				   if((mySprite.SpritePos.x+velocity.x)>-20 && (mySprite.SpritePos.x+velocity.x)<800)
				   mySprite.SpritePos.x+=velocity.x;
				   if((mySprite.SpritePos.y+velocity.y)>0 && (mySprite.SpritePos.y+velocity.y)<660)
				   mySprite.SpritePos.y+=velocity.y;
				   }
		   
		// end velocity logic
		 if(mySprite.killme!=-1){
			if(an.isStopped())
			 {
				closeRequested();
			 }
		 }
		
		 for(int i=0;i<3;i++){
			 if(others[i].isAlive){
				 if(others[i].isVisible1){
					 if(HitBullet(mySprite.SpritePos.x,mySprite.SpritePos.y,(float)mySpriteImage.getWidth(),(float)mySpriteImage.getHeight(),others[i].bull1.x,others[i].bull1.y,(float)bullet.getWidth(),(float)bullet.getHeight())){
						 mySprite.killme=others[i].PlayerId;
						 others[i].isVisible1=false;
						 break;
					 }
				 }
				 if(others[i].isVisible2){
					 if(HitBullet(mySprite.SpritePos.x,mySprite.SpritePos.y,(float)mySpriteImage.getWidth(),(float)mySpriteImage.getHeight(),others[i].bull2.x,others[i].bull2.y,(float)bullet.getWidth(),(float)bullet.getHeight())){
						 mySprite.killme=others[i].PlayerId;
						 others[i].isVisible2=false;
						 break;
					 }
				 }
			 }
		 }
		 
		try{
			oos=new ObjectOutputStream(client.getOutputStream());
			oos.flush();
			oos.writeObject(mySprite);
		}catch(Exception e){System.exit(0);}
		
		
	}
	
		
		
	
	
	public boolean CollisionDetection(int x){
	    Rectangle r1=new Rectangle(mySprite.SpritePos.x,mySprite.SpritePos.y,(float)mySpriteImage.getWidth(),(float)mySpriteImage.getHeight());
	    Rectangle r2=new Rectangle(others[x].SpritePos.x,others[x].SpritePos.y,(float)othersImage[x].getWidth(),(float)othersImage[x].getHeight());
		return r1.intersects(r2);
	    }
	public boolean HitBullet(float x1,float y1,float w1,float h1,float x2,float y2,float w2,float h2){
		Rectangle r1=new Rectangle(x1,y1,w1,h1);
		Rectangle r2=new Rectangle(x2,y2,w2,h2);
		return r2.intersects(r1);
	}
	@Override
    public boolean closeRequested()
    {
		try{
	  oos=new ObjectOutputStream(client.getOutputStream());
	  oos.flush();
	  oos.writeObject(new Sprite(mySprite.PlayerId));
	}catch(Exception e){}
	JOptionPane.showMessageDialog(frame, "Game Over ","Game Over",1);
	JOptionPane.showMessageDialog(frame, PlayerName+" Your Score = "+mySprite.Score,"Scores",-1); 
	System.exit(0); // Use this if you want to quit the app.
      return false;
    }
	@Override
	public void actionPerformed(ActionEvent ac) {
		// TODO Auto-generated method stub
		if(ac.getActionCommand().contentEquals("Connect")){
			
			name=name_t.getText();
			port=port_t.getText();
			ip=ip_t.getText();
			if((ip.length()>0) && (name.length()>0) && (port.length()>0))
			{
				frame.dispose();
				yes=false;
			}
		}
	}
	
	
	public static void main(String arg[]){
		BattleGame s=new BattleGame();
		AppGameContainer app=null;
		try{
			app=new AppGameContainer(s,900,750,false);
			app.setIcon("res/icon.png");
			app.setTargetFrameRate(30);
			app.setShowFPS(false);
			app.start();
			}catch(SlickException e){}
	}	
	
	public void InitializeAnimation()throws SlickException{
		
		an=new Animation();
		an.addFrame(new Image("res/1.png"), 1000);
		an.addFrame(new Image("res/1.png"), 1000);
		an.addFrame(new Image("res/2.png"), 1000);
		an.addFrame(new Image("res/2.png"), 1000);
		an.addFrame(new Image("res/3.png"), 1000);
		an.addFrame(new Image("res/3.png"), 1000);
		an.addFrame(new Image("res/4.png"), 1000);
		an.addFrame(new Image("res/4.png"), 1000);
		an.addFrame(new Image("res/5.png"), 1000);
		an.addFrame(new Image("res/5.png"), 1000);
		an.addFrame(new Image("res/6.png"), 1000);
		an.addFrame(new Image("res/6.png"), 1000);
		an.addFrame(new Image("res/7.png"), 1000);
		an.addFrame(new Image("res/7.png"), 1000);
		an.addFrame(new Image("res/8.png"), 1000);
		an.addFrame(new Image("res/8.png"), 1000);
		an.addFrame(new Image("res/9.png"), 1000);
		an.addFrame(new Image("res/9.png"), 1000);
		an.addFrame(new Image("res/10.png"), 1000);
		an.addFrame(new Image("res/10.png"), 1000);
		an.addFrame(new Image("res/11.png"), 1000);
		an.addFrame(new Image("res/11.png"), 1000);
		an.addFrame(new Image("res/12.png"), 1000);
		an.addFrame(new Image("res/12.png"), 1000);
		an.addFrame(new Image("res/13.png"), 1000);
		an.addFrame(new Image("res/13.png"), 1000);
		an.addFrame(new Image("res/14.png"), 1000);
		an.addFrame(new Image("res/14.png"), 1000);
		an.setAutoUpdate(true);
		an.stopAt(27);
		
		an1=an.copy();
		an2=an.copy();
		an3=an.copy();
		an1.setAutoUpdate(true);
		an1.stopAt(27);
		an2.setAutoUpdate(true);
		an2.stopAt(27);
		an3.setAutoUpdate(true);
		an3.stopAt(27);

		
	}
	
	
	
}