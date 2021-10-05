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

import com.gft.criminal_api.controllers.dto.DelegadoDTO;
import com.gft.criminal_api.models.Delegado;
import com.gft.criminal_api.repositories.DelegadoRepository;
import com.gft.criminal_api.services.DelegadoService;

@RestController
@RequestMapping("/delegado")
public class DelegadoController {

	@Autowired
	private DelegadoService delegadoService;
	
	@Autowired
	private DelegadoRepository delegadoRepository;
	
	@GetMapping //listar delegados
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<List<DelegadoDTO>> listar() {
		return delegadoService.listar();
	}
	
	@GetMapping("/asc") //listar delegados em ordem alfabética crescente
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public List<DelegadoDTO> listarDelegadoAsc(){
		return delegadoService.listarDelegadoAsc();
	}
	
	@GetMapping("/desc") //listar delegados em ordem alfabética decrescente
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public List<DelegadoDTO> listarDelegadoDesc(){
		return delegadoService.listarDelegadoDesc();
	}

	@PostMapping //salvar delegados
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<DelegadoDTO> salvar(@Valid @RequestBody Delegado delegado, HttpServletResponse response) {
		return delegadoService.salvar(delegado, response);
	}
	
	@GetMapping("/{id}") //buscar delegados por id
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<DelegadoDTO> buscarPorId(@PathVariable Long id) {
		Optional<Delegado> delegado = delegadoRepository.findById(id);
		DelegadoDTO delegadoDTO = DelegadoDTO.from(delegado);
		return !delegado.isEmpty() ? ResponseEntity.ok(delegadoDTO) : ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}") //editar delegacias
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Delegado> editar(@PathVariable Long id, @Valid @RequestBody Delegado delegado) {
        Delegado delegadoSalvo = delegadoService.editar(id, delegado);
        return ResponseEntity.ok(delegadoSalvo);
    }
	
	@DeleteMapping("/{id}") //excluir delegados
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void remover(@PathVariable Long id) {
		delegadoRepository.deleteById(id);
	}
}
