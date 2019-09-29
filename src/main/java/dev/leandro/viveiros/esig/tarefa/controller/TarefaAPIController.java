package dev.leandro.viveiros.esig.tarefa.controller;

import com.sun.faces.action.RequestMapping;
import dev.leandro.viveiros.esig.tarefa.model.TarefaRestModel;
import dev.leandro.viveiros.esig.tarefa.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TarefaAPIController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    TarefaRestModel all() {
        return new TarefaRestModel(tarefaService.findAllWithoutIp());
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Integer id) {
        tarefaService.delete(id);
    }
}