package gamePackage;
import java.net.*;
import java.io.*;


public class ServerClass extends Thread{
	
	private ServerSocket server;
	private Socket temp;
	private ObjectOutputStream oos;
	private int id=0;
	public static Sprite allPlayers[]=new Sprite[4];
	
	
		

	public ServerClass() {
		
		id=0;
		try{
			for(int i=0;i<4;i++){
			allPlayers[i]=new Sprite(i);
			}
			server=new ServerSocket(8000);
			//start();
		}catch(Exception e){}
		//start();
		while(true){
		try{
			
			temp=server.accept();
			
			for(id=0;id<4;id++){
				if(!(allPlayers[id].isAlive)){
					break;
				}
				
			}
			//System.out.println(""+id);
			if(id<4){
			allPlayers[id].isAlive=true;
			
			oos=new ObjectOutputStream(temp.getOutputStream());
			oos.flush();
			oos.writeObject(allPlayers[id]);
			
			for(int i=0;i<4;i++){
				if(id!=i){
					oos.writeObject(allPlayers[i]);
				}
			}
			new ServerThread(temp,allPlayers[0],allPlayers[1],allPlayers[2],allPlayers[3],id);
			}
			else{
				temp.close();
			}
			}catch(Exception e){}
			
			id=0;
			
		}// end while
				
	}

	public void run(){
		
		
	}
	public static void main(String[] args) {
	
		new ServerClass();
		// TODO Auto-generated method stub
	
	}

}
