import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Clase red implementando estructuras de datos
 * Java
 * @author Ian Eduardo Chavez Munioa,310027276. 
 * @author Eduardo Rubio Lezama,311011047. 
 * @version 1.0, oct 2020
 * @since Concurrente 2020-1
 */

public class Sopa implements Runnable{
	/*
	* Sopa es el atributo de tipo arreglo que ocupamos para 
	* modelar la sopa de letras. 
	*   
	*/ 	
	char [][] sopa; 
	/*
	* Sopa es el atributo de tipo arreglo que ocupamos para 
	* modelar la sopa de letras. 
	*   
	*/ 
	LinkedList<String> listaDepalabras; 
	
	/*
     *Constructor por omision
     */ 
	public Sopa(){
	
	}
	
	public Sopa (String sopaPlano, String sopaPalabra)throws FileNotFoundException, IOException{
		this.sopa = leeArchivo(sopaPlano);
		this.listaDepalabras = leePalabras(sopaPalabra);
	}
	
	public int tamanioMatriz(String archivo) throws FileNotFoundException, IOException{
  	String cadena = "";
  	FileReader f = new FileReader(archivo);
  	BufferedReader b = new BufferedReader(f); 
  	String[] values;
  	int tamanio = 0; 
  		while((cadena = b.readLine())!=null) {
    		tamanio++;
  		}
  		return tamanio; 
	}
	public char [][]  cargaMatriz(String letras, String archivo) throws FileNotFoundException, IOException{
		int it = 0; 
        int tamanio = tamanioMatriz(archivo);
        char[][] sopa =new char[tamanio][tamanio];
        char [] aCaracteres = letras.toCharArray();
        for (int i =0;i<sopa.length; i++) {
        	for (int j =0;j<sopa[0].length;j++ ) {
        		if(aCaracteres[it]!='\n'){
        			sopa[i][j] = aCaracteres[it];
        		}
        		it++;
        	}
        	it++;
        }
		return sopa;
	}
	
	public char [][] leeArchivo(String archivo) throws FileNotFoundException, IOException {
        String cadena = "";
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        String letras = ""; 
        while((cadena = b.readLine())!=null)
        	letras +=cadena+"\n";
        return cargaMatriz(letras,archivo);         
    }

   	public LinkedList<String> leePalabras(String archivo) throws FileNotFoundException, IOException{
   		String cadena = "";
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        String letras = ""; 
        //cadena = b.readLine();
        while((cadena = b.readLine())!=null)
        	letras +=cadena+"\n";
        return cargaPalabras(letras);
	}
   	public LinkedList<String> cargaPalabras(String letras) throws FileNotFoundException, IOException{
		String convertir = ""; 
        char [] aCaracteres = letras.toCharArray();
        LinkedList<String> listaDepalabras = new LinkedList<String>();; 
        for(int i =0;i<aCaracteres.length;i++){
        	if(aCaracteres[i]!='\n'){
        		convertir += aCaracteres[i]; 
        	}else{
        		listaDepalabras.add(convertir);
        		convertir=""; 
        	}
        }
        return listaDepalabras;
	} 	
    
    public String surSopa(char [][] sopa, String palabras, int i, int j){
		char [] aCaracteres;
		aCaracteres = palabras.toCharArray();
		for (int l = 0; l<aCaracteres.length ; l++) {
			if((i>sopa.length-1)) return null;
			if((sopa[i][j]!=aCaracteres[l]))	return null; 
			i++;   
		}
		return palabras;
	}
	public String esteSopa(char [][] sopa, String palabras, int i, int j){
		char [] aCaracteres; 
		aCaracteres = palabras.toCharArray();
		for (int l = 0; l<aCaracteres.length ; l++) {
			if((j>sopa[0].length-1))	return null;
			if ((sopa[i][j]!=aCaracteres[l])) return null; 
			j++;   
		}
		return palabras;
	}

	public String surEsteSopa(char [][] sopa, String palabras, int i, int j){
		char [] aCaracteres;
		aCaracteres = palabras.toCharArray();
		for (int l = 0; l<aCaracteres.length ; l++) {
			if(i>sopa.length-1 || j>sopa[0].length-1) return null; 
			if(sopa[i][j]!=aCaracteres[l])	return null;
			i++;
			j++;   
		}
		return palabras;
	}

	public String norteSopa(char [][] sopa, String palabras, int i, int j){
		char [] aCaracteres;
		aCaracteres = palabras.toCharArray();
		for (int l = 0; l<aCaracteres.length ; l++) {
			if(i<0)	return null;
			if(sopa[i][j]!=aCaracteres[l])	return null;
			i--;
		}
		return palabras;
	}

	public String oesteSopa(char [][] sopa, String palabras, int i, int j){
		char [] aCaracteres;
		aCaracteres = palabras.toCharArray();
		for (int l = 0; l<aCaracteres.length ; l++) {
			if(j<0) return null;
			if (sopa[i][j]!=aCaracteres[l])	return null;
			j--;
		}
		return palabras;
	}
	public String norEsteSopa(char [][] sopa, String palabras, int i, int j){
		char [] aCaracteres;
		aCaracteres = palabras.toCharArray();
		for (int l = 0; l<aCaracteres.length ; l++) {
			if(i<0 || j>sopa[0].length-1)	return null;
			if(sopa[i][j]!=aCaracteres[l])	return null;
			i--;
			j++;   
		}
		return palabras;
	}

