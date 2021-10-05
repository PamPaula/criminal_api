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

import com.gft.criminal_api.controllers.dto.LegistaDTO;
import com.gft.criminal_api.events.RecursoCriadoEvent;
import com.gft.criminal_api.models.Legista;
import com.gft.criminal_api.repositories.LegistaRepository;

@Service
public class LegistaService {

	@Autowired
	private LegistaRepository legistaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//listar legistas
	public ResponseEntity<List<LegistaDTO>> listar() {
		List<LegistaDTO> legistaDTOList = new ArrayList<>();
		List<Legista> legistaList = legistaRepository.findAll();
		for(Legista legista : legistaList) {
			LegistaDTO legistaDTO = LegistaDTO.from(legista);
			legistaDTOList.add(legistaDTO);
		}
		return !legistaDTOList.isEmpty() ? ResponseEntity.ok(legistaDTOList) : ResponseEntity.noContent().build();
	}
	
	//salvar legistas
	public ResponseEntity<LegistaDTO> salvar(Legista legista, HttpServletResponse response) {
		Legista legistaSalvo = legistaRepository.save(legista);
		LegistaDTO legistaDTO = LegistaDTO.from(legistaSalvo);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, legistaSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(legistaDTO);
	}

	//buscar legistas por id
	public Legista buscarLegistaPeloId(Long id) {
		Optional<Legista> legistaSalvo = legistaRepository.findById(id);
		if(legistaSalvo.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return legistaSalvo.get();
	}

	//editar legistas
	public Legista editar(Long id, Legista legista) {
		Optional<Legista> legistaSalvo = legistaRepository.findById(id);
        BeanUtils.copyProperties(legista, legistaSalvo, "id");
        if(legistaSalvo.isEmpty() ) {
            throw new EmptyResultDataAccessException(1);
        }
        return legistaRepository.save(legista);
	}
}
