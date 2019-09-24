/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Regex;

import Main.Main;
import Thompson.Automata;
import Thompson.Estado;
import Thompson.Transicion;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author Carlos
 */
public class Test {
    Automata automata;
    String cadena;

    /*
    * CONSTRUCTOR DE LA CLASE
    * @param {automata} automata con que se hara el test
    * @param {cadena} cadena a probar
    */
    public Test(Automata automata, String cadena) {
        this.automata = automata;
        this.cadena = cadena;
    }
    
    
    public Boolean simular(){
        Estado inicial = automata.getInicial();
        ArrayList<Estado> estados = automata.getEstados();
        ArrayList<Estado> aceptacion = new ArrayList(automata.getAceptacion());
        HashSet<Estado> conjunto = eClosure(inicial);
        for(Character c: cadena.toCharArray()){
            conjunto = move(conjunto,c.toString());
            
            HashSet<Estado> temp = new HashSet();
            Iterator<Estado> iter = conjunto.iterator();           
            while(iter.hasNext()){
                Estado siguiente = iter.next();
                temp = eClosure(siguiente);
            }
            conjunto = temp;
        }
        boolean res = false;
        for(Estado estado : aceptacion){
            if(conjunto.contains(estado)) res = true;
        }
        return res;
    }
    
    
    public HashSet<Estado> eClosure(Estado estado){
        Stack<Estado> pila = new Stack();
        Estado actual = estado;
        actual.getTransiciones();
        HashSet<Estado>  resultado = new HashSet();
        pila.push(actual);
        
        while(!pila.isEmpty()){
            actual = pila.pop();
            for(Transicion t : ((ArrayList<Transicion>)actual.getTransiciones())){
                if(t.getSimbolo().equals(Main.EPSILON) && !resultado.contains(t.getFin())){
                    resultado.add(t.getFin());
                    pila.push(t.getFin());
                }
            }
        }
        
        resultado.add(estado);
        return resultado;
    }
    
    public HashSet<Estado> move (HashSet<Estado> estados, String simbolo){
        HashSet<Estado> alcanzados = new HashSet();
        Iterator<Estado> iterador = estados.iterator();
        while(iterador.hasNext()){
            
            for(Transicion t : ((ArrayList<Transicion>)iterador.next().getTransiciones())){
                Estado siguiente = t.getFin();                
                String sim = (String)t.getSimbolo();
                if(sim.equals(simbolo)) {
                    alcanzados.add(siguiente);                    
                }
            }
        }
        return alcanzados;
    }
    
    public Estado move(Estado estado,String simbolo){
        ArrayList<Estado> alcanzados = new ArrayList();
        System.out.println(alcanzados.get(0).getId() + "prueba");
        for(Transicion t: ((ArrayList<Transicion>)estado.getTransiciones())){
            Estado siguiente = t.getFin();
            String simb = (String)t.getSimbolo();
            if(simb.equals(simbolo) && !alcanzados.contains(siguiente)){
                alcanzados.add(siguiente);
            }
        }
        System.out.println(alcanzados.get(0).getId() + "prueba");
        return alcanzados.get(0);
    }
    
}
