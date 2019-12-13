package Modelo;

import java.util.ArrayList;

/**
 * Clase modelo del no terminal que contiene el nombre del no terminar, además un booleano que nos dice 
 * si este es anulable o no, una lista de primeros y una lista de siguientes.
 * 
 * @author Andrés Quintero
 * @author Juan Esteban Gutiérrez
 */
public class NoTerminal{
    char nombre;
    boolean esAnulable;
    ArrayList<Character> conjuntoDePrimeros;
    ArrayList<Character> conjuntoDeSiguientes;

    /**
     * Constructor de no terminal, este inicializa el nombre del no terminal, además se inicializan las listas vacías.
     * Se establace el no terminal como falso.
     * 
     * @param nombre 
     */
    public NoTerminal(char nombre) {
        this.conjuntoDePrimeros = new ArrayList<>();
        this.conjuntoDeSiguientes = new ArrayList<>();
        this.nombre = nombre;
        this.esAnulable = false;
    }
    
    /**
     * Método que añade un terminal al conjunto de siguientes.
     * 
     * @param t 
     */
    public void setSiguiente(char t){
        this.conjuntoDeSiguientes.add(t);
    }    
    
    /**
     * Método que retorna verdadero si un caracter está en el conjunto de primeros, falso de lo contrario.
     * 
     * @param t
     * @return 
     */
    public boolean estaEnPrimeros(Character t){
        for(Character term: this.conjuntoDePrimeros){
            if(t == term){
                return true;
            }
        }
        return false;
    }

    /**
     * Método que retorna el nombre de un no terminal. Es un carácter.
     * 
     * @return 
     */
    public char getNombre() {
        return nombre;
    }

    /**
     * Método que establece el nombre de un no terminal.
     * 
     * @param nombre 
     */
    public void setNombre(char nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que determina si un no terminal es anulable, falso de contrario.
     * 
     * @return 
     */
    public boolean isEsAnulable() {
        return esAnulable;
    }

    /**
     * Método que establece si un no terminal es anulable o no.
     * 
     * @param esAnulable 
     */
    public void setEsAnulable(boolean esAnulable) {
        this.esAnulable = esAnulable;
    }

    /**
     * Método que retorna el conjunto de primeros correspondiente a un no terminal.
     * 
     * @return 
     */
    public ArrayList<Character> getConjuntoDePrimeros() {
        return conjuntoDePrimeros;
    }

    /**
     * Mpetodo que establece el conjunto de primeros de un no terminal. 
    * 
     * @param conjuntoDePrimeros 
     */
    public void setConjuntoDePrimeros(ArrayList<Character> conjuntoDePrimeros) {
        for(Character t:conjuntoDePrimeros){
            if(!this.conjuntoDePrimeros.contains(t)){
                this.conjuntoDePrimeros.add(t);
            }
        }
    }

    /**
     * Método que retorna el conjunto de siguientes de un no terminal.
     * 
     * @return 
     */
    public ArrayList<Character> getConjuntoDeSiguientes() {
        return conjuntoDeSiguientes;
    }

    /**
     * Método que establece el conjunto de siguientes de un no terminal.
     * 
     * @param conjunto 
     */
    public void setConjuntoDeSiguientes(ArrayList<Character> conjunto) {
        for(Character c: conjunto){
            if (!this.conjuntoDeSiguientes.contains(c)) {
                this.conjuntoDeSiguientes.add(c);
            }
        }
    }     
}
