import java.util.*;

public class Video{
	int factor_tiempo = 10;

	/*static int id = 0;
	int time;
	int id;
	int time_left;
	int factor = 10;
	int d;
	Random rnd = new Random();

	public Video(int demanda){
		d = demanda;
		time = 0;
		time_left = time;
		id = nextID()
	}

	public double nextTime(){
		return Math.log(1/rnd.nextDouble())*this.d;
	}

	public static int nextID(){
		return id++;
	}*/

	public static double random_number_generator(int media){
		Random rand = new Random();
		return -Math.log(1-rand.nextDouble())*media;
	}
}