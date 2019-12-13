package Modelo;

import java.util.ArrayList;

/**
 * Clase modelo de la gramática, en ella se encuentran todos los atributos o métodos correspondientes.
 * Se tiene lista de producciones, no terminales anulables, producciones anulables, lista de no terminales, 
 * un conjunto llamado procesados que fue creado para facilitar el método de siguientes y por último, la cantidad 
 * de producciones.
 * 
 * @author Andrés Quintero
 * @author Juan Esteban Gutiérrez
 */
public class Gramatica {

    private ArrayList<Produccion> producciones;
    private ArrayList<NoTerminal> noTerminalesAnulables;
    private ArrayList<Produccion> produccionesAnulables;
    private ArrayList<NoTerminal> listaDeNoTerminales;
    private ArrayList<NoTerminal> listaNoTerminalesNoAnulables;
    ArrayList<NoTerminal> procesados;
    private int cantidadProducciones;

    /**
     * Constructor de la gramática, se inicializan como vacías las listas de la clase y la cantidad de producciones se establecen cero.
     * Además se crean los No Terminales que se envían como parámetro.
     * 
     * @param noTerminales 
     */
    public Gramatica(String noTerminales) {
        this.producciones = new ArrayList<>();
        this.noTerminalesAnulables = new ArrayList<>();
        this.producciones = new ArrayList<>();
        this.listaDeNoTerminales = new ArrayList<>();
        this.listaNoTerminalesNoAnulables = new ArrayList<>();
        this.cantidadProducciones = 0;
        this.produccionesAnulables = new ArrayList<>();
        this.procesados = new ArrayList<>();
        crearNoTerminales(noTerminales);
    }

    /**
     * Método que agrega los no terminales que ingresó el usuario a la lista de no terminales de la clase.
     * 
     * @param nt 
     */
    public void crearNoTerminales(String nt) {
        for (int i = 0; i <= nt.length() - 1; i++) {
            listaDeNoTerminales.add(new NoTerminal(nt.charAt(i)));
        }
    }

    /**
     * Método que sirve para obtener una i - ésima producción en la lista de producciones.
     * 
     * @param i
     * @return 
     */
    public Produccion getProduccion(int i) {
        return producciones.get(i);
    }

    /**
     * Método que agrega una producción ingresada por el usuario a la lista de producciones de la clase.
     * Además, si el terminal no está en los no anulables, se añade a la lista de no terminales no anulables.
     * 
     * @param ladoIzquierdo
     * @param ladoDerecho
     * @param numeroProduccion 
     */
    public void agregarProduccion(String ladoIzquierdo, String ladoDerecho, int numeroProduccion) {
        Produccion p = new Produccion(this.listaDeNoTerminales, numeroProduccion);
        p.agregarLadoIzquierdo(ladoIzquierdo.charAt(0));
        p.agregarLadoDerecho(ladoDerecho);
        this.producciones.add(p);
        if (!p.isEsAnulable()) {
            if (!estaEnLosNoAnulables(p.getLadoIzquierdo())) {
                this.listaNoTerminalesNoAnulables.add(p.getLadoIzquierdo());
            }
        }
        cantidadProducciones++;
    }

    /**
     * Retorna verdadero si es de la forma especial, falso de lo contrario. 
     * Este método contiene dos condiciones. Se especificarán en cada método.
     * 
     * @return 
     */
    public boolean EsDeLaFormaEspecial() {
        for (Produccion p : producciones) {
            if (!p.cumpleCondicionEspecial1() && !p.cumpleCondicionEspecial2()) {
                return false;

            }
        }
        return true;
    }

