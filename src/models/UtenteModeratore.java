package models;

import java.util.Scanner;

public class UtenteModeratore extends Utente {
	
	private Scanner input = new Scanner(System.in);
	
	
	
	public UtenteModeratore(String ruolo, String nome, String cognome, String nickname, String email, String password,
			Boolean status) {
		super(ruolo, nome, cognome, nickname, email, password, status);
	}

	private Boolean bloccaUtente(Utente u) {
		u.setStatus(false);
		if(u.getStatus() == false) {
			return true;
		}
		return false;
	}
	
	private Boolean sbloccaUtente(Utente u) {
		u.setStatus(true);
		if(u.getStatus()) {
			return true;
		}
		return false;
	}
	
	private Boolean modificaUtetente(Utente u) {
		Integer scelta = null;
		do {
			System.out.println("Che cosa vuoi modificare?\n1. Profilo dell'utente" + "\n2. Post dell'utente");
			try {
				scelta = Integer.parseInt(input.nextLine());
				if(scelta < 1 || scelta > 2) {
					System.out.println("Valore non valido, riprova.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Valore non valido, riprova.");
			}
		} while (scelta < 1 || scelta > 2);

		switch(scelta) {
		case "1":
			modificaProfiloUtente(u);
			break;
		case "2":
			break;
		default:
			break;
		}
	}

	private void modificaProfiloUtente(Utente u) {
		// TODO Auto-generated method stub
		
	}
	
}
