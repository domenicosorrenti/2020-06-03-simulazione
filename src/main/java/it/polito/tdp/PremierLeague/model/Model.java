package it.polito.tdp.PremierLeague.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {

	private Graph<Player, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	
	private Map<Integer, Player> mapPlayers;
	
	
	public Model() {
		dao = new PremierLeagueDAO();
		this.mapPlayers = new HashMap<>();
		
		for(Player p : dao.listAllPlayers())
			this.mapPlayers.put(p.playerID, p);
		
	}
	
	public void creaGrafo(float x) {
		
		this.grafo = new SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List<Player> vertici = dao.getVertici(mapPlayers, x);
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		List<Arco> archi = dao.getArchi(mapPlayers, x);
		
		for(Arco a : archi) {
			
			if(a.getPeso() > 0)
				Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(), a.getPeso() );
			else
				Graphs.addEdgeWithVertices(this.grafo, a.getP2(), a.getP1(), ( (float) -1) * a.getPeso() );	
		}
	}
	
	public int getnVertici(){
		return this.grafo.vertexSet().size();
	}
	
	public int getnArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<TopPlayer> listTopPlayers(){
		
		List<TopPlayer> l = new ArrayList<TopPlayer>();
		
		for(Player p : this.grafo.vertexSet()) {
			
			double a = 0;
			
			for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(p))
				a = 0;
			
		}
		
		return null;
		
	}
	
	
}
