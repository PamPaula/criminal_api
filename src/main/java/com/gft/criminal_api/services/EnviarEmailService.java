package com.gft.criminal_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EnviarEmailService {
	
	@Autowired
	private JavaMailSender enviadorEmail;
	
	public void enviarEmail(String destino, String corpo, String assunto) {	
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom("enviandoemailcriminal@gmail.com");
		mensagem.setTo(destino);
		mensagem.setText(corpo);
		mensagem.setSubject(assunto);		
		enviadorEmail.send(mensagem);
		System.out.println("Enviando email de acesso!");
	}

}
