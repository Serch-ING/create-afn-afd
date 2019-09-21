/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thompson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;


/**
 *
 * @author Carlos
 */
public class Simulacion {
    private String resultado;

    /*
    * CONSTRUCTOR DE LA CLASE
    */
    public Simulacion() {
    }
    
    /*
    * CONSTRUCTOR DE LA CLASE
    @param {automata} automata que se simulara no importa si es afn o afd
    @param {regex} ER
    */
    public Simulacion(Automata automata, String regex) {
        simular(regex,automata);
    }
    
    /*
    * METODO QUE HARA LA SIMULACION
    * @param {regex} ER
    * @param {automata} Automata a simular
    */
    public Boolean simular(String regex, Automata automata){
        Estado inicial = automata.getInicial();
        ArrayList<Estado> estados = automata.getEstados();
        ArrayList<Estado> aceptacion = new ArrayList(automata.getAceptacion());
        
        return false;
        
    }
    
    
    public HashSet eLock(Estado estado){
        Stack<Estado> pila = new Stack();
        Estado actual = estado;
        actual.getTransiciones();
        HashSet<Estado> resultado = new HashSet();
        
        pila.push(actual);
        
        while(!pila.isEmpty()){
            actual = pila.pop();
            
           
        }
        
        
        return resultado;
    }
    
    
    
    
}
