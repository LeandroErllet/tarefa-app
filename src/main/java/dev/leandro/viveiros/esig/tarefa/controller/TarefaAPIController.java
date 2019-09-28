package dev.leandro.viveiros.esig.tarefa.controller;

import com.sun.faces.action.RequestMapping;
import dev.leandro.viveiros.esig.tarefa.model.Tarefa;
import dev.leandro.viveiros.esig.tarefa.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TarefaAPIController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    List<Tarefa> all() {
        return tarefaService.findAllWithoutIp();
    }
}