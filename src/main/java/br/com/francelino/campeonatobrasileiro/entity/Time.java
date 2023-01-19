package br.com.francelino.campeonatobrasileiro.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String nome;

    @Column(length = 4)
    private String sigla;

    @Column(length = 2)
    private String uf;
}
