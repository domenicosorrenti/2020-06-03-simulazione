package it.polito.tdp.PremierLeague.model;

public class Opponent implements Comparable<Opponent> {

	private Player opponent;
	private double peso;
	public Opponent(Player opponent, double peso) {
		super();
		this.opponent = opponent;
		this.peso = peso;
	}
	
	public Player getOpponent() {
		return opponent;
	}

	public double getPeso() {
		return peso;
	}

	@Override
	public int compareTo(Opponent other) {
		
		return (int) (other.getPeso() - this.peso);
	}

	@Override
	public String toString() {
		return opponent +" - "+ peso;
	}
	
	
	
	
}
