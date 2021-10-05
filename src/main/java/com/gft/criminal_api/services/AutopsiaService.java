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

import com.gft.criminal_api.controllers.dto.AutopsiaDTO;
import com.gft.criminal_api.events.RecursoCriadoEvent;
import com.gft.criminal_api.models.Autopsia;
import com.gft.criminal_api.repositories.AutopsiaRepository;

@Service
public class AutopsiaService {

	@Autowired
	private AutopsiaRepository autopsiaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//listar autopsias
	public ResponseEntity<List<AutopsiaDTO>> listar() {
		List<AutopsiaDTO> autopsiaDTOList = new ArrayList<>();
		List<Autopsia> autopsiaList = autopsiaRepository.findAll();
		for(Autopsia autopsia : autopsiaList) {
			AutopsiaDTO autopsiaDTO = AutopsiaDTO.from(autopsia);
			autopsiaDTOList.add(autopsiaDTO);
		}
		return !autopsiaDTOList.isEmpty() ? ResponseEntity.ok(autopsiaDTOList) : ResponseEntity.noContent().build();
	}
	
	//salvar autopsias
	public ResponseEntity<AutopsiaDTO> salvar(Autopsia autopsia, HttpServletResponse response) {
		Autopsia autopsiaSalva = autopsiaRepository.save(autopsia);
		AutopsiaDTO autopsiaDTO = AutopsiaDTO.from(autopsiaSalva);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, autopsiaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(autopsiaDTO);
	}
	
	//buscar autopsias por id
	public Autopsia buscarAutopsiaPeloId(Long id) {
		Optional<Autopsia> autopsiaSalva = autopsiaRepository.findById(id);
		if(autopsiaSalva.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return autopsiaSalva.get();
	}
	
	//editar autopsias
	public Autopsia editar(Long id, Autopsia autopsia) {
		Optional<Autopsia> autopsiaSalva = autopsiaRepository.findById(id);
        BeanUtils.copyProperties(autopsia, autopsiaSalva, "id");
        if(autopsiaSalva.isEmpty() ) {
            throw new EmptyResultDataAccessException(1);
        }
        return autopsiaRepository.save(autopsia);
	}
}
