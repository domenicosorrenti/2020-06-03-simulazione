package it.polito.tdp.PremierLeague.model;

public class PlayerDT implements Comparable<PlayerDT>{

	private Player player;
	private double gradoTit;
	
	public PlayerDT(Player player, double gradoTit) {
		super();
		this.player = player;
		this.gradoTit = gradoTit;
		
	}
	public Player getPlayer() {
		return player;
	}
	public double getGradoTit() {
		return gradoTit;
	}

	@Override
	public int compareTo(PlayerDT other) {
		
		return (int) (other.getGradoTit() - this.gradoTit);
	}
	@Override
	public String toString() {
		return player +" : "+ gradoTit;
	}
	
	
	
	
}
