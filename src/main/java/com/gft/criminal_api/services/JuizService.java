package com.gft.criminal_api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gft.criminal_api.controllers.dto.JuizDTO;
import com.gft.criminal_api.events.RecursoCriadoEvent;
import com.gft.criminal_api.models.Juiz;
import com.gft.criminal_api.models.Usuario;
import com.gft.criminal_api.repositories.JuizRepository;

@Service
public class JuizService {
	
	@Autowired
	private JuizRepository juizRepository;
	
	@Autowired
	private FabricaDeUsuarioService fabricaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//listar juizes
	public ResponseEntity<List<JuizDTO>> listar() {
		List<JuizDTO> juizDTOList = new ArrayList<>();
		List<Juiz> juizList = juizRepository.findAll();
		for(Juiz juiz : juizList) {
			JuizDTO juizDTO = JuizDTO.from(juiz);
			juizDTOList.add(juizDTO);
		}
		return !juizDTOList.isEmpty() ? ResponseEntity.ok(juizDTOList) : ResponseEntity.noContent().build();
	}
	
	//salvar juizes
	@Transactional
	public ResponseEntity<JuizDTO> salvar(Juiz juiz, HttpServletResponse response) {
		Juiz juizSalvo = juizRepository.save(juiz);
		Usuario usuario = fabricaService.criarUsuario(juizSalvo);
		juizSalvo.setUsuario(usuario);
		juizRepository.save(juiz);
		JuizDTO juizDTO = JuizDTO.from(juizSalvo);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, juizSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(juizDTO);
	}

	//buscar juizes por id
	public Juiz buscarJuizPeloId(Long id) {
		Optional<Juiz> juizSalvo = juizRepository.findById(id);
		if(juizSalvo.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return juizSalvo.get();
	}
		
	//editar juizes
	public Juiz editar(Long id, Juiz juiz) {
		Optional<Juiz> juizSalvo = juizRepository.findById(id);
	    BeanUtils.copyProperties(juiz, juizSalvo, "id");
	    if(juizSalvo.isEmpty()) {
	    	throw new EmptyResultDataAccessException(1);
	    }
	    return juizRepository.save(juiz);
	}
}
