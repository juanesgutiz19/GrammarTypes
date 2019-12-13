package Modelo;

import java.util.ArrayList;

/**
 * Clase modelo de una producción, en ella se encuentra un no terminal al lado izquierdo y al lado
 * derecho se compone de una hilera, que de por sí contiene terminales y no terminales, es el lado derecho.
 * Además está el número de la producción, un booleano que determina si una producción es anulable o no
 * una lista de no terminales de la gramática, una lista de los primeros de la producción y los conjuntos 
 * de selección.
 * 
 * @author Andrés Quintero
 * @author Juan Esteban Gutiérrez
 */
public class Produccion {

    private NoTerminal ladoIzquierdo;
    private Hilera ladoDerecho;
    private int numeroDeLaProduccion;
    private boolean esAnulable;
    private ArrayList<NoTerminal> listaNoTerminalesGramatica;
    private ArrayList<Character> primerosDeLaProduccion;
    private ArrayList<Character> seleccionDeLaProduccion;

    /**
     * Método que devuelve los primeros de una producción.
     * 
     * @return 
     */
    public ArrayList<Character> getPrimerosDeLaProduccion() {
        return primerosDeLaProduccion;
    }

    /**
     * Método que establece los primeros de una producción.
     * 
     * @param primeros 
     */
    public void setListaPrimerosDeLaProduccion(ArrayList<Character> primeros) {
        for (Character t : primeros) {
            setPrimerosDeLaProduccion(t);
        }
    }

