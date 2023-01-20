package br.com.francelino.campeonatobrasileiro.repository;

import br.com.francelino.campeonatobrasileiro.entity.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogoRepository extends JpaRepository<Jogo, Integer> {
}
