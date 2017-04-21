/*
MP03 Projecte
Gerard Rey González 1r ASIX
*/

package projecte.gui;

import java.util.Scanner;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class projecte1 {

    // Nombre màxim de caselles per a l'array
    private static final int MAX_BOSSES=20;
    //Array on guardarem la informació dels Bosses
    private static Boss[] array = new Boss[MAX_BOSSES];

    private static int opcio, opt, casella, aparicio;

    private static char yn;

    private static char[] atacs = {'m','a','c','h'};

    // Retorna el valor de l'array (Utilitzat per el GUI)
    public static Boss[] getArray() {
        return array;
    }
  
    //Retorna el valor màxim de caselles (Utilitzat per el GUI)
    public static int getMaxBoss(){
      return MAX_BOSSES;
    }
    
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {

        inicialitzar();
        do {
          printMenu();
          triaOpcio();
        } while (opcio!=0);

    }

    /**
     * Carrega el fitxer de guardat dins l'array
     */
    public static void inicialitzar() {
        
        for (int q = 0; q < array.length; q++) {
          array[q] = new Boss();
          array[q].setOmplit(false);
        }

        File f = new File("boss.dat");

        if(f.exists()) {

          ObjectInputStream dades = null;

          try {
            dades = new ObjectInputStream(new FileInputStream(f));

            for (int i = 0; i < array.length; i++) {
              Boss b = (Boss)dades.readObject();
              array[i] = b;
            }

            System.out.println("Done");

          } catch (ClassNotFoundException | IOException ex) {
            System.err.println("ERROR EN LLEGIR EL FITXER");
          } finally {
            try {
              if (dades != null) dades.close();
            } catch (IOException ex) {
              System.out.println("ERROR EN TANCAR EL FITXER");
            }
          }
        }
    }

    /**
     * Carrega l'array al fitxer de guardat i surt del programa
     */
    public static void sortirPrograma() {
        
        File f = new File("boss.dat");

        ObjectOutputStream dades = null;
        try {
          dades = new ObjectOutputStream(new FileOutputStream(f));

            for (Boss b : array) {
                dades.writeObject(b);
            }

        } catch (IOException ex) {
          System.err.println("ERROR EN ESCRIURE EL FITXER");
        } finally {
          try {
            if (dades != null) dades.close();
          } catch (IOException ex) {
            System.err.println("ERROR EN TANCAR EL FITXER");
          }
        }

        System.exit(0);
    }

    // Reordena l'array per a deixar les caselles buides excloses (Utilitzat per el GUI)
    public static void reordena() {

        for (int i = 0; i < array.length-1; i++) {
            if (!array[i].isOmplit()) {
                int j;
                for (j = i; j < array.length && !array[j].isOmplit(); j++);
                if (j<20) {
                  array[i].setNom(array[j].getNom());
                  array[i].setAparicio(array[j].getAparicio());
                  array[i].setZona(array[j].getZona());
                  array[i].setAtac(array[j].getAtac());
                  array[i].setTamany(array[j].getTamany());
                  array[i].setAnimes(array[j].getAnimes());
                  array[i].setDesc(array[j].getDesc());
                  array[i].setOmplit(true);
                  array[j].setOmplit(false);
                }
            }
        }

    }
    
    //
    private static void printMenu() {
      Scanner e = new Scanner(System.in);

      do {

        System.out.println("####Dark Souls Bosses####");
        System.out.println("0. Sortir ");
        System.out.println("1. Afegir Boss");
        System.out.println("2. Esborrar Boss");
        System.out.println("3. Modificar Boss");
        System.out.println("4. Llistar Boss");
        System.out.println("5. Ajuda");
        System.out.println("#########################");
        System.out.println("Elegeix una opció [0-4]: ");

        try {
          opcio = e.nextInt();
          break;
        } catch (java.util.InputMismatchException x) {
          System.err.println("\nEL VALOR INTRODUÏT NO ÉS VÀLID!\n");
          e.next();
        }
      } while(true);
    }

    private static void triaOpcio() {

      switch (opcio) {
        case 0: sortirPrograma(); break;
        case 1: newBoss(); break;
        case 2: esborrarBoss(); break;
        case 3: modificarBoss(); break;
        case 4: llistarBoss(); break;
      }

    }

  
    @SuppressWarnings("empty-statement")
    private static boolean casellaDisponible() {
      int i;
      for( i = 0; i < array.length && array[i].isOmplit(); i++);
      casella = i;
      return (i<array.length);
    }

    @SuppressWarnings("empty-statement")
    private static boolean bossExist() {
      int i;
      for (i = 0; i < array.length && !array[i].isOmplit(); i++);
      casella = i;
      return (i<array.length);
    }

    private static void noValue() {
      System.out.println("\nNo es troba cap boss introduït");
      System.out.println("###################\n");
    }

    private static void triaBoss() {
      Scanner entry = new Scanner(System.in);

      llistarBoss();

      do {
        System.out.println("Quin boss desitges esborrar?[1-" + MAX_BOSSES + "]");
        casella = entry.nextInt();
      } while (casella < 0 && casella > MAX_BOSSES);

      casella--;
    }

    private static void mostrarBoss() {
      System.out.println(array[casella]);
    }

    private static void tipusAtacs() {
      Scanner entry = new Scanner(System.in);

      do { // Demana les dades
          System.out.println("Tipus d'atacs (\"m\", \"a\" o \"c\"): ");
          System.out.println("Prem h per a mes ajuda");
          array[casella].setAtac(entry.skip("[\r\n]*").next().charAt(0)); // si les dades no són vàlides es repetirà el bucle fins que siguin correctes
          if (atacs[0] == array[casella].getAtac() || atacs[1] == array[casella].getAtac() || atacs[2] == array[casella].getAtac()) {
              break;
          } else if (atacs[3] == 'h') { // si premem 'h' es mostrarà l'ajuda
              System.out.println("#AJUDA#");
              System.out.println("m = magia \na = armes a distància \nc = cos a cos");
              System.out.println("#######");
          } else {
              System.out.println("El valor introduït no és vàlid. \n");
          }
      } while(true);
    }

    private static void setValor(int opcio) {
        do {
          Scanner e = new Scanner(System.in);
          try {
             switch (opcio) {
                 case 0: 
                     System.out.println("Introdueix el nom complet: ");
                     array[casella].setNom(e.skip("[\r\n]*").nextLine()); 
                     break;
                 case 1: 
                     System.out.println("Joc de la primera aparició (valor numèric): ");
                     array[casella].setAparicio(e.skip("[\r\n]*").nextInt()); 
                     break;
                 case 2: 
                     System.out.println("Zona on es troba: ");
                     array[casella].setZona(e.skip("[\r\n]*").nextLine()); 
                     break;
                 case 3: 
                     System.out.println("Tamany (en metres): ");
                     array[casella].setTamany(e.skip("[\r\n]*").nextDouble()); 
                     break;
                 case 4: 
                     System.out.println("Animes: ");
                     array[casella].setAnimes(e.skip("[\r\n]*").nextInt()); 
                     break;
                 case 5: 
                     System.out.println("Descripció: ");
                     array[casella].setDesc(e.skip("[\r\n]*").nextLine()); 
                     break;
             }
             break;
          } catch (java.util.InputMismatchException ex) {
             System.err.println("VALOR INVÀLID! Introdueix un valor vàlid");
  //           ex.printStackTrace();
          }
        } while (true);
    }

    private static void newBoss() {
      Scanner e = new Scanner(System.in);

      if (casellaDisponible()) {
          System.out.println("\n###Afegir un Boss###");

          setValor(0);

          do {
            setValor(1);
            aparicio = array[casella].getAparicio();
            if (aparicio < 1 || aparicio > 3) System.err.println("VALOR INVÀLID! Només existeixen 3 jocs en la saga");
          } while(aparicio < 1 || aparicio > 3);

          setValor(2);

          tipusAtacs();

          setValor(3);

          setValor(4);

          setValor(5);

          array[casella].setOmplit(true);
      } else {
          //En cas d'haver dades introduïdes anteriorment s'executaran les següents línies
          System.out.println("Les dades ja han estat introduïdes, si en vols posar més l'hauràs d'esborrar primer.");
          System.out.println("Vols esborrar les dades per introduïr de noves? (s/n)");
          yn = e.next().charAt(0);
          if(yn == 's') {
            llistarBoss();
            System.out.println("Quin boss desitges eliminar per a introduïr dades noves?");
            opt = e.nextInt();
            System.out.println("Estàs segur de que vols esborrar les dades del boss " + opt + "? (s/n)");
            yn = e.next().charAt(0);
            if (yn == 's') array[opt-1].setOmplit(false);
          }
      }
    }

    private static void esborrarBoss() {
      Scanner entry = new Scanner(System.in);

      System.out.println("\n###Esborrar Boss###");

      if (bossExist()) {

        triaBoss();

        if (!array[casella].isOmplit()) {
          System.out.println("\nEl boss elegit no existeix o ja es troba esborrat. Perfavor elegeix un boss diferent: ");
          entry.next();
          esborrarBoss();
        }

        System.out.println("S'esborraran les següents dades: \n");

        mostrarBoss();

        System.out.println("Estas segur d'esborrar les dades? (s/n)");
        yn = entry.next().charAt(0);
        if (yn == 's') {
            array[casella].setOmplit(false);
            System.out.println("Dades esborrades correctament");
            System.out.println("###################\n");
        } else {
            System.out.println("No s'han esborrat les dades");
            System.out.println("###################\n");
        }
      } else noValue();

    }

    private static void modificarBoss() {
      Scanner e = new Scanner(System.in);
      int opcioInt;

      System.out.println("###Modificar Boss###");

      if(!bossExist()) noValue();
      else {
          triaBoss();

          do {
              System.out.println("Tria el camp que vols modificar: \n");
              System.out.println("0. Tornar al menú");
              System.out.println("1. Nom complet: " + array[casella].getNom());
              System.out.println("2. Entrega de la primera aparició: " + array[casella].getAparicio());
              System.out.println("3. Zona on es troba: " + array[casella].getZona());
              System.out.println("4. Tipus d'atacs: " + array[casella].getAtac());
              System.out.println("5. Tamany: " + array[casella].getTamany());
              System.out.println("6. Animes: " + array[casella].getAnimes());
              System.out.println("7. Descripció: " + array[casella].getDesc() + "\n");
              System.out.println("Camp a modificar [0-7]: ");
              opcioInt = e.nextInt();
              if (opcioInt == 0) {
                  System.out.println("####################\n");
                  break;
              } else if (opcioInt < 0 || opcioInt > 7){
                  System.err.println("VALOR INVÀLID!");
              } else {
                  System.out.println("Introdueix el nou valor: ");
                  switch(opcioInt){
                      case 1:
                          setValor(0);
                          break;
                      case 2:
                          do {
                              setValor(1);
                              aparicio = array[casella].getAparicio();
                          } while(aparicio < 1 || aparicio > 3);
                          break;
                      case 3:
                          setValor(2);
                          break;
                      case 4:
                          tipusAtacs();
                          break;
                      case 5:
                          setValor(3);
                          break;
                      case 6:
                          setValor(4);
                          break;
                      case 7:
                          setValor(5);
                          break;
                  }
                  System.out.println("Vols modificar un altre valor? (s/n)");
                  yn = e.next().charAt(0);
                  if (yn == 'n') break;
                  System.out.println("####################\n");
              }
          } while(true);
      }
    }

    private static void llistarBoss() {

      if(bossExist()) {
        for (casella = 0; casella < array.length; casella++) {
          if (array[casella].isOmplit()) {
            System.out.println("\nBoss num " + (casella+1));
            System.out.println("#########################");
            mostrarBoss();
          }
        }
      } else noValue();

    }



}
