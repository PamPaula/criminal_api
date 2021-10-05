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

import com.gft.criminal_api.controllers.dto.LegistaDTO;
import com.gft.criminal_api.models.Legista;
import com.gft.criminal_api.repositories.LegistaRepository;
import com.gft.criminal_api.services.LegistaService;

@RestController
@RequestMapping("/legista")
public class LegistaController {

	@Autowired
	private LegistaService legistaService;
	
	@Autowired
	private LegistaRepository legistaRepository;
	
	@GetMapping //listar legistas
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<List<LegistaDTO>> listar() {
		return legistaService.listar();
	}
	
	@PostMapping //salvar legistas
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<LegistaDTO> salvar(@Valid @RequestBody Legista legista, HttpServletResponse response) {
		return legistaService.salvar(legista, response);
	}
	
	@GetMapping("/{id}") //buscar legista por id
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<LegistaDTO> buscarPorId(@PathVariable Long id) {
		Optional<Legista> legista = legistaRepository.findById(id);
		LegistaDTO legistaDTO = LegistaDTO.from(legista);
		return !legista.isEmpty() ? ResponseEntity.ok(legistaDTO) : ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}") //editar legistas
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Legista> editar(@PathVariable Long id, @Valid @RequestBody Legista legista) {
        Legista legistaSalvo = legistaService.editar(id, legista);
        return ResponseEntity.ok(legistaSalvo);
    }
	
	@DeleteMapping("/{id}") //excluir legistas
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void remover(@PathVariable Long id) {
		legistaRepository.deleteById(id);
	}
}
