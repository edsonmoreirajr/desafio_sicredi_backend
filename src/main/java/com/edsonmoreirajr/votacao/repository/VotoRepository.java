package com.edsonmoreirajr.votacao.repository;

import com.edsonmoreirajr.votacao.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findByAssociadoIdAndSessaoId(Long associadoId, Long sessaoId);

    @Modifying
    @Query("DELETE FROM Voto WHERE sessao.id = :sessaoId")
    void deleteAllBySessaoId(Long sessaoId);
}
