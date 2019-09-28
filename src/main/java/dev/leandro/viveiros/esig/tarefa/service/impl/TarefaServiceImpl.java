package dev.leandro.viveiros.esig.tarefa.service.impl;

import dev.leandro.viveiros.esig.tarefa.model.Tarefa;
import dev.leandro.viveiros.esig.tarefa.repository.TarefaRepository;
import dev.leandro.viveiros.esig.tarefa.service.TarefaService;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class TarefaServiceImpl implements TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;


    @Override
    public long count() {
        return tarefaRepository
                .findAll().stream()
                .filter(tarefa -> tarefa.getIp().equalsIgnoreCase(getUserIP()))
                .count();
    }

    @Override
    public List<Tarefa> findAll() {
        return tarefaRepository
                .findAll().stream()
                .filter(tarefa -> tarefa.getIp().equalsIgnoreCase(getUserIP()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Tarefa> findAllWithoutIp() {
        return tarefaRepository.findAll();
    }

    @Override
    public List<Tarefa> findByIp(String ip) {
        return tarefaRepository.findByIp(ip);
    }

    @Override
    public List<Tarefa> findDone() {
        return tarefaRepository
                .findAllByDoneTrue().stream()
                .filter(tarefa -> tarefa.getIp().equalsIgnoreCase(getUserIP()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Tarefa> findPending() {
        return tarefaRepository
                .findAllByDoneFalse().stream()
                .filter(tarefa -> tarefa.getIp().equalsIgnoreCase(getUserIP()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Tarefa tarefa) {
        tarefaRepository.save(tarefa);
    }

    @Override
    public void updateDescription(Integer id, String description) {
        tarefaRepository.updateDescription(id, description);
    }

    @Override
    public void delete(Integer id) {
        tarefaRepository.deleteById(id);
    }

    @Override
    public void toggleStatus(Integer id) {
        val optionalTarefa = tarefaRepository.findById(id);

        if (!optionalTarefa.isPresent()) {
            throw new RuntimeException("Entidade não encontrada");
        }

        optionalTarefa.ifPresent(tarefa -> {
            tarefa.toggleStatus();
            save(tarefa);
        });
    }


    @Override
    public void toggleAllStatus() {
        val count = count();
        if (count == 0) {
            return;
        }
        val doneCount = countDone();

        if (doneCount == count) {
            tarefaRepository.markAllAsPending();
            return;
        }
        tarefaRepository.markAllAsDone();
    }

    @Override
    public long countDone() {
        return tarefaRepository
                .findAllByDoneTrue().stream()
                .filter(tarefa -> tarefa.getIp().equalsIgnoreCase(getUserIP()))
                .count();
    }

    @Override
    public long countPending() {
       return tarefaRepository
                .findAllByDoneFalse().stream()
                .filter(tarefa -> tarefa.getIp().equalsIgnoreCase(getUserIP()))
                .count();
    }

    private String getUserIP() {
        var httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        var ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = httpServletRequest.getRemoteAddr();
        }
        return ipAddress;
    }
}
