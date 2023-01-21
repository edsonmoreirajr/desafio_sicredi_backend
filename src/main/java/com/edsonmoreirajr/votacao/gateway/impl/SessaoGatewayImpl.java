package com.edsonmoreirajr.votacao.gateway.impl;

import com.edsonmoreirajr.votacao.config.message.MessageSourceService;
import com.edsonmoreirajr.votacao.entity.Sessao;
import com.edsonmoreirajr.votacao.gateway.SessaoGateway;
import com.edsonmoreirajr.votacao.repository.SessaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessaoGatewayImpl implements SessaoGateway {
    private final static String ENTITY_NOT_FOUND_SESSAO = "entity-not-found.sessao";
    private final MessageSourceService messageSourceService;

    private final SessaoRepository sessaoRepository;

    @Override
    public Optional<Sessao> getSessaoById(Long id) {
        return sessaoRepository.findById(id);
    }

    @Override
    public Page<Sessao> getAllSessoes(Pageable pageable) {
        return sessaoRepository.findAll(pageable);
    }

    @Override
    public Sessao createOrUpdateSessao(Sessao sessao) {
        return sessaoRepository.save(sessao);
    }

    @Override
    public void deleteSessao(Long id) {
        var pauta = sessaoRepository.findById(id);
        if (pauta.isEmpty()) {
            throw new EntityNotFoundException(messageSourceService.getMessage(ENTITY_NOT_FOUND_SESSAO, id));
        }
        sessaoRepository.deleteById(id);
    }

    @Override
    public Optional<Sessao> getSessaoByPautaId(Long pautaId) {
        return sessaoRepository.findByPautaId(pautaId);
    }
}
