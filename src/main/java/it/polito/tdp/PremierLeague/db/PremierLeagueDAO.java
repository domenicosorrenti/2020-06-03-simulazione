package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Arco;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Team;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Team> listAllTeams(){
		String sql = "SELECT * FROM Teams";
		List<Team> result = new ArrayList<Team>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Team team = new Team(res.getInt("TeamID"), res.getString("Name"));
				result.add(team);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Player> getVertici(Map<Integer, Player> mapPlayers, float x){
		
		String sql = "SELECT PlayerID as id, AVG(Goals) as media "
				+ "FROM actions "
				+ "GROUP BY PlayerID "
				+ "HAVING AVG(Goals) > ?";
		
		List<Player> result = new ArrayList<Player>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setFloat(1, x);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Player p = mapPlayers.get(res.getInt("id"));
				result.add(p);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Arco> getArchi(Map<Integer, Player> mapPlayers, float x){
		
		String sql = "SELECT a1.PlayerID as id1, a2.PlayerID as id2, (SUM(a1.TimePlayed) - SUM(a2.TimePlayed)) AS s "
				+ "FROM actions a1, actions a2 "
				+ "WHERE a1.MatchID = a2.MatchID AND a1.Starts = 1 AND a1.Starts = a2.Starts "
				+ "AND a1.PlayerID > a2.PlayerID AND a1.TeamID <> a2.TeamID "
				+ "AND a1.PlayerID IN (SELECT PlayerID "
				+ "                    FROM actions "
				+ "                    GROUP BY PlayerID "
				+ "                    HAVING AVG(Goals) > ?) "
				+ "AND a2.PlayerID IN (SELECT PlayerID "
				+ "                    FROM actions "
				+ "                    GROUP BY PlayerID "
				+ "                    HAVING AVG(Goals) > ?) "
				+ "GROUP BY a1.PlayerID,a2.PlayerID "
				+ "HAVING s <> 0";
		

		List<Arco> result = new ArrayList<Arco>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setFloat(1, x);
			st.setFloat(2, x);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Player p1 = mapPlayers.get(res.getInt("id1"));
				Player p2 = mapPlayers.get(res.getInt("id2"));
				float peso = res.getFloat("s");
				Arco a = new Arco(p1, p2, peso);
				
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public List<Arco> getArchi2(Map<Integer, Player> mapPlayers){
		
		String sql = "SELECT a1.PlayerID as id1, a2.PlayerID as id2, (SUM(a1.TimePlayed) - SUM(a2.TimePlayed)) AS s "
				+ "FROM actions a1, actions a2 "
				+ "WHERE a1.MatchID = a2.MatchID AND a1.Starts = 1 AND a1.Starts = a2.Starts "
				+ "AND a1.PlayerID <> a2.PlayerID AND a1.TeamID <> a2.TeamID "
				+ "GROUP BY a1.PlayerID,a2.PlayerID "
				+ "HAVING s > 0";
		

		List<Arco> result = new ArrayList<Arco>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Player p1 = mapPlayers.get(res.getInt("id1"));
				Player p2 = mapPlayers.get(res.getInt("id2"));
				float peso = res.getFloat("s");
				Arco a = new Arco(p1, p2, peso);
				
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
