import java.util.LinkedList; 

public class Primos {

	//Clase privada para mantener una 
	//sola instancia de la misma lista
	private class ListaDePrimos {
		//Lista final de primos
		public volatile int[] primos; 

		ListaDePrimos(int rango) {
			this.primos = new int[rango];
		}

		public void agrega(int n) {
			this.primos[n] = n;
		}

		public int[] getListaPrimos() {
			return this.primos; 
		}
	}

	/**
	* Clase para calcular primos de forma concurrente
	*/
	private class CalculaPrimos implements Runnable {

		private ListaDePrimos lista; 
		private int inicio_rango; 
		private int fin_rango; 

		public CalculaPrimos(ListaDePrimos lista, int inicio_rango, int fin_rango) {
			this.lista = lista;
			this.inicio_rango = inicio_rango; 
			this.fin_rango = fin_rango; 
		}

		@Override
		public void run() { 
			String name = Thread.currentThread().getName();
			// System.out.println("Started Thread " + name);
			// System.out.println(name + "- inicio_rango " + this.inicio_rango + " fin_rango " + this.fin_rango);
			for (int i = this.inicio_rango; i < fin_rango; i++) {
				if (esPrimo(i)) {
					this.lista.agrega(i);
				}
			}
		}

		/**
		* Comprueba si un entero n es primo o no. 
		*/
		boolean esPrimo(int num) {
			boolean flag = true;
			for(int i = 2; i <= num / 2; i++) {
			    if (num % i == 0) {
			        flag = false;
			        break;
			    }
			}

			return flag;
		}
	}
	

	/*Mensaje de error en argumentos*/
	public static void errorMsg() {
		System.out.print("\nError:  Argumentos Incorrectos\n");
		System.out.print("Ejemplo: Primos N p\n");
	}

	/**
	* Recibe el num de hilos y el rango y crea los Threads correspondientes
	*/
	public int[] calculaPrimos(int rango, int num_hilos) {
		//La lista de primos
		ListaDePrimos lista = new ListaDePrimos(rango);
		//Lista de Hilos 
		LinkedList<Thread> hilos = new LinkedList<Thread>();
		//Calculo los rangos 
		int inicio_rango = 0; 
		int salto = rango/num_hilos;
		int fin_rango = salto; 

		//Agrego p Threads
		for (int i = 0; i < num_hilos; i++) {
			CalculaPrimos c = new CalculaPrimos(lista, inicio_rango, fin_rango);
			Thread thread = new Thread(c, Integer.toString(i));
			hilos.add(thread);
			inicio_rango = fin_rango;	
			fin_rango += salto;
			//Es el ultimo 
			if (i+2 == num_hilos) 
				fin_rango += rango % num_hilos; 
		}

		try {
			for (Thread thread : hilos) {
				//Corro los Threads
				thread.start(); 
				thread.join();
			}
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}

		return lista.getListaPrimos();
	}

	//MAIN
	public static void main(String[] args) {
		if (args.length < 2) {
			Primos.errorMsg();
			return; 
		}
		
		Primos p = new Primos();
		//Obtengo los argumentos
		int rango =  Integer.parseInt(args[0], 10);
		int num_hilos =  Integer.parseInt(args[1]);

		if (num_hilos > rango) {
			System.err.println("ERROR: Rango debe de ser mayor al número de hilos!");
			return; 
		}

		long startTime = System.nanoTime();
		int[] res =  p.calculaPrimos(rango, num_hilos);
		long tiempo = System.nanoTime() - startTime;
		System.out.format("Tiempo de Ejecución %d%n", tiempo);
		for (int i = 0; i < res.length; i++) {
			if (res[i] != 0)
				System.out.print(res[i] + " ");
		}
		System.out.println();
	}
}