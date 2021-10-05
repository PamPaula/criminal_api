package com.gft.criminal_api.controllers.dto;

import java.util.Date;
import java.util.Optional;

import com.gft.criminal_api.models.Autopsia;
import com.gft.criminal_api.models.Legista;
import com.gft.criminal_api.models.Vitima;

public class AutopsiaDTO {

	private Date data;
	private String laudo;
	private Vitima vitima;
	private Legista legista;
	
	public AutopsiaDTO() {

	}

	public AutopsiaDTO(Date data, String laudo, Vitima vitima, Legista legista) {
		this.data = data;
		this.laudo = laudo;
		this.vitima = vitima;
		this.legista = legista;
	}

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getLaudo() {
		return laudo;
	}
	public void setLaudo(String laudo) {
		this.laudo = laudo;
	}
	public Vitima getVitima() {
		return vitima;
	}
	public void setVitima(Vitima vitima) {
		this.vitima = vitima;
	}
	public Legista getLegista() {
		return legista;
	}
	public void setLegista(Legista legista) {
		this.legista = legista;
	}

	public static AutopsiaDTO from(Autopsia autopsia) {
		return new AutopsiaDTO(autopsia.getData(), autopsia.getLaudo(), autopsia.getVitima()
				, autopsia.getLegista());
	}
	
	public static AutopsiaDTO from(Optional<Autopsia> autopsia) {
		return new AutopsiaDTO(autopsia.get().getData(), autopsia.get().getLaudo(), autopsia.get().getVitima()
				, autopsia.get().getLegista());
	}
}
