package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DaoPost;
import dao.DaoUtente;
import models.Commento;
import models.Valutazione;

public class FunzioniUtils {
	private static DaoUtente daoUtente = new DaoUtente(); //Per richiamare eventualmente alcune funzioni del DAO
	private static DaoPost daoPost = new DaoPost();
	
	public static boolean emailExists(String email) {
		String query = "SELECT COUNT(*) FROM utente WHERE email = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query)){
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			return rs.getInt(1) > 0; //Seleziona la prima colonna(il contenuto del count) se quest'ultimo ha un
									//valore superiore a 0 voul dire che esiste
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean nicknameExists(String nickname) {
		String query = "SELECT COUNT(*) FROM utente WHERE nickname = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setString(1, nickname);
			ResultSet rs = stmt.executeQuery();
			return rs.getInt(1) > 0; 
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static List<String> recuperaIngredienti(Integer idPost){
		List<String> ingredienti = new ArrayList<String>();
		String query = "SELECT i.nome "
					+ "FROM ingrediente i "
					+ "JOIN post_ingrediente pi ON i.id_ingrediente = pi.id_ingrediente "
					+ "WHERE i.id_post = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				ingredienti.add(rs.getString("nome"));
			}
			return ingredienti;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Valutazione> recuperaValutazioni(Integer idPost) {
		List<Valutazione> valutazioni = new ArrayList<Valutazione>();
		String query = "SELECT * "
					+ "FROM valutazione v "
					+ "JOIN post p ON v.id_post = p.id_post "
					+ "WHERE v.id_post = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setInt(1, idPost);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				valutazioni.add(new Valutazione(
										daoUtente.cercaPerNicknameOrEmailOrId(rs.getString("id_utente")),
										rs.getInt("voto")));
			}
			return valutazioni;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Integer recuperaValutazioneMedia(Integer idPost) {
		List<Valutazione> valutazioni = new ArrayList<Valutazione>();
		Integer sommaValutazioni = 0;
		String query = "SELECT * "
					+ "FROM valutazione v "
					+ "JOIN post p ON v.id_post = p.id_post "
					+ "WHERE v.id_post = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setInt(1, idPost);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Valutazione val = new Valutazione(
										daoUtente.cercaPerNicknameOrEmailOrId(rs.getString("id_utente")),
										rs.getInt("voto"));
				sommaValutazioni += val.getValutazione();
				valutazioni.add(val);
			}
			
			return sommaValutazioni / valutazioni.size();
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Commento> recuperaCommenti(Integer idPost) {
		List<Commento> commenti = new ArrayList<Commento>();
		String query = "SELECT * "
					+ "FROM commento c "
					+ "JOIN post p ON c.id_post = p.id_post "
					+ "WHERE c.id_post = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setInt(1, idPost);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				commenti.add(new Commento(
										daoUtente.cercaPerNicknameOrEmailOrId(rs.getString("id_utente")),
										rs.getString("commento")));
			}
			return commenti;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
