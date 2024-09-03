package com.java.bff_agendador.business;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.java.bff_agendador.business.dto.in.TarefasDTORequest;
import com.java.bff_agendador.business.dto.out.TarefasDTOResponse;
import com.java.bff_agendador.infrastructure.enums.StatusNotificacao;
import com.java.bff_agendador.infrastructure.security.client.TarefasClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasClient tarefasClient;

    public TarefasDTOResponse gravarTarefa(String token, TarefasDTORequest dto) {
        return tarefasClient.gravarTarefas(dto, token);
    }

    public List<TarefasDTOResponse> buscaTarefaAgendadaPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal, String token) {
        return tarefasClient.buscaListaDeTarefasPorPeriodo(dataInicial, dataFinal, token);
    }

    public List<TarefasDTOResponse> buscaTarefaPorEmail(String token) {
        return tarefasClient.buscaTarefasPorEmail(token);
    }

    public void deletarPorId(String id, String token) {
        tarefasClient.deletaTarefaPorId(id, token);
    }

    public TarefasDTOResponse alteraStatus(StatusNotificacao status, String id, String token) {
        return tarefasClient.alteraStatusNotificacao(status, id, token);
    }

    public TarefasDTOResponse updateTarefas(TarefasDTORequest dto, String id, String token) {
        return tarefasClient.updateTarefas(dto, id, token);
    }

}
