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

import com.gft.criminal_api.controllers.dto.DelegaciaDTO;
import com.gft.criminal_api.models.Delegacia;
import com.gft.criminal_api.repositories.DelegaciaRepository;
import com.gft.criminal_api.services.DelegaciaService;

@RestController
@RequestMapping("/delegacia")
public class DelegaciaController {
	
	@Autowired
	private DelegaciaService delegaciaService;
	
	@Autowired
	private DelegaciaRepository delegaciaRepository;
	
	@GetMapping //listar delegacias
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<List<DelegaciaDTO>> listar() {
		return delegaciaService.listar();
	}
	
	@PostMapping //salvar delegacias
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<DelegaciaDTO> salvar(@Valid @RequestBody Delegacia delegacia, HttpServletResponse response) {
		return delegaciaService.salvar(delegacia, response);
	}
	
	@GetMapping("/{id}") //buscar delegacias por id
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<DelegaciaDTO> buscarPorId(@PathVariable Long id) {
		Optional<Delegacia> delegacia = delegaciaRepository.findById(id);
		DelegaciaDTO delegaciaDTO = DelegaciaDTO.from(delegacia);
		return !delegacia.isEmpty() ? ResponseEntity.ok(delegaciaDTO) : ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}") //editar delegacias
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Delegacia> editar(@PathVariable Long id, @Valid @RequestBody Delegacia delegacia) {
        Delegacia delegaciaSalva = delegaciaService.editar(id, delegacia);
        return ResponseEntity.ok(delegaciaSalva);
    }
	
	@DeleteMapping("/{id}") //excluir delegacias
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void remover(@PathVariable Long id) {
		delegaciaRepository.deleteById(id);
	}
}
