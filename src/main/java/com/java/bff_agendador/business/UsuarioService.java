package com.java.bff_agendador.business;

import org.springframework.stereotype.Service;

import com.java.bff_agendador.business.dto.in.EnderecoDTORequest;
import com.java.bff_agendador.business.dto.in.LoginRequestDTO;
import com.java.bff_agendador.business.dto.in.TelefoneDTORequest;
import com.java.bff_agendador.business.dto.in.UsuarioDTORequest;
import com.java.bff_agendador.business.dto.out.EnderecoDTOResponse;
import com.java.bff_agendador.business.dto.out.TelefoneDTOResponse;
import com.java.bff_agendador.business.dto.out.UsuarioDTOResponse;
import com.java.bff_agendador.infrastructure.security.client.UsuarioClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioClient client;

    public UsuarioDTOResponse salvarUsuario(UsuarioDTORequest usuarioDTO) {
        return client.salvaUsuario(usuarioDTO);
    }

    public String loginUsuario(LoginRequestDTO usuarioDTO) {
        return client.login(usuarioDTO);
    }

    public UsuarioDTOResponse buscarUsuarioPorEmail(String email, String token) {
        return client.buscaUsuarioPorEmail(email, token);
    }

    public void deletaUsuarioPorEmail(String email, String token){
        client.deletaUsuarioPorEmail(email, token);
    }

    public UsuarioDTOResponse atualiarDadosUsuario(String token,UsuarioDTORequest dto) {
        return client.atualizaDadoUsuario(dto, token);
    }

    public EnderecoDTOResponse atualizaEndereco(Long idEndereco, EnderecoDTORequest dto, String token) {
        return client.atualizaEndereco(dto, idEndereco, token);
    }

    public TelefoneDTOResponse atualizaTelefone(Long idTelefone, TelefoneDTORequest dto, String token) {
        return client.atualizaTelefone(dto, idTelefone, token);
    }

    public EnderecoDTOResponse cadastraEndereco(String token, EnderecoDTORequest dto) {
        return client.cadastraEndereco(dto, token);
    }

    public TelefoneDTOResponse cadastraTelefone(String token, TelefoneDTORequest dto) {
        return client.cadastraTelefone(dto, token);
    }


}
