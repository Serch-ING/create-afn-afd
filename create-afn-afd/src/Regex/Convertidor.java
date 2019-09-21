/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Regex;

import Main.Main;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class Convertidor {
    
    private final Map<Character,Integer> precedencia;
    
    /*
    * CONSTRUCTOR DE LA CLASE
    */
    public Convertidor() {
        Map<Character, Integer> map = new HashMap<>();
        map.put('(', 1);
        map.put('|', 2);
        map.put('.', 3);
        map.put('?', 4);
        map.put('*', 4); 
        map.put('+', 4); 
        precedencia = Collections.unmodifiableMap(map);
    }
    
    /*
    * METODO PARA OBTENER LA PRECEDENCIA DE UN CARACTER
    * @param {c} caracter
    * @return int
    */
    
    private Integer getPrecedencia(Character c){
        Integer  p = precedencia.get(c);
        return (p == null) ? 6 : p;
    }
    
    /*
    * METODO QUE INSERTARA UN char en una posicion deseada
    @param {s} cadena original
    @param {index} posicion a insertar
    @param {c} caracter
    @return string alterado
    */
    
    private String insertarChartAt(String s, int index,Object c){
        return s.substring(0,index) + c + s.substring(index+1);
    }
    
    /*
    * METODO QUE AGREGA UN char en una posicion deseada
    @param {s} cadena original
    @param {index} posicion a insertar
    @param {c} caracter
    @return string alterado
    */
    private String addCharAt(String s, int index, Object c){
        String val = s.substring(index,index + 1);
        return s.substring(0,index) + val + c + s.substring(index + 1);
    }
    
    
    /*
    METODO QUE SE ENCARGARA DE SUSTITUIR EL ? 
    @param {regex} ER
    @return {string} ER ya editada
    */
    public String editarRegexInterrogacion(String regex){
        for (int i = 0; i < regex.length(); i++){
            Character c = regex.charAt(i);
            
            if(c.equals('?'))
            {
                if(regex.charAt(i-1) == ')')
                {
                    regex = insertarChartAt(regex,i,"|" + Main.EPSILON + ")");
                    int j = i;
                    while ( j != 0){
                        if(regex.charAt(j) ==  '(') break;
                        j--;
                    }
                    
                    regex = addCharAt(regex,j,"(");
                    
                }
                else
                {
                    regex = insertarChartAt(regex,i,"|" + Main.EPSILON + ")");
                    regex = addCharAt(regex,i-1,"(" + regex.charAt(i - 1));
                }
                
            }
            
        }
        regex = balancear(regex);
        return regex;
    }

     /*
    METODO QUE SE ENCARGARA DE SUSTITUIR EL +
    @param {regex} ER
    @return {string} ER ya editada
    */
    public String editarRegexPositiva(String regex){
        int comparar = 0;
        for(int i = 0; i < regex.length(); i++){
            Character c = regex.charAt(i);
            if(c.equals(('+'))){
                if(regex.charAt(i - 1) == ')')
                {
                    int j = i;
                    while(j != 0){
                        if(regex.charAt(j) == ')') comparar ++;
                        else if(regex.charAt(j) == '('){
                            comparar--;
                            if(comparar == 0) break;
                        }
                        j--;
                    }
                    String regexTemp = regex.substring(j,i);
                    regex = insertarChartAt(regex,i,regexTemp + "*");
                }
                else regex = insertarChartAt(regex,i,regex.charAt(i - 1) + "*");
            }
        }
        regex = balancear(regex);
        return regex;
    }
    
    
    /*
    METODO QUE SE ENCARGARA DE AGREGAR UN . EXPLICITAMENTE
    @param {regex} ER
    @return {string} ER ya editada
    */
    public String formatearRegex(String regex){
        regex = regex.trim();
        regex = editarRegexInterrogacion(regex);
        regex = editarRegexPositiva(regex);
        
        String regexTemp = "";
        List<Character> operadores = Arrays.asList('|','?','+','*');
        List<Character> operadoresBinarios = Arrays.asList('|');
        
        for(int i = 0; i < regex.length(); i++){
            Character c = regex.charAt(i);
            if(i + 1 < regex.length()){
                Character c2 = regex.charAt(i + 1);
                regexTemp += c;
                if(!c.equals('(') && !c2.equals(')') && !operadores.contains(c2) && !operadoresBinarios.contains(c)) regexTemp += '.';
            }
        }
        regexTemp += regex.charAt(regex.length() - 1);
        return regexTemp;
    }
    
    /*
    METODO QUE SE ENCARGARA DE AGREGAR ABREVIAR UN OR
    @param {regex} ER
    @return {string} ER ya editada
    */
    
    public String abreviarOr(String regex){
        String resultado = "";
        try{
            for(int i = 0; i < regex.length(); i++){
                Character c = regex.charAt(i);
                if(c == '[')
                {
                    if(regex.charAt(i + 2) == '-')
                    {
                        int inicio = regex.charAt(i + 1);
                        int fin = regex.charAt(i + 3);
                        resultado += "(";
                        for(int j = 0; j <= fin - inicio; j++)
                        {
                            if(j == (fin-inicio)) resultado += Character.toString((char)(inicio + j));
                            else resultado += Character.toString((char)(inicio + j)) + '|';
                        }
                        resultado += ")";
                        i = i + 4;
                    }
                    else resultado += c;
                }
                else resultado += c;
            }
        }catch(Exception e)
        {
            System.out.println("OR Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "OR Error: " + e.getMessage());
            resultado = "aa";
        }
        return resultado;
    }
    
     /*
    METODO QUE SE ENCARGARA DE AGREGAR ABREVIAR UN And
    @param {regex} ER
    @return {string} ER ya editada
    */
    
    public String abreviarAnd(String regex){
        String resultado = "";
        try{
            for(int i = 0; i < regex.length(); i++){
                Character c = regex.charAt(i);
                if(c == '[')
                {
                    if(regex.charAt(i + 2) == '.')
                    {
                        int inicio = regex.charAt(i + 1);
                        int fin = regex.charAt(i + 3);
                        resultado += "(";
                        for(int j = 0; j <= fin - inicio; j++)
                        {
                            resultado += Character.toString((char)(inicio + j));
                        }
                        resultado += ")";
                        i = i + 4;
                    }
                    
                } else resultado += c;
            }
        }catch(Exception e)
        {
            System.out.println("And Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "And Error: " + e.getMessage());
            resultado = "aa";
        }
        return resultado;
    }
    
    
    
    
    /*
    METODO QUE SE ENCARGARA DE BALANCEAR LOS PARENTESIS
    @param {regex} ER
    @return {string} ER ya editada
    */
    
    
    public String balancear(String regex){
       int p1 = cantParentesisIzq(regex);
       int p2 = cantParentesisDer(regex);
       
       while(p1 != p2){
           if(p1 > p2) regex += ")";
           else if(p1 < p2) regex = "(" + regex ;
           p1 = cantParentesisIzq(regex);
           p2 = cantParentesisDer(regex);
       }
       return regex;
    }
    
    /*
    METODO QUE SE ENCARGARA DE CONTAR LOS (
    @param {regex} ER
    @return {int} cantidad de (
    */
    private int cantParentesisIzq(String regex){
        int p = 0;
        for(int i = 0; i < regex.length(); i++){
            Character c = regex.charAt(i);
            if(c.equals(('('))) p++;
        }
        return p;
    }
    
    /*
    METODO QUE SE ENCARGARA DE CONTAR LOS )
    @param {regex} ER
    @return {int} cantidad de )
    */
    private int cantParentesisDer(String regex){
        int p = 0;
        for(int i = 0; i < regex.length(); i++){
            Character c = regex.charAt(i);
            if(c.equals((')'))) p++;
        }
        return p;
    }
    
    
     /*
    METODO QUE SE ENCARGARA DE OBTENER EL POSTFIJO (Metodo Shunting-yard)
    @param {regex} ER
    @return {string} ER ya editada
    */
    
    public String getPostFijo(String regex){
        String postfijo = "";
        regex = abreviarOr(regex);
        regex = abreviarAnd(regex);
        
        Stack<Character> stack = new Stack();
        
        String formateadoRegex = formatearRegex(regex);
        
        for(Character c : formateadoRegex.toCharArray()){
            switch(c){
                case '(':
                    stack.push(c);
                    break;
                 
                case ')':
                    while(!stack.peek().equals('(')){
                        postfijo += stack.pop();
                    }
                    stack.pop();
                    break;
                    
                    
                default:
                    while(stack.size() > 0)
                    {
                        Character cc = stack.peek();
                        Integer ccPrecedencia = getPrecedencia(cc);
                        Integer cPrecedencia = getPrecedencia(c);
                        if(ccPrecedencia  >=  cPrecedencia) postfijo += stack.pop();
                        else break;
                    }
                    stack.push(c);
                    break;
            }
        }
        while(stack.size() > 0) postfijo += stack.pop();
        
        return postfijo;
    }
    
    
}
