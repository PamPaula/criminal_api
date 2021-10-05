package com.gft.criminal_api.controllers.dto;

import java.util.Optional;

import com.gft.criminal_api.models.Delegacia;
import com.gft.criminal_api.models.Delegado;

public class DelegadoDTO {

	private String nome;
	private String funcional;
	private String turno;
	private Delegacia delegacia;
	
	public DelegadoDTO() {
		
	}

	public DelegadoDTO(String nome, String funcional, String turno, Delegacia delegacia) {
		this.nome = nome;
		this.funcional = funcional;
		this.turno = turno;
		this.delegacia = delegacia;
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
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public Delegacia getDelegacia() {
		return delegacia;
	}
	public void setDelegacia(Delegacia delegacia) {
		this.delegacia = delegacia;
	}

	public static DelegadoDTO from(Delegado delegado) {
		return new DelegadoDTO(delegado.getNome(), delegado.getFuncional(), delegado.getTurno()
				, delegado.getDelegacia());
	}
	
	public static DelegadoDTO from(Optional<Delegado> delegado) {
		return new DelegadoDTO(delegado.get().getNome(), delegado.get().getFuncional()
				, delegado.get().getTurno(), delegado.get().getDelegacia());
	}

}
