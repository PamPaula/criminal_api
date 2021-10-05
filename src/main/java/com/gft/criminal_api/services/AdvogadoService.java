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

import com.gft.criminal_api.controllers.dto.AdvogadoDTO;
import com.gft.criminal_api.events.RecursoCriadoEvent;
import com.gft.criminal_api.models.Advogado;
import com.gft.criminal_api.models.Usuario;
import com.gft.criminal_api.repositories.AdvogadoRepository;

@Service
public class AdvogadoService {

	@Autowired
	private AdvogadoRepository advogadoRepository;
	
	@Autowired
	private FabricaDeUsuarioService fabricaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//listar advogados
	public ResponseEntity<List<AdvogadoDTO>> listar() {
		List<AdvogadoDTO> advogadoDTOList = new ArrayList<>();
		List<Advogado> advogadoList = advogadoRepository.findAll();
		for(Advogado advogado : advogadoList) {
			AdvogadoDTO advogadoDTO = AdvogadoDTO.from(advogado);
			advogadoDTOList.add(advogadoDTO);
		}
		return !advogadoDTOList.isEmpty() ? ResponseEntity.ok(advogadoDTOList) : ResponseEntity.noContent().build();
	}
	
	//salvar advogados
	@Transactional
	public ResponseEntity<AdvogadoDTO> salvar(Advogado advogado, HttpServletResponse response) {
		Advogado advogadoSalvo = advogadoRepository.save(advogado);
		Usuario usuario = fabricaService.criarUsuario(advogadoSalvo);
		advogadoSalvo.setUsuario(usuario);
		advogadoRepository.save(advogado);
		AdvogadoDTO advogadoDTO = AdvogadoDTO.from(advogadoSalvo);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, advogadoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(advogadoDTO);
	}

	//buscar advogados por id
	public Advogado buscarAdvogadoPeloId(Long id) {
		Optional<Advogado> advogadoSalvo = advogadoRepository.findById(id);
		if(advogadoSalvo.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return advogadoSalvo.get();
	}
		
	//editar advogados
	public Advogado editar(Long id, Advogado advogado) {
		Optional<Advogado> advogadoSalvo = advogadoRepository.findById(id);
	    BeanUtils.copyProperties(advogado, advogadoSalvo, "id");
	    if(advogadoSalvo.isEmpty()) {
	    	throw new EmptyResultDataAccessException(1);
	    }
	    return advogadoRepository.save(advogado);
	}
}
