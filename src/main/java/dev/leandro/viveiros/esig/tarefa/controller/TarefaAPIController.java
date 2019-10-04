package dev.leandro.viveiros.esig.tarefa.controller;

import dev.leandro.viveiros.esig.tarefa.model.TarefaRestModel;
import dev.leandro.viveiros.esig.tarefa.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TarefaAPIController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping("/api")
    TarefaRestModel all() {
        return new TarefaRestModel(tarefaService.findAllWithoutIp());
    }
}