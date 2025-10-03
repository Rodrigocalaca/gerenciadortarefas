package com.empresa.gerenciadortarefas;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TarefaControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Test
    void deveCriarEListarTarefa() {
        String baseUrl = "http://localhost:" + port + "/api/tarefas";

        // cria
        Tarefa t = new Tarefa();
        t.setTitulo("Estudar");
        t.setDescricao("Spring + H2");
        ResponseEntity<Tarefa> createResp = rest.postForEntity(baseUrl, t, Tarefa.class);
        assertThat(createResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createResp.getBody()).isNotNull();
        assertThat(createResp.getBody().getId()).isNotNull();

        // lista
        ResponseEntity<Tarefa[]> listResp = rest.getForEntity(baseUrl, Tarefa[].class);
        assertThat(listResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(listResp.getBody()).isNotEmpty();
    }
}
