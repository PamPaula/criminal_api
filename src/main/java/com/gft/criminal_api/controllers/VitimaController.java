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

import com.gft.criminal_api.controllers.dto.VitimaDTO;
import com.gft.criminal_api.models.Vitima;
import com.gft.criminal_api.repositories.VitimaRepository;
import com.gft.criminal_api.services.VitimaService;

@RestController
@RequestMapping("/vitima")
public class VitimaController {

	@Autowired
	private VitimaService vitimaService;
	
	@Autowired
	private VitimaRepository vitimaRepository;
	
	@GetMapping //listar vitimas
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<List<VitimaDTO>> listar() {
		return vitimaService.listar();
	}
	
	@PostMapping //salvar vitimas
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<VitimaDTO> salvar(@Valid @RequestBody Vitima vitima, HttpServletResponse response) {
		return vitimaService.salvar(vitima, response);
	}
	
	@GetMapping("/{id}") //buscar vitmas por id
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<VitimaDTO> buscarPorId(@PathVariable Long id) {
		Optional<Vitima> vitima = vitimaRepository.findById(id);
		VitimaDTO vitimaDTO = VitimaDTO.from(vitima);
		return !vitima.isEmpty() ? ResponseEntity.ok(vitimaDTO) : ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}") //editar vitimas
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Vitima> editar(@PathVariable Long id, @Valid @RequestBody Vitima vitima) {
        Vitima vitimaSalva = vitimaService.editar(id, vitima);
        return ResponseEntity.ok(vitimaSalva);
    }
	
	@DeleteMapping("/{id}") //excluir vitimas
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void remover(@PathVariable Long id) {
		vitimaRepository.deleteById(id);
	}
}
