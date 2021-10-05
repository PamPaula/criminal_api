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

import com.gft.criminal_api.controllers.dto.AdvogadoDTO;
import com.gft.criminal_api.models.Advogado;
import com.gft.criminal_api.repositories.AdvogadoRepository;
import com.gft.criminal_api.services.AdvogadoService;

@RestController
@RequestMapping("/advogado")
public class AdvogadoController {
	
	@Autowired
	private AdvogadoService advogadoService;
	
	@Autowired
	private AdvogadoRepository advogadoRepository;
	
	@GetMapping //listar advogados
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<List<AdvogadoDTO>> listar() {
		return advogadoService.listar();
	}
	
	@PostMapping //salvar advogados
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<AdvogadoDTO> salvar(@Valid @RequestBody Advogado advogado, HttpServletResponse response) {
		return advogadoService.salvar(advogado, response);
	}
	
	@GetMapping("/{id}") //buscar advogados por id
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<AdvogadoDTO> buscarPorId(@PathVariable Long id) {
		Optional<Advogado> advogado = advogadoRepository.findById(id);
		AdvogadoDTO advogadoDTO = AdvogadoDTO.from(advogado);
		return !advogado.isEmpty() ? ResponseEntity.ok(advogadoDTO) : ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}") //editar advogados
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Advogado> editar(@PathVariable Long id, @Valid @RequestBody Advogado advogado) {
        Advogado advogadoSalvo = advogadoService.editar(id, advogado);
        return ResponseEntity.ok(advogadoSalvo);
    }
	
	@DeleteMapping("/{id}") //excluir advogados
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void remover(@PathVariable Long id) {
		advogadoRepository.deleteById(id);
	}
}
