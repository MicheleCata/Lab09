package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Graph<Country, DefaultEdge> grafo;
	private Map<Integer, Country> idMap;
	private BordersDAO dao;
	
	private Map<Country,Country> visita;
	
	private List<Country> percorso;

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
	
	public List<Country> trovaStati (Country partenza) {
		
		List<Country> percorso = new LinkedList<>();
		BreadthFirstIterator<Country, DefaultEdge> it = new BreadthFirstIterator<> (grafo, partenza);
		visita = new HashMap<>();
		visita.put(partenza, null);
		it.addTraversalListener(new TraversalListener<Country, DefaultEdge>() {

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				Country c1= grafo.getEdgeSource(e.getEdge());
				Country c2= grafo.getEdgeTarget(e.getEdge());
				
				if (visita.containsKey(c1) &&  !visita.containsKey(c2)) {
					visita.put(c2, c1);
				}
				
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		while (it.hasNext()) {
			percorso.add(it.next());
		}
		
		if(!visita.containsKey(partenza))
			return null;
		
		return percorso;
	}
	
	// metodo con ALGORITMO RICORSIVO
	public List<Country> trovaStati2 (Country partenza) {
		this.percorso=new ArrayList<>();
		List<Country> parziale = new ArrayList<>();
		
		parziale.add(partenza);
		cerca(parziale);
		
		return this.percorso;
		
	}

	private void cerca(List<Country> parziale) {
		
		percorso= new ArrayList<>(parziale);
		for (Country stato : Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
			if (!parziale.contains(stato)) {
				parziale.add(stato);
				cerca(parziale);
			}
		}
		
	}
	
}
