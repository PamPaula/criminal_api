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

import com.gft.criminal_api.controllers.dto.DelegaciaDTO;
import com.gft.criminal_api.events.RecursoCriadoEvent;
import com.gft.criminal_api.models.Delegacia;
import com.gft.criminal_api.repositories.DelegaciaRepository;

@Service
public class DelegaciaService {

	@Autowired
	private DelegaciaRepository delegaciaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//listar delegacias
	public ResponseEntity<List<DelegaciaDTO>> listar() {
		List<DelegaciaDTO> delegaciaDTOList = new ArrayList<>();
		List<Delegacia> delegaciaList = delegaciaRepository.findAll();
		for(Delegacia delegacia : delegaciaList) {
			DelegaciaDTO delegaciaDTO = DelegaciaDTO.from(delegacia);
			delegaciaDTOList.add(delegaciaDTO);
		}
		return !delegaciaDTOList.isEmpty() ? ResponseEntity.ok(delegaciaDTOList) : ResponseEntity.noContent().build();
	}

	//salvar delegacias
	public ResponseEntity<DelegaciaDTO> salvar(Delegacia delegacia, HttpServletResponse response) {
		Delegacia delegaciaSalva = delegaciaRepository.save(delegacia);
		DelegaciaDTO delegaciaDTO = DelegaciaDTO.from(delegaciaSalva);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, delegaciaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(delegaciaDTO);
	}
	
	//buscar delegacias por id
	public Delegacia buscarDelegaciaPeloId(Long id) {
		Optional<Delegacia> delegaciaSalva = delegaciaRepository.findById(id);
		if(delegaciaSalva.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return delegaciaSalva.get();
	}

	//editar delegacias
	public Delegacia editar(Long id, Delegacia delegacia) {
		Optional<Delegacia> delegaciaSalva = delegaciaRepository.findById(id);
        BeanUtils.copyProperties(delegacia, delegaciaSalva, "id");
        if(delegaciaSalva.isEmpty() ) {
            throw new EmptyResultDataAccessException(1);
        }
        return delegaciaRepository.save(delegacia);
	}
}
 