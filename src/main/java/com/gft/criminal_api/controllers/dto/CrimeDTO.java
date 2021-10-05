package com.gft.criminal_api.controllers.dto;

import java.util.Date;
import java.util.Optional;

import com.gft.criminal_api.models.Crime;
import com.gft.criminal_api.models.Criminoso;
import com.gft.criminal_api.models.Vitima;

public class CrimeDTO {

	private Date data;
	private String descricao;
	private Criminoso criminoso;
	private Vitima vitima;
	
	public CrimeDTO() {

	}

	public CrimeDTO(Date data, String descricao, Criminoso criminoso, Vitima vitima) {
		this.data = data;
		this.descricao = descricao;
		this.criminoso = criminoso;
		this.vitima = vitima;
	}

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Criminoso getCriminoso() {
		return criminoso;
	}
	public void setCriminoso(Criminoso criminoso) {
		this.criminoso = criminoso;
	}
	public Vitima getVitima() {
		return vitima;
	}
	public void setVitima(Vitima vitima) {
		this.vitima = vitima;
	}

	public static CrimeDTO from(Crime crime) {
		return new CrimeDTO(crime.getData(), crime.getDescricao(), crime.getCriminoso()
				, crime.getVitima());
	}
	
	public static CrimeDTO from(Optional<Crime> crime) {
		return new CrimeDTO(crime.get().getData(), crime.get().getDescricao(), crime.get().getCriminoso()
				, crime.get().getVitima());
	}
}