	public String surOesteSopa(char [][] sopa, String palabras, int i, int j){
		char [] aCaracteres;
		aCaracteres = palabras.toCharArray();
		for (int l = 0; l<aCaracteres.length ; l++) {
			if(i>sopa[0].length-1 || j<0 )	return null;
			if(sopa[i][j]!=aCaracteres[l])	return null;
			i++;
			j--;   
		}
		return palabras;
	}

	public String norOesteSopa(char [][] sopa, String palabras, int i, int j){
		char [] aCaracteres;
		aCaracteres = palabras.toCharArray();
		for (int l = 0; l<aCaracteres.length ; l++) {
			if(i<0 || j<0 )	return null; 
			if(sopa[i][j]!=aCaracteres[l])	return null;
			i--;
			j--;   
		}
		return palabras;
	}
	
	public void agregaPalabras(String palabra, int i, int j, String direccion){
		String especificacion = ""; 
		if(palabra != null){
			especificacion = palabra + " (" + i +","+j+") " + direccion;
			System.out.println(especificacion);
		}
	}
	public void hiloNS(String palabra,int i, int j){
		agregaPalabras(surSopa(sopa,palabra,i,j),i,j,"S");
		agregaPalabras(norteSopa(sopa,palabra,i,j),i,j,"N");

		
	}
	public void hiloEO(String palabra,int i, int j){
		agregaPalabras(esteSopa(sopa,palabra,i,j),i,j,"E");
		agregaPalabras(oesteSopa(sopa,palabra,i,j),i,j,"O");
	}
	public void hiloNESO(String palabra,int i, int j){
		agregaPalabras(norEsteSopa(sopa,palabra,i,j),i,j,"NE");
		agregaPalabras(surOesteSopa(sopa,palabra,i,j),i,j,"SO");
	}
	public void hiloNOSE(String palabra,int i, int j){
		agregaPalabras(norOesteSopa(sopa,palabra,i,j),i,j,"NO");
		agregaPalabras(surEsteSopa(sopa,palabra,i,j),i,j,"SE");
	}
    public void run () {
    	String palabra = "";
    	for (int i = 0;i<sopa.length ;i++ ) {
    		for (int j = 0;j<sopa[0].length ;j++ ) {
    			for (int k =0;k<listaDepalabras.size() ; k++) {
    				palabra = listaDepalabras.get(k);
    				if (Thread.currentThread().getName() == "1"){
						hiloNS(palabra,i,j); 		
					}else if (Thread.currentThread().getName() == "2"){
						hiloEO(palabra,i,j);
					}else if (Thread.currentThread().getName() == "3"){
						hiloNESO(palabra,i,j);
					}else if (Thread.currentThread().getName() == "4"){
						hiloNOSE(palabra,i,j);
					}
    			}
    		}
    	}
    }
    public void sopaSecuencial(){
    	String palabra = "";
    	for (int i = 0;i<sopa.length ; i++ ) {
    		for (int j = 0;j<sopa[0].length ;j++ ) {
    			for (int k =0;k<listaDepalabras.size() ; k++) {
    				palabra = listaDepalabras.get(k);
    				hiloNS(palabra,i,j);
    				hiloEO(palabra,i,j);
    				hiloNESO(palabra,i,j);
    				hiloNOSE(palabra,i,j); 
    			}
    		}
    	}
    }
    
    public static void main(String[] args)  throws FileNotFoundException, IOException{
    	Sopa s = new Sopa(args[1],args[2]);
  		  		  
  		if(args[0].equals("s")){
  			System.out.println("Forma secuencial");
  		  	long TInicio, TFin, tiempo; //Variables para determinar el tiempo de ejecución
  			TInicio = System.currentTimeMillis(); //Tomamos la hora en que inicio el algoritmo y la almacenamos en la variable inicio
			s.sopaSecuencial();
			TFin = System.currentTimeMillis(); //Tomamos la hora en que finalizó el algoritmo y la almacenamos en la variable T
  			tiempo = TFin - TInicio; //Calculamos los milisegundos de diferencia
  			System.out.println("Tiempo de ejecución en milisegundos: " + tiempo); //Mostramos en pantalla el tiempo de ejecución en milisegundos
  		}if(args[0].equals("c")){
  			System.out.println("Forma concurrente");
    	  	long TInicio, TFin, tiempo; //Variables para determinar el tiempo de ejecución
  			TInicio = System.currentTimeMillis(); //Tomamos la hora en que inicio el algoritmo y la almacenamos en la variable inicio
    	  	Runnable r = s;
    	  	Thread t1 = new Thread (r, "1");
    	  	Thread t2 = new Thread (r, "2");
    	  	Thread t3 = new Thread (r, "3");
    	  	Thread t4 = new Thread (r, "4");
    	  	t1.start (); 
    	  	t2.start (); 
    	  	t3.start (); 
    	  	t4.start ();

    	  	try{
    	  		t1.join ();
    	  		t2.join ();
    	  		t3.join ();
    	  		t4.join ();
    	  	} catch(InterruptedException e) {

    	  	}
    	  	TFin = System.currentTimeMillis(); //Tomamos la hora en que finalizó el algoritmo y la almacenamos en la variable T
  			tiempo = TFin - TInicio; //Calculamos los milisegundos de diferencia
  			System.out.println("Tiempo de ejecución en milisegundos: " + tiempo); //Mostramos en pantalla el tiempo de ejecución en milisegundos
		}
    }

}
