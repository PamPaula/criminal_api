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

import com.gft.criminal_api.controllers.dto.PrisaoDTO;
import com.gft.criminal_api.events.RecursoCriadoEvent;
import com.gft.criminal_api.models.Prisao;
import com.gft.criminal_api.repositories.PrisaoRepository;

@Service
public class PrisaoService {

	@Autowired
	private PrisaoRepository prisaoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//listar prisões
	public ResponseEntity<List<PrisaoDTO>> listar() {
		List<PrisaoDTO> prisaoDTOList = new ArrayList<>();
		List<Prisao> prisaoList = prisaoRepository.findAll();
		for(Prisao prisao : prisaoList) {
			PrisaoDTO prisaoDTO = PrisaoDTO.from(prisao);
			prisaoDTOList.add(prisaoDTO);
		}
		return !prisaoDTOList.isEmpty() ? ResponseEntity.ok(prisaoDTOList) : ResponseEntity.noContent().build();
	}
	
	//salvar prisões
	public ResponseEntity<PrisaoDTO> salvar(Prisao prisao, HttpServletResponse response) {
		Prisao prisaoSalva = prisaoRepository.save(prisao);
		PrisaoDTO prisaoDTO = PrisaoDTO.from(prisaoSalva);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, prisaoSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(prisaoDTO);
	}
		
	//buscar prisões por id
	public Prisao buscarPrisaoPeloId(Long id) {
		Optional<Prisao> prisaoSalva = prisaoRepository.findById(id);
		if(prisaoSalva.isEmpty() ) {
			throw new EmptyResultDataAccessException(1);
		}
		return prisaoSalva.get();
	}
	
	//editar prisões
	public Prisao editar(Long id, Prisao prisao) {
		Optional<Prisao> prisaoSalva = prisaoRepository.findById(id);
        BeanUtils.copyProperties(prisao, prisaoSalva, "id");
        if(prisaoSalva.isEmpty() ) {
            throw new EmptyResultDataAccessException(1);
        }
        return prisaoRepository.save(prisao);
	}
}
