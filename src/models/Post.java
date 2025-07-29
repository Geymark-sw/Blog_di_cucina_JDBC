package models;

import java.util.ArrayList;
import java.util.List;

import dao.DaoPost;

public class Post {
	
	private Integer idPost;
	private Utente proprietario;
	private String titolo;
	private String descrizione;
	private List<String> ingredienti = new ArrayList<String>();
	private List<Valutazione> valutazioni = new ArrayList<Valutazione>();
	private int valutazioneMedia;
	private List<Commento> commenti = new ArrayList<Commento>();
	
	public Post() {
		
	}
	
	public Post(Utente proprietario, String titolo, String descrizione, List<String> ingredienti) {
		setIdPost();
		this.proprietario = proprietario;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.ingredienti = ingredienti;
	}
	
	
	
	

	public Post(Utente proprietario, String titolo, String descrizione, List<String> ingredienti,
			List<Valutazione> valutazioni, int valutazioneMedia, List<Commento> commenti) {
		super();
		setIdPost();
		this.proprietario = proprietario;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.ingredienti = ingredienti;
		this.valutazioni = valutazioni;
		this.valutazioneMedia = valutazioneMedia;
		this.commenti = commenti;
	}
	

	public Integer getIdPost() {
		return idPost;
	}

	public void setIdPost(Integer idPost) {
		this.idPost = idPost;
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

	public List<Valutazione> getValutazioni() {
		return valutazioni;
	}

	public void setValutazioni(List<Valutazione> valutazioni) {
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
	
	public void setIdPost() {
		DaoPost dao = new DaoPost();
		//settare id_ultimo post + 1
		//poi puoi aggiungere su db
		this.idPost = dao.ritornaUltimoAggiunto().getIdPost() +  1;
	}

	@Override
	public String toString() {
		return "Post [proprietario=" + proprietario + ", titolo=" + titolo + ", descrizione=" + descrizione
				+ ", ingredienti=" + ingredienti + ", valutazioni=" + valutazioni + ", valutazioneMedia="
				+ valutazioneMedia + ", commenti=" + commenti + "]";
	}
	
	
	
}
