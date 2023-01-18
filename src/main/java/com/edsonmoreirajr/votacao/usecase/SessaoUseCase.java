package com.edsonmoreirajr.votacao.usecase;

import com.edsonmoreirajr.votacao.dto.SessaoDto;
import com.edsonmoreirajr.votacao.dto.request.SessaoRequest;
import com.edsonmoreirajr.votacao.entity.Sessao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SessaoUseCase {
    Page<SessaoDto> getAllSessoes(Pageable pageable);

    SessaoDto updateSessao(Long id, SessaoRequest sessaoRequest);

    SessaoDto getSessaoById(Long id);

    void deleteSessao(Long id);

    SessaoDto abrirSessao(SessaoRequest sessaoRequest);

    void fecharSessao(Long id);

    void validaSessaoEstaAtiva(Sessao sessao, Long id);

    void verificaSeHaTempoRestanteParaVotar(Sessao sessao);
}
