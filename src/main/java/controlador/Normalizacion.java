package controlador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import modelo.Gramatica;
import modelo.Palabra;

public class Normalizacion {

/*Se crea un  contructor el cual recibo los parametros de la gramatica a normalizar, los cuales son sus variables terminales
    variables no terminarles, su variable inicial y su sigma, se crea dos array para guardar todas las variables terminales
    y no terminales*/
    public Gramatica crearGramatica(String terminalesString, String noTerminalesString, String inicial, String sigamString){
        List<String> terminales = stringToList(terminalesString);
        List<String> noTerminales = stringToList(noTerminalesString);
        Map<String, Set<Palabra>> sigma = new HashMap<>();
        Gramatica g = new Gramatica(terminales, noTerminales, inicial, sigma);
        guardarSigma(sigamString, g);
        return g;
    }

    /*El metodo guardar sigma permite guardar todas las transiciónes de cada una de las terminales asi se logra
    evaluar cada una de ellas, entonces este metodo se llama en el contructor crear gramatica para que se guarden
    las transiciónes que se deben evaluar*/
    public void guardarSigma(String sigamString, Gramatica g){
        DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        String[] definiciones = sigamString.split(" ");
        Map<String, Set<Palabra>> sigma = g.getSigma();
        for(String def : definiciones){
            String noTerminalDef = def.substring(0, 1);
            if(!graph.containsVertex(noTerminalDef)){
                graph.addVertex(noTerminalDef);
            }
            String[] array = def.substring(3).split("/");
            Set<Palabra> palabras = new HashSet<>();
            for (String palabra : array) {
                Palabra p = new Palabra(palabra);
                String[] simbolos = palabra.split("");
                for (String simbolo : simbolos) {
                    if(!graph.containsEdge(noTerminalDef, simbolo)){
                        if(!graph.containsVertex(simbolo)){
                            graph.addVertex(simbolo);
                        }
                        graph.addEdge(noTerminalDef, simbolo); 
                    }
                }
                palabras.add(p);
            }
            sigma.put(noTerminalDef, palabras);
        }
        g.setSigma(sigma);
        g.setGraph(graph);
    }
    
    public Set<String> stringToSet(String s){
        s = s.trim();
        String[] array = s.split(",");
        return new HashSet<>(Arrays.asList(array));
    }

