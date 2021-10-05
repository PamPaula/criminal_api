package com.gft.criminal_api.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.criminal_api.controllers.dto.PrisaoDTO;
import com.gft.criminal_api.models.Prisao;
import com.gft.criminal_api.repositories.PrisaoRepository;
import com.gft.criminal_api.services.PrisaoService;

@RestController
@RequestMapping("/prisao")
public class PrisaoController {
	
	@Autowired
	private PrisaoService prisaoService;
	
	@Autowired
	private PrisaoRepository prisaoRepository;
	
	@GetMapping //listar prisões
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<List<PrisaoDTO>> listar() {
		return prisaoService.listar();
	}

	@PostMapping //salvar prisões
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PrisaoDTO> salvar(@Valid @RequestBody Prisao prisao, HttpServletResponse response) {
		return prisaoService.salvar(prisao, response);
	}
	
	@GetMapping("/{id}") //buscar prisões por id
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<PrisaoDTO> buscarPorId(@PathVariable Long id) {
		Optional<Prisao> prisao = prisaoRepository.findById(id);
		PrisaoDTO prisaoDTO = PrisaoDTO.from(prisao);
		return !prisao.isEmpty() ? ResponseEntity.ok(prisaoDTO) : ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}") //editar prisões
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Prisao> editar(@PathVariable Long id, @Valid @RequestBody Prisao prisao) {
        Prisao prisaoSalva = prisaoService.editar(id, prisao);
        return ResponseEntity.ok(prisaoSalva);
    }
	
	@DeleteMapping("/{id}") //excluir prisao
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void remover(@PathVariable Long id) {
		prisaoRepository.deleteById(id);
	}
}
