package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			if(cerca(c.getIdCommento()) != null ) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private Commento cerca(Long idCommento) {
		String query = "SELECT * "
					+ "FROM commento c "
					+ "WHERE c.id_commento = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setLong(1, idCommento);
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
	
	public boolean elimina(Long idCommento) {
		String query = "DELET * "
					+ "FROM commento c "
					+ "WHERE c.id_commento = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setLong(1, idCommento);
			stmt.executeQuery();
			if(cerca(idCommento) == null) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Commento> cercaTutti(Long idPost){
		List<Commento> commenti = new ArrayList<Commento>();
		String query = "SELECT * "
					+ "FROM commento c JOIN post p ON c.id_post = p.id_post "
					+ "WHERE p.id_post = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setLong(1, idPost);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				commenti.add(new Commento(rs.getLong("id_commento"),
						daoUtente.cercaPerNicknameOrEmailOrId(rs.getString("id_utente")),
						rs.getString("commento")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
