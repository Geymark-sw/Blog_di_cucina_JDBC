package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Utente;
import utils.DBConnection;

public class DaoUtente {
	
	public DaoUtente() {
		
	}
	
	public boolean inserisci(Utente u) {
		String query = "INSERT INTO utente(ruolo, nome, cognome, nickname, email, password) "
					+ "VALUES(?, ?, ?, ?, ?, ?);";
		
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			if(cercaPerNicknameOrEmail(stmt.getSt))
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean cancella(String nicknameOrEmail) {
		String query = "DELETE * "
					+ "FROM utente u "
					+ "WHERE u.nickname = ? OR u.email = ?";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setString(1, nicknameOrEmail);
			stmt.setString(2, nicknameOrEmail);
			stmt.executeUpdate();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Utente cercaPerNicknameOrEmail(String nicknameOrEmail) {
		String query = "SELECT *"
					+ "FROM utente u"
					+ "WHERE u.nickname = ? OR u.email = ?";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setString(1, nicknameOrEmail);
			stmt.setString(2, nicknameOrEmail);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return new Utente(rs.getString("ruolo"), 
								rs.getString("nome"), 
								rs.getString("cognome"), 
								rs.getString("nickname"),
								rs.getString("email"),
								rs.getString("password"));
			}
			return null;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Utente> selezionaTutti(){
		List<Utente> utenti = new ArrayList<Utente>();
		String query = "SELECT * "
					+ "FROM utente;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Utente u = cercaPerNicknameOrEmail(rs.getString("nickname"));
				utenti.add(u);
			}
			return utenti;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Utente ritornaUltimoAggiunto() {
		String query = "SELECT * "
					+ "FROM utente u "
					+ "ORDER BY u.id_utente DESC "
					+ "LIMIT 1";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return cercaPerNicknameOrEmail(rs.getString("nickname"));
			}
			return null;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
