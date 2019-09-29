package dev.leandro.viveiros.esig.tarefa.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TarefaRestModel {

    private List<Tarefa> tarefas;

    public TarefaRestModel(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }
}
