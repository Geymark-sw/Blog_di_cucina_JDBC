package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Post;
import models.Utente;
import utils.DBConnection;
import utils.FunzioniUtils;

public class DaoPost {
	
	private static DaoUtente daoUtente = new DaoUtente();
	
	public DaoPost() {
		
	}
	
	public Post ritornaUltimoAggiunto() {
		String query = "SELECT * "
					+ "FROM post p "
					+ "ORDER BY p.id_post DESC "
					+ "LIMIT 1";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return cercaPerId(rs.getInt("id_post"));
			}
			return null;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Post cercaPerId(Integer idPost) {
		String query = "SELECT *"
					+ "FROM post p "
					+ "WHERE p.id_post = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return new Post(daoUtente.cercaPerNicknameOrEmailOrId(rs.getString("id_utente")), //recupero il proprietario del post
								rs.getString("titolo"),
								rs.getString("descrizione"),
								FunzioniUtils.recuperaIngredienti(idPost),
								FunzioniUtils.recuperaValutazioni(idPost),
								FunzioniUtils.recuperaValutazioneMedia(idPost),
								FunzioniUtils.recuperaCommenti(idPost)
								);
				
			}
			return null;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean inserisci(Integer idUtente, Post post) {
		String query = "INSERT INTO post(id_utente_proprietario_post, titolo, descrizione)"
					+ "VALUES (?, ?, ?)";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setInt(1, idUtente);
			stmt.setString(2, post.getTitolo());
			stmt.setString(3, post.getDescrizione());
			stmt.executeUpdate();
			if(cercaPerId(post.getIdPost()) != null) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean cancella(Integer idPost) {
		String query = "DELETE * "
					+ "FROM post p "
					+ "WHERE p.id_post = ?;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setInt(1, idPost);
			stmt.executeUpdate();
			if(cercaPerId(idPost) == null) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Post> cercaTutti(){
		List<Post> posts = new ArrayList<Post>();
		String query = "SELECT * "
					+ "FROM post;";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Post p = new Post(
						rs.getInt("id_post"),
						daoUtente.cercaPerNicknameOrEmailOrId(rs.getString("id_utente_propritario_post")),
						rs.getString("titolo"),
						rs.getString("descrizione")
						);
				p.setIngredienti(FunzioniUtils.recuperaIngredienti(p.getIdPost()));
				p.setValutazioni(FunzioniUtils.recuperaValutazioni(p.getIdPost()));
				p.setValutazioneMedia(p.getIdPost());
				p.setCommenti(FunzioniUtils.recuperaCommenti(p.getIdPost()));
				
				posts.add(p);
			}
			return posts;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	//Cerca per titolo
	//Cerca per Ingrediente
	
	public List<Post> cercaPerTitolo(String input){
		// richiamare da funzioniUtils
	}

}
