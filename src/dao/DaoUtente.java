package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Utente;
import utils.DBConnection;
import utils.FunzioniUtils;

public class DaoUtente {
	
	public DaoUtente() {
		
	}
	
	public boolean inserisci(Utente u) {
		String query = "INSERT INTO utente(ruolo, nome, cognome, nickname, email, password) "
					+ "VALUES(?, ?, ?, ?, ?, ?);";
		
		if(FunzioniUtils.emailExists(u.getEmail())) {
			System.out.println("Impossibile effettuare la registrazione, l'email e' gia' esistente");
			return false;
		}else if(FunzioniUtils.nicknameExists(u.getNickname())){
			System.out.println("Impossibile effettuare la registrazione, il nickname e' gia' esistente");
			return false;
		}
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setString(1, u.getRuolo().toString());			//I metodi sotto servono per rendere la stringa in modo tacle che cominci con la Maiuscola
			stmt.setString(2, u.getNome().substring(0, 1).toUpperCase() + u.getNome().substring(1).toLowerCase());
			stmt.setString(3, u.getCognome().substring(0, 1).toUpperCase() + u.getCognome().toLowerCase());
			stmt.setString(4, u.getNickname());
			stmt.setString(5, u.getEmail().toLowerCase());
			stmt.setString(6,u.getPassword());
			stmt.executeUpdate();
			System.out.println("Registrazione effettuata con successo!\nBenvenuto " + u.getNickname());
			return true;
					
			
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
			System.out.println("Utente non presente nel sistema!");
			return false;
		}
	}
	
	public Utente cercaPerNicknameOrEmailOrId(String nicknameOrEmailOrId) {
		String query = "SELECT *"
					+ "FROM utente u"
					+ "WHERE u.nickname = ? OR u.email = ? OR u.id_utente = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setString(1, nicknameOrEmailOrId);
			stmt.setString(2, nicknameOrEmailOrId);
			stmt.setString(3, nicknameOrEmailOrId);
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
				Utente u = cercaPerNicknameOrEmailOrId(rs.getString("nickname"));
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
				return cercaPerNicknameOrEmailOrId(rs.getString("nickname"));
			}
			return null;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
