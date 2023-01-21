package com.edsonmoreirajr.votacao.repository;

import com.edsonmoreirajr.votacao.entity.Associado;
import com.edsonmoreirajr.votacao.entity.enums.EnumStatusAssociado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    @Query("SELECT status FROM Associado WHERE id = :id")
    EnumStatusAssociado findStatusByAssociadoId(Long id);

    Optional<Associado> findByCpf(String cpf);
}
