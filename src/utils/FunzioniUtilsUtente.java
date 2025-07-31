package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dao.DaoUtente;
import models.Utente;

public class FunzioniUtilsUtente {
	private static DaoUtente daoUtente = new DaoUtente(); // Per richiamare eventualmente alcune funzioni del DAO

	public static boolean emailExists(String email) {
		String query = "SELECT COUNT(*) FROM utente WHERE email = ?;";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			return rs.getInt(1) > 0; // Seleziona la prima colonna(il contenuto del count) se quest'ultimo ha un
										// valore superiore a 0 voul dire che esiste
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean nicknameExists(String nickname) {
		String query = "SELECT COUNT(*) FROM utente WHERE nickname = ?;";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setString(1, nickname);
			ResultSet rs = stmt.executeQuery();
			return rs.getInt(1) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<Utente> accorpaUtentiDaRicerca(List<Utente> utentiNomiSimili, List<Utente> utentiCognomiSimili,
			List<Utente> utentiNicknameSimili) {
		List<Utente> utentiAccorpati = new ArrayList<Utente>();
		List<String> nicknames = new ArrayList<String>();

		// Aggiungo tutti gli utenti con nomi simili
		for (Utente u : utentiNomiSimili) {
			if (!nicknames.contains(u.getNickname())) {
				nicknames.add(u.getNickname());
				utentiAccorpati.add(u);
			}
		}

		// Aggiungo tutti gli utenti con cognomi simili ma escludendo quelli con
		// nickname uguale
		for (Utente u : utentiCognomiSimili) {
			if (!nicknames.contains(u.getNickname())) {
				nicknames.add(u.getNickname());
				utentiAccorpati.add(u);
			}
		}

		// Aggiungo tutti gli utenti con cognomi simili ma escludendo quelli con
		// nickname uguale
		for (Utente u : utentiNicknameSimili) {
			if (!nicknames.contains(u.getNickname())) {
				nicknames.add(u.getNickname());
				utentiAccorpati.add(u);
			}
		}

		return utentiAccorpati;

	}

	// Da riga 168 a riga 234 sono funzioni per ricercare un nickname
	/////////////////// Calcola la distanza di Levenshtein tra due
	// stringhe///////////////////
	public static int levenshtein(String s1, String s2) {
		int[][] dp = new int[s1.length() + 1][s2.length() + 1];

		for (int i = 0; i <= s1.length(); i++) {
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else {
					dp[i][j] = Math.min(dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1),
							Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
				}
			}
		}

		return dp[s1.length()][s2.length()];
	}

	// Calcola la similarità tra due stringhe in percentuale (da 0.0 a 1.0)
	public static double similarita(String s1, String s2) {
		int maxLen = Math.max(s1.length(), s2.length());
		if (maxLen == 0)
			return 1.0;
		return 1.0 - ((double) levenshtein(s1.toLowerCase(), s2.toLowerCase()) / maxLen);
	}

	// Cerca i Nomi simili nella lista
	public static List<Utente> trovaNomiSimili(String input, List<Utente> listaUtenti, double soglia) {
		List<Utente> risultati = new ArrayList<>();
		for (Utente utente : listaUtenti) {
			double sim = similarita(input, utente.getNome());
			if (sim >= soglia) {
				risultati.add(utente);
			}
		}
		return risultati;
	}

	// Cerca i Cognomi simili nella lista
	public static List<Utente> trovaCognomiSimili(String input, List<Utente> listaUtenti, double soglia) {
		List<Utente> risultati = new ArrayList<>();
		for (Utente utente : listaUtenti) {
			double sim = similarita(input, utente.getCognome());
			if (sim >= soglia) {
				risultati.add(utente);
			}
		}
		return risultati;
	}

	// Cerca i nickname simili nella lista
	public static List<Utente> trovaNicknameSimili(String input, List<Utente> listaUtenti, double soglia) {
		List<Utente> risultati = new ArrayList<>();
		for (Utente utente : listaUtenti) {
			double sim = similarita(input, utente.getNickname());
			if (sim >= soglia) {
				risultati.add(utente);
			}
		}
		return risultati;
	}
	
	//Funzione principale per cercare utenti che si appoggia ad altre funzioni
	public static List<Utente> cercaUtentiSimili(String input) {
		List<Utente> utentiNomiSimili = FunzioniUtilsUtente.trovaNomiSimili(input, daoUtente.selezionaTutti(), 0.65); // ricerca
																														// per
																														// nome
		List<Utente> utentiCognomiSimili = FunzioniUtilsUtente.trovaCognomiSimili(input, daoUtente.selezionaTutti(),
				0.65); // ricerca per cognome con tolleranza del 65%
		List<Utente> utentiNicknamesSimili = FunzioniUtilsUtente.trovaNicknameSimili(input, daoUtente.selezionaTutti(),
				0.65); // ricerca per cognome

		List<Utente> utentiAccorpati = FunzioniUtilsUtente.accorpaUtentiDaRicerca(utentiNomiSimili, utentiCognomiSimili,
				utentiNicknamesSimili);

		return utentiAccorpati;
	}

}