    /**
     * Retorna verdadero si es lineal por la derecha, falso de lo contrario. 
     * Este método contiene tres condiciones. Se especificarán en cada método.
     * 
     * @return 
     */
    public boolean esLinealPorLaDerecha() {
        for (Produccion p : producciones) {
            if (!p.cumpleCondicionLinealPorLaDerecha1() && !p.cumpleCondicionLinealPorLaDerecha2() && !p.cumpleCondicionLinealPorLaDerecha3()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retorna verdadero si es S, falso de lo contrario. 
     * Este método contiene dos condiciones. Se especificarán en cada método.
     * 
     * @return 
     */
    public boolean esGramaticaS() {
        for (Produccion p : producciones) {
            if (!p.cumpleCondicionGramaticaS1()) {
                return false;
            }
        }
        return cumpleCondicionGramaticaS2();
    }

    /**
     * El método retorna verdadero si para las producciones en las que el lado izquierdo sea el mismo, comiencen con 
     * un terminal distinto al lado derecho.
     * 
     * @return 
     */
    public boolean cumpleCondicionGramaticaS2() {
        for (int i = 0; i < producciones.size(); i++) {
            char noTerminalActual = producciones.get(i).getLadoIzquierdo().getNombre();
            char ladoDerechoTerminal1 = producciones.get(i).getLadoDerecho().getHileraTerminalesYNoTerminales().charAt(0);
            ArrayList<Character> listaTemporal = new ArrayList<>();
            listaTemporal.add(ladoDerechoTerminal1);
            for (int j = 0; j < producciones.size(); j++) {
                if ((producciones.get(j).getLadoIzquierdo().getNombre() == noTerminalActual) && (producciones.get(i).getNumeroDeLaProduccion() != producciones.get(j).getNumeroDeLaProduccion())) {
                    char ladoDerechoTerminal2 = producciones.get(j).getLadoDerecho().getHileraTerminalesYNoTerminales().charAt(0);
                    listaTemporal.add(ladoDerechoTerminal2);
                }
            }
            if (hayRepetidos(listaTemporal)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Algoritmo que cuenta las veces que aparece un caracter en una lista de carácteres.
     * Retorna la ocurrencia.
     * 
     * @param lista
     * @param c
     * @return 
     */
    public static int cuentaOcurrencia(ArrayList<Character> lista, Character c) {
        int count = 0;
        for (Character caracter : lista) {
            if (caracter == c) {
                count++;
            }
        }
        return count;
    }

    /**
     * Método que retorna verdadero si hay carácteres repetidos en una lista.
     * 
     * @param lista
     * @return 
     */
    public static boolean hayRepetidos(ArrayList<Character> lista) {
        for (Character c : lista) {
            if (cuentaOcurrencia(lista, c) > 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que devuelve las producciones de una gramática.
     * 
     * @return 
     */
    public ArrayList<Produccion> getProducciones() {
        return producciones;
    }

    /**
     * Método que establece las producciones de una gramática.
     * 
     * @param producciones 
     */
    public void setProducciones(ArrayList<Produccion> producciones) {
        this.producciones = producciones;
    }

    /**
     * Método que devuelve los no terminales anulables de la gramática.
     * 
     * @return 
     */
    public ArrayList<NoTerminal> getNoTerminalesAnulables() {
        return noTerminalesAnulables;
    }

    /**
     * Método que establece los no terminales no anulables de una gramática.
     * 
     * @param noTerminalesAnulables 
     */
    public void setNoTerminalesAnulables(ArrayList<NoTerminal> noTerminalesAnulables) {
        this.noTerminalesAnulables = noTerminalesAnulables;
    }

    /**
     * El método retorna verdadero si para las producciones en las que el lado izquierdo sea el mismo, comiencen con 
     * un terminal distinto al lado derecho. Similar a como lo hace con la gramática S.
     * 
     * @return 
     */
    public boolean cumpleCondicionGramaticaQ2() {
        for (int i = 0; i < producciones.size(); i++) {
            char noTerminalActual = producciones.get(i).getLadoIzquierdo().getNombre();
            char ladoDerechoTerminal1 = producciones.get(i).getLadoDerecho().getHileraTerminalesYNoTerminales().charAt(0);
            ArrayList<Character> listaTemporal = new ArrayList<>();
            listaTemporal.add(ladoDerechoTerminal1);
            for (int j = 0; j < producciones.size(); j++) {
                if ((producciones.get(j).getLadoIzquierdo().getNombre() == noTerminalActual) && (producciones.get(i).getNumeroDeLaProduccion() != producciones.get(j).getNumeroDeLaProduccion())) {
                    char ladoDerechoTerminal2 = producciones.get(j).getLadoDerecho().getHileraTerminalesYNoTerminales().charAt(0);
                    listaTemporal.add(ladoDerechoTerminal2);
                }
            }
            if (hayRepetidos(listaTemporal)) {
                return false;
            }
        }
        return true;

    }

    /**
     * Método que retorna verdadero si en una gramática en una producción que lleve a secuencia nula, 
     * el conjunto de selección de la producción del no terminal que está a la izquierda, es disyunto con los 
     * conjuntos de selección de de las producciones con ese mismo no terminal a la izquierda.
     * 
     * @return 
     */
    public boolean cumpleCondicionGramaticaQ3() {
        for (int i = 0; i < producciones.size(); i++) {
            if (producciones.get(i).getLadoDerecho().getModelamiento().charAt(0) == '#') {
                ArrayList<Character> listaTemporal = new ArrayList<>();
                char noTerminalActual = producciones.get(i).getLadoIzquierdo().getNombre();
                for (int j = 0; j < producciones.size(); j++) {
                    if (producciones.get(j).getLadoIzquierdo().getNombre() == noTerminalActual) {
                        for (Character c : producciones.get(j).getSeleccionDeLaProduccion()) {
                            listaTemporal.add(c);
                        }
                    }
                }
                if (hayRepetidos(listaTemporal)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Método que retorna verdadero si una gramática es S, falso de lo contrario.
     * Hay tres condiciones que son especificadas en cada método.
     * 
     * @return 
     */
    public boolean esGramaticaQ() {
        int count = 0;
        
        for (Produccion p : producciones) {
            if (p.getLadoDerecho().isSecuenciaNula()) {
                count++;
            }
            if (!p.cumpleCondicionGramaticaQ1()) {
                return false;
            }
        }
        if (count == 0) {
            return false;
        }
        if (cumpleCondicionGramaticaQ2() && cumpleCondicionGramaticaQ3()) {
            return true;
        }
        return false;
    }

    /**
     * Método que nos retorna verdadero si una gramática es LL(1) o no.
     * Básicamente para que sea LL(1), los conjuntos de selección de las producciones cuyo símbolo
     * de lado izquierdo son iguales deben ser disyuntos.
     * 
     * @return 
     */
    public boolean esLL1() {
        int count = 0;
        for (int i = 0; i < producciones.size(); i++) {            
            if (producciones.get(i).getLadoDerecho().isSecuenciaNula()) {
                count++;
            }            
            ArrayList<Character> listaTemporal = new ArrayList<>();
            char noTerminalActual = producciones.get(i).getLadoIzquierdo().getNombre();
            for (int j = 0; j < producciones.size(); j++) {
                if (producciones.get(j).getLadoIzquierdo().getNombre() == noTerminalActual) {
                    for (Character c : producciones.get(j).getSeleccionDeLaProduccion()) {
                        listaTemporal.add(c);
                    }
                }
            }
            if (hayRepetidos(listaTemporal)) {
                return false;
            }
        }
        if (count == 0) {
                return false;
            }
        return true;
    }

    /**
     * Método que devuelve las producciones anulables de la gramática.
     * 
     * @return 
     */
    public ArrayList<Produccion> getProduccionesAnulables() {
        return produccionesAnulables;
    }

    /**
     * Método que establece las producciones anulables de la gramática.
     * 
     * @param produccionesAnulables 
     */
    public void setProduccionesAnulables(ArrayList<Produccion> produccionesAnulables) {
        this.produccionesAnulables = produccionesAnulables;
    }

    /**
     * Método que añade no terminales a la lista.
     */
    public void hacerListaDeTerminales() {
        for (Produccion p : this.getProducciones()) {
            if (!estaEnLaLista(p.getLadoIzquierdo(), this.listaDeNoTerminales)) {
                this.listaDeNoTerminales.add(p.getLadoIzquierdo());
            }
        }
    }

    /**
     * Método que imprime la gramática, fue usado más que todo para pruebas.
     * 
     * @return 
     */
    public String imprimirGramatica() {
        String gram = "";
        for (Produccion p : producciones) {
            gram += p.getNumeroDeLaProduccion() + " " + p.getLadoIzquierdo().getNombre() + "=" + p.getLadoDerecho().getHileraTerminalesYNoTerminales() + "\n";
        }
        return gram;
    }

    /**
     * Método que se encarga de sacar los siguientes de un no terminal.
     * 
     */
    public void hacerSiguientes() {
        for (NoTerminal nt : this.listaDeNoTerminales) {
            siguientes(nt);
        }
    }

    /**
     * Método que calcula los conjuntos de selección de las producciones.
     * 
     */
    public void calculaSeleccionProducciones() {
        for (Produccion o : producciones) {
            o.seleccionProduccion();
        }
    }

    /**
     * Método que saca los siguientes de un no terminal.
     * Lo más enredado del método fue definir el punto de parada, puesto que 
     * en cierto momento el algoritmo nos mfuncionaba para practicamente cualquier 
     * gramática pero ingresamos la siguiente:
        A=aBC
        A=DbA
        B=#
        B=bAB
        C=cC
        C=DdB
        D=#
        D=eE
        E=BD
        E=f
     * Al ingresarla y buscar los siguientes se quedaba en un ciclo infinito en la segundo producción
     * lo que nos llevó a caer en cuenta y añadir las líneas de código necesarias para el funcionamiento del
     * método. Usamos una lista de procesados, para saber cuáles no terminales ya fueron tratados o no.
     * 
     * @param nt
     * @return 
     */
    public ArrayList<Character> siguientes(NoTerminal nt) {
        ArrayList<Character> conjuntoSiguientes = nt.getConjuntoDeSiguientes();
        this.procesados.add(nt);
        for (Produccion p : this.producciones) {
            if (p.getLadoDerecho().getModelo().contains(nt)) {
                for (int i = 0; i <= p.getLadoDerecho().getModelamiento().length() - 1; i++) {
                    if (p.getLadoDerecho().getModelamiento().charAt(i) == 'N') {
                        NoTerminal noTerm = (NoTerminal) p.getLadoDerecho().getModelo().get(i);
                        if (noTerm == nt) {
                            boolean estaListo = false;
                            int j = 1;
                            ArrayList<Character> sig;
                            if (i == p.getLadoDerecho().getModelamiento().length() - 1) {
                                if (nt != p.getLadoIzquierdo()) {
                                    if (!procesados.contains(p.getLadoIzquierdo())) {
                                        sig = siguientes(p.getLadoIzquierdo());
                                    } else {
                                        sig = p.getLadoIzquierdo().getConjuntoDeSiguientes();
                                    }
                                    nt.setConjuntoDeSiguientes(sig);
                                    for (Character c : sig) {
                                        if (!conjuntoSiguientes.contains(c)) {
                                            conjuntoSiguientes.add(c);
                                        }
                                    }
                                    estaListo = true;
                                }
                            }
                            while (!estaListo && (j + i <= p.getLadoDerecho().getModelamiento().length() - 1)) {
                                estaListo = true;
                                if (i == p.getLadoDerecho().getModelamiento().length() - 1) {
                                    if (nt != p.getLadoIzquierdo()) {
                                        sig = siguientes(p.getLadoIzquierdo());
                                        nt.setConjuntoDeSiguientes(sig);
                                        for (Character c : sig) {
                                            if (!conjuntoSiguientes.contains(c)) {
                                                conjuntoSiguientes.add(c);
                                            }
                                        }
                                        estaListo = true;
                                        break;
                                    }
                                }
                                if (p.getLadoDerecho().getModelamiento().charAt(j + i) == 'T') {
                                    if (!conjuntoSiguientes.contains(p.getLadoDerecho().getHileraTerminalesYNoTerminales().charAt(i + j))) {
                                        conjuntoSiguientes.add(p.getLadoDerecho().getHileraTerminalesYNoTerminales().charAt(i + j));
                                    }
                                    if (!nt.getConjuntoDeSiguientes().contains(p.getLadoDerecho().getHileraTerminalesYNoTerminales().charAt(i + j))) {
                                        nt.getConjuntoDeSiguientes().add(p.getLadoDerecho().getHileraTerminalesYNoTerminales().charAt(i + j));
                                    }
                                    estaListo = true;
                                    break;
                                }
                                if (p.getLadoDerecho().getModelamiento().charAt(i + j) == 'N') {
                                    noTerm = (NoTerminal) p.getLadoDerecho().getModelo().get(i + j);
                                    if (nt == noTerm) {
                                        return null;
                                    }

                                    sig = noTerm.getConjuntoDePrimeros();
                                    nt.setConjuntoDeSiguientes(sig);
                                    for (Character c : sig) {
                                        if (!conjuntoSiguientes.contains(c)) {
                                            conjuntoSiguientes.add(c);
                                        }
                                    }
                                    if (noTerm.esAnulable) {
                                        estaListo = false;
                                        if (i + j == p.getLadoDerecho().getModelamiento().length() - 1) {
                                            if (nt != p.getLadoIzquierdo()) {
                                                sig = siguientes(p.getLadoIzquierdo());
                                                nt.setConjuntoDeSiguientes(sig);
                                                for (Character c : sig) {
                                                    if (!conjuntoSiguientes.contains(c)) {
                                                        conjuntoSiguientes.add(c);
                                                    }
                                                }
                                                estaListo = true;
                                                break;
                                            }
                                        }
                                        j++;
                                    } else {
                                        estaListo = true;
                                        break;
                                    }

                                }
                            }
                            if (estaListo) {
                                break;
                            }
                        }
                    }
                }

            }
        }
        return conjuntoSiguientes;
    }

    /**
     * Método que se encarga de calcular los primeros para todos los no terminales
     * de la lista de la clase.
     */
    public void primerosNoTerminales() {
        for (NoTerminal nt : this.listaDeNoTerminales) {
            calcularPrimerosNoTerminal(nt);
        }
    }

    /**
     * Método que se encarga de crear los primeros de las producciones.
     */
    public void calcularPrimerosProducciones() {
        for (Produccion p : this.producciones) {
            for (int i = 0; i <= p.getLadoDerecho().getModelamiento().length() - 1; i++) {
                if (p.getLadoDerecho().getModelamiento().charAt(i) == 'T') {
                    Character t = (Character) p.getLadoDerecho().getHileraTerminalesYNoTerminales().charAt(i);
                    p.setPrimerosDeLaProduccion(t);
                    break;
                } else if (p.getLadoDerecho().getModelamiento().charAt(i) == 'N') {
                    NoTerminal nt = (NoTerminal) p.getLadoDerecho().getModelo().get(i);
                    if (!nt.esAnulable) {
                        p.setListaPrimerosDeLaProduccion(nt.conjuntoDePrimeros);
                        break;
                    } else {
                        p.setListaPrimerosDeLaProduccion(nt.conjuntoDePrimeros);
                    }

                }
            }
        }

    }

    /**
     * Método que se encarga de crear los primeros correspondientes a un No terminal.
     * Este algoritmo no nos dio tanta dificultad como el de siguientes.
     * 
     * @param nt
     * @return 
     */
    public ArrayList calcularPrimerosNoTerminal(NoTerminal nt) {
        ArrayList<Character> primeros = nt.getConjuntoDePrimeros();
        for (Produccion p : this.producciones) {
            boolean encontroPrimero = false;//Variable que indica que encontro el primero de la produccion p
            if (nt == p.getLadoIzquierdo()) {
                for (int i = 0; i <= p.getLadoDerecho().getModelamiento().length() - 1; i++) {
                    if (p.getLadoDerecho().getModelamiento().charAt(i) == 'T') {
                        encontroPrimero = true;
                        Character t = (Character) p.getLadoDerecho().getHileraTerminalesYNoTerminales().charAt(i);
                        if (!estaEnLaLista(nt, primeros)) {
                            if (!nt.estaEnPrimeros(t)) {
                                primeros.add(t);
                            }
                        }
                        if (!nt.estaEnPrimeros(t)) {
                            nt.getConjuntoDePrimeros().add(t);
                        }
                    } else if (p.getLadoDerecho().getModelamiento().charAt(i) == 'N') {
                        NoTerminal noTerm;
                        noTerm = (NoTerminal) p.getLadoDerecho().getModelo().get(i);
                        if (noTerm == nt) {
                            if (noTerm.esAnulable) {
                                continue;
                            } else {
                                break;
                            }
                        }
                        if (!noTerm.esAnulable) {
                            ArrayList prim = calcularPrimerosNoTerminal(noTerm);
                            nt.setConjuntoDePrimeros(prim);
                            encontroPrimero = true;
                            break;
                        } else {
                            ArrayList prim = calcularPrimerosNoTerminal(noTerm);
                            nt.setConjuntoDePrimeros(prim);
                            continue;
                        }
                    }
                    if (encontroPrimero) {
                        break;
                    }
                }
            }
        }
        return primeros;
    }

    /**
     * Retorna verdadero si la produccion p tiene al lado derecho secuencia nula
     *
     * @param p
     * @return
     */
    public boolean produccionAnulableTipo1(Produccion p) {
        if (p.isEsAnulable()) {
            return false;
        }
        boolean produccionAnulable = false;
        if (p.getLadoDerecho().isSecuenciaNula()) {
            produccionAnulable = true;
            p.setEsAnulable(produccionAnulable);
            this.getProduccionesAnulables().add(p);
        }
        return produccionAnulable;

    }

    /**
     * Determina una posibilidad en la que una producción es anulable.
     * 
     * @param p
     * @return 
     */
    public boolean produccionAnulableTipo2(Produccion p) {
        if (p.isEsAnulable()) {
            return false;
        }
        if (p.getLadoDerecho().hayAlgunTerminal()) {
            return false;
        } else {
            for (int i = 0; i <= p.getLadoDerecho().getModelamiento().length() - 1; i++) {
                NoTerminal nt = (NoTerminal) p.getLadoDerecho().getModelo().get(i);
                if ((nt != null) && nt.esAnulable && i == p.getLadoDerecho().getModelamiento().length() - 1) {
                    p.setEsAnulable(true);
                    p.getLadoIzquierdo().setEsAnulable(true);
                    if (!this.getProduccionesAnulables().contains(p)) {
                        this.getProduccionesAnulables().add(p);
                    }
                    return true;
                } else if (!nt.esAnulable) {
                    return false;
                }

            }
            return false;
        }

    }

    /**
     * Método para determinar cuáles son las producciones anulables de la gramática.
     * 
     */
    public void calcularProduccionesAnulables() {
        for (Produccion p : this.producciones) {
            if (produccionAnulableTipo1(p)) {
                continue;
            }
        }
        boolean estaListo = false;
        while (!estaListo) {
            estaListo = true;
            for (Produccion p : this.producciones) {
                if (produccionAnulableTipo2(p)) {
                    estaListo = false;
                    break;
                }
            }
        }
    }

    /**
     * Método que calcula los no terminales anulables de la gramática.
     * Realiza un recorrido de las producciones.
     */
    public void calcularNoTerminalesAnulables() {
        for (Produccion p : producciones) {
            if (p.getLadoDerecho().isSecuenciaNula()) {
                noTerminalesAnulables.add(p.getLadoIzquierdo());
                this.listaNoTerminalesNoAnulables.remove(p.getLadoIzquierdo());
                p.getLadoIzquierdo().setEsAnulable(true);
            }
        }
        boolean estaListo = false;
        while (!estaListo) {
            estaListo = true;
            boolean estaEnAnulables = false;
            for (Produccion p : producciones) {
                //Si Al lado derecho de la producción no hay ningun TERMINAL
                if (!p.getLadoDerecho().hayAlgunTerminal()) {
                    NoTerminal noTerm;
                    for (Object n : p.getLadoDerecho().getModelo()) {
                        noTerm = (NoTerminal) n;
                        if (this.noTerminalesAnulables.contains(noTerm)) {
                            estaEnAnulables = true;
                        } else {
                            estaEnAnulables = false;
                            break;
                        }
                    }
                    if (estaEnAnulables) {
                        if (!estaEnlosNoAnulables(p.getLadoIzquierdo())) {
                            this.noTerminalesAnulables.add(p.getLadoIzquierdo());
                            this.listaNoTerminalesNoAnulables.remove(p.getLadoIzquierdo());
                            estaListo = false;
                            break;
                        }
                    }
                }
            }
        }

    }

    /**
     * Retorna verdadero si un no terminal está en la lista de no terminales no anulables.
     * 
     * @param nt
     * @return 
     */
    public boolean estaEnLosNoAnulables(NoTerminal nt) {
        return this.listaNoTerminalesNoAnulables.contains(nt);
    }

    /**
     * Método que retorna verdadero si un no terminal está en una lista de no terminales.
     * 
     * @param nt
     * @param lista
     * @return 
     */
    public boolean estaEnLaLista(NoTerminal nt, ArrayList lista) {
        return lista.contains(nt);
    }

    /**
     * Retorna verdadero si un no terminal está en la lista de anulables.
     * 
     * @param nt
     * @return 
     */
    public boolean estaEnlosNoAnulables(NoTerminal nt) {
        return this.noTerminalesAnulables.contains(nt);
    }

    /**
     * Método que retorna la lista de los no terminales de la gramática.
     * 
     * @return 
     */
    public ArrayList<NoTerminal> getListaDeNoTerminales() {
        return listaDeNoTerminales;
    }

    /**
     * Método que establece la lista de los no terminales de la gramática.
     * 
     * @param listaDeNoTerminales 
     */
    public void setListaDeNoTerminales(ArrayList<NoTerminal> listaDeNoTerminales) {
        this.listaDeNoTerminales = listaDeNoTerminales;
    }

    /**
     * Método que devuelve la lista de los no terminales no anulables.
     * 
     * @return 
     */
    public ArrayList<NoTerminal> getListaNoTerminalesNoAnulables() {
        return listaNoTerminalesNoAnulables;
    }

    /**
     * Método que establece o asigna la lista de los no terminales no anulables.
     * 
     * @param listaNoTerminalesNoAnulables 
     */
    public void setListaNoTerminalesNoAnulables(ArrayList<NoTerminal> listaNoTerminalesNoAnulables) {
        this.listaNoTerminalesNoAnulables = listaNoTerminalesNoAnulables;
    }

    /**
     * Método que retorna la cantidad de producciones de una gramática.
     * 
     * @return 
     */
    public int getCantidadProducciones() {
        return cantidadProducciones;
    }

    /**
     * Método que establece la cantidad de producciones de una gramática.
     * 
     * @param cantidadProducciones 
     */
    public void setCantidadProducciones(int cantidadProducciones) {
        this.cantidadProducciones = cantidadProducciones;
    }

}
