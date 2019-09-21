/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thompson;

import Main.Main;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Carlos
 */
public class Automata {
    private Estado inicial;
    private final  ArrayList<Estado> aceptacion;
    private final ArrayList<Estado> estados;
    private HashSet simbolos;
    private int tipoAutomata;
    private String[] resultadoRegex;
    private String lenguaje;

    /*
    * CONSTRUCTOR DE LA CLASE
    */
    public Automata() {
        this.aceptacion = new ArrayList<>();
        this.estados = new ArrayList<>();
        this.simbolos = new HashSet();
        this.resultadoRegex = new String[3];
    }

    
    
    /*
    * GET DEL ATRIBUTO inicial
    */
    public Estado getInicial() {
        return inicial;
    }

    /*
    * SET DEL ATRIBUTO inicial
    */
    public void setInicial(Estado inicial) {
        this.inicial = inicial;
    }

    /*
    * GET DEL ATRIBUTO inicial
    */
    public HashSet getSimbolos() {
        return simbolos;
    }

    /*
    * SET DEL ATRIBUTO simbolos
    */
    public void setSimbolos(HashSet simbolos) {
        this.simbolos = simbolos;
    }

    /*
    * GET DEL ATRIBUTO tipoAutomata
    */
    public int getTipoAutomata() {
        return tipoAutomata;
    }

    /*
    * SET DEL ATRIBUTO tipoAutomata
    */
    public void setTipoAutomata(int tipoAutomata) {
        this.tipoAutomata = tipoAutomata;
    }

    /*
    * GET DEL ATRIBUTO resultadoRegex
    */
    public String[] getResultadoRegex() {
        return resultadoRegex;
    }

    /*
    * SET DEL ATRIBUTO resultadoRegex
    */
    public void setResultadoRegex(String[] resultadoRegex) {
        this.resultadoRegex = resultadoRegex;
    }

    /*
    * GET DEL ATRIBUTO lenguaje
    */
    public String getLenguaje() {
        return lenguaje;
    }

    /*
    * SET DEL ATRIBUTO lenguaje
    */
    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public ArrayList<Estado> getAceptacion() {
        return aceptacion;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }
    
    
    
    
    
    /*
     * METODO QUE AGREGA ESTADOS DE ACEPTACION
     * @param {estado} estado a agregar 
    */
    public void addEstadoAceptacion(Estado estado){
        this.aceptacion.add(estado);
    }
    
    /*
    * METODO QUE AGREGA ESTADOS 
    * @param {estado} estado a agregar
    */
    public void addEstados(Estado estado){
        this.estados.add(estado);
    }
    
    
    
    /*
    * METODO QUE CREA LOS SIMBOLOS PERMITIDOS DEPENDIENTO DE LA REGEX INGRESADA
    * @param {regex} expresion a analizar
    */
    public void  crearSimbolos(String regex){
        for(Character c: regex.toCharArray()){
            if( c != '|' && c != '.' && c != '*' && c != Main.EPSILON_CHAR) this.simbolos.add(Character.toString(c));
        }
    }
    
    /*
    * METODO QUE SETEA AL RESULTADO DEL REGEX
    * @param {key} key del objeto
    * @param {value} valor del objeto
    */
    public void addResultadoRegex(int key, String value){
        this.resultadoRegex[key] = value;
    }
    
    /*
    * METODO QUE DEVUELVE LA INFORMACION DEL AUTOMATA
    * @return String
    */
    
    @Override
    public String toString(){
        String res = "";
        res += (this.tipoAutomata == 0) ? "-------------------- AFD ------------------\n"  : "-------------------- AFND ------------------\n"; 
        res += "Alfabeto: " + this.simbolos + "\n";
        res += "Estado Inicial: " + this.inicial + "\n";
        res += "Estado Final: " + this.aceptacion + "\n";
        res += "Conjuntos: " + this.estados + "\n";
        res += "Transiciones: ";
        for (int i = 0; i < this.estados.size(); i++){
            Estado temp = this.estados.get(i);
            res += temp.getTransiciones() + "-";
        }
        res += "\n";
        res += "Lenguaje: " +  this.lenguaje + "\n";
        res += "Cadena: " + this.resultadoRegex[1]  + "\n";
        res += "Resultado: " + this.resultadoRegex[2];
        return res;
    }
    
    
    
    
    
    
}
