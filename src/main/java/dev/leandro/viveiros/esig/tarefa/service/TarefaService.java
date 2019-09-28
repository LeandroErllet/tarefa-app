package dev.leandro.viveiros.esig.tarefa.service;

import dev.leandro.viveiros.esig.tarefa.model.Tarefa;

import java.util.List;

public interface TarefaService {

    long count();


    List<Tarefa> findAll();

    List<Tarefa> findAllWithoutIp();

    List<Tarefa> findByIp(String ip);

    List<Tarefa> findDone();

    List<Tarefa> findPending();

    void save(Tarefa tarefa);

    void updateDescription(Integer id, String description);

    void delete(Integer id);

    long countDone();

    long countPending();

    void toggleStatus(Integer id);

    void toggleAllStatus();
}
