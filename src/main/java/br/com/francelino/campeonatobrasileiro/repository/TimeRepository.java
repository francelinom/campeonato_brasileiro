package br.com.francelino.campeonatobrasileiro.repository;

import br.com.francelino.campeonatobrasileiro.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<Time, Integer> {
}