    public ArrayList<String> stringToList(String s){
        s = s.trim();
        String[] array = s.split(",");
        return new ArrayList<>(Arrays.asList(array));
    }
    
    
/*Recibe la gramatica recibida y normalza dicha gramatica la cual evalua sus varibles inutiles, variables inalcanzables, variables
    nulas y variables unitaria entonces llama a todos los metodos elicimar de cada una y devuelve la gramatica ya normalizada*/
    public Gramatica preChomsky(Gramatica g){
        Gramatica pc = g;
        while(existenInutiles(pc) || existenInalacanzables(pc, pc.getInicial()) || existenNulas(pc) || existenUnitarias(pc)){
            eliminarInutiles(pc);
            eliminarInalacanzables(pc,  pc.getInicial());
            eliminarNulas(pc);
            eliminarUnitarias(pc);
        }
        return pc;
    }

/*Aqui ya aplica la gramatica chomsky recibiendo el metodo preChomsky que le devuelve la gramatica ya normalizada
    en la cual su principal objetivo es que solo haya palabras de dos terminos sino es asi
    se crea una variable para simplificar la palabra y normalizar todo a chomsky*/
    public Gramatica aplicarChomsky(Gramatica g){
        Gramatica chomsky = preChomsky(g);
        Map<String, Set<Palabra>> sigma = chomsky.getSigma();
        Map<String, String> relacionT = new HashMap<>();
        Set<String> keys = sigma.keySet();
        String nuevaNoTerminal = "";
        int subIndexT = 0;
        for (String terminal : chomsky.getTerminales()) {
            Set<Palabra> nuevoSet = new HashSet<>();
            List<String> newSimbol = new ArrayList<>();
            nuevaNoTerminal = crearNuevaVariable('t', subIndexT);
            newSimbol.add(terminal);
            Palabra nuevaPalabra = new Palabra(newSimbol);
            nuevoSet.add(nuevaPalabra);
            chomsky.getNoTerminales().add(nuevaNoTerminal);
            sigma.put(nuevaNoTerminal, nuevoSet);
            relacionT.put(terminal, nuevaNoTerminal);
            subIndexT++;
        }
        int subIndexP = 1;
        for (int i = 0; i<sigma.size(); i++) {
            String key = keys.toArray(new String[0])[i];
            for(int j = 0; j<sigma.get(key).size(); j++){
                Palabra palabra =  sigma.get(key).toArray(new Palabra[0])[j];
                if(palabra.length()>2){
                    nuevaNoTerminal = crearNuevaVariable('p', subIndexP);
                    List<String> newSimbol = new ArrayList<>();
                    newSimbol.add(palabra.getSimbolos().get(0));
                    newSimbol.add(nuevaNoTerminal);
                    Palabra nuevaPalabra = new Palabra(newSimbol);
                    sigma.get(key).add(nuevaPalabra);
                    chomsky.getNoTerminales().add(nuevaNoTerminal);
                    Set<Palabra> nuevoSet = new HashSet<>();
                    Palabra nuevaPalabra2 = new Palabra(palabra.substring(1));
                    nuevoSet.add(nuevaPalabra2);
                    sigma.put(nuevaNoTerminal, nuevoSet);
                    subIndexP++;
                    sigma.get(key).remove(palabra);
                    j=-1;
                    i=-1;
                }
            }
        }
        reemplazarNoTerminales(sigma, chomsky.getTerminales(), relacionT);
        chomsky.setSigma(sigma);
        actualizarGraph(chomsky);
        return chomsky;
    }

    
/*Las variables 1,2,3 normalmente se llevan a variables de chomsky este metodo permite que cada una de ellas tenga su variable
    en chomsky*/    
    public void reemplazarNoTerminales(Map<String, Set<Palabra>> sigma, List<String> terminales, Map<String, String> relacionT){
        Set<String> keys = sigma.keySet();
        for (String key : keys) {
            for (Palabra p : sigma.get(key)) {
                for (String t : terminales) {
                    if(p.getSimbolos().contains(t)){
                        int i = p.getSimbolos().indexOf(t);
                        if(!key.equals(relacionT.get(t))){
                            p.getSimbolos().set(i, relacionT.get(t));
                            p.actualizarPalabra();
                        }
                    }
                }
            }
        }
    }


/*Evalua el sigma mirando si existen las variables inutiles*/
    public boolean existenInutiles(Gramatica g){
        Map<String, Set<Palabra>> sigma = g.getSigma();
        for(String noTerminal:g.getNoTerminales()){
            if(!sigma.containsKey(noTerminal)){
                return true;
            }
        }
        return false;
    }

/*Elimina las variables inutiles que se encuentran en las transiciónes de la gramatica*/
    public void eliminarInutiles(Gramatica g){
        Map<String, Set<Palabra>> sigma = g.getSigma();
        Set<String> keys = sigma.keySet();
        for(int k = 0; k<g.getNoTerminales().size(); k++){
            String noTerminal = g.getNoTerminales().toArray(new String[0])[k];
            if(!sigma.containsKey(noTerminal)){
                for (String key : keys) {
                    for (int i = 0; i<sigma.get(key).size(); i++) {
                        String palabra = sigma.get(key).toArray(new Palabra[0])[i].getPalabra();
                        if(palabra!=null){
                            
                            if(palabra.contains(noTerminal)){
                            
                                sigma.get(key).remove(sigma.get(key).toArray(new Palabra[0])[i]);
                                g.getNoTerminales().remove(noTerminal);
                                g.getGraph().removeVertex(noTerminal);
                                i--;
                            }
                        } 
                    }
                }
            }
        }
        actualizarGraph(g);
        g.setSigma(sigma);
    }

/*Evalua el sigma mirando si existen las variables Inalcanzables*/
    public boolean existenInalacanzables(Gramatica g, String inicial){
        DefaultDirectedGraph<String, DefaultEdge> graph = g.getGraph();
        Set<String> nodos = graph.vertexSet();
        for (String vertex : nodos) {
            if(graph.inDegreeOf(vertex)==0 && !vertex.equals(inicial)){
                return true;
            }
        }
        return false;
    }

/*Elimina las variables Inalcanzables que se encuentran en las transiciónes de la gramatica*/
    public void eliminarInalacanzables(Gramatica g, String inicial){
        DefaultDirectedGraph<String, DefaultEdge> graph = g.getGraph();
        Set<String> nodos = graph.vertexSet();
        String[] vertices = nodos.toArray(new String[0]);
        for(int i = 0; i<vertices.length; i++){
            String vertex = vertices[i];
            if(graph.inDegreeOf(vertex)==0 && !g.getTerminales().contains(vertex) && !vertex.equals(inicial)){
                g.getGraph().removeVertex(vertex);
                g.getNoTerminales().remove(vertex);
                g.getSigma().remove(vertex);
            }
        }
        actualizarGraph(g);
    }


/*Evalua el sigma mirando si existen las variables Nulas*/
    public boolean existenNulas(Gramatica g){
        DefaultDirectedGraph<String, DefaultEdge> graph = g.getGraph();
        if(graph.containsVertex("&")){
            Set<String> nodos = graph.vertexSet();
            for (String vertex : nodos) {
                if(graph.containsEdge(vertex, "&")){
                    return true;
                }
            }
        }
        return false;
    }
    
    
/*Elimina las variables Nulas que se encuentran en las transiciónes de la gramatica*/
    public void eliminarNulas(Gramatica g){
        DefaultDirectedGraph<String, DefaultEdge> graph = g.getGraph();
        Map<String, Set<Palabra>> sigma = g.getSigma();
        Set<String> keys = sigma.keySet();
        Palabra vacia = new Palabra("&");
        if(graph.containsVertex("&")){
            Set<String> nodos = graph.vertexSet();
            for (String vertex : nodos) {
                if(graph.containsEdge(vertex, "&")){
                    sigma.get(vertex).remove(vacia);
                    graph.removeEdge(vertex, "&");
                    for (String key : keys) {
                        for(int i = 0; i<sigma.get(key).size(); i++){
                            Palabra palabra = sigma.get(key).toArray(new Palabra[0])[i];
                            if(palabra.getPalabra().contains(vertex)){
                                List<String> letras = palabra.getSimbolos();
                                ArrayList<Integer> positions = new ArrayList<>();
                                for (int j = 0; j<letras.size(); j++) {
                                    if(vertex.equals(String.valueOf(letras.get(j)))){
                                        positions.add(j);
                                    }
                                }
                                if(palabra.length()>1){
                                    for (Integer pos : positions) {
                                        String nuevaPalabra = palabra.substring(0, pos) + palabra.substring(pos+1);
                                        Palabra np = new Palabra(nuevaPalabra);
                                        sigma.get(key).add(np);
                                    }
                                }else if(palabra.length()==1){
                                    sigma.get(key).add(vacia);
                                    if(!graph.containsEdge(key, "&")){
                                        graph.addEdge(key, "&");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        g.setSigma(sigma);
        g.setGraph(graph);
        actualizarGraph(g);
    }

    
/*Evalua el sigma mirando si existen las variables Unitarias*/
    public boolean existenUnitarias(Gramatica g){
        Map<String, Set<Palabra>> sigma = g.getSigma();
        List<String> keys = g.getNoTerminales();
        for (String key : keys) {
            Palabra[] palabras = sigma.get(key).toArray(new Palabra[0]);
            for (int i = 0; i<sigma.get(key).size(); i++) {
                Palabra palabra = palabras[i];
                boolean esNoterminalT = palabra.length()==1 && 
                        sigma.containsKey(palabra.getPalabra()) && 
                        sigma.get(palabra.getPalabra()).size()==1 && 
                        g.getTerminales().contains(sigma.get(palabra.getPalabra()).toArray(new Palabra[0])[0].getPalabra());
                
                if( palabra.length()==1 && g.getNoTerminales().contains(palabra.getPalabra()) && !esNoterminalT){
                    return true;
                }
            }
        }
        return false;
    }

/*Elimina las variables Unitarias que se encuentran en las transiciónes de la gramatica*/
    public void eliminarUnitarias(Gramatica g){
        DefaultDirectedGraph<String, DefaultEdge> graph = g.getGraph();
        Map<String, Set<Palabra>> sigma = g.getSigma();
        List<String> keys = g.getNoTerminales();
        for (String key : keys) {
            Palabra[] palabras = sigma.get(key).toArray(new Palabra[0]);
            for (int i = 0; i<palabras.length; i++) {
                Palabra palabra = palabras[i];
                boolean esNoterminalT = palabra.length()==1 && 
                        sigma.containsKey(palabra.getPalabra()) && 
                        sigma.get(palabra.getPalabra()).size()==1 && 
                        g.getTerminales().contains(sigma.get(palabra.getPalabra()).toArray(new Palabra[0])[0].getPalabra());
                if( palabra.length()==1 && !g.getTerminales().contains(palabra.getPalabra()) && !esNoterminalT){
                    sigma.get(key).remove(palabra);
                    graph.removeEdge(key, palabra.getPalabra());
                    sigma.get(key).addAll(sigma.get(palabra.getPalabra()));
                    for (Palabra p :  sigma.get(key)) {
                        for (String v : palabra.getSimbolos()) {
                            if(!graph.containsEdge(key, v)){
                                graph.addEdge(key, v);
                            }
                        }
                    }
                }
            }
        }
        g.setSigma(sigma);
        g.setGraph(graph);
        actualizarGraph(g);
    }

/*Crea las nuevas variables de sustitución para asignar la palabra y volverla en chomsky*/
    public String crearNuevaVariable(char tipo, int subIndex){
        StringBuilder subIndexCode = new StringBuilder();
        char[] indexes = (""+subIndex).toCharArray();
        for (char index : indexes) {
            if(index=='0'){
                subIndexCode.append("\u2080");
            }
            if(index=='1'){
                subIndexCode.append("\u2081");
            }
            if(index=='2'){
                subIndexCode.append("\u2082");
            }
            if(index=='3'){
                subIndexCode.append("\u2083");
            }
            if(index=='4'){
                subIndexCode.append("\u2084");
            }
            if(index=='5'){
                subIndexCode.append("\u2085");
            }
            if(index=='6'){
                subIndexCode.append("\u2086");
            }
            if(index=='7'){
                subIndexCode.append("\u2087");
            }
            if(index=='8'){
                subIndexCode.append("\u2088");
            }
            if(index=='9'){
                subIndexCode.append("\u2089");
            }
        }
        String variable = "";
        if(tipo=='p'){
            variable = "P"+subIndexCode;
        }
        if(tipo=='t'){
            variable = "T"+subIndexCode;
        }
        if(tipo=='x'){
            variable = "X"+subIndexCode;
        }
        if(tipo=='n'){
            variable = "."+subIndexCode;
        }
        return variable;
    }

/*Actualiza la gramatica del sigma*/
    public void actualizarGraph(Gramatica g){
        DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        Map<String, Set<Palabra>> sigma = g.getSigma();
        List<String> keys = g.getNoTerminales();
        for (String key : keys) {
            Set<Palabra> aux1 = sigma.get(key);
            if(aux1!=null && aux1.size()>0){
                
                Iterator<Palabra> aux2 = aux1.iterator();
                int fin = sigma.get(key).size();
                for (int it=0; it<fin; it++) {
                    Palabra palabra = aux2.next();
                    if(!graph.containsVertex(key)){
                        graph.addVertex(key);
                    }
                    for(String s : palabra.getSimbolos()){
                        if(!graph.containsEdge(key, s)){
                            if(!graph.containsVertex(s)){
                                graph.addVertex(s);
                            }
                            graph.addEdge(key, s); 
                        }
                    }
                }
                
            }
            
        }
        g.setGraph(graph);
    }

/*Evalua la gramatica si se encuentra en forma normal de chomsky si no existen variables nulas, variables Inalcanzables, 
    variables Inutiles y variables Unitarias */
    public boolean estaEnFNC (Gramatica g){
        if(existenNulas(g) || existenInalacanzables(g, g.getInicial()) || existenInutiles(g) || existenUnitarias(g)){
            return false;
        }
        List<String> keys = g.getNoTerminales();
        for (String key : keys) {
            for (Palabra palabra : g.getSigma().get(key)) {
                if(palabra.length()>2){
                    return false;
                }
            }
        }
        return true;
    }

//    public Gramatica aplicarGreybash(Gramatica g){
//        Gramatica greybatch = new Gramatica();
//        greybatch.setInicial(crearNuevaVariable('x', 1));
//        greybatch.setTerminales(g.getTerminales());
//        crearVariablesGreybash(g);
//        actualizarGraph(greybatch);
//        while (existeRecursividadIzquierda(greybatch)) {
//            eliminarRecursividadIzquierda(greybatch);
//        }
//        eliminarRecursividadInmediataIzquierda(greybatch);
//
//        return greybatch;
//    }

/*Convirtiendo las variables de chomsky en variables de gleybash pasando toda la gramatica y el sigma*/
    public Gramatica crearVariablesGreybash(Gramatica g){
        Gramatica greybatch = new Gramatica();
        greybatch.setInicial(crearNuevaVariable('x', 1));
        greybatch.setTerminales(g.getTerminales());
        
        Map<String, Set<Palabra>> sigma = g.getSigma();
        Map<String, Set<Palabra>> sigmaG = new HashMap<>();
        List<String> noTerminalesG = new ArrayList<>();
        Map<String, String> relacionVariables = new HashMap<>();
        List<String> keys = g.getNoTerminales();

        int subIndex = 1;
        for (String key : keys) {
            String variableG = "";
            if(key.equals(g.getInicial())){
                variableG = crearNuevaVariable('x', 1);
            }else{
                variableG = crearNuevaVariable('x', subIndex);
            }
            noTerminalesG.add(variableG);
            relacionVariables.put(key, variableG);
            subIndex++;
        }

        for (String key : keys) {
            Set<Palabra> palabras = new HashSet<>();
            for (Palabra p : sigma.get(key)) {
                List<String> simbolos = new ArrayList<>();
                for (String s : p.getSimbolos()) {
                    if(!g.getTerminales().contains(s)){
                        simbolos.add(relacionVariables.get(s));
                    }else{
                        simbolos.add(s);
                    }
                }
                Palabra nuevaP = new Palabra(simbolos);
                palabras.add(nuevaP);
            }
            sigmaG.put(relacionVariables.get(key), palabras);
        }
        greybatch.setNoTerminales(noTerminalesG);
        greybatch.setSigma(sigmaG);
        actualizarGraph(greybatch);
        
        return greybatch;
    }

/*Recibe la gramatica de gleybast y revisa si existe recursividad a la izquierda*/
    public boolean existeRecursividadIzquierda(Gramatica g){
        DefaultDirectedGraph<String, DefaultEdge> graph = g.getGraph();
        List<String> keys = g.getNoTerminales();
        for (String key : keys) {
            for (String target:keys) {
                if(!key.equals(target) && graph.containsEdge(key, target) && graph.containsEdge(target, key)){
                    return true;
                }
            }
        }
        return false;
    }

/*Elimina la recursividad a la izquierda que esta en la gramatica de gleybash*/
    public void eliminarRecursividadIzquierda(Gramatica g){
        DefaultDirectedGraph<String, DefaultEdge> graph = g.getGraph();
        Map<String, Set<Palabra>> sigma = g.getSigma();
        List<String> keys = g.getNoTerminales();

        for (String key : keys) {
            for (String target:keys) {
                if(!key.equals(target) && graph.containsEdge(key, target) && graph.containsEdge(target, key)){
                    for (int i = 0; i<sigma.get(key).size(); i++) {
                        Palabra p = sigma.get(key).toArray(new Palabra[0])[i];
                        int j = p.getSimbolos().indexOf(target);
                        if (j>-1) {
                            for (int k = 0; k<sigma.get(target).size(); k++) {
                                Palabra p2 = sigma.get(target).toArray(new Palabra[0])[k];
                                if(!p2.getSimbolos().contains(target)){
                                    p.getSimbolos().remove(target);
                                    p.getSimbolos().addAll(j, p2.getSimbolos());
                                    p.actualizarPalabra();
                                    actualizarGraph(g);
                                }
                            }
                        }
                    }
                }
            }
        }
        g.setSigma(sigma);
        actualizarGraph(g);
    }

/*Elimina la recursividad inmediatamente a la izquierda que esta en la gramatica de gleybash*/
    public void eliminarRecursividadInmediataIzquierda(Gramatica g){
        DefaultDirectedGraph<String, DefaultEdge> graph = g.getGraph();
        Map<String, Set<Palabra>> sigma = g.getSigma();
        List<String> keys = new ArrayList<>();
        keys.addAll(g.getNoTerminales());
        for (int i = 0; i<keys.size(); i++) {
            String key = keys.get(i);
            if(graph.containsEdge(key, key)){
                String newNT = key+crearNuevaVariable('n', 1);
                sigma.put(newNT, new HashSet<Palabra>());
                int posAdd = g.getNoTerminales().indexOf(key);
                g.getNoTerminales().add(posAdd+1, newNT);
                Set<Palabra> nuevasPalabras = new HashSet<>();
                Palabra[] palabras =  sigma.get(key).toArray(new Palabra[0]);
                for (int j = 0; j< palabras.length; j++) {
                    Palabra p = palabras[j];
                    if(p.getSimbolos().contains(key)){
                        Palabra newP = new Palabra(p.getSimbolos());
                        newP.getSimbolos().remove(0);
                        newP.actualizarPalabra();
                        Palabra newP2 = new Palabra(newP.getSimbolos());
                        newP2.getSimbolos().add(newNT);
                        newP2.actualizarPalabra();
                        sigma.get(newNT).add(newP);
                        sigma.get(newNT).add(newP2);
                    }else{
                        nuevasPalabras.add(p);
                        Palabra newP = new Palabra(p.getSimbolos());
                        newP.getSimbolos().add(newNT);
                        newP.actualizarPalabra();
                        nuevasPalabras.add(newP);
                    }
                }
                sigma.put(key, nuevasPalabras);
            }
        }
        g.setSigma(sigma);
        actualizarGraph(g);
    }

}

