package it.polito.tdp.PremierLeague.model;

public class TopPlayer implements Comparable<TopPlayer>{

	private Player p;
	private double delta;
	public TopPlayer(Player p, double delta) {
		super();
		this.p = p;
		this.delta = delta;
	}
	public Player getP() {
		return p;
	}
	public double getDelta() {
		return delta;
	}
	@Override
	public int compareTo(TopPlayer other) {
		
		return (int) (other.getDelta() - this.delta);
	}
	
	
}
