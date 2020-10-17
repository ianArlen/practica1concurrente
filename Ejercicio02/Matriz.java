import java.io.FileInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.util.Arrays; 


/**
* Clase para multiplicacion de Matrices 
* concurrente y secuencial.
*/
public class Matriz implements Runnable {

	//La matriz a multiplicar
	private int[][] matrix; 
	//El resultado de la multiplicacion
	private int[][] result; 

	/**
	* Constructor
	* Recibe el archivo con la matriz. 
	*/
	public Matriz(String archivo) {
		this.matrix = llenaMatriz(archivo); 
		this.result = new int[matrix.length][matrix.length];
	}


	public int[][] getResult() {
		return this.result;
	}

	//Para cuando sea concurrente
	@Override
	public void run() {
		// String name = Thread.currentThread().getName();
		// System.out.println("Starting Thread " + name);
		//Escribe en la matriz result
    	this.multiplicaConcurrente(name);
    }

	//Llena la matriz de números leyendo el archivo
	private static int[][] llenaMatriz(String archivo) {
		int[][] matrix = null; 

		try {
			// Abrir archivo
			Scanner scanner = new Scanner( new File(archivo) );
			String text = scanner.useDelimiter("\\A").next();
			scanner.close(); // Put this call in a finally block

			String[] lines = text.split("\n"); 
			
			//Presuponiendo que es n x n
			matrix = new int[lines.length][lines.length];

			//Lleno la matriz 
			for (int i = 0; i < lines.length; i++) {
				String[] curr_line = lines[i].split(" "); 
				for (int j = 0; j < curr_line.length; j++) {
					matrix[i][j] = Integer.parseInt(curr_line[j]);
				}
			}

		} catch (Exception e) {
			System.out.println("Error procesando el archivo: " + e.getMessage());
		}

		return matrix; 
	}

	/**
	* Multiplica una matriz por si misma
	* @return result la matriz resultante
	*/
	private int[][] multiplicaMatriz(int[][] matrix) {
		int[][] result = new int[matrix.length][matrix.length];

		//Por cada fila 
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < matrix.length; j++) {
				for (int k = 0; k < matrix.length; k++) {
					result[i][j] += matrix[i][k] * matrix[k][j];
				}
			}
		}

		return result; 
	}

	/*
	* Multiplica una matriz de manera secuencial
	* y escribe en un archivo producto.txt
	*/
	public int[][] multiplicaSecuencial() {
		//Multiplico Matriz
		int[][] result = this.multiplicaMatriz(this.matrix);
		return result;
	}

	public void multiplicaConcurrente(String threadName) {
		//Indices de los cuadrantes 
		//Inicio y final de i & inicio y final de j
		int ini_i = 0; 
		int ini_j = 0; 
		int fin_i = 0; 
		int fin_j = 0; 
		int n = this.matrix.length; //Tamaño matriz
		int mitad = n/2; //Mitad de la matriz

		//segun el cuadrante se asigan indices
		switch(threadName) {
			//cuadrante 1 
			case "1": 
				fin_i = mitad; 
				fin_j = mitad; 
				break; 

			case "2": 
				ini_i = mitad; 
				fin_i = n; 
				fin_j = mitad; 
				break; 

			case "3": 
				fin_i = mitad;
				ini_j = mitad; 
				fin_j = n; 
				break; 

			case "4":
				ini_i = mitad; 
				fin_i = n; 
				ini_j = mitad; 
				fin_j = n; 
				break; 
		}

		// System.out.println("ini_i : " + ini_i);
		// System.out.println("fin_i : " + fin_i);

		// System.out.println("ini_j : " + ini_j);
		// System.out.println("fin_j : " + fin_j);
		this.multiplicaCuadranteMatriz(ini_i, fin_i, ini_j, fin_j);
	}

	private void multiplicaCuadranteMatriz(int ini_i, int fin_i, int ini_j, int fin_j) {
	 	// Multiplico la matriz según los indices 
		for (int i = ini_i; i < fin_i; i++) {
			for (int j = ini_j; j < fin_j; j++) {
				for (int k = 0; k < this.matrix.length; k++) {
					this.result[i][j] += this.matrix[i][k] * this.matrix[k][j];
				}
			}
		}
	}

	/**
	* Escribe el archivo resultado. 
	* Recibe una matriz de enteros
	*/
	private void outputFileResult(int[][] matrix, long tiempo,String fileName) {
		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write("Tiempo de Ejecución: " + tiempo + "\n");
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix.length; j++) {
					writer.write(Integer.toString(matrix[i][j]) + " ");
				}
				writer.write("\n");
			}
			writer.close();

		} catch (Exception e) {
			System.out.println("Error procesando el archivo: " + e.getMessage());
		}    
	}

	/*Mensaje de error en argumentos*/
	public static void errorMsg() {
		System.out.println("\nError:  Argumentos Incorrectos\n");
		System.out.println("Ejemplo: java Matriz -c archivo.txt\n");
	}


	//MAIN
	public static void main(String[] args) {

		//Compruebo argumentos
		if (args.length < 2) {
			Matriz.errorMsg();
			return; 
		}

		//Obj Matriz
		Matriz matriz = new Matriz(args[1]);

		if (args[0].equals("-c")) {
			long startTime = System.nanoTime();
			//concurrente
			try {
				//Inicio 4 Threads Concurrentes! 
				Thread t1 = new Thread(matriz, "1");
				Thread t2 = new Thread(matriz, "2");
				Thread t3 = new Thread(matriz, "3");
				Thread t4 = new Thread(matriz, "4");
				//Los inicio
				t1.start();
				t2.start();
				t3.start();
				t4.start();
				//Espero a que acaben para escribir Resultado 
				t1.join();
				t2.join();
				t3.join();
				t4.join();

				long tiempo = System.nanoTime() - startTime;
							
				//Escribo resultado
				matriz.outputFileResult(matriz.getResult(), tiempo,"producto-concurrente.txt");
				System.out.format("Output: producto-concurrente.txt %n");
				
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			
			

		} else if (args[0].equals("-s")) {

			long startTime = System.nanoTime();
			//Multiplico de manera secuencial
			int[][] result = matriz.multiplicaSecuencial();
			long tiempo = System.nanoTime() - startTime;
			matriz.outputFileResult(result, tiempo, "producto.txt");
			System.out.format("Output: producto.txt %n");


			
		} else {
			Matriz.errorMsg();
		}
	}
}

