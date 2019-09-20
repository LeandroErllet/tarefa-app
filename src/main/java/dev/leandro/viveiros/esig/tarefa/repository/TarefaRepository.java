package dev.leandro.viveiros.esig.tarefa.repository;

import dev.leandro.viveiros.esig.tarefa.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {

    List<Tarefa> findAllByDoneTrue();

    List<Tarefa> findAllByDoneFalse();

    @Modifying
    @Query("update Tarefa t set t.description = :description where id = :id")
    void updateDescription(@Param("id") Integer id, @Param("description") String description);

    @Modifying
    @Query("update Tarefa t set t.done = true")
    void markAllAsDone();

    @Modifying
    @Query("update Tarefa t set t.done = false")
    void markAllAsPending();



}
