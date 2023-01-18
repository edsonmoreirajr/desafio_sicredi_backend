package com.edsonmoreirajr.votacao.repository;

import com.edsonmoreirajr.votacao.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    @Query("SELECT ativo FROM Sessao WHERE id = :id")
    Boolean findAtivoBySessaoId(Long id);

    Optional<Sessao> findByPautaId(Long pautaId);
}
