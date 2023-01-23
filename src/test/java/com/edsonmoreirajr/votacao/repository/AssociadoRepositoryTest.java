package com.edsonmoreirajr.votacao.repository;

import com.edsonmoreirajr.votacao.entity.Associado;
import com.edsonmoreirajr.votacao.repository.impl.PautaRepositoryCustomImpl;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AssociadoRepositoryTest {

    EasyRandomParameters parameters = new EasyRandomParameters()
            .excludeField(FieldPredicates.named("id"))
            .stringLengthRange(11, 11);
    private final EasyRandom easyRandom = new EasyRandom(parameters);

    @Autowired
    private AssociadoRepository associadoRepository;
    @Autowired
    private VotoRepository votoRepository;
    @MockBean
    private PautaRepositoryCustomImpl pautaRepositoryCustom;

    @BeforeEach
    public void cleanUp() {
        votoRepository.deleteAll();
        associadoRepository.deleteAll();
    }

    @Test
    public void deveRetornarPageDeAssociadosComMesmosAssociadosInseridosNoBanco_QuandoFindAll() {

        List<Associado> associados = easyRandom.objects(Associado.class, 10).toList();
        var pageable = PageRequest.of(0, 10);

        associadoRepository.saveAllAndFlush(associados);
        Page<Associado> associadoPage = associadoRepository.findAll(pageable);

        assertNotNull(associadoPage);
        assertFalse(associadoPage.isEmpty());
        assertThat(associados)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(associadoPage.getContent());
    }
}
