package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Utente;
import utils.DBConnection;

public class DaoFollows {
	
	public DaoFollows() {
		
	}
	
	public static boolean segui(Utente follower, Utente followed) {
		String query = "INSERT INTO follows(id_follower, id_followed)"
					+ "VALUES(?, ?)";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query)){
			stmt.setLong(1, followed.getIdUtente());
			stmt.setLong(2, followed.getIdUtente());
			stmt.executeUpdate();
			if(cerca(follower.getIdUtente(), followed.getIdUtente())) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean cerca(long idUtenteFollower, long idUtenteFollowed) {
		String query = "SELECT * "
					+ "FROM follows f "
					+ "WHERE f.id_follower = ? AND f.id_followed = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setLong(1, idUtenteFollower);
			stmt.setLong(2, idUtenteFollowed);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean rimuoviFollow(long idUtenteFollower, long idUtenteFollowed) {
		String query = "DELETE * "
					+ "FROM follows f "
					+ "WHERE f.id_follower = ? AND f.id_followed = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setLong(1, idUtenteFollower);
			stmt.setLong(2, idUtenteFollowed);
			stmt.executeUpdate();
			if(!cerca(idUtenteFollower, idUtenteFollowed)) { //Se non trova la relazione di follow
				return true; 								//Allora rimozione effettuata con successo
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;	
	}

}
