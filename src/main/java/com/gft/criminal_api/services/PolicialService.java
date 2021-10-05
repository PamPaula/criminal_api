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

import com.gft.criminal_api.controllers.dto.PolicialDTO;
import com.gft.criminal_api.events.RecursoCriadoEvent;
import com.gft.criminal_api.filters.FiltroPolicial;
import com.gft.criminal_api.models.Policial;
import com.gft.criminal_api.repositories.PolicialRepository;

@Service
public class PolicialService {

	@Autowired
	private PolicialRepository policialRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//listar policiais
	public ResponseEntity<List<PolicialDTO>> listar() {
		List<PolicialDTO> policialDTOList = new ArrayList<>();
		List<Policial> policialList = policialRepository.findAll();
		for(Policial policial : policialList) {
			PolicialDTO policialDTO = PolicialDTO.from(policial);
			policialDTOList.add(policialDTO);
		}
		return !policialDTOList.isEmpty() ? ResponseEntity.ok(policialDTOList) : ResponseEntity.noContent().build();
	}
	
	//salvar policiais
	public ResponseEntity<PolicialDTO> salvar(Policial policial, HttpServletResponse response) {
		Policial policialSalvo = policialRepository.save(policial);
		PolicialDTO policialDTO = PolicialDTO.from(policialSalvo);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, policialSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(policialDTO);
	}
	
	//buscar policiais por id
	public Policial buscarPolicialPeloId(Long id) {
		Optional<Policial> policialSalvo = policialRepository.findById(id);
		if(policialSalvo.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return policialSalvo.get();
	}

	//buscar policial por nome
	public List<PolicialDTO> buscarPolicialNome(FiltroPolicial filtroPolicial){
		List<PolicialDTO> policialDTOLista = new ArrayList<>();
		List<Policial> policialLista = policialRepository.filtrarPolicialNome(filtroPolicial);
		for(Policial policial : policialLista) {
			PolicialDTO policialDTO = PolicialDTO.from(policial);
			policialDTOLista.add(policialDTO);
		}
		return policialDTOLista;
	}

	//editar policiais
	public Policial editar(Long id, Policial policial) {
		Optional<Policial> policialSalvo = policialRepository.findById(id);
	    BeanUtils.copyProperties(policial, policialSalvo, "id");
	    if(policialSalvo.isEmpty() ) {
	    	throw new EmptyResultDataAccessException(1);
	    }
	    return policialRepository.save(policial);
	}
}
