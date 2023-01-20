package br.com.francelino.campeonatobrasileiro.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class TimeDTO {
    private Integer id;
    private String nome;
    private String sigla;
    private String uf;
    private String estadio;
}
