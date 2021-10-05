package com.gft.criminal_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gft.criminal_api.models.Advogado;
import com.gft.criminal_api.models.Delegado;
import com.gft.criminal_api.models.Juiz;
import com.gft.criminal_api.models.Perfil;
import com.gft.criminal_api.models.Usuario;

@Service
public class FabricaDeUsuarioService {
	
	@Autowired
	private UsuarioService usuarioService;
	
	public Usuario criarUsuario(Advogado advogado) {
		Usuario usuario = new Usuario();
		usuario.setEmail(advogado.getEmail());
		usuario.setSenha(new BCryptPasswordEncoder().encode("1234"));
		usuario.setPerfil(new Perfil(2L));
		return usuarioService.salvarUsuario(usuario);
	}
	
	public Usuario criarUsuario(Juiz juiz) {
		Usuario usuario = new Usuario();
		usuario.setEmail(juiz.getEmail());
		usuario.setSenha(new BCryptPasswordEncoder().encode("Gft2021"));
		usuario.setPerfil(new Perfil(1L));
		return usuarioService.salvarUsuario(usuario);
	}
	
	public Usuario criarUsuario(Delegado delegado) {
		Usuario usuario = new Usuario();
		usuario.setEmail(delegado.getEmail());
		usuario.setSenha(new BCryptPasswordEncoder().encode("Gft2021"));
		usuario.setPerfil(new Perfil(1L));
		return usuarioService.salvarUsuario(usuario);
	}
}
