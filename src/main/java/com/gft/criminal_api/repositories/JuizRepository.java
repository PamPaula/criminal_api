package com.gft.criminal_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.criminal_api.models.Juiz;

@Repository
public interface JuizRepository extends JpaRepository<Juiz, Long> {

}
