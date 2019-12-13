package Controlador;

import Modelo.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import Modelo.utilidades.Utilidades;

/**
 * Clase principal, en ella se crea la gramática y se invocan los métodos correspondientes a la instancia de esta
 * Además, en esta clase se da la interacción con el usuario, pidiéndole los datos necesarios para la construcción 
 * de la gramática. En ella se da toda la información de la gramática: siguientes, primeros, selección y las clasificaciones
 * de la gramática, si es especial, lineal por la derecha, S, Q y LL(1).
 * 
 * @author Andrés Quintero
 * @author Juan Esteban Gutiérrez
 */
public class ReconocedorGramatica {

    static ArrayList<String> listaGramatica;

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        
        boolean bandera = false;

        do {
            String ladoIzquierdo;
            String ladoDerecho;
            int numeroProduccion = 1;
            String entrada = "";
            Scanner ingreso = new Scanner(System.in);
            Gramatica a = null;
            int decision = Utilidades.readInteger("¿Cómo desea ingresar la gramatica?: \n1.- Manualmente\n2.- Por Archivo\n");
            if (decision == 1) {
                System.out.println("Ingrese los No Terminales: (Ej: ABC)");
                String noTerminales = ingreso.next();
                a = new Gramatica(noTerminales);
                int cantidadProducciones = Utilidades.readInteger("Ingrese el número de producciones: \n");
                System.out.println("Ingrese las producciones en su orden respectivo: (Ej: A=bC) ");
                for (int i = 1; i <= cantidadProducciones; i++) {
                    entrada = ingreso.next();
                    ladoIzquierdo = retornaLadoIzquierdo(entrada);
                    ladoDerecho = retornaLadoDerecho(entrada);
                    a.agregarProduccion(ladoIzquierdo, ladoDerecho, numeroProduccion);
                    numeroProduccion++;
                }
            } else if (decision == 2) {
                System.out.println("Ingrese la ruta del archivo: (Ej: C:\\Users\\JuanGutierrez\\Downloads\\gramaticaPrueba3.txt\\)");
                String ruta = null;
                ruta = ingreso.next();
                leerArchivo(ruta);
                a = new Gramatica(listaGramatica.get(0));
                boolean primero = true;
                for (String s : listaGramatica) {
                    if (primero == true) {
                        primero = false;
                        continue;
                    }
                    entrada = s;
                    ladoIzquierdo = retornaLadoIzquierdo(entrada);
                    ladoDerecho = retornaLadoDerecho(entrada);
                    a.agregarProduccion(ladoIzquierdo, ladoDerecho, numeroProduccion);
                    numeroProduccion++;
                }
                System.out.println(a.imprimirGramatica());
            }

            a.calcularNoTerminalesAnulables();
            a.calcularProduccionesAnulables();
            a.primerosNoTerminales();
            a.calcularPrimerosProducciones();
            a.hacerSiguientes();
            a.calculaSeleccionProducciones();
            System.out.println("-----------------------------------");
            System.out.println("------No Terminales Anulables------");
            System.out.println("-----------------------------------");
            for (NoTerminal nta : a.getNoTerminalesAnulables()) {
                System.out.println(nta.getNombre() + " ");
            }
            
            System.out.println("-----------------------------------");
            System.out.println("------Producciones Anulables-------");
            System.out.println("-----------------------------------");
            for (Produccion p : a.getProducciones()) {
                if (p.isEsAnulable()) {
                    System.out.println(p.getNumeroDeLaProduccion());
                }
            }
            
            System.out.println("-----------------------------------");
            System.out.println("-----Primeros Cada No Terminal-----");
            System.out.println("-----------------------------------");
            for (NoTerminal nt : a.getListaDeNoTerminales()) {
                System.out.print("Primeros de " + nt.getNombre() + ": ");
                for (Character t : nt.getConjuntoDePrimeros()) {
                    System.out.print(t + " ");
                }
                System.out.print("\n");
            }
            System.out.println("-----------------------------------");
            System.out.println("-----Primeros Cada Producción-----");
            System.out.println("-----------------------------------");
            for (Produccion p : a.getProducciones()) {
                System.out.print("Primeros(" + p.getNumeroDeLaProduccion() + ") : ");
                for (Character t : p.getPrimerosDeLaProduccion()) {
                    System.out.print(t + " ");
                }
                System.out.print("\n");
            }
            

            System.out.println("-----------------------------------");
            System.out.println("----Siguientes Cada No Terminal----");
            System.out.println("-----------------------------------");
            for (NoTerminal nt : a.getListaDeNoTerminales()) {
                System.out.print("Siguientes de " + nt.getNombre() + ": ");
                for (Character t : nt.getConjuntoDeSiguientes()) {
                    System.out.print(t + " ");
                }
                System.out.print("\n");
            }

            System.out.println("-----------------------------------");
            System.out.println("-----Selección Cada Producción-----");
            System.out.println("-----------------------------------");
            for (Produccion p : a.getProducciones()) {
                System.out.print("Selección(" + p.getNumeroDeLaProduccion() + ") : ");
                for (Character c : p.getSeleccionDeLaProduccion()) {
                    System.out.print(c + " ");
                }
                System.out.print("\n");
            }
            System.out.println("-----------------------------------");
            System.out.println("-----Clasificaciones Gramática-----");
            System.out.println("-----------------------------------");
            if (a.EsDeLaFormaEspecial()) {
                System.out.println("- La gramática es de la forma especial");
            } else {
                System.out.println("- La gramática NO es de la forma especial");
            }
            if (a.esLinealPorLaDerecha()) {
                System.out.println("- La gramática  es lineal por la derecha");
            } else {
                System.out.println("- La gramática NO es lineal por la derecha");
            }
            if (a.esGramaticaS()) {
                System.out.println("- La gramática es S");
            } else {
                System.out.println("- La gramática NO es S");
            }
            if (a.esGramaticaQ()) {
                System.out.println("- La gramática es Q");
            } else {
                System.out.println("- La gramática NO es Q");
            }
            if (a.esLL1()) {
                System.out.println("- La gramática es LL(1)");
            } else {
                System.out.println("- La gramática NO es LL(1)");
            }
            
            System.out.println("¿Desea ingresar otra gramática? (S/N)");
            String respuesta = ingreso.next();
            if (respuesta.equalsIgnoreCase("S")) {
                bandera = true;
            }else {
                bandera = false;
            }
        } while (bandera);

    }

    /**
     * Método que retorna el lado derecho de una producción como un String. 
    * 
     * @param cadena
     * @return 
     */
    public static String retornaLadoDerecho(String cadena) {
        String li = "";
        for (int i = 2; i <= cadena.length() - 1; i++) {
            li += Character.toString(cadena.charAt(i));
        }
        return li;
    }

    /**
     * Método que retorna el lado izquierdo de una producción como un String, el lado izquierdo vendría a ser 
     * un No terminal.
     * 
     * @param cadena
     * @return 
     */
    public static String retornaLadoIzquierdo(String cadena) {
        return Character.toString(cadena.charAt(0));
    }

    /**
     * Método que se encarga de leer el archivo a través de una ruta que es ingresada por el usuario.
     * 
     * @param archivo
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void leerArchivo(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        listaGramatica = new ArrayList<String>();
        while ((cadena = b.readLine()) != null) {
            listaGramatica.add(cadena);
        }
        b.close();
    }

}
