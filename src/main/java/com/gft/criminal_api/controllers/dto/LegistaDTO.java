package com.gft.criminal_api.controllers.dto;

import java.util.Optional;

import com.gft.criminal_api.models.Legista;

public class LegistaDTO {
	
	private String nome;
	private String crm;

	public LegistaDTO() {
		
	}

	public LegistaDTO(String nome, String crm) {
		this.nome = nome;
		this.crm = crm;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}

	public static LegistaDTO from(Legista legista) {
		return new LegistaDTO(legista.getNome(), legista.getCrm());
	}
	
	public static LegistaDTO from(Optional<Legista> legista) {
		return new LegistaDTO(legista.get().getNome(), legista.get().getCrm());
	}
}
