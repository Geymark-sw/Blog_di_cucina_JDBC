package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Post;
import models.Valutazione;
import utils.DBConnection;

public class DaoValutazione {
	
	private static DaoUtente daoUtente = new DaoUtente();
	
	public DaoValutazione() {
		
	}
	//il voto deve essere compreso tra 0 e 5
	public boolean inserisci(Valutazione v, Post p) {
		String query = "INSERT INTO valutazione(id_utente, id_post, voto) "
					+ "VALUES(?, ?, ?);";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setLong(1, v.getProprietario().getIdUtente());
			stmt.setLong(2, p.getIdPost());
			stmt.setInt(3, v.getValutazione());
			stmt.executeUpdate();
			if(cerca(v.getIdValutazione()) != null) {
				return true;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean elimina(Long idValutazione) {
		String query = "DELETE * "
					+ "FROM valutazione v "
					+ "WHERE v.id_valutazione = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setLong(1, idValutazione);
			stmt.executeUpdate();
			if(cerca(idValutazione) == null) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public Valutazione ritornaUltimoAggiunto() {
		String query = "SELECT * "
					+ "FROM valutazione v "
					+ "ORDER BY v.id_valutazione DESC "
					+ "LIMIT 1";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return cerca(rs.getLong("id_valutazione"));
			}
			return null;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	private Valutazione cerca(Long idValutazione) {
		String query = "SELECT * "
					+ "FROM valutazione v "
					+ "WHERE v.id_valutazione = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setLong(1, idValutazione);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return new Valutazione(rs.getLong("id_valutazione"),
										daoUtente.cercaPerNicknameOrEmailOrId(rs.getString("id_utente")),
										rs.getInt("voto"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Valutazione> cercaTutti(){
		List<Valutazione> valutazioni = new ArrayList<Valutazione>();
		String query = "SELECT *"
					+ "FROM valutazione";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				valutazioni.add(new Valutazione(rs.getLong("id_valutazione"),
												daoUtente.cercaPerNicknameOrEmailOrId(rs.getString("id_utente")),
												rs.getInt("voto")));
			}
			return valutazioni;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
