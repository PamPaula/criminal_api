package com.gft.criminal_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.criminal_api.models.Legista;

@Repository
public interface LegistaRepository extends JpaRepository<Legista, Long> {

}
