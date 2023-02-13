package br.com.francelino.campeonatobrasileiro.repository;

import br.com.francelino.campeonatobrasileiro.entity.Jogo;
import br.com.francelino.campeonatobrasileiro.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JogoRepository extends JpaRepository<Jogo, Integer> {
    // SELECT * FROM JOGO WHERE TIME1 = :TIME1 AND ENCERRADO = :ENCERRADO
    List<Jogo> findByTime1Encerrado(Time time, Boolean encerrado);

    List<Jogo> findByTime2Encerrado(Time time, Boolean encerrado);
}
