package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Commento;
import models.Post;
import utils.DBConnection;

public class DaoCommento {
	
	private DaoUtente daoUtente = new DaoUtente();
	
	public DaoCommento() {
		
	}
	
	public boolean inserisci(Post p, Commento c) {
		String query = "INSERT INTO commento(id_post, id_utente, commento) "
					+ "VALUES(?, ?, ?)";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setLong(1, p.getIdPost());
			stmt.setLong(2, c.getProprietario().getIdUtente());
			stmt.setString(3, c.getCommento());
			stmt.executeUpdate();
			if(cerca(c.get))
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public Commento cercaUltimoAggiunto() {
		String query = "SELECT * "
					+ "FROM commento c "
					+ "ORDER BY id_commento DESC "
					+ "LIMIT 1;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return new Commento(rs.getLong("id_commento"),
									daoUtente.cercaPerNicknameOrEmailOrId(rs.getString("id_utente")),
									rs.getString("commento"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
