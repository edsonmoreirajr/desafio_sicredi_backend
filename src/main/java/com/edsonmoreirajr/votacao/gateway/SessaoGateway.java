package com.edsonmoreirajr.votacao.gateway;

import com.edsonmoreirajr.votacao.entity.Sessao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SessaoGateway {

    Optional<Sessao> getSessaoById(Long id);

    Page<Sessao> getAllSessoes(Pageable pageable);

    Sessao createOrUpdateSessao(Sessao sessao);

    void deleteSessao(Long id);

    Optional<Sessao> getSessaoByPautaId(Long pautaId);
}
