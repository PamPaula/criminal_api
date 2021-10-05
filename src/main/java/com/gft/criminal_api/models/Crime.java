package com.gft.criminal_api.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Crime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date data;
	
	@NotEmpty
	@Size(min = 3, max = 100)
	private String descricao;
	
	@OneToOne
	private Criminoso criminoso;
	
	@OneToOne
	private Vitima vitima;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
}
