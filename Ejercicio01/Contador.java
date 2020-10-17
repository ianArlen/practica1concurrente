public class Contador implements Runnable {
  private int contador = 0;
  private static final int rondas = 10;

  public void run () {
    //Obtengo el nombre
    String nombre = Thread.currentThread().getName();
    int id = (nombre.equals("T1")) ? 1 : 2;
    try{
        for(int i = 0; i < Contador.rondas; i++) {

           //Penultimo de T2, debió llenar un buen el contador hasta 999999
          //Debe de esperar a que T1 despierte y escriba 1 en el contador
          if (id == 2 && (i+1) == Contador.rondas) {
            Thread.sleep(2000);
          }

          int tmp = contador;
          System.out.println("Thread: "+nombre+" tmp = " + contador);

           // Espera a Inicio de T1
          if (id == 2 && i == 0) {
            Thread.sleep(1000);
          }
          
          // T1 leyó 0 en contador, espera a T2 llene un buen
          if (id == 1 && i == 0) {
            Thread.sleep(2000);
          }

          //Segunda de T1, debió aumentar contador = 1, espera a T2 a que lea contador = 1
          if (id == 1 && i == 1) {
            // debug(nombre,  i,  contador,  tmp, "Segunda de T1, debió aumentar contador = 1, espera a T2 a que lea contador = 1");
            Thread.sleep(2000);
          }
          //Ultimo loop de T2, leyó contador como 1!!!!!
          if (id == 2 && (i+1) == Contador.rondas) {
            // debug(nombre,  i,  contador,  tmp, "Ultimo loop de T2, leyó contador como 1!!!!!");
            Thread.sleep(2000);
          }

          contador = tmp + 1;
          System.out.println("Thread: "+nombre+" contador = " + tmp + " + 1");
        }
      } catch (InterruptedException e) {
        System.out.println ("Se interrumpio el hilo!");
      }
  }

  public String toString() {
    return "" + this.contador;
  }

  public static void main (String[] args) {
    try {
      Contador c = new Contador();

      //Se crean dos hilos que ejecutan el metodo run()
      Thread t1 = new Thread (c, "T1");
      Thread t2 = new Thread (c, "T2");

      t1.start (); t2.start ();

      //Espera a que los hilos terminen
      t1.join (); t2.join ();

      //Imprime el contador
      System.out.println(c);
    } catch (InterruptedException e) {
      System.out.println ("Se interrumpio el hilo!");
    }
  }
}