/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizadores;

import Thompson.Automata;

/**
 *
 * @author Carlos
 */
public class Lexico {
    
    private final String input;
    
    //----------------------------- EXPRESIONES COMUNES ----------------------------------------
    private final String letra = "[a-zA-z]";
    private final String digito = "\\d";
    private final String numero = digito + "(" + digito + ")*";
    private final String id = letra +  "(" + letra + "|" + digito + ")*";
    private final String cadena = "\"" + "(" + numero + "|" + letra + "|[^\\\"])*" + "\"";
    private final String caracter = "\'" + "(" + numero + "|" + letra + "|[^\\\'])*" + "\'";
    private final String espacio = "(\\s)*";
    private final boolean flagcadena = false;
    private final boolean output = true;
    
    //-------------------------------------- AUTOMATAS ------------------------------------------
    private Automata _letra;
    private Automata _digito;
    private Automata _identificador;
    private Automata _cadena;
    private Automata _numero;
    private Automata _caracter;
    private Automata _espacio;
    private Automata _basicSet;
    private Automata _igual;
    private Automata _plusMinus;

    /*
    * CONSTRUCTOR DE LA CLASE
    * @param {input} regex 
    */
    public Lexico(String input) {
        this.input = input;
    }
    
    /*
    * METODO QUE SE ENCARGARA DE VERIFICAR LA ER
    * @param {regex} ER a comparar
    * @param {index} indice para hacer substring
    * @return {string} con cadena encontrada
    */
    public String checkER(String regex, int index){
        String coincidencia = "";
        String search = cadena.substring(index);
        String resultado = "";
        return resultado;
    }
    
    
    
    
}
