/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 *
 * @author Diana Lucia Figueroa
 */

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Gramatica {

    private List<String> terminales;
    private List<String> noTerminales;
    private String inicial;
    private Map<String, Set<Palabra>> sigma;
    private DefaultDirectedGraph<String, DefaultEdge> graph; /*Un grafo dirigido creado con la libreria*/

    public Gramatica(){}

    public Gramatica(List<String> terminales, List<String> noTerminales, String inicial, Map<String, Set<Palabra>> sigma){
        this.terminales = terminales;
        this.noTerminales = noTerminales;
        this.inicial = inicial;
        this.sigma = sigma;
    }

    public Gramatica(List<String> terminales, List<String> noTerminales, String inicial){
        this.terminales = terminales;
        this.noTerminales = noTerminales;
        this.inicial = inicial;
    }

    public String getInicial() {
        return inicial;
    }

    public List<String> getTerminales() {
        return terminales;
    }

    public List<String> getNoTerminales() {
        return noTerminales;
    }

    public Map<String, Set<Palabra>> getSigma() {
        return sigma;
    }

    public void setInicial(String inicial) {
        this.inicial = inicial;
    }
    
    public void setTerminales(List<String> terminales) {
        this.terminales = terminales;
    }

    public void setNoTerminales(List<String> noTerminales) {
        this.noTerminales = noTerminales;
    }

    public void setSigma(Map<String, Set<Palabra>> sigma) {
        this.sigma = sigma;
    }

    public DefaultDirectedGraph<String, DefaultEdge> getGraph() {
        return graph;
    }

    public void setGraph(DefaultDirectedGraph<String, DefaultEdge> graph) {
        this.graph = graph;
    }

    @Override
    public String toString() {
        StringBuilder gramatica = new StringBuilder();
        SortedSet<String> sortedNT = new TreeSet<>();
        sortedNT.addAll(this.noTerminales);
        gramatica.append("Variables no terminales: "+this.noTerminales.toString()+"\n");
        gramatica.append("Variable inicial: "+this.inicial+"\n");
        gramatica.append("Variables terminales: "+this.terminales.toString()+"\n");
        gramatica.append("Sigma:\n");
        List<String> keys = this.noTerminales;
        SortedSet<String> sortedKeys = new TreeSet<>();
        sortedKeys.addAll(keys);
        for (String key : keys) {
            Set<Palabra> palabras = this.sigma.get(key);
            if(palabras!=null){
                gramatica.append(key+" -> ");
                for (Palabra palabra : palabras) {
                    gramatica.append(palabra.getPalabra()+"/");
                }
                gramatica.append("\n");
            }
        }
        return gramatica.toString();
    }

    public String sigmaToString(){
        /*StringBuilder s = new StringBuilder();
        for (String key : this.noTerminales) {
            s.append(key+" -> ");
            Set<Palabra> palabras = this.sigma.get(key);
            for (Palabra palabra : palabras) {
                s.append(palabra.getPalabra()+"/");
            }
            s.deleteCharAt(s.length()-1);
            s.append("<br>");
        }
        return s.toString();*/
        StringBuilder msg = new StringBuilder();
        List<String> keys = this.noTerminales;
        SortedSet<String> sortedKeys = new TreeSet<>();
        sortedKeys.addAll(keys);
        for (String key : keys) {
            Set<Palabra> palabras = this.sigma.get(key);
            if(palabras!=null){
                msg.append(key+" -> ");
                for (Palabra palabra : palabras) {
                    msg.append(palabra.getPalabra()+"/");
                }
                msg.append("<br>");
            }
        }
        return msg.toString();
    }


}
