package br.com.francelino.campeonatobrasileiro.service;

import br.com.francelino.campeonatobrasileiro.dto.ClassificacaoDTO;
import br.com.francelino.campeonatobrasileiro.dto.ClassificacaoTimeDTO;
import br.com.francelino.campeonatobrasileiro.dto.JogoDTO;
import br.com.francelino.campeonatobrasileiro.dto.JogoFinalizadoDTO;
import br.com.francelino.campeonatobrasileiro.entity.Jogo;
import br.com.francelino.campeonatobrasileiro.entity.Time;
import br.com.francelino.campeonatobrasileiro.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class JogoService {
    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private TimeService timeService;

    public void gerarJogos(LocalDateTime primeiraRodada) {

        final List<Time> times = timeService.findAll();
        List<Time> all1 = new ArrayList<>();
        List<Time> all2 = new ArrayList<>();
        all1.addAll(times);
        all2.addAll(times);

        jogoRepository.deleteAll();

        List<Jogo> jogos = new ArrayList<>();

        int t = times.size();
        int m = times.size() / 2;
        LocalDateTime dataJogo = primeiraRodada;
        Integer rodada = 0;
        for (int i = 0; i < t - 1; i++) {
            rodada = i + 1;
            for (int j = 0; j < m; j++) {
                Time time1;
                Time time2;
                if (j % 2 == 1 || i % 2 == 1 && j == 0) {
                    time1 = times.get(t - j - 1);
                    time2 = times.get(j);
                } else {
                    time1 = times.get(j);
                    time2 = times.get(t - j - 1);
                }
                if (time1 == null) {
                    System.out.println("Time 1 null");
                }
                jogos.add(gerarJogo(dataJogo, rodada, time1, time2));
                dataJogo = dataJogo.plusDays(7);
            }
            // girar os times no sentido horário, matendo o primeiro no lugar
            times.add(1, times.remove(times.size() - 1));
        }
        jogos.forEach(jogo -> System.out.println(jogo));
        jogoRepository.saveAll(jogos);
        List<Jogo> jogos2 = new ArrayList<>();
        jogos.forEach(jogo -> {
            Time time1 = jogo.getTime2();
            Time time2 = jogo.getTime1();
            jogos2.add(gerarJogo(jogo.getData().plusDays(7 * jogos.size()), jogo.getRodada() + jogos.size(), time1, time2));
        });
    }

    private Jogo gerarJogo(LocalDateTime dataJogo, Integer rodada, Time time1, Time time2) {
        Jogo jogo = new Jogo();
        jogo.setTime1(time1);
        jogo.setTime2(time2);
        jogo.setRodada(rodada);
        jogo.setData(dataJogo);
        jogo.setEncerrado(false);
        jogo.setGolsTime1(0);
        jogo.setGolsTime2(0);
        jogo.setPublicoPagante(0);
        return jogo;
    }

    private JogoDTO entityToDTO(Jogo entity) {
        JogoDTO jogoDTO = new JogoDTO();
        jogoDTO.setId(entity.getId());
        jogoDTO.setData(entity.getData());
        jogoDTO.setEncerrado(entity.getEncerrado());
        jogoDTO.setGolsTime1(entity.getGolsTime1());
        jogoDTO.setGolsTime2(entity.getGolsTime2());
        jogoDTO.setPublicoPagante(entity.getPublicoPagante());
        jogoDTO.setRodada(entity.getRodada());
        jogoDTO.setTime1(timeService.toDto(entity.getTime1()));
        jogoDTO.setTime2(timeService.toDto(entity.getTime2()));
        return jogoDTO;
    }

    public List<JogoDTO> listarJogos() {
        return jogoRepository.findAll().stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());
    }

    public JogoDTO finalizar(Integer id, JogoFinalizadoDTO jogoFinalizadoDTO) throws Exception {
        Optional<Jogo> optionalJogo = jogoRepository.findById(id);
        if (optionalJogo.isPresent()) {
            final Jogo jogo = optionalJogo.get();
            jogo.setGolsTime1(jogoFinalizadoDTO.getGolsTime1());
            jogo.setGolsTime2(jogoFinalizadoDTO.getGolsTime2());
            jogo.setEncerrado(true);
            jogo.setPublicoPagante(jogoFinalizadoDTO.getPublicoPagante());
            return entityToDTO(jogoRepository.save(jogo));
        } else {
            throw new Exception("Jogo não existe");
        }
    }

    public ClassificacaoDTO obterClassificacao() {
        ClassificacaoDTO classificacaoDTO = new ClassificacaoDTO();
        final List<Time> times = timeService.findAll();

        times.forEach(time -> {
            final List<Jogo> jogosMandante = jogoRepository.findByTime1Encerrado(time, true);
            final List<Jogo> jogosVisitante = jogoRepository.findByTime2Encerrado(time, true);
            AtomicReference<Integer> vitorias = new AtomicReference<>(0);
            AtomicReference<Integer> empates = new AtomicReference<>(0);
            AtomicReference<Integer> derrotas = new AtomicReference<>(0);
            AtomicReference<Integer> golsSofridos = new AtomicReference<>(0);
            AtomicReference<Integer> golsMarcados = new AtomicReference<>(0);

            jogosMandante.forEach(jogo -> {
                if (jogo.getGolsTime1() > jogo.getGolsTime2()) {
                    vitorias.getAndSet(vitorias.get() + 1);
                } else if (jogo.getGolsTime1() < jogo.getGolsTime2()) {
                    derrotas.getAndSet(derrotas.get() + 1);
                } else {
                    empates.getAndSet(empates.get() + 1);
                }
                golsMarcados.set(golsMarcados.get() + jogo.getGolsTime1());
                golsSofridos.set(golsSofridos.get() + jogo.getGolsTime2());
            });

            jogosVisitante.forEach(jogo -> {
                if (jogo.getGolsTime2() > jogo.getGolsTime1()) {
                    vitorias.getAndSet(vitorias.get() + 1);
                } else if (jogo.getGolsTime2() < jogo.getGolsTime1()) {
                    derrotas.getAndSet(derrotas.get() + 1);
                } else {
                    empates.getAndSet(empates.get() + 1);
                }
                golsMarcados.set(golsMarcados.get() + jogo.getGolsTime2());
                golsSofridos.set(golsSofridos.get() + jogo.getGolsTime1());
            });

            ClassificacaoTimeDTO classificacaoTimeDTO = new ClassificacaoTimeDTO();
            classificacaoTimeDTO.setIdTime(time.getId());
            classificacaoTimeDTO.setTime(time.getNome());
            classificacaoTimeDTO.setPontos(vitorias.get() * 3 + empates.get());
            classificacaoTimeDTO.setDerrotas(derrotas.get());
            classificacaoTimeDTO.setEmpates(empates.get());
            classificacaoTimeDTO.setVitorias(vitorias.get());
            classificacaoTimeDTO.setGolsMarcados(golsMarcados.get());
            classificacaoTimeDTO.setGolsSofridos(golsSofridos.get());
            classificacaoTimeDTO.setJogos(derrotas.get() + empates.get() + vitorias.get());
            classificacaoDTO.getTimes().add(classificacaoTimeDTO);
        });
        return classificacaoDTO;
    }

    public JogoDTO obterJogo(Integer id) {
        return entityToDTO(jogoRepository.findById(id).get());
    }
}
