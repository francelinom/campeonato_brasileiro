package br.com.francelino.campeonatobrasileiro.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer golsTime1;
    private Integer golsTime2;
    private Integer publicoPagante;
    private LocalDateTime data;
    private Integer rodada;
    private Boolean encerrado;

    @ManyToOne
    @JoinColumn(name = "time1")
    private Time time1;

    @ManyToOne
    @JoinColumn(name = "time2")
    private Time time2;
}
