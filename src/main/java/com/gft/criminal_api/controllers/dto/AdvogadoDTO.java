package com.gft.criminal_api.controllers.dto;

import java.util.Optional;

import com.gft.criminal_api.models.Advogado;

public class AdvogadoDTO {
	
	private String nome;
	private String email;
	private String telefone;
	
	public AdvogadoDTO() {
	
	}

	public AdvogadoDTO(String nome, String email, String telefone) {
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
	
	public static AdvogadoDTO from(Advogado advogado) {
		return new AdvogadoDTO(advogado.getNome(),advogado.getEmail(), advogado.getTelefone());
	}
	
	public static AdvogadoDTO from(Optional<Advogado> advogado) {
		return new AdvogadoDTO(advogado.get().getNome(), advogado.get().getEmail()
				, advogado.get().getTelefone());
	}

}
