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

import com.gft.criminal_api.controllers.dto.CriminosoDTO;
import com.gft.criminal_api.events.RecursoCriadoEvent;
import com.gft.criminal_api.models.Criminoso;
import com.gft.criminal_api.repositories.CriminosoRepository;

@Service
public class CriminosoService {

	@Autowired
	private CriminosoRepository criminosoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//listar criminosos
	public ResponseEntity<List<CriminosoDTO>> listar() {
		List<CriminosoDTO> criminosoDTOList = new ArrayList<>();
		List<Criminoso> criminosoList = criminosoRepository.findAll();
		for(Criminoso criminoso : criminosoList) {
			CriminosoDTO criminosoDTO = CriminosoDTO.from(criminoso);
			criminosoDTOList.add(criminosoDTO);
		}
		return !criminosoDTOList.isEmpty() ? ResponseEntity.ok(criminosoDTOList) : ResponseEntity.noContent().build();
	}
	
	//salvar criminosos
	public ResponseEntity<CriminosoDTO> salvar(Criminoso criminoso, HttpServletResponse response) {
		Criminoso criminosoSalvo = criminosoRepository.save(criminoso);
		CriminosoDTO criminosoDTO = CriminosoDTO.from(criminosoSalvo);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, criminosoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(criminosoDTO);
	}
	
	//buscar criminosos por id
	public Criminoso buscarCriminosoPeloId(Long id) {
		Optional<Criminoso> criminosoSalvo = criminosoRepository.findById(id);
		if(criminosoSalvo.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return criminosoSalvo.get();
	}
		
	//editar criminosos
	public Criminoso editar(Long id, Criminoso criminoso) {
		Optional<Criminoso> criminosoSalvo = criminosoRepository.findById(id);
        BeanUtils.copyProperties(criminoso, criminosoSalvo, "id");
        if(criminosoSalvo.isEmpty() ) {
            throw new EmptyResultDataAccessException(1);
        }
        return criminosoRepository.save(criminoso);
	}
}
