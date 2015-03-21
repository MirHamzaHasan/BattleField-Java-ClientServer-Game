package gamePackage;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;


public class ServerThread extends Thread{
public static Sprite allPlayers[]=new Sprite[4];
private ObjectOutputStream oos;
private ObjectInputStream ois;
private Socket client;
public static int Scores[]=new int[4];


private int id;
public ServerThread(Socket client,Sprite first,Sprite second,Sprite third,Sprite fourth,int id){
	allPlayers[0]=first;
	allPlayers[1]=second;
	allPlayers[2]=third;
	allPlayers[3]=fourth;
	Scores[0]=allPlayers[0].Score;
	Scores[1]=allPlayers[1].Score;
	Scores[2]=allPlayers[2].Score;
	Scores[3]=allPlayers[3].Score;
	this.id=id;
	this.client=client;
	try {
		this.client.setTcpNoDelay(true);
	} catch (SocketException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		this.client.setReceiveBufferSize(65536);
		this.client.setSendBufferSize(65536);
		this.client.setPerformancePreferences(1, 0, 2);
	
	} catch (SocketException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	start();

	
}
public void run(){ 
	
	while(allPlayers[id].isAlive){
		for(int i=0;i<4;i++){
			allPlayers[i].Score=Scores[i];
		}
		try{
		oos=new ObjectOutputStream(client.getOutputStream());
		oos.flush();
		oos.writeObject(allPlayers[id]);
		for(int i=0;i<4;i++){
			if(i!=id){
				oos.flush();
				oos.writeObject(allPlayers[i]);
			}
		}
		}catch(Exception e){break;}
		
		
		try{
			ois=new ObjectInputStream(client.getInputStream());
			allPlayers[id]=(Sprite)ois.readObject();
			ServerClass.allPlayers[id]=allPlayers[id];
			
		}catch(Exception e){break;}
		if(allPlayers[id].killme!=-1){	
			Scores[allPlayers[id].killme]+=10;
			
		}
	}
	Scores[id]=0;
	ServerClass.allPlayers[id]=new Sprite(id);
}




}
