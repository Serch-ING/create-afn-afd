/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automatas;

import Arbol.Arbol;
import Arbol.Nodo;
import Main.Main;
import Regex.Test;
import Thompson.Automata;
import Thompson.Estado;
import Thompson.Transicion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;

/**
 *
 * @author Carlos
 */
public class AFD {
    Automata afd;
    Test test;
    private final HashMap resultadoFollowPos;
    /*
    * CONSTRUCTOR DE LA CLASE
    @param {afn} Automata AFN a convertir a AFD
    */
    public AFD() {
        this.afd = new Automata();
        test = new Test(afd,"AFD");
        this.resultadoFollowPos = new HashMap();
    }
    
    
    
  
    
    
    
    
    

    public Automata getAfd() {
        return afd;
    }
    
    
    public Boolean creacionDirecta(Arbol arbolSintactico){
        
        generarNumeracionNodos(arbolSintactico);
        
        ArrayList<Nodo> arrayNodos = arbolSintactico.getArrayNodos();      
        
        for (int i = 0;i<arrayNodos.size();i++){
            if (arrayNodos.get(i).getId().equals("*")||arrayNodos.get(i).getId().equals("."))
                followPos(arrayNodos.get(i));
        }
        toStringFollowPos();
        return crearEstados(arbolSintactico) != null;

    }
     
    
    private void toStringFollowPos() {
        System.out.println("follow pos");
       
         Iterator it = resultadoFollowPos.entrySet().iterator();
         while (it.hasNext()) {
             Map.Entry<Integer, Nodo> e = (Map.Entry) it.next();
             System.out.println(e.getKey() + " " + e.getValue());
         }
        
    }  

    public boolean nullable(Nodo expresion){
        if (expresion.getId().equals(Main.EPSILON))
            return true;
        else if (expresion.getId().equals("*"))
            return true;
        else if (expresion.getId().equals("|"))
            return (nullable(expresion.getIzquierda()))||(nullable(expresion.getDerecha()));
        else if (expresion.getId().equals("."))
            return (nullable(expresion.getIzquierda()))&&(nullable(expresion.getDerecha()));
        else if (expresion.isIsLeaf()==true)
            return false;
      
        return false;
        
    }
    

    public TreeSet firstPos(Nodo nodoEval){
        TreeSet resultado = new TreeSet();
        if (nodoEval.getId().equals(Main.EPSILON))
            return resultado;
        else if (nodoEval.isIsLeaf()){
            resultado.add(nodoEval);
        }
        else if (nodoEval.getId().equals("|")){
           resultado.addAll(firstPos(nodoEval.getIzquierda()));
           resultado.addAll(firstPos(nodoEval.getDerecha()));
           return resultado;
           
        }
        else if (nodoEval.getId().equals(".")){
            if (nullable(nodoEval.getIzquierda())){
                resultado.addAll(firstPos(nodoEval.getIzquierda()));
                resultado.addAll(firstPos(nodoEval.getDerecha()));
            }
            else{
                resultado.addAll(firstPos(nodoEval.getIzquierda()));
            }
        }
        else if (nodoEval.getId().equals("*")){
            resultado.addAll(firstPos(nodoEval.getIzquierda()));
        }
        
        return resultado;
    }
    
    
    public ArrayList lastPos(Nodo nodoEval){
        ArrayList resultado = new ArrayList();
        
        if (nodoEval.getId().equals(Main.EPSILON))
            return resultado;
          
        else if (nodoEval.isIsLeaf()){
           resultado.add(nodoEval);
           return resultado;
        }
        else if (nodoEval.getId().equals("*")){
            resultado.addAll(lastPos(nodoEval.getIzquierda()));
        }
        else if (nodoEval.getId().equals("|")){
            resultado.addAll(lastPos(nodoEval.getIzquierda()));
            resultado.addAll(lastPos(nodoEval.getDerecha()));
        }
        else if (nodoEval.getId().equals(".")){
            if (nullable(nodoEval.getDerecha())){
                
                resultado.addAll(lastPos(nodoEval.getIzquierda()));
                resultado.addAll(lastPos(nodoEval.getDerecha()));
            }
            else{
                resultado.addAll(lastPos(nodoEval.getDerecha()));
            }
        }
        
        return resultado;
    }
    
   
    public void followPos(Nodo nodoEval){
       
        if (nodoEval.getId().equals("*")){
            
            ArrayList<Nodo> lastPosition = lastPos(nodoEval);
            
            TreeSet<Nodo> firstPosition = firstPos(nodoEval);
              
            for(int j=0;j<lastPosition.size();j++){
                int numero = lastPosition.get(j).getNumeroNodo();

                if(resultadoFollowPos.containsKey(numero)){
                    firstPosition.addAll((Collection)resultadoFollowPos.get(numero));
                }
                resultadoFollowPos.put(numero,firstPosition);
                   
            }
        }
        else if (nodoEval.getId().equals(".")){
            ArrayList<Nodo> lastPosition = lastPos(nodoEval.getIzquierda());
            TreeSet<Nodo> firstPosition = firstPos(nodoEval.getDerecha());
            for (int i = 0;i<lastPosition.size();i++){
                int numero = (int) lastPosition.get(i).getNumeroNodo();
                if (resultadoFollowPos.containsKey(numero)){
                   firstPosition.addAll((Collection) resultadoFollowPos.get(numero));//merge                   
                }              
                resultadoFollowPos.put(numero, firstPosition);
                firstPosition = firstPos(nodoEval.getDerecha());
            }
            
        }
        
       
    }
    

