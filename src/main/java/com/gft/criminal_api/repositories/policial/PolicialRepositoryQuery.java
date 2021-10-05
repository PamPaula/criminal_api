package com.gft.criminal_api.repositories.policial;

import java.util.List;

import com.gft.criminal_api.filters.FiltroPolicial;
import com.gft.criminal_api.models.Policial;

public interface PolicialRepositoryQuery {
	
	public List<Policial> filtrarPolicialNome(FiltroPolicial filtroPolicial);
}
