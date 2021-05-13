package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer, Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c= new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				idMap.put(c.getCodC(), c);
			}
			
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Country> getVertici(int anno,Map<Integer,Country>idMap) {
		String sql = "Select DISTINCT cc.CCode "+
				"From contiguity c, country cc "+
				"where (cc.CCode=c.`state1no` OR cc.CCode=c.state2no) AND    year <= ? AND c.conttype=1";
		List<Country> result = new ArrayList<Country>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				result.add((idMap.get(rs.getInt("cc.CCode"))));
			}
			
		conn.close();
		return result;
		
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
		
	public List<Border> getCountryPairs(int anno, Map <Integer, Country> idMap) {
		String sql ="Select c.state1no,c.state2no "
				+ "From contiguity c "
				+ "where c.state1no>c.state2no AND (c.year<=?) AND c.conttype=1";
		
		List<Border> result=new ArrayList<Border>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Country c1= idMap.get(rs.getInt("c.state1no"));
				Country c2= idMap.get(rs.getInt("c.state2no"));
				result.add(new Border(c1,c2));
			}
			conn.close();
			return result;
			
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Errore connessione al database");
				throw new RuntimeException("Error Connection Database");
			}
		
	}
	
}