    private void generarNumeracionNodos(Arbol arbol){
        ArrayList<Nodo> arrayNodos = arbol.getArrayNodos();
        int index = 1;
        for (int i = 0 ;i<arrayNodos.size();i++){
            if (arrayNodos.get(i).isIsLeaf()){
                arrayNodos.get(i).setNumeroNodo(index);
                index++;
            }
        }
        arbol.setArrayNodos(arrayNodos);
        
    }
    
   
    public Object crearEstados(Arbol arbolSintactico) {
        Automata afd_result = new Automata();
        afd_result.setTipoAutomata(1);

        definirAlfabeto(afd_result, arbolSintactico);
        Estado inicial = new Estado(0);
        TreeSet<Nodo> resultadoInicial = firstPos(arbolSintactico.getRoot());
        afd_result.setInicial(inicial);
        afd_result.addEstados(inicial);

        ArrayList<ArrayList<TreeSet>> estadosCreados = new ArrayList();
        ArrayList conversionInicial = new ArrayList(resultadoInicial);

        estadosCreados.add(conversionInicial);

        for (Nodo temp : (ArrayList<Nodo>) conversionInicial) {
            if (temp.getId().equals("#")) {
                afd_result.addEstadoAceptacion(inicial);
            }
        }

        int indexEstadoInicio = 0;
        int indexEstados = 1;
        Queue<ArrayList> cola = new LinkedList();
        cola.add(conversionInicial);
        int i = 0;
        while (!cola.isEmpty()) {
            ArrayList<Nodo> actual = cola.poll();
            if(actual.size() > 100) return null;
            boolean control = true;
            for (String letra : (HashSet<String>) afd_result.getSimbolos()) {
                ArrayList temporal = new ArrayList();
                Boolean flag = false;
                for (Nodo n : actual) {
                    if (n.getId().equals(letra)) {
                        temporal.addAll((TreeSet<Nodo>) resultadoFollowPos.get(n.getNumeroNodo()));
                        flag = true;
                    }

                }
                
                if (flag) {
                    if (control) {

                        if (!estadosCreados.contains(temporal)) {
                            
                            Estado siguiente = new Estado(indexEstados);
                            indexEstados++;
                            Estado estadoAnterior = afd_result.getEstado(indexEstadoInicio);

                            estadoAnterior.setTransiciones(new Transicion(estadoAnterior, siguiente, letra));
                            afd_result.addEstados(siguiente);
                            cola.add(temporal);
                            estadosCreados.add(temporal);
                            
                            for (Nodo temp : (ArrayList<Nodo>) temporal) {
                                if (temp.getId().equals("#") && !afd_result.getAceptacion().contains(siguiente)) {
                                    afd_result.addEstadoAceptacion(siguiente);
                                }
                            }
                            
                        } else {
                            Estado estadoAnterior = afd_result.getEstado(indexEstadoInicio);
                            Estado estadoSiguiente = afd_result.getEstado(estadosCreados.indexOf(temporal));
                            estadoAnterior.setTransiciones(new Transicion(estadoAnterior, estadoSiguiente, letra));
                        }
                    }
                }
                
            }
            indexEstadoInicio++;

        }

        System.out.println(afd_result);
        this.afd = afd_result;
        return "";
    }
    
    
    
     public void definirAlfabeto(Automata afd, Arbol arbol){
      HashSet alfabeto = new HashSet();
      String expresion = arbol.getRoot().postOrder();
      for (Character ch: expresion.toCharArray()){
          if (ch!='*'&&ch!='.'&&ch!='|'&&ch!='#'&&ch!=Main.EPSILON_CHAR){
              alfabeto.add(Character.toString(ch));
          }
      }
      afd.setSimbolos(alfabeto);

  }

    
    
}
