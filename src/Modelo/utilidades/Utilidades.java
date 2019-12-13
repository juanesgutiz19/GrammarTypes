package Modelo.utilidades;

import java.util.Scanner;


public class Utilidades {

    public static Scanner in = new Scanner(System.in);

    public static int readInteger(String message) {
        String aux;
        do {
            System.out.print(message);
            aux = in.nextLine();
        } while (intvalidation(aux) == false);
        return (Integer.parseInt(aux));
    }

    public static double readDouble(String message) {
        String aux;
        do {
            System.out.print(message);
            aux = in.nextLine();
        } while (doubleValidation(aux) == false);
        return (Double.parseDouble(aux));
    }

    public static String readString(String message) {
        System.out.print(message);
        return (in.nextLine());
    }

    public static boolean intvalidation(String d) {
        try {
            int k = Integer.parseInt(d);
            return true;
        } catch (Exception e) {
            System.out.println("¡Escriba un número!");
            return false;
        }
    }

    public static boolean doubleValidation(String d) {
        try {
            double k = Double.parseDouble(d);
            return true;
        } catch (Exception e) {
            System.out.println("Dato invalido.");
            return false;
        }
    }

    public static int verificarIntervalo(int p, int q, String message1, String message2) {
        int dato;
        do {
            dato = Utilidades.readInteger(message1);
            if (p >= dato || dato >= q) {
                System.out.println(message2);
            }
        } while (p >= dato || dato >= q);
        return dato;
    }


}
