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

import com.gft.criminal_api.controllers.dto.VitimaDTO;
import com.gft.criminal_api.events.RecursoCriadoEvent;
import com.gft.criminal_api.models.Vitima;
import com.gft.criminal_api.repositories.VitimaRepository;

@Service
public class VitimaService {

	@Autowired
	private VitimaRepository vitimaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//listar vitimas
	public ResponseEntity<List<VitimaDTO>> listar() {
		List<VitimaDTO> vitimaDTOList = new ArrayList<>();
		List<Vitima> vitimaList = vitimaRepository.findAll();
		for(Vitima vitima : vitimaList) {
			VitimaDTO vitimaDTO = VitimaDTO.from(vitima);
			vitimaDTOList.add(vitimaDTO);
		}
		return !vitimaDTOList.isEmpty() ? ResponseEntity.ok(vitimaDTOList) : ResponseEntity.noContent().build();
	}
	
	//salvar vitimas
	public ResponseEntity<VitimaDTO> salvar(Vitima vitima, HttpServletResponse response) {
		Vitima vitimaSalva = vitimaRepository.save(vitima);
		VitimaDTO vitimaDTO = VitimaDTO.from(vitimaSalva);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, vitimaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(vitimaDTO);
	}
	
	//buscar vitimas por id
	public Vitima buscarVitimaPeloId(Long id) {
		Optional<Vitima> vitimaSalva = vitimaRepository.findById(id);
		if(vitimaSalva.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return vitimaSalva.get();
	}	
		
	//editar vitimas
	public Vitima editar(Long id, Vitima vitima) {
		Optional<Vitima> vitimaSalva = vitimaRepository.findById(id);
        BeanUtils.copyProperties(vitima, vitimaSalva, "id");
        if(vitimaSalva.isEmpty() ) {
            throw new EmptyResultDataAccessException(1);
        }
        return vitimaRepository.save(vitima);
	}
}
