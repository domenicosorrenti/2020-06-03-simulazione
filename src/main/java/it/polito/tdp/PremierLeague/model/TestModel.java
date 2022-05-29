package it.polito.tdp.PremierLeague.model;

import java.util.*;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		model.creaGrafo( (float) 0.5);
		
		List<PlayerDT> dream = model.getDreamTeam(4);
		
		for(PlayerDT pdt : dream)
			System.out.println(pdt);

	}

}
