import java.util.*;

public class Colas{
	public static Viewer lista[] = new Viewer[2];
	public static int videos = 100;

	public static void main(String[] args) {
		long inicio = System.currentTimeMillis();
		boolean shortest;
		Scanner scan = new Scanner(System.in);
		System.out.println("Demanda 1");
		int demanda1 = scan.nextInt();
		System.out.println("Demanda 2");
		int demanda2 = scan.nextInt();
		System.out.println("Busqueda 1");
		int busqueda1 = scan.nextInt();
		System.out.println("Busqueda 2");
		int busqueda2 = scan.nextInt();
		if(demanda1 < demanda2){
			shortest = true;
		}else{
			shortest = false;
		}
		System.out.println("Politica 0 FCFS, 1 LJF, 2 SJF, 3 LCFS, 4 RR");
		int politica = scan.nextInt();
		int quantum=0;
		if (politica == 4){
			System.out.println("quantum?");
			quantum = scan.nextInt();
		}
		lista[0] = new Viewer(demanda1,busqueda1,0, politica, shortest,quantum); 
		lista[1] = new Viewer(demanda2,busqueda2,1, politica, !shortest, quantum); 
		lista[0].start();
		lista[1].start();
		while(Colas.lista[0].isAlive() && Colas.lista[1].isAlive());
		Colas.lista[0].stop();
		Colas.lista[1].stop();
		long fin = System.currentTimeMillis();
		long total_time = fin - inicio;
		double throughput1 = (double)(lista[0].c*1000)/(double)(total_time);
		double throughput2 = (double)(lista[1].c*1000)/(double)(total_time);
		double response_time = (lista[0].time_viewing + lista[0].time_waiting + lista[1].time_viewing + lista[1].time_waiting)/total_time;
		double response_time1 = (double)(lista[0].time_viewing + lista[0].time_waiting)/(double)total_time;
		double response_time2 = (double)(lista[1].time_viewing + lista[1].time_waiting)/(double)total_time;
		double line_length = (lista[0].time_waiting + lista[1].time_waiting)/total_time;
		double line_length1 = (double)(lista[0].time_waiting)/(double)total_time;
		double line_length2 = (double)(lista[1].time_waiting)/(double)total_time;
		System.out.println("Elapsed time: " + total_time);
		System.out.println("Vistos: " + lista[0].c);
		System.out.println("Vistos: " + lista[1].c);
		System.out.println("Throughput 1: "+ throughput1);
		System.out.println("Throughput 2: "+ throughput2);
		System.out.println("Response Time 1: "+ response_time1);
		System.out.println("Response Time 2: "+ response_time2);
		System.out.println("Line length: "+ (line_length1+line_length2));
		//System.out.println(lista[0].time_waiting);
		//System.out.println(lista[0].time_viewing);
		//System.out.println(lista[1].time_waiting);
		//System.out.println(lista[1].time_viewing);
	}
}