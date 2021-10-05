package com.gft.criminal_api.controllers.dto;

import java.util.Optional;

import com.gft.criminal_api.models.Policial;

public class PolicialDTO {

	private String nome;
	private String funcional;
	private String patente;
	
	public PolicialDTO() {
		
	}

	public PolicialDTO(String nome, String funcional, String patente) {
		this.nome = nome;
		this.funcional = funcional;
		this.patente = patente;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFuncional() {
		return funcional;
	}
	public void setFuncional(String funcional) {
		this.funcional = funcional;
	}
	public String getPatente() {
		return patente;
	}
	public void setPatente(String patente) {
		this.patente = patente;
	}

	public static PolicialDTO from(Policial policial) {
		return new PolicialDTO(policial.getNome(), policial.getFuncional(), policial.getPatente());
	}
	
	public static PolicialDTO from(Optional<Policial> policial) {
		return new PolicialDTO(policial.get().getNome(), policial.get().getFuncional()
				, policial.get().getPatente());
	}

}
