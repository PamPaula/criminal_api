package com.gft.criminal_api.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.criminal_api.controllers.dto.AutopsiaDTO;
import com.gft.criminal_api.models.Autopsia;
import com.gft.criminal_api.repositories.AutopsiaRepository;
import com.gft.criminal_api.services.AutopsiaService;

@RestController
@RequestMapping("/autopsia")
public class AutopsiaController {

	@Autowired
	private AutopsiaService autopsiaService;
	
	@Autowired
	private AutopsiaRepository autopsiaRepository;
	
	@GetMapping //listar autopsias
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<List<AutopsiaDTO>> listar() {
		return autopsiaService.listar();
	}
	
	@PostMapping //salvar autopsias
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<AutopsiaDTO> salvar(@Valid @RequestBody Autopsia autopsia, HttpServletResponse response) {
		return autopsiaService.salvar(autopsia, response);
	}
	
	@GetMapping("/{id}") //buscar autopsias por id
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<AutopsiaDTO> buscarPorId(@PathVariable Long id) {
		Optional<Autopsia> autopsia = autopsiaRepository.findById(id);
		AutopsiaDTO autopsiaDTO = AutopsiaDTO.from(autopsia);
		return !autopsia.isEmpty() ? ResponseEntity.ok(autopsiaDTO) : ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}") //editar autopsias
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Autopsia> editar(@PathVariable Long id, @Valid @RequestBody Autopsia autopsia) {
        Autopsia autopsiaSalva = autopsiaService.editar(id, autopsia);
        return ResponseEntity.ok(autopsiaSalva);
    }
	
	/*@DeleteMapping("/{id}") //excluir autopsias
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void remover(@PathVariable Long id) {
		autopsiaRepository.deleteById(id);
	}*/
}

