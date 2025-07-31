package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Post;
import utils.DBConnection;
import utils.FunzioniUtilsPost;


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
								FunzioniUtilsPost.recuperaIngredienti(idPost),
								FunzioniUtilsPost.recuperaValutazioni(idPost),
								FunzioniUtilsPost.recuperaValutazioneMedia(idPost),
								FunzioniUtilsPost.recuperaCommenti(idPost)
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
				p.setIngredienti(FunzioniUtilsPost.recuperaIngredienti(p.getIdPost()));
				p.setValutazioni(FunzioniUtilsPost.recuperaValutazioni(p.getIdPost()));
				p.setValutazioneMedia(p.getIdPost());
				p.setCommenti(FunzioniUtilsPost.recuperaCommenti(p.getIdPost()));
				
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
	
	public List<Post> cercaPerTitolo(String titolo){
		// richiamare da funzioniUtils
		return FunzioniUtilsPost.trovaPostTitoliSimili(titolo, cercaTutti(), 0.5);
	}
	
	public List<Post> cercaPerIngredienti(List<String> ingredienti){
		return FunzioniUtilsPost.trovaPostIngredientiSimili(ingredienti, this.cercaTutti(), 0.5);
	}
	
	public List<Post> cercaPostPerParolaChiaveInDescrizione(String parolaChiave){
		return FunzioniUtilsPost.trovaPostPerParolaChiaveInDescrizione(parolaChiave);
	}
	
	
}
