package it.polito.tdp.borders.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		System.out.println("Creo il grafo relativo al 2000");
        model.createGraph(2000);
		
        List<Country> countries = model.getCountries();
        System.out.format("Trovate %d nazioni\n", countries.size());

        System.out.format("Numero componenti connesse: %d\n", model.getNConnessioni());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
        List<Country> statiRaggiungibili = model.trovaStati2(model.getCountries().get(8));
        int count = statiRaggiungibili.size();
        System.out.println(count);
	
	}

}
