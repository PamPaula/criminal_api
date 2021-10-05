package com.gft.criminal_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.criminal_api.models.Policial;
import com.gft.criminal_api.repositories.policial.PolicialRepositoryQuery;

@Repository
public interface PolicialRepository extends JpaRepository<Policial, Long>, PolicialRepositoryQuery {

}
