package com.gft.criminal_api.controllers.dto;

import java.util.Optional;

import com.gft.criminal_api.models.Criminoso;

public class CriminosoDTO {
	
	private String nome;
	private String cpf;

	public CriminosoDTO() {

	}
	
	public CriminosoDTO(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public static CriminosoDTO from(Criminoso criminoso) {
		return new CriminosoDTO(criminoso.getNome(), criminoso.getCpf());
	}
	
	public static CriminosoDTO from(Optional<Criminoso> criminoso) {
		return new CriminosoDTO(criminoso.get().getNome(), criminoso.get().getCpf());
	}
}
