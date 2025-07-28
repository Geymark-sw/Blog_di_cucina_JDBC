package models;

import java.util.List;

public class Post {
	
	private Utente proprietario;
	private String titolo;
	private String descrizione;
	private List<String> ingredienti;
	private List<Integer> valutazioni;
	private int valutazioneMedia;
	private List<Commento> commenti;
	
	public Post() {
		
	}
	
	public Post(Utente proprietario, String titolo, String descrizione, List<String> ingredienti) {
		this.proprietario = proprietario;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.ingredienti = ingredienti;
	}

	public Utente getProprietario() {
		return proprietario;
	}

	public void setProprietario(Utente proprietario) {
		this.proprietario = proprietario;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<String> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(List<String> ingredienti) {
		this.ingredienti = ingredienti;
	}

	public List<Integer> getValutazioni() {
		return valutazioni;
	}

	public void setValutazioni(List<Integer> valutazioni) {
		this.valutazioni = valutazioni;
	}

	public int getValutazioneMedia() {
		return valutazioneMedia;
	}

	public void setValutazioneMedia(int valutazioneMedia) {
		this.valutazioneMedia = valutazioneMedia;
	}

	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}

	@Override
	public String toString() {
		return "Post [proprietario=" + proprietario + ", titolo=" + titolo + ", descrizione=" + descrizione
				+ ", ingredienti=" + ingredienti + ", valutazioni=" + valutazioni + ", valutazioneMedia="
				+ valutazioneMedia + ", commenti=" + commenti + "]";
	}
	
	
	
}
