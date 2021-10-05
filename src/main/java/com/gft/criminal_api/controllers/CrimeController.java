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

import com.gft.criminal_api.controllers.dto.CrimeDTO;
import com.gft.criminal_api.models.Crime;
import com.gft.criminal_api.repositories.CrimeRepository;
import com.gft.criminal_api.services.CrimeService;

@RestController
@RequestMapping("/crime")
public class CrimeController {

	@Autowired
	private CrimeService crimeService;
	
	@Autowired
	private CrimeRepository crimeRepository;
	
	@GetMapping //listar crimes
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<List<CrimeDTO>> listar() {
		return crimeService.listar();
	}
	
	@PostMapping //salvar crimes
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<CrimeDTO> salvar(@Valid @RequestBody Crime crime, HttpServletResponse response) {
		return crimeService.salvar(crime, response);
	}
	
	@GetMapping("/{id}") //buscar crimes por id
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<CrimeDTO> buscarPorId(@PathVariable Long id) {
		Optional<Crime> crime = crimeRepository.findById(id);
		CrimeDTO crimeDTO = CrimeDTO.from(crime);
		return !crime.isEmpty() ? ResponseEntity.ok(crimeDTO) : ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}") //editar crimes
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Crime> editar(@PathVariable Long id, @Valid @RequestBody Crime crime) {
        Crime crimeSalvo = crimeService.editar(id, crime);
        return ResponseEntity.ok(crimeSalvo);
    }
	
	@DeleteMapping("/{id}") //excluir crimes
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void remover(@PathVariable Long id) {
		crimeRepository.deleteById(id);
	}
}
