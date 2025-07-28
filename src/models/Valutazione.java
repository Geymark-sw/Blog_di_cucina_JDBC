package models;

public class Valutazione {
	
	private Utente proprietario;
	private int valutazione;
	
	private Valutazione(Utente proprietario, int valutazione) {
		this.proprietario = proprietario;
		this.valutazione = valutazione;
	}
	
	public Utente getProprietario() {
		return this.proprietario;
	}
	
	public void setProprietario(Utente proprietario) {
		this.proprietario = proprietario;
	}
	
	public int getValutazione() {
		return this.valutazione;
	}
	
	public void setValutazione(int valutazione) {
		this.valutazione = valutazione;
	}

}
