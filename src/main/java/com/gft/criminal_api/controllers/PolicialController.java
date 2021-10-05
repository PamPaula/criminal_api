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

import com.gft.criminal_api.controllers.dto.PolicialDTO;
import com.gft.criminal_api.filters.FiltroPolicial;
import com.gft.criminal_api.models.Policial;
import com.gft.criminal_api.repositories.PolicialRepository;
import com.gft.criminal_api.services.PolicialService;

@RestController
@RequestMapping("/policial")
public class PolicialController {

	@Autowired
	private PolicialService policialService;
	
	@Autowired
	private PolicialRepository policialRepository;
	
	@GetMapping //listar policiais
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<List<PolicialDTO>> listar() {
		return policialService.listar();
	}
	
	@PostMapping //salvar policiais
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PolicialDTO> salvar(@Valid @RequestBody Policial policial, HttpServletResponse response) {
		return policialService.salvar(policial, response);
	}
	
	@GetMapping("/{id}") //buscar policiais por id
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public ResponseEntity<PolicialDTO> buscarPorId(@PathVariable Long id) {
		Optional<Policial> policial = policialRepository.findById(id);
		PolicialDTO policialDTO = PolicialDTO.from(policial);
		return !policial.isEmpty() ? ResponseEntity.ok(policialDTO) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/nome/{nome}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	public List<PolicialDTO> buscarPolicialNome(FiltroPolicial filtroPolicial){
		return policialService.buscarPolicialNome(filtroPolicial);
	}

	@PutMapping("/{id}") //editar policiais
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Policial> editar(@PathVariable Long id, @Valid @RequestBody Policial policial) {
        Policial policialSalvo = policialService.editar(id, policial);
        return ResponseEntity.ok(policialSalvo);
    }
	
	@DeleteMapping("/{id}") //excluir policiais
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void remover(@PathVariable Long id) {
		policialRepository.deleteById(id);
	}
}
