package Modelo;

/**
 * Clase modelo del Terminal, contiene como atributo solo el nombre como un char.
 * 
 * @author Andrés Quintero
 * @author Juan Esteban Gutiérrez
 */
public class Terminal{
    private char nombre;

    /**
     * Constructor del Terminal, recibe como parámetro el nombre.
     * 
     * @param nombre 
     */
    public Terminal(char nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que devuelve el nombre de un terminal.
     * 
     * @return 
     */
    public char getNombre() {
        return nombre;
    }

    /**
     * Método que establece el nombre de un terminal de una gramática.
     * 
     * @param nombre 
     */
    public void setNombre(char nombre) {
        this.nombre = nombre;
    }
    
    
}
