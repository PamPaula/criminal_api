package com.gft.criminal_api.controllers.dto;

import java.util.Optional;

import com.gft.criminal_api.models.Juiz;

public class JuizDTO {
	
	private String nome;
	private String email;
	private String telefone;
	
	public JuizDTO() {
		super();
	}

	public JuizDTO(String nome, String email, String telefone) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public static JuizDTO from(Juiz juiz) {
		return new JuizDTO(juiz.getNome(), juiz.getEmail(), juiz.getTelefone());
	}
	
	public static JuizDTO from(Optional<Juiz> juiz) {
		return new JuizDTO(juiz.get().getNome(), juiz.get().getEmail(), juiz.get().getTelefone());
	}

}
