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

import com.gft.criminal_api.controllers.dto.JuizDTO;
import com.gft.criminal_api.models.Juiz;
import com.gft.criminal_api.repositories.JuizRepository;
import com.gft.criminal_api.services.JuizService;

@RestController
@RequestMapping("/juiz")
public class JuizController {

	@Autowired
	private JuizService juizService;
	
	@Autowired
	private JuizRepository juizRepository;
	
	@GetMapping //listar juizes
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<List<JuizDTO>> listar() {
		return juizService.listar();
	}
	
	@PostMapping //salvar juizes
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<JuizDTO> salvar(@Valid @RequestBody Juiz juiz, HttpServletResponse response) {
		return juizService.salvar(juiz, response);
	}
	
	@GetMapping("/{id}") //buscar juizes por id
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<JuizDTO> buscarPorId(@PathVariable Long id) {
		Optional<Juiz> juiz = juizRepository.findById(id);
		JuizDTO juizDTO = JuizDTO.from(juiz);
		return !juiz.isEmpty() ? ResponseEntity.ok(juizDTO) : ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}") //editar juizes
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Juiz> editar(@PathVariable Long id, @Valid @RequestBody Juiz juiz) {
        Juiz juizSalvo = juizService.editar(id, juiz);
        return ResponseEntity.ok(juizSalvo);
    }
	
	@DeleteMapping("/{id}") //excluir juizes
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void remover(@PathVariable Long id) {
		juizRepository.deleteById(id);
	}
}
