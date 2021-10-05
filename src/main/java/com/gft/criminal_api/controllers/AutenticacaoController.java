package com.gft.criminal_api.controllers;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.criminal_api.controllers.dto.TokenDTO;
import com.gft.criminal_api.controllers.forms.AutenticacaoForm;
import com.gft.criminal_api.services.AutenticacaoService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	@Autowired
	private AutenticacaoService autenticacaoService;

	@PostMapping
	public ResponseEntity<TokenDTO> autenticar(@RequestBody AutenticacaoForm authForm) {
		try {
			return ResponseEntity.ok(autenticacaoService.autenticar(authForm));
		}catch(AuthenticationException ae) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}	
	}
}
