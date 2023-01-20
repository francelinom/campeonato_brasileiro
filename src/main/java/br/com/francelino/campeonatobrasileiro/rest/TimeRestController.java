package br.com.francelino.campeonatobrasileiro.rest;

import br.com.francelino.campeonatobrasileiro.dto.TimeDTO;
import br.com.francelino.campeonatobrasileiro.entity.Time;
import br.com.francelino.campeonatobrasileiro.service.TimeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/times")
public class TimeRestController {

    @Autowired
    private TimeService timeService;

    @GetMapping
    public ResponseEntity<List<TimeDTO>> getTimes() {
        return ResponseEntity.ok().body(timeService.listarTimes());
    }

    @ApiOperation(value = "Obtém os dados de um time")
    @GetMapping(value = "{id}")
    public ResponseEntity<TimeDTO> getTime(@PathVariable Integer id) {
        return ResponseEntity.ok().body(timeService.obterTime(id));
    }

    @PostMapping
    public ResponseEntity<TimeDTO> cadastrarTime(@Validated @RequestBody TimeDTO time) throws Exception {
        return ResponseEntity.ok().body(timeService.cadastrarTime(time));
    }
}
