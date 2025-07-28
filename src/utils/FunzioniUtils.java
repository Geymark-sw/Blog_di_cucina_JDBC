package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DaoUtente;

public class FunzioniUtils {
	private static DaoUtente daoUtente = new DaoUtente();
	
	public static boolean emailExists(String email) {
		String query = "SELECT COUNT(*) FROM utente WHERE email = ?";
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query)){
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			return rs.getInt(1) > 0;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
