package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dao.DaoPost;
import dao.DaoUtente;
import models.Commento;
import models.Post;
import models.Valutazione;

public class FunzioniUtilsPost {

	private static DaoUtente daoUtente = new DaoUtente(); // Per richiamare eventualmente alcune funzioni del DAO
	private static DaoPost daoPost = new DaoPost();

	public static List<String> recuperaIngredienti(Integer idPost) {
		List<String> ingredienti = new ArrayList<String>();
		String query = "SELECT i.nome " + "FROM ingrediente i "
				+ "JOIN post_ingrediente pi ON i.id_ingrediente = pi.id_ingrediente " + "WHERE i.id_post = ?;";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query);) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ingredienti.add(rs.getString("nome"));
			}
			return ingredienti;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Valutazione> recuperaValutazioni(Integer idPost) {
		List<Valutazione> valutazioni = new ArrayList<Valutazione>();
		String query = "SELECT * " + "FROM valutazione v " + "JOIN post p ON v.id_post = p.id_post "
				+ "WHERE v.id_post = ?;";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setInt(1, idPost);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				valutazioni.add(new Valutazione(daoUtente.cercaPerNicknameOrEmailOrId(rs.getString("id_utente")),
						rs.getInt("voto")));
			}
			return valutazioni;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Integer recuperaValutazioneMedia(Integer idPost) {
		List<Valutazione> valutazioni = new ArrayList<Valutazione>();
		Integer sommaValutazioni = 0;
		String query = "SELECT * " + "FROM valutazione v " + "JOIN post p ON v.id_post = p.id_post "
				+ "WHERE v.id_post = ?;";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setInt(1, idPost);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Valutazione val = new Valutazione(daoUtente.cercaPerNicknameOrEmailOrId(rs.getString("id_utente")),
						rs.getInt("voto"));
				sommaValutazioni += val.getValutazione();
				valutazioni.add(val);
			}

			return sommaValutazioni / valutazioni.size();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Commento> recuperaCommenti(Integer idPost) {
		List<Commento> commenti = new ArrayList<Commento>();
		String query = "SELECT * " + "FROM commento c " + "JOIN post p ON c.id_post = p.id_post "
				+ "WHERE c.id_post = ?;";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setInt(1, idPost);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				commenti.add(new Commento(daoUtente.cercaPerNicknameOrEmailOrId(rs.getString("id_utente")),
						rs.getString("commento")));
			}
			return commenti;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Da riga 94 a riga 122 sono funzioni per somiglianza tra stringhe
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
	
	public static List<Post> trovaPostTitoliSimili(String input, List<Post> listaPost, double soglia) {
		List<Post> risultati = new ArrayList<>();
		for (Post post : listaPost) {
			double sim = similarita(input, post.getTitolo());
			if (sim >= soglia) {
				risultati.add(post);
			}
		}
		return risultati;
	}

	public static List<Post> trovaPostIngredientiSimili(List<String> ingredienti, List<Post> listaPost, double soglia) {
		List<Post> risultati = new ArrayList<>();
		for (Post post : listaPost) {

			if (ricettaSimile(post.getIngredienti(), ingredienti, soglia)) {
				risultati.add(post);
			}
		}
		return risultati;
	}

	// Restituisce true se la ricetta contiene ingredienti simili a quelli cercati
    public static boolean ricettaSimile(List<String> ricetta, List<String> ricerca, double soglia) {
        if (ricetta == null || ricerca == null || ricetta.isEmpty() || ricerca.isEmpty()) {
            return false;
        }

        Set<String> ricettaSet = new HashSet<>();
        for (String ingrediente : ricetta) {
            ricettaSet.add(ingrediente.toLowerCase().trim());
        }

        Set<String> ricercaSet = new HashSet<>();
        for (String ingrediente : ricerca) {
            ricercaSet.add(ingrediente.toLowerCase().trim());
        }

        // Trova gli ingredienti della ricerca presenti nella ricetta
        Set<String> comuni = new HashSet<>(ricercaSet);
        comuni.retainAll(ricettaSet);

        // Caso speciale: se stai cercando un solo ingrediente
        if (ricercaSet.size() == 1) {
            return !comuni.isEmpty(); // basta che ci sia
        }

        // Calcolo della percentuale di ingredienti trovati rispetto a quelli cercati
        double percentuale = (double) comuni.size() / ricercaSet.size();

        return percentuale >= soglia;
    }

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static List<Post> trovaPostPerParolaChiaveInDescrizione(String parolaChiave) {
		List<Post> risultati = new ArrayList<>();
		for (Post post : daoPost.cercaTutti()) {
			
			if (contieneAvanzato(post.getDescrizione(), parolaChiave)) {
				risultati.add(post);
			}
		}
		return risultati;
	}
    
 // Rimuove accenti e normalizza il testo
    public static String normalizza(String input) {
        if (input == null) return "";
        // Rimuove accenti: à → a, è → e, etc.
        String normalizzata = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Rimuove i caratteri diacritici (accenti)
        normalizzata = normalizzata.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalizzata.toLowerCase();
    }

    // Controlla se 'sotto' è contenuta in 'testo', ignorando maiuscole, accenti, e accetta anche parole parziali
    public static boolean contieneAvanzato(String testo, String sotto) {
        if (testo == null || sotto == null || testo.isEmpty() || sotto.isEmpty()) {
            return false;
        }

        String testoNorm = normalizza(testo);
        String sottoNorm = normalizza(sotto);

        return testoNorm.contains(sottoNorm);
    }

}
