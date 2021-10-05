package com.gft.criminal_api.controllers.dto;

import java.util.Date;
import java.util.Optional;

import com.gft.criminal_api.models.Criminoso;
import com.gft.criminal_api.models.Delegacia;
import com.gft.criminal_api.models.Delegado;
import com.gft.criminal_api.models.Policial;
import com.gft.criminal_api.models.Prisao;

public class PrisaoDTO {
	
	private Date data;
	private Policial policial;
	private Criminoso criminoso;
	private Delegacia delegacia;
	private Delegado delegado;
	
	public PrisaoDTO() {

	}

	public PrisaoDTO(Date data, Policial policial, Criminoso criminoso, Delegacia delegacia, Delegado delegado) {
		this.data = data;
		this.policial = policial;
		this.criminoso = criminoso;
		this.delegacia = delegacia;
		this.delegado = delegado;
	}

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Policial getPolicial() {
		return policial;
	}
	public void setPolicial(Policial policial) {
		this.policial = policial;
	}
	public Criminoso getCriminoso() {
		return criminoso;
	}
	public void setCriminoso(Criminoso criminoso) {
		this.criminoso = criminoso;
	}
	public Delegacia getDelegacia() {
		return delegacia;
	}
	public void setDelegacia(Delegacia delegacia) {
		this.delegacia = delegacia;
	}
	public Delegado getDelegado() {
		return delegado;
	}
	public void setDelegado(Delegado delegado) {
		this.delegado = delegado;
	}
	
	public static PrisaoDTO from(Prisao prisao) {
		return new PrisaoDTO(prisao.getData(), prisao.getPolicial(), prisao.getCriminoso()
				, prisao.getDelegacia(), prisao.getDelegado());
	}
	
	public static PrisaoDTO from(Optional<Prisao> prisao) {
		return new PrisaoDTO(prisao.get().getData(), prisao.get().getPolicial(), prisao.get().getCriminoso()
				, prisao.get().getDelegacia(), prisao.get().getDelegado());
	}
}
