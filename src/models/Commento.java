package models;

public class Commento {
	
	private Utente proprietario;
	private String commento;
	
	public Commento(Utente proprietario, String commento) {
		super();
		this.proprietario = proprietario;
		this.commento = commento;
	}

	public Utente getProprietario() {
		return proprietario;
	}

	public void setProprietario(Utente proprietario) {
		this.proprietario = proprietario;
	}

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}
	
	

}
