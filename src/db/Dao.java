package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import bean.Country;
import bean.Listening;

public class Dao {
	
	public List<String> getArtistiPiuAscoltati(int mese){       //credo sia giusto
		String query = "select a.artist, count(l.id) as num "
				+ "from listening l, artist a "
				+ "where l.artistid=a.id and l.month=? "
				+ "group by l.artistid "
				+ "order by num DESC "
				+ "limit 20";
		Connection conn = DBConnect.getConnection();
		try{
			List<String> bo = new LinkedList<String>();
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, mese);
			ResultSet res = st.executeQuery();
			while(res.next()){
				bo.add("Nome dell'artista: " +res.getString("artist")+"  Numero di ascolti: " +res.getInt("num")+" \n");
				
			}
			conn.close();
			return bo;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
	

	public Set<Country> getNazioni(int mese){                                //mette nazioni ripetute x via del numero 
		String query ="select  c.id, c.country, count(l.id) as num "
				+ "from listening l, artist a, country c "
				+ "where l.artistid=a.id and l.month=? and c.id=l.countryid "
				+ "group by l.artistid "
				+ "order by  num DESC "
				+ "limit 20";
		Connection conn = DBConnect.getConnection();
		try{
			Set<Country> nazioni = new HashSet<>();
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, mese);
			ResultSet res = st.executeQuery();
			while(res.next()){
				Country c = new Country(res.getInt("id"), res.getString("country"));
				if(!nazioni.contains(c)){     //ma le mette lo stesso perche il num di ascolti è diverso  
				   nazioni.add(c);
				}
			}
			conn.close();
			System.out.println(nazioni.toString());
			return nazioni;
		}catch(SQLException e ){
			e.printStackTrace();
			System.out.println("null");
			return null;
		}
	}
	
//	public List<Listening> getTuttiAscoltiDelMese(int mese){
//		String query ="select *  from listening l  where l.month=?";
//		Connection conn = DBConnect.getConnection();
//		try{
//			List<Listening> ascolti = new LinkedList<>();
//			PreparedStatement st = conn.prepareStatement(query);
//			st.setInt(1, mese);
//			ResultSet res = st.executeQuery();
//			while(res.next()){
//			Listening l = new Listening(res.getLong("id"), res.getInt("userid"), res.getInt("month"), res.getInt("weekday"), res.getDouble("longitude"), res.getDouble("latitude"),res.getInt("countryid"), res.getInt("cityid"), res.getInt("artistid"),res.getInt("trackid") );
//			ascolti.add(l);
//				}
//			conn.close();
//			return ascolti;
//		}catch(SQLException e ){
//			e.printStackTrace();
//			return null;
//		}		
//	
//	}
//	
	
	
	public List<Integer> listaArtistInComune(int c1, int  c2, int mese){
		String query ="select distinct l1.artistid "
				+ "from listening l1 , listening l2  "
				+ "where l1.month=? and l2.month=? and "
				+ "l1.countryid=?     and l2.countryid=?  "
				+ "and l1.artistid=l2.artistid";
		Connection conn = DBConnect.getConnection();
		try{
			List<Integer> artistiInComune = new LinkedList<>();
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1,  mese);
			st.setInt(2,  mese);
			st.setInt(3, c1);
			st.setInt(4, c2);
			
			ResultSet res = st.executeQuery();
			while(res.next()){
			artistiInComune.add(res.getInt("artistID"));   //lista di interi che sono id di artisti
			
				}
			conn.close();
			System.out.println(artistiInComune);
			return artistiInComune;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}		
	
	}
	
	
	
//	public static void main(String [] args){
//		Dao dao = new Dao();
//		dao.getNazioni(12);
//		//dao.almenoUnArtistaInComune(3,  4, 11);
//	}
//	
	
	
	
	
	
	
	

	
	
	
}
