package Modelo;

import java.util.ArrayList;

/**
 * Clase que facilita el proceso de recorrer el lado derecho de una producción.
 * 
 * @author Andrés Quintero
 * @author Juan Esteban Gutiérrez
 */
public class Hilera {
   
    /**
     * String modelamiento: Crea un modelo de como esta compuesta la hilera. Ejemplo:
     * Si la hilera es AbCsrRDB, entonces el modelamiento es NTNTTNNN
     * Donde la N en la posicion 'i'  del modelamiento indican que en la posicion 'i' de la lista modelo 
     * hay un NO TERMINAL, analogamente para la T que indica que hay un TERMINAL
     */
    private String modelamiento;   
    private String hileraTerminalesYNoTerminales;
    private boolean secuenciaNula;
    private  ArrayList<Object> modelo;
    private ArrayList<NoTerminal> listaNoTerminalesGramatica;
       
    /**
     * Contructor de la clase hilera que recibe como parámetro la lista de terminales y no terminales
     * ingresada por el usuario. además modelamiento se establece como un String vacío, lo mismo con la
     * hilera de terminales y no terminales, el booleano de secuencia nula se inicializa como falso.
     * La lista modelo se inicializa vacía.
     * 
     * @param listaNoTerminalesGramatica 
     */
    public Hilera(ArrayList listaNoTerminalesGramatica) {
        this.modelamiento = "";
        this.hileraTerminalesYNoTerminales = "";
        this.secuenciaNula = false;
        this.modelo = new ArrayList<>();
        this.listaNoTerminalesGramatica = listaNoTerminalesGramatica;
    }    
    
    /**
     * Método que agregar la secuencia nula al lado derecho de la producción.
     */
    public void agregarNulo(){
        modelamiento = "#";
        hileraTerminalesYNoTerminales = "#";
        secuenciaNula = true;
        modelo.clear();
        modelo.add(null);
    }
    
    /**
     * Método que agrega un no terminal al lado derecho de una producción.
     * 
     * @param noTerminal 
     */
    public void agregarNoTerminal(char noTerminal){
        this.hileraTerminalesYNoTerminales += noTerminal;
        this.modelamiento +='N';
        for(NoTerminal nt:listaNoTerminalesGramatica){
            if(nt.getNombre() == noTerminal){
                modelo.add(nt);
                return;
            }
        }
    }
    
    /**
     * Método que agrega un terminal al lado derecho de una producción.
     * 
     * @param terminal 
     */
    public void agregarTerminal(char terminal){
        this.hileraTerminalesYNoTerminales+=terminal;
        this.modelamiento += 'T';
        this.modelo.add(new Terminal(terminal));
    }
    
    /**
     * Método que retorna verdadero si el lado derecho de una producción es la secuencia nula, de lo contrario retorna falso.
     * 
     * @return 
     */
    public boolean isSecuenciaNula() {
        return secuenciaNula;
    }

    /**
     * Método que retorna la lista modelo del lado derecho de una producción.
     * 
     * @return 
     */
    public ArrayList getModelo() {
        return modelo;
    }

    /**
     * Método que establece que el lado derecho de una producción es la secuencia nula.
     * 
     * @param secuenciaNula 
     */
    public void setSecuenciaNula(boolean secuenciaNula) {
        this.secuenciaNula = secuenciaNula;
    }
    
    /**
     * Método que retorna la hilera de terminales y no terminales del lado derecho de una producción.
     * 
     * @return 
     */
    public String getHileraTerminalesYNoTerminales(){
        return hileraTerminalesYNoTerminales;
    }

    /**
     * Método que establece la hilera de terminales y no terminales del lado derecho de una producción.
     * 
     * @param hileraTerminalesYNoTerminales 
     */
    public void setHileraTerminalesYNoTerminales(String hileraTerminalesYNoTerminales) {
        this.hileraTerminalesYNoTerminales = hileraTerminalesYNoTerminales;
    }

    /**
     * Método que retorna el String modelamiento.
     * 
     * @return 
     */
    public String getModelamiento() {
        return modelamiento;
    }

    /**
     * Método que establece el String modelamiento del lado derecho de una producción.
     * 
     * @param modelamiento 
     */
    public void setModelamiento(String modelamiento) {
        this.modelamiento = modelamiento;
    }    

    /**
     * Método que retorna verdadero si todos los símbolos que hay al lado derecho de una producción son terminales, 
     * falso de lo contrario.
     * 
     * @return 
     */
    public boolean todosSonTerminales(){
        if (modelamiento != "#") {
            if (hayAlgunNoTerminal()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Método que retorna verdadero si el lado derecho de una producción se compone por solamente terminales y al final tiene un 
     * no terminal.
     * 
     * @return 
     */
    public boolean terminalesMasUnNoTerminal() {
        if (modelamiento.charAt(0) != '#') {
            boolean noHaEncontradoNoTerminal = true;
            if (modelamiento.charAt(0) == 'T') {
                int i = 1;
                while (noHaEncontradoNoTerminal && i < modelamiento.length()) {
                    if (modelamiento.charAt(i) == 'N') {
                        if (i == modelamiento.length() - 1) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    i++;
                }
            }
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Método que retorna verdadero si en el lado derecho de alguna producción hay algún terminal.
     * Falso de lo contrario.
     * 
     * @return 
     */
    public boolean hayAlgunTerminal(){
        for(int i = 0; i <= this.modelamiento.length()-1;i++){
            if(this.modelamiento.charAt(i)=='T'){
                return true;
            }
        }
        return false;        
    }
    
    /**
     * Método que retorna verdadero si el lado derecho de una producción 
     * 
     * @return 
     */
    public boolean comienzaPorUnTerminal(){
        if (modelamiento.charAt(0) != '#') {
            return modelamiento.charAt(0) == 'T';
        }
        return false;
    }
    
    /**
     * Método que retorna verdadero si el lado derecho de una producción comienza por un terminal o por la secuencia nula.
     * 
     * @return 
     */
    public boolean comienzaPorUnTerminalOSecNula(){
        if (modelamiento.charAt(0) == 'T' || modelamiento.charAt(0) == '#') {
            return true;
        }
        return false;
    }
    
   /**
    * Método que retorna verdadero si al lado derecho de una producción hay algún no terminal.
    * 
    * @return 
    */
    public boolean hayAlgunNoTerminal(){
        for(int i = 0; i <= this.modelamiento.length()-1;i++){
            if(this.modelamiento.charAt(i)=='N'){
                return true;
            }
        }
        return false;        
    }   
}
