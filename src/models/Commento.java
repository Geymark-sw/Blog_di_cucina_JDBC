package models;

import dao.DaoCommento;

public class Commento {
	
	private Long idCommento;
	private Utente proprietario;
	private String commento;
	
	public Commento(Utente proprietario, String commento) {
		super();
		setIdCommento();
		this.proprietario = proprietario;
		this.commento = commento;
	}
	
	

	public Commento(Long idCommento, Utente proprietario, String commento) {
		super();
		this.idCommento = idCommento;
		this.proprietario = proprietario;
		this.commento = commento;
	}



	public Long getIdCommento() {
		return idCommento;
	}



	public void setIdCommento(Long idCommento) {
		this.idCommento = idCommento;
	}
	
	public void setIdCommento() {
		DaoCommento daoCommento = new DaoCommento();
		
		this.idCommento = daoCommento.cercaUltimoAggiunto().getIdCommento() + 1;
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
