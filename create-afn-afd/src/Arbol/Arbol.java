/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Carlos
 */
public class Arbol<T> {

    private Nodo<T> root;       
    private Nodo<T> actual;   
    private final Stack pila;
    private ArrayList arrayNodos;
    
    
   /**
    * Constructor 
    * inicializa la raiz del arbol
    */
    public Arbol(){
        this.arrayNodos = new ArrayList();
        this.pila = new Stack();
        this.root= new Nodo("");
    }

    public void buildTree(String cadenaEnPrefix){
        
        this.root = new Nodo(cadenaEnPrefix);
        pila.add(this.root);
        buildPostFixTree((Nodo<T>) this.root);
        this.root=this.actual;
        
        

        
    }

    private void buildPostFixTree(Nodo<T> nodo){
       
      
        String texto_postfix = (String) nodo.getRegex();
       
        char letra_inicial = texto_postfix.charAt(0);
       
        if(letra_inicial!='*'&&letra_inicial!='|'&&letra_inicial!='.'){
            
            String sub_cadena = texto_postfix.substring(1);
            Nodo nuevo = new Nodo((sub_cadena));
            nuevo.setId(""+letra_inicial);
            nuevo.setIsLeaf(true);
            arrayNodos.add(nuevo);
                       
            pila.remove(this.root);
            pila.add(nuevo);
            buildPostFixTree(nuevo);
           
           
        }
         else
            
        {

            if(letra_inicial == '*'){
               
                String sub_cadena = texto_postfix.substring(1);
                Nodo nuevo = new Nodo(sub_cadena);
                nuevo.setId((T) (""+letra_inicial));
                
                Nodo nodoPila = (Nodo)pila.pop();
                nuevo.setIzquierda(nodoPila);
                arrayNodos.add(nuevo);
                pila.add(nuevo);
                buildPostFixTree(nuevo);
               
           
            }

            else if(letra_inicial=='|'||letra_inicial=='.'){
               
                String sub_cadena = texto_postfix.substring(1);
           
                Nodo nuevo = new Nodo(sub_cadena);
                nuevo.setId(""+letra_inicial);
              
                nuevo.setDerecha((Nodo) pila.pop());
               
               
                if (!pila.isEmpty())
                    nuevo.setIzquierda((Nodo)pila.pop());
                else
                    nuevo.setIzquierda(nodo);
                
                 pila.add(nuevo);
                 arrayNodos.add(nuevo);
                 this.actual = nuevo;
                 if (!sub_cadena.isEmpty())
                    buildPostFixTree(nuevo);
                
            }
        }
        
        
        
        
    }

    public Nodo<T> getRoot() {
        return root;
    }

    public void setRoot(Nodo<T> root) {
        this.root = root;
    }
    
    public Nodo<T> getResultado() {
        return actual;
    }

    public void setResultado(Nodo<T> resultado) {
        this.actual = resultado;
    }

    public ArrayList getArrayNodos() {
        return arrayNodos;
    }

    public void setArrayNodos(ArrayList arrayNodos) {
        this.arrayNodos = arrayNodos;
    }
   
    
    
}
