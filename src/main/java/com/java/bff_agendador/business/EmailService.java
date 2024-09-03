package com.java.bff_agendador.business;

import org.springframework.stereotype.Service;

import com.java.bff_agendador.business.dto.out.TarefasDTOResponse;
import com.java.bff_agendador.infrastructure.security.client.EmailClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailClient emailClient;

    public void enviaEmail(TarefasDTOResponse dto) {
       emailClient.enviarEmail(dto);
    }

}