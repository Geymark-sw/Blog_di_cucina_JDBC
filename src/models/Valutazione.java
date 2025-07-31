package models;


import dao.DaoValutazione;

public class Valutazione {
	
	private Long idValutazione;
	private Utente proprietario;
	private int valutazione;
	
	//Costruttore senza id auto assegnato interagendo col DB
	public Valutazione(Utente proprietario, int valutazione) {
		setIdValutazione();
		this.proprietario = proprietario;
		this.valutazione = valutazione;
	}
	
	
	
	//Costruttore con id che potrebbe servire quando si ricerca sul DB per ricostruire l'oggetto
	public Valutazione(Long idValutazione, Utente proprietario, int valutazione) {
		super();
		this.idValutazione = idValutazione;
		this.proprietario = proprietario;
		this.valutazione = valutazione;
	}





	public Long getIdValutazione() {
		return idValutazione;
	}



	public void setIdValutazione(Long idValutazione) {
		this.idValutazione = idValutazione;
	}
	
	public void setIdValutazione() {
		DaoValutazione dao = new DaoValutazione();
		//settare id_ultimo utente + 1
		//poi puoi aggiungere su db
		this.idValutazione = dao.ritornaUltimoAggiunto().getIdValutazione() +  1;
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
