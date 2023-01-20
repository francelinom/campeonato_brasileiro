package br.com.francelino.campeonatobrasileiro.rest;

import br.com.francelino.campeonatobrasileiro.entity.Time;
import br.com.francelino.campeonatobrasileiro.service.TimeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/times")
public class TimeRestController {

    @Autowired
    private TimeService timeService;

    @GetMapping
    public ResponseEntity<List<Time>> getTimes() {
        return ResponseEntity.ok().body(timeService.listarTimes());
    }

    @ApiOperation(value = "Obtém os dados de um time")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Time> getTime(@PathVariable Integer id) {
        return ResponseEntity.ok().body(timeService.obterTime(id));
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarTime(Time time) {
        timeService.cadastrarTime(time);
        return ResponseEntity.ok().build();
    }
}
