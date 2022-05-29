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
	
	private List<PlayerDT> listPlayerDT;
	private List<PlayerDT> dreamTeam;
	private double gradoMax;
	
	
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
	
	public Player getTopPlayer(){
		
		Player best = null;
		int max = Integer.MIN_VALUE;
		
		for(Player p : this.grafo.vertexSet()) 
			if(this.grafo.outDegreeOf(p) > max) {
				max = this.grafo.outDegreeOf(p);
				best = p;
			}
			
		return best;
		
	}
	
	public List<Opponent> getOpponents(Player top){
		
		List<Opponent> l = new ArrayList<Opponent>();
		
		for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(top)) {
			
			Opponent o = new Opponent(this.grafo.getEdgeTarget(e), this.grafo.getEdgeWeight(e));
			
			l.add(o);
		}
		
		Collections.sort(l);
		return l;
			
	}
	
	
	//Parte 2!
	
	
	public List<PlayerDT> getPlayerDT(){
		
		if(this.grafo == null)
			return null;
		
		List<PlayerDT> l = new ArrayList<>();
		
		for(Player p : this.grafo.vertexSet()) {
			
			double a = 0;
			for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(p))
				a += this.grafo.getEdgeWeight(e);
			
			double b = 0;
			for(DefaultWeightedEdge e : this.grafo.incomingEdgesOf(p))
				b += this.grafo.getEdgeWeight(e);
			
			double c = a-b;
			
			PlayerDT pdt = new PlayerDT(p, c);
			l.add(pdt);
		}
		
		Collections.sort(l);
		
		return l;
		
	}
	
	
	public List<PlayerDT> getDreamTeam(int k){
		
		this.listPlayerDT = getPlayerDT();
		List<PlayerDT> parziale = new ArrayList<>();
		dreamTeam = new ArrayList<>();
		gradoMax = Integer.MIN_VALUE;
		
		cerca(parziale, 0, k);
		return dreamTeam;
	}
	
	private void cerca(List<PlayerDT> parziale, int livello, int k) {
		
		if(livello == k) {
			
			if(calcolaGrado(parziale) > gradoMax) {
				gradoMax = calcolaGrado(parziale);
				dreamTeam = new ArrayList<>(parziale);
			}
			
		}else {
			
			for(PlayerDT pdt : this.listPlayerDT) {
				
				if(!parziale.contains(pdt)) {
					parziale.add(pdt);
					
					if(aggiuntaPossibile(parziale))
						this.cerca(parziale, livello+1, k);
					
					parziale.remove(parziale.size()-1);
				}
				
			}
			
		}
		
		
	}

	private boolean aggiuntaPossibile(List<PlayerDT> parz) {
		
		if(parz.size() <= 1)
			return true;
		
		boolean ok = true;

		PlayerDT last = parz.remove(parz.size()-1);
		
		for(PlayerDT p : parz) {
			
			for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(p.getPlayer()))
				if(this.grafo.getEdgeTarget(e).equals(last.getPlayer())) {
					ok = false;
					break;
				}
		}
		
		return ok;
	}

	private double calcolaGrado(List<PlayerDT> parziale) {

		double grado = 0;
		
		for(PlayerDT p : parziale) {
			grado += p.getGradoTit();
		}

		return grado;
	}
	
}
