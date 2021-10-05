package com.gft.criminal_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.criminal_api.models.Autopsia;

@Repository
public interface AutopsiaRepository extends JpaRepository<Autopsia, Long> {

}
