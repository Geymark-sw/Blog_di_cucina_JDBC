package models;

import java.util.ArrayList;
import java.util.List;

import dao.DaoUtente;
import utils.Ruolo;

public class Utente {
	
	protected long idUtente;
	protected Ruolo ruolo;
	protected String nome;
	protected String cognome;
	protected String nickname;
	protected String email;
	protected String password;
	protected Boolean status;
	protected List<Utente> followers = new ArrayList<Utente>();
	protected List<Utente> seguiti = new ArrayList<Utente>();
	protected List<Post> posts = new ArrayList<Post>();
	
	
	public Utente(String ruolo, String nome, String cognome, String nickname, String email, String password, Boolean status) {
		super();
		setRuolo(ruolo);
		setIdUtente();
		this.nome = nome;
		this.cognome = cognome;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.status = status;
	}
	
	public Utente() {
		
	}
	
	public Utente(long idUtente, String ruolo, String nome, String cognome, String nickname, String email, String password, Boolean status) {
		super();
		this.idUtente = idUtente;
		setRuolo(ruolo);
		this.nome = nome;
		this.cognome = cognome;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.status = status;
	}
	
	
	
	public Utente(long idUtente, Ruolo ruolo, String nome, String cognome, String nickname, String email,
			String password, Boolean status, List<Utente> followers, List<Utente> seguiti, List<Post> posts) {
		super();
		this.idUtente = idUtente;
		this.ruolo = ruolo;
		this.nome = nome;
		this.cognome = cognome;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.status = status;
		this.followers = followers;
		this.seguiti = seguiti;
		this.posts = posts;
	}

	public long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(long id) {
		this.idUtente = id;
	}
	
	public void setIdUtente() {
		DaoUtente dao = new DaoUtente();
		//settare id_ultimo utente + 1
		//poi puoi aggiungere su db
		this.idUtente = dao.ritornaUltimoAggiunto().getIdUtente() +  1;
	}
	

	public Ruolo getRuolo() {
		return ruolo;
	}
	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}
	public void setRuolo(String ruolo) {
		switch(ruolo.toLowerCase()) {
		case "admin":
			this.ruolo = Ruolo.ADMIN;
			break;
		case "moderatore":
			this.ruolo = Ruolo.MODERATORE;
			break;
		case "normale":
			this.ruolo = Ruolo.NORMALE;
		}
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<Utente> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Utente> followers) {
		this.followers = followers;
	}

	public List<Utente> getSeguiti() {
		return seguiti;
	}

	public void setSeguiti(List<Utente> seguiti) {
		this.seguiti = seguiti;
	}
	
	

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "Utente [ruolo=" + ruolo + ", nome=" + nome + ", cognome=" + cognome + ", nickname=" + nickname
				+ ", email=" + email + ", password=" + password + "]";
	}
	
	

}
