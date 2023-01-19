package br.com.francelino.campeonatobrasileiro.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer golsTime1;
    private Integer golsTime2;
    private Integer publicoPagante;


    private Time time1;
    private Time time2;
}
