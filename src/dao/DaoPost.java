package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
				return cerca(rs.getString("id_post"));
			}
			return null;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Post cerca(Integer idPost) {
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

	public boolean inserisci(Integer id_utente, Post post) {
		String query = "INSERT INTO post()";
	}

}
