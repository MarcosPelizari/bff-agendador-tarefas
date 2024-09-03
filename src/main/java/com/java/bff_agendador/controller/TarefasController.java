package com.java.bff_agendador.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.bff_agendador.business.TarefasService;
import com.java.bff_agendador.business.dto.in.TarefasDTORequest;
import com.java.bff_agendador.business.dto.out.TarefasDTOResponse;
import com.java.bff_agendador.infrastructure.enums.StatusNotificacao;
import com.java.bff_agendador.infrastructure.security.SecurityConfig;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Cadastra tarefas de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class TarefasController {

    private final TarefasService tarefaService;

    @PostMapping
    @Operation(summary = "Salvar Tarefas de Usuários", description = "Cria uma nova tarefa")
    @ApiResponse(responseCode = "200", description = "Tarefa salva com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TarefasDTOResponse> gravarTarefa(
            @RequestBody TarefasDTORequest dto,
            @RequestHeader(name = "Authorization", required = false) String token) {

        System.out.println("DTO log: " + dto);

        return ResponseEntity.ok(tarefaService.gravarTarefa(token, dto));
    }

    @GetMapping("/eventos")
    @Operation(summary = "Busca tarefas por Período", description = "Busca tarefas cadastradas por período")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<List<TarefasDTOResponse>> buscaListaTarefaPorPeriodo(
            @RequestParam("dataInicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam("dataFinal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader(name = "Authorization", required = false) String token) {

        return ResponseEntity.ok(tarefaService.buscaTarefaAgendadaPorPeriodo(dataInicial, dataFinal, token));
    }

    @GetMapping
    @Operation(summary = "Busca lista de tarefas por email de usuário", description = "Busca tarefas cadastradas por usuário")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Email não encontrado")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<List<TarefasDTOResponse>> buscaTarefaPorEmail(@RequestHeader(name = "Authorization", required = false) String token) {

        return ResponseEntity.ok(tarefaService.buscaTarefaPorEmail(token));
    }

    @DeleteMapping
    @Operation(summary = "Deleta tarefas por Id", description = "Deleta tarefas cadastrads por ID")
    @ApiResponse(responseCode = "200", description = "Tarefas deletadas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Tarefa id não encontrada")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<Void> deletarTarefaPorId(
            @RequestParam("id") String id,
            @RequestHeader(name = "Authorization", required = false) String token) {
        tarefaService.deletarPorId(id, token);

        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Operation(summary = "Altera status de tarefas", description = "Altera status das tarefas cadastradas")
    @ApiResponse(responseCode = "200", description = "Status da tarefa alterado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Tarefa id não encontrada")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<TarefasDTOResponse> alteraStatusNotificacao(
            @RequestParam("status") StatusNotificacao status,
            @RequestParam("id") String id,
            @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefaService.alteraStatus(status, id, token));
    }

    @PutMapping
    @Operation(summary = "Altera dados de tarefas", description = "Altera dados das tarefas cadastradas")
    @ApiResponse(responseCode = "200", description = "Tarefas alterads")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Tarefa id não encontrada")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<TarefasDTOResponse> updateTarefa(
            @RequestBody TarefasDTORequest dto, @RequestParam("id") String id,
            @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefaService.updateTarefas(dto, id, token));
    }

}
