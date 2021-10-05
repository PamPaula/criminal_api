package com.gft.criminal_api.controllers.dto;

import java.util.Optional;

import com.gft.criminal_api.models.Delegacia;

public class DelegaciaDTO {

	private String telefone;
	private String batalhao;
	
	public DelegaciaDTO() {

	}

	public DelegaciaDTO(String telefone, String batalhao) {
		this.telefone = telefone;
		this.batalhao = batalhao;
	}
	
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getBatalhao() {
		return batalhao;
	}
	public void setBatalhao(String batalhao) {
		this.batalhao = batalhao;
	}
	
	public static DelegaciaDTO from(Delegacia delegacia) {
		return new DelegaciaDTO(delegacia.getTelefone(), delegacia.getBatalhao());
	}
	
	public static DelegaciaDTO from(Optional<Delegacia> delegacia) {
		return new DelegaciaDTO(delegacia.get().getTelefone(), delegacia.get().getBatalhao());
	}

}
