import java.util.*;

public class Cliente{
	static int id = 0;
	int time;
	int id;
	int time_left;
	Random rnd = new Random();
	public Cliente(int max_time){
		time = rnd.nextInt(max_time+1);
		time_left = time;
		id = nextID();
	}

	public static int nextID(){
		return id++;
	}

	//Devuelve verdadero si el 
	public boolean serve(){
		this.time_left = this.time_left-1
		return this.time_left == 0
	}

	public
}