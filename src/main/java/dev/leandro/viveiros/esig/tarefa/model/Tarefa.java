package dev.leandro.viveiros.esig.tarefa.model;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Getter
@Setter
@Table(name = "tarefa")
public class Tarefa  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @Column(name = "ip", nullable = false, length = 32)
    private String ip;

    @Column(name = "done", length = 1)
    private Boolean done = false;

    public void toggleStatus() {
        done = !done;
    }

}
