package gamePackage;
import java.io.Serializable;
import org.newdawn.slick.geom.*;

// i use this as a Structure
public class Sprite implements Serializable{
private static final long serialVersionUID = 1L;
public int PlayerId;
public boolean isAlive;
public Vector2f SpritePos;
public float Rotation;
public int Score;
public Vector2f bull1;
public Vector2f bull2;
public boolean isVisible1;
public boolean isVisible2;
public int killme;


public Sprite(){
	PlayerId=-1;
	isAlive=false;
	SpritePos=new Vector2f(0,0);
	Rotation=0.0f;
	
}
public Sprite(int id){
	killme=-1;
	Score=0;
	PlayerId=id;
	isAlive=false;
	isVisible1=false;
	isVisible2=false;
	if(id==0){
	SpritePos=new Vector2f(0, 650);
	Rotation=90;}
	if(id==1){
		SpritePos=new Vector2f(780, 650);
		Rotation=90;}
	if(id==2){
		SpritePos=new Vector2f(0,0);
		Rotation=270;}
	if(id==3){
		SpritePos=new Vector2f(660, 0);
		Rotation=270;}
	

}
}
