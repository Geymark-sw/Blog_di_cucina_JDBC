package models;

import java.util.Scanner;

import utils.FunzioniUtilsUtente;

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
		case 1:
			return modificaProfiloUtente(u);
			
		case 2:
			return modificaPostUtente(u);
			break;
		default:
			return false;
		}
	}

	private void modificaPostUtente(Utente u) {
		// TODO Auto-generated method stub
		
	}

	private Boolean modificaProfiloUtente(Utente u) {
		FunzioniUtilsUtente.stampaAnagraficaUtente(u);
		Integer scelta = null;
		do {
			System.out.println("Che cosa vuoi modificare?\n1. Nome" 
													+ "\n2. Cognome"
													+ "\n3. Nickname"
													+ "\n4. Email"
													+ "\n5. Password"
													+ "\n6. Status");
			try {
				scelta = Integer.parseInt(input.nextLine());
				if(scelta < 1 || scelta > 6) {
					System.out.println("Valore non valido, riprova.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Valore non valido, riprova.");
			}
		} while (scelta < 1 || scelta > 6);
		
		switch(scelta) {
		case 1:
			System.out.println("Inserisci il nuovo nome:");
			String nome = input.nextLine();
			u.setNome(nome);
			System.out.println("Modifica effettuata con successo!");
			break;
		case 2:
			System.out.println("Inserisci il nuovo cognome:");
			String cognome = input.nextLine();
			u.setCognome(cognome);
			System.out.println("Modifica effettuata con successo!");
			break;
		case 3:
			System.out.println("Inserisci in nuovo nickname:");
			String nickname = input.nextLine();
			u.setNickname(nickname);
			System.out.println("Modifica effettuata con successo!");
		case 4:
			System.out.println("Inserisci la nuova email:");
			String email = input.nextLine();
			u.setEmail(email);
			System.out.println("Modifica Effettuata con successo!");
		case 5:
			System.out.println("Inserisci la nuova password:");
			String password = input.nextLine();
			u.setPassword(password);
			System.out.println("Modifica effettuata con successo!");
			break;
		default:
			return false;
		}
		return true;
		
	}
	
}
