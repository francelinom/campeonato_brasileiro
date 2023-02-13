package br.com.francelino.campeonatobrasileiro.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClassificacaoDTO {
    private List<ClassificacaoDTO> times = new ArrayList<>();
}