    /**
     * Método que retorna verdadera si una producción comienza al lado derecho con unterminal y un no terminal.
     * Además debe ser de tamaño 2 la hilera.
     * 
     * @return 
     */
    public boolean cumpleCondicionEspecial1() {
        if (ladoDerecho.getModelamiento().length() == 2) {
            if (ladoDerecho.getModelamiento().substring(0, 2).equals("TN")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que retorna verdadero si una producción tiene al lado derecho la secuencia nula.
     * 
     * @return 
     */
    public boolean cumpleCondicionEspecial2() {
        return String.valueOf(ladoDerecho.getModelamiento().charAt(0)).equals("#");
    }
    
    /**
     * Condición en donde debe haber hilera de solo terminales seguidas de un No terminal
     * @return 
     */
    public boolean cumpleCondicionLinealPorLaDerecha1(){
        if (ladoDerecho.getModelamiento().charAt(0) != '#' && ladoDerecho.getModelamiento().length() == 1) {
            return true;
        }
        
        if (ladoDerecho.terminalesMasUnNoTerminal()) {
            return true;
        }
        return false;
    }
    
    /**
     * Condición donde debe haber una hilera de solo terminales a la derecha.
     * @return 
     */
    public boolean cumpleCondicionLinealPorLaDerecha2(){
        return ladoDerecho.todosSonTerminales();
    }

    /**
     * Método que retorna verdadero si una gramática tiene a la derecha la secuencia nula.
     * 
     * @return 
     */
    public boolean cumpleCondicionLinealPorLaDerecha3(){
        if (String.valueOf(ladoDerecho.getModelamiento().charAt(0)).equals("#")) {
            return true;
        }
        return false;
    }

    /**
     * En la condición está incluida el hecho de que alguna producción lleve a secuencia nula. Si lo hace 
     * retorna falso.
     * @return 
     */
    public boolean cumpleCondicionGramaticaS1(){
        return ladoDerecho.comienzaPorUnTerminal();
    }
    
    /**
     * Método que retorna verdadero si a la derecha de una producción está la secuencia o comienza por un terminal.
     * 
     * @return 
     */
    public boolean cumpleCondicionGramaticaQ1(){
        return ladoDerecho.comienzaPorUnTerminalOSecNula();
    }
    
    
    /**
     * Método que establece los primeros de una producción.
     * 
     * @param primero 
     */
    public void setPrimerosDeLaProduccion(Character primero) {
        if (!this.primerosDeLaProduccion.contains(primero)) {
            this.primerosDeLaProduccion.add(primero);
        }
    }

    /**
     * Método que crear el conjunto de selección de la producción.
     */
    public void seleccionProduccion() {
        //this.seleccionDeLaProduccion = primerosDeLaProduccion;
        for (Character a: primerosDeLaProduccion) {
            seleccionDeLaProduccion.add(a);
        }
        if (isEsAnulable()) {
            for (Character c : ladoIzquierdo.getConjuntoDeSiguientes()) {
                if (!estaEnSeleccion(c, seleccionDeLaProduccion)) {
                    seleccionDeLaProduccion.add(c);
                }
            }
        }
    }

    /**
     * Método que retorna verdadero si un caracter está en una lista de carácteres, falso de los contrario.
     * 
     * @param c
     * @param seleccion
     * @return 
     */
    public boolean estaEnSeleccion(Character c, ArrayList<Character> seleccion) {
        for (Character x : seleccion) {
            if (x == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que retorna verdadero si un terminal está en una lista de terminales, falso de lo contrario.
     * 
     * @param t
     * @param lista
     * @return 
     */
    public boolean estaEnLaLista(Terminal t, ArrayList<Terminal> lista) {
        for (Terminal termi : lista) {
            if (t.getNombre() == termi.getNombre()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que nos devuelve el conjunto de selección de una producción.
     * 
     * @return 
     */
    public ArrayList<Character> getSeleccionDeLaProduccion() {
        return seleccionDeLaProduccion;
    }

    /**
     * Método que establece el conjunto de selección de una producción.
     * 
     * @param seleccionDeLaProduccion 
     */
    public void setSeleccionDeLaProduccion(ArrayList<Character> seleccionDeLaProduccion) {
        this.seleccionDeLaProduccion = seleccionDeLaProduccion;
    }

    /**
     * Constructor de la clase producción,inicializa los primeros y selección como vacios, se establecen
     * los no terminales de la gramática, el npumero de la producción y además el lado derecho.
     * 
     * @param listaNoTerminalesGramatica
     * @param numero 
     */
    public Produccion(ArrayList listaNoTerminalesGramatica, int numero) {
        this.primerosDeLaProduccion = new ArrayList<>();
        this.seleccionDeLaProduccion = new ArrayList<>();
        this.listaNoTerminalesGramatica = listaNoTerminalesGramatica;
        this.numeroDeLaProduccion = numero;
        this.ladoDerecho = new Hilera(listaNoTerminalesGramatica);
    }

    /**
     * Método que agrega un lado derecho a una producción, con base en lo que ingresó el usuario.
     * 
     * @param ladoDerecho 
     */
    public void agregarLadoDerecho(String ladoDerecho) {
        if (ladoDerecho.charAt(0) == '#') {
            this.ladoDerecho.agregarNulo();
        } else {
            for (int i = 0; i <= ladoDerecho.length() - 1; i++) {
                if (Character.isUpperCase(ladoDerecho.charAt(i))) {
                    this.ladoDerecho.agregarNoTerminal(ladoDerecho.charAt(i));
                } else {
                    this.ladoDerecho.agregarTerminal(ladoDerecho.charAt(i));
                }
            }
        }

    }

    /**
     * Método que sirve para agregar el lado izquierdo con base en lo que ingresó el usuario.
     * 
     * @param ladoIzquierdo 
     */
    public void agregarLadoIzquierdo(char ladoIzquierdo) {
        for (NoTerminal nt : listaNoTerminalesGramatica) {
            if (nt.nombre == ladoIzquierdo) {
                this.ladoIzquierdo = nt;
                if (this.numeroDeLaProduccion == 1) {
                    this.ladoIzquierdo.conjuntoDeSiguientes.add('$');
                }
                return;
            }
        }
    }

    /**
     * Método que retorna el número de una producción.
     * 
     * @return 
     */
    public int getNumeroDeLaProduccion() {
        return numeroDeLaProduccion;
    }

    /**
     * Método que establece el número de una producción.
     * 
     * @param numeroDeLaProduccion 
     */
    public void setNumeroDeLaProduccion(int numeroDeLaProduccion) {
        this.numeroDeLaProduccion = numeroDeLaProduccion;
    }

    /**
     * Método que devuelve el no terminal que está al lado derecho de una producción.
     * 
     * @return 
     */
    public NoTerminal getLadoIzquierdo() {
        return ladoIzquierdo;
    }

    /**
     * Método que establece el lado izquierdo de una producción.
     * 
     * @param ladoIzquierdo 
     */
    public void setLadoIzquierdo(NoTerminal ladoIzquierdo) {
        this.ladoIzquierdo = ladoIzquierdo;
    }

    /**
     * Método que retorna el lado derecho de una producción.
     * 
     * @return 
     */
    public Hilera getLadoDerecho() {
        return ladoDerecho;
    }

    /**
     * Método que establece el lado derecho de una producción.
     * 
     * @param ladoDerecho 
     */
    public void setLadoDerecho(Hilera ladoDerecho) {
        this.ladoDerecho = ladoDerecho;
    }

    /**
     * Método que retorna verdadero si una producción es anulable, falso de lo contrario.
     * 
     * @return 
     */
    public boolean isEsAnulable() {
        return esAnulable;
    }

    /**
     * Método que establece si una producción.
     * 
     * @param esAnulable 
     */
    public void setEsAnulable(boolean esAnulable) {
        this.esAnulable = esAnulable;
    }
}
