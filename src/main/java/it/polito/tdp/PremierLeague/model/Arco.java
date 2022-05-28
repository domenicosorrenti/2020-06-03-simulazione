package it.polito.tdp.PremierLeague.model;

public class Arco {

	Player p1;
	Player p2;
	float peso;
	public Arco(Player p1, Player p2, float peso) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.peso = peso;
	}
	public Player getP1() {
		return p1;
	}
	public Player getP2() {
		return p2;
	}
	public float getPeso() {
		return peso;
	}
	
	
}
