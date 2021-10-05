package com.gft.criminal_api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gft.criminal_api.controllers.dto.CrimeDTO;
import com.gft.criminal_api.events.RecursoCriadoEvent;
import com.gft.criminal_api.models.Crime;
import com.gft.criminal_api.repositories.CrimeRepository;

@Service
public class CrimeService {

	@Autowired
	private CrimeRepository crimeRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//listar crimes
	public ResponseEntity<List<CrimeDTO>> listar() {
		List<CrimeDTO> crimeDTOList = new ArrayList<>();
		List<Crime> crimeList = crimeRepository.findAll();
		for(Crime crime : crimeList) {
			CrimeDTO crimeDTO = CrimeDTO.from(crime);
			crimeDTOList.add(crimeDTO);
		}
		return !crimeDTOList.isEmpty() ? ResponseEntity.ok(crimeDTOList) : ResponseEntity.noContent().build();
	}
	
	//salvar crimes
	public ResponseEntity<CrimeDTO> salvar(Crime crime, HttpServletResponse response) {
		Crime crimeSalvo = crimeRepository.save(crime);
		CrimeDTO crimeDTO = CrimeDTO.from(crimeSalvo);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, crimeSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(crimeDTO);
	}
	
	//buscar crimes por id
	public Crime buscarCrimePeloId(Long id) {
		Optional<Crime> crimeSalvo = crimeRepository.findById(id);
		if(crimeSalvo.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return crimeSalvo.get();
	}

	//editar crimes
	public Crime editar(Long id, Crime crime) {
		Optional<Crime> crimeSalvo = crimeRepository.findById(id);
        BeanUtils.copyProperties(crime, crimeSalvo, "id");
        if(crimeSalvo.isEmpty() ) {
            throw new EmptyResultDataAccessException(1);
        }
        return crimeRepository.save(crime);
	}
}
