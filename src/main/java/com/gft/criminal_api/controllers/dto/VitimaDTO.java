package com.gft.criminal_api.controllers.dto;

import java.util.Optional;

import com.gft.criminal_api.models.Vitima;

public class VitimaDTO {

	private String nome;
	private String cpf;
	
	public VitimaDTO() {

	}

	public VitimaDTO(String nome, String cpf) {
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

	public static VitimaDTO from(Vitima vitima) {
		return new VitimaDTO(vitima.getNome(), vitima.getCpf());
	}
	
	public static VitimaDTO from(Optional<Vitima> vitima) {
		return new VitimaDTO(vitima.get().getNome(), vitima.get().getCpf());
	}
}
