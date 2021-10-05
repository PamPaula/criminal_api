package com.gft.criminal_api.repositories.policial;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.gft.criminal_api.filters.FiltroPolicial;
import com.gft.criminal_api.models.Policial;

public class PolicialRepositoryImpl implements PolicialRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Policial> filtrarPolicialNome(FiltroPolicial filtroPolicial) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Policial> criteria = builder.createQuery(Policial.class);
		Root<Policial> root = criteria.from(Policial.class);
		Predicate[] predicates = criarRestricoes(filtroPolicial, builder, root);
		criteria.where(predicates);
		TypedQuery<Policial> query = manager.createQuery(criteria);
		return query.getResultList();
	}

	private Predicate[] criarRestricoes(FiltroPolicial filtroPolicial, CriteriaBuilder builder, Root<Policial> root) {
		List<Predicate> predicates = new ArrayList<>();
		if(filtroPolicial.getNome() != null) {
			predicates.add(builder.like(
					builder.lower(root.get("nome")), "%" + filtroPolicial.getNome().toLowerCase() + "%"));			
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
