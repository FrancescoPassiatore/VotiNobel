package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	
	private List<Esame> allEsami;
	private Set<Esame> migliore; // Meglio avere un set perchè non vogliamo valori ripetuti
	private double mediaMigliore;

	public Model() {
		EsameDAO esameDao = new EsameDAO();
		this.allEsami = esameDao.getTuttiEsami();
		
	}
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		migliore = new HashSet<>();
		mediaMigliore = 0.0;
		
		Set<Esame> parziale = new HashSet<>();
		
		cercaMigliore(parziale,0,numeroCrediti);
		
		return migliore;	
	}

	private void cercaMigliore(Set<Esame> parziale, int L ,int numeroCrediti) {
		
		int sommaCrediti = sommaCrediti(parziale);
		
		if (sommaCrediti > numeroCrediti) {
			return;
		}
		if (sommaCrediti == numeroCrediti) {
			double mediaVoti = calcolaMedia(parziale);
			if (mediaVoti > mediaMigliore) {
				mediaMigliore = mediaVoti;
				migliore = new HashSet<>(parziale);}// non si puo fare migliore = parziale , faccio solo una copia del riferimento cosi
			return;
		}
		if (L == allEsami.size()) {
			return;
			
		}
		// provo ad aggiungere il prossimo elemento
		// L = 0 {e1}
		// L = 1 {e1,e2} = {e1}/{e2} = {}
		parziale.add(allEsami.get(L));
		cercaMigliore(parziale,L+1,numeroCrediti);
		parziale.remove(allEsami.get(L));
		
		// provo ad aggiungere il prossimo elemento
		cercaMigliore(parziale,L+1,numeroCrediti);
		
	}
	
	private void cerca(Set<Esame> parziale, int L, int numeroCrediti) {
		
		int sommaCrediti = sommaCrediti(parziale);
		
		if (sommaCrediti > numeroCrediti) {
			return;
		}
		if (sommaCrediti == numeroCrediti) {
			double mediaVoti = calcolaMedia(parziale);
			if (mediaVoti > mediaMigliore) {
				mediaMigliore = mediaVoti;
				migliore = new HashSet<>(parziale);}// non si puo fare migliore = parziale , faccio solo una copia del riferimento cosi
			return;
		}
		if (L == allEsami.size()) {
			return;
			
		}
		
		// se arrivo qui, numeroCrediti > sommaCrediti
		
		for(Esame e : allEsami) {
			
			if (!parziale.contains(e)) {
				parziale.add(e);
				cerca(parziale,L+1,numeroCrediti);
				parziale.remove(e); //necessario fare BACKTRACKING
			}
			
		}
		}
	
		
		
	
	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return (double) somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
