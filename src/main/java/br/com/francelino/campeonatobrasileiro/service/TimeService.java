package br.com.francelino.campeonatobrasileiro.service;

import br.com.francelino.campeonatobrasileiro.dto.TimeDTO;
import br.com.francelino.campeonatobrasileiro.entity.Time;
import br.com.francelino.campeonatobrasileiro.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeService {

    @Autowired
    private TimeRepository repository;

    public TimeDTO cadastrarTime(TimeDTO time) throws Exception {
        Time entity = toEntity(time);
        if (time.getId() == null) {
            entity = repository.save(entity);
            return toDto(entity);
        } else {
            throw new Exception("Time já existe");
        }
    }

    private Time toEntity(TimeDTO time) {
        Time entity = new Time();
        entity.setUf(time.getUf());
        entity.setId(time.getId());
        entity.setNome(time.getNome());
        entity.setEstadio(time.getEstadio());
        entity.setSigla(time.getSigla());
        return entity;
    }

    private TimeDTO toDto(Time entity) {
        TimeDTO dto = new TimeDTO();
        dto.setEstadio(entity.getEstadio());
        dto.setNome(entity.getNome());
        dto.setId(entity.getId());
        dto.setUf(entity.getUf());
        dto.setSigla(entity.getSigla());
        return dto;
    }

    public List<TimeDTO> listarTimes() {

        return repository.findAll().stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }

    public TimeDTO obterTime(Integer id) {
        return toDto(repository.findById(id).get());
    }
}
