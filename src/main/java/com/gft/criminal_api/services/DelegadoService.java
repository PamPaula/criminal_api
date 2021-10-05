package com.gft.criminal_api.services;

import java.util.ArrayList;
import java.util.Comparator;
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

import com.gft.criminal_api.controllers.dto.DelegadoDTO;
import com.gft.criminal_api.events.RecursoCriadoEvent;
import com.gft.criminal_api.models.Delegado;
import com.gft.criminal_api.models.Usuario;
import com.gft.criminal_api.repositories.DelegadoRepository;

@Service
public class DelegadoService {

	@Autowired
	private DelegadoRepository delegadoRepository;
	
	@Autowired
	private FabricaDeUsuarioService fabricaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//listar delegados
	public ResponseEntity<List<DelegadoDTO>> listar() {
		List<DelegadoDTO> delegadoDTOList = new ArrayList<>();
		List<Delegado> delegadoList = delegadoRepository.findAll();
		for(Delegado delegado : delegadoList) {
			DelegadoDTO delegadoDTO = DelegadoDTO.from(delegado);
			delegadoDTOList.add(delegadoDTO);
		}
		return !delegadoDTOList.isEmpty() ? ResponseEntity.ok(delegadoDTOList) : ResponseEntity.noContent().build();
	}
	
	//listar delegados em ordem alfabética crescente
	public List<DelegadoDTO> listarDelegadoAsc(){
		List<DelegadoDTO> delegadoDTOLista = new ArrayList<>();
		List<Delegado> delegadoAscLista = delegadoRepository.findAll();
		delegadoAscLista.sort(Comparator.comparing(Delegado :: getNome));
		for(Delegado delegado : delegadoAscLista) {
			DelegadoDTO delegadoDTO = DelegadoDTO.from(delegado);
			delegadoDTOLista.add(delegadoDTO);
		}
		return delegadoDTOLista;
	}

	//listar delegados em ordem alfabética decrescente
	public List<DelegadoDTO> listarDelegadoDesc(){
		List<DelegadoDTO> delegadoDTOLista = new ArrayList<>();
		List<Delegado> delegadoDescLista = delegadoRepository.findAll();
		delegadoDescLista.sort(Comparator.comparing(Delegado :: getNome).reversed());
		for(Delegado delegado : delegadoDescLista) {
			DelegadoDTO delegadoDTO = DelegadoDTO.from(delegado);
			delegadoDTOLista.add(delegadoDTO);
		}
		return delegadoDTOLista;
	}

	//salvar delegados
	@Transactional
	public ResponseEntity<DelegadoDTO> salvar(Delegado delegado, HttpServletResponse response) {
		Delegado delegadoSalvo = delegadoRepository.save(delegado);
		Usuario usuario = fabricaService.criarUsuario(delegadoSalvo);
		delegadoSalvo.setUsuario(usuario);
		delegadoRepository.save(delegado);
		DelegadoDTO delegadoDTO = DelegadoDTO.from(delegadoSalvo);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, delegadoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(delegadoDTO);
	}

	//buscar delegados por id
	public Delegado buscarDelegadoPeloId(Long id) {
		Optional<Delegado> delegadoSalvo = delegadoRepository.findById(id);
		if(delegadoSalvo.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return delegadoSalvo.get();
	}
		
	//editar delegados
	public Delegado editar(Long id, Delegado delegado) {
		Optional<Delegado> delegadoSalvo = delegadoRepository.findById(id);
	    BeanUtils.copyProperties(delegado, delegadoSalvo, "id");
	    if(delegadoSalvo.isEmpty() ) {
	    	throw new EmptyResultDataAccessException(1);
	    }
	    return delegadoRepository.save(delegado);
	}
}
