package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Graph<Country, DefaultEdge> grafo;
	private Map<Integer, Country> idMap;
	private BordersDAO dao;

	public Model() {
		dao = new BordersDAO();
		idMap= new HashMap<>();
		dao.loadAllCountries(idMap);
	}
	
	public void createGraph(int anno) {
		grafo = new SimpleGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getVertici(anno,idMap));
		
		for (Border b: dao.getCountryPairs(anno,idMap)) {
			Graphs.addEdgeWithVertices(grafo, b.getC1(), b.getC2());
		}
		
		System.out.format("Grafo creato con %d vertici E %d archi\n", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
	}
	
	

	public Graph<Country, DefaultEdge> getGrafo() {
		return grafo;
	}

	public void setGrafo(Graph<Country, DefaultEdge> grafo) {
		this.grafo = grafo;
	}

	public int getNConnessioni () {
		
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<Country, DefaultEdge>(grafo);
		return ci.connectedSets().size();
	}

	public List<Country> getCountries() {
		List<Country> countries= new ArrayList<Country> ();
		
		for (Country c: grafo.vertexSet())
			countries.add(c);
		
		return countries;
	}
	
	//ESERCIZIO 2, funzionalità "Stati raggiungibili" 
	
	//metodo con BreadthFirstIterator
	
}
