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

import com.gft.criminal_api.controllers.dto.CriminosoDTO;
import com.gft.criminal_api.models.Criminoso;
import com.gft.criminal_api.repositories.CriminosoRepository;
import com.gft.criminal_api.services.CriminosoService;

@RestController
@RequestMapping("/criminoso")
public class CriminosoController {

	@Autowired
	private CriminosoService criminosoService;
	
	@Autowired
	private CriminosoRepository criminosoRepository;
	
	@GetMapping //listar criminosos
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<List<CriminosoDTO>> listar() {
		return criminosoService.listar();
	}
	
	@PostMapping //salvar criminosos
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<CriminosoDTO> salvar(@Valid @RequestBody Criminoso criminoso, HttpServletResponse response) {
		return criminosoService.salvar(criminoso, response);
	}
	
	@GetMapping("/{id}") //buscar criminosos por id
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<CriminosoDTO> buscarPorId(@PathVariable Long id) {
		Optional<Criminoso> criminoso = criminosoRepository.findById(id);
		CriminosoDTO criminosoDTO = CriminosoDTO.from(criminoso);
		return !criminoso.isEmpty() ? ResponseEntity.ok(criminosoDTO) : ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}") //editar criminosos
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Criminoso> editar(@PathVariable Long id, @Valid @RequestBody Criminoso criminoso) {
        Criminoso criminosoSalvo = criminosoService.editar(id, criminoso);
        return ResponseEntity.ok(criminosoSalvo);
    }
	
	@DeleteMapping("/{id}") //excluir criminosos
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void remover(@PathVariable Long id) {
		criminosoRepository.deleteById(id);
	}
}
