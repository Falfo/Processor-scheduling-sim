public class Viewer extends Thread{
	int factor = 10;
	int d;
	int b;
	int c;
	Video v;
	boolean vcr;
	boolean waiting;
	boolean searching;
	int num;
	int otro;
	boolean shortest;
	long time_searching;
	long time_viewing;
	long time_waiting;
	int pol;
	int q;
	public Viewer(int demanda,int busq, int corr, int politica, boolean shortest, int quantum){
		this.pol = politica;//0 FCFS, 1 LJF, 2 SJF, 3 LCFS, 4 RR
		this.num = corr;
		if(this.num == 0){
			this.otro = 1;
		}else{
			this.otro = 0;
		}
		this.vcr = false;
		this.waiting = false;
		this.searching = true;
		this.shortest = shortest;
		this.d = demanda;
		this.b = busq;
		this.c = 0;
		this.time_searching = 0;
		this.time_waiting = 0;
		this.time_viewing = 0;
		this.q = quantum;
	}

	public void buscar(){
		System.out.println("Viewer "+ this.num + " buscando");
		this.vcr = false;
		this.waiting = false;
		this.searching = true;
		long inicio = System.currentTimeMillis();
		try{
			Thread.sleep((long)(factor*Video.random_number_generator(this.b)));
		}catch(Exception e){}
		long fin = System.currentTimeMillis();
		this.time_searching += fin - inicio;
	}

	public void waitFCFS(){
		//System.out.println("Viewer "+ this.num + " esperando");
		this.vcr = false;
		this.waiting = true;
		this.searching = false;
		boolean first = true;
		long inicio = System.currentTimeMillis();
		while(Colas.lista[this.otro].vcr){
			if(first){
				System.out.println("Viewer "+ this.num + " esperando");
				first=false;
			}
			System.out.print("");
		}
		long fin = System.currentTimeMillis();
		this.time_waiting += fin - inicio;
	}

	public void verFCFS(){
		System.out.println("Viewer "+ this.num + " viendo");
		this.vcr = true;
		this.waiting = false;
		this.searching = false;
		long inicio = System.currentTimeMillis();
		try{
			Thread.sleep((long)(factor*Video.random_number_generator(this.d)));
		}catch(Exception e){}
		long fin = System.currentTimeMillis();
		this.time_viewing += fin - inicio;
		this.c += 1;
	}

	public void verPreempted(){
		long time = 0;
		System.out.println("Viewer "+ this.num + " viendo");
		this.vcr = true;
		this.waiting = false;
		this.searching = false;
		long wait = 0;
		long inicio2 = System.currentTimeMillis();
		long fin2 = System.currentTimeMillis();
		long tiempo = factor*(long)Video.random_number_generator(this.d); //random
		long inicio = System.currentTimeMillis();
		long fin = System.currentTimeMillis();
		long calc = fin - inicio;
		boolean justchanged = false;
		while(calc < tiempo){
			if(!Colas.lista[this.otro].vcr){
				if (justchanged){
					System.out.println("Viewer "+ this.num + " viendo");
					justchanged = false;
				}
				this.vcr = true;
				this.waiting = false;
				this.searching = false;
				calc = System.currentTimeMillis() - inicio - wait;
			}else{
				inicio2 = System.currentTimeMillis();
				this.vcr = false;
				this.waiting = true;
				this.searching = false;
				System.out.println("Viewer "+ this.num + " esperando a " + this.otro);
				while(Colas.lista[this.otro].vcr){
					System.out.print("");
				}
				fin2 = System.currentTimeMillis();
				wait+=fin2-inicio2;
				justchanged=true;
			}
		}
		fin = System.currentTimeMillis();
		this.time_viewing = fin - inicio - wait;
		this.time_waiting += wait;
		this.c += 1;
	}

	public void verRR(){
		long time = 0;
		System.out.println("Viewer "+ this.num + " viendo");
		this.vcr = true;
		this.waiting = false;
		this.searching = false;
		long wait = 0;
		long inicio2 = System.currentTimeMillis();
		long fin2 = System.currentTimeMillis();
		long tiempo = factor*(long)Video.random_number_generator(this.d); //random
		long inicio = System.currentTimeMillis();
		long fin = System.currentTimeMillis();
		long calc = fin - inicio;
		long remaining_time = tiempo;
		boolean justchanged = false;
		long quanta = (long)(tiempo/this.q/factor);
		long cont = 0;
		while(calc < tiempo){
			if(!Colas.lista[this.otro].vcr){
				if (justchanged){
					cont++;
					System.out.println("Viewer "+ this.num + " viendo");
					justchanged = false;
				}
				this.vcr = true;
				this.waiting = false;
				this.searching = false;
				if(remaining_time>=this.q){
					try{
						Thread.sleep((long)this.q);
					}catch(Exception e){}
				}else{
					try{
						Thread.sleep((long)remaining_time);
					}catch(Exception e){}
				}
				calc = System.currentTimeMillis() - inicio - wait;
				remaining_time=tiempo-calc;
				this.vcr = false;
				this.waiting = true;
				this.searching = false;
				//System.out.print("");
				//System.out.print("");
				//System.out.print("");
				//System.out.print("");
				try{
					Thread.sleep((long)1);
					}catch(Exception e){}
			}else{
				inicio2 = System.currentTimeMillis();
				this.vcr = false;
				this.waiting = true;
				this.searching = false;
				System.out.println("Viewer "+ this.num + " esperando a " + this.otro);
				while(Colas.lista[this.otro].vcr){
					System.out.print("");
				}
				fin2 = System.currentTimeMillis();
				wait+=fin2-inicio2;
				justchanged=true;
			}
		}
		fin = System.currentTimeMillis();
		this.time_viewing = fin - inicio - wait;
		this.time_waiting += wait;
		this.c += 1;
	}

	public void run(){
		int inicio = 0;
		int fin = 0;
		while(Colas.lista[0].c+Colas.lista[1].c < Colas.videos){
			this.buscar();
			if(this.pol == 0){
				this.waitFCFS();
				this.verFCFS();
			}else if(this.pol == 1){
				if(!this.shortest){
					this.verFCFS();
				}else{
					this.verPreempted();
				}
			}else if(this.pol == 2){
				if(this.shortest){
					this.verFCFS();
				}else{
					this.verPreempted();
				}
			}else if(this.pol == 3){
				if(Colas.lista[this.otro].vcr){
					this.verFCFS();
				}else{
					this.verPreempted();
				}
			}else if(this.pol == 4){
				this.verRR();
			}
		}
	}
}