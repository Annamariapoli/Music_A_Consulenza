package application;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import bean.Country;
import bean.Listening;
import db.Dao;

public class Model {
	
	private Dao dao = new Dao();
	private SimpleWeightedGraph<Country, DefaultWeightedEdge> grafo = null;
	
	public List<String> getMesi(){                     //ok
		List<String> mesi = new LinkedList<>();
		String s1 = "Gennaio";
		String s2 = "Febbraio";
		String s3 = "Marzo";
		String s4 = "Aprile";
		String s5 = "Maggio";
		String s6 = "Giungo";
		String s7= "Luglio";
		String s8 = "Agosto";
		String s9 = "Settembre";
		String s10 ="Ottobre";
		String s11 ="Novembre";
		String s12= "Dicembre";
		mesi.add(s1);
		mesi.add(s2);
		mesi.add(s3);
		mesi.add(s4);
		mesi.add(s5);
		mesi.add(s6);
		mesi.add(s7);
		mesi.add(s8);
		mesi.add(s9);
		mesi.add(s10);
		mesi.add(s11);
		mesi.add(s12);
		return mesi;
	}

	
	public int getNumeroMese(String nomeMese){     //ok
		int num =0;
		if(nomeMese=="Gennaio"){
			num =1;
		}
		if(nomeMese=="Febbraio"){
			num =2;
		}
		if(nomeMese=="Marzo"){
			num =3;
		}
		if(nomeMese=="Aprile"){
			num =4;
		}
		if(nomeMese=="Maggio"){
			num =5;
		}
		if(nomeMese=="Giungo"){
			num =6;
		}
		if(nomeMese=="Luglio"){
			num =7;
		}
		if(nomeMese=="Agosto"){
			num =8;
		}
		if(nomeMese=="Settembre"){
			num =9;
		}
		if(nomeMese=="Ottobre"){
			num =10;
		}
		
		if(nomeMese=="Novembre"){
			num =11;
		}
		if(nomeMese=="Dicembre"){
			num =12;
		}
		return num;
	}
	
	
	
	public List<String> getPiuAsco(int mese){                     //ok
		List<String> que = dao.getArtistiPiuAscoltati(mese);
		return que;
	}
	
	public Set<Country> getNazioni(int mese){            //nazioni ripetute
		Set<Country> nazioni = new HashSet<>();
		nazioni = dao.getNazioni(mese);
		return nazioni;
	}
//	
//	public List<Listening> getTuttiAscolti(int mese){
//		List<Listening> ascolti = dao.getTuttiAscoltiDelMese(mese);
//		return ascolti;
//	}
	
	public List<Integer> getListaArtistiComune(int c1, int c2, int mese){
		List<Integer> art = dao.listaArtistInComune(c1, c2, mese);
		return art;
	}
	
	
	
	//il peso dell'arco è il mumero di artisti in comune
	//se hanno  almeno un artista in comune in quel mese, metto arco
	
	public SimpleWeightedGraph<Country, DefaultWeightedEdge> buildGraph(int mese){
		grafo = new SimpleWeightedGraph<Country, DefaultWeightedEdge> (DefaultWeightedEdge.class);
		Set<Country> nazioni = getNazioni(mese);
		Graphs.addAllVertices(grafo, nazioni);
				
		for(Country c1 : grafo.vertexSet()){
			for(Country c2 : grafo.vertexSet()){
				if(!c1.equals(c2)){	
					List<Integer> artistiComuni= getListaArtistiComune(c1.getId(), c2.getId(), mese);
					if(artistiComuni.size()!=0){                                         //se hanno almeno un atista in comune
						Graphs.addEdge(grafo, c1, c2, artistiComuni.size());
					}
			    }
		    }
	     }
	     System.out.println(grafo.toString());
	     return grafo;
	
	}
	
	
	public int maxdistanza(SimpleWeightedGraph<Country, DefaultWeightedEdge>grafo){  //max num di collegamenti di un vertice
		int max =0;
		for(Country c : grafo.vertexSet()){
			int contoGrado= grafo.degreeOf(c);
			if(contoGrado> max){
				max=contoGrado;
			}
		}
		return max;   //grado piu alto
	}
	
	
	public double getMaxDistanzaTraDueNodi(SimpleWeightedGraph<Country, DefaultWeightedEdge>grafo){  //arco ke ha peso maggiore
		double maxPeso=0;
		for(DefaultWeightedEdge e : grafo.edgeSet()){
			if(grafo.getEdgeWeight(e) > maxPeso){
				maxPeso = grafo.getEdgeWeight(e);
				
			}
		}
		return maxPeso;
	}
	
	
	
	
	public static void main(String [] args){
		Model m = new Model();
		m.buildGraph(11);			
	}
}
