package dev.leandro.viveiros.esig.tarefa.controller;


import dev.leandro.viveiros.esig.tarefa.model.Tarefa;
import dev.leandro.viveiros.esig.tarefa.service.TarefaService;
import dev.leandro.viveiros.esig.tarefa.util.TarefaFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Component
@Getter
@Setter
@Scope("view")
public class TarefaController implements Serializable {

    @Autowired
    private TarefaService tarefaService;

    private Tarefa tarefa, selected;

    private List<Tarefa> tarefas;

    private long tarefaCount, doneTarefasCount, pendingTarefasCount;

    private TarefaFilter filter;

    @PostConstruct
    public void init() {
        tarefas = new ArrayList<>();
        tarefa = new Tarefa();
        loadData();
    }

    private void loadData() {
        doneTarefasCount = tarefaService.countDone();
        pendingTarefasCount = tarefaService.countPending();
        tarefaCount = tarefaService.count();

        if (filter == TarefaFilter.PENDING) {
            tarefas = tarefaService.findPending();
        } else if (filter == TarefaFilter.DONE) {
            tarefas = tarefaService.findDone();
        } else {
            tarefas = tarefaService.findAll();
        }
    }

    public Boolean hasTarefas() {
        return tarefas.size() > 0;
    }

    public void toggleDoneFilter() {
        if (filter == TarefaFilter.DONE) {
            clearFiltersAndLoadData();
            return;
        }

        filter = TarefaFilter.DONE;
        loadData();
    }

    public void togglePendingFilter() {
        if (filter == TarefaFilter.PENDING) {
            clearFiltersAndLoadData();
            return;
        }

        filter = TarefaFilter.PENDING;
        loadData();
    }

    private void clearFiltersAndLoadData() {
        filter = null;
        loadData();
    }

    public boolean canFilter(TarefaFilter filterType) {
        if (filterType == TarefaFilter.DONE) {
            return doneTarefasCount > 0 || filter == TarefaFilter.DONE;
        }

        if (filterType == TarefaFilter.PENDING) {
            return pendingTarefasCount > 0 || filter == TarefaFilter.PENDING;
        }

        return true;
    }

    public void delete(Tarefa tarefa) {
        tarefaService.delete(tarefa.getId());
        loadData();
    }

    public void edit(Tarefa tarefa) {
        selected = tarefa;
    }

    public Boolean isEditing(Tarefa tarefa) {
        return selected != null && selected.equals(tarefa);
    }

    public void cancelEdit() {
        selected = null;
    }

    public void save(Tarefa tarefa) {
        tarefaService.save(tarefa);
        selected = null;

        loadData();
    }

    public void toggleAllStatus() {
        tarefaService.toggleAllStatus();
        loadData();
    }

    public Boolean getAllDone() {
        return tarefaCount > 0 && doneTarefasCount == tarefaCount;
    }

    public void create() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        tarefa.setIp(ipAddress);
        tarefaService.save(tarefa);
        tarefa = new Tarefa();

        loadData();
    }

    public void clearTarefa() {
        tarefa = new Tarefa();
    }

    public TarefaFilter getDoneFilter() {
        return TarefaFilter.DONE;
    }

    public TarefaFilter getPendingFilter() {
        return TarefaFilter.PENDING;
    }
}
